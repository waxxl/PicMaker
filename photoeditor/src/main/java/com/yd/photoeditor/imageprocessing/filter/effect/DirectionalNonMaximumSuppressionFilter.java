package com.yd.photoeditor.imageprocessing.filter.effect;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class DirectionalNonMaximumSuppressionFilter extends ImageFilter {
    public static final String DIRECTIONAL_NON_MAXINUM_SUPPRESSION_FRAGMENT_SHADER = "precision mediump float;\nvarying highp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform highp float texelWidth;\nuniform highp float texelHeight;\nuniform mediump float upperThreshold;\nuniform mediump float lowerThreshold;\nvoid main()\n{\n\tvec3 currentGradientAndDirection = texture2D(inputImageTexture, textureCoordinate).rgb;\n\tvec2 gradientDirection = ((currentGradientAndDirection.gb * 2.0) - 1.0) * vec2(texelWidth, texelHeight);\n\tfloat firstSampledGradientMagnitude = texture2D(inputImageTexture, textureCoordinate + gradientDirection).r;\n\tfloat secondSampledGradientMagnitude = texture2D(inputImageTexture, textureCoordinate - gradientDirection).r;\n\tfloat multiplier = step(firstSampledGradientMagnitude, currentGradientAndDirection.r);\n\tmultiplier = multiplier * step(secondSampledGradientMagnitude, currentGradientAndDirection.r);\n\tfloat thresholdCompliance = smoothstep(lowerThreshold, upperThreshold, currentGradientAndDirection.r);\n\tmultiplier = multiplier * thresholdCompliance;\n\tgl_FragColor = vec4(multiplier, multiplier, multiplier, 1.0);\n}";
    private float mLowerThreshold;
    private int mLowerThresholdLocation;
    private float mTexelHeight;
    private int mTexelHeightLocation;
    private float mTexelWidth;
    private int mTexelWidthLocation;
    private float mUpperThreshold;
    private int mUpperThresholdLocation;

    public DirectionalNonMaximumSuppressionFilter() {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, DIRECTIONAL_NON_MAXINUM_SUPPRESSION_FRAGMENT_SHADER);
    }

    public void onInit() {
        super.onInit();
        this.mTexelWidthLocation = GLES20.glGetUniformLocation(getProgram(), "texelWidth");
        this.mTexelHeightLocation = GLES20.glGetUniformLocation(getProgram(), "texelHeight");
        this.mUpperThresholdLocation = GLES20.glGetUniformLocation(getProgram(), "upperThreshold");
        this.mLowerThresholdLocation = GLES20.glGetUniformLocation(getProgram(), "lowerThreshold");
    }

    public void onInitialized() {
        super.onInitialized();
        setTexelWidth(this.mTexelWidth);
        setTexelHeight(this.mTexelHeight);
        setUpperThreshold(this.mUpperThreshold);
        setLowerThreshold(this.mLowerThreshold);
    }

    public void setTexelWidth(float f) {
        this.mTexelWidth = f;
        setFloat(this.mTexelWidthLocation, this.mTexelWidth);
    }

    public void setTexelHeight(float f) {
        this.mTexelHeight = f;
        setFloat(this.mTexelHeightLocation, this.mTexelHeight);
    }

    public void setUpperThreshold(float f) {
        this.mUpperThreshold = f;
        setFloat(this.mUpperThresholdLocation, this.mUpperThreshold);
    }

    public void setLowerThreshold(float f) {
        this.mLowerThreshold = f;
        setFloat(this.mLowerThresholdLocation, this.mLowerThreshold);
    }
}
