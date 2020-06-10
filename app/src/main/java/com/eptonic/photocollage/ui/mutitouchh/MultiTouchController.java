package com.eptonic.photocollage.ui.mutitouchh;

import android.util.Log;
import android.view.MotionEvent;
import java.lang.reflect.Method;

public class MultiTouchController<T> {
    private static int ACTION_POINTER_INDEX_SHIFT = 8;
    private static int ACTION_POINTER_UP = 6;
    public static final boolean DEBUG = false;
    private static final long EVENT_SETTLE_TIME_INTERVAL = 20;
    private static final float MAX_MULTITOUCH_DIM_JUMP_SIZE = 40.0f;
    private static final float MAX_MULTITOUCH_POS_JUMP_SIZE = 30.0f;
    public static final int MAX_TOUCH_POINTS = 20;
    private static final float MIN_MULTITOUCH_SEPARATION = 30.0f;
    public static final int MODE_DRAG = 1;
    public static final int MODE_NOTHING = 0;
    public static final int MODE_PINCH = 2;
    public static final int MODE_ST_GRAB = 3;
    private static final float THRESHOLD = 3.0f;
    private static Method m_getHistoricalPressure;
    private static Method m_getHistoricalX;
    private static Method m_getHistoricalY;
    private static Method m_getPointerCount;
    private static Method m_getPointerId;
    private static Method m_getPressure;
    private static Method m_getX;
    private static Method m_getY;
    public static final boolean multiTouchSupported;
    private static final int[] pointerIds = new int[20];
    private static final float[] pressureVals = new float[20];
    private static final float[] xVals = new float[20];
    private static final float[] yVals = new float[20];
    private boolean handleSingleTouchEvents;
    private PointInfo mCurrPt;
    private float mCurrPtAng;
    private float mCurrPtDiam;
    private float mCurrPtHeight;
    private float mCurrPtWidth;
    private float mCurrPtX;
    private float mCurrPtY;
    private final PositionAndScale mCurrXform;
    private boolean mDragOccurred;
    private int mMode;
    private PointInfo mPrevPt;
    private long mSettleEndTime;
    private long mSettleStartTime;
    MultiTouchObjectCanvas<T> objectCanvas;
    private T selectedObject;
    private float startAngleMinusPinchAngle;
    private float startPosX;
    private float startPosY;
    private float startScaleOverPinchDiam;
    private float startScaleXOverPinchWidth;
    private float startScaleYOverPinchHeight;

    public interface MultiTouchObjectCanvas<T> {
        T getDraggableObjectAtPoint(PointInfo pointInfo);

        void getPositionAndScale(T t, PositionAndScale positionAndScale);

        boolean pointInObjectGrabArea(PointInfo pointInfo, T t);

        void selectObject(T t, PointInfo pointInfo);

        boolean setPositionAndScale(T t, PositionAndScale positionAndScale, PointInfo pointInfo);
    }

    private void extractCurrPtInfo() {
        float f;
        float f2;
        float f3;
        mCurrPtX = mCurrPt.getX();
        mCurrPtY = mCurrPt.getY();
        float f4 = 0.0f;
        if (!mCurrXform.updateScale) {
            f = 0.0f;
        } else {
            f = mCurrPt.getMultiTouchDiameter();
        }
        mCurrPtDiam = Math.max(21.3f, f);
        if (!mCurrXform.updateScaleXY) {
            f2 = 0.0f;
        } else {
            f2 = mCurrPt.getMultiTouchWidth();
        }
        mCurrPtWidth = Math.max(30.0f, f2);
        if (!mCurrXform.updateScaleXY) {
            f3 = 0.0f;
        } else {
            f3 = mCurrPt.getMultiTouchHeight();
        }
        mCurrPtHeight = Math.max(30.0f, f3);
        if (mCurrXform.updateAngle) {
            f4 = mCurrPt.getMultiTouchAngle();
        }
        mCurrPtAng = f4;
    }

    public MultiTouchController(MultiTouchObjectCanvas<T> multiTouchObjectCanvas) {
        this(multiTouchObjectCanvas, true);
    }

    public MultiTouchController(MultiTouchObjectCanvas<T> multiTouchObjectCanvas, boolean z) {
        selectedObject = null;
        mCurrXform = new PositionAndScale();
        mDragOccurred = false;
        mMode = 0;
        mCurrPt = new PointInfo();
        mPrevPt = new PointInfo();
        handleSingleTouchEvents = z;
        objectCanvas = multiTouchObjectCanvas;
    }

