package com.yd.photoeditor.model;

import android.graphics.PointF;

import com.yd.photoeditor.config.Constants;
import com.yd.photoeditor.config.PLog;
import com.yd.photoeditor.imageprocessing.filter.ImageRender;
import com.yd.photoeditor.imageprocessing.filter.ImageRenderGroup;
import com.yd.photoeditor.imageprocessing.filter.blend.P1;
import com.yd.photoeditor.imageprocessing.filter.colour.P4;
import com.yd.photoeditor.imageprocessing.filter.colour.P5;
import com.yd.photoeditor.imageprocessing.filter.colour.P13;
import com.yd.photoeditor.imageprocessing.filter.colour.P7;
import com.yd.photoeditor.imageprocessing.filter.colour.P8;
import com.yd.photoeditor.imageprocessing.filter.colour.P10;
import com.yd.photoeditor.imageprocessing.filter.effect.F1;
import com.yd.photoeditor.imageprocessing.filter.effect.VVVVVVVVVVVVVVVV;
import com.yd.photoeditor.imageprocessing.filter.effect.F2;
import com.yd.photoeditor.imageprocessing.filter.effect.F12;
import com.yd.photoeditor.imageprocessing.filter.effect.F12345;
import com.yd.photoeditor.imageprocessing.filter.effect.F123456;
import com.yd.photoeditor.imageprocessing.filter.effect.Fxxx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilterInfo extends XXXXXXXXXXXXXX {
    private static final String TAG = FilterInfo.class.getSimpleName();
    private String mCmd;
    private String[] mCommands;
    private ImageRender mImageRender;
    private String mPackageFolder;
    private long mPackageId;
    private final Map<String, ArrayList<String>> mParamsMap = new HashMap();

    public ImageRender getImageFilter() {
        ImageRender imageRender = this.mImageRender;
        if (imageRender == null || !imageRender.isInitialized()) {
            buildImageFilter();
        }
        return mImageRender;
    }

    public void setCmd(String str) {
        mCmd = str;
    }

    public String getCmd() {
        return mCmd;
    }

    public void setPackageFolder(String str) {
        mPackageFolder = str;
    }

    public String getPackageFolder() {
        return mPackageFolder;
    }

    public void setPackageId(long j) {
        mPackageId = j;
    }

    public long getPackageId() {
        return mPackageId;
    }

    private void buildImageFilter() {
        PLog.d(TAG, "buildImageFilter, name=" + getTitle());
        if (mCmd == null || mCmd.length() < 1) {
            mImageRender = new ImageRender();
            return;
        }
        if (mCommands == null) {
            mCommands = mCmd.split(",");
        }
        ArrayList arrayList = new ArrayList();
        String[] strArr = mCommands;
        if (strArr != null) {
            for (String strt : strArr) {
                arrayList.add(createFilter(strt));
            }
        }
        if (arrayList.size() > 1) {
            mImageRender = new ImageRenderGroup(arrayList);
        } else if (arrayList.size() > 0) {
            mImageRender = (ImageRender) arrayList.get(0);
        } else {
            mImageRender = new ImageRender();
        }
    }

    private ImageRender createFilter(String str) {
        P1 p1;
        ImageRender imageRender;
        float f;
        ArrayList arrayList = mParamsMap.get(str);
        if (arrayList == null) {
            String[] split = str.trim().split("\\ ");
            ArrayList arrayList2 = new ArrayList();
            if (split != null && split.length > 0) {
                for (String trim : split) {
                    String trim2 = trim.trim();
                    if (trim2.length() > 0) {
                        arrayList2.add(trim2);
                    }
                }
            }
            mParamsMap.put(str, arrayList2);
            arrayList = arrayList2;
        }
        if (arrayList.size() > 0) {
            String str3 = (String) arrayList.get(0);
            if (str3.equalsIgnoreCase(Constants.EFFECTS_CMDS[1])) {
                P5 p5 = new P5();
                if (arrayList.size() > 1) {
                    try {
                        p5.setHue(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return p5;
            } else if (str3.equalsIgnoreCase(Constants.EFFECTS_CMDS[2])) {
                P10 p10 = new P10();
                if (arrayList.size() > 1) {
                    try {
                        p10.setIntensity(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                return p10;
            } else if (str3.equalsIgnoreCase("E")) {
                VVVVVVVVVVVVVVVV embossFilter = new VVVVVVVVVVVVVVVV();
                if (arrayList.size() > 1) {
                    try {
                        embossFilter.setIntensity(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                return embossFilter;
            } else if (str3.equalsIgnoreCase("Sk")) {
                F12345 f12345 = new F12345();
                if (arrayList.size() > 1) {
                    try {
                        f12345.setLineSize(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
                return f12345;
            } else if (str3.equalsIgnoreCase("K")) {
                F12 f12 = new F12();
                if (arrayList.size() > 1) {
                    try {
                        f12.setRadius(Integer.parseInt((String) arrayList.get(1)));
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                return f12;
            } else if (str3.equalsIgnoreCase("D")) {
                if (arrayList.size() > 1) {
                    try {
                        return new F1(Integer.parseInt((String) arrayList.get(1)));
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
                return new F1();
            } else if (str3.equalsIgnoreCase("St")) {
                F123456 f123456 = new F123456();
                if (arrayList.size() > 1) {
                    try {
                        f123456.setQuantizationLevels(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                    if (arrayList.size() > 2) {
                        try {
                            f123456.setThreshold(Float.parseFloat((String) arrayList.get(2)));
                        } catch (Exception e8) {
                            e8.printStackTrace();
                        }
                    }
                    if (arrayList.size() > 3) {
                        try {
                            f123456.setBlurSize(Float.parseFloat((String) arrayList.get(3)));
                        } catch (Exception e9) {
                            e9.printStackTrace();
                        }
                    }
                    if (arrayList.size() > 4) {
                        try {
                            f123456.setTexelWidth(Float.parseFloat((String) arrayList.get(4)));
                        } catch (Exception e10) {
                            e10.printStackTrace();
                        }
                    }
                    if (arrayList.size() > 5) {
                        try {
                            f123456.setTexelHeight(Float.parseFloat((String) arrayList.get(5)));
                        } catch (Exception e11) {
                            e11.printStackTrace();
                        }
                    }
                }
                return f123456;
            } else if (str3.equalsIgnoreCase("Ha")) {
                F2 f2 = new F2();
                if (arrayList.size() > 1) {
                    try {
                        f2.setFractionalWidthOfAPixel(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e12) {
                        e12.printStackTrace();
                    }
                    if (arrayList.size() > 2) {
                        try {
                            f2.setAspectRatio(Float.parseFloat((String) arrayList.get(2)));
                        } catch (Exception e13) {
                            e13.printStackTrace();
                        }
                    }
                }
                return f2;
            } else if (str3.equalsIgnoreCase(Constants.EFFECTS_CMDS[3])) {
                P8 p8 = new P8();
                if (arrayList.size() > 1) {
                    try {
                        p8.setSaturation(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e14) {
                        e14.printStackTrace();
                    }
                }
                return p8;
            } else if (str3.equalsIgnoreCase(Constants.EFFECTS_CMDS[4])) {
                P13 p13 = new P13();
                if (arrayList.size() > 1) {
                    try {
                        p13.setIntensity(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e15) {
                        e15.printStackTrace();
                    }
                }
                if (arrayList.size() > 2) {
                    try {
                        p13.setColorRed(Float.parseFloat((String) arrayList.get(2)), Float.parseFloat((String) arrayList.get(3)), Float.parseFloat((String) arrayList.get(4)));
                    } catch (Exception e16) {
                        e16.printStackTrace();
                    }
                }
                return p13;
            } else if (str3.equalsIgnoreCase(Constants.EFFECTS_CMDS[5])) {
                PointF pointF = new PointF(0.5f, 0.5f);
                float[] fArr = new float[3];
                float f2 = 0.75f;
                    if (arrayList.size() > 1) {
                        pointF = new PointF(Float.parseFloat((String) arrayList.get(1)), Float.parseFloat((String) arrayList.get(2)));
                    }
                    if (arrayList.size() > 3) {
                        fArr[0] = Float.parseFloat((String) arrayList.get(3));
                        fArr[1] = Float.parseFloat((String) arrayList.get(4));
                        fArr[2] = Float.parseFloat((String) arrayList.get(5));
                    }
                    f = arrayList.size() > 6 ? Float.parseFloat((String) arrayList.get(6)) : 0.25f;
                    try {
                        if (arrayList.size() > 7) {
                            f2 = Float.parseFloat((String) arrayList.get(7));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new Fxxx(pointF, fArr, f, f2);
                    }
                return new Fxxx(pointF, fArr, f, f2);
            } else if (str3.equalsIgnoreCase(Constants.EFFECTS_CMDS[6])) {
                P7 p7 = new P7();
                try {
                    if (arrayList.size() > 1) {
                        p7.setOpacity(Float.parseFloat((String) arrayList.get(1)));
                    }
                } catch (Exception e19) {
                    e19.printStackTrace();
                }
                return p7;
            } else if (str3.equalsIgnoreCase(Constants.EFFECTS_CMDS[6])) {
                return new P4();
            }
        }
        return new ImageRender();
    }
}
