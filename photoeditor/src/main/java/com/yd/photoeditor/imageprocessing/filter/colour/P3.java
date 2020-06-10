package com.yd.photoeditor.imageprocessing.filter.colour;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageRender;

public class P3 extends ImageRender {
    public static final String AAAA = "precision highp float;\nvarying highp vec2 textureCoordinate;\n\nuniform sampler2D inputImageTexture;\n\nuniform lowp mat4 colorMatrix;\nuniform lowp float intensity;\n\nvoid main()\n{\n    lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n    lowp vec4 outputColor = textureColor * colorMatrix;\n    \n    gl_FragColor = (intensity * outputColor) + ((1.0 - intensity) * textureColor);\n}";
    private float[] mColorMatrix;
    private int mColorMatrixLocation;
    private float mIntensity;
    private int mIntensityLocation;

    public P3() {
        this(1.0f, new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f});
    }

    public P3(float f, float[] fArr) {
        super(ImageRender.NO_FILTER_VERTEX_SHADER, AAAA);
        this.mIntensity = f;
        this.mColorMatrix = fArr;
    }

    public void onInit() {
        super.onInit();
        this.mColorMatrixLocation = GLES20.glGetUniformLocation(getProgram(), "colorMatrix");
        this.mIntensityLocation = GLES20.glGetUniformLocation(getProgram(), "intensity");
    }

    public void onInitialized() {
        super.onInitialized();
        setIntensity(this.mIntensity);
        setColorMatrix(this.mColorMatrix);
    }

    public void setIntensity(float f) {
        this.mIntensity = f;
        setFloat(this.mIntensityLocation, f);
    }

    public void setColorMatrix(float[] fArr) {
        this.mColorMatrix = fArr;
        setUniformMatrix4f(this.mColorMatrixLocation, fArr);
    }
}
