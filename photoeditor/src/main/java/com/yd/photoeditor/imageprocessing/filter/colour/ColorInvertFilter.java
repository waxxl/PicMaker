package com.yd.photoeditor.imageprocessing.filter.colour;

import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class ColorInvertFilter extends ImageFilter {
    public static final String COLOR_INVERT_FRAGMENT_SHADER = "precision highp float;\nvarying highp vec2 textureCoordinate;\n\nuniform sampler2D inputImageTexture;\n\nvoid main()\n{\n    lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n    \n    gl_FragColor = vec4((1.0 - textureColor.rgb), textureColor.w);\n}";

    public ColorInvertFilter() {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, COLOR_INVERT_FRAGMENT_SHADER);
    }
}
