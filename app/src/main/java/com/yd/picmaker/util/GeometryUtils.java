package com.yd.picmaker.util;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GeometryUtils {
    public static boolean isInCircle(PointF pointF, float f, PointF pointF2) {
        return Math.sqrt(((pointF.x - pointF2.x) * (pointF.x - pointF2.x)) + ((pointF.y - pointF2.y) * (pointF.y - pointF2.y))) <= ((double) f);
    }

    public static boolean contains(List<PointF> list, PointF pointF) {
        int size = list.size() - 1;
        boolean z = false;
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i).y > pointF.y) != (list.get(size).y > pointF.y) && pointF.x < (((list.get(size).x - list.get(i).x) * (pointF.y - list.get(i).y)) / (list.get(size).y - list.get(i).y)) + list.get(i).x) {
                z = !z;
            }
            size = i;
        }
        return z;
    }

    public static void createRectanglePath(Path path, float f, float f2, float f3) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PointF(0.0f, 0.0f));
        arrayList.add(new PointF(f, 0.0f));
        arrayList.add(new PointF(f, f2));
        arrayList.add(new PointF(0.0f, f2));
        createPathWithCircleCorner(path, arrayList, f3);
    }

    public static void createRegularPolygonPath(Path path, float f, int i, float f2) {
        float f3 = f / 2.0f;
        createRegularPolygonPath(path, f, f3, f3, i, f2);
    }

    public static void createRegularPolygonPath(Path path, float f, float f2, float f3, int i, float f4) {
        int i2 = i;
        double d = i2;
        Double.isNaN(d);
        float f5 = (float) (6.283185307179586d / d);
        ArrayList arrayList = new ArrayList();
        double d2 = f2;
        double d3 = f / 2.0f;
        double cos = Math.cos(0.0d);
        Double.isNaN(d3);
        Double.isNaN(d2);
        float f6 = (float) ((cos * d3) + d2);
        double d4 = f3;
        double sin = Math.sin(0.0d);
        Double.isNaN(d3);
        Double.isNaN(d4);
        arrayList.add(new PointF(f6, (float) ((sin * d3) + d4)));
        for (int i3 = 1; i3 < i2; i3++) {
            double d5 = ((float) i3) * f5;
            double cos2 = Math.cos(d5);
            Double.isNaN(d3);
            Double.isNaN(d2);
            double sin2 = Math.sin(d5);
            Double.isNaN(d3);
            Double.isNaN(d4);
            arrayList.add(new PointF((float) ((cos2 * d3) + d2), (float) ((sin2 * d3) + d4)));
        }
        Path path2 = path;
        createPathWithCircleCorner(path, arrayList, f4);
    }

    public static List<PointF> shrinkPathCollageUsingMap(List<PointF> list, float f, HashMap<PointF, PointF> hashMap) {
        ArrayList arrayList = new ArrayList();
        for (PointF next : list) {
            PointF pointF = hashMap.get(next);
            arrayList.add(new PointF(next.x + (pointF.x * f), next.y + (pointF.y * f)));
        }
        return arrayList;
    }

    public static List<PointF> shrinkPathCollage_3_3(List<PointF> list, int i, float f, RectF rectF) {
        PointF pointF;
        PointF pointF2;
        float f2;
        float f3;
        ArrayList arrayList = new ArrayList();
        PointF pointF3 = list.get(i);
        if (i > 0) {
            pointF = list.get(i - 1);
        } else {
            pointF = list.get(list.size() - 1);
        }
        if (i < list.size() - 1) {
            pointF2 = list.get(i + 1);
        } else {
            pointF2 = list.get(0);
        }
        for (PointF next : list) {
            PointF pointF4 = new PointF();
            if (rectF != null) {
                f3 = ((rectF.left != 0.0f || next.x >= pointF3.x) && (rectF.right != 1.0f || next.x < pointF3.x)) ? f : f * 2.0f;
                f2 = ((rectF.top != 0.0f || next.y >= pointF3.y) && (rectF.bottom != 1.0f || next.y < pointF3.y)) ? f : 2.0f * f;
            } else {
                f3 = f;
                f2 = f3;
            }
            if (pointF.x == pointF2.x) {
                if (pointF.x < pointF3.x) {
                    if (next.x <= pointF3.x) {
                        pointF4.x = next.x + f3;
                    } else {
                        pointF4.x = next.x - f3;
                    }
                } else if (next.x < pointF3.x) {
                    pointF4.x = next.x + f3;
                } else {
                    pointF4.x = next.x - f3;
                }
                if (next == pointF || next == pointF2 || next == pointF3) {
                    if (next != pointF && next != pointF2) {
                        pointF4.y = next.y;
                    } else if (next.y < pointF3.y) {
                        pointF4.y = next.y - f;
                    } else {
                        pointF4.y = next.y + f;
                    }
                } else if (next.y < pointF3.y) {
                    pointF4.y = next.y + f2;
                } else {
                    pointF4.y = next.y - f2;
                }
            }
            arrayList.add(pointF4);
        }
        return arrayList;
    }

    public static List<PointF> shrinkPath(List<PointF> list, float f, RectF rectF) {
        float f2;
        float f3;
        ArrayList arrayList = new ArrayList();
        if (f == 0.0f) {
            arrayList.addAll(list);
        } else {
            PointF pointF = new PointF(0.0f, 0.0f);
            for (PointF next : list) {
                pointF.x += next.x;
                pointF.y += next.y;
            }
            pointF.x /= (float) list.size();
            pointF.y /= (float) list.size();
            for (PointF next2 : list) {
                PointF pointF2 = new PointF();
                if (rectF != null) {
                    f3 = ((rectF.left != 0.0f || next2.x >= pointF.x) && (rectF.right != 1.0f || next2.x < pointF.x)) ? f : f * 2.0f;
                    f2 = ((rectF.top != 0.0f || next2.y >= pointF.y) && (rectF.bottom != 1.0f || next2.y < pointF.y)) ? f : 2.0f * f;
                } else {
                    f3 = f;
                    f2 = f3;
                }
                if (Math.abs(pointF.x - next2.x) < 1.0f) {
                    pointF2.x = next2.x;
                } else if (next2.x < pointF.x) {
                    pointF2.x = next2.x + f3;
                } else if (next2.x > pointF.x) {
                    pointF2.x = next2.x - f3;
                }
                if (Math.abs(pointF.y - next2.y) < 1.0f) {
                    pointF2.y = next2.y;
                } else if (next2.y < pointF.y) {
                    pointF2.y = next2.y + f2;
                } else if (next2.y > pointF.y) {
                    pointF2.y = next2.y - f2;
                }
                arrayList.add(pointF2);
            }
        }
        return arrayList;
    }

    public static List<PointF> commonShrinkPath(List<PointF> list, float f, Map<PointF, PointF> map) {
        boolean z;
        PointF pointF;
        PointF pointF2;
        ArrayList arrayList = new ArrayList();
        if (f == 0.0f) {
            arrayList.addAll(list);
        } else {
            ArrayList<PointF> jarvis = jarvis(list);
            for (int i = 0; i < list.size(); i++) {
                PointF pointF3 = list.get(i);
                Iterator<PointF> it = jarvis.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (pointF3 == it.next()) {
                            z = false;
                            break;
                        }
                    } else {
                        z = true;
                        break;
                    }
                }
                if (i == 0) {
                    pointF = list.get(list.size() - 1);
                } else {
                    pointF = list.get(i - 1);
                }
                if (i == list.size() - 1) {
                    pointF2 = list.get(0);
                } else {
                    pointF2 = list.get(i + 1);
                }
                PointF pointF4 = map.get(pointF3);
                PointF shrinkPoint = shrinkPoint(pointF3, pointF, pointF2, pointF4.x * f, pointF4.y * f, !z, !z);
                if (shrinkPoint != null) {
                    arrayList.add(shrinkPoint);
                } else {
                    arrayList.add(new PointF(0.0f, 0.0f));
                }
            }
        }
        return arrayList;
    }

    public static void createPathWithCubicCorner(Path path, List<PointF> list, float f) {
        Path path2 = path;
        List<PointF> list2 = list;
        float f2 = f;
        path.reset();
        for (int i = 0; i < list.size(); i++) {
            if (f2 != 0.0f && list.size() >= 3) {
                PointF pointF = new PointF(list.get(i).x, list.get(i).y);
                PointF pointF2 = new PointF();
                PointF pointF3 = new PointF();
                if (i == 0) {
                    pointF2.x = list.get(list.size() - 1).x;
                    pointF2.y = list.get(list.size() - 1).y;
                } else {
                    int i2 = i - 1;
                    pointF2.x = list.get(i2).x;
                    pointF2.y = list.get(i2).y;
                }
                if (i == list.size() - 1) {
                    pointF3.x = list.get(0).x;
                    pointF3.y = list.get(0).y;
                } else {
                    int i3 = i + 1;
                    pointF3.x = list.get(i3).x;
                    pointF3.y = list.get(i3).y;
                }
                double d = f2;
                PointF findPointOnSegment = findPointOnSegment(pointF, pointF2, d);
                PointF findPointOnSegment2 = findPointOnSegment(pointF, pointF3, d);
                PointF findMiddlePoint = findMiddlePoint(findPointOnSegment, findPointOnSegment2, pointF);
                if (i == 0) {
                    path.moveTo(findPointOnSegment.x, findPointOnSegment.y);
                } else {
                    path.lineTo(findPointOnSegment.x, findPointOnSegment.y);
                }
                path.cubicTo(findPointOnSegment.x, findPointOnSegment.y, findMiddlePoint.x, findMiddlePoint.y, findPointOnSegment2.x, findPointOnSegment2.y);
            } else if (i == 0) {
                path.moveTo(list.get(i).x, list.get(i).y);
            } else {
                path.lineTo(list.get(i).x, list.get(i).y);
            }
        }
    }

    private static boolean containPoint(List<PointF> list, PointF pointF) {
        for (PointF next : list) {
            if (next == pointF) {
                return true;
            }
            if (next.x == pointF.x && next.y == pointF.y) {
                return true;
            }
        }
        return false;
    }

    public static Map<PointF, PointF[]> createPathWithCircleCorner(Path path, List<PointF> list, List<PointF> list2, float f) {
        int i;
        boolean z;
        PointF[] pointFArr;
        Path path2 = path;
        List<PointF> list3 = list;
        List<PointF> list4 = list2;
        if (list3 == null || list.isEmpty()) {
            return null;
        }
        HashMap hashMap = new HashMap();
        path.reset();
        int i2 = 3;
        PointF[] pointFArr2 = {list3.get(0), list3.get(0), list3.get(0)};
        ArrayList<PointF> jarvis = jarvis(list);
        PointF[] pointFArr3 = pointFArr2;
        int i3 = 0;
        while (i3 < list.size()) {
            if (f == 0.0f || list.size() < i2) {
                i = i3;
                if (i == 0) {
                    path2.moveTo(list3.get(i).x, list3.get(i).y);
                } else {
                    path2.lineTo(list3.get(i).x, list3.get(i).y);
                }
            } else {
                if (!((list4 == null || list2.size() <= 0) || containPoint(list4, list3.get(i3)))) {
                    if (i3 == 0) {
                        path2.moveTo(list3.get(i3).x, list3.get(i3).y);
                    } else {
                        path2.lineTo(list3.get(i3).x, list3.get(i3).y);
                    }
                    if (i3 == list.size() - 1) {
                        path2.lineTo(pointFArr3[1].x, pointFArr3[1].y);
                    }
                    i = i3;
                } else {
                    Iterator<PointF> it = jarvis.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next() == list3.get(i3)) {
                                z = false;
                                break;
                            }
                        } else {
                            z = true;
                            break;
                        }
                    }
                    PointF pointF = new PointF(list3.get(i3).x, list3.get(i3).y);
                    PointF pointF2 = new PointF();
                    PointF pointF3 = new PointF();
                    if (i3 == 0) {
                        pointF2.x = list3.get(list.size() - 1).x;
                        pointF2.y = list3.get(list.size() - 1).y;
                    } else {
                        int i4 = i3 - 1;
                        pointF2.x = list3.get(i4).x;
                        pointF2.y = list3.get(i4).y;
                    }
                    if (i3 == list.size() - 1) {
                        pointF3.x = list3.get(0).x;
                        pointF3.y = list3.get(0).y;
                    } else {
                        int i5 = i3 + 1;
                        pointF3.x = list3.get(i5).x;
                        pointF3.y = list3.get(i5).y;
                    }
                    PointF[] pointFArr4 = new PointF[i2];
                    double[] dArr = new double[2];
                    double[] dArr2 = dArr;
                    PointF[] pointFArr5 = pointFArr4;
                    i = i3;
                    createArc(pointF, pointF2, pointF3, f, dArr, pointFArr4, z);
                    if (i == 0) {
                        pointFArr = pointFArr5;
                        path2.moveTo(pointFArr[1].x, pointFArr[1].y);
                    } else {
                        pointFArr = pointFArr5;
                        path2.lineTo(pointFArr[1].x, pointFArr[1].y);
                    }
                    path2.arcTo(new RectF(pointFArr[0].x - f, pointFArr[0].y - f, pointFArr[0].x + f, pointFArr[0].y + f), (float) dArr2[0], (float) dArr2[1], false);
                    if (i == 0) {
                        pointFArr3 = pointFArr;
                    }
                    if (i == list.size() - 1) {
                        path2.lineTo(pointFArr3[1].x, pointFArr3[1].y);
                    }
                    hashMap.put(list3.get(i), pointFArr);
                }
            }
            i3 = i + 1;
            i2 = 3;
        }
        return hashMap;
    }

    public static void createPathWithCircleCorner(Path path, List<PointF> list, float f) {
        boolean z;
        Path path2 = path;
        List<PointF> list2 = list;
        path.reset();
        ArrayList<PointF> jarvis = jarvis(list);
        PointF[] pointFArr = null;
        for (int i = 0; i < list.size(); i++) {
            if (f != 0.0f && list.size() >= 3) {
                Iterator<PointF> it = jarvis.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next() == list2.get(i)) {
                            z = false;
                            break;
                        }
                    } else {
                        z = true;
                        break;
                    }
                }
                PointF pointF = new PointF(list2.get(i).x, list2.get(i).y);
                PointF pointF2 = new PointF();
                PointF pointF3 = new PointF();
                if (i == 0) {
                    pointF2.x = list2.get(list.size() - 1).x;
                    pointF2.y = list2.get(list.size() - 1).y;
                } else {
                    int i2 = i - 1;
                    pointF2.x = list2.get(i2).x;
                    pointF2.y = list2.get(i2).y;
                }
                if (i == list.size() - 1) {
                    pointF3.x = list2.get(0).x;
                    pointF3.y = list2.get(0).y;
                } else {
                    int i3 = i + 1;
                    pointF3.x = list2.get(i3).x;
                    pointF3.y = list2.get(i3).y;
                }
                PointF[] pointFArr2 = new PointF[3];
                double[] dArr = new double[2];
                createArc(pointF, pointF2, pointF3, f, dArr, pointFArr2, z);
                if (i == 0) {
                    path2.moveTo(pointFArr2[1].x, pointFArr2[1].y);
                } else {
                    path2.lineTo(pointFArr2[1].x, pointFArr2[1].y);
                }
                path2.arcTo(new RectF(pointFArr2[0].x - f, pointFArr2[0].y - f, pointFArr2[0].x + f, pointFArr2[0].y + f), (float) dArr[0], (float) dArr[1], false);
                if (i != 0) {
                    pointFArr2 = pointFArr;
                }
                if (i == list.size() - 1) {
                    path2.lineTo(pointFArr2[1].x, pointFArr2[1].y);
                }
                pointFArr = pointFArr2;
            } else if (i == 0) {
                path2.moveTo(list2.get(i).x, list2.get(i).y);
            } else {
                path2.lineTo(list2.get(i).x, list2.get(i).y);
            }
        }
    }

    public static PointF findPointOnSegment(PointF pointF, PointF pointF2, double d) {
        if (d == 0.0d) {
            return new PointF(pointF.x, pointF.y);
        }
        PointF pointF3 = new PointF();
        double abs = Math.abs(pointF.x - pointF2.x);
        Double.isNaN(abs);
        double sqrt = (float) Math.sqrt(((pointF.x - pointF2.x) * (pointF.x - pointF2.x)) + ((pointF.y - pointF2.y) * (pointF.y - pointF2.y)));
        Double.isNaN(sqrt);
        double d2 = (abs * d) / sqrt;
        double abs2 = Math.abs(pointF.y - pointF2.y);
        Double.isNaN(abs2);
        Double.isNaN(sqrt);
        double d3 = (abs2 * d) / sqrt;
        if (pointF.x > pointF2.x) {
            double d4 = pointF.x;
            Double.isNaN(d4);
            pointF3.x = (float) (d4 - d2);
        } else {
            double d5 = pointF.x;
            Double.isNaN(d5);
            pointF3.x = (float) (d5 + d2);
        }
        if (pointF.y > pointF2.y) {
            double d6 = pointF.y;
            Double.isNaN(d6);
            pointF3.y = (float) (d6 - d3);
        } else {
            double d7 = pointF.y;
            Double.isNaN(d7);
            pointF3.y = (float) (d7 + d3);
        }
        return pointF3;
    }

    public static PointF findMiddlePoint(PointF pointF, PointF pointF2, PointF pointF3) {
        return findMiddlePoint(pointF, pointF2, (float) (Math.sqrt(((pointF.x - pointF2.x) * (pointF.x - pointF2.x)) + ((pointF.y - pointF2.y) * (pointF.y - pointF2.y))) / 2.0d), pointF3);
    }

    public static PointF findMiddlePoint(PointF pointF, PointF pointF2, float f, PointF pointF3) {
        float f2 = pointF2.y - pointF.y;
        float f3 = pointF.x - pointF2.x;
        float f4 = (pointF2.x * pointF.y) - (pointF.x * pointF2.y);
        PointF[] findMiddlePoint = findMiddlePoint(pointF, pointF2, f);
        if (((pointF3.x * f2) + (pointF3.y * f3) + f4) * ((f2 * findMiddlePoint[0].x) + (f3 * findMiddlePoint[0].y) + f4) > Float.MIN_VALUE) {
            return findMiddlePoint[0];
        }
        return findMiddlePoint[1];
    }

    public static boolean createArc(PointF pointF, PointF pointF2, PointF pointF3, float f, double[] dArr, PointF[] pointFArr, boolean z) {
        pointFArr[0] = findPointOnBisector(pointF, pointF2, pointF3, f);
        double sqrt = Math.sqrt((((pointF.x - pointFArr[0].x) * (pointF.x - pointFArr[0].x)) + ((pointF.y - pointFArr[0].y) * (pointF.y - pointFArr[0].y))) - (f * f));
        pointFArr[1] = findPointOnSegment(pointF, pointF2, sqrt);
        pointFArr[2] = findPointOnSegment(pointF, pointF3, sqrt);
        double sqrt2 = Math.sqrt(((pointF.x - pointFArr[0].x) * (pointF.x - pointFArr[0].x)) + ((pointF.y - pointFArr[0].y) * (pointF.y - pointFArr[0].y)));
        double d = f;
        Double.isNaN(d);
        double acos = Math.acos(d / sqrt2);
        double atan2 = Math.atan2(pointFArr[1].y - pointFArr[0].y, pointFArr[1].x - pointFArr[0].x);
        double atan22 = Math.atan2(pointFArr[2].y - pointFArr[0].y, pointFArr[2].x - pointFArr[0].x) - atan2;
        if (!z) {
            atan22 = acos * 2.0d;
        }
        dArr[0] = Math.toDegrees(atan2);
        dArr[1] = Math.toDegrees(atan22);
        double degrees = Math.toDegrees(acos * 2.0d);
        if (Math.abs(degrees - Math.abs(dArr[1])) > 1.0d) {
            dArr[1] = -degrees;
        }
        return false;
    }

    public static PointF findPointOnBisector(PointF pointF, PointF pointF2, PointF pointF3, float f) {
        PointF pointF4 = pointF2;
        PointF pointF5 = pointF3;
        float f2 = f;
        double[] coefficients = getCoefficients(pointF, pointF2);
        double[] coefficients2 = getCoefficients(pointF, pointF5);
        double d = coefficients2[0];
        double d2 = pointF4.x;
        Double.isNaN(d2);
        double d3 = coefficients2[1];
        double d4 = pointF4.y;
        Double.isNaN(d4);
        double d5 = (d * d2) + (d3 * d4) + coefficients2[2];
        double d6 = coefficients[0];
        double d7 = pointF5.x;
        Double.isNaN(d7);
        double d8 = d6 * d7;
        double d9 = coefficients[1];
        double d10 = pointF5.y;
        Double.isNaN(d10);
        double d11 = d8 + (d9 * d10) + coefficients[2];
        double sqrt = Math.sqrt((coefficients[0] * coefficients[0]) + (coefficients[1] * coefficients[1]));
        double sqrt2 = Math.sqrt((coefficients2[0] * coefficients2[0]) + (coefficients2[1] * coefficients2[1]));
        if (d11 > 0.0d) {
            if (d5 > 0.0d) {
                double d12 = coefficients[0];
                double d13 = coefficients[1];
                double d14 = f2;
                Double.isNaN(d14);
                double d15 = coefficients2[0];
                double d16 = coefficients2[1];
                Double.isNaN(d14);
                return findIntersectPoint(d12, d13, (sqrt * d14) - coefficients[2], d15, d16, (d14 * sqrt2) - coefficients2[2]);
            }
            double d17 = coefficients[0];
            double d18 = coefficients[1];
            double d19 = f2;
            Double.isNaN(d19);
            double d20 = (sqrt * d19) - coefficients[2];
            double d21 = d19;
            Double.isNaN(d21);
            return findIntersectPoint(d17, d18, d20, -coefficients2[0], -coefficients2[1], (sqrt2 * d21) + coefficients2[2]);
        } else if (d5 > 0.0d) {
            double d22 = f2;
            Double.isNaN(d22);
            double d23 = coefficients2[0];
            double d24 = coefficients2[1];
            Double.isNaN(d22);
            return findIntersectPoint(-coefficients[0], -coefficients[1], (sqrt * d22) + coefficients[2], d23, d24, (d22 * sqrt2) - coefficients2[2]);
        } else {
            double d25 = f2;
            Double.isNaN(d25);
            double d26 = (sqrt * d25) + coefficients[2];
            Double.isNaN(d25);
            return findIntersectPoint(-coefficients[0], -coefficients[1], d26, -coefficients2[0], -coefficients2[1], (d25 * sqrt2) + coefficients2[2]);
        }
    }

    public static double distanceToLine(double[] dArr, PointF pointF) {
        double sqrt = Math.sqrt((dArr[0] * dArr[0]) + (dArr[1] * dArr[1]));
        double d = dArr[0];
        double d2 = pointF.x;
        Double.isNaN(d2);
        double d3 = d * d2;
        double d4 = dArr[1];
        double d5 = pointF.y;
        Double.isNaN(d5);
        return Math.abs(((d3 + (d4 * d5)) + dArr[2]) / sqrt);
    }

    public static PointF shrinkPoint(PointF pointF, PointF pointF2, PointF pointF3, float f, float f2, boolean z, boolean z2) {
        float f3 = f;
        float f4 = f2;
        double[] coefficients = getCoefficients(pointF, pointF2);
        double[] coefficients2 = getCoefficients(pointF, pointF3);
        double d = f3;
        double sqrt = Math.sqrt((coefficients[0] * coefficients[0]) + (coefficients[1] * coefficients[1]));
        Double.isNaN(d);
        double d2 = (d * sqrt) - coefficients[2];
        double d3 = f4;
        double sqrt2 = Math.sqrt((coefficients2[0] * coefficients2[0]) + (coefficients2[1] * coefficients2[1]));
        Double.isNaN(d3);
        double d4 = (d3 * sqrt2) - coefficients2[2];
        double d5 = -f3;
        double sqrt3 = Math.sqrt((coefficients[0] * coefficients[0]) + (coefficients[1] * coefficients[1]));
        Double.isNaN(d5);
        double d6 = (d5 * sqrt3) - coefficients[2];
        double d7 = -f4;
        double sqrt4 = Math.sqrt((coefficients2[0] * coefficients2[0]) + (coefficients2[1] * coefficients2[1]));
        Double.isNaN(d7);
        double d8 = (d7 * sqrt4) - coefficients2[2];
        double d9 = d2;
        PointF findIntersectPoint = findIntersectPoint(coefficients[0], coefficients[1], d9, coefficients2[0], coefficients2[1], d4);
        PointF findIntersectPoint2 = findIntersectPoint(coefficients[0], coefficients[1], d9, coefficients2[0], coefficients2[1], d8);
        PointF findIntersectPoint3 = findIntersectPoint(coefficients[0], coefficients[1], d6, coefficients2[0], coefficients2[1], d4);
        PointF findIntersectPoint4 = findIntersectPoint(coefficients[0], coefficients[1], d6, coefficients2[0], coefficients2[1], d8);
        if (testShrunkPoint(coefficients, coefficients2, pointF2, pointF3, findIntersectPoint, z, z2)) {
            return findIntersectPoint;
        }
        if (testShrunkPoint(coefficients, coefficients2, pointF2, pointF3, findIntersectPoint2, z, z2)) {
            return findIntersectPoint2;
        }
        if (testShrunkPoint(coefficients, coefficients2, pointF2, pointF3, findIntersectPoint3, z, z2)) {
            return findIntersectPoint3;
        }
        if (testShrunkPoint(coefficients, coefficients2, pointF2, pointF3, findIntersectPoint4, z, z2)) {
            return findIntersectPoint4;
        }
        return null;
    }

    private static boolean testShrunkPoint(double[] dArr, double[] dArr2, PointF pointF, PointF pointF2, PointF pointF3, boolean z, boolean z2) {
        PointF pointF4 = pointF;
        PointF pointF5 = pointF2;
        PointF pointF6 = pointF3;
        if (pointF6 != null && pointF6.x < Float.MAX_VALUE && pointF6.y < Float.MAX_VALUE) {
            double d = dArr[0];
            double d2 = pointF6.x;
            Double.isNaN(d2);
            double d3 = dArr[1];
            double d4 = pointF6.y;
            Double.isNaN(d4);
            double d5 = (d * d2) + (d3 * d4) + dArr[2];
            double d6 = dArr[0];
            double d7 = pointF5.x;
            Double.isNaN(d7);
            double d8 = d6 * d7;
            double d9 = dArr[1];
            double d10 = pointF5.y;
            Double.isNaN(d10);
            double d11 = d5 * (d8 + (d9 * d10) + dArr[2]);
            double d12 = dArr2[0];
            double d13 = pointF6.x;
            Double.isNaN(d13);
            double d14 = d12 * d13;
            double d15 = dArr2[1];
            double d16 = pointF6.y;
            Double.isNaN(d16);
            double d17 = d14 + (d15 * d16) + dArr2[2];
            double d18 = dArr2[0];
            double d19 = pointF4.x;
            Double.isNaN(d19);
            double d20 = d18 * d19;
            double d21 = dArr2[1];
            double d22 = pointF4.y;
            Double.isNaN(d22);
            double d23 = d17 * (d20 + (d21 * d22) + dArr2[2]);
            boolean z3 = d11 > Double.MIN_VALUE;
            boolean z4 = z2;
            boolean z5 = d23 > Double.MIN_VALUE;
            return z3 == z4 && z5 == z;
        }
        return false;
    }

    public static PointF findIntersectPoint(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = (d * d5) - (d2 * d4);
        double d8 = (d5 * d3) - (d2 * d6);
        double d9 = (d * d6) - (d3 * d4);
        if (d7 == 0.0d && d8 == 0.0d) {
            return new PointF(Float.MAX_VALUE, Float.MAX_VALUE);
        }
        if (d7 != 0.0d || d8 == 0.0d) {
            return new PointF((float) (d8 / d7), (float) (d9 / d7));
        }
        return null;
    }

    public static double[] findBisector(PointF pointF, PointF pointF2, PointF pointF3) {
        PointF pointF4 = pointF2;
        PointF pointF5 = pointF3;
        double[] coefficients = getCoefficients(pointF, pointF2);
        double[] coefficients2 = getCoefficients(pointF, pointF5);
        double sqrt = Math.sqrt((coefficients[0] * coefficients[0]) + (coefficients[1] * coefficients[1]));
        double sqrt2 = Math.sqrt((coefficients2[0] * coefficients2[0]) + (coefficients2[1] * coefficients2[1]));
        double d = (coefficients[0] / sqrt) + (coefficients2[0] / sqrt2);
        double d2 = (coefficients[1] / sqrt) + (coefficients2[1] / sqrt2);
        double d3 = (coefficients[2] / sqrt) + (coefficients2[2] / sqrt2);
        double d4 = (coefficients[0] / sqrt) - (coefficients2[0] / sqrt2);
        double d5 = (coefficients[1] / sqrt) - (coefficients2[1] / sqrt2);
        double d6 = (coefficients[2] / sqrt) - (coefficients2[2] / sqrt2);
        double d7 = pointF4.x;
        Double.isNaN(d7);
        double d8 = pointF4.y;
        Double.isNaN(d8);
        double d9 = pointF5.x;
        Double.isNaN(d9);
        double d10 = pointF5.y;
        Double.isNaN(d10);
        if (((d7 * d) + (d8 * d2) + d3) * ((d9 * d) + (d10 * d2) + d3) > Double.MIN_VALUE) {
            return new double[]{d4, d5, d6};
        }
        return new double[]{d, d2, d3};
    }

    public static double[] getCoefficients(PointF pointF, PointF pointF2) {
        return new double[]{(double) (pointF2.y - pointF.y), (double) (pointF.x - pointF2.x), (double) ((pointF2.x * pointF.y) - (pointF.x * pointF2.y))};
    }

    public static PointF[] findMiddlePoint(PointF pointF, PointF pointF2, float f) {
        PointF[] pointFArr = new PointF[2];
        float f2 = pointF2.x - pointF.x;
        float f3 = pointF2.y - pointF.y;
        float f4 = (pointF2.x + pointF.x) / 2.0f;
        float f5 = (pointF2.y + pointF.y) / 2.0f;
        if (f2 == 0.0f) {
            pointFArr[0] = new PointF(pointF.x + f, f5);
            pointFArr[1] = new PointF(pointF.x - f, f5);
        } else if (f3 == 0.0f) {
            pointFArr[0] = new PointF(f4, pointF.y + f);
            pointFArr[1] = new PointF(f4, pointF.y - f);
        } else {
            double d = f;
            double sqrt = Math.sqrt(((f3 * f3) / (f2 * f2)) + 1.0f);
            Double.isNaN(d);
            float f6 = (float) (d / sqrt);
            float f7 = (f3 / f2) * f6;
            pointFArr[0] = new PointF(f4 - f7, f5 + f6);
            pointFArr[1] = new PointF(f4 + f7, f5 - f6);
        }
        return pointFArr;
    }

    public static boolean CCW(PointF pointF, PointF pointF2, PointF pointF3) {
        return ((((int) pointF2.y) - ((int) pointF.y)) * (((int) pointF3.x) - ((int) pointF2.x))) - ((((int) pointF2.x) - ((int) pointF.x)) * (((int) pointF3.y) - ((int) pointF2.y))) < 0;
    }

    public static ArrayList<PointF> jarvis(List<PointF> list) {
        ArrayList<PointF> arrayList = new ArrayList<>();
        int size = list.size();
        if (size < 3) {
            for (PointF add : list) {
                arrayList.add(add);
            }
            return arrayList;
        }
        int[] iArr = new int[size];
        Arrays.fill(iArr, -1);
        int i = 0;
        for (int i2 = 1; i2 < size; i2++) {
            if (((int) list.get(i2).x) < ((int) list.get(i).x)) {
                i = i2;
            }
        }
        int i3 = i;
        while (true) {
            int i4 = (i3 + 1) % size;
            for (int i5 = 0; i5 < size; i5++) {
                if (CCW(list.get(i3), list.get(i5), list.get(i4))) {
                    i4 = i5;
                }
            }
            iArr[i3] = i4;
            if (i4 == i) {
                break;
            }
            i3 = i4;
        }
        for (int i6 = 0; i6 < iArr.length; i6++) {
            if (iArr[i6] != -1) {
                arrayList.add(list.get(i6));
            }
        }
        return arrayList;
    }
}