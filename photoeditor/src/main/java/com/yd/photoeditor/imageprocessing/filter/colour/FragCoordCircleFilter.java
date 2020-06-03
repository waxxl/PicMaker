package com.yd.photoeditor.imageprocessing.filter.colour;

import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class FragCoordCircleFilter extends ImageFilter {
    public static final String CIRCLE_FILTER_FRAGMENT_SHADER = "precision highp float;\nvarying highp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     vec2 pos = mod(gl_FragCoord.xy, vec2(50.0)) - vec2(25.0);\n     float dist_squared = dot(pos, pos);\n    gl_FragColor = mix(vec4(.90, .90, .90, 1.0), vec4(.20, .20, .40, 1.0),step(400.0, dist_squared));\n}";

    public FragCoordCircleFilter() {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, CIRCLE_FILTER_FRAGMENT_SHADER);
    }
}