    public void setHandleSingleTouchEvents(boolean z) {
        handleSingleTouchEvents = z;
    }

    public boolean getHandleSingleTouchEvents() {
        return handleSingleTouchEvents;
    }

    public boolean dragOccurred() {
        return mDragOccurred;
    }

    static {
        boolean z = true;
        try {
            m_getPointerCount = MotionEvent.class.getMethod("getPointerCount");
            m_getPointerId = MotionEvent.class.getMethod("getPointerId", Integer.TYPE);
            m_getPressure = MotionEvent.class.getMethod("getPressure", Integer.TYPE);
            m_getHistoricalX = MotionEvent.class.getMethod("getHistoricalX", Integer.TYPE, Integer.TYPE);
            m_getHistoricalY = MotionEvent.class.getMethod("getHistoricalY", Integer.TYPE, Integer.TYPE);
            m_getHistoricalPressure = MotionEvent.class.getMethod("getHistoricalPressure", Integer.TYPE, Integer.TYPE);
            m_getX = MotionEvent.class.getMethod("getX", Integer.TYPE);
            m_getY = MotionEvent.class.getMethod("getY", Integer.TYPE);
        } catch (Exception e) {
            Log.e("MultiTouchController", "static initializer failed", e);
            z = false;
        }
        multiTouchSupported = z;
        if (multiTouchSupported) {
            try {
                ACTION_POINTER_UP = MotionEvent.class.getField("ACTION_POINTER_UP").getInt(null);
                ACTION_POINTER_INDEX_SHIFT = MotionEvent.class.getField("ACTION_POINTER_INDEX_SHIFT").getInt(null);
            } catch (Exception unused) {
            }
        }
    }


    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        long j = 0;
        float f;
        float f2;
        float f3;
        Object obj;
        Object obj2;
        Object obj3;
        MotionEvent motionEvent2 = motionEvent;
        try {
            int intValue = multiTouchSupported ? ((Integer) m_getPointerCount.invoke(motionEvent2, new Object[0])).intValue() : 1;
            if (mMode == 0 && !handleSingleTouchEvents && intValue == 1) {
                return false;
            }
            int action = motionEvent.getAction();
            int historySize = motionEvent.getHistorySize() / intValue;
            int i = 0;
            while (i <= historySize) {
                boolean z2 = i < historySize;
                if (multiTouchSupported) {
                    if (intValue != 1) {
                        int min = Math.min(intValue, 20);
                        for (int i2 = 0; i2 < min; i2++) {
                            pointerIds[i2] = ((Integer) m_getPointerId.invoke(motionEvent2, new Object[]{Integer.valueOf(i2)})).intValue();
                            float[] fArr = xVals;
                            if (z2) {
                                obj = m_getHistoricalX.invoke(motionEvent2, Integer.valueOf(i2), Integer.valueOf(i));
                            } else {
                                obj = m_getX.invoke(motionEvent2, Integer.valueOf(i2));
                            }
                            fArr[i2] = ((Float) obj).floatValue();
                            float[] fArr2 = yVals;
                            if (z2) {
                                obj2 = m_getHistoricalY.invoke(motionEvent2, Integer.valueOf(i2), Integer.valueOf(i));
                            } else {
                                obj2 = m_getY.invoke(motionEvent2, Integer.valueOf(i2));
                            }
                            fArr2[i2] = ((Float) obj2).floatValue();
                            float[] fArr3 = pressureVals;
                            if (z2) {
                                obj3 = m_getHistoricalPressure.invoke(motionEvent2, Integer.valueOf(i2), Integer.valueOf(i));
                            } else {
                                obj3 = m_getPressure.invoke(motionEvent2, Integer.valueOf(i2));
                            }
                            fArr3[i2] = ((Float) obj3).floatValue();
                        }
                        float[] fArr4 = xVals;
                        float[] fArr5 = yVals;
                        float[] fArr6 = pressureVals;
                        int[] iArr = pointerIds;
                        int i3 = !z2 ? 2 : action;
                        if (!z2) {
                            if (action == 1 || (((1 << ACTION_POINTER_INDEX_SHIFT) - 1) & action) == ACTION_POINTER_UP || action == 3) {
                                z = false;
                                if (z2) {
                                    j = motionEvent2.getHistoricalEventTime(i);
                                } else {
                                    j = motionEvent.getEventTime();
                                }
                                decodeTouchEvent(intValue, fArr4, fArr5, fArr6, iArr, i3, z, j);
                                i++;
                                historySize = historySize;
                            }
                        }
                        z = true;
                        decodeTouchEvent(intValue, fArr4, fArr5, fArr6, iArr, i3, z, j);
                        i++;
                        historySize = historySize;
                    }
                }
                float[] fArr7 = xVals;
                if (z2) {
                    f = motionEvent2.getHistoricalX(i);
                } else {
                    f = motionEvent.getX();
                }
                fArr7[0] = f;
                float[] fArr8 = yVals;
                if (z2) {
                    f2 = motionEvent2.getHistoricalY(i);
                } else {
                    f2 = motionEvent.getY();
                }
                fArr8[0] = f2;
                float[] fArr9 = pressureVals;
                if (z2) {
                    f3 = motionEvent2.getHistoricalPressure(i);
                } else {
                    f3 = motionEvent.getPressure();
                }
                fArr9[0] = f3;
                float[] fArr42 = xVals;
                float[] fArr52 = yVals;
                float[] fArr62 = pressureVals;
                int[] iArr2 = pointerIds;
                z = true;
                decodeTouchEvent(intValue, fArr42, fArr52, fArr62, iArr2, action, z, j);
                i++;
                historySize = historySize;
            }
            return this.selectedObject != null;
        } catch (Exception e) {
            Log.e("MultiTouchController", "onTouchEvent() failed", e);
            return false;
        }
    }

    private void decodeTouchEvent(int i, float[] fArr, float[] fArr2, float[] fArr3, int[] iArr, int i2, boolean z, long j) {
        PointInfo pointInfo = this.mPrevPt;
        this.mPrevPt = this.mCurrPt;
        this.mCurrPt = pointInfo;
        this.mCurrPt.set(i, fArr, fArr2, fArr3, iArr, i2, z, j);
        multiTouchController();
    }

    private void anchorAtThisPositionAndScale() {
        float f;
        T t = this.selectedObject;
        if (t != null) {
            this.objectCanvas.getPositionAndScale(t, this.mCurrXform);
            if (this.mCurrXform.updateScale && this.mCurrXform.scale != 0.0f) {
                f = this.mCurrXform.scale;
            } else {
                f = 1.0f;
            }
            float f2 = 1.0f / f;
            extractCurrPtInfo();
            this.startPosX = (this.mCurrPtX - this.mCurrXform.xOff) * f2;
            this.startPosY = (this.mCurrPtY - this.mCurrXform.yOff) * f2;
            this.startScaleOverPinchDiam = this.mCurrXform.scale / this.mCurrPtDiam;
            this.startScaleXOverPinchWidth = this.mCurrXform.scaleX / this.mCurrPtWidth;
            this.startScaleYOverPinchHeight = this.mCurrXform.scaleY / this.mCurrPtHeight;
            this.startAngleMinusPinchAngle = this.mCurrXform.angle - this.mCurrPtAng;
        }
    }

    private void performDragOrPinch() {
        float f;
        if (this.selectedObject != null) {
            float f2 = 1.0f;
            if (this.mCurrXform.updateScale && this.mCurrXform.scale != 0.0f) {
                f2 = this.mCurrXform.scale;
            }
            extractCurrPtInfo();
            float f3 = this.mCurrPtX - (this.startPosX * f2);
            float f4 = this.mCurrPtY - (this.startPosY * f2);
            float x = this.mCurrPt.getX() - this.mPrevPt.getX();
            float y = this.mCurrPt.getY() - this.mPrevPt.getY();
            float unused = this.mCurrXform.scale;
            if (this.mMode == 3) {
                if (x < 0.0f || y < 0.0f) {
                    f = this.mCurrXform.scale - 0.04f;
                } else {
                    f = this.mCurrXform.scale + 0.04f;
                }
                if (f < 0.35f) {
                    return;
                }
            } else {
                f = this.startScaleOverPinchDiam * this.mCurrPtDiam;
            }
            float f5 = f;
            if (this.mDragOccurred || pastThreshold(Math.abs(x), Math.abs(y), f5)) {
                this.mCurrXform.set(f3, f4, f5, this.startScaleXOverPinchWidth * this.mCurrPtWidth, this.startScaleYOverPinchHeight * this.mCurrPtHeight, this.startAngleMinusPinchAngle + this.mCurrPtAng);
                this.objectCanvas.setPositionAndScale(this.selectedObject, this.mCurrXform, this.mCurrPt);
                this.mDragOccurred = true;
            }
        }
    }

    private boolean pastThreshold(float f, float f2, float f3) {
        if (f >= THRESHOLD || f2 >= THRESHOLD || f3 != this.mCurrXform.scale) {
            this.mDragOccurred = true;
            return true;
        }
        this.mDragOccurred = false;
        return false;
    }

    private void multiTouchController() {
        int i = this.mMode;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        if (!this.mCurrPt.isDown()) {
                            this.mMode = 0;
                            MultiTouchObjectCanvas<T> multiTouchObjectCanvas = this.objectCanvas;
                            this.selectedObject = null;
                            multiTouchObjectCanvas.selectObject(null, this.mCurrPt);
                            this.mDragOccurred = false;
                            return;
                        }
                        performDragOrPinch();
                    }
                } else if (!this.mCurrPt.isMultiTouch() || !this.mCurrPt.isDown()) {
                    if (!this.mCurrPt.isDown()) {
                        this.mMode = 0;
                        MultiTouchObjectCanvas<T> multiTouchObjectCanvas2 = this.objectCanvas;
                        this.selectedObject = null;
                        multiTouchObjectCanvas2.selectObject(null, this.mCurrPt);
                        return;
                    }
                    this.mMode = 1;
                    anchorAtThisPositionAndScale();
                    this.mSettleStartTime = this.mCurrPt.getEventTime();
                    this.mSettleEndTime = this.mSettleStartTime + EVENT_SETTLE_TIME_INTERVAL;
                } else if (Math.abs(this.mCurrPt.getX() - this.mPrevPt.getX()) > 30.0f || Math.abs(this.mCurrPt.getY() - this.mPrevPt.getY()) > 30.0f || Math.abs(this.mCurrPt.getMultiTouchWidth() - this.mPrevPt.getMultiTouchWidth()) * 0.5f > MAX_MULTITOUCH_DIM_JUMP_SIZE || Math.abs(this.mCurrPt.getMultiTouchHeight() - this.mPrevPt.getMultiTouchHeight()) * 0.5f > MAX_MULTITOUCH_DIM_JUMP_SIZE) {
                    anchorAtThisPositionAndScale();
                    this.mSettleStartTime = this.mCurrPt.getEventTime();
                    this.mSettleEndTime = this.mSettleStartTime + EVENT_SETTLE_TIME_INTERVAL;
                } else if (this.mCurrPt.eventTime < this.mSettleEndTime) {
                    anchorAtThisPositionAndScale();
                } else {
                    performDragOrPinch();
                }
            } else if (!this.mCurrPt.isDown()) {
                this.mMode = 0;
                MultiTouchObjectCanvas<T> multiTouchObjectCanvas3 = this.objectCanvas;
                this.selectedObject = null;
                multiTouchObjectCanvas3.selectObject(null, this.mCurrPt);
                this.mDragOccurred = false;
            } else if (this.mCurrPt.isMultiTouch()) {
                this.mMode = 2;
                anchorAtThisPositionAndScale();
                this.mSettleStartTime = this.mCurrPt.getEventTime();
                this.mSettleEndTime = this.mSettleStartTime + EVENT_SETTLE_TIME_INTERVAL;
            } else if (this.mCurrPt.getEventTime() < this.mSettleEndTime) {
                anchorAtThisPositionAndScale();
            } else {
                performDragOrPinch();
            }
        } else if (this.mCurrPt.isDown()) {
            this.selectedObject = this.objectCanvas.getDraggableObjectAtPoint(this.mCurrPt);
            T t = this.selectedObject;
            if (t == null) {
                return;
            }
            if (this.objectCanvas.pointInObjectGrabArea(this.mCurrPt, t)) {
                this.mMode = 3;
                this.objectCanvas.selectObject(this.selectedObject, this.mCurrPt);
                anchorAtThisPositionAndScale();
                long eventTime = this.mCurrPt.getEventTime();
                this.mSettleEndTime = eventTime;
                this.mSettleStartTime = eventTime;
                return;
            }
            this.mMode = 1;
            this.objectCanvas.selectObject(this.selectedObject, this.mCurrPt);
            anchorAtThisPositionAndScale();
            long eventTime2 = this.mCurrPt.getEventTime();
            this.mSettleEndTime = eventTime2;
            this.mSettleStartTime = eventTime2;
        }
    }

    public int getMode() {
        return this.mMode;
    }

    public static class PointInfo {
        private int action;
        private float angle;
        private boolean angleIsCalculated;
        private float diameter;
        private boolean diameterIsCalculated;
        private float diameterSq;
        private boolean diameterSqIsCalculated;

        /* renamed from: dx */
        private float f4302dx;

        /* renamed from: dy */
        private float f4303dy;
        /* access modifiers changed from: private */
        public long eventTime;
        private boolean isDown;
        private boolean isMultiTouch;
        private int numPoints;
        private final int[] pointerIds = new int[20];
        private float pressureMid;
        private final float[] pressures = new float[20];
        private float xMid;

        /* renamed from: xs */
        private final float[] f4304xs = new float[20];
        private float yMid;

        /* renamed from: ys */
        private final float[] f4305ys = new float[20];

        private int julery_isqrt(int i) {
            int i2 = 0;
            int i3 = 32768;
            int i4 = 15;
            while (true) {
                int i5 = i4 - 1;
                int i6 = ((i2 << 1) + i3) << i4;
                if (i >= i6) {
                    i2 += i3;
                    i -= i6;
                }
                i3 >>= 1;
                if (i3 <= 0) {
                    return i2;
                }
                i4 = i5;
            }
        }

        public void set(int i, float[] fArr, float[] fArr2, float[] fArr3, int[] iArr, int i2, boolean z, long j) {
            this.eventTime = j;
            this.action = i2;
            this.numPoints = i;
            for (int i3 = 0; i3 < i; i3++) {
                this.f4304xs[i3] = fArr[i3];
                this.f4305ys[i3] = fArr2[i3];
                this.pressures[i3] = fArr3[i3];
                this.pointerIds[i3] = iArr[i3];
            }
            this.isDown = z;
            this.isMultiTouch = i >= 2;
            if (this.isMultiTouch) {
                this.xMid = (fArr[0] + fArr[1]) * 0.5f;
                this.yMid = (fArr2[0] + fArr2[1]) * 0.5f;
                this.pressureMid = (fArr3[0] + fArr3[1]) * 0.5f;
                this.f4302dx = Math.abs(fArr[1] - fArr[0]);
                this.f4303dy = Math.abs(fArr2[1] - fArr2[0]);
            } else {
                this.xMid = fArr[0];
                this.yMid = fArr2[0];
                this.pressureMid = fArr3[0];
                this.f4303dy = 0.0f;
                this.f4302dx = 0.0f;
            }
            this.angleIsCalculated = false;
            this.diameterIsCalculated = false;
            this.diameterSqIsCalculated = false;
        }

        public void set(PointInfo pointInfo) {
            this.numPoints = pointInfo.numPoints;
            for (int i = 0; i < this.numPoints; i++) {
                this.f4304xs[i] = pointInfo.f4304xs[i];
                this.f4305ys[i] = pointInfo.f4305ys[i];
                this.pressures[i] = pointInfo.pressures[i];
                this.pointerIds[i] = pointInfo.pointerIds[i];
            }
            this.xMid = pointInfo.xMid;
            this.yMid = pointInfo.yMid;
            this.pressureMid = pointInfo.pressureMid;
            this.f4302dx = pointInfo.f4302dx;
            this.f4303dy = pointInfo.f4303dy;
            this.diameter = pointInfo.diameter;
            this.diameterSq = pointInfo.diameterSq;
            this.angle = pointInfo.angle;
            this.isDown = pointInfo.isDown;
            this.action = pointInfo.action;
            this.isMultiTouch = pointInfo.isMultiTouch;
            this.diameterIsCalculated = pointInfo.diameterIsCalculated;
            this.diameterSqIsCalculated = pointInfo.diameterSqIsCalculated;
            this.angleIsCalculated = pointInfo.angleIsCalculated;
            this.eventTime = pointInfo.eventTime;
        }

        public boolean isMultiTouch() {
            return this.isMultiTouch;
        }

        public float getMultiTouchWidth() {
            if (this.isMultiTouch) {
                return this.f4302dx;
            }
            return 0.0f;
        }

        public float getMultiTouchHeight() {
            if (this.isMultiTouch) {
                return this.f4303dy;
            }
            return 0.0f;
        }

        public float getMultiTouchDiameterSq() {
            float f;
            if (!this.diameterSqIsCalculated) {
                if (this.isMultiTouch) {
                    float f2 = this.f4302dx;
                    float f3 = this.f4303dy;
                    f = (f2 * f2) + (f3 * f3);
                } else {
                    f = 0.0f;
                }
                this.diameterSq = f;
                this.diameterSqIsCalculated = true;
            }
            return this.diameterSq;
        }

        public float getMultiTouchDiameter() {
            if (!this.diameterIsCalculated) {
                float f = 0.0f;
                if (!this.isMultiTouch) {
                    this.diameter = 0.0f;
                } else {
                    float multiTouchDiameterSq = getMultiTouchDiameterSq();
                    if (multiTouchDiameterSq != 0.0f) {
                        f = ((float) julery_isqrt((int) (multiTouchDiameterSq * 256.0f))) / 16.0f;
                    }
                    this.diameter = f;
                    float f2 = this.diameter;
                    float f3 = this.f4302dx;
                    if (f2 < f3) {
                        this.diameter = f3;
                    }
                    float f4 = this.diameter;
                    float f5 = this.f4303dy;
                    if (f4 < f5) {
                        this.diameter = f5;
                    }
                }
                this.diameterIsCalculated = true;
            }
            return this.diameter;
        }

        public float getMultiTouchAngle() {
            if (!this.angleIsCalculated) {
                if (!this.isMultiTouch) {
                    this.angle = 0.0f;
                } else {
                    float[] fArr = this.f4305ys;
                    float[] fArr2 = this.f4304xs;
                    this.angle = (float) Math.atan2(fArr[1] - fArr[0], fArr2[1] - fArr2[0]);
                }
                this.angleIsCalculated = true;
            }
            return this.angle;
        }

        public int getNumTouchPoints() {
            return this.numPoints;
        }

        public float getX() {
            return this.xMid;
        }

        public float[] getXs() {
            return this.f4304xs;
        }

        public float getY() {
            return this.yMid;
        }

        public float[] getYs() {
            return this.f4305ys;
        }

        public int[] getPointerIds() {
            return this.pointerIds;
        }

        public float getPressure() {
            return this.pressureMid;
        }

        public float[] getPressures() {
            return this.pressures;
        }

        public boolean isDown() {
            return this.isDown;
        }

        public int getAction() {
            return this.action;
        }

        public long getEventTime() {
            return this.eventTime;
        }
    }

    public static class PositionAndScale {
        /* access modifiers changed from: private */
        public float angle;
        /* access modifiers changed from: private */
        public float scale;
        /* access modifiers changed from: private */
        public float scaleX;
        /* access modifiers changed from: private */
        public float scaleY;
        /* access modifiers changed from: private */
        public boolean updateAngle;
        /* access modifiers changed from: private */
        public boolean updateScale;
        /* access modifiers changed from: private */
        public boolean updateScaleXY;
        /* access modifiers changed from: private */
        public float xOff;
        /* access modifiers changed from: private */
        public float yOff;

        public void set(float f, float f2, boolean z, float f3, boolean z2, float f4, float f5, boolean z3, float f6) {
            this.xOff = f;
            this.yOff = f2;
            this.updateScale = z;
            float f7 = 1.0f;
            if (f3 == 0.0f) {
                f3 = 1.0f;
            }
            this.scale = f3;
            this.updateScaleXY = z2;
            if (f4 == 0.0f) {
                f4 = 1.0f;
            }
            this.scaleX = f4;
            if (f5 != 0.0f) {
                f7 = f5;
            }
            this.scaleY = f7;
            this.updateAngle = z3;
            this.angle = f6;
        }

        /* access modifiers changed from: protected */
        public void set(float f, float f2, float f3, float f4, float f5, float f6) {
            this.xOff = f;
            this.yOff = f2;
            float f7 = 1.0f;
            if (f3 == 0.0f) {
                f3 = 1.0f;
            }
            this.scale = f3;
            if (f4 == 0.0f) {
                f4 = 1.0f;
            }
            this.scaleX = f4;
            if (f5 != 0.0f) {
                f7 = f5;
            }
            this.scaleY = f7;
            this.angle = f6;
        }

        public float getXOff() {
            return this.xOff;
        }

        public float getYOff() {
            return this.yOff;
        }

        public float getScale() {
            if (!this.updateScale) {
                return 1.0f;
            }
            return this.scale;
        }

        public float getScaleX() {
            if (!this.updateScaleXY) {
                return 1.0f;
            }
            return this.scaleX;
        }

        public float getScaleY() {
            if (!this.updateScaleXY) {
                return 1.0f;
            }
            return this.scaleY;
        }

        public float getAngle() {
            if (!this.updateAngle) {
                return 0.0f;
            }
            return this.angle;
        }
    }
}