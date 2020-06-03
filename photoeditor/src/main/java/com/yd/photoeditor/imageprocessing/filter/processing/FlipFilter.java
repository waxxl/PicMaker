package com.yd.photoeditor.imageprocessing.filter.processing;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class FlipFilter extends ImageFilter {
    public static final String FLIP_FILTER_FRAGMENT_SHADER = "uniform lowp int flipType;\nvarying highp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nvoid main()\n{\n     highp vec2 textureCoordinateToUse = vec2(textureCoordinate.x, 1.0 - textureCoordinate.y);\n     if(flipType != 0)textureCoordinateToUse = vec2(1.0 - textureCoordinate.x, textureCoordinate.y);\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinateToUse);\n}";
    private int mFlipType;
    private int mFlipTypeLocation;

    public FlipFilter(int i) {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, FLIP_FILTER_FRAGMENT_SHADER);
        this.mFlipType = i;
    }

    public void onInit() {
        super.onInit();
        this.mFlipTypeLocation = GLES20.glGetUniformLocation(getProgram(), "flipType");
    }

    public void onInitialized() {
        super.onInitialized();
        setFlipType(this.mFlipType);
    }

    public void setFlipType(int i) {
        this.mFlipType = i;
        setInteger(this.mFlipTypeLocation, this.mFlipType);
    }
}
