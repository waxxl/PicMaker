package com.yd.photoeditor.view.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ColorPickerView extends View {
    private static final float BORDER_WIDTH_PX = 1.0f;
    private static final int PANEL_ALPHA = 2;
    private static final int PANEL_HUE = 1;
    private static final int PANEL_SAT_VAL = 0;
    private float ALPHA_PANEL_HEIGHT;
    private float HUE_PANEL_WIDTH;
    private float PALETTE_CIRCLE_TRACKER_RADIUS;
    private float PANEL_SPACING;
    private float RECTANGLE_TRACKER_OFFSET;
    private int mAlpha;
    private Paint mAlphaPaint;
    private AlphaPatternDrawable mAlphaPattern;
    private RectF mAlphaRect;
    private Shader mAlphaShader;
    private String mAlphaSliderText;
    private Paint mAlphaTextPaint;
    private int mBorderColor;
    private Paint mBorderPaint;
    private float mDensity;
    private float mDrawingOffset;
    private RectF mDrawingRect;
    private float mHue;
    private Paint mHuePaint;
    private RectF mHueRect;
    private Shader mHueShader;
    private Paint mHueTrackerPaint;
    private int mLastTouchedPanel;
    private OnColorChangedListener mListener;
    private float mSat;
    private Shader mSatShader;
    private Paint mSatValPaint;
    private RectF mSatValRect;
    private Paint mSatValTrackerPaint;
    private boolean mShowAlphaPanel;
    private int mSliderTrackerColor;
    private Point mStartTouchPoint;
    private float mVal;
    private Shader mValShader;

    public interface OnColorChangedListener {
        void onColorChanged(int i);
    }

    public ColorPickerView(Context context) {
        this(context, null);
    }

    public ColorPickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ColorPickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.HUE_PANEL_WIDTH = 30.0f;
        this.ALPHA_PANEL_HEIGHT = 20.0f;
        this.PANEL_SPACING = 10.0f;
        this.PALETTE_CIRCLE_TRACKER_RADIUS = 5.0f;
        this.RECTANGLE_TRACKER_OFFSET = 2.0f;
        this.mDensity = 1.0f;
        this.mAlpha = 255;
        this.mHue = 360.0f;
        this.mSat = 0.0f;
        this.mVal = 0.0f;
        this.mAlphaSliderText = "";
        this.mSliderTrackerColor = -14935012;
        this.mBorderColor = -9539986;
        this.mShowAlphaPanel = false;
        this.mLastTouchedPanel = 0;
        this.mStartTouchPoint = null;
        init();
    }

    private void init() {
        this.mDensity = getContext().getResources().getDisplayMetrics().density;
        float f = this.PALETTE_CIRCLE_TRACKER_RADIUS;
        float f2 = this.mDensity;
        this.PALETTE_CIRCLE_TRACKER_RADIUS = f * f2;
        this.RECTANGLE_TRACKER_OFFSET *= f2;
        this.HUE_PANEL_WIDTH *= f2;
        this.ALPHA_PANEL_HEIGHT *= f2;
        this.PANEL_SPACING *= f2;
        this.mDrawingOffset = calculateRequiredOffset();
        initPaintTools();
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    private void initPaintTools() {
        this.mSatValPaint = new Paint();
        this.mSatValTrackerPaint = new Paint();
        this.mHuePaint = new Paint();
        this.mHueTrackerPaint = new Paint();
        this.mAlphaPaint = new Paint();
        this.mAlphaTextPaint = new Paint();
        this.mBorderPaint = new Paint();
        this.mSatValTrackerPaint.setStyle(Paint.Style.STROKE);
        this.mSatValTrackerPaint.setStrokeWidth(this.mDensity * 2.0f);
        this.mSatValTrackerPaint.setAntiAlias(true);
        this.mHueTrackerPaint.setColor(this.mSliderTrackerColor);
        this.mHueTrackerPaint.setStyle(Paint.Style.STROKE);
        this.mHueTrackerPaint.setStrokeWidth(this.mDensity * 2.0f);
        this.mHueTrackerPaint.setAntiAlias(true);
        this.mAlphaTextPaint.setColor(-14935012);
        this.mAlphaTextPaint.setTextSize(this.mDensity * 14.0f);
        this.mAlphaTextPaint.setAntiAlias(true);
        this.mAlphaTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mAlphaTextPaint.setFakeBoldText(true);
    }

    private float calculateRequiredOffset() {
        return Math.max(Math.max(this.PALETTE_CIRCLE_TRACKER_RADIUS, this.RECTANGLE_TRACKER_OFFSET), this.mDensity * 1.0f) * 1.5f;
    }

    private int[] buildHueColorArray() {
        int[] iArr = new int[361];
        int length = iArr.length - 1;
        int i = 0;
        while (length >= 0) {
            iArr[i] = Color.HSVToColor(new float[]{(float) length, 1.0f, 1.0f});
            length--;
            i++;
        }
        return iArr;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mDrawingRect.width() > 0.0f && this.mDrawingRect.height() > 0.0f) {
            drawSatValPanel(canvas);
            drawHuePanel(canvas);
            drawAlphaPanel(canvas);
        }
    }

    private void drawSatValPanel(Canvas canvas) {
        Canvas canvas2 = canvas;
        RectF rectF = this.mSatValRect;
        this.mBorderPaint.setColor(this.mBorderColor);
        canvas.drawRect(this.mDrawingRect.left, this.mDrawingRect.top, rectF.right + 1.0f, rectF.bottom + 1.0f, this.mBorderPaint);
        if (this.mValShader == null) {
            this.mValShader = new LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom, -1, -16777216, Shader.TileMode.CLAMP);
        }
        this.mSatShader = new LinearGradient(rectF.left, rectF.top, rectF.right, rectF.top, -1, Color.HSVToColor(new float[]{this.mHue, 1.0f, 1.0f}), Shader.TileMode.CLAMP);
        this.mSatValPaint.setShader(new ComposeShader(this.mValShader, this.mSatShader, PorterDuff.Mode.MULTIPLY));
        canvas2.drawRect(rectF, this.mSatValPaint);
        Point satValToPoint = satValToPoint(this.mSat, this.mVal);
        this.mSatValTrackerPaint.setColor(-16777216);
        canvas2.drawCircle((float) satValToPoint.x, (float) satValToPoint.y, this.PALETTE_CIRCLE_TRACKER_RADIUS - (this.mDensity * 1.0f), this.mSatValTrackerPaint);
        this.mSatValTrackerPaint.setColor(-2236963);
        canvas2.drawCircle((float) satValToPoint.x, (float) satValToPoint.y, this.PALETTE_CIRCLE_TRACKER_RADIUS, this.mSatValTrackerPaint);
    }

    private void drawHuePanel(Canvas canvas) {
        RectF rectF = this.mHueRect;
        this.mBorderPaint.setColor(this.mBorderColor);
        canvas.drawRect(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f, this.mBorderPaint);
        if (this.mHueShader == null) {
            this.mHueShader = new LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom, buildHueColorArray(), null, Shader.TileMode.CLAMP);
            this.mHuePaint.setShader(this.mHueShader);
        }
        canvas.drawRect(rectF, this.mHuePaint);
        float f = (this.mDensity * 4.0f) / 2.0f;
        Point hueToPoint = hueToPoint(this.mHue);
        RectF rectF2 = new RectF();
        rectF2.left = rectF.left - this.RECTANGLE_TRACKER_OFFSET;
        rectF2.right = rectF.right + this.RECTANGLE_TRACKER_OFFSET;
        rectF2.top = ((float) hueToPoint.y) - f;
        rectF2.bottom = ((float) hueToPoint.y) + f;
        canvas.drawRoundRect(rectF2, 2.0f, 2.0f, this.mHueTrackerPaint);
    }

    private void drawAlphaPanel(Canvas canvas) {
        RectF rectF;
        if (this.mShowAlphaPanel && (rectF = this.mAlphaRect) != null && this.mAlphaPattern != null) {
            this.mBorderPaint.setColor(this.mBorderColor);
            canvas.drawRect(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f, this.mBorderPaint);
            this.mAlphaPattern.draw(canvas);
            float[] fArr = {this.mHue, this.mSat, this.mVal};
            this.mAlphaShader = new LinearGradient(rectF.left, rectF.top, rectF.right, rectF.top, Color.HSVToColor(fArr), Color.HSVToColor(0, fArr), Shader.TileMode.CLAMP);
            this.mAlphaPaint.setShader(this.mAlphaShader);
            canvas.drawRect(rectF, this.mAlphaPaint);
            String str = this.mAlphaSliderText;
            if (!(str == null || str == "")) {
                canvas.drawText(str, rectF.centerX(), rectF.centerY() + (this.mDensity * 4.0f), this.mAlphaTextPaint);
            }
            float f = (this.mDensity * 4.0f) / 2.0f;
            Point alphaToPoint = alphaToPoint(this.mAlpha);
            RectF rectF2 = new RectF();
            rectF2.left = ((float) alphaToPoint.x) - f;
            rectF2.right = ((float) alphaToPoint.x) + f;
            rectF2.top = rectF.top - this.RECTANGLE_TRACKER_OFFSET;
            rectF2.bottom = rectF.bottom + this.RECTANGLE_TRACKER_OFFSET;
            canvas.drawRoundRect(rectF2, 2.0f, 2.0f, this.mHueTrackerPaint);
        }
    }

    private Point hueToPoint(float f) {
        RectF rectF = this.mHueRect;
        float height = rectF.height();
        Point point = new Point();
        point.y = (int) ((height - ((f * height) / 360.0f)) + rectF.top);
        point.x = (int) rectF.left;
        return point;
    }

    private Point satValToPoint(float f, float f2) {
        RectF rectF = this.mSatValRect;
        float height = rectF.height();
        float width = rectF.width();
        Point point = new Point();
        point.x = (int) ((f * width) + rectF.left);
        point.y = (int) (((1.0f - f2) * height) + rectF.top);
        return point;
    }

    private Point alphaToPoint(int i) {
        RectF rectF = this.mAlphaRect;
        float width = rectF.width();
        Point point = new Point();
        point.x = (int) ((width - ((((float) i) * width) / 255.0f)) + rectF.left);
        point.y = (int) rectF.top;
        return point;
    }

    private float[] pointToSatVal(float f, float f2) {
        float f3;
        float f4;
        RectF rectF = this.mSatValRect;
        float[] fArr = new float[2];
        float width = rectF.width();
        float height = rectF.height();
        if (f < rectF.left) {
            f3 = 0.0f;
        } else if (f > rectF.right) {
            f3 = width;
        } else {
            f3 = f - rectF.left;
        }
        if (f2 < rectF.top) {
            f4 = 0.0f;
        } else if (f2 > rectF.bottom) {
            f4 = height;
        } else {
            f4 = f2 - rectF.top;
        }
        fArr[0] = (1.0f / width) * f3;
        fArr[1] = 1.0f - ((1.0f / height) * f4);
        return fArr;
    }

    private float pointToHue(float f) {
        float f2;
        RectF rectF = this.mHueRect;
        float height = rectF.height();
        if (f < rectF.top) {
            f2 = 0.0f;
        } else if (f > rectF.bottom) {
            f2 = height;
        } else {
            f2 = f - rectF.top;
        }
        return 360.0f - ((f2 * 360.0f) / height);
    }

    private int pointToAlpha(int i) {
        int i2;
        RectF rectF = this.mAlphaRect;
        int width = (int) rectF.width();
        float f = (float) i;
        if (f < rectF.left) {
            i2 = 0;
        } else if (f > rectF.right) {
            i2 = width;
        } else {
            i2 = i - ((int) rectF.left);
        }
        return 255 - ((i2 * 255) / width);
    }

    public boolean onTrackballEvent(MotionEvent r9) {
        /*
            r8 = this;
            float r0 = r9.getX()
            float r1 = r9.getY()
            int r2 = r9.getAction()
            r3 = 2
            r4 = 0
            r5 = 1
            if (r2 != r3) goto L_0x007b
            int r2 = r8.mLastTouchedPanel
            r6 = 0
            if (r2 == 0) goto L_0x0051
            r7 = 1092616192(0x41200000, float:10.0)
            if (r2 == r5) goto L_0x003a
            if (r2 == r3) goto L_0x001d
            goto L_0x007b
        L_0x001d:
            boolean r1 = r8.mShowAlphaPanel
            if (r1 == 0) goto L_0x007b
            android.graphics.RectF r1 = r8.mAlphaRect
            if (r1 != 0) goto L_0x0026
            goto L_0x007b
        L_0x0026:
            int r1 = r8.mAlpha
            float r1 = (float) r1
            float r0 = r0 * r7
            float r1 = r1 - r0
            int r0 = (int) r1
            r1 = 255(0xff, float:3.57E-43)
            if (r0 >= 0) goto L_0x0033
            r0 = 0
            goto L_0x0037
        L_0x0033:
            if (r0 <= r1) goto L_0x0037
            r0 = 255(0xff, float:3.57E-43)
        L_0x0037:
            r8.mAlpha = r0
            goto L_0x004f
        L_0x003a:
            float r0 = r8.mHue
            float r1 = r1 * r7
            float r0 = r0 - r1
            r1 = 1135869952(0x43b40000, float:360.0)
            int r2 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r2 >= 0) goto L_0x0047
            r0 = 0
            goto L_0x004d
        L_0x0047:
            int r2 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r2 <= 0) goto L_0x004d
            r0 = 1135869952(0x43b40000, float:360.0)
        L_0x004d:
            r8.mHue = r0
        L_0x004f:
            r0 = 1
            goto L_0x007c
        L_0x0051:
            float r2 = r8.mSat
            r7 = 1112014848(0x42480000, float:50.0)
            float r0 = r0 / r7
            float r0 = r0 + r2
            float r2 = r8.mVal
            float r1 = r1 / r7
            float r1 = r2 - r1
            r2 = 1065353216(0x3f800000, float:1.0)
            int r7 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r7 >= 0) goto L_0x0064
            r0 = 0
            goto L_0x006a
        L_0x0064:
            int r7 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r7 <= 0) goto L_0x006a
            r0 = 1065353216(0x3f800000, float:1.0)
        L_0x006a:
            int r7 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r7 >= 0) goto L_0x0070
            r1 = 0
            goto L_0x0076
        L_0x0070:
            int r6 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r6 <= 0) goto L_0x0076
            r1 = 1065353216(0x3f800000, float:1.0)
        L_0x0076:
            r8.mSat = r0
            r8.mVal = r1
            goto L_0x004f
        L_0x007b:
            r0 = 0
        L_0x007c:
            if (r0 == 0) goto L_0x009e
            com.yd.photoeditor.colorpicker.ColorPickerView$OnColorChangedListener r9 = r8.mListener
            if (r9 == 0) goto L_0x009a
            int r0 = r8.mAlpha
            r1 = 3
            float[] r1 = new float[r1]
            float r2 = r8.mHue
            r1[r4] = r2
            float r2 = r8.mSat
            r1[r5] = r2
            float r2 = r8.mVal
            r1[r3] = r2
            int r0 = android.graphics.Color.HSVToColor(r0, r1)
            r9.onColorChanged(r0)
        L_0x009a:
            r8.invalidate()
            return r5
        L_0x009e:
            boolean r9 = super.onTrackballEvent(r9)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yd.photoeditor.colorpicker.ColorPickerView.onTrackballEvent(android.view.MotionEvent):boolean");
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mStartTouchPoint = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
            z = moveTrackersIfNeeded(motionEvent);
        } else if (action != 1) {
            z = action == 2 && moveTrackersIfNeeded(motionEvent);
        } else {
            this.mStartTouchPoint = null;
            z = moveTrackersIfNeeded(motionEvent);
        }
        if (!z) {
            return super.onTouchEvent(motionEvent);
        }
        OnColorChangedListener onColorChangedListener = this.mListener;
        if (onColorChangedListener != null) {
            onColorChangedListener.onColorChanged(Color.HSVToColor(this.mAlpha, new float[]{this.mHue, this.mSat, this.mVal}));
        }
        invalidate();
        return true;
    }

    private boolean moveTrackersIfNeeded(MotionEvent motionEvent) {
        Point point = this.mStartTouchPoint;
        if (point == null) {
            return false;
        }
        int i = point.x;
        float f = (float) i;
        float f2 = (float) this.mStartTouchPoint.y;
        if (this.mHueRect.contains(f, f2)) {
            this.mLastTouchedPanel = 1;
            this.mHue = pointToHue(motionEvent.getY());
            return true;
        } else if (this.mSatValRect.contains(f, f2)) {
            this.mLastTouchedPanel = 0;
            float[] pointToSatVal = pointToSatVal(motionEvent.getX(), motionEvent.getY());
            this.mSat = pointToSatVal[0];
            this.mVal = pointToSatVal[1];
            return true;
        } else {
            RectF rectF = this.mAlphaRect;
            if (rectF == null || !rectF.contains(f, f2)) {
                return false;
            }
            this.mLastTouchedPanel = 2;
            this.mAlpha = pointToAlpha((int) motionEvent.getX());
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        int chooseWidth = chooseWidth(mode, size);
        int chooseHeight = chooseHeight(mode2, size2);
        if (!this.mShowAlphaPanel) {
            int i3 = (int) ((((float) chooseWidth) - this.PANEL_SPACING) - this.HUE_PANEL_WIDTH);
            if (i3 > chooseHeight || getTag().equals("landscape")) {
                chooseWidth = (int) (((float) chooseHeight) + this.PANEL_SPACING + this.HUE_PANEL_WIDTH);
            } else {
                chooseHeight = i3;
            }
        } else {
            float f = this.ALPHA_PANEL_HEIGHT;
            float f2 = this.HUE_PANEL_WIDTH;
            int i4 = (int) ((((float) chooseHeight) - f) + f2);
            if (i4 > chooseWidth) {
                chooseHeight = (int) ((((float) chooseWidth) - f2) + f);
            } else {
                chooseWidth = i4;
            }
        }
        setMeasuredDimension(chooseWidth, chooseHeight);
    }

    private int chooseWidth(int i, int i2) {
        return (i == Integer.MIN_VALUE || i == 1073741824) ? i2 : getPrefferedWidth();
    }

    private int chooseHeight(int i, int i2) {
        return (i == Integer.MIN_VALUE || i == 1073741824) ? i2 : getPrefferedHeight();
    }

    private int getPrefferedWidth() {
        int prefferedHeight = getPrefferedHeight();
        if (this.mShowAlphaPanel) {
            prefferedHeight = (int) (((float) prefferedHeight) - (this.PANEL_SPACING + this.ALPHA_PANEL_HEIGHT));
        }
        return (int) (((float) prefferedHeight) + this.HUE_PANEL_WIDTH + this.PANEL_SPACING);
    }

    private int getPrefferedHeight() {
        int i = (int) (this.mDensity * 200.0f);
        return this.mShowAlphaPanel ? (int) (((float) i) + this.PANEL_SPACING + this.ALPHA_PANEL_HEIGHT) : i;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mDrawingRect = new RectF();
        this.mDrawingRect.left = this.mDrawingOffset + ((float) getPaddingLeft());
        this.mDrawingRect.right = (((float) i) - this.mDrawingOffset) - ((float) getPaddingRight());
        this.mDrawingRect.top = this.mDrawingOffset + ((float) getPaddingTop());
        this.mDrawingRect.bottom = (((float) i2) - this.mDrawingOffset) - ((float) getPaddingBottom());
        setUpSatValRect();
        setUpHueRect();
        setUpAlphaRect();
    }

    private void setUpSatValRect() {
        RectF rectF = this.mDrawingRect;
        float height = rectF.height() - 2.0f;
        if (this.mShowAlphaPanel) {
            height -= this.PANEL_SPACING + this.ALPHA_PANEL_HEIGHT;
        }
        float f = rectF.left + 1.0f;
        float f2 = rectF.top + 1.0f;
        this.mSatValRect = new RectF(f, f2, height + f, f2 + height);
    }

    private void setUpHueRect() {
        RectF rectF = this.mDrawingRect;
        this.mHueRect = new RectF((rectF.right - this.HUE_PANEL_WIDTH) + 1.0f, rectF.top + 1.0f, rectF.right - 1.0f, (rectF.bottom - 1.0f) - (this.mShowAlphaPanel ? this.PANEL_SPACING + this.ALPHA_PANEL_HEIGHT : 0.0f));
    }

    private void setUpAlphaRect() {
        if (this.mShowAlphaPanel) {
            RectF rectF = this.mDrawingRect;
            this.mAlphaRect = new RectF(rectF.left + 1.0f, (rectF.bottom - this.ALPHA_PANEL_HEIGHT) + 1.0f, rectF.right - 1.0f, rectF.bottom - 1.0f);
            this.mAlphaPattern = new AlphaPatternDrawable((int) (this.mDensity * 5.0f));
            this.mAlphaPattern.setBounds(Math.round(this.mAlphaRect.left), Math.round(this.mAlphaRect.top), Math.round(this.mAlphaRect.right), Math.round(this.mAlphaRect.bottom));
        }
    }

    public void setOnColorChangedListener(OnColorChangedListener onColorChangedListener) {
        this.mListener = onColorChangedListener;
    }

    public void setBorderColor(int i) {
        this.mBorderColor = i;
        invalidate();
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public int getColor() {
        return Color.HSVToColor(this.mAlpha, new float[]{this.mHue, this.mSat, this.mVal});
    }

    public void setColor(int i) {
        setColor(i, false);
    }

    public void setColor(int i, boolean z) {
        OnColorChangedListener onColorChangedListener;
        int alpha = Color.alpha(i);
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        this.mAlpha = alpha;
        this.mHue = fArr[0];
        this.mSat = fArr[1];
        this.mVal = fArr[2];
        if (z && (onColorChangedListener = this.mListener) != null) {
            onColorChangedListener.onColorChanged(Color.HSVToColor(this.mAlpha, new float[]{this.mHue, this.mSat, this.mVal}));
        }
        invalidate();
    }

    public float getDrawingOffset() {
        return this.mDrawingOffset;
    }

    public void setAlphaSliderVisible(boolean z) {
        if (this.mShowAlphaPanel != z) {
            this.mShowAlphaPanel = z;
            this.mValShader = null;
            this.mSatShader = null;
            this.mHueShader = null;
            this.mAlphaShader = null;
            requestLayout();
        }
    }

    public boolean getAlphaSliderVisible() {
        return this.mShowAlphaPanel;
    }

    public void setSliderTrackerColor(int i) {
        this.mSliderTrackerColor = i;
        this.mHueTrackerPaint.setColor(this.mSliderTrackerColor);
        invalidate();
    }

    public int getSliderTrackerColor() {
        return this.mSliderTrackerColor;
    }

    public void setAlphaSliderText(int i) {
        setAlphaSliderText(getContext().getString(i));
    }

    public void setAlphaSliderText(String str) {
        this.mAlphaSliderText = str;
        invalidate();
    }

    public String getAlphaSliderText() {
        return this.mAlphaSliderText;
    }
}
