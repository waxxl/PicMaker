package com.yd.photoeditor.imageprocessing.filter.colour;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;

public class WhiteBalanceFilter extends ImageFilter {
    public static final String WHITE_BALANCE_FRAGMENT_SHADER = "precision highp float;\nuniform sampler2D inputImageTexture;\nvarying highp vec2 textureCoordinate;\n \nuniform lowp float temperature;\nuniform lowp float tint;\n\nconst lowp vec3 warmFilter = vec3(0.93, 0.54, 0.0);\n\nconst mediump mat3 RGBtoYIQ = mat3(0.299, 0.587, 0.114, 0.596, -0.274, -0.322, 0.212, -0.523, 0.311);\nconst mediump mat3 YIQtoRGB = mat3(1.0, 0.956, 0.621, 1.0, -0.272, -0.647, 1.0, -1.105, 1.702);\n\nvoid main()\n{\n\tlowp vec4 source = texture2D(inputImageTexture, textureCoordinate);\n\t\n\tmediump vec3 yiq = RGBtoYIQ * source.rgb; //adjusting tint\n\tyiq.b = clamp(yiq.b + tint*0.5226*0.1, -0.5226, 0.5226);\n\tlowp vec3 rgb = YIQtoRGB * yiq;\n\n\tlowp vec3 processed = vec3(\n\t\t(rgb.r < 0.5 ? (2.0 * rgb.r * warmFilter.r) : (1.0 - 2.0 * (1.0 - rgb.r) * (1.0 - warmFilter.r))), //adjusting temperature\n\t\t(rgb.g < 0.5 ? (2.0 * rgb.g * warmFilter.g) : (1.0 - 2.0 * (1.0 - rgb.g) * (1.0 - warmFilter.g))), \n\t\t(rgb.b < 0.5 ? (2.0 * rgb.b * warmFilter.b) : (1.0 - 2.0 * (1.0 - rgb.b) * (1.0 - warmFilter.b))));\n\n\tgl_FragColor = vec4(mix(rgb, processed, temperature), source.a);\n}";
    private float mTemperature;
    private int mTemperatureLocation;
    private float mTint;
    private int mTintLocation;

    public WhiteBalanceFilter() {
        this(5000.0f, 0.0f);
    }

    public WhiteBalanceFilter(float f, float f2) {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, WHITE_BALANCE_FRAGMENT_SHADER);
        this.mTemperature = f;
        this.mTint = f2;
    }

    public void onInit() {
        super.onInit();
        this.mTemperatureLocation = GLES20.glGetUniformLocation(getProgram(), "temperature");
        this.mTintLocation = GLES20.glGetUniformLocation(getProgram(), "tint");
        setTemperature(this.mTemperature);
        setTint(this.mTint);
    }

    public void setTemperature(float f) {
        this.mTemperature = f;
        int i = this.mTemperatureLocation;
        float f2 = this.mTemperature;
        double d = f2 < 5000.0f ? 4.0E-4d : 6.0E-5d;
        double d2 = f2;
        Double.isNaN(d2);
        setFloat(i, (float) ((d2 - 5000.0d) * d));
    }

    public void setTint(float f) {
        this.mTint = f;
        int i = this.mTintLocation;
        double d = this.mTint;
        Double.isNaN(d);
        setFloat(i, (float) (d / 100.0d));
    }
}
