package com.yd.photoeditor.imageprocessing.filter.blend;

import android.opengl.GLES20;

import com.yd.photoeditor.imageprocessing.filter.TwoInputRender;

public class P2 extends TwoInputRender {
    private float mMix;
    private int mMixLocation;

    public P2(String str) {
        this(str, 0.5f);
    }

    public P2(String str, float f) {
        super(str);
        this.mMix = f;
    }

    public void onInit() {
        super.onInit();
        this.mMixLocation = GLES20.glGetUniformLocation(getProgram(), "mixturePercent");
    }

    public void onInitialized() {
        super.onInitialized();
        setMix(this.mMix);
    }

    public void setMix(float f) {
        this.mMix = f;
        setFloat(this.mMixLocation, this.mMix);
    }
}
