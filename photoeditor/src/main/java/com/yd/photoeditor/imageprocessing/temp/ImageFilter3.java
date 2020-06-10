package com.yd.photoeditor.imageprocessing.temp;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.GLException;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.WindowManager;

import com.yd.photoeditor.imageprocessing.filter.ImageRender;
import com.yd.photoeditor.imageprocessing.filter.ImageRenderGroup;
import com.yd.photoeditor.imageprocessing.filter.TwoInputRender;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

public class ImageFilter3 {
    private static final String TAG = "ImageProcessor";
    /* access modifiers changed from: private */
    public final Context mContext;
    private Bitmap mCurrentBitmap;
    /* access modifiers changed from: private */
    public ImageRender mFilter;
    private GLSurfaceView mGlSurfaceView;
    /* access modifiers changed from: private */
    public final ImageFilter2 mRenderer;
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

    public ImageFilter3(Context context) {
        if (supportsOpenGLES2(context)) {
            this.mContext = context;
            this.mFilter = new ImageRender();
            this.mRenderer = new ImageFilter2(this.mFilter);
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
        if (mGlSurfaceView != null) {
            mGlSurfaceView.requestRender();
        }
    }

    private ReentrantLock lock = new ReentrantLock(false);

    public synchronized Bitmap getBitmap() {
//          Bitmap bitmap;
//
//        mGlSurfaceView.queueEvent(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        EGL10 egl = (EGL10) EGLContext.getEGL();
//                        GL10 gl = (GL10)egl.eglGetCurrentContext().getGL();
//
////                        mCurrentBitmap = createBitmapFromGLSurface(0, 0, mGlSurfaceView.getWidth(), mGlSurfaceView.getHeight(), gl);
//                        bitmap = readBufferPixelToBitmap(mGlSurfaceView.getWidth(), mGlSurfaceView.getWidth());
//                        try {
//                            lock.unlock();
//                        }catch (Exception e){
//                            Log.e(TAG, "lock.unlock(); failed " + e.getMessage());
//                        }
//                    }
//                }
//        );
//        lock.tryLock();
        EGL10 egl = (EGL10) EGLContext.getEGL();
        GL10 gl = (GL10) egl.eglGetCurrentContext().getGL();

        mCurrentBitmap = createBitmapFromGLSurface(0, 0, mGlSurfaceView.getWidth(), mGlSurfaceView.getHeight(), gl);
        return readBufferPixelToBitmap(mGlSurfaceView.getWidth(), mGlSurfaceView.getWidth());
        //return readBufferPixelToBitmap(mGlSurfaceView.getWidth(), mGlSurfaceView.getHeight());
    }

    private Bitmap readBufferPixelToBitmap(int width, int height) {
        ByteBuffer buf = ByteBuffer.allocateDirect(width * height * 4);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buf);
        buf.rewind();

        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmp.copyPixelsFromBuffer(buf);
        return bmp;
    }

    private Bitmap createBitmapFromGLSurface(int x, int y, int w, int h, GL10 gl) {

        int bitmapBuffer[] = new int[w * h];
        int bitmapSource[] = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);

