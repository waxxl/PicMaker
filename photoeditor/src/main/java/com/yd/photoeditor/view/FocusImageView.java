package com.yd.photoeditor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.yd.photoeditor.R;

public class FocusImageView extends ImageView {
    public static final int CIRCLE_FOCUS = 0;
    private static final int DRAG = 1;
    private static final int MIN_CIRCLE_RADIUS = 20;
    private static final int NONE = 0;
    public static final int NO_FOCUS = -1;
    public static final int RECTANGLE_FOCUS = 1;
    private static final int ZOOM = 2;
    private float mCircleRadius = 100.0f;
    private float mCircleX = 0.0f;
    private float mCircleY = 0.0f;
    private float mD = 0.0f;
    private boolean mDisplayFocus = true;
    private PointF mFirstA = new PointF();
    private PointF mFirstB = new PointF();
    private OnImageFocusListener mFocusListener;
    private float mFocusStrokeWidth = 1.0f;
    private int mFocusType = -1;
    private float[] mLastEvent = null;
    private float mLinearFocusRadius = 10.0f;
    private float mMaxLinearFocusRadius = 100.0f;
    private PointF mMid = new PointF();
    private float mMinLinearFocusRadius = 10.0f;
    private int mMode = 0;
    private float mNewRot = 0.0f;
    private float mOldDist = 1.0f;
    private Paint mPaint = new Paint();
    private PointF mPointA = new PointF();
    private PointF mPointB = new PointF();
    private PointF mSecondA = new PointF();
    private PointF mSecondB = new PointF();
    private PointF mStart = new PointF();

    public interface OnImageFocusListener {
        void onCircleFocus(float[] fArr, float f);

        void onLinearFocus(float[] fArr, float f);

        void onNoFocus();
    }

    public FocusImageView(Context context) {
        super(context);
        init();
    }

