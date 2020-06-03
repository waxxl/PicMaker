package com.yd.photoeditor.imageprocessing.filter.colour;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class GammaFilter extends ImageFilter {
    public static final String GAMMA_FRAGMENT_SHADER = "precision highp float;\nvarying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform lowp float gamma;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     gl_FragColor = vec4(pow(textureColor.rgb, vec3(gamma)), textureColor.w);\n }";
    private float mGamma;
    private int mGammaLocation;

    public GammaFilter() {
        this(1.2f);
    }

    public GammaFilter(float f) {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, GAMMA_FRAGMENT_SHADER);
        this.mGamma = f;
    }

    public void onInit() {
        super.onInit();
        this.mGammaLocation = GLES20.glGetUniformLocation(getProgram(), "gamma");
    }

    public void onInitialized() {
        super.onInitialized();
        setGamma(this.mGamma);
    }

    public void setGamma(float f) {
        this.mGamma = f;
        setFloat(this.mGammaLocation, this.mGamma);
    }
}
