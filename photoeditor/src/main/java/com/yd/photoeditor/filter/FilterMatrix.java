package com.yd.photoeditor.filter;

/**
 * Created by chengdazhi on 8/10/16.
 */
public class FilterMatrix {

    private static final float[] COMMON = new float[]{
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0
    };

    public static final float[] common() {
        return COMMON.clone();
    }

    private static final float[] GREY_SCALE = new float[]{
            0.33F, 0.59F, 0.11F, 0, 0,
            0.33F, 0.59F, 0.11F, 0, 0,
            0.33F, 0.59F, 0.11F, 0, 0,
            0, 0, 0, 1, 0,
    };

    public static final float[] greyScale() {
        return GREY_SCALE.clone();
    }

    private static final float[] INVERT = new float[]{
            -1, 0, 0, 0, 255,
            0, -1, 0, 0, 255,
            0, 0, -1, 0, 255,
            0, 0, 0, 1, 0,
    };

    public static final float[] invert() {
        return INVERT.clone();
    }

    private static final float[] RGB_TO_BGR = new float[]{
            0, 0, 1, 0, 0,
            0, 1, 0, 0, 0,
            1, 0, 0, 0, 0,
            0, 0, 0, 1, 0,
    };

    public static final float[] rgbToBgr() {
        return RGB_TO_BGR.clone();
    }

    private static final float[] SEPIA = new float[]{
            0.393F, 0.769F, 0.189F, 0, 0,
            0.349F, 0.686F, 0.168F, 0, 0,
            0.272F, 0.534F, 0.131F, 0, 0,
            0, 0, 0, 1, 0,
    };

    public static final float[] sepia() {
        return SEPIA.clone();
    }

    private static final float[] BRIGHT = new float[]{
            1.438F, -0.122F, -0.016F, 0, 0,
            -0.062F, 1.378F, -0.016F, 0, 0,
            -0.062F, -0.122F, 1.483F, 0, 0,
            0, 0, 0, 1, 0,
    };

    public static final float[] bright() {
        return BRIGHT.clone();
    }

    private static final float[] BLACK_AND_WHITE = new float[]{
            1.5F, 1.5F, 1.5F, 0, -255,
            1.5F, 1.5F, 1.5F, 0, -255,
            1.5F, 1.5F, 1.5F, 0, -255,
            0, 0, 0, 1, 0,
    };

    public static final float[] blackAndWhite() {
        return BLACK_AND_WHITE.clone();
    }

    private static final float[] VINTAGE_PINHOLE = new float[]{
            0.6279345635605994F, 0.3202183420819367F, -0.03965408211312453F, 0, 9.651285835294123F,
            0.02578397704808868F, 0.6441188644374771F, 0.03259127616149294F, 0, 7.462829176470591F,
            0.0466055556782719F, -0.0851232987247891F, 0.5241648018700465F, 0, 5.159190588235296F,
            0, 0, 0, 1, 0
    };

    public static final float[] vintagePinhole() {
        return VINTAGE_PINHOLE.clone();
    }

    private static final float[] KODACHROME = new float[]{
            1.1285582396593525F, -0.3967382283601348F, -0.03992559172921793F, 0, 63.72958762196502F,
            -0.16404339962244616F, 1.0835251566291304F, -0.05498805115633132F, 0, 24.732407896706203F,
            -0.16786010706155763F, -0.5603416277695248F, 1.6014850761964943F, 0, 35.62982807460946F,
            0, 0, 0, 1, 0
    };

    public static final float[] kodachrome() {
        return KODACHROME.clone();
    }

    private static final float[] TECHNICOLOR = new float[]{
            1.9125277891456083F, -0.8545344976951645F, -0.09155508482755585F, 0, 11.793603434377337F,
            -0.3087833385928097F, 1.7658908555458428F, -0.10601743074722245F, 0, -70.35205161461398F,
            -0.231103377548616F, -0.7501899197440212F, 1.847597816108189F, 0, 30.950940869491138F,
            0, 0, 0, 1, 0
    };

    public static final float[] technicolor() {
        return TECHNICOLOR.clone();
    }
//lomo


    public static final float colormatrix_lomo[] = {

            1.7f, 0.1f, 0.1f, 0, -73.1f,

            0, 1.7f, 0.1f, 0, -73.1f,

            0, 0.1f, 1.6f, 0, -73.1f,

            0, 0, 0, 1.0f, 0};


//黑白

