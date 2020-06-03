package com.yd.photoeditor.imageprocessing.filter.colour;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class ChannelLookupFilter extends ImageFilter {
    public static final String CHANNEL_LOOKUP_FRAGMENT_SHADER = "precision highp float;\nvarying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform vec4 red;\n uniform vec4 green;\n uniform vec4 blue;\n uniform vec4 alpha;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     float r = red.x * textureColor.r * textureColor.r * textureColor.r + red.y * textureColor.r * textureColor.r + red.z * textureColor.r + red.w;\n     if(r < 0.0) r = 0.0;\n     if(r > 1.0) r = 1.0;\n     float g = green.x * textureColor.g * textureColor.g * textureColor.g + green.y * textureColor.g * textureColor.g + green.z * textureColor.g + green.w;\n     if(g < 0.0) g = 0.0;\n     if(g > 1.0) g = 1.0;\n     float b = blue.x * textureColor.b * textureColor.b * textureColor.b + blue.y * textureColor.b * textureColor.b + blue.z * textureColor.b + blue.w;\n     if(b < 0.0) b = 0.0;\n     if(b > 1.0) b = 1.0;\n     float a = alpha.x * textureColor.a * textureColor.a * textureColor.a + alpha.y * textureColor.a * textureColor.a + alpha.z * textureColor.a + alpha.w;\n     if(a < 0.0) a = 0.0;\n     if(a > 1.0) a = 1.0;\n     gl_FragColor = vec4(r, g, b, a);\n }";
    private float[] mAlpha;
    private int mAlphaLocation;
    private float[] mBlue;
    private int mBlueLocation;
    private float[] mGreen;
    private int mGreenLocation;
    private float[] mRed;
    private int mRedLocation;

    public ChannelLookupFilter(float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4) {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, CHANNEL_LOOKUP_FRAGMENT_SHADER);
        this.mRed = fArr;
        this.mGreen = fArr2;
        this.mBlue = fArr3;
        this.mAlpha = fArr4;
    }

    public void onInit() {
        super.onInit();
        this.mRedLocation = GLES20.glGetUniformLocation(getProgram(), "red");
        this.mGreenLocation = GLES20.glGetUniformLocation(getProgram(), "green");
        this.mBlueLocation = GLES20.glGetUniformLocation(getProgram(), "blue");
        this.mAlphaLocation = GLES20.glGetUniformLocation(getProgram(), "alpha");
    }

    public void onInitialized() {
        super.onInitialized();
        setRed(this.mRed);
        setGreen(this.mGreen);
        setBlue(this.mBlue);
        setAlpha(this.mAlpha);
    }

    public void setRed(float[] fArr) {
        this.mRed = fArr;
        setFloatVec4(this.mRedLocation, this.mRed);
    }

    public void setGreen(float[] fArr) {
        this.mGreen = fArr;
        setFloatVec4(this.mGreenLocation, this.mGreen);
    }

    public void setBlue(float[] fArr) {
        this.mBlue = fArr;
        setFloatVec4(this.mBlueLocation, this.mBlue);
    }

    public void setAlpha(float[] fArr) {
        this.mAlpha = fArr;
        setFloatVec4(this.mAlphaLocation, this.mAlpha);
    }
}