        try {
            gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer);
            int offset1, offset2;
            for (int i = 0; i < h; i++) {
                offset1 = i * w;
                offset2 = (h - i - 1) * w;
                for (int j = 0; j < w; j++) {
                    int texturePixel = bitmapBuffer[offset1 + j];
                    int blue = (texturePixel >> 16) & 0xff;
                    int red = (texturePixel << 16) & 0x00ff0000;
                    int pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (GLException e) {
            Log.e(TAG, "createBitmapFromGLSurface: " + e.getMessage(), e);
            return null;
        }

        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
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
        ImageFilter6 imageFilter6 = ImageFilter6.NORMAL;
        if (i == 90) {
            imageFilter6 = ImageFilter6.ROTATION_90;
        } else if (i == 180) {
            imageFilter6 = ImageFilter6.ROTATION_180;
        } else if (i == 270) {
            imageFilter6 = ImageFilter6.ROTATION_270;
        }
        this.mRenderer.setRotationCamera(imageFilter6, z, z2);
    }

    private void setUpCameraGingerbread(Camera camera) {
        this.mRenderer.setdownSurfaceTexture(camera);
    }

    public void setFilter(ImageRender imageRender) {
        this.mFilter = imageRender;
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
        new LoadImageUriTask(this, uri).execute();
    }

    public void setImage(File file) {
        new LoadImageFileTask(this, file).execute();
    }

    /* access modifiers changed from: protected */
    public String getPath(Uri uri) {
        Cursor query = this.mContext.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        String string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_data")) : null;
        query.close();
        return string;
    }

    public Bitmap getBitmapWithFilterApplied() {
        //return getBitmapWithFilterApplied(mCurrentBitmap);
        return getBitmapWithFilterApplied(mCurrentBitmap);
    }

    public Bitmap getBitmapWithFilterApplied(Bitmap bitmap) {
        if (this.mGlSurfaceView != null) {
            this.mRenderer.deleteImage();
            final Semaphore semaphore = new Semaphore(0);
            this.mRenderer.addDraw(new Runnable() {
                public void run() {
                    ImageFilter3.this.mFilter.destroy();
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
        ImageFilter2 imageFilter2 = new ImageFilter2(mFilter);
        imageFilter2.setRotation(ImageFilter6.NORMAL, this.mRenderer.isFlippedHorizontally(), this.mRenderer.isFlippedVertically());
        imageFilter2.setScaleType(this.mScaleType);
        ImageFilter5 imageFilter5 = new ImageFilter5(bitmap.getWidth(), bitmap.getHeight());
        imageFilter5.setRenderer(imageFilter2);
        imageFilter2.setImageBitmap(bitmap, false);
        Bitmap bitmap2 = imageFilter5.getBitmap();
        this.mFilter.destroy();
        imageFilter2.deleteImage();
        imageFilter5.destroy();
        this.mFilter = new ImageRender();
        this.mRenderer.setFilter(this.mFilter);
        if (mCurrentBitmap != null) {
            this.mRenderer.setImageBitmap(mCurrentBitmap, false);
        }
        requestRender();
        return bitmap2;
    }

    public static Bitmap getFiltratedBitmap(Bitmap bitmap, ImageRender imageRender) {
        boolean z;
        ImageFilter2 imageFilter2 = new ImageFilter2(imageRender);
        imageFilter2.setEnableBlend(false);
        ImageFilter5 imageFilter5 = new ImageFilter5(bitmap.getWidth(), bitmap.getHeight());
        imageFilter5.setRenderer(imageFilter2);
        imageFilter2.setImageBitmap(bitmap, false);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        boolean z2 = imageRender instanceof TwoInputRender;
        if (z2) {
            TwoInputRender twoInputFilter = (TwoInputRender) imageRender;
            z = twoInputFilter.isRecycleBitmap();
            twoInputFilter.setRecycleBitmap(false);
        } else {
            if (imageRender instanceof ImageRenderGroup) {
                ImageRenderGroup imageFilterGroup = (ImageRenderGroup) imageRender;
                for (int i = 0; i < imageFilterGroup.getFilters().size(); i++) {
                    if (imageFilterGroup.getFilters().get(i) instanceof TwoInputRender) {
                        TwoInputRender twoInputFilter2 = (TwoInputRender) imageFilterGroup.getFilters().get(i);
                        sparseBooleanArray.put(i, twoInputFilter2.isRecycleBitmap());
                        twoInputFilter2.setRecycleBitmap(false);
                    }
                }
            }
            z = false;
        }
        imageFilter2.setFilter(imageRender);
        Bitmap bitmap2 = imageFilter5.getBitmap();
        if (z2) {
            ((TwoInputRender) imageRender).setRecycleBitmap(z);
        } else if (imageRender instanceof ImageRenderGroup) {
            ImageRenderGroup imageFilterGroup2 = (ImageRenderGroup) imageRender;
            for (int i2 = 0; i2 < imageFilterGroup2.getFilters().size(); i2++) {
                if (imageFilterGroup2.getFilters().get(i2) instanceof TwoInputRender) {
                    ((TwoInputRender) imageFilterGroup2.getFilters().get(i2)).setRecycleBitmap(sparseBooleanArray.get(i2, true));
                }
            }
        }
        imageRender.destroy();
        imageFilter2.deleteImage();
        imageFilter5.destroy();
        return bitmap2;

    }

    public Bitmap getCurrentBitmap() {
        requestRender();
        ImageFilter5 imageFilter5 = new ImageFilter5(mCurrentBitmap.getWidth(), mCurrentBitmap.getHeight());
        imageFilter5.setRenderer(mRenderer);
        for (int i = 0 ;i<1000; i++) {
            requestRender();
        }

        return imageFilter5.getBitmap();
     }

    public static void getBitmapForMultipleFilters(Bitmap bitmap, List<ImageRender> list, ResponseListener<Bitmap> responseListener) {
        if (!list.isEmpty()) {
            ImageFilter2 imageFilter2 = new ImageFilter2(list.get(0));
            imageFilter2.setImageBitmap(bitmap, false);
            ImageFilter5 imageFilter5 = new ImageFilter5(bitmap.getWidth(), bitmap.getHeight());
            imageFilter5.setRenderer(imageFilter2);
            for (ImageRender next : list) {
                imageFilter2.setFilter(next);
                responseListener.response(imageFilter5.getBitmap());
                next.destroy();
            }
            imageFilter2.deleteImage();
            imageFilter5.destroy();
        }
    }

    /* access modifiers changed from: private */
    public int getOutputWidth() {
        ImageFilter2 imageFilter2 = this.mRenderer;
        if (imageFilter2 != null && imageFilter2.getFrameWidth() != 0) {
            return this.mRenderer.getFrameWidth();
        }
        Bitmap bitmap = this.mCurrentBitmap;
        if (bitmap != null) {
            return bitmap.getWidth();
        }
        Display defaultDisplay = this.mContext.getSystemService(WindowManager.class).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /* access modifiers changed from: private */
    public int getOutputHeight() {
        ImageFilter2 imageFilter2 = this.mRenderer;
        if (imageFilter2 != null && imageFilter2.getFrameHeight() != 0) {
            return this.mRenderer.getFrameHeight();
        }
        Bitmap bitmap = this.mCurrentBitmap;
        if (bitmap != null) {
            return bitmap.getHeight();
        }
        Display defaultDisplay = this.mContext.getSystemService(WindowManager.class).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private class LoadImageUriTask extends LoadImageTask {
        private final Uri mUri;

        public LoadImageUriTask(ImageFilter3 imageFilter3, Uri uri) {
            super(imageFilter3);
            this.mUri = uri;
        }

        public Bitmap decode(BitmapFactory.Options options) {
            InputStream inputStream;
            try {
                if (!this.mUri.getScheme().startsWith("http")) {
                    if (!this.mUri.getScheme().startsWith("https")) {
                        inputStream = ImageFilter3.this.mContext.getContentResolver().openInputStream(this.mUri);
                        return BitmapFactory.decodeStream(inputStream, null, options);
                    }
                }
                inputStream = new URL(this.mUri.toString()).openStream();
                return BitmapFactory.decodeStream(inputStream, null, options);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public int getImageOrientation() throws IOException {
            Cursor query = ImageFilter3.this.mContext.getContentResolver().query(this.mUri, new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);
            if (query == null || query.getCount() != 1) {
                return 0;
            }
            query.moveToFirst();
            return query.getInt(0);
        }
    }

    private class LoadImageFileTask extends LoadImageTask {
        private final File mImageFile;

        public LoadImageFileTask(ImageFilter3 imageFilter3, File file) {
            super(imageFilter3);
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
        private final ImageFilter3 mGPUImage;
        private int mOutputHeight;
        private int mOutputWidth;

        /* access modifiers changed from: protected */
        public abstract Bitmap decode(BitmapFactory.Options options);

        /* access modifiers changed from: protected */
        public abstract int getImageOrientation() throws IOException;

        public LoadImageTask(ImageFilter3 imageFilter3) {
            this.mGPUImage = imageFilter3;
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            if (ImageFilter3.this.mRenderer != null && ImageFilter3.this.mRenderer.getFrameWidth() == 0) {
                try {
                    synchronized (ImageFilter3.this.mRenderer.mSurfaceChangedWaiter) {
                        ImageFilter3.this.mRenderer.mSurfaceChangedWaiter.wait(3000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.mOutputWidth = ImageFilter3.this.getOutputWidth();
            this.mOutputHeight = ImageFilter3.this.getOutputHeight();
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
            if (ImageFilter3.this.mScaleType != ScaleType.CENTER_CROP) {
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
            if (ImageFilter3.this.mScaleType != ScaleType.CENTER_CROP ? f4 < f6 : f4 > f6) {
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
            return ImageFilter3.this.mScaleType == ScaleType.CENTER_CROP ? z && z2 : z || z2;
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
