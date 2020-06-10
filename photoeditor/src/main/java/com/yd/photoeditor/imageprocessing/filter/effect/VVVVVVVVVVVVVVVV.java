package com.yd.photoeditor.imageprocessing.filter.effect;

import com.yd.photoeditor.imageprocessing.filter.colour.P11;

public class VVVVVVVVVVVVVVVV extends P11 {
    private float mIntensity;

    public VVVVVVVVVVVVVVVV() {
        this(1.0f);
    }

    public VVVVVVVVVVVVVVVV(float f) {
        this.mIntensity = f;
    }

    public void onInit() {
        super.onInit();
        setIntensity(this.mIntensity);
    }

    public void setIntensity(float f) {
        this.mIntensity = f;
        float f2 = -f;
        setConvolutionKernel(new float[]{-2.0f * f, f2, 0.0f, f2, 1.0f, f, 0.0f, f, f * 2.0f});
    }

    public float getIntensity() {
        return this.mIntensity;
    }
}
