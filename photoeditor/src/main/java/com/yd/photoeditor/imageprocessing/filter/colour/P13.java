package com.yd.photoeditor.imageprocessing.filter.colour;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageRender;

public class P13 extends ImageRender {
    public static final String KKKKKKKKKK = " precision lowp float;\n  \n  varying highp vec2 textureCoordinate;\n  \n  uniform sampler2D inputImageTexture;\n  uniform float intensity;\n  uniform vec3 filterColor;\n  \n  const mediump vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\n  \n  void main()\n  {\n \t//desat, then apply overlay blend\n \tlowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n \tfloat luminance = dot(textureColor.rgb, luminanceWeighting);\n \t\n \tlowp vec4 desat = vec4(vec3(luminance), 1.0);\n \t\n \t//overlay\n \tlowp vec4 outputColor = vec4(\n                                  (desat.r < 0.5 ? (2.0 * desat.r * filterColor.r) : (1.0 - 2.0 * (1.0 - desat.r) * (1.0 - filterColor.r))),\n                                  (desat.g < 0.5 ? (2.0 * desat.g * filterColor.g) : (1.0 - 2.0 * (1.0 - desat.g) * (1.0 - filterColor.g))),\n                                  (desat.b < 0.5 ? (2.0 * desat.b * filterColor.b) : (1.0 - 2.0 * (1.0 - desat.b) * (1.0 - filterColor.b))),\n                                  1.0\n                                  );\n \t\n \t//which is better, or are they equal?\n \tgl_FragColor = vec4( mix(textureColor.rgb, outputColor.rgb, intensity), textureColor.a);\n  }";
    private float[] mColor;
    private int mFilterColorLocation;
    private float mIntensity;
    private int mIntensityLocation;

    public P13() {
        this(1.0f, new float[]{0.6f, 0.45f, 0.3f, 1.0f});
    }

    public P13(float f, float[] fArr) {
        super(ImageRender.NO_FILTER_VERTEX_SHADER, KKKKKKKKKK);
        this.mIntensity = f;
        this.mColor = fArr;
    }

    public void onInit() {
        super.onInit();
        this.mIntensityLocation = GLES20.glGetUniformLocation(getProgram(), "intensity");
        this.mFilterColorLocation = GLES20.glGetUniformLocation(getProgram(), "filterColor");
    }

    public void onInitialized() {
        super.onInitialized();
        setIntensity(1.0f);
        setColor(new float[]{0.6f, 0.45f, 0.3f, 1.0f});
    }

    public void setIntensity(float f) {
        this.mIntensity = f;
        setFloat(this.mIntensityLocation, this.mIntensity);
    }

    public void setColor(float[] fArr) {
        this.mColor = fArr;
        float[] fArr2 = this.mColor;
        setColorRed(fArr2[0], fArr2[1], fArr2[2]);
    }

    public void setColorRed(float f, float f2, float f3) {
        setFloatVec3(this.mFilterColorLocation, new float[]{f, f2, f3});
    }
}
