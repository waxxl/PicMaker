package com.yd.photoeditor.imageprocessing.filter.processing;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageRender;

public class WWWVV extends ImageRender {
    public static final String PINCH_DISTORTION_FRAGMENT_SHADER = "precision highp float;\nvarying highp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform highp float aspectRatio;\nuniform highp vec2 center;\nuniform highp float radius;\nuniform highp float scale;\nvoid main()\n{\nhighp vec2 textureCoordinateToUse = vec2(textureCoordinate.x, (textureCoordinate.y * aspectRatio + 0.5 - 0.5 * aspectRatio));\nhighp float dist = distance(center, textureCoordinateToUse);\ntextureCoordinateToUse = textureCoordinate;\nif (dist < radius)\n{\ntextureCoordinateToUse -= center;\nhighp float percent = 1.0 + ((0.5 - dist) / 0.5) * scale;\ntextureCoordinateToUse = textureCoordinateToUse * percent;\ntextureCoordinateToUse += center;\ngl_FragColor = texture2D(inputImageTexture, textureCoordinateToUse );\n}else{\ngl_FragColor = texture2D(inputImageTexture, textureCoordinate );\n}\n}";
    private float mAspectRatio = 1.0f;
    private int mAspectRatioLocation;
    private float[] mCenter = {0.5f, 0.5f};
    private int mCenterLocation;
    private float mRadius = 1.0f;
    private int mRadiusLocation;
    private float mScale = 0.5f;
    private int mScaleLocation;

    public WWWVV() {
        super(ImageRender.NO_FILTER_VERTEX_SHADER, PINCH_DISTORTION_FRAGMENT_SHADER);
    }

    public void onInit() {
        super.onInit();
        this.mAspectRatioLocation = GLES20.glGetUniformLocation(getProgram(), "aspectRatio");
        this.mCenterLocation = GLES20.glGetUniformLocation(getProgram(), "center");
        this.mRadiusLocation = GLES20.glGetUniformLocation(getProgram(), "radius");
        this.mScaleLocation = GLES20.glGetUniformLocation(getProgram(), "scale");
    }

    public void onInitialized() {
        super.onInitialized();
        setAspectRatio(this.mAspectRatio);
        setCenter(this.mCenter);
        setRadius(this.mRadius);
        setScale(this.mScale);
    }

    public void setAspectRatio(float f) {
        this.mAspectRatio = f;
        setFloat(this.mAspectRatioLocation, this.mAspectRatio);
    }

    public void setCenter(float[] fArr) {
        this.mCenter = fArr;
        setFloatVec2(this.mCenterLocation, this.mCenter);
    }

    public void setRadius(float f) {
        this.mRadius = f;
        setFloat(this.mRadiusLocation, this.mRadius);
    }

    public void setScale(float f) {
        this.mScale = f;
        setFloat(this.mScaleLocation, this.mScale);
    }
}
