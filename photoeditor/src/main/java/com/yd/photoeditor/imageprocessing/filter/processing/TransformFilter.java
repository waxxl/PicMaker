package com.yd.photoeditor.imageprocessing.filter.processing;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class TransformFilter extends ImageFilter {
    public static final String TRANSFORM_FILTER_VERTEX_SHADER = "precision highp float;\nuniform vec2 translate;\nuniform vec2 scale;\nattribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = vec4(position.xy * scale + translate, position.zw);\n    textureCoordinate = inputTextureCoordinate.xy;\n}";
    private float[] mScale = {1.0f, 1.0f};
    private int mScaleLocation;
    private float[] mTranslate = {0.0f, 0.0f};
    private int mTranslateLocation;

    public TransformFilter() {
        super(TRANSFORM_FILTER_VERTEX_SHADER, ImageFilter.NO_FILTER_FRAGMENT_SHADER);
    }

    public void onInit() {
        super.onInit();
        this.mTranslateLocation = GLES20.glGetUniformLocation(getProgram(), "translate");
        this.mScaleLocation = GLES20.glGetUniformLocation(getProgram(), "scale");
    }

    public void onInitialized() {
        super.onInitialized();
        setTranslate(this.mTranslate);
        setScale(this.mScale);
    }

    public void setTranslate(float[] fArr) {
        this.mTranslate = fArr;
        setFloatVec2(this.mTranslateLocation, this.mTranslate);
    }

    public void setScale(float[] fArr) {
        this.mScale = fArr;
        setFloatVec2(this.mScaleLocation, this.mScale);
    }

    public float[] getTranslate() {
        return this.mTranslate;
    }

    public float[] getScale() {
        return this.mScale;
    }

    public TransformFilter copyFilter() {
        TransformFilter transformFilter = new TransformFilter();
        transformFilter.setTranslate(this.mTranslate);
        transformFilter.setScale(this.mScale);
        return transformFilter;
    }
}
