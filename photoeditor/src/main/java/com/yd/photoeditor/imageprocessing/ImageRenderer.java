package com.yd.photoeditor.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.yd.photoeditor.imageprocessing.filter.ImageFilter;
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

public class ImageRenderer implements GLSurfaceView.Renderer, Camera.PreviewCallback {
    public static final float[] CUBE = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
    public static final int NO_IMAGE = -1;
    protected int mAddedPadding;
    private float mBgAlpha = 1.0f;
    private float mBgBlue = 0.0f;
    private float mBgGreen = 0.0f;
    private float mBgRed = 0.0f;
    private boolean mEnableBlend = true;
    /* access modifiers changed from: private */
    public ImageFilter mFilter;
    private boolean mFlipHorizontal;
    private boolean mFlipVertical;
    private final FloatBuffer mGLCubeBuffer;
    /* access modifiers changed from: private */
    public IntBuffer mGLRgbBuffer;
    private final FloatBuffer mGLTextureBuffer;
    /* access modifiers changed from: private */
    public int mGLTextureId = -1;
    /* access modifiers changed from: private */
    public int mImageHeight;
    /* access modifiers changed from: private */
    public int mImageWidth;
    /* access modifiers changed from: private */
    public int mOutputHeight;
    /* access modifiers changed from: private */
    public int mOutputWidth;
    private Rotation mRotation;
    private final Queue<Runnable> mRunOnDraw;
    private ImageProcessor.ScaleType mScaleType = ImageProcessor.ScaleType.CENTER_CROP;
    public final Object mSurfaceChangedWaiter = new Object();
    /* access modifiers changed from: private */
    public SurfaceTexture mSurfaceTexture = null;

    private float addDistance(float f, float f2) {
        return f == 0.0f ? f2 : 1.0f - f2;
    }

    public ImageRenderer(ImageFilter imageFilter) {
        this.mFilter = imageFilter;
        this.mRunOnDraw = new LinkedList();
        this.mGLCubeBuffer = ByteBuffer.allocateDirect(CUBE.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mGLCubeBuffer.put(CUBE).position(0);
        this.mGLTextureBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        setRotation(Rotation.NORMAL, false, false);
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        GLES20.glClearColor(this.mBgRed, this.mBgGreen, this.mBgBlue, this.mBgAlpha);
        GLES20.glDisable(2929);
        if (this.mEnableBlend) {
            GLES20.glEnable(3042);
            GLES20.glBlendFunc(1, 771);
        }
        this.mFilter.init();
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        this.mOutputWidth = i;
        this.mOutputHeight = i2;
        GLES20.glViewport(0, 0, i, i2);
        GLES20.glUseProgram(this.mFilter.getProgram());
        this.mFilter.onOutputSizeChanged(i, i2);
        synchronized (this.mSurfaceChangedWaiter) {
            this.mSurfaceChangedWaiter.notifyAll();
        }
    }

    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(16640);
        synchronized (this.mRunOnDraw) {
            while (!this.mRunOnDraw.isEmpty()) {
                this.mRunOnDraw.poll().run();
            }
        }
        this.mFilter.onDraw(this.mGLTextureId, this.mGLCubeBuffer, this.mGLTextureBuffer);
        SurfaceTexture surfaceTexture = this.mSurfaceTexture;
        if (surfaceTexture != null) {
            surfaceTexture.updateTexImage();
        }
    }

    public void onPreviewFrame(final byte[] bArr, final Camera camera) {

    }

    public void setEnableBlend(boolean z) {
        this.mEnableBlend = z;
    }

