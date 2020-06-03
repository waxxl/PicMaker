package com.yd.photoeditor.imageprocessing.filter.effect;

import com.yd.photoeditor.imageprocessing.filter.ImageFilterGroup;
import com.yd.photoeditor.imageprocessing.filter.processing.GaussianBlurFilter;

public class SmoothToonFilter extends ImageFilterGroup {
    GaussianBlurFilter blurFilter = new GaussianBlurFilter();
    ToonFilter toonFilter;

    public SmoothToonFilter() {
        addFilter(this.blurFilter);
        this.toonFilter = new ToonFilter();
        addFilter(this.toonFilter);
        getFilters().add(this.blurFilter);
        setBlurSize(0.5f);
        setThreshold(0.2f);
        setQuantizationLevels(10.0f);
    }

    public void setTexelWidth(float f) {
        this.toonFilter.setTexelWidth(f);
    }

    public void setTexelHeight(float f) {
        this.toonFilter.setTexelHeight(f);
    }

    public void setBlurSize(float f) {
        this.blurFilter.setBlurSize(f);
    }

    public void setThreshold(float f) {
        this.toonFilter.setThreshold(f);
    }

    public void setQuantizationLevels(float f) {
        this.toonFilter.setQuantizationLevels(f);
    }
}
