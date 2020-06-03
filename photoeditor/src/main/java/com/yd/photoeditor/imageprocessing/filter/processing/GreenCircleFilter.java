package com.yd.photoeditor.imageprocessing.filter.processing;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class GreenCircleFilter extends ImageFilter {
    public static final String RED_FILTER_FRAGMENT_SHADER = "precision highp float;\nuniform highp vec2 centerCircle;\nuniform highp float radius;\nuniform highp float aspectRatio;\nvarying highp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     highp vec2 textureCoordinateToUse = vec2(textureCoordinate.x, (textureCoordinate.y * aspectRatio + 0.5 - 0.5 * aspectRatio));\n     highp float distanceFromCenter = distance(centerCircle, textureCoordinateToUse);\n     if(distanceFromCenter < radius){\n\t\t\tgl_FragColor = vec4(0.0, 1.0, 0.0, 1.0);\n\t\t}else\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}";
    private float mAspectRatio = 1.0f;
    private int mAspectRatioLocation;
    private float[] mCenterCircle = {0.5f, 0.5f};
    private int mCenterCircleLocation;
    private float mRadius = 0.1f;
    private int mRadiusLocation;

    public GreenCircleFilter() {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, RED_FILTER_FRAGMENT_SHADER);
    }

    public void onInit() {
        super.onInit();
        this.mCenterCircleLocation = GLES20.glGetUniformLocation(getProgram(), "centerCircle");
        this.mRadiusLocation = GLES20.glGetUniformLocation(getProgram(), "radius");
        this.mAspectRatioLocation = GLES20.glGetUniformLocation(getProgram(), "aspectRatio");
    }

    public void onInitialized() {
        super.onInitialized();
        setCenterCircle(this.mCenterCircle);
        setRadius(this.mRadius);
        setAspectRatio(this.mAspectRatio);
    }

    public void setCenterCircle(float[] fArr) {
        this.mCenterCircle = fArr;
        setFloatVec2(this.mCenterCircleLocation, this.mCenterCircle);
    }

    public void setRadius(float f) {
        this.mRadius = f;
        setFloat(this.mRadiusLocation, f);
    }

    public void setAspectRatio(float f) {
        this.mAspectRatio = f;
        setFloat(this.mAspectRatioLocation, this.mAspectRatio);
    }
}
