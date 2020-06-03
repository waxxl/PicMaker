package com.yd.photoeditor.imageprocessing.filter.processing;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class RotationFilter extends ImageFilter {
    public static final String CIRCLE_FILTER_FRAGMENT_SHADER = "precision highp float;\nuniform highp float angle;\nvarying highp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     mat2 rotation = mat2( cos(angle), sin(angle),-sin(angle), cos(angle));\n     vec2 pos = rotation *(textureCoordinate - vec2(0.5, 0.5)) + vec2(0.5, 0.5);\n     if(pos.x < 0.0 || pos.x > 1.0 || pos.y < 0.0 || pos.y > 1.0) gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);\n else \n     gl_FragColor = texture2D(inputImageTexture, pos);\n}";
    private float mAngle;
    private int mAngleLocation;

    public RotationFilter(float f) {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, CIRCLE_FILTER_FRAGMENT_SHADER);
        this.mAngle = f;
    }

    public void onInit() {
        super.onInit();
        this.mAngleLocation = GLES20.glGetUniformLocation(getProgram(), "angle");
    }

    public void onInitialized() {
        super.onInitialized();
        setAngle(this.mAngle);
    }

    public void setAngle(float f) {
        this.mAngle = f;
        setFloat(this.mAngleLocation, this.mAngle);
    }
}
