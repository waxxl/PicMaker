package com.yd.photoeditor.model;

import com.yd.photoeditor.config.ALog;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;
import com.yd.photoeditor.imageprocessing.filter.ImageFilterGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilterInfo extends ItemInfo {
    private static final String TAG = FilterInfo.class.getSimpleName();
    private String mCmd;
    private String[] mCommands;
    private ImageFilter mImageFilter;
    private Language[] mNames;
    private String mPackageFolder;
    private long mPackageId;
    private Map<String, ArrayList<String>> mParamsMap = new HashMap();

    public ImageFilter getImageFilter() {
        ImageFilter imageFilter = this.mImageFilter;
        if (imageFilter == null || !imageFilter.isInitialized()) {
            buildImageFilter();
        }
        return this.mImageFilter;
    }

    public void setLanguages(Language[] languageArr) {
        this.mNames = languageArr;
    }

    public Language[] getLanguages() {
        return this.mNames;
    }

    public void setCmd(String str) {
        this.mCmd = str;
    }

    public String getCmd() {
        return this.mCmd;
    }

    public void setPackageFolder(String str) {
        this.mPackageFolder = str;
    }

    public String getPackageFolder() {
        return this.mPackageFolder;
    }

    public void setPackageId(long j) {
        this.mPackageId = j;
    }

    public long getPackageId() {
        return this.mPackageId;
    }

    private void buildImageFilter() {
        ALog.d(TAG, "buildImageFilter, name=" + getTitle());
        String str = this.mCmd;
        if (str == null || str.length() < 1) {
            this.mImageFilter = new ImageFilter();
            return;
        }
        if (this.mCommands == null) {
            this.mCommands = this.mCmd.split(",");
        }
        ArrayList arrayList = new ArrayList();
        String[] strArr = this.mCommands;
        if (strArr != null) {
            for (String createFilter : strArr) {
                arrayList.add(createFilter(createFilter));
            }
        }
        if (arrayList.size() > 1) {
            this.mImageFilter = new ImageFilterGroup(arrayList);
        } else if (arrayList.size() > 0) {
            this.mImageFilter = (ImageFilter) arrayList.get(0);
        } else {
            this.mImageFilter = new ImageFilter();
        }
    }

    private ImageFilter createFilter(String r18) {
        /*
            r17 = this;
            r1 = r17
            r0 = r18
            java.util.Map<java.lang.String, java.util.ArrayList<java.lang.String>> r2 = r1.mParamsMap
            java.lang.Object r2 = r2.get(r0)
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            r3 = 0
            if (r2 != 0) goto L_0x003f
            java.lang.String r2 = r18.trim()
            java.lang.String r4 = "\\ "
            java.lang.String[] r2 = r2.split(r4)
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            if (r2 == 0) goto L_0x0039
            int r5 = r2.length
            if (r5 <= 0) goto L_0x0039
            int r5 = r2.length
            r6 = 0
        L_0x0025:
            if (r6 >= r5) goto L_0x0039
            r7 = r2[r6]
            java.lang.String r7 = r7.trim()
            int r8 = r7.length()
            if (r8 <= 0) goto L_0x0036
            r4.add(r7)
        L_0x0036:
            int r6 = r6 + 1
            goto L_0x0025
        L_0x0039:
            java.util.Map<java.lang.String, java.util.ArrayList<java.lang.String>> r2 = r1.mParamsMap
            r2.put(r0, r4)
            r2 = r4
        L_0x003f:
            int r0 = r2.size()
            if (r0 <= 0) goto L_0x056c
            java.lang.Object r0 = r2.get(r3)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r4 = "H"
            boolean r4 = r0.equalsIgnoreCase(r4)
            r5 = 1
            if (r4 == 0) goto L_0x0072
            dauroi.com.imageprocessing.filter.colour.HueFilter r3 = new dauroi.com.imageprocessing.filter.colour.HueFilter
            r3.<init>()
            int r0 = r2.size()
            if (r0 <= r5) goto L_0x0071
            java.lang.Object r0 = r2.get(r5)     // Catch:{ Exception -> 0x006d }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x006d }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x006d }
            r3.setHue(r0)     // Catch:{ Exception -> 0x006d }
            goto L_0x0071
        L_0x006d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0071:
            return r3
        L_0x0072:
            java.lang.String r4 = "S"
            boolean r4 = r0.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x0098
            dauroi.com.imageprocessing.filter.colour.SepiaFilter r3 = new dauroi.com.imageprocessing.filter.colour.SepiaFilter
            r3.<init>()
            int r0 = r2.size()
            if (r0 <= r5) goto L_0x0097
            java.lang.Object r0 = r2.get(r5)     // Catch:{ Exception -> 0x0093 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0093 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x0093 }
            r3.setIntensity(r0)     // Catch:{ Exception -> 0x0093 }
            goto L_0x0097
        L_0x0093:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0097:
            return r3
        L_0x0098:
            java.lang.String r4 = "E"
            boolean r4 = r0.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x00be
            dauroi.com.imageprocessing.filter.effect.EmbossFilter r3 = new dauroi.com.imageprocessing.filter.effect.EmbossFilter
            r3.<init>()
            int r0 = r2.size()
            if (r0 <= r5) goto L_0x00bd
            java.lang.Object r0 = r2.get(r5)     // Catch:{ Exception -> 0x00b9 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x00b9 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x00b9 }
            r3.setIntensity(r0)     // Catch:{ Exception -> 0x00b9 }
            goto L_0x00bd
        L_0x00b9:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00bd:
            return r3
        L_0x00be:
            java.lang.String r4 = "Sk"
            boolean r4 = r0.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x00e4
            dauroi.com.imageprocessing.filter.effect.SketchFilter r3 = new dauroi.com.imageprocessing.filter.effect.SketchFilter
            r3.<init>()
            int r0 = r2.size()
            if (r0 <= r5) goto L_0x00e3
            java.lang.Object r0 = r2.get(r5)     // Catch:{ Exception -> 0x00df }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x00df }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x00df }
            r3.setLineSize(r0)     // Catch:{ Exception -> 0x00df }
            goto L_0x00e3
        L_0x00df:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00e3:
            return r3
        L_0x00e4:
            java.lang.String r4 = "K"
            boolean r4 = r0.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x010a
            dauroi.com.imageprocessing.filter.effect.KuwaharaFilter r3 = new dauroi.com.imageprocessing.filter.effect.KuwaharaFilter
            r3.<init>()
            int r0 = r2.size()
            if (r0 <= r5) goto L_0x0109
            java.lang.Object r0 = r2.get(r5)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0105 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0105 }
            r3.setRadius(r0)     // Catch:{ Exception -> 0x0105 }
            goto L_0x0109
        L_0x0105:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0109:
            return r3
        L_0x010a:
            java.lang.String r4 = "D"
            boolean r4 = r0.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x0132
            int r0 = r2.size()
            if (r0 <= r5) goto L_0x012c
            java.lang.Object r0 = r2.get(r5)     // Catch:{ Exception -> 0x0128 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0128 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0128 }
            dauroi.com.imageprocessing.filter.effect.DilationFilter r2 = new dauroi.com.imageprocessing.filter.effect.DilationFilter     // Catch:{ Exception -> 0x0128 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x0128 }
            return r2
        L_0x0128:
            r0 = move-exception
            r0.printStackTrace()
        L_0x012c:
            dauroi.com.imageprocessing.filter.effect.DilationFilter r0 = new dauroi.com.imageprocessing.filter.effect.DilationFilter
            r0.<init>()
            return r0
        L_0x0132:
            java.lang.String r4 = "St"
            boolean r4 = r0.equalsIgnoreCase(r4)
            r6 = 5
            r7 = 4
            r8 = 3
            r9 = 2
            if (r4 == 0) goto L_0x01bc
            dauroi.com.imageprocessing.filter.effect.SmoothToonFilter r3 = new dauroi.com.imageprocessing.filter.effect.SmoothToonFilter
            r3.<init>()
            int r0 = r2.size()
            if (r0 <= r5) goto L_0x01bb
            java.lang.Object r0 = r2.get(r5)     // Catch:{ Exception -> 0x0157 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0157 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x0157 }
            r3.setQuantizationLevels(r0)     // Catch:{ Exception -> 0x0157 }
            goto L_0x015b
        L_0x0157:
            r0 = move-exception
            r0.printStackTrace()
        L_0x015b:
            int r0 = r2.size()
            if (r0 <= r9) goto L_0x0173
            java.lang.Object r0 = r2.get(r9)     // Catch:{ Exception -> 0x016f }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x016f }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x016f }
            r3.setThreshold(r0)     // Catch:{ Exception -> 0x016f }
            goto L_0x0173
        L_0x016f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0173:
            int r0 = r2.size()
            if (r0 <= r8) goto L_0x018b
            java.lang.Object r0 = r2.get(r8)     // Catch:{ Exception -> 0x0187 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0187 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x0187 }
            r3.setBlurSize(r0)     // Catch:{ Exception -> 0x0187 }
            goto L_0x018b
        L_0x0187:
            r0 = move-exception
            r0.printStackTrace()
        L_0x018b:
            int r0 = r2.size()
            if (r0 <= r7) goto L_0x01a3
            java.lang.Object r0 = r2.get(r7)     // Catch:{ Exception -> 0x019f }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x019f }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x019f }
            r3.setTexelWidth(r0)     // Catch:{ Exception -> 0x019f }
            goto L_0x01a3
        L_0x019f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01a3:
            int r0 = r2.size()
            if (r0 <= r6) goto L_0x01bb
            java.lang.Object r0 = r2.get(r6)     // Catch:{ Exception -> 0x01b7 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x01b7 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x01b7 }
            r3.setTexelHeight(r0)     // Catch:{ Exception -> 0x01b7 }
            goto L_0x01bb
        L_0x01b7:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01bb:
            return r3
        L_0x01bc:
            java.lang.String r4 = "Ha"
            boolean r4 = r0.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x01fa
            dauroi.com.imageprocessing.filter.effect.HalftoneFilter r3 = new dauroi.com.imageprocessing.filter.effect.HalftoneFilter
            r3.<init>()
            int r0 = r2.size()
            if (r0 <= r5) goto L_0x01f9
            java.lang.Object r0 = r2.get(r5)     // Catch:{ Exception -> 0x01dd }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x01dd }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x01dd }
            r3.setFractionalWidthOfAPixel(r0)     // Catch:{ Exception -> 0x01dd }
            goto L_0x01e1
        L_0x01dd:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01e1:
            int r0 = r2.size()
            if (r0 <= r9) goto L_0x01f9
            java.lang.Object r0 = r2.get(r9)     // Catch:{ Exception -> 0x01f5 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x01f5 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x01f5 }
            r3.setAspectRatio(r0)     // Catch:{ Exception -> 0x01f5 }
            goto L_0x01f9
        L_0x01f5:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01f9:
            return r3
        L_0x01fa:
            java.lang.String r4 = "Sat"
            boolean r4 = r0.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x0220
            dauroi.com.imageprocessing.filter.colour.SaturationFilter r3 = new dauroi.com.imageprocessing.filter.colour.SaturationFilter
            r3.<init>()
            int r0 = r2.size()
            if (r0 <= r5) goto L_0x021f
            java.lang.Object r0 = r2.get(r5)     // Catch:{ Exception -> 0x021b }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x021b }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x021b }
            r3.setSaturation(r0)     // Catch:{ Exception -> 0x021b }
            goto L_0x021f
        L_0x021b:
            r0 = move-exception
            r0.printStackTrace()
        L_0x021f:
            return r3
        L_0x0220:
            java.lang.String r4 = "M"
            boolean r4 = r0.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x0272
            dauroi.com.imageprocessing.filter.colour.MonochromeFilter r3 = new dauroi.com.imageprocessing.filter.colour.MonochromeFilter
            r3.<init>()
            int r0 = r2.size()
            if (r0 <= r5) goto L_0x0245
            java.lang.Object r0 = r2.get(r5)     // Catch:{ Exception -> 0x0241 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0241 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x0241 }
            r3.setIntensity(r0)     // Catch:{ Exception -> 0x0241 }
            goto L_0x0245
        L_0x0241:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0245:
            int r0 = r2.size()
            if (r0 <= r9) goto L_0x0271
            java.lang.Object r0 = r2.get(r9)     // Catch:{ Exception -> 0x026d }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x026d }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x026d }
            java.lang.Object r4 = r2.get(r8)     // Catch:{ Exception -> 0x026d }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x026d }
            float r4 = java.lang.Float.parseFloat(r4)     // Catch:{ Exception -> 0x026d }
            java.lang.Object r2 = r2.get(r7)     // Catch:{ Exception -> 0x026d }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x026d }
            float r2 = java.lang.Float.parseFloat(r2)     // Catch:{ Exception -> 0x026d }
            r3.setColorRed(r0, r4, r2)     // Catch:{ Exception -> 0x026d }
            goto L_0x0271
        L_0x026d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0271:
            return r3
        L_0x0272:
            java.lang.String r4 = "V"
            boolean r4 = r0.equalsIgnoreCase(r4)
            r10 = 7
            r11 = 6
            if (r4 == 0) goto L_0x0304
            android.graphics.PointF r4 = new android.graphics.PointF
            r0 = 1056964608(0x3f000000, float:0.5)
            r4.<init>(r0, r0)
            float[] r12 = new float[r8]
            r14 = 1061158912(0x3f400000, float:0.75)
            int r0 = r2.size()     // Catch:{ Exception -> 0x02f8 }
            if (r0 <= r5) goto L_0x02a7
            android.graphics.PointF r0 = new android.graphics.PointF     // Catch:{ Exception -> 0x02f8 }
            java.lang.Object r15 = r2.get(r5)     // Catch:{ Exception -> 0x02f8 }
            java.lang.String r15 = (java.lang.String) r15     // Catch:{ Exception -> 0x02f8 }
            float r15 = java.lang.Float.parseFloat(r15)     // Catch:{ Exception -> 0x02f8 }
            java.lang.Object r16 = r2.get(r9)     // Catch:{ Exception -> 0x02f8 }
            java.lang.String r16 = (java.lang.String) r16     // Catch:{ Exception -> 0x02f8 }
            float r13 = java.lang.Float.parseFloat(r16)     // Catch:{ Exception -> 0x02f8 }
            r0.<init>(r15, r13)     // Catch:{ Exception -> 0x02f8 }
            r4 = r0
        L_0x02a7:
            int r0 = r2.size()     // Catch:{ Exception -> 0x02f8 }
            if (r0 <= r8) goto L_0x02d1
            java.lang.Object r0 = r2.get(r8)     // Catch:{ Exception -> 0x02f8 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x02f8 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x02f8 }
            r12[r3] = r0     // Catch:{ Exception -> 0x02f8 }
            java.lang.Object r0 = r2.get(r7)     // Catch:{ Exception -> 0x02f8 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x02f8 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x02f8 }
            r12[r5] = r0     // Catch:{ Exception -> 0x02f8 }
            java.lang.Object r0 = r2.get(r6)     // Catch:{ Exception -> 0x02f8 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x02f8 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x02f8 }
            r12[r9] = r0     // Catch:{ Exception -> 0x02f8 }
        L_0x02d1:
            int r0 = r2.size()     // Catch:{ Exception -> 0x02f8 }
            if (r0 <= r11) goto L_0x02e3
            java.lang.Object r0 = r2.get(r11)     // Catch:{ Exception -> 0x02f8 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x02f8 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x02f8 }
            r13 = r0
            goto L_0x02e5
        L_0x02e3:
            r13 = 1048576000(0x3e800000, float:0.25)
        L_0x02e5:
            int r0 = r2.size()     // Catch:{ Exception -> 0x02f6 }
            if (r0 <= r10) goto L_0x02fe
            java.lang.Object r0 = r2.get(r10)     // Catch:{ Exception -> 0x02f6 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x02f6 }
            float r14 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x02f6 }
            goto L_0x02fe
        L_0x02f6:
            r0 = move-exception
            goto L_0x02fb
        L_0x02f8:
            r0 = move-exception
            r13 = 1048576000(0x3e800000, float:0.25)
        L_0x02fb:
            r0.printStackTrace()
        L_0x02fe:
            dauroi.com.imageprocessing.filter.effect.VignetteFilter r0 = new dauroi.com.imageprocessing.filter.effect.VignetteFilter
            r0.<init>(r4, r12, r13, r14)
            return r0
        L_0x0304:
            java.lang.String r4 = "O"
            boolean r4 = r0.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x032a
            dauroi.com.imageprocessing.filter.colour.OpacityFilter r3 = new dauroi.com.imageprocessing.filter.colour.OpacityFilter
            r3.<init>()
            int r0 = r2.size()     // Catch:{ Exception -> 0x0325 }
            if (r0 <= r5) goto L_0x0329
            java.lang.Object r0 = r2.get(r5)     // Catch:{ Exception -> 0x0325 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0325 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x0325 }
            r3.setOpacity(r0)     // Catch:{ Exception -> 0x0325 }
            goto L_0x0329
        L_0x0325:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0329:
            return r3
        L_0x032a:
            java.lang.String r4 = "T"
            boolean r4 = r0.equalsIgnoreCase(r4)
            java.lang.String r12 = "assets://"
            r13 = 9
            if (r4 == 0) goto L_0x0380
            dauroi.com.imageprocessing.filter.effect.ToneCurveFilter r3 = new dauroi.com.imageprocessing.filter.effect.ToneCurveFilter
            r3.<init>()
            int r0 = r2.size()
            if (r0 <= r5) goto L_0x037f
            java.lang.Object r0 = r2.get(r5)
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = r0.startsWith(r12)
            if (r0 == 0) goto L_0x036c
            android.content.Context r0 = com.yd.photoeditor.PhotoEditorApp.getAppContext()     // Catch:{ IOException -> 0x0367 }
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch:{ IOException -> 0x0367 }
            java.lang.Object r2 = r2.get(r5)     // Catch:{ IOException -> 0x0367 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ IOException -> 0x0367 }
            java.lang.String r2 = r2.substring(r13)     // Catch:{ IOException -> 0x0367 }
            java.io.InputStream r0 = r0.open(r2)     // Catch:{ IOException -> 0x0367 }
            r3.setFromCurveFileInputStream(r0)     // Catch:{ IOException -> 0x0367 }
            goto L_0x037f
        L_0x0367:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x037f
        L_0x036c:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x037b }
            java.lang.Object r2 = r2.get(r5)     // Catch:{ FileNotFoundException -> 0x037b }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ FileNotFoundException -> 0x037b }
            r0.<init>(r2)     // Catch:{ FileNotFoundException -> 0x037b }
            r3.setFromCurveFileInputStream(r0)     // Catch:{ FileNotFoundException -> 0x037b }
            goto L_0x037f
        L_0x037b:
            r0 = move-exception
            r0.printStackTrace()
        L_0x037f:
            return r3
        L_0x0380:
            java.lang.String r4 = "C"
            boolean r4 = r0.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x046c
            float[] r0 = new float[r7]     // Catch:{ Exception -> 0x0466 }
            float[] r4 = new float[r7]     // Catch:{ Exception -> 0x0466 }
            float[] r12 = new float[r7]     // Catch:{ Exception -> 0x0466 }
            float[] r14 = new float[r7]     // Catch:{ Exception -> 0x0466 }
            java.lang.Object r15 = r2.get(r5)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r15 = (java.lang.String) r15     // Catch:{ Exception -> 0x0466 }
            float r15 = java.lang.Float.parseFloat(r15)     // Catch:{ Exception -> 0x0466 }
            r0[r3] = r15     // Catch:{ Exception -> 0x0466 }
            java.lang.Object r15 = r2.get(r9)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r15 = (java.lang.String) r15     // Catch:{ Exception -> 0x0466 }
            float r15 = java.lang.Float.parseFloat(r15)     // Catch:{ Exception -> 0x0466 }
            r0[r5] = r15     // Catch:{ Exception -> 0x0466 }
            java.lang.Object r15 = r2.get(r8)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r15 = (java.lang.String) r15     // Catch:{ Exception -> 0x0466 }
            float r15 = java.lang.Float.parseFloat(r15)     // Catch:{ Exception -> 0x0466 }
            r0[r9] = r15     // Catch:{ Exception -> 0x0466 }
            java.lang.Object r7 = r2.get(r7)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x0466 }
            float r7 = java.lang.Float.parseFloat(r7)     // Catch:{ Exception -> 0x0466 }
            r0[r8] = r7     // Catch:{ Exception -> 0x0466 }
            java.lang.Object r6 = r2.get(r6)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0466 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ Exception -> 0x0466 }
            r4[r3] = r6     // Catch:{ Exception -> 0x0466 }
            java.lang.Object r6 = r2.get(r11)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0466 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ Exception -> 0x0466 }
            r4[r5] = r6     // Catch:{ Exception -> 0x0466 }
            java.lang.Object r6 = r2.get(r10)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0466 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ Exception -> 0x0466 }
            r4[r9] = r6     // Catch:{ Exception -> 0x0466 }
            r6 = 8
            java.lang.Object r6 = r2.get(r6)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0466 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ Exception -> 0x0466 }
            r4[r8] = r6     // Catch:{ Exception -> 0x0466 }
            java.lang.Object r6 = r2.get(r13)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0466 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ Exception -> 0x0466 }
            r12[r3] = r6     // Catch:{ Exception -> 0x0466 }
            r6 = 10
            java.lang.Object r6 = r2.get(r6)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0466 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ Exception -> 0x0466 }
            r12[r5] = r6     // Catch:{ Exception -> 0x0466 }
            r6 = 11
            java.lang.Object r6 = r2.get(r6)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0466 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ Exception -> 0x0466 }
            r12[r9] = r6     // Catch:{ Exception -> 0x0466 }
            r6 = 12
            java.lang.Object r6 = r2.get(r6)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0466 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ Exception -> 0x0466 }
            r12[r8] = r6     // Catch:{ Exception -> 0x0466 }
            r6 = 13
            java.lang.Object r6 = r2.get(r6)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0466 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ Exception -> 0x0466 }
            r14[r3] = r6     // Catch:{ Exception -> 0x0466 }
            r3 = 14
            java.lang.Object r3 = r2.get(r3)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Exception -> 0x0466 }
            float r3 = java.lang.Float.parseFloat(r3)     // Catch:{ Exception -> 0x0466 }
            r14[r5] = r3     // Catch:{ Exception -> 0x0466 }
            r3 = 15
            java.lang.Object r3 = r2.get(r3)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Exception -> 0x0466 }
            float r3 = java.lang.Float.parseFloat(r3)     // Catch:{ Exception -> 0x0466 }
            r14[r9] = r3     // Catch:{ Exception -> 0x0466 }
            r3 = 16
            java.lang.Object r2 = r2.get(r3)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x0466 }
            float r2 = java.lang.Float.parseFloat(r2)     // Catch:{ Exception -> 0x0466 }
            r14[r8] = r2     // Catch:{ Exception -> 0x0466 }
            dauroi.com.imageprocessing.filter.colour.ChannelLookupFilter r2 = new dauroi.com.imageprocessing.filter.colour.ChannelLookupFilter     // Catch:{ Exception -> 0x0466 }
            r2.<init>(r0, r4, r12, r14)     // Catch:{ Exception -> 0x0466 }
            return r2
        L_0x0466:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x056c
        L_0x046c:
            java.lang.String r3 = "G"
            boolean r3 = r0.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x047a
            dauroi.com.imageprocessing.filter.colour.GrayscaleFilter r0 = new dauroi.com.imageprocessing.filter.colour.GrayscaleFilter
            r0.<init>()
            return r0
        L_0x047a:
            java.lang.String r3 = "L1"
            boolean r4 = r0.equalsIgnoreCase(r3)
            java.lang.String r6 = "Bd"
            java.lang.String r7 = "Ba"
            java.lang.String r8 = "L2"
            if (r4 != 0) goto L_0x04a2
            boolean r4 = r0.equalsIgnoreCase(r8)
            if (r4 != 0) goto L_0x04a2
            boolean r4 = r0.equalsIgnoreCase(r7)
            if (r4 != 0) goto L_0x04a2
            java.lang.String r4 = "Bs"
            boolean r4 = r0.equalsIgnoreCase(r4)
            if (r4 != 0) goto L_0x04a2
            boolean r4 = r0.equalsIgnoreCase(r6)
            if (r4 == 0) goto L_0x056c
        L_0x04a2:
            boolean r3 = r0.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x04af
            dauroi.com.imageprocessing.filter.colour.OneDimenLookupFilter r0 = new dauroi.com.imageprocessing.filter.colour.OneDimenLookupFilter
            r0.<init>()
        L_0x04ad:
            r3 = r0
            goto L_0x04ff
        L_0x04af:
            boolean r3 = r0.equalsIgnoreCase(r8)
            if (r3 == 0) goto L_0x04bb
            dauroi.com.imageprocessing.filter.colour.LookupFilter r0 = new dauroi.com.imageprocessing.filter.colour.LookupFilter
            r0.<init>()
            goto L_0x04ad
        L_0x04bb:
            boolean r3 = r0.equalsIgnoreCase(r7)
            if (r3 == 0) goto L_0x04df
            dauroi.com.imageprocessing.filter.blend.AlphaBlendFilter r3 = new dauroi.com.imageprocessing.filter.blend.AlphaBlendFilter
            r3.<init>()
            int r0 = r2.size()
            if (r0 <= r9) goto L_0x04ff
            java.lang.Object r0 = r2.get(r9)     // Catch:{ Exception -> 0x04da }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x04da }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x04da }
            r3.setMix(r0)     // Catch:{ Exception -> 0x04da }
            goto L_0x04ff
        L_0x04da:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x04ff
        L_0x04df:
            boolean r3 = r0.equalsIgnoreCase(r6)
            if (r3 == 0) goto L_0x04eb
            dauroi.com.imageprocessing.filter.blend.DestinationOverBlendFilter r0 = new dauroi.com.imageprocessing.filter.blend.DestinationOverBlendFilter
            r0.<init>()
            goto L_0x04ad
        L_0x04eb:
            java.lang.String r3 = "Bo"
            boolean r0 = r0.equalsIgnoreCase(r3)
            if (r0 == 0) goto L_0x04f9
            dauroi.com.imageprocessing.filter.blend.SourceOverBlendFilter r0 = new dauroi.com.imageprocessing.filter.blend.SourceOverBlendFilter
            r0.<init>()
            goto L_0x04ad
        L_0x04f9:
            dauroi.com.imageprocessing.filter.blend.SoftLightBlendFilter r0 = new dauroi.com.imageprocessing.filter.blend.SoftLightBlendFilter
            r0.<init>()
            goto L_0x04ad
        L_0x04ff:
            r3.setRecycleBitmap(r5)
            int r0 = r2.size()
            if (r0 <= r5) goto L_0x056b
            java.lang.Object r0 = r2.get(r5)
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = r0.startsWith(r12)
            if (r0 == 0) goto L_0x0537
            android.content.Context r0 = com.yd.photoeditor.PhotoEditorApp.getAppContext()     // Catch:{ IOException -> 0x0532 }
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch:{ IOException -> 0x0532 }
            java.lang.Object r2 = r2.get(r5)     // Catch:{ IOException -> 0x0532 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ IOException -> 0x0532 }
            java.lang.String r2 = r2.substring(r13)     // Catch:{ IOException -> 0x0532 }
            java.io.InputStream r0 = r0.open(r2)     // Catch:{ IOException -> 0x0532 }
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeStream(r0)     // Catch:{ IOException -> 0x0532 }
            r3.setBitmap(r0)     // Catch:{ IOException -> 0x0532 }
            goto L_0x056b
        L_0x0532:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x056b
        L_0x0537:
            java.lang.String r0 = r1.mPackageFolder
            if (r0 == 0) goto L_0x056b
            int r0 = r0.length()
            if (r0 <= 0) goto L_0x056b
            java.lang.String r0 = com.yd.photoeditor.utils.Utils.FILTER_FOLDER     // Catch:{ Exception -> 0x0567 }
            java.lang.String r4 = "/"
            java.lang.String r0 = r0.concat(r4)     // Catch:{ Exception -> 0x0567 }
            java.lang.String r4 = r1.mPackageFolder     // Catch:{ Exception -> 0x0567 }
            java.lang.String r0 = r0.concat(r4)     // Catch:{ Exception -> 0x0567 }
            java.lang.String r4 = "/"
            java.lang.String r0 = r0.concat(r4)     // Catch:{ Exception -> 0x0567 }
            java.lang.Object r2 = r2.get(r5)     // Catch:{ Exception -> 0x0567 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x0567 }
            java.lang.String r0 = r0.concat(r2)     // Catch:{ Exception -> 0x0567 }
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeFile(r0)     // Catch:{ Exception -> 0x0567 }
            r3.setBitmap(r0)     // Catch:{ Exception -> 0x0567 }
            goto L_0x056b
        L_0x0567:
            r0 = move-exception
            r0.printStackTrace()
        L_0x056b:
            return r3
        L_0x056c:
            dauroi.com.imageprocessing.filter.ImageFilter r0 = new dauroi.com.imageprocessing.filter.ImageFilter
            r0.<init>()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yd.photoeditor.model.FilterInfo.createFilter(java.lang.String):dauroi.com.imageprocessing.filter.ImageFilter");
    }
}
