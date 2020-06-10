package com.yd.photoeditor.imageprocessing.filter.effect;

import com.yd.photoeditor.imageprocessing.filter.ImageRenderGroup;
import com.yd.photoeditor.imageprocessing.filter.processing.VWWWV;

public class F123456 extends ImageRenderGroup {
    VWWWV blurFilter = new VWWWV();
    F111 f111;

    public F123456() {
        addFilter(this.blurFilter);
        this.f111 = new F111();
        addFilter(this.f111);
        getFilters().add(this.blurFilter);
        setBlurSize(0.5f);
        setThreshold(0.2f);
        setQuantizationLevels(10.0f);
    }

    public void setTexelWidth(float f) {
        this.f111.setTexelWidth(f);
    }

    public void setTexelHeight(float f) {
        this.f111.setTexelHeight(f);
    }

    public void setBlurSize(float f) {
        this.blurFilter.setBlurSize(f);
    }

    public void setThreshold(float f) {
        this.f111.setThreshold(f);
    }

    public void setQuantizationLevels(float f) {
        this.f111.setQuantizationLevels(f);
    }
}
