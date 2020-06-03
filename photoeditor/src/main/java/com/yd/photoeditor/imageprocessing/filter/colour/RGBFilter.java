package com.yd.photoeditor.imageprocessing.filter.colour;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class RGBFilter extends ImageFilter {
    public static final String RGB_FRAGMENT_SHADER = "precision highp float;\n  varying highp vec2 textureCoordinate;\n  \n  uniform sampler2D inputImageTexture;\n  uniform highp float red;\n  uniform highp float green;\n  uniform highp float blue;\n  \n  void main()\n  {\n      highp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n      \n      gl_FragColor = vec4(textureColor.r * red, textureColor.g * green, textureColor.b * blue, 1.0);\n  }\n";
    private float mBlue;
    private int mBlueLocation;
    private float mGreen;
    private int mGreenLocation;
    private boolean mIsInitialized;
    private float mRed;
    private int mRedLocation;

    public RGBFilter() {
        this(1.0f, 1.0f, 1.0f);
    }

    public RGBFilter(float f, float f2, float f3) {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, RGB_FRAGMENT_SHADER);
        this.mIsInitialized = false;
        this.mRed = f;
        this.mGreen = f2;
        this.mBlue = f3;
    }

    public void onInit() {
        super.onInit();
        this.mRedLocation = GLES20.glGetUniformLocation(getProgram(), "red");
        this.mGreenLocation = GLES20.glGetUniformLocation(getProgram(), "green");
        this.mBlueLocation = GLES20.glGetUniformLocation(getProgram(), "blue");
        this.mIsInitialized = true;
        setRed(this.mRed);
        setGreen(this.mGreen);
        setBlue(this.mBlue);
    }

    public void setRed(float f) {
        this.mRed = f;
        if (this.mIsInitialized) {
            setFloat(this.mRedLocation, this.mRed);
        }
    }

    public void setGreen(float f) {
        this.mGreen = f;
        if (this.mIsInitialized) {
            setFloat(this.mGreenLocation, this.mGreen);
        }
    }

    public void setBlue(float f) {
        this.mBlue = f;
        if (this.mIsInitialized) {
            setFloat(this.mBlueLocation, this.mBlue);
        }
    }
}
