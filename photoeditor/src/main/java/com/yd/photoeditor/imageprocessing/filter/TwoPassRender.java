package com.yd.photoeditor.imageprocessing.filter;

public class TwoPassRender extends ImageRenderGroup {
    public TwoPassRender(String str, String str2, String str3, String str4) {
        super(null);
        addFilter(new ImageRender(str, str2));
        addFilter(new ImageRender(str3, str4));
    }
}
