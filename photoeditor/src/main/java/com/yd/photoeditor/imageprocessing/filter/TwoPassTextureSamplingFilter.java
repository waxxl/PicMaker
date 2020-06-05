package com.yd.photoeditor.imageprocessing.filter;

import android.opengl.GLES20;

public class TwoPassTextureSamplingFilter extends TwoPassFilter {
    public float getHorizontalTexelOffsetRatio() {
        return 1.0f;
    }

    public float getVerticalTexelOffsetRatio() {
        return 1.0f;
    }

    public TwoPassTextureSamplingFilter(String str, String str2, String str3, String str4) {
        super(str, str2, str3, str4);
    }

    public void onInit() {
        super.onInit();
        initTexelOffsets();
    }

    /* access modifiers changed from: protected */
    public void initTexelOffsets() {
        float horizontalTexelOffsetRatio = getHorizontalTexelOffsetRatio();
        ImageFilter imageFilter = this.mFilters.get(0);
        int glGetUniformLocation = GLES20.glGetUniformLocation(imageFilter.getProgram(), "texelWidthOffset");
        int glGetUniformLocation2 = GLES20.glGetUniformLocation(imageFilter.getProgram(), "texelHeightOffset");
        imageFilter.setFloat(glGetUniformLocation, horizontalTexelOffsetRatio / ((float) this.mOutputWidth));
        imageFilter.setFloat(glGetUniformLocation2, 0.0f);
        float verticalTexelOffsetRatio = getVerticalTexelOffsetRatio();
        ImageFilter imageFilter2 = this.mFilters.get(1);
        int glGetUniformLocation3 = GLES20.glGetUniformLocation(imageFilter2.getProgram(), "texelWidthOffset");
        int glGetUniformLocation4 = GLES20.glGetUniformLocation(imageFilter2.getProgram(), "texelHeightOffset");
        imageFilter2.setFloat(glGetUniformLocation3, 0.0f);
        imageFilter2.setFloat(glGetUniformLocation4, verticalTexelOffsetRatio / ((float) this.mOutputHeight));
    }

    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        initTexelOffsets();
    }
}
