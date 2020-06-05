package com.yd.photoeditor.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.util.Log;
import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

public class PixelBuffer {
    static final boolean LIST_CONFIGS = false;
    static final String TAG = "PixelBuffer";
    Bitmap mBitmap;
    EGL10 mEGL = ((EGL10) EGLContext.getEGL());
    EGLConfig mEGLConfig;
    EGLConfig[] mEGLConfigs;
    EGLContext mEGLContext;
    EGLDisplay mEGLDisplay = mEGL.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
    EGLSurface mEGLSurface;
    GL10 mGL;
    int mHeight;
    GLSurfaceView.Renderer mRenderer;
    String mThreadOwner;
    int mWidth;

    public PixelBuffer(int i, int i2) {
        mWidth = i;
        mHeight = i2;
        int[] iArr = {12375, mWidth, 12374, this.mHeight, 12344};
        mEGL.eglInitialize(this.mEGLDisplay, new int[2]);
        mEGLConfig = chooseConfig();
        mEGLContext = mEGL.eglCreateContext(mEGLDisplay, mEGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
        mEGLSurface = mEGL.eglCreatePbufferSurface(mEGLDisplay, mEGLConfig, iArr);
        EGL10 egl10 = mEGL;
        EGLDisplay eGLDisplay = this.mEGLDisplay;
        EGLSurface eGLSurface = this.mEGLSurface;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.mEGLContext);
        this.mGL = (GL10) this.mEGLContext.getGL();
        this.mThreadOwner = Thread.currentThread().getName();
    }

    public void setRenderer(GLSurfaceView.Renderer renderer) {
        mRenderer = renderer;
        if (!Thread.currentThread().getName().equals(this.mThreadOwner)) {
            Log.e(TAG, "setRenderer: This thread does not own the OpenGL context.");
            return;
        }
        mRenderer.onSurfaceCreated(mGL, this.mEGLConfig);
        mRenderer.onSurfaceChanged(mGL, this.mWidth, this.mHeight);
    }

    public Bitmap getBitmap() {
        if (this.mRenderer == null) {
            Log.e(TAG, "getBitmap: Renderer was not set.");
            return null;
        } else if (!Thread.currentThread().getName().equals(this.mThreadOwner)) {
            Log.e(TAG, "getBitmap: This thread does not own the OpenGL context.");
            return null;
        } else {
            this.mRenderer.onDrawFrame(this.mGL);
            this.mRenderer.onDrawFrame(this.mGL);
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            convertToBitmap();

            return this.mBitmap;
        }
    }

    public void destroy() {
        this.mEGL.eglMakeCurrent(this.mEGLDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        this.mEGL.eglDestroySurface(this.mEGLDisplay, this.mEGLSurface);
        this.mEGL.eglDestroyContext(this.mEGLDisplay, this.mEGLContext);
        this.mEGL.eglTerminate(this.mEGLDisplay);
    }

    private EGLConfig chooseConfig() {
        int[] iArr = new int[1];
        int[] iArr2 = {12325, 0, 12326, 0, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12344};
        int[] iArr3 = iArr;
        this.mEGL.eglChooseConfig(this.mEGLDisplay, iArr2, null, 0, iArr3);
        int i = iArr[0];
        this.mEGLConfigs = new EGLConfig[i];
        this.mEGL.eglChooseConfig(this.mEGLDisplay, iArr2, this.mEGLConfigs, i, iArr3);
        return this.mEGLConfigs[0];
    }

    private void listConfig() {
        Log.i(TAG, "Config List {");
        for (EGLConfig eGLConfig : this.mEGLConfigs) {
            Log.i(TAG, "    <d,s,r,g,b,a> = <" + getConfigAttrib(eGLConfig, 12325) + "," + getConfigAttrib(eGLConfig, 12326) + "," + getConfigAttrib(eGLConfig, 12324) + "," + getConfigAttrib(eGLConfig, 12323) + "," + getConfigAttrib(eGLConfig, 12322) + "," + getConfigAttrib(eGLConfig, 12321) + ">");
        }
        Log.i(TAG, "}");
    }

    private int getConfigAttrib(EGLConfig eGLConfig, int i) {
        int[] iArr = new int[1];
        if (this.mEGL.eglGetConfigAttrib(this.mEGLDisplay, eGLConfig, i, iArr)) {
            return iArr[0];
        }
        return 0;
    }

    private void convertToBitmap() {
        IntBuffer allocate = IntBuffer.allocate(this.mWidth * this.mHeight);
        IntBuffer allocate2 = IntBuffer.allocate(this.mWidth * this.mHeight);
        this.mGL.glReadPixels(0, 0, this.mWidth, this.mHeight, 6408, 5121, allocate);
        int i = 0;
        int i2 = this.mHeight;
        int i4 = this.mWidth;
        while (true) {
            if (i < i2) {
                int i3 = 0;
                while (true) {
                    if (i3 >= i4) {
                        break;
                    }
                    allocate2.put((((this.mHeight - i) - 1) * i4) + i3, allocate.get((i4 * i) + i3));
                    i3++;
                }
                i++;
            } else {
                this.mBitmap = Bitmap.createBitmap(this.mWidth, mHeight, Bitmap.Config.ARGB_4444);
                this.mBitmap.copyPixelsFromBuffer(allocate2);
                return;
            }
        }

    }

    private Bitmap createBitmapFromGLSurface()
            throws OutOfMemoryError {
        int[] bitmapBuffer = new int[mWidth * mHeight];
        int[] bitmapSource = new int[mWidth * mHeight];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);

        try {
            mGL.glReadPixels(0, 0, mWidth, mHeight, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer);
            int offset1, offset2;
            for (int i = 0; i < mHeight; i++) {
                offset1 = i * mWidth;
                offset2 = (mHeight - i - 1) * mWidth;
                for (int j = 0; j < mWidth; j++) {
                    int texturePixel = bitmapBuffer[offset1 + j];
                    int blue = (texturePixel >> 16) & 0xff;
                    int red = (texturePixel << 16) & 0x00ff0000;
                    int pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (Exception e) {
            return null;
        }
        mBitmap = Bitmap.createBitmap(bitmapSource, mWidth, mHeight, Bitmap.Config.ARGB_8888);
        return mBitmap;
    }
}
