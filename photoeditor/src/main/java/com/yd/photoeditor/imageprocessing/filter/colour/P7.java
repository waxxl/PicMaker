package com.yd.photoeditor.imageprocessing.filter.colour;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageRender;

public class P7 extends ImageRender {
    public static final String DDDDDDDDDDD = "precision highp float;\n  varying highp vec2 textureCoordinate;\n  \n  uniform sampler2D inputImageTexture;\n  uniform lowp float opacity;\n  \n  void main()\n  {\n      lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n      \n      gl_FragColor = vec4(textureColor.rgb, textureColor.a * opacity);\n  }\n";
    private float mOpacity;
    private int mOpacityLocation;

    public P7() {
        this(1.0f);
    }

    public P7(float f) {
        super(ImageRender.NO_FILTER_VERTEX_SHADER, DDDDDDDDDDD);
        this.mOpacity = f;
    }

    public void onInit() {
        super.onInit();
        this.mOpacityLocation = GLES20.glGetUniformLocation(getProgram(), "opacity");
    }

    public void onInitialized() {
        super.onInitialized();
        setOpacity(this.mOpacity);
    }

    public void setOpacity(float f) {
        this.mOpacity = f;
        setFloat(this.mOpacityLocation, this.mOpacity);
    }
}
