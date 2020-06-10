package com.yd.photoeditor.imageprocessing.temp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.yd.photoeditor.imageprocessing.filter.ImageRender;
import com.yd.photoeditor.imageprocessing.util.OpenGlUtils;
import com.yd.photoeditor.imageprocessing.util.TextureRotationUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.Queue;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ImageFilter2 implements GLSurfaceView.Renderer, Camera.PreviewCallback {
    public static final float[] CUBE = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
    public static final int NO_IMAGE = -1;
    protected int mAddedPadding;
    private float mAlpha = 1.0f;
    private float mBlue = 0.0f;
    private float mGreen = 0.0f;
    private float mRed = 0.0f;
    private boolean isEnableBlend = true;
    public ImageRender filter;
    private boolean flipHorizontal;
    private boolean flipVertical;
    private final FloatBuffer gLCubeBuffer;
    public IntBuffer gLRgbBuffer;
    private final FloatBuffer gLTextureBuffer;
    public int gLTextureId = -1;
    public int mImageHeight;
    public int mImageWidth;
    public int mOutputHeight;
    public int mOutputWidth;
    private ImageFilter6 mImageFilter6;
    private final Queue<Runnable> mRunOnDraw;
    private ImageFilter3.ScaleType mScaleType = ImageFilter3.ScaleType.CENTER_CROP;
    public SurfaceTexture mSurfaceTexture = null;
    public final Object mSurfaceChangedWaiter = new Object();


    private float addDistance(float f, float f2) {
        return f == 0.0f ? f2 : 1.0f - f2;
    }

    public ImageFilter2(ImageRender imageRender) {
        this.filter = imageRender;
        this.mRunOnDraw = new LinkedList();
        this.gLCubeBuffer = ByteBuffer.allocateDirect(CUBE.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.gLCubeBuffer.put(CUBE).position(0);
        this.gLTextureBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        setRotation(ImageFilter6.NORMAL, false, false);
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        GLES20.glClearColor(this.mRed, this.mGreen, this.mBlue, this.mAlpha);
        GLES20.glDisable(2929);
        if (this.isEnableBlend) {
            GLES20.glEnable(3042);
            GLES20.glBlendFunc(1, 771);
        }
        this.filter.init();
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        mOutputWidth = i;
        mOutputHeight = i2;
        GLES20.glViewport(0, 0, i, i2);
        GLES20.glUseProgram(this.filter.getProgram());
        this.filter.onOutputSizeChanged(i, i2);
        synchronized (this.mSurfaceChangedWaiter) {
            this.mSurfaceChangedWaiter.notifyAll();
        }
    }

    public void onDrawFrame(GL10 gl10) {

        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        synchronized (mRunOnDraw) {
            while (!this.mRunOnDraw.isEmpty()) {
                this.mRunOnDraw.poll().run();
            }
        }

        filter.onDraw(this.gLTextureId, this.gLCubeBuffer, this.gLTextureBuffer);
        if (mSurfaceTexture != null) {
            mSurfaceTexture.updateTexImage();
        }

    }

    public void onPreviewFrame(final byte[] bArr, final Camera camera) {

    }

    public void setEnableBlend(boolean z) {
        this.isEnableBlend = z;
    }

    public void setdownSurfaceTexture(final Camera camera) {
        addDraw(new Runnable() {
            public void run() {
                int[] iArr = new int[1];
                GLES20.glGenTextures(1, iArr, 0);
                SurfaceTexture unused = ImageFilter2.this.mSurfaceTexture = new SurfaceTexture(iArr[0]);
                try {
                    camera.setPreviewTexture(ImageFilter2.this.mSurfaceTexture);
                    camera.setPreviewCallback(ImageFilter2.this);
                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setFilter(final ImageRender imageRender) {
        addDraw(new Runnable() {
            public void run() {
                ImageRender temp = filter;
                filter = imageRender;
                if (temp != null) {
                    temp.destroy();
                }
                filter.init();
                GLES20.glUseProgram(filter.getProgram());
                filter.onOutputSizeChanged(mOutputWidth, mOutputHeight);

            }
        });
    }


    public void deleteImage() {
        addDraw(new Runnable() {
            public void run() {
                GLES20.glDeleteTextures(1, new int[]{ImageFilter2.this.gLTextureId}, 0);
                gLTextureId = -1;
            }
        });
    }

    public void setImageBitmap(Bitmap bitmap) {
        setImageBitmap(bitmap, true);
    }

    public void setImageBitmap(final Bitmap bitmap, final boolean recycle) {
        if (bitmap != null) {
            addDraw(new Runnable() {
                public void run() {
                    Bitmap bitmap2;
                    if (bitmap.getWidth() % 2 == 1) {
                        bitmap2 = Bitmap.createBitmap(bitmap.getWidth() + 1, bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap2);
                        canvas.drawARGB(0, 0, 0, 0);
                        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, null);
                        mAddedPadding = 1;
                    } else {
                        mAddedPadding = 0;
                        bitmap2 = null;
                    }
                    mImageWidth = bitmap.getWidth();
                    mImageHeight = bitmap.getHeight();
                    gLTextureId = OpenGlUtils.loadTexture(bitmap != null ? bitmap : bitmap, gLTextureId, recycle);
                    if (recycle && (bitmap2 = bitmap) != null && !bitmap2.isRecycled()) {
                        bitmap2.recycle();
                    }
                    adjustImageScaling();
                }
            });
        }
    }

    public void setScaleType(ImageFilter3.ScaleType scaleType) {
        this.mScaleType = scaleType;
    }

    public int getFrameWidth() {
        return this.mOutputWidth;
    }

    public int getFrameHeight() {
        return this.mOutputHeight;
    }

    public void adjustImageScaling() {
        float f;
        float f2;
        float f3 = (float) this.mOutputWidth;
        float f4 = (float) this.mOutputHeight;
        if (this.mImageFilter6 == ImageFilter6.ROTATION_270 || this.mImageFilter6 == ImageFilter6.ROTATION_90) {
            f3 = (float) this.mOutputHeight;
            f4 = (float) this.mOutputWidth;
        }
        float min = Math.min(f3 / ((float) this.mImageWidth), f4 / ((float) this.mImageHeight));
        this.mImageWidth = Math.round(((float) this.mImageWidth) * min);
        this.mImageHeight = Math.round(((float) this.mImageHeight) * min);
        int i = this.mImageWidth;
        if (((float) i) != f3) {
            f2 = ((float) i) / f3;
        } else {
            int i2 = this.mImageHeight;
            if (((float) i2) != f4) {
                f = ((float) i2) / f4;
                f2 = 1.0f;
                float[] fArr = CUBE;
                float[] rotation = TextureRotationUtil.getRotation(this.mImageFilter6, this.flipHorizontal, this.flipVertical);
                if (this.mScaleType != ImageFilter3.ScaleType.CENTER_CROP) {
                    float f5 = ((1.0f / f2) - 1.0f) / 2.0f;
                    float f6 = ((1.0f / f) - 1.0f) / 2.0f;
                    rotation = new float[]{addDistance(rotation[0], f6), addDistance(rotation[1], f5), addDistance(rotation[2], f6), addDistance(rotation[3], f5), addDistance(rotation[4], f6), addDistance(rotation[5], f5), addDistance(rotation[6], f6), addDistance(rotation[7], f5)};
                } else {
                    float[] fArr2 = CUBE;
                    fArr = new float[]{fArr2[0] * f2, fArr2[1] * f, fArr2[2] * f2, fArr2[3] * f, fArr2[4] * f2, fArr2[5] * f, fArr2[6] * f2, fArr2[7] * f};
                }
                this.gLCubeBuffer.clear();
                this.gLCubeBuffer.put(fArr).position(0);
                this.gLTextureBuffer.clear();
                this.gLTextureBuffer.put(rotation).position(0);
            }
            f2 = 1.0f;
        }
        f = 1.0f;
        float[] fArr3 = CUBE;
        float[] rotation2 = TextureRotationUtil.getRotation(this.mImageFilter6, this.flipHorizontal, this.flipVertical);
        if (this.mScaleType != ImageFilter3.ScaleType.CENTER_CROP) {
        }
        this.gLCubeBuffer.clear();
        this.gLCubeBuffer.put(fArr3).position(0);
        this.gLTextureBuffer.clear();
        this.gLTextureBuffer.put(rotation2).position(0);

    }

    public void setRotationCamera(ImageFilter6 imageFilter6, boolean z, boolean z2) {
        setRotation(imageFilter6, z2, z);
    }

    public void setRotation(ImageFilter6 imageFilter6, boolean z, boolean z2) {
        this.mImageFilter6 = imageFilter6;
        this.flipHorizontal = z;
        this.flipVertical = z2;
        adjustImageScaling();
    }

    public ImageFilter6 getRotation() {
        return this.mImageFilter6;
    }

    public boolean isFlippedHorizontally() {
        return this.flipHorizontal;
    }

    public boolean isFlippedVertically() {
        return this.flipVertical;
    }

    public void addDraw(Runnable runnable) {
        synchronized (this.mRunOnDraw) {
            mRunOnDraw.add(runnable);
        }
    }

    public void setBackground(float f, float f2, float f3, float f4) {
        this.mRed = f;
        this.mGreen = f2;
        this.mBlue = f3;
        this.mAlpha = f4;
    }
}
