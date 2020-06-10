package com.yd.photoeditor.imageprocessing.filter.processing;

import android.opengl.GLES20;

import com.yd.photoeditor.imageprocessing.filter.ImageRender;

public class WWWV extends ImageRender {
    public static final String CROP_FILTER_VERTEX_SHADER = "precision highp float;\nattribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nuniform float left;\nuniform float top;\nuniform float right;\nuniform float bottom;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n \t float x = inputTextureCoordinate.x;\n \t float y = inputTextureCoordinate.y;\n    if(x < left) x = left;\n    if(x > right) x = right;\n    if(y < top) y = top;\n    if(y > bottom) y = bottom;\n    textureCoordinate = vec2(x,y);\n}";
    private float mBottom;
    private int mBottomLocation;
    private float mLeft;
    private int mLeftLocation;
    private float mRight;
    private int mRightLocation;
    private float mTop;
    private int mTopLocation;

    public WWWV(float f, float f2, float f3, float f4) {
        super(CROP_FILTER_VERTEX_SHADER, ImageRender.NO_FILTER_FRAGMENT_SHADER);
        this.mLeft = f;
        this.mTop = f2;
        this.mRight = f3;
        this.mBottom = f4;
    }

    public void onInit() {
        super.onInit();
        this.mLeftLocation = GLES20.glGetUniformLocation(getProgram(), "left");
        this.mTopLocation = GLES20.glGetUniformLocation(getProgram(), "top");
        this.mRightLocation = GLES20.glGetUniformLocation(getProgram(), "right");
        this.mBottomLocation = GLES20.glGetUniformLocation(getProgram(), "bottom");
    }

    public void onInitialized() {
        super.onInitialized();
        setLeft(this.mLeft);
        setTop(this.mTop);
        setRight(this.mRight);
        setBottom(this.mBottom);
    }

    public void setLeft(float f) {
        this.mLeft = f;
        setFloat(this.mLeftLocation, this.mLeft);
    }

    public void setRight(float f) {
        this.mRight = f;
        setFloat(this.mRightLocation, this.mRight);
    }

    public void setTop(float f) {
        this.mTop = f;
        setFloat(this.mTopLocation, this.mTop);
    }

    public void setBottom(float f) {
        this.mBottom = f;
        setFloat(this.mBottomLocation, this.mBottom);
    }
}
