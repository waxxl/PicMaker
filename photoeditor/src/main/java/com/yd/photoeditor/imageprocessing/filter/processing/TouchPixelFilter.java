package com.yd.photoeditor.imageprocessing.filter.processing;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class TouchPixelFilter extends ImageFilter {
    public static final String TOUCH_FILTER_FRAGMENT_SHADER = "precision highp float;\nuniform vec2 centerCircle;\nuniform float radius;\nvarying highp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n\thighp float distanceFromCenter = distance(gl_FragCoord.xy, centerCircle);\n   if(distanceFromCenter < radius){\n\t\tgl_FragColor = vec4(0.0, 1.0, 0.0, 1.0);\n\t\t}else\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}";
    private float[] mCenterCircle = {0.0f, 0.0f};
    private int mCenterCircleLocation;
    private float mRadius = 1.0f;
    private int mRadiusLocation;

    public TouchPixelFilter() {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, TOUCH_FILTER_FRAGMENT_SHADER);
    }

    public void onInit() {
        super.onInit();
        this.mCenterCircleLocation = GLES20.glGetUniformLocation(getProgram(), "centerCircle");
        this.mRadiusLocation = GLES20.glGetUniformLocation(getProgram(), "radius");
    }

    public void onInitialized() {
        super.onInitialized();
        setCenterCircle(this.mCenterCircle);
        setRadius(this.mRadius);
    }

    public void setCenterCircle(float[] fArr) {
        this.mCenterCircle = fArr;
        setFloatVec2(this.mCenterCircleLocation, this.mCenterCircle);
    }

    public void setRadius(float f) {
        this.mRadius = f;
        setFloat(this.mRadiusLocation, f);
    }

    public float getRadius() {
        return this.mRadius;
    }

    public float[] getCenterCircle() {
        return this.mCenterCircle;
    }
}
