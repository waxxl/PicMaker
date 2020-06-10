package com.yd.photoeditor.imageprocessing.filter.processing;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageRender;

public class WWWWVW extends ImageRender {
    private static final String FAST_BLUR_FRAGMENT_SHADER = "precision mediump float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform float texelWidth;\nuniform float texelHeight;\nvoid main(){\n   vec2 firstOffset = vec2(1.3846153846 * texelWidth, 1.3846153846 * texelHeight);\n   vec2 secondOffset = vec2(3.2307692308 * texelWidth, 3.2307692308 * texelHeight);\n   vec3 sum = vec3(0,0,0);\n   vec4 color = texture2D(inputImageTexture, textureCoordinate);\n   sum += color.rgb * 0.2270270270;\n   sum += texture2D(inputImageTexture, textureCoordinate - firstOffset).rgb * 0.3162162162;\n   sum += texture2D(inputImageTexture, textureCoordinate + firstOffset).rgb * 0.3162162162;\n   sum += texture2D(inputImageTexture, textureCoordinate - secondOffset).rgb * 0.0702702703;\n   sum += texture2D(inputImageTexture, textureCoordinate + secondOffset).rgb * 0.0702702703;\n   gl_FragColor = vec4(sum, color.a);\n}\n";
    private float mTexelHeight;
    private int mTexelHeightLocation;
    private float mTexelWidth;
    private int mTexelWidthLocation;

    public WWWWVW(float f, float f2) {
        super(ImageRender.NO_FILTER_VERTEX_SHADER, FAST_BLUR_FRAGMENT_SHADER);
        this.mTexelWidth = f;
        this.mTexelHeight = f2;
    }

    public void onInit() {
        super.onInit();
        this.mTexelWidthLocation = GLES20.glGetUniformLocation(getProgram(), "texelWidth");
        this.mTexelHeightLocation = GLES20.glGetUniformLocation(getProgram(), "texelHeight");
    }

    public void onInitialized() {
        super.onInitialized();
        setTexelWidth(this.mTexelWidth);
        setTexelHeight(this.mTexelHeight);
    }

    public void setTexelWidth(float f) {
        this.mTexelWidth = f;
        setFloat(this.mTexelWidthLocation, this.mTexelWidth);
    }

    public void setTexelHeight(float f) {
        this.mTexelHeight = f;
        setFloat(this.mTexelHeightLocation, this.mTexelHeight);
    }
}
