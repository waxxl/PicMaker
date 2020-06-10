package com.yd.photoeditor.imageprocessing.filter.colour;

import com.yd.photoeditor.imageprocessing.filter.ImageRender;

public class P4 extends ImageRender {
    public static final String AAAAA = "precision highp float;\n\nvarying vec2 textureCoordinate;\n\nuniform sampler2D inputImageTexture;\n\nconst highp vec3 W = vec3(0.2125, 0.7154, 0.0721);\n\nvoid main()\n{\n  lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n  float luminance = dot(textureColor.rgb, W);\n\n  gl_FragColor = vec4(vec3(luminance), textureColor.a);\n}";

    public P4() {
        super(ImageRender.NO_FILTER_VERTEX_SHADER, AAAAA);
    }
}
