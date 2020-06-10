package com.yd.photoeditor.imageprocessing.filter.processing;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.TwoInputRender;

public class WW extends TwoInputRender {
    public static final String SELECTIVE_BLUR_FRAGMENT_SHADER = "precision highp float;\nvarying highp vec2 textureCoordinate;\nvarying highp vec2 textureCoordinate2;\nuniform sampler2D inputImageTexture;\nuniform sampler2D inputImageTexture2;\nuniform lowp float excludeCircleRadius;\nuniform lowp vec2 excludeCirclePoint;\nuniform lowp float excludeBlurSize;\nvoid main(){\nlowp vec4 sharpImageColor = texture2D(inputImageTexture, textureCoordinate);\nlowp vec4 blurredImageColor = texture2D(inputImageTexture2, textureCoordinate2);\nhighp float distanceFromCenter = distance(gl_FragCoord.xy, excludeCirclePoint);\n   if(distanceFromCenter < excludeCircleRadius){\n\t\tgl_FragColor = mix(sharpImageColor, blurredImageColor, smoothstep(excludeCircleRadius - excludeBlurSize, excludeCircleRadius, distanceFromCenter));\n\t  }else\n    gl_FragColor = blurredImageColor;\n}";
    private float mBlurSize;
    private int mBlurSizeLocation;
    private float[] mCenterPoint;
    private int mCenterPointLocation;
    private float mRadius;
    private int mRadiusLocation;

    public WW(float[] fArr, float f, float f2) {
        super(SELECTIVE_BLUR_FRAGMENT_SHADER);
        this.mRadius = f;
        this.mCenterPoint = fArr;
        this.mBlurSize = f2;
    }

    public WW() {
        super(SELECTIVE_BLUR_FRAGMENT_SHADER);
        this.mRadius = 0.1f;
        this.mCenterPoint = new float[]{0.5f, 0.5f};
        this.mBlurSize = 0.1f;
    }

    public void onInit() {
        super.onInit();
        this.mRadiusLocation = GLES20.glGetUniformLocation(getProgram(), "excludeCircleRadius");
        this.mCenterPointLocation = GLES20.glGetUniformLocation(getProgram(), "excludeCirclePoint");
        this.mBlurSizeLocation = GLES20.glGetUniformLocation(getProgram(), "excludeBlurSize");
    }

    public void onInitialized() {
        super.onInitialized();
        setRadius(this.mRadius);
        setCenterPoint(this.mCenterPoint);
        setBlurSize(this.mBlurSize);
    }

    public void setRadius(float f) {
        this.mRadius = f;
        setFloat(this.mRadiusLocation, this.mRadius);
    }

    public void setCenterPoint(float[] fArr) {
        this.mCenterPoint = fArr;
        setFloatVec2(this.mCenterPointLocation, this.mCenterPoint);
    }

    public void setBlurSize(float f) {
        this.mBlurSize = f;
        setFloat(this.mBlurSizeLocation, this.mBlurSize);
    }

    public float getRadius() {
        return this.mRadius;
    }

    public float getBlurSize() {
        return this.mBlurSize;
    }

    public float[] getCenterPoint() {
        return this.mCenterPoint;
    }
}
