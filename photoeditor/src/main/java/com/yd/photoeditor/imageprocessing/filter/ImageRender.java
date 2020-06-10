package com.yd.photoeditor.imageprocessing.filter;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.util.OpenGlUtils;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.Scanner;

public class ImageRender {
    public static final String NO_FILTER_FRAGMENT_SHADER = "precision highp float;\nvarying highp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}";
    public static final String NO_FILTER_VERTEX_SHADER = "precision highp float;\nattribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}";
    private final String mFragmentShader;
    protected int mGLAttribPosition;
    protected int mGLAttribTextureCoordinate;
    protected int mGLProgId;
    protected int mGLUniformTexture;
    private boolean mIsInitialized;
    protected int mOutputHeight;
    protected int mOutputWidth;
    private final LinkedList<Runnable> mRunOnDraw;
    private final String mVertexShader;

    public void onDestroy() {
    }

    /* access modifiers changed from: protected */
    public void onDrawArraysPre() {
    }

    public void onInitialized() {
    }

    public ImageRender() {
        this(NO_FILTER_VERTEX_SHADER, NO_FILTER_FRAGMENT_SHADER);
    }

    public ImageRender(String str, String str2) {
        this.mRunOnDraw = new LinkedList<>();
        this.mVertexShader = str;
        this.mFragmentShader = str2;
    }

    public final void init() {
        onInit();
        this.mIsInitialized = true;
        onInitialized();
    }

    public void onInit() {
        mGLProgId = OpenGlUtils.loadProgram(mVertexShader, mFragmentShader);
        mGLAttribPosition = GLES20.glGetAttribLocation(mGLProgId, "position");
        mGLUniformTexture = GLES20.glGetUniformLocation(mGLProgId, "inputImageTexture");
        mGLAttribTextureCoordinate = GLES20.glGetAttribLocation(mGLProgId, "inputTextureCoordinate");
        mIsInitialized = true;
    }

    public final void destroy() {
        this.mIsInitialized = false;
        GLES20.glDeleteProgram(mGLProgId);
        onDestroy();
    }

    public void onOutputSizeChanged(int i, int i2) {
        mOutputWidth = i;
        mOutputHeight = i2;
    }

    private Object object = new Object();
    public void onDraw(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {

            GLES20.glUseProgram(this.mGLProgId);
            runPendingOnDrawTasks();
            if (mIsInitialized) {
                floatBuffer.position(0);
                GLES20.glVertexAttribPointer(mGLAttribPosition, 2, 5126, false, 0, floatBuffer);
                GLES20.glEnableVertexAttribArray(mGLAttribPosition);
                floatBuffer2.position(0);
                GLES20.glVertexAttribPointer(mGLAttribTextureCoordinate, 2, 5126, false, 0, floatBuffer2);
                GLES20.glEnableVertexAttribArray(mGLAttribTextureCoordinate);
                if (i != -1) {
                    GLES20.glActiveTexture(33984);
                    GLES20.glBindTexture(3553, i);
                    GLES20.glUniform1i(this.mGLUniformTexture, 0);
                }
                onDrawArraysPre();
                GLES20.glDrawArrays(5, 0, 4);
                GLES20.glDisableVertexAttribArray(mGLAttribPosition);
                GLES20.glDisableVertexAttribArray(mGLAttribTextureCoordinate);
                GLES20.glBindTexture(3553, 0);
            }

    }

    /* access modifiers changed from: protected */
    public void runPendingOnDrawTasks() {
        while (!this.mRunOnDraw.isEmpty()) {
            this.mRunOnDraw.removeFirst().run();
        }
    }

    public boolean isInitialized() {
        return this.mIsInitialized;
    }

    public int getOutputWidth() {
        return this.mOutputWidth;
    }

    public int getOutputHeight() {
        return this.mOutputHeight;
    }

    public int getProgram() {
        return this.mGLProgId;
    }

    public int getAttribPosition() {
        return this.mGLAttribPosition;
    }

    public int getAttribTextureCoordinate() {
        return this.mGLAttribTextureCoordinate;
    }

    public int getUniformTexture() {
        return this.mGLUniformTexture;
    }

    /* access modifiers changed from: protected */
    public void setInteger(final int i, final int i2) {
        runOnDraw(new Runnable() {
            public void run() {
                GLES20.glUniform1i(i, i2);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void setFloat(final int i, final float f) {
        runOnDraw(new Runnable() {
            public void run() {
                GLES20.glUniform1f(i, f);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void setFloatVec2(final int i, final float[] fArr) {
        if (fArr != null) {
            runOnDraw(new Runnable() {
                public void run() {
                    GLES20.glUniform2fv(i, 1, FloatBuffer.wrap(fArr));
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void setFloatVec3(final int i, final float[] fArr) {
        if (fArr != null) {
            runOnDraw(new Runnable() {
                public void run() {
                    GLES20.glUniform3fv(i, 1, FloatBuffer.wrap(fArr));
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void setFloatVec4(final int i, final float[] fArr) {
        if (fArr != null) {
            runOnDraw(new Runnable() {
                public void run() {
                    GLES20.glUniform4fv(i, 1, FloatBuffer.wrap(fArr));
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void setFloatVec4Array(final int i, final float[] fArr) {
        if (fArr != null) {
            runOnDraw(new Runnable() {
                public void run() {
                    GLES20.glUniform4fv(i, fArr.length / 4, FloatBuffer.wrap(fArr));
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void setFloatArray(final int i, final float[] fArr) {
        if (fArr != null) {
            runOnDraw(new Runnable() {
                public void run() {
                    GLES20.glUniform1fv(i, fArr.length, FloatBuffer.wrap(fArr));
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void setPoint(final int i, final PointF pointF) {
        runOnDraw(new Runnable() {
            public void run() {
                GLES20.glUniform2fv(i, 1, new float[]{pointF.x, pointF.y}, 0);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void setUniformMatrix3f(final int i, final float[] fArr) {
        runOnDraw(new Runnable() {
            public void run() {
                GLES20.glUniformMatrix3fv(i, 1, false, fArr, 0);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void setUniformMatrix4f(final int i, final float[] fArr) {
        runOnDraw(new Runnable() {
            public void run() {
                GLES20.glUniformMatrix4fv(i, 1, false, fArr, 0);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void runOnDraw(Runnable runnable) {
        synchronized (this.mRunOnDraw) {
            this.mRunOnDraw.addLast(runnable);
        }
    }

    public static String loadShader(String str, Context context) {
        try {
            InputStream open = context.getAssets().open(str);
            String convertStreamToString = convertStreamToString(open);
            open.close();
            return convertStreamToString;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convertStreamToString(InputStream inputStream) {
        Scanner useDelimiter = new Scanner(inputStream).useDelimiter("\\A");
        return useDelimiter.hasNext() ? useDelimiter.next() : "";
    }
}
