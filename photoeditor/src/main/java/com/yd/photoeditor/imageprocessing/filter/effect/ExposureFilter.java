package com.yd.photoeditor.imageprocessing.filter.effect;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class ExposureFilter extends ImageFilter {
    public static final String EXPOSURE_FRAGMENT_SHADER = "precision highp float;\n varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform highp float exposure;\n \n void main()\n {\n     highp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     gl_FragColor = vec4(textureColor.rgb * pow(2.0, exposure), textureColor.w);\n } ";
    private float mExposure;
    private int mExposureLocation;

    public ExposureFilter() {
        this(1.0f);
    }

    public ExposureFilter(float f) {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, EXPOSURE_FRAGMENT_SHADER);
        this.mExposure = f;
    }

    public void onInit() {
        super.onInit();
        this.mExposureLocation = GLES20.glGetUniformLocation(getProgram(), "exposure");
    }

    public void onInitialized() {
        super.onInitialized();
        setExposure(this.mExposure);
    }

    public void setExposure(float f) {
        this.mExposure = f;
        setFloat(this.mExposureLocation, this.mExposure);
    }
}
