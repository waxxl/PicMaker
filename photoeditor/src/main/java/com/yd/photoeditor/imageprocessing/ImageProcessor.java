package com.yd.photoeditor.imageprocessing;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.WindowManager;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;
import com.yd.photoeditor.imageprocessing.filter.ImageFilterGroup;
import com.yd.photoeditor.imageprocessing.filter.TwoInputFilter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ImageProcessor {
    /* access modifiers changed from: private */
    public final Context mContext;
    private Bitmap mCurrentBitmap;
    /* access modifiers changed from: private */
    public ImageFilter mFilter;
    private GLSurfaceView mGlSurfaceView;
    /* access modifiers changed from: private */
    public final ImageRenderer mRenderer;
    /* access modifiers changed from: private */
    public ScaleType mScaleType = ScaleType.CENTER_CROP;

    public interface OnPictureSavedListener {
        void onPictureSaved(Uri uri);
    }

    public interface ResponseListener<T> {
        void response(T t);
    }

    public enum ScaleType {
        CENTER_INSIDE,
        CENTER_CROP
    }

    public void saveToPictures(Bitmap bitmap, String str, String str2, OnPictureSavedListener onPictureSavedListener) {
    }

    public void saveToPictures(String str, String str2, OnPictureSavedListener onPictureSavedListener) {
    }

    public ImageProcessor(Context context) {
        if (supportsOpenGLES2(context)) {
            this.mContext = context;
            this.mFilter = new ImageFilter();
            this.mRenderer = new ImageRenderer(this.mFilter);
            return;
        }
        throw new IllegalStateException("OpenGL ES 2.0 is not supported on this phone.");
    }

    public void setBackground(float f, float f2, float f3, float f4) {
        this.mRenderer.setBackground(f, f2, f3, f4);
    }

    @SuppressLint("WrongConstant")
    private boolean supportsOpenGLES2(Context context) {
        return ((ActivityManager) context.getSystemService("activity")).getDeviceConfigurationInfo().reqGlEsVersion >= 131072;
    }

    public void setGLSurfaceView(GLSurfaceView gLSurfaceView) {
        this.mGlSurfaceView = gLSurfaceView;
        this.mGlSurfaceView.getHolder().setFormat(-3);
        this.mGlSurfaceView.setEGLContextClientVersion(2);
        this.mGlSurfaceView.setRenderer(this.mRenderer);
        this.mGlSurfaceView.setRenderMode(0);
        this.mGlSurfaceView.requestRender();
    }

    public void requestRender() {
        GLSurfaceView gLSurfaceView = this.mGlSurfaceView;
        if (gLSurfaceView != null) {
            gLSurfaceView.requestRender();
        }
    }

    public void setUpCamera(Camera camera) {
        setUpCamera(camera, 0, false, false);
    }

    public void setUpCamera(Camera camera, int i, boolean z, boolean z2) {
        this.mGlSurfaceView.setRenderMode(1);
        if (Build.VERSION.SDK_INT > 10) {
            setUpCameraGingerbread(camera);
        } else {
            camera.setPreviewCallback(this.mRenderer);
            camera.startPreview();
        }
        Rotation rotation = Rotation.NORMAL;
        if (i == 90) {
            rotation = Rotation.ROTATION_90;
        } else if (i == 180) {
            rotation = Rotation.ROTATION_180;
        } else if (i == 270) {
            rotation = Rotation.ROTATION_270;
        }
        this.mRenderer.setRotationCamera(rotation, z, z2);
    }

    private void setUpCameraGingerbread(Camera camera) {
        this.mRenderer.setUpSurfaceTexture(camera);
    }

    public void setFilter(ImageFilter imageFilter) {
        this.mFilter = imageFilter;
        this.mRenderer.setFilter(this.mFilter);
        requestRender();
    }

    public void setImage(Bitmap bitmap) {
        setImage(bitmap, false);
        this.mCurrentBitmap = bitmap;
    }

    private void setImage(Bitmap bitmap, boolean z) {
        this.mRenderer.setImageBitmap(bitmap, z);
        requestRender();
    }

    public void setScaleType(ScaleType scaleType) {
        this.mScaleType = scaleType;
        this.mRenderer.setScaleType(scaleType);
        this.mRenderer.deleteImage();
        this.mCurrentBitmap = null;
        requestRender();
    }

    public void deleteImage() {
        this.mRenderer.deleteImage();
        this.mCurrentBitmap = null;
        requestRender();
    }

    public void setImage(Uri uri) {
        new LoadImageUriTask(this, uri).execute(new Void[0]);
    }

    public void setImage(File file) {
        new LoadImageFileTask(this, file).execute(new Void[0]);
    }

    /* access modifiers changed from: protected */
    public String getPath(Uri uri) {
        Cursor query = this.mContext.getContentResolver().query(uri, new String[]{"_data"}, (String) null, (String[]) null, (String) null);
        String string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_data")) : null;
        query.close();
        return string;
    }

    public Bitmap getBitmapWithFilterApplied() {
        return getBitmapWithFilterApplied(this.mCurrentBitmap);
    }

    public Bitmap getBitmapWithFilterApplied(Bitmap bitmap) {
        if (this.mGlSurfaceView != null) {
            this.mRenderer.deleteImage();
            final Semaphore semaphore = new Semaphore(0);
            this.mRenderer.runOnDraw(new Runnable() {
                public void run() {
                    ImageProcessor.this.mFilter.destroy();
                    semaphore.release();
                }
            });
            requestRender();
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ImageRenderer imageRenderer = new ImageRenderer(this.mFilter);
        imageRenderer.setRotation(Rotation.NORMAL, this.mRenderer.isFlippedHorizontally(), this.mRenderer.isFlippedVertically());
        imageRenderer.setScaleType(this.mScaleType);
        PixelBuffer pixelBuffer = new PixelBuffer(bitmap.getWidth(), bitmap.getHeight());
        pixelBuffer.setRenderer(imageRenderer);
        imageRenderer.setImageBitmap(bitmap, false);
        Bitmap bitmap2 = pixelBuffer.getBitmap();
        this.mFilter.destroy();
        imageRenderer.deleteImage();
        pixelBuffer.destroy();
        this.mFilter = new ImageFilter();
        this.mRenderer.setFilter(this.mFilter);
        Bitmap bitmap3 = this.mCurrentBitmap;
        if (bitmap3 != null) {
            this.mRenderer.setImageBitmap(bitmap3, false);
        }
        requestRender();
        return bitmap2;
    }

    public static Bitmap getFiltratedBitmap(Bitmap bitmap, ImageFilter imageFilter) {
        boolean z;
        ImageRenderer imageRenderer = new ImageRenderer(imageFilter);
        imageRenderer.setEnableBlend(false);
        PixelBuffer pixelBuffer = new PixelBuffer(bitmap.getWidth(), bitmap.getHeight());
        pixelBuffer.setRenderer(imageRenderer);
        imageRenderer.setImageBitmap(bitmap, false);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        boolean z2 = imageFilter instanceof TwoInputFilter;
        if (z2) {
            TwoInputFilter twoInputFilter = (TwoInputFilter) imageFilter;
            z = twoInputFilter.isRecycleBitmap();
            twoInputFilter.setRecycleBitmap(false);
        } else {
            if (imageFilter instanceof ImageFilterGroup) {
                ImageFilterGroup imageFilterGroup = (ImageFilterGroup) imageFilter;
                for (int i = 0; i < imageFilterGroup.getFilters().size(); i++) {
                    if (imageFilterGroup.getFilters().get(i) instanceof TwoInputFilter) {
                        TwoInputFilter twoInputFilter2 = (TwoInputFilter) imageFilterGroup.getFilters().get(i);
                        sparseBooleanArray.put(i, twoInputFilter2.isRecycleBitmap());
                        twoInputFilter2.setRecycleBitmap(false);
                    }
                }
            }
            z = false;
        }
        imageRenderer.setFilter(imageFilter);
        Bitmap bitmap2 = pixelBuffer.getBitmap();
        if (z2) {
            ((TwoInputFilter) imageFilter).setRecycleBitmap(z);
        } else if (imageFilter instanceof ImageFilterGroup) {
            ImageFilterGroup imageFilterGroup2 = (ImageFilterGroup) imageFilter;
            for (int i2 = 0; i2 < imageFilterGroup2.getFilters().size(); i2++) {
                if (imageFilterGroup2.getFilters().get(i2) instanceof TwoInputFilter) {
                    ((TwoInputFilter) imageFilterGroup2.getFilters().get(i2)).setRecycleBitmap(sparseBooleanArray.get(i2, true));
                }
            }
        }
        imageFilter.destroy();
        imageRenderer.deleteImage();
        pixelBuffer.destroy();
        return bitmap2;
    }

    public static void getBitmapForMultipleFilters(Bitmap bitmap, List<ImageFilter> list, ResponseListener<Bitmap> responseListener) {
        if (!list.isEmpty()) {
            ImageRenderer imageRenderer = new ImageRenderer(list.get(0));
            imageRenderer.setImageBitmap(bitmap, false);
            PixelBuffer pixelBuffer = new PixelBuffer(bitmap.getWidth(), bitmap.getHeight());
            pixelBuffer.setRenderer(imageRenderer);
            for (ImageFilter next : list) {
                imageRenderer.setFilter(next);
                responseListener.response(pixelBuffer.getBitmap());
                next.destroy();
            }
            imageRenderer.deleteImage();
            pixelBuffer.destroy();
        }
    }

    /* access modifiers changed from: private */
    public int getOutputWidth() {
        ImageRenderer imageRenderer = this.mRenderer;
        if (imageRenderer != null && imageRenderer.getFrameWidth() != 0) {
            return this.mRenderer.getFrameWidth();
        }
        Bitmap bitmap = this.mCurrentBitmap;
        if (bitmap != null) {
            return bitmap.getWidth();
        }
        Display defaultDisplay = ((WindowManager) this.mContext.getSystemService(WindowManager.class)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /* access modifiers changed from: private */
    public int getOutputHeight() {
        ImageRenderer imageRenderer = this.mRenderer;
        if (imageRenderer != null && imageRenderer.getFrameHeight() != 0) {
            return this.mRenderer.getFrameHeight();
        }
        Bitmap bitmap = this.mCurrentBitmap;
        if (bitmap != null) {
            return bitmap.getHeight();
        }
        Display defaultDisplay = ((WindowManager) this.mContext.getSystemService(WindowManager.class)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private class LoadImageUriTask extends LoadImageTask {
        private final Uri mUri;

        public LoadImageUriTask(ImageProcessor imageProcessor, Uri uri) {
            super(imageProcessor);
            this.mUri = uri;
        }

        public Bitmap decode(BitmapFactory.Options options) {
            InputStream inputStream;
            try {
                if (!this.mUri.getScheme().startsWith("http")) {
                    if (!this.mUri.getScheme().startsWith("https")) {
                        inputStream = ImageProcessor.this.mContext.getContentResolver().openInputStream(this.mUri);
                        return BitmapFactory.decodeStream(inputStream, (Rect) null, options);
                    }
                }
                inputStream = new URL(this.mUri.toString()).openStream();
                return BitmapFactory.decodeStream(inputStream, (Rect) null, options);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public int getImageOrientation() throws IOException {
            Cursor query = ImageProcessor.this.mContext.getContentResolver().query(this.mUri, new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, (String) null, (String[]) null, (String) null);
            if (query == null || query.getCount() != 1) {
                return 0;
            }
            query.moveToFirst();
            return query.getInt(0);
        }
    }

    private class LoadImageFileTask extends LoadImageTask {
        private final File mImageFile;

        public LoadImageFileTask(ImageProcessor imageProcessor, File file) {
            super(imageProcessor);
            this.mImageFile = file;
        }

        /* access modifiers changed from: protected */
        public Bitmap decode(BitmapFactory.Options options) {
            return BitmapFactory.decodeFile(this.mImageFile.getAbsolutePath(), options);
        }

        /* access modifiers changed from: protected */
        public int getImageOrientation() throws IOException {
            int attributeInt = new ExifInterface(this.mImageFile.getAbsolutePath()).getAttributeInt("Orientation", 1);
            if (attributeInt == 1) {
                return 0;
            }
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt != 6) {
                return attributeInt != 8 ? 0 : 270;
            }
            return 90;
        }
    }

    private abstract class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {
        private final ImageProcessor mGPUImage;
        private int mOutputHeight;
        private int mOutputWidth;

        /* access modifiers changed from: protected */
        public abstract Bitmap decode(BitmapFactory.Options options);

        /* access modifiers changed from: protected */
        public abstract int getImageOrientation() throws IOException;

        public LoadImageTask(ImageProcessor imageProcessor) {
            this.mGPUImage = imageProcessor;
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            if (ImageProcessor.this.mRenderer != null && ImageProcessor.this.mRenderer.getFrameWidth() == 0) {
                try {
                    synchronized (ImageProcessor.this.mRenderer.mSurfaceChangedWaiter) {
                        ImageProcessor.this.mRenderer.mSurfaceChangedWaiter.wait(3000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.mOutputWidth = ImageProcessor.this.getOutputWidth();
            this.mOutputHeight = ImageProcessor.this.getOutputHeight();
            return loadResizedImage();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            this.mGPUImage.setImage(bitmap);
        }

        private Bitmap loadResizedImage() {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            decode(options);
            int i = 1;
            while (true) {
                boolean z = false;
                boolean z2 = options.outWidth / i > this.mOutputWidth;
                if (options.outHeight / i > this.mOutputHeight) {
                    z = true;
                }
                if (!checkSize(z2, z)) {
                    break;
                }
                i++;
            }
            int i2 = i - 1;
            if (i2 < 1) {
                i2 = 1;
            }
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = i2;
            options2.inPreferredConfig = Bitmap.Config.RGB_565;
            options2.inPurgeable = true;
            options2.inTempStorage = new byte[32768];
            Bitmap decode = decode(options2);
            if (decode == null) {
                return null;
            }
            return scaleBitmap(rotateImage(decode));
        }

        private Bitmap scaleBitmap(Bitmap bitmap) {
            int[] scaleSize = getScaleSize(bitmap.getWidth(), bitmap.getHeight());
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, scaleSize[0], scaleSize[1], true);
            bitmap.recycle();
            System.gc();
            if (ImageProcessor.this.mScaleType != ScaleType.CENTER_CROP) {
                return createScaledBitmap;
            }
            int i = scaleSize[0] - this.mOutputWidth;
            int i2 = scaleSize[1] - this.mOutputHeight;
            Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap, i / 2, i2 / 2, scaleSize[0] - i, scaleSize[1] - i2);
            createScaledBitmap.recycle();
            return createBitmap;
        }

        private int[] getScaleSize(int i, int i2) {
            float f;
            float f2;
            float f3 = (float) i;
            float f4 = f3 / ((float) this.mOutputWidth);
            float f5 = (float) i2;
            float f6 = f5 / ((float) this.mOutputHeight);
            if (ImageProcessor.this.mScaleType != ScaleType.CENTER_CROP ? f4 < f6 : f4 > f6) {
                float f7 = (float) this.mOutputHeight;
                f = (f7 / f5) * f3;
                f2 = f7;
            } else {
                float f8 = (float) this.mOutputWidth;
                f2 = (f8 / f3) * f5;
                f = f8;
            }
            return new int[]{Math.round(f), Math.round(f2)};
        }

        private boolean checkSize(boolean z, boolean z2) {
            return ImageProcessor.this.mScaleType == ScaleType.CENTER_CROP ? z && z2 : z || z2;
        }

        private Bitmap rotateImage(Bitmap bitmap) {
            if (bitmap == null) {
                return null;
            }
            try {
                int imageOrientation = getImageOrientation();
                if (imageOrientation == 0) {
                    return bitmap;
                }
                Matrix matrix = new Matrix();
                matrix.postRotate((float) imageOrientation);
                Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return createBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return bitmap;
            }
        }
    }
}
