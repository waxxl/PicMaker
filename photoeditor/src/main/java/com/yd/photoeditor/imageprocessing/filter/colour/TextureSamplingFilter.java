package com.yd.photoeditor.imageprocessing.filter.colour;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class TextureSamplingFilter extends ImageFilter {
    public static final String THREE_X_THREE_TEXTURE_SAMPLING_VERTEX_SHADER = "precision highp float;\nattribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nuniform highp float texelWidth; \nuniform highp float texelHeight; \n\nvarying vec2 textureCoordinate;\nvarying vec2 leftTextureCoordinate;\nvarying vec2 rightTextureCoordinate;\n\nvarying vec2 topTextureCoordinate;\nvarying vec2 topLeftTextureCoordinate;\nvarying vec2 topRightTextureCoordinate;\n\nvarying vec2 bottomTextureCoordinate;\nvarying vec2 bottomLeftTextureCoordinate;\nvarying vec2 bottomRightTextureCoordinate;\n\nvoid main()\n{\n    gl_Position = position;\n\n    vec2 widthStep = vec2(texelWidth, 0.0);\n    vec2 heightStep = vec2(0.0, texelHeight);\n    vec2 widthHeightStep = vec2(texelWidth, texelHeight);\n    vec2 widthNegativeHeightStep = vec2(texelWidth, -texelHeight);\n\n    textureCoordinate = inputTextureCoordinate.xy;\n    leftTextureCoordinate = inputTextureCoordinate.xy - widthStep;\n    rightTextureCoordinate = inputTextureCoordinate.xy + widthStep;\n\n    topTextureCoordinate = inputTextureCoordinate.xy - heightStep;\n    topLeftTextureCoordinate = inputTextureCoordinate.xy - widthHeightStep;\n    topRightTextureCoordinate = inputTextureCoordinate.xy + widthNegativeHeightStep;\n\n    bottomTextureCoordinate = inputTextureCoordinate.xy + heightStep;\n    bottomLeftTextureCoordinate = inputTextureCoordinate.xy - widthNegativeHeightStep;\n    bottomRightTextureCoordinate = inputTextureCoordinate.xy + widthHeightStep;\n}";
    private boolean mHasOverriddenImageSizeFactor;
    private float mLineSize;
    private float mTexelHeight;
    private float mTexelWidth;
    private int mUniformTexelHeightLocation;
    private int mUniformTexelWidthLocation;

    public TextureSamplingFilter() {
        this(ImageFilter.NO_FILTER_VERTEX_SHADER);
    }

    public TextureSamplingFilter(String str) {
        super(THREE_X_THREE_TEXTURE_SAMPLING_VERTEX_SHADER, str);
        this.mHasOverriddenImageSizeFactor = false;
        this.mLineSize = 1.0f;
    }

    public void onInit() {
        super.onInit();
        this.mUniformTexelWidthLocation = GLES20.glGetUniformLocation(getProgram(), "texelWidth");
        this.mUniformTexelHeightLocation = GLES20.glGetUniformLocation(getProgram(), "texelHeight");
        if (this.mTexelWidth != 0.0f) {
            updateTexelValues();
        }
    }

    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        if (!this.mHasOverriddenImageSizeFactor) {
            setLineSize(this.mLineSize);
        }
    }

    public void setTexelWidth(float f) {
        this.mHasOverriddenImageSizeFactor = true;
        this.mTexelWidth = f;
        setFloat(this.mUniformTexelWidthLocation, f);
    }

    public void setTexelHeight(float f) {
        this.mHasOverriddenImageSizeFactor = true;
        this.mTexelHeight = f;
        setFloat(this.mUniformTexelHeightLocation, f);
    }

    public void setLineSize(float f) {
        this.mLineSize = f;
        this.mTexelWidth = f / ((float) getOutputWidth());
        this.mTexelHeight = f / ((float) getOutputHeight());
        updateTexelValues();
    }

    private void updateTexelValues() {
        setFloat(this.mUniformTexelWidthLocation, this.mTexelWidth);
        setFloat(this.mUniformTexelHeightLocation, this.mTexelHeight);
    }
}
