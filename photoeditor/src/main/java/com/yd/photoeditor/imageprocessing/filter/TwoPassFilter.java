package com.yd.photoeditor.imageprocessing.filter;

import java.util.List;

public class TwoPassFilter extends ImageFilterGroup {
    public TwoPassFilter(String str, String str2, String str3, String str4) {
        super((List<ImageFilter>) null);
        addFilter(new ImageFilter(str, str2));
        addFilter(new ImageFilter(str3, str4));
    }
}
