package com.yd.photoeditor.imageprocessing.filter.processing;

import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageRender;

public class WWW extends ImageRender {
    private static final String CLASSIC_NOISE_3D_FRAGMENT_SHADER = "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying highp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float time;\nuniform vec2 resolution;\nvec3 mod289(vec3 x)\n{\nreturn x - floor(x * (1.0 / 289.0)) * 289.0;\n}\nvec4 mod289(vec4 x)\n{\nreturn x - floor(x * (1.0 / 289.0)) * 289.0;\n}\nvec4 permute(vec4 x)\n{\nreturn mod289(((x*34.0)+1.0)*x);\n}\nvec4 taylorInvSqrt(vec4 r)\n{\nreturn 1.79284291400159 - 0.85373472095314 * r;\n}\nvec3 fade(vec3 t) {\nreturn t*t*t*(t*(t*6.0-15.0)+10.0);\n}\nfloat cnoise(vec3 P)\n{\nvec3 Pi0 = floor(P);\nvec3 Pi1 = Pi0 + vec3(1.0);\nPi0 = mod289(Pi0);\nPi1 = mod289(Pi1);\nvec3 Pf0 = fract(P);\nvec3 Pf1 = Pf0 - vec3(1.0);\nvec4 ix = vec4(Pi0.x, Pi1.x, Pi0.x, Pi1.x);\nvec4 iy = vec4(Pi0.yy, Pi1.yy);\nvec4 iz0 = Pi0.zzzz;\nvec4 iz1 = Pi1.zzzz;\nvec4 ixy = permute(permute(ix) + iy);\nvec4 ixy0 = permute(ixy + iz0);\nvec4 ixy1 = permute(ixy + iz1);\nvec4 gx0 = ixy0 * (1.0 / 7.0);\nvec4 gy0 = fract(floor(gx0) * (1.0 / 7.0)) - 0.5;\ngx0 = fract(gx0);\nvec4 gz0 = vec4(0.5) - abs(gx0) - abs(gy0);\nvec4 sz0 = step(gz0, vec4(0.0));\ngx0 -= sz0 * (step(0.0, gx0) - 0.5);\ngy0 -= sz0 * (step(0.0, gy0) - 0.5);\nvec4 gx1 = ixy1 * (1.0 / 7.0);\nvec4 gy1 = fract(floor(gx1) * (1.0 / 7.0)) - 0.5;\ngx1 = fract(gx1);\nvec4 gz1 = vec4(0.5) - abs(gx1) - abs(gy1);\nvec4 sz1 = step(gz1, vec4(0.0));\ngx1 -= sz1 * (step(0.0, gx1) - 0.5);\ngy1 -= sz1 * (step(0.0, gy1) - 0.5);\nvec3 g000 = vec3(gx0.x,gy0.x,gz0.x);\nvec3 g100 = vec3(gx0.y,gy0.y,gz0.y);\nvec3 g010 = vec3(gx0.z,gy0.z,gz0.z);\nvec3 g110 = vec3(gx0.w,gy0.w,gz0.w);\nvec3 g001 = vec3(gx1.x,gy1.x,gz1.x);\nvec3 g101 = vec3(gx1.y,gy1.y,gz1.y);\nvec3 g011 = vec3(gx1.z,gy1.z,gz1.z);\nvec3 g111 = vec3(gx1.w,gy1.w,gz1.w);\nvec4 norm0 = taylorInvSqrt(vec4(dot(g000, g000), dot(g010, g010), dot(g100, g100), dot(g110, g110)));\ng000 *= norm0.x;\ng010 *= norm0.y;\ng100 *= norm0.z;\ng110 *= norm0.w;\nvec4 norm1 = taylorInvSqrt(vec4(dot(g001, g001), dot(g011, g011), dot(g101, g101), dot(g111, g111)));\ng001 *= norm1.x;\ng011 *= norm1.y;\ng101 *= norm1.z;\ng111 *= norm1.w;\nfloat n000 = dot(g000, Pf0);\nfloat n100 = dot(g100, vec3(Pf1.x, Pf0.yz));\nfloat n010 = dot(g010, vec3(Pf0.x, Pf1.y, Pf0.z));\nfloat n110 = dot(g110, vec3(Pf1.xy, Pf0.z));\nfloat n001 = dot(g001, vec3(Pf0.xy, Pf1.z));\nfloat n101 = dot(g101, vec3(Pf1.x, Pf0.y, Pf1.z));\nfloat n011 = dot(g011, vec3(Pf0.x, Pf1.yz));\nfloat n111 = dot(g111, Pf1);\nvec3 fade_xyz = fade(Pf0);\nvec4 n_z = mix(vec4(n000, n100, n010, n110), vec4(n001, n101, n011, n111), fade_xyz.z);\nvec2 n_yz = mix(n_z.xy, n_z.zw, fade_xyz.y);\nfloat n_xyz = mix(n_yz.x, n_yz.y, fade_xyz.x);\nreturn 2.2 * n_xyz;\n}\nfloat surface3 ( vec3 coord ) {\nfloat frequency = 4.0;\nfloat n = 0.0;\nn += 1.0\t* abs( cnoise( coord * frequency ) );\nn += 0.5\t* abs( cnoise( coord * frequency * 2.0 ) );\nn += 0.25\t* abs( cnoise( coord * frequency * 4.0 ) );\nreturn n;\n}\nvoid main( void ) {\nvec2 position = gl_FragCoord.xy / resolution.xy;\nvec4 color = texture2D(inputImageTexture, textureCoordinate);\nfloat gray = surface3(vec3(position, (color.r + color.g + color.b) / 3.0));\ngl_FragColor = vec4(gray, gray, gray, color.a);\n}\n";
    private float[] mResolution;
    private int mResolutionLocation;
    private float mTime = 1.0f;
    private int mTimeLocation;

    public WWW(float[] fArr) {
        super(ImageRender.NO_FILTER_VERTEX_SHADER, CLASSIC_NOISE_3D_FRAGMENT_SHADER);
        this.mResolution = fArr;
    }

    public void onInit() {
        super.onInit();
        this.mResolutionLocation = GLES20.glGetUniformLocation(getProgram(), "resolution");
        this.mTimeLocation = GLES20.glGetUniformLocation(getProgram(), "time");
    }

    public void onInitialized() {
        super.onInitialized();
        setResolution(this.mResolution);
        setTime(this.mTime);
    }

    public void setResolution(float[] fArr) {
        this.mResolution = fArr;
        setFloatVec2(this.mResolutionLocation, this.mResolution);
    }

    public void setTime(float f) {
        this.mTime = f;
        setFloat(this.mTimeLocation, this.mTime);
    }
}