    public FocusImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public FocusImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.mMinLinearFocusRadius = getResources().getDimension(R.dimen.photo_editor_min_linear_focus_radius);
        this.mFocusStrokeWidth = getResources().getDimension(R.dimen.photo_editor_focus_stroke_width);
    }

    public void saveInstanceState(Bundle bundle) {
        bundle.putFloat("com.yd.photoeditor.view.FocusImageView.mFocusStrokeWidth", this.mFocusStrokeWidth);
        bundle.putFloat("com.yd.photoeditor.view.FocusImageView.mCircleRadius", this.mCircleRadius);
        bundle.putFloat("com.yd.photoeditor.view.FocusImageView.mCircleX", this.mCircleX);
        bundle.putFloat("com.yd.photoeditor.view.FocusImageView.mCircleY", this.mCircleY);
        bundle.putParcelable("com.yd.photoeditor.view.FocusImageView.mFirstA", this.mFirstA);
        bundle.putParcelable("com.yd.photoeditor.view.FocusImageView.mFirstB", this.mFirstB);
        bundle.putParcelable("com.yd.photoeditor.view.FocusImageView.mSecondA", this.mSecondA);
        bundle.putParcelable("com.yd.photoeditor.view.FocusImageView.mSecondB", this.mSecondB);
        bundle.putBoolean("com.yd.photoeditor.view.FocusImageView.mDisplayFocus", this.mDisplayFocus);
        bundle.putInt("com.yd.photoeditor.view.FocusImageView.mFocusType", this.mFocusType);
        bundle.putFloat("com.yd.photoeditor.view.FocusImageView.mMinLinearFocusRadius", this.mMinLinearFocusRadius);
        bundle.putFloat("com.yd.photoeditor.view.FocusImageView.mMaxLinearFocusRadius", this.mMaxLinearFocusRadius);
        bundle.putParcelable("com.yd.photoeditor.view.FocusImageView.mPointA", this.mPointA);
        bundle.putParcelable("com.yd.photoeditor.view.FocusImageView.mPointB", this.mPointB);
        bundle.putFloat("com.yd.photoeditor.view.FocusImageView.mLinearFocusRadius", this.mLinearFocusRadius);
        bundle.putInt("com.yd.photoeditor.view.FocusImageView.mMode", this.mMode);
        bundle.putParcelable("com.yd.photoeditor.view.FocusImageView.mStart", this.mStart);
        bundle.putParcelable("com.yd.photoeditor.view.FocusImageView.mMid", this.mMid);
        bundle.putFloat("com.yd.photoeditor.view.FocusImageView.mOldDist", this.mOldDist);
        bundle.putFloat("com.yd.photoeditor.view.FocusImageView.mD", this.mD);
        bundle.putFloat("com.yd.photoeditor.view.FocusImageView.mNewRof", this.mNewRot);
    }

    public void restoreInstanceState(Bundle bundle) {
        this.mFocusStrokeWidth = bundle.getFloat("com.yd.photoeditor.view.FocusImageView.mFocusStrokeWidth", this.mFocusStrokeWidth);
        this.mCircleRadius = bundle.getFloat("com.yd.photoeditor.view.FocusImageView.mCircleRadius", this.mCircleRadius);
        this.mCircleX = bundle.getFloat("com.yd.photoeditor.view.FocusImageView.mCircleX", this.mCircleX);
        this.mCircleY = bundle.getFloat("com.yd.photoeditor.view.FocusImageView.mCircleY", this.mCircleY);
        PointF pointF = (PointF) bundle.getParcelable("com.yd.photoeditor.view.FocusImageView.mFirstA");
        if (pointF != null) {
            this.mFirstA = pointF;
        }
        PointF pointF2 = (PointF) bundle.getParcelable("com.yd.photoeditor.view.FocusImageView.mFirstB");
        if (pointF2 != null) {
            this.mFirstB = pointF2;
        }
        PointF pointF3 = (PointF) bundle.getParcelable("com.yd.photoeditor.view.FocusImageView.mSecondA");
        if (pointF3 != null) {
            this.mSecondA = pointF3;
        }
        PointF pointF4 = (PointF) bundle.getParcelable("com.yd.photoeditor.view.FocusImageView.mSecondB");
        if (pointF4 != null) {
            this.mSecondB = pointF4;
        }
        this.mDisplayFocus = bundle.getBoolean("com.yd.photoeditor.view.FocusImageView.mDisplayFocus", this.mDisplayFocus);
        this.mFocusType = bundle.getInt("com.yd.photoeditor.view.FocusImageView.mFocusType", this.mFocusType);
        this.mMinLinearFocusRadius = bundle.getFloat("com.yd.photoeditor.view.FocusImageView.mMinLinearFocusRadius", this.mMinLinearFocusRadius);
        this.mMaxLinearFocusRadius = bundle.getFloat("com.yd.photoeditor.view.FocusImageView.mMaxLinearFocusRadius", this.mMaxLinearFocusRadius);
        PointF pointF5 = (PointF) bundle.getParcelable("com.yd.photoeditor.view.FocusImageView.mPointA");
        if (pointF5 != null) {
            this.mPointA = pointF5;
        }
        PointF pointF6 = (PointF) bundle.getParcelable("com.yd.photoeditor.view.FocusImageView.mPointB");
        if (pointF6 != null) {
            this.mPointB = pointF6;
        }
        this.mLinearFocusRadius = bundle.getFloat("com.yd.photoeditor.view.FocusImageView.mLinearFocusRadius", this.mLinearFocusRadius);
        this.mMode = bundle.getInt("com.yd.photoeditor.view.FocusImageView.mMode", this.mMode);
        PointF pointF7 = (PointF) bundle.getParcelable("com.yd.photoeditor.view.FocusImageView.mStart");
        if (pointF7 != null) {
            this.mStart = pointF7;
        }
        PointF pointF8 = (PointF) bundle.getParcelable("com.yd.photoeditor.view.FocusImageView.mMid");
        if (pointF8 != null) {
            this.mMid = pointF8;
        }
        this.mOldDist = bundle.getFloat("com.yd.photoeditor.view.FocusImageView.mOldDist", this.mOldDist);
        this.mD = bundle.getFloat("com.yd.photoeditor.view.FocusImageView.mD", this.mD);
        this.mNewRot = bundle.getFloat("com.yd.photoeditor.view.FocusImageView.mNewRof", this.mNewRot);
    }

    public void setDisplayFocus(boolean z) {
        this.mDisplayFocus = z;
        invalidate();
    }

    public void setFocusType(int i) {
        this.mFocusType = i;
        invalidate();
    }

    public int getFocusType() {
        return this.mFocusType;
    }

    public void setOnImageFocusListener(OnImageFocusListener onImageFocusListener) {
        this.mFocusListener = onImageFocusListener;
    }

    public float[] getCircle() {
        return new float[]{this.mCircleX, this.mCircleY, this.mCircleRadius};
    }

    public void setCircle(float f, float f2, float f3) {
        this.mCircleX = f;
        this.mCircleY = f2;
        this.mCircleRadius = f3;
    }

    public void setupFocusInfos(int i, int i2) {
        this.mCircleX = (float) (i / 2);
        float f = (float) (i2 / 2);
        this.mCircleY = f;
        this.mCircleRadius = (float) Math.min(i / 8, i2 / 8);
        PointF pointF = this.mPointA;
        pointF.x = 0.0f;
        pointF.y = f;
        PointF pointF2 = this.mPointB;
        float f2 = (float) i;
        pointF2.x = f2;
        pointF2.y = f;
        float f3 = (float) i2;
        this.mLinearFocusRadius = f3 / 6.0f;
        this.mMaxLinearFocusRadius = ((float) Math.sqrt((double) ((i * i) + (i2 * i2)))) + 2.0f;
        calculateLinearFocusLines(f2, f3);
        this.mFocusType = 0;
        invalidate();
    }

    public void circleFocus() {
        float[] fArr = {this.mCircleX, this.mCircleY};
        invalidate();
        OnImageFocusListener onImageFocusListener = this.mFocusListener;
        if (onImageFocusListener != null) {
            onImageFocusListener.onCircleFocus(fArr, this.mCircleRadius);
        }
    }

    public void noFocus() {
        invalidate();
        OnImageFocusListener onImageFocusListener = this.mFocusListener;
        if (onImageFocusListener != null) {
            onImageFocusListener.onNoFocus();
        }
    }

    public void linearFocus() {
        calculateLinearFocusLines((float) getWidth(), (float) getHeight());
        PointF pointF = new PointF();
        pointF.x = this.mPointA.x;
        pointF.y = ((float) getHeight()) - this.mPointA.y;
        PointF pointF2 = new PointF();
        pointF2.x = this.mPointB.x;
        pointF2.y = ((float) getHeight()) - this.mPointB.y;
        float[] coefficients = new Line(pointF, pointF2).getCoefficients();
        invalidate();
        OnImageFocusListener onImageFocusListener = this.mFocusListener;
        if (onImageFocusListener != null) {
            onImageFocusListener.onLinearFocus(coefficients, this.mLinearFocusRadius);
        }
    }

    public float[] getLinearFocusInfos(float f, float f2, float f3) {
        int height = getHeight();
        PointF pointF = new PointF();
        pointF.x = this.mPointA.x * f;
        float f4 = (float) height;
        pointF.y = (f4 - this.mPointA.y) * f;
        PointF pointF2 = new PointF();
        pointF2.x = this.mPointB.x * f;
        pointF2.y = (f4 - this.mPointB.y) * f;
        float[] coefficients = new Line(pointF, pointF2).getCoefficients();
        return new float[]{coefficients[0], coefficients[1], (coefficients[0] * f2 * f) + (coefficients[1] * f3 * f) + coefficients[2], this.mLinearFocusRadius * f};
    }

    public float[] getLinearFocusInfos(int i, int i2) {
        PointF pointF = new PointF();
        pointF.x = this.mPointA.x;
        float f = (float) i2;
        pointF.y = f - this.mPointA.y;
        PointF pointF2 = new PointF();
        pointF2.x = this.mPointB.x;
        pointF2.y = f - this.mPointB.y;
        float[] coefficients = new Line(pointF, pointF2).getCoefficients();
        return new float[]{coefficients[0], coefficients[1], coefficients[2], this.mLinearFocusRadius};
    }

    public PointF[] getLinearFocusLine() {
        return new PointF[]{new PointF(this.mPointA.x, this.mPointA.y), new PointF(this.mPointB.x, this.mPointB.y)};
    }

    public float getLinearFocusRadius() {
        return this.mLinearFocusRadius;
    }

    private void calculateLinearFocusLines(float f, float f2) {
        Line[] findBesideLines = findBesideLines(new Line(this.mPointA, this.mPointB), this.mLinearFocusRadius);
        Line line = findBesideLines[0];
        Line line2 = findBesideLines[1];
        float[] coefficients = line.getCoefficients();
        float[] coefficients2 = line2.getCoefficients();
        PointF pointF = this.mFirstA;
        pointF.x = 0.0f;
        pointF.y = 0.0f;
        PointF pointF2 = this.mFirstB;
        pointF2.x = 0.0f;
        pointF2.y = 0.0f;
        if (coefficients[0] != 0.0f) {
            pointF.x = (-coefficients[2]) / coefficients[0];
            pointF.y = 0.0f;
            pointF2.y = f2;
            pointF2.x = (-(coefficients[2] + (pointF2.y * coefficients[1]))) / coefficients[0];
        } else if (coefficients[1] != 0.0f) {
            pointF.x = 0.0f;
            pointF.y = (-coefficients[2]) / coefficients[1];
            pointF2.x = f;
            pointF2.y = (-(coefficients[2] + (pointF2.x * coefficients[0]))) / coefficients[1];
        }
        PointF pointF3 = this.mSecondA;
        pointF3.x = 0.0f;
        pointF3.y = 0.0f;
        PointF pointF4 = this.mSecondB;
        pointF4.x = 0.0f;
        pointF4.y = 0.0f;
        if (coefficients2[0] != 0.0f) {
            pointF3.x = (-coefficients2[2]) / coefficients2[0];
            pointF3.y = 0.0f;
            pointF4.y = f2;
            pointF4.x = (-(coefficients2[2] + (pointF4.y * coefficients2[1]))) / coefficients2[0];
        } else if (coefficients2[1] != 0.0f) {
            pointF3.x = 0.0f;
            pointF3.y = (-coefficients2[2]) / coefficients2[1];
            pointF4.x = f;
            pointF4.y = (-(coefficients2[2] + (pointF4.x * coefficients2[0]))) / coefficients2[1];
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDisplayFocus) {
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth(this.mFocusStrokeWidth);
            this.mPaint.setColor(-1);
            this.mPaint.setAntiAlias(true);
            int i = this.mFocusType;
            if (i == 0) {
                canvas.drawCircle(this.mCircleX, this.mCircleY, this.mCircleRadius, this.mPaint);
            } else if (i == 1) {
                Canvas canvas2 = canvas;
                canvas2.drawLine(this.mFirstA.x, this.mFirstA.y, this.mFirstB.x, this.mFirstB.y, this.mPaint);
                canvas2.drawLine(this.mSecondA.x, this.mSecondA.y, this.mSecondB.x, this.mSecondB.y, this.mPaint);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent r10) {
        /*
            r9 = this;
            super.onTouchEvent(r10)
            int r0 = r9.mFocusType
            r1 = 1
            r2 = -1
            if (r0 != r2) goto L_0x000a
            return r1
        L_0x000a:
            int r0 = r10.getAction()
            r0 = r0 & 255(0xff, float:3.57E-43)
            r2 = 0
            if (r0 == 0) goto L_0x01b0
            r3 = 0
            if (r0 == r1) goto L_0x01a6
            r4 = 1092616192(0x41200000, float:10.0)
            r5 = 2
            if (r0 == r5) goto L_0x0064
            r6 = 5
            if (r0 == r6) goto L_0x0023
            r10 = 6
            if (r0 == r10) goto L_0x01ab
            goto L_0x01c3
        L_0x0023:
            float r0 = r9.spacing(r10)
            r9.mOldDist = r0
            float r0 = r9.mOldDist
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x01c3
            android.graphics.PointF r0 = r9.mMid
            r9.midPoint(r0, r10)
            r9.mMode = r5
            r0 = 4
            float[] r0 = new float[r0]
            r9.mLastEvent = r0
            float[] r0 = r9.mLastEvent
            float r2 = r10.getX(r3)
            r0[r3] = r2
            float[] r0 = r9.mLastEvent
            float r2 = r10.getX(r1)
            r0[r1] = r2
            float[] r0 = r9.mLastEvent
            float r2 = r10.getY(r3)
            r0[r5] = r2
            float[] r0 = r9.mLastEvent
            r2 = 3
            float r3 = r10.getY(r1)
            r0[r2] = r3
            float r10 = r9.rotation(r10)
            r9.mD = r10
            goto L_0x01c3
        L_0x0064:
            int r0 = r9.mMode
            if (r0 != r1) goto L_0x00b7
            float r0 = r10.getX()
            android.graphics.PointF r2 = r9.mStart
            float r2 = r2.x
            float r0 = r0 - r2
            float r2 = r10.getY()
            android.graphics.PointF r3 = r9.mStart
            float r3 = r3.y
            float r2 = r2 - r3
            int r3 = r9.mFocusType
            if (r3 != 0) goto L_0x0089
            float r3 = r9.mCircleX
            float r3 = r3 + r0
            r9.mCircleX = r3
            float r0 = r9.mCircleY
            float r0 = r0 + r2
            r9.mCircleY = r0
            goto L_0x00a5
        L_0x0089:
            android.graphics.PointF r3 = r9.mPointA
            float r4 = r3.x
            float r4 = r4 + r0
            r3.x = r4
            android.graphics.PointF r3 = r9.mPointA
            float r4 = r3.y
            float r4 = r4 + r2
            r3.y = r4
            android.graphics.PointF r3 = r9.mPointB
            float r4 = r3.x
            float r4 = r4 + r0
            r3.x = r4
            android.graphics.PointF r0 = r9.mPointB
            float r3 = r0.y
            float r3 = r3 + r2
            r0.y = r3
        L_0x00a5:
            android.graphics.PointF r0 = r9.mStart
            float r2 = r10.getX()
            r0.x = r2
            android.graphics.PointF r0 = r9.mStart
            float r10 = r10.getY()
            r0.y = r10
            goto L_0x0184
        L_0x00b7:
            if (r0 != r5) goto L_0x0184
            int r0 = r10.getPointerCount()
            if (r0 != r5) goto L_0x0184
            float r0 = r9.spacing(r10)
            android.graphics.Matrix r2 = new android.graphics.Matrix
            r2.<init>()
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x0184
            int r4 = r9.mFocusType
            if (r4 != 0) goto L_0x00f3
            float r4 = r9.mCircleRadius
            float r6 = r9.mOldDist
            float r6 = r0 - r6
            float r4 = r4 + r6
            r6 = 1101004800(0x41a00000, float:20.0)
            int r7 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r7 >= 0) goto L_0x00df
            r4 = 1101004800(0x41a00000, float:20.0)
        L_0x00df:
            int r6 = r9.getWidth()
            int r7 = r9.getHeight()
            int r6 = java.lang.Math.max(r6, r7)
            float r6 = (float) r6
            float r4 = java.lang.Math.min(r6, r4)
            r9.mCircleRadius = r4
            goto L_0x0126
        L_0x00f3:
            if (r4 != r1) goto L_0x0126
            float r4 = r9.mOldDist
            float r6 = r0 / r4
            float r4 = r0 / r4
            int r7 = r9.getWidth()
            int r7 = r7 / r5
            float r7 = (float) r7
            int r8 = r9.getHeight()
            int r8 = r8 / r5
            float r8 = (float) r8
            r2.postScale(r6, r4, r7, r8)
            float r4 = r9.mLinearFocusRadius
            float r6 = r9.mOldDist
            float r6 = r0 - r6
            float r4 = r4 + r6
            r9.mLinearFocusRadius = r4
            float r4 = r9.mLinearFocusRadius
            float r6 = r9.mMinLinearFocusRadius
            int r7 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r7 >= 0) goto L_0x011e
            r9.mLinearFocusRadius = r6
            goto L_0x0126
        L_0x011e:
            float r6 = r9.mMaxLinearFocusRadius
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x0126
            r9.mLinearFocusRadius = r6
        L_0x0126:
            float[] r4 = r9.mLastEvent
            if (r4 == 0) goto L_0x0144
            float r10 = r9.rotation(r10)
            r9.mNewRot = r10
            float r10 = r9.mNewRot
            float r4 = r9.mD
            float r10 = r10 - r4
            int r4 = r9.getWidth()
            int r4 = r4 / r5
            float r4 = (float) r4
            int r6 = r9.getHeight()
            int r6 = r6 / r5
            float r6 = (float) r6
            r2.postRotate(r10, r4, r6)
        L_0x0144:
            float r10 = r9.mNewRot
            r9.mD = r10
            r9.mOldDist = r0
            int r10 = r9.mFocusType
            if (r10 != r1) goto L_0x0184
            float[] r10 = new float[r5]
            float[] r0 = new float[r5]
            android.graphics.PointF r4 = r9.mPointA
            float r4 = r4.x
            r10[r3] = r4
            android.graphics.PointF r4 = r9.mPointA
            float r4 = r4.y
            r10[r1] = r4
            r2.mapPoints(r0, r10)
            android.graphics.PointF r4 = r9.mPointA
            r5 = r0[r3]
            r4.x = r5
            r5 = r0[r1]
            r4.y = r5
            android.graphics.PointF r4 = r9.mPointB
            float r4 = r4.x
            r10[r3] = r4
            android.graphics.PointF r4 = r9.mPointB
            float r4 = r4.y
            r10[r1] = r4
            r2.mapPoints(r0, r10)
            android.graphics.PointF r10 = r9.mPointB
            r2 = r0[r3]
            r10.x = r2
            r0 = r0[r1]
            r10.y = r0
        L_0x0184:
            int r10 = r9.mFocusType
            if (r10 != r1) goto L_0x0195
            int r10 = r9.getWidth()
            float r10 = (float) r10
            int r0 = r9.getHeight()
            float r0 = (float) r0
            r9.calculateLinearFocusLines(r10, r0)
        L_0x0195:
            int r10 = r9.mFocusType
            if (r10 != 0) goto L_0x019d
            r9.circleFocus()
            goto L_0x01a2
        L_0x019d:
            if (r10 != r1) goto L_0x01a2
            r9.linearFocus()
        L_0x01a2:
            r9.invalidate()
            goto L_0x01c3
        L_0x01a6:
            r9.mDisplayFocus = r3
            r9.invalidate()
        L_0x01ab:
            r9.mMode = r3
            r9.mLastEvent = r2
            goto L_0x01c3
        L_0x01b0:
            android.graphics.PointF r0 = r9.mStart
            float r3 = r10.getX()
            float r10 = r10.getY()
            r0.set(r3, r10)
            r9.mMode = r1
            r9.mLastEvent = r2
            r9.mDisplayFocus = r1
        L_0x01c3:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yd.photoeditor.view.FocusImageView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private float rotation(MotionEvent motionEvent) {
        return (float) Math.toDegrees(Math.atan2((double) (motionEvent.getY(0) - motionEvent.getY(1)), (double) (motionEvent.getX(0) - motionEvent.getX(1))));
    }

    private float spacing(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    private void midPoint(PointF pointF, MotionEvent motionEvent) {
        pointF.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
    }

    private Line[] findBesideLines(Line line, float f) {
        float[] coefficients = line.getCoefficients();
        double d = (double) f;
        double sqrt = Math.sqrt((double) ((coefficients[0] * coefficients[0]) + (coefficients[1] * coefficients[1])));
        Double.isNaN(d);
        double d2 = d * sqrt;
        double d3 = (double) coefficients[2];
        Double.isNaN(d3);
        double d4 = (double) coefficients[2];
        Double.isNaN(d4);
        Line[] lineArr = new Line[2];
        lineArr[0] = new Line();
        lineArr[0].a = coefficients[0];
        lineArr[0].b = coefficients[1];
        lineArr[0].c = (float) (d3 - d2);
        lineArr[1] = new Line();
        lineArr[1].a = coefficients[0];
        lineArr[1].b = coefficients[1];
        lineArr[1].c = (float) (d4 + d2);
        return lineArr;
    }

    public static class Line {
        float a = 0.0f;
        float b = 0.0f;
        float c = 0.0f;
        PointF first = new PointF();
        PointF second = new PointF();

        public Line() {
        }

        public Line(PointF pointF, PointF pointF2) {
            this.first = pointF;
            this.second = pointF2;
            findGeneralEquation();
        }

        public void findGeneralEquation() {
            this.a = this.second.y - this.first.y;
            this.b = this.first.x - this.second.x;
            this.c = (this.first.y * this.second.x) - (this.first.x * this.second.y);
        }

        public float[] getCoefficients() {
            return new float[]{this.a, this.b, this.c};
        }

        public float getValues(PointF pointF) {
            return (this.a * pointF.x) + (this.b * pointF.y) + this.c;
        }
    }

    /* access modifiers changed from: protected */
    public int findIntersectedPoint(Line line, Line line2, PointF pointF) {
        float f = line.a;
        float f2 = line.b;
        float f3 = line.c;
        float f4 = line2.a;
        float f5 = line2.b;
        float f6 = line2.c;
        float f7 = (f * f5) - (f4 * f2);
        float f8 = (f2 * f6) - (f5 * f3);
        float f9 = (f3 * f4) - (f * f6);
        if (f7 == 0.0f) {
            return (f7 == 0.0f && f8 == 0.0f && f9 == 0.0f) ? -1 : 1;
        }
        pointF.x = f8 / f7;
        pointF.y = f9 / f7;
        return 0;
    }
}
