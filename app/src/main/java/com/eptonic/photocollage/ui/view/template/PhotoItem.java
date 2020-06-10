package com.eptonic.photocollage.ui.view.template;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.HashMap;

public class PhotoItem {
    public static final int CORNER_METHOD_3_13 = 2;
    public static final int CORNER_METHOD_3_6 = 1;
    public static final int CORNER_METHOD_DEFAULT = 0;
    public static final int SHRINK_METHOD_3_3 = 1;
    public static final int SHRINK_METHOD_3_6 = 3;
    public static final int SHRINK_METHOD_3_8 = 4;
    public static final int SHRINK_METHOD_COMMON = 5;
    public static final int SHRINK_METHOD_DEFAULT = 0;
    public static final int SHRINK_METHOD_USING_MAP = 2;
    public RectF bound = new RectF();
    public boolean centerInClearBound = false;
    public ArrayList<PointF> clearAreaPoints;
    public Path clearPath = null;
    public boolean clearPathAlignParentRight = false;
    public boolean clearPathInCenterHorizontal = false;
    public boolean clearPathInCenterVertical = false;
    public RectF clearPathRatioBound = null;
    public float clearPathScaleRatio = 1.0f;
    public int cornerMethod = 0;
    public boolean disableShrink = false;
    public boolean fitBound = false;
    public boolean hasBackground = false;
    public String imagePath;
    public int index = 0;
    public String maskPath;
    public Path path = null;
    public boolean pathAlignParentRight = false;
    public boolean pathInCenterHorizontal = false;
    public boolean pathInCenterVertical = false;
    public RectF pathRatioBound = null;
    public float pathScaleRatio = 1.0f;
    public ArrayList<PointF> pointList = new ArrayList<>();
    public HashMap<PointF, PointF> shrinkMap;
    public int shrinkMethod = 0;

    /* renamed from: x */
    public float x = 0.0f;

    /* renamed from: y */
    public float y = 0.0f;
}
