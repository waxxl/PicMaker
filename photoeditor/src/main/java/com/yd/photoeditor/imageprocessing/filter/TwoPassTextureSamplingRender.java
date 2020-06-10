package com.yd.photoeditor.imageprocessing.filter;

import android.opengl.GLES20;

public class TwoPassTextureSamplingRender extends TwoPassRender {
    public float getHorizontalTexelOffsetRatio() {
        return 1.0f;
    }

    public float getVerticalTexelOffsetRatio() {
        return 1.0f;
    }

    public TwoPassTextureSamplingRender(String str, String str2, String str3, String str4) {
        super(str, str2, str3, str4);
    }

    public void onInit() {
        super.onInit();
        initTexelOffsets();
    }

    /* access modifiers changed from: protected */
    public void initTexelOffsets() {
        float horizontalTexelOffsetRatio = getHorizontalTexelOffsetRatio();
        ImageRender imageRender = this.mFilters.get(0);
        int glGetUniformLocation = GLES20.glGetUniformLocation(imageRender.getProgram(), "texelWidthOffset");
        int glGetUniformLocation2 = GLES20.glGetUniformLocation(imageRender.getProgram(), "texelHeightOffset");
        imageRender.setFloat(glGetUniformLocation, horizontalTexelOffsetRatio / ((float) this.mOutputWidth));
        imageRender.setFloat(glGetUniformLocation2, 0.0f);
        float verticalTexelOffsetRatio = getVerticalTexelOffsetRatio();
        ImageRender imageRender2 = this.mFilters.get(1);
        int glGetUniformLocation3 = GLES20.glGetUniformLocation(imageRender2.getProgram(), "texelWidthOffset");
        int glGetUniformLocation4 = GLES20.glGetUniformLocation(imageRender2.getProgram(), "texelHeightOffset");
        imageRender2.setFloat(glGetUniformLocation3, 0.0f);
        imageRender2.setFloat(glGetUniformLocation4, verticalTexelOffsetRatio / ((float) this.mOutputHeight));
    }

    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        initTexelOffsets();
    }
}
