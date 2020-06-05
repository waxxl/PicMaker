package com.yd.photoeditor.imageprocessing.filter.effect;

import android.graphics.Point;
import android.graphics.PointF;
import android.opengl.GLES20;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class ToneCurveFilter extends ImageFilter {
    public static final String TONE_CURVE_FRAGMENT_SHADER = "precision highp float;\n varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D toneCurveTexture;\n\n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     lowp float redCurveValue = texture2D(toneCurveTexture, vec2(textureColor.r, 0.0)).r;\n     lowp float greenCurveValue = texture2D(toneCurveTexture, vec2(textureColor.g, 0.0)).g;\n     lowp float blueCurveValue = texture2D(toneCurveTexture, vec2(textureColor.b, 0.0)).b;\n\n     gl_FragColor = vec4(redCurveValue, greenCurveValue, blueCurveValue, textureColor.a);\n }";
    private PointF[] mBlueControlPoints;
    /* access modifiers changed from: private */
    public ArrayList<Float> mBlueCurve;
    private PointF[] mGreenControlPoints;
    /* access modifiers changed from: private */
    public ArrayList<Float> mGreenCurve;
    private PointF[] mRedControlPoints;
    /* access modifiers changed from: private */
    public ArrayList<Float> mRedCurve;
    private PointF[] mRgbCompositeControlPoints;
    /* access modifiers changed from: private */
    public ArrayList<Float> mRgbCompositeCurve;
    /* access modifiers changed from: private */
    public int[] mToneCurveTexture = {-1};
    private int mToneCurveTextureUniformLocation;

    public ToneCurveFilter() {
        super(ImageFilter.NO_FILTER_VERTEX_SHADER, TONE_CURVE_FRAGMENT_SHADER);
        PointF[] pointFArr = {new PointF(0.0f, 0.0f), new PointF(0.5f, 0.5f), new PointF(1.0f, 1.0f)};
        this.mRgbCompositeControlPoints = pointFArr;
        this.mRedControlPoints = pointFArr;
        this.mGreenControlPoints = pointFArr;
        this.mBlueControlPoints = pointFArr;
    }

    public void onInit() {
        super.onInit();
        this.mToneCurveTextureUniformLocation = GLES20.glGetUniformLocation(getProgram(), "toneCurveTexture");
        GLES20.glActiveTexture(33987);
        GLES20.glGenTextures(1, this.mToneCurveTexture, 0);
        GLES20.glBindTexture(3553, this.mToneCurveTexture[0]);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
    }

    public void onInitialized() {
        super.onInitialized();
        setRgbCompositeControlPoints(this.mRgbCompositeControlPoints);
        setRedControlPoints(this.mRedControlPoints);
        setGreenControlPoints(this.mGreenControlPoints);
        setBlueControlPoints(this.mBlueControlPoints);
    }

    /* access modifiers changed from: protected */
    public void onDrawArraysPre() {
        if (this.mToneCurveTexture[0] != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, this.mToneCurveTexture[0]);
            GLES20.glUniform1i(this.mToneCurveTextureUniformLocation, 3);
        }
    }

    public void setFromCurveFileInputStream(InputStream inputStream) {
        try {
            short readShort = readShort(inputStream);
            short readShort2 = readShort(inputStream);
            PrintStream printStream = System.out;
            printStream.println("version=" + readShort + ", totalCurves=" + readShort2);
            ArrayList arrayList = new ArrayList(readShort2);
            for (int i = 0; i < readShort2; i++) {
                int readShort3 = readShort(inputStream);
                PointF[] pointFArr = new PointF[readShort3];
                for (int i2 = 0; i2 < readShort3; i2++) {
                    pointFArr[i2] = new PointF(((float) readShort(inputStream)) * 0.003921569f, ((float) readShort(inputStream)) * 0.003921569f);
                }
                arrayList.add(pointFArr);
            }
            inputStream.close();
            this.mRgbCompositeControlPoints = (PointF[]) arrayList.get(0);
            this.mRedControlPoints = (PointF[]) arrayList.get(1);
            this.mGreenControlPoints = (PointF[]) arrayList.get(2);
            this.mBlueControlPoints = (PointF[]) arrayList.get(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private short readShort(InputStream inputStream) throws IOException {
        return (short) (inputStream.read() | (inputStream.read() << 8));
    }

    public void setRgbCompositeControlPoints(PointF[] pointFArr) {
        this.mRgbCompositeControlPoints = pointFArr;
        this.mRgbCompositeCurve = createSplineCurve(this.mRgbCompositeControlPoints);
        updateToneCurveTexture();
    }

    public void setRedControlPoints(PointF[] pointFArr) {
        this.mRedControlPoints = pointFArr;
        this.mRedCurve = createSplineCurve(this.mRedControlPoints);
        updateToneCurveTexture();
    }

    public void setGreenControlPoints(PointF[] pointFArr) {
        this.mGreenControlPoints = pointFArr;
        this.mGreenCurve = createSplineCurve(this.mGreenControlPoints);
        updateToneCurveTexture();
    }

    public void setBlueControlPoints(PointF[] pointFArr) {
        this.mBlueControlPoints = pointFArr;
        this.mBlueCurve = createSplineCurve(this.mBlueControlPoints);
        updateToneCurveTexture();
    }

    private void updateToneCurveTexture() {
        runOnDraw(new Runnable() {
            public void run() {
                GLES20.glActiveTexture(33987);
                GLES20.glBindTexture(3553, ToneCurveFilter.this.mToneCurveTexture[0]);
                if (ToneCurveFilter.this.mRedCurve.size() >= 256 && ToneCurveFilter.this.mGreenCurve.size() >= 256 && ToneCurveFilter.this.mBlueCurve.size() >= 256 && ToneCurveFilter.this.mRgbCompositeCurve.size() >= 256) {
                    byte[] bArr = new byte[1024];
                    for (int i = 0; i < 256; i++) {
                        int i2 = i * 4;
                        float f = (float) i;
                        bArr[i2] = (byte) (((int) Math.min(Math.max(ToneCurveFilter.this.mBlueCurve.get(i).floatValue() + f + ToneCurveFilter.this.mRgbCompositeCurve.get(i).floatValue(), 0.0f), 255.0f)) & 255);
                        bArr[i2 + 1] = (byte) (((int) Math.min(Math.max(ToneCurveFilter.this.mGreenCurve.get(i).floatValue() + f + ToneCurveFilter.this.mRgbCompositeCurve.get(i).floatValue(), 0.0f), 255.0f)) & 255);
                        bArr[i2 + 2] = (byte) (((int) Math.min(Math.max(f + ToneCurveFilter.this.mRedCurve.get(i).floatValue() + ToneCurveFilter.this.mRgbCompositeCurve.get(i).floatValue(), 0.0f), 255.0f)) & 255);
                        bArr[i2 + 3] = -1;
                    }
                    GLES20.glTexImage2D(3553, 0, 6408, 256, 1, 0, 6408, 5121, ByteBuffer.wrap(bArr));
                }
            }
        });
    }

    private ArrayList<Float> createSplineCurve(PointF[] pointFArr) {
        if (pointFArr == null || pointFArr.length <= 0) {
            return null;
        }
        PointF[] pointFArr2 = pointFArr.clone();
        Arrays.sort(pointFArr2, new Comparator<PointF>() {
            public int compare(PointF pointF, PointF pointF2) {
                if (pointF.x < pointF2.x) {
                    return -1;
                }
                return pointF.x > pointF2.x ? 1 : 0;
            }
        });
        Point[] pointArr = new Point[pointFArr2.length];
        for (int i = 0; i < pointFArr.length; i++) {
            PointF pointF = pointFArr2[i];
            pointArr[i] = new Point((int) (pointF.x * 255.0f), (int) (pointF.y * 255.0f));
        }
        ArrayList<Point> createSplineCurve2 = createSplineCurve2(pointArr);
        Point point = createSplineCurve2.get(0);
        if (point.x > 0) {
            for (int i2 = point.x; i2 >= 0; i2--) {
                createSplineCurve2.add(0, new Point(i2, 0));
            }
        }
        Point point2 = createSplineCurve2.get(createSplineCurve2.size() - 1);
        if (point2.x < 255) {
            int i3 = point2.x;
            while (true) {
                i3++;
                if (i3 > 255) {
                    break;
                }
                createSplineCurve2.add(new Point(i3, 255));
            }
        }
        ArrayList<Float> arrayList = new ArrayList<>(createSplineCurve2.size());
        Iterator<Point> it = createSplineCurve2.iterator();
        while (it.hasNext()) {
            Point next = it.next();
            Point point3 = new Point(next.x, next.x);
            float sqrt = (float) Math.sqrt(Math.pow(point3.x - next.x, 2.0d) + Math.pow(point3.y - next.y, 2.0d));
            if (point3.y > next.y) {
                sqrt = -sqrt;
            }
            arrayList.add(Float.valueOf(sqrt));
        }
        return arrayList;
    }

    private ArrayList<Point> createSplineCurve2(Point[] pointArr) {
        Point[] pointArr2 = pointArr;
        ArrayList<Double> createSecondDerivative = createSecondDerivative(pointArr);
        int size = createSecondDerivative.size();
        if (size < 1) {
            return null;
        }
        double[] dArr = new double[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            dArr[i2] = createSecondDerivative.get(i2).doubleValue();
        }
        ArrayList<Point> arrayList = new ArrayList<>(size + 1);
        while (i < size - 1) {
            Point point = pointArr2[i];
            int i3 = i + 1;
            Point point2 = pointArr2[i3];
            int i4 = point.x;
            while (i4 < point2.x) {
                double d = i4 - point.x;
                double d2 = point2.x - point.x;
                Double.isNaN(d);
                Double.isNaN(d2);
                double d3 = d / d2;
                double d4 = 1.0d - d3;
                double d5 = point2.x - point.x;
                int i5 = size;
                double d6 = point.y;
                Double.isNaN(d6);
                Point point3 = point;
                ArrayList<Point> arrayList2 = arrayList;
                double d7 = point2.y;
                Double.isNaN(d7);
                Double.isNaN(d5);
                Double.isNaN(d5);
                double d8 = (d6 * d4) + (d7 * d3) + (((d5 * d5) / 6.0d) * (((((d4 * d4) * d4) - d4) * dArr[i]) + ((((d3 * d3) * d3) - d3) * dArr[i3])));
                double d9 = 0.0d;
                if (d8 > 255.0d) {
                    d9 = 255.0d;
                } else if (d8 >= 0.0d) {
                    d9 = d8;
                }
                ArrayList<Point> arrayList3 = arrayList2;
                arrayList3.add(new Point(i4, (int) Math.round(d9)));
                i4++;
                arrayList = arrayList3;
                size = i5;
                point = point3;
                Point[] pointArr3 = pointArr;
            }
            pointArr2 = pointArr;
            i = i3;
        }
        ArrayList<Point> arrayList4 = arrayList;
        if (arrayList4.size() == 255) {
            Point[] pointArr4 = pointArr;
            arrayList4.add(pointArr4[pointArr4.length - 1]);
        }
        return arrayList4;
    }

    private ArrayList<Double> createSecondDerivative(Point[] pointArr) {
        int i;
        Point[] pointArr2 = pointArr;
        int length = pointArr2.length;
        if (length <= 1) {
            return null;
        }
        double[][] dArr = (double[][]) Array.newInstance(double.class, new int[]{length, 3});
        double[] dArr2 = new double[length];
        char c = 0;
        dArr[0][1] = 1.0d;
        double d = 0.0d;
        dArr[0][0] = 0.0d;
        dArr[0][2] = 0.0d;
        int i2 = 1;
        while (true) {
            i = length - 1;
            if (i2 >= i) {
                break;
            }
            Point point = pointArr2[i2 - 1];
            Point point2 = pointArr2[i2];
            int i3 = i2 + 1;
            Point point3 = pointArr2[i3];
            double[] dArr3 = dArr[i2];
            double d2 = point2.x - point.x;
            Double.isNaN(d2);
            dArr3[c] = d2 / 6.0d;
            double[] dArr4 = dArr[i2];
            double d3 = point3.x - point.x;
            Double.isNaN(d3);
            dArr4[1] = d3 / 3.0d;
            double[] dArr5 = dArr[i2];
            double d4 = point3.x - point2.x;
            Double.isNaN(d4);
            dArr5[2] = d4 / 6.0d;
            double d5 = point3.y - point2.y;
            double d6 = point3.x - point2.x;
            Double.isNaN(d5);
            Double.isNaN(d6);
            double d7 = d5 / d6;
            double d8 = point2.y - point.y;
            double d9 = point2.x - point.x;
            Double.isNaN(d8);
            Double.isNaN(d9);
            dArr2[i2] = d7 - (d8 / d9);
            i2 = i3;
            c = 0;
            d = 0.0d;
        }
        double d10 = d;
        char c2 = 0;
        dArr2[0] = d10;
        dArr2[i] = d10;
        dArr[i][1] = 1.0d;
        dArr[i][0] = d10;
        dArr[i][2] = d10;
        int i4 = 1;
        while (i4 < length) {
            int i5 = i4 - 1;
            double d11 = dArr[i4][c2] / dArr[i5][1];
            double[] dArr6 = dArr[i4];
            dArr6[1] = dArr6[1] - (dArr[i5][2] * d11);
            dArr[i4][0] = 0.0d;
            dArr2[i4] = dArr2[i4] - (d11 * dArr2[i5]);
            i4++;
            c2 = 0;
        }
        for (int i6 = length - 2; i6 >= 0; i6--) {
            int i7 = i6 + 1;
            double d12 = dArr[i6][2] / dArr[i7][1];
            double[] dArr7 = dArr[i6];
            dArr7[1] = dArr7[1] - (dArr[i7][0] * d12);
            dArr[i6][2] = 0.0d;
            dArr2[i6] = dArr2[i6] - (d12 * dArr2[i7]);
        }
        ArrayList<Double> arrayList = new ArrayList<>(length);
        for (int i8 = 0; i8 < length; i8++) {
            arrayList.add(Double.valueOf(dArr2[i8] / dArr[i8][1]));
        }
        return arrayList;
    }
}