    public static final float colormatrix_heibai[] = {

            0.8f, 1.6f, 0.2f, 0, -163.9f,

            0.8f, 1.6f, 0.2f, 0, -163.9f,

            0.8f, 1.6f, 0.2f, 0, -163.9f,

            0, 0, 0, 1.0f, 0};

//旧化

    public static final float colormatrix_huajiu[] = {

            0.2f, 0.5f, 0.1f, 0, 40.8f,

            0.2f, 0.5f, 0.1f, 0, 40.8f,

            0.2f, 0.5f, 0.1f, 0, 40.8f,

            0, 0, 0, 1, 0};


//哥特

    public static final float colormatrix_gete[] = {

            1.9f, -0.3f, -0.2f, 0, -87.0f,

            -0.2f, 1.7f, -0.1f, 0, -87.0f,

            -0.1f, -0.6f, 2.0f, 0, -87.0f,

            0, 0, 0, 1.0f, 0};


//锐色

    public static final float colormatrix_ruise[] = {

            4.8f, -1.0f, -0.1f, 0, -388.4f,

            -0.5f, 4.4f, -0.1f, 0, -388.4f,

            -0.5f, -1.0f, 5.2f, 0, -388.4f,

            0, 0, 0, 1.0f, 0};


//淡雅

    public static final float colormatrix_danya[] = {

            0.6f, 0.3f, 0.1f, 0, 73.3f,

            0.2f, 0.7f, 0.1f, 0, 73.3f,

            0.2f, 0.3f, 0.4f, 0, 73.3f,

            0, 0, 0, 1.0f, 0};


//酒红

    public static final float colormatrix_jiuhong[] = {

            1.2f, 0.0f, 0.0f, 0.0f, 0.0f,

            0.0f, 0.9f, 0.0f, 0.0f, 0.0f,

            0.0f, 0.0f, 0.8f, 0.0f, 0.0f,

            0, 0, 0, 1.0f, 0};


//清宁

    public static final float colormatrix_qingning[] = {

            0.9f, 0, 0, 0, 0,

            0, 1.1f, 0, 0, 0,

            0, 0, 0.9f, 0, 0,

            0, 0, 0, 1.0f, 0};


//浪漫

    public static final float colormatrix_langman[] = {

            0.9f, 0, 0, 0, 63.0f,

            0, 0.9f, 0, 0, 63.0f,

            0, 0, 0.9f, 0, 63.0f,

            0, 0, 0, 1.0f, 0};


//光晕

    public static final float colormatrix_guangyun[] = {

            0.9f, 0, 0, 0, 64.9f,

            0, 0.9f, 0, 0, 64.9f,

            0, 0, 0.9f, 0, 64.9f,

            0, 0, 0, 1.0f, 0};


//蓝调

    public static final float colormatrix_landiao[] = {

            2.1f, -1.4f, 0.6f, 0.0f, -31.0f,

            -0.3f, 2.0f, -0.3f, 0.0f, -31.0f,

            -1.1f, -0.2f, 2.6f, 0.0f, -31.0f,

            0.0f, 0.0f, 0.0f, 1.0f, 0.0f

    };


//梦幻

    public static final float colormatrix_menghuan[] = {

            0.8f, 0.3f, 0.1f, 0.0f, 46.5f,

            0.1f, 0.9f, 0.0f, 0.0f, 46.5f,

            0.1f, 0.3f, 0.7f, 0.0f, 46.5f,

            0.0f, 0.0f, 0.0f, 1.0f, 0.0f

    };
    public static final float colormatrix_yese[] = {

            1.0f, 0.0f, 0.0f, 0.0f, -66.6f,

            0.0f, 1.1f, 0.0f, 0.0f, -66.6f,

            0.0f, 0.0f, 1.0f, 0.0f, -66.6f,

            0.0f, 0.0f, 0.0f, 1.0f, 0.0f

    };

    public static final float NO_R[] = {
            0, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0

    };
    public static final float NO_G[] = {
            1, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0,

    };
    public static final float NO_B[] = {
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 1, 0

    };
    //5\高饱和度效果
    public static float[] SAT = new float[]{
            1.438f, -0.122f, -0.016f, 0, -0.03f,
            -0.062f, 1.378f, -0.016f, 0, 0.05f,
            -0.062f, -0.122f, 1.483f, 0, -0.02f,
            0, 0, 0, 1, 0
    };
    //3\怀旧效果
    public static float[] OLD = new float[]{
            0.393f, 0.769f, 0.189f, 0, 0,
            0.349f, 0.686f, 0.168f, 0, 0,
            0.272f, 0.543f, 0.131f, 0, 0,
            0, 0, 0, 1, 0
    };
}
