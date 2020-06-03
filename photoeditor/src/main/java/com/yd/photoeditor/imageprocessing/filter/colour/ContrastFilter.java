package com.yd.photoeditor.imageprocessing.filter.colour;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class ContrastFilter extends ImageFilter {
    public static final String CONTRAST_FRAGMENT_SHADER = "precision highp float;\nvarying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform lowp float contrast;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     gl_FragColor = vec4(((textureColor.rgb - vec3(0.5)) * contrast + vec3(0.5)), textureColor.w);\n }";
    private float mContrast;
    private int mContrastLocation;

    public ContrastFilter() {
        this(1.2f);
    }

    public ContrastFilter(float f) {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, CONTRAST_FRAGMENT_SHADER);
        this.mContrast = f;
    }

    public void onInit() {
        super.onInit();
        this.mContrastLocation = GLES20.glGetUniformLocation(getProgram(), "contrast");
    }

    public void onInitialized() {
        super.onInitialized();
        setContrast(this.mContrast);
    }

    public void setContrast(float f) {
        this.mContrast = f;
        setFloat(this.mContrastLocation, this.mContrast);
    }
}
