package com.yd.photoeditor.model;

import android.graphics.PointF;

import com.yd.photoeditor.config.ALog;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;
import com.yd.photoeditor.imageprocessing.filter.ImageFilterGroup;
import com.yd.photoeditor.imageprocessing.filter.blend.AlphaBlendFilter;
import com.yd.photoeditor.imageprocessing.filter.colour.GrayscaleFilter;
import com.yd.photoeditor.imageprocessing.filter.colour.HueFilter;
import com.yd.photoeditor.imageprocessing.filter.colour.MonochromeFilter;
import com.yd.photoeditor.imageprocessing.filter.colour.OpacityFilter;
import com.yd.photoeditor.imageprocessing.filter.colour.SaturationFilter;
import com.yd.photoeditor.imageprocessing.filter.colour.SepiaFilter;
import com.yd.photoeditor.imageprocessing.filter.effect.DilationFilter;
import com.yd.photoeditor.imageprocessing.filter.effect.EmbossFilter;
import com.yd.photoeditor.imageprocessing.filter.effect.HalftoneFilter;
import com.yd.photoeditor.imageprocessing.filter.effect.KuwaharaFilter;
import com.yd.photoeditor.imageprocessing.filter.effect.SketchFilter;
import com.yd.photoeditor.imageprocessing.filter.effect.SmoothToonFilter;
import com.yd.photoeditor.imageprocessing.filter.effect.VignetteFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilterInfo extends ItemInfo {
    private static final String TAG = FilterInfo.class.getSimpleName();
    private String mCmd;
    private String[] mCommands;
    private ImageFilter mImageFilter;
    private String mPackageFolder;
    private long mPackageId;
    private final Map<String, ArrayList<String>> mParamsMap = new HashMap();

    public ImageFilter getImageFilter() {
        ImageFilter imageFilter = this.mImageFilter;
        if (imageFilter == null || !imageFilter.isInitialized()) {
            buildImageFilter();
        }
        return mImageFilter;
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
        ALog.d(TAG, "buildImageFilter, name=" + getTitle());
        if (mCmd == null || mCmd.length() < 1) {
            mImageFilter = new HueFilter();
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
            mImageFilter = new ImageFilterGroup(arrayList);
        } else if (arrayList.size() > 0) {
            mImageFilter = (ImageFilter) arrayList.get(0);
        } else {
            mImageFilter = new HueFilter();
        }
    }

    private ImageFilter createFilter(String str) {
        AlphaBlendFilter alphaBlendFilter;
        ImageFilter imageFilter;
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
            if (str3.equalsIgnoreCase("H")) {
                HueFilter hueFilter = new HueFilter();
                if (arrayList.size() > 1) {
                    try {
                        hueFilter.setHue(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return hueFilter;
            } else if (str3.equalsIgnoreCase("S")) {
                SepiaFilter sepiaFilter = new SepiaFilter();
                if (arrayList.size() > 1) {
                    try {
                        sepiaFilter.setIntensity(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                return sepiaFilter;
            } else if (str3.equalsIgnoreCase("E")) {
                EmbossFilter embossFilter = new EmbossFilter();
                if (arrayList.size() > 1) {
                    try {
                        embossFilter.setIntensity(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                return embossFilter;
            } else if (str3.equalsIgnoreCase("Sk")) {
                SketchFilter sketchFilter = new SketchFilter();
                if (arrayList.size() > 1) {
                    try {
                        sketchFilter.setLineSize(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
                return sketchFilter;
            } else if (str3.equalsIgnoreCase("K")) {
                KuwaharaFilter kuwaharaFilter = new KuwaharaFilter();
                if (arrayList.size() > 1) {
                    try {
                        kuwaharaFilter.setRadius(Integer.parseInt((String) arrayList.get(1)));
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                return kuwaharaFilter;
            } else if (str3.equalsIgnoreCase("D")) {
                if (arrayList.size() > 1) {
                    try {
                        return new DilationFilter(Integer.parseInt((String) arrayList.get(1)));
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
                return new DilationFilter();
            } else if (str3.equalsIgnoreCase("St")) {
                SmoothToonFilter smoothToonFilter = new SmoothToonFilter();
                if (arrayList.size() > 1) {
                    try {
                        smoothToonFilter.setQuantizationLevels(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                    if (arrayList.size() > 2) {
                        try {
                            smoothToonFilter.setThreshold(Float.parseFloat((String) arrayList.get(2)));
                        } catch (Exception e8) {
                            e8.printStackTrace();
                        }
                    }
                    if (arrayList.size() > 3) {
                        try {
                            smoothToonFilter.setBlurSize(Float.parseFloat((String) arrayList.get(3)));
                        } catch (Exception e9) {
                            e9.printStackTrace();
                        }
                    }
                    if (arrayList.size() > 4) {
                        try {
                            smoothToonFilter.setTexelWidth(Float.parseFloat((String) arrayList.get(4)));
                        } catch (Exception e10) {
                            e10.printStackTrace();
                        }
                    }
                    if (arrayList.size() > 5) {
                        try {
                            smoothToonFilter.setTexelHeight(Float.parseFloat((String) arrayList.get(5)));
                        } catch (Exception e11) {
                            e11.printStackTrace();
                        }
                    }
                }
                return smoothToonFilter;
            } else if (str3.equalsIgnoreCase("Ha")) {
                HalftoneFilter halftoneFilter = new HalftoneFilter();
                if (arrayList.size() > 1) {
                    try {
                        halftoneFilter.setFractionalWidthOfAPixel(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e12) {
                        e12.printStackTrace();
                    }
                    if (arrayList.size() > 2) {
                        try {
                            halftoneFilter.setAspectRatio(Float.parseFloat((String) arrayList.get(2)));
                        } catch (Exception e13) {
                            e13.printStackTrace();
                        }
                    }
                }
                return halftoneFilter;
            } else if (str3.equalsIgnoreCase("Sat")) {
                SaturationFilter saturationFilter = new SaturationFilter();
                if (arrayList.size() > 1) {
                    try {
                        saturationFilter.setSaturation(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e14) {
                        e14.printStackTrace();
                    }
                }
                return saturationFilter;
            } else if (str3.equalsIgnoreCase("M")) {
                MonochromeFilter monochromeFilter = new MonochromeFilter();
                if (arrayList.size() > 1) {
                    try {
                        monochromeFilter.setIntensity(Float.parseFloat((String) arrayList.get(1)));
                    } catch (Exception e15) {
                        e15.printStackTrace();
                    }
                }
                if (arrayList.size() > 2) {
                    try {
                        monochromeFilter.setColorRed(Float.parseFloat((String) arrayList.get(2)), Float.parseFloat((String) arrayList.get(3)), Float.parseFloat((String) arrayList.get(4)));
                    } catch (Exception e16) {
                        e16.printStackTrace();
                    }
                }
                return monochromeFilter;
            } else if (str3.equalsIgnoreCase("V")) {
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
                        return new VignetteFilter(pointF, fArr, f, f2);
                    }
                return new VignetteFilter(pointF, fArr, f, f2);
            } else if (str3.equalsIgnoreCase("O")) {
                OpacityFilter opacityFilter = new OpacityFilter();
                try {
                    if (arrayList.size() > 1) {
                        opacityFilter.setOpacity(Float.parseFloat((String) arrayList.get(1)));
                    }
                } catch (Exception e19) {
                    e19.printStackTrace();
                }
                return opacityFilter;
            } else if (str3.equalsIgnoreCase("G")) {
                return new GrayscaleFilter();
            }
        }
        return new ImageFilter();
    }
}