    public void setUpSurfaceTexture(final Camera camera) {
        runOnDraw(new Runnable() {
            public void run() {
                int[] iArr = new int[1];
                GLES20.glGenTextures(1, iArr, 0);
                SurfaceTexture unused = ImageRenderer.this.mSurfaceTexture = new SurfaceTexture(iArr[0]);
                try {
                    camera.setPreviewTexture(ImageRenderer.this.mSurfaceTexture);
                    camera.setPreviewCallback(ImageRenderer.this);
                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setFilter(final ImageFilter imageFilter) {
        runOnDraw(new Runnable() {
            public void run() {
                ImageFilter access$600 = ImageRenderer.this.mFilter;
                ImageFilter unused = ImageRenderer.this.mFilter = imageFilter;
                if (access$600 != null) {
                    access$600.destroy();
                }
                ImageRenderer.this.mFilter.init();
                GLES20.glUseProgram(ImageRenderer.this.mFilter.getProgram());
                ImageRenderer.this.mFilter.onOutputSizeChanged(ImageRenderer.this.mOutputWidth, ImageRenderer.this.mOutputHeight);
            }
        });
    }

    public void deleteImage() {
        runOnDraw(new Runnable() {
            public void run() {
                GLES20.glDeleteTextures(1, new int[]{ImageRenderer.this.mGLTextureId}, 0);
                int unused = ImageRenderer.this.mGLTextureId = -1;
            }
        });
    }

    public void setImageBitmap(Bitmap bitmap) {
        setImageBitmap(bitmap, true);
    }

    public void setImageBitmap(final Bitmap bitmap, final boolean z) {
        if (bitmap != null) {
            runOnDraw(new Runnable() {
                public void run() {
                    Bitmap bitmap2;
                    if (bitmap.getWidth() % 2 == 1) {
                        bitmap2 = Bitmap.createBitmap(bitmap.getWidth() + 1, bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap2);
                        canvas.drawARGB(0, 0, 0, 0);
                        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, (Paint) null);
                        ImageRenderer.this.mAddedPadding = 1;
                    } else {
                        ImageRenderer.this.mAddedPadding = 0;
                        bitmap2 = null;
                    }
                    int unused = ImageRenderer.this.mImageWidth = bitmap.getWidth();
                    int unused2 = ImageRenderer.this.mImageHeight = bitmap.getHeight();
                    int unused3 = ImageRenderer.this.mGLTextureId = OpenGlUtils.loadTexture(bitmap != null ? bitmap : bitmap, ImageRenderer.this.mGLTextureId, z);
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    if (z && (bitmap2 = bitmap) != null && !bitmap2.isRecycled()) {
                        bitmap.recycle();
                    }
                    ImageRenderer.this.adjustImageScaling();
                }
            });
        }
    }

    public void setScaleType(ImageProcessor.ScaleType scaleType) {
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
        if (this.mRotation == Rotation.ROTATION_270 || this.mRotation == Rotation.ROTATION_90) {
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
                float[] rotation = TextureRotationUtil.getRotation(this.mRotation, this.mFlipHorizontal, this.mFlipVertical);
                if (this.mScaleType != ImageProcessor.ScaleType.CENTER_CROP) {
                    float f5 = ((1.0f / f2) - 1.0f) / 2.0f;
                    float f6 = ((1.0f / f) - 1.0f) / 2.0f;
                    rotation = new float[]{addDistance(rotation[0], f6), addDistance(rotation[1], f5), addDistance(rotation[2], f6), addDistance(rotation[3], f5), addDistance(rotation[4], f6), addDistance(rotation[5], f5), addDistance(rotation[6], f6), addDistance(rotation[7], f5)};
                } else {
                    float[] fArr2 = CUBE;
                    fArr = new float[]{fArr2[0] * f2, fArr2[1] * f, fArr2[2] * f2, fArr2[3] * f, fArr2[4] * f2, fArr2[5] * f, fArr2[6] * f2, fArr2[7] * f};
                }
                this.mGLCubeBuffer.clear();
                this.mGLCubeBuffer.put(fArr).position(0);
                this.mGLTextureBuffer.clear();
                this.mGLTextureBuffer.put(rotation).position(0);
            }
            f2 = 1.0f;
        }
        f = 1.0f;
        float[] fArr3 = CUBE;
        float[] rotation2 = TextureRotationUtil.getRotation(this.mRotation, this.mFlipHorizontal, this.mFlipVertical);
        if (this.mScaleType != ImageProcessor.ScaleType.CENTER_CROP) {
        }
        this.mGLCubeBuffer.clear();
        this.mGLCubeBuffer.put(fArr3).position(0);
        this.mGLTextureBuffer.clear();
        this.mGLTextureBuffer.put(rotation2).position(0);

    }

    public void setRotationCamera(Rotation rotation, boolean z, boolean z2) {
        setRotation(rotation, z2, z);
    }

    public void setRotation(Rotation rotation, boolean z, boolean z2) {
        this.mRotation = rotation;
        this.mFlipHorizontal = z;
        this.mFlipVertical = z2;
        adjustImageScaling();
    }

    public Rotation getRotation() {
        return this.mRotation;
    }

    public boolean isFlippedHorizontally() {
        return this.mFlipHorizontal;
    }

    public boolean isFlippedVertically() {
        return this.mFlipVertical;
    }

    public void runOnDraw(Runnable runnable) {
        synchronized (this.mRunOnDraw) {
            this.mRunOnDraw.add(runnable);
        }
    }

    public void setBackground(float f, float f2, float f3, float f4) {
        this.mBgRed = f;
        this.mBgGreen = f2;
        this.mBgBlue = f3;
        this.mBgAlpha = f4;
    }
}
