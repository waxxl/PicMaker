package com.yd.photoeditor.imageprocessing.filter.processing;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.TwoInputRender;

public class WWWWWWWW extends TwoInputRender {
    public static final String LINEAR_FOCUS_FRAGMENT = "precision highp float;\nvarying highp vec2 textureCoordinate;\nvarying highp vec2 textureCoordinate2;\nuniform sampler2D inputImageTexture;\nuniform sampler2D inputImageTexture2;\nuniform vec3 line;\nuniform float radius;\nuniform float exclude;\nfloat distanceFromLine(vec3 line, vec2 point){\n\tfloat v = abs(line.x * point.x + line.y * point.y + line.z);\n\tfloat s = sqrt(line.x * line.x + line.y * line.y);\n\treturn (v / s);\n}\nvoid main(){\n\tlowp vec4 sharpImageColor = texture2D(inputImageTexture, textureCoordinate);\n\tlowp vec4 blurredImageColor = texture2D(inputImageTexture2, textureCoordinate2);\n\tfloat d = distanceFromLine(line, gl_FragCoord.xy);\n   if(d < radius){\n\t    gl_FragColor = mix(sharpImageColor, blurredImageColor, smoothstep(radius - exclude, radius, d));\n\t }else\n\t\tgl_FragColor = blurredImageColor;\n}";
    private float mExclude = 0.1f;
    private int mGLExcludeLocation;
    private int mGLLineLocation;
    private int mGLRadiusLocation;
    private float[] mLine = {1.0f, 1.0f, 1.0f};
    private float mRadius = 1.0f;

    public WWWWWWWW() {
        super(LINEAR_FOCUS_FRAGMENT);
    }

    public WWWWWWWW(float[] fArr, float f, float f2) {
        super(LINEAR_FOCUS_FRAGMENT);
        this.mLine = fArr;
        this.mRadius = f;
        this.mExclude = f2;
    }

    public void onInit() {
        super.onInit();
        this.mGLLineLocation = GLES20.glGetUniformLocation(getProgram(), "line");
        this.mGLRadiusLocation = GLES20.glGetUniformLocation(getProgram(), "radius");
        this.mGLExcludeLocation = GLES20.glGetUniformLocation(getProgram(), "exclude");
    }

    public void onInitialized() {
        super.onInitialized();
        setLine(this.mLine);
        setRadius(this.mRadius);
        setExclude(this.mExclude);
    }

    public void setLine(float[] fArr) {
        this.mLine = fArr;
        setFloatVec3(this.mGLLineLocation, fArr);
    }

    public void setRadius(float f) {
        this.mRadius = f;
        setFloat(this.mGLRadiusLocation, f);
    }

    public void setExclude(float f) {
        this.mExclude = f;
        setFloat(this.mGLExcludeLocation, f);
    }

    public float[] getLine() {
        return this.mLine;
    }

    public float getRadius() {
        return this.mRadius;
    }

    public float getExclude() {
        return this.mExclude;
    }
}
