package com.yd.photoeditor.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterMatrixManager {
    public FilterMatrixManager() {
        initOptions();
    }

    private List<Integer> options;

    public List<Integer> getOptions() {
        return options;
    }

//    public List<String> getOptionTexts() {
//        return optionTexts;
//    }

    public void initOptions() {
        options = new ArrayList<>();

        options.add(Mode.GREY_SCALE);
        options.add(Mode.SAT);
        options.add(Mode.OLD);
        options.add(Mode.INVERT);
        options.add(Mode.RGB_TO_BGR);
        options.add(Mode.SEPIA);
        options.add(Mode.BLACK_AND_WHITE);
        options.add(Mode.BRIGHT);
        options.add(Mode.VINTAGE_PINHOLE);
        options.add(Mode.KODACHROME);
        options.add(Mode.TECHNICOLOR);
        options.add(Mode.T0);
        options.add(Mode.T1);
        options.add(Mode.T2);
        options.add(Mode.T3);
        options.add(Mode.T4);

    }


    public static class Mode {
        public static final int NONE = -1;
        public static final int SATURATION = 0;
        public static final int GREY_SCALE = 1;
        public static final int INVERT = 2;
        public static final int RGB_TO_BGR = 3;
        public static final int SEPIA = 4;
        public static final int BLACK_AND_WHITE = 5;
        public static final int BRIGHT = 6;
        public static final int VINTAGE_PINHOLE = 7;
        public static final int KODACHROME = 8;
        public static final int TECHNICOLOR = 9;
        public static final int T0 = 10;
        public static final int T1 = 11;
        public static final int T2 = 12;
        public static final int T3 = 13;
        public static final int T4 = 14;
        public static final int NO_R = 15;
        public static final int NO_G = 16;
        public static final int NO_B = 17;
        public static final int SAT = 18;
        public static final int OLD = 19;
    }


    public static float[] calculateMatrix(int mode, int brightness, float contrast, float saturation) {
        return applyBrightnessAndContrast(getMatrixByMode(mode), brightness, contrast);
    }

    public static float[] applyBrightnessAndContrast(float[] matrix, int brightness, float contrast) {
        float t = (1.0F - contrast) / 2.0F * 255.0F;
        for (int i = 0; i < 3; i++) {
            for (int j = i * 5; j < i * 5 + 3; j++) {
                matrix[j] *= contrast;
            }
            matrix[5 * i + 4] += t + brightness;
        }
        return matrix;
    }

    public static float[] getMatrixByMode(int mode) {
        float[] targetMatrix = FilterMatrix.common();
        switch (mode) {
            case Mode.NONE:
                targetMatrix = FilterMatrix.common();
                break;
            case Mode.GREY_SCALE:
                targetMatrix = FilterMatrix.greyScale();
                break;
            case Mode.INVERT:
                targetMatrix = FilterMatrix.invert();
                break;
            case Mode.RGB_TO_BGR:
                targetMatrix = FilterMatrix.rgbToBgr();
                break;
            case Mode.SEPIA:
                targetMatrix = FilterMatrix.sepia();
                break;
            case Mode.BRIGHT:
                targetMatrix = FilterMatrix.bright();
                break;
            case Mode.BLACK_AND_WHITE:
                targetMatrix = FilterMatrix.blackAndWhite();
                break;
            case Mode.VINTAGE_PINHOLE:
                targetMatrix = FilterMatrix.vintagePinhole();
                break;
            case Mode.KODACHROME:
                targetMatrix = FilterMatrix.kodachrome();
                break;
            case Mode.TECHNICOLOR:
                targetMatrix = FilterMatrix.technicolor();
                break;
            case Mode.T0:
                targetMatrix = FilterMatrix.colormatrix_danya;
                break;
            case Mode.T1:
                targetMatrix = FilterMatrix.colormatrix_gete;
                break;
            case Mode.T2:
                targetMatrix = FilterMatrix.colormatrix_guangyun;
                break;
            case Mode.T3:
                targetMatrix = FilterMatrix.colormatrix_landiao;
                break;
            case Mode.T4:
                targetMatrix = FilterMatrix.colormatrix_menghuan;
                break;
            case Mode.NO_R:
                targetMatrix = FilterMatrix.NO_R;
                break;
            case Mode.NO_G:
                targetMatrix = FilterMatrix.NO_G;
                break;
            case Mode.NO_B:
                targetMatrix = FilterMatrix.NO_B;
                break;
            case Mode.SAT:
                targetMatrix = FilterMatrix.SAT;
                break;
            case Mode.OLD:
                targetMatrix = FilterMatrix.OLD;
                break;
        }
        return targetMatrix;
    }
}
