package com.yd.photoeditor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.yd.photoeditor.R;

public class RectangleCropMaskImageView extends ImageView {
    private static final int BOTTOM_LINE = 7;
    public static final float CUSTOM_SIZE = -2.0f;
    private static final int INSIDE_RECT = 8;
    private static final int LEFT_BOTTOM_CIRCLE = 2;
    private static final int LEFT_LINE = 4;
    private static final int LEFT_TOP_CIRCLE = 0;
    private static final int MIN_TOUCH_DIST_DP = 15;
    public static final int MOVE_DOWN = 1;
    public static final int MOVE_LEFT = 2;
    public static final int MOVE_RIGHT = 3;
    public static final int MOVE_UP = 0;
    public static final float ORIGINAL_SIZE = -1.0f;
    private static final int OUTSIDE_RECT = -1;
    private static final int RIGHT_BOTTOM_CIRCLE = 3;
    private static final int RIGHT_LINE = 6;
    private static final int RIGHT_TOP_CIRCLE = 1;
    private static final int TOP_LINE = 5;
    private float mBottom;
    private IChangeDirection mChangeDirection;
    private Bitmap mCircleBitmap;
    private int mCurrentTouchedArea = -1;
    private PointF mCurrentTouchedPoint = new PointF();
    private float mFingerWidth = 15.0f;
    private float mLeft;
    private int mMaskColor;
    private int mMinTouchDist = 15;
    private Paint mPaint;
    private boolean mPaintMode = false;
    private float mRatio = -1.0f;
    private float mRight;
    private float mScaleRatio = 1.0f;
    private boolean mSlideMode = false;
    private float mTop;
    private float radius = 0.0f;

    public interface IChangeDirection {
        void changeDirection(int i);

        void clickAt(float f, float f2);
    }

    public RectangleCropMaskImageView(Context context) {
        super(context);
        initClipArea();
    }

    public RectangleCropMaskImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initClipArea();
    }

    public RectangleCropMaskImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initClipArea();
    }

    public void setPaintMode(boolean z) {
        this.mPaintMode = z;
        invalidate();
    }

    public void setScaleRatio(float f) {
        this.mScaleRatio = f;
    }

    public float getScaleRatio() {
        return this.mScaleRatio;
    }

    public void setRatio(float f) {
        this.mRatio = f;
        invalidate();
    }

    public void setSlideMode(boolean z) {
        this.mSlideMode = z;
    }

    public boolean isSlideMode() {
        return this.mSlideMode;
    }

    public float getRatio() {
        return this.mRatio;
    }

    public void setCropArea(RectF rectF) {
        this.mTop = rectF.top;
        this.mBottom = rectF.bottom;
        this.mLeft = rectF.left;
        this.mRight = rectF.right;
        invalidate();
    }

    public RectF getCropArea() {
        return new RectF(this.mLeft, this.mTop, this.mRight, this.mBottom);
    }

    public boolean isPaintMode() {
        return this.mPaintMode;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPaintMode) {
            this.mPaint.setColor(this.mMaskColor);
            this.mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(0.0f, 0.0f, this.mLeft, (float) getHeight(), this.mPaint);
            canvas.drawRect(this.mRight, 0.0f, (float) getWidth(), (float) getHeight(), this.mPaint);
            canvas.drawRect(this.mLeft, 0.0f, this.mRight, this.mTop, this.mPaint);
            canvas.drawRect(this.mLeft, this.mBottom, this.mRight, (float) getHeight(), this.mPaint);
            this.mPaint.setStrokeWidth(3.0f);
            this.mPaint.setColor(-1);
            float f = this.mLeft;
            float f2 = this.mTop;
            canvas.drawLine(f, f2, this.mRight, f2, this.mPaint);
            float f3 = this.mLeft;
            canvas.drawLine(f3, this.mTop, f3, this.mBottom, this.mPaint);
            float f4 = this.mRight;
            canvas.drawLine(f4, this.mTop, f4, this.mBottom, this.mPaint);
            float f5 = this.mLeft;
            float f6 = this.mBottom;
            canvas.drawLine(f5, f6, this.mRight, f6, this.mPaint);
            this.radius = ((float) this.mCircleBitmap.getWidth()) / 2.0f;
            Bitmap bitmap = this.mCircleBitmap;
            float f7 = this.mLeft;
            float f8 = this.radius;
            canvas.drawBitmap(bitmap, f7 - f8, this.mTop - f8, (Paint) null);
            Bitmap bitmap2 = this.mCircleBitmap;
            float f9 = this.mRight;
            float f10 = this.radius;
            canvas.drawBitmap(bitmap2, f9 - f10, this.mTop - f10, (Paint) null);
            Bitmap bitmap3 = this.mCircleBitmap;
            float f11 = this.mLeft;
            float f12 = this.radius;
            canvas.drawBitmap(bitmap3, f11 - f12, this.mBottom - f12, (Paint) null);
            Bitmap bitmap4 = this.mCircleBitmap;
            float f13 = this.mRight;
            float f14 = this.radius;
            canvas.drawBitmap(bitmap4, f13 - f14, this.mBottom - f14, (Paint) null);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mPaintMode) {
            if (this.mSlideMode) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    this.mCurrentTouchedPoint.x = motionEvent.getX();
                    this.mCurrentTouchedPoint.y = motionEvent.getY();
                } else if (action == 1) {
                    PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
                    float f = ((pointF.x - this.mCurrentTouchedPoint.x) * (pointF.x - this.mCurrentTouchedPoint.x)) + ((pointF.y - this.mCurrentTouchedPoint.y) * (pointF.y - this.mCurrentTouchedPoint.y));
                    int i = this.mMinTouchDist;
                    if (f >= ((float) (i * i))) {
                        int findDirectionMovement = findDirectionMovement(this.mCurrentTouchedPoint, pointF);
                        IChangeDirection iChangeDirection = this.mChangeDirection;
                        if (iChangeDirection != null) {
                            iChangeDirection.changeDirection(findDirectionMovement);
                        }
                    } else {
                        IChangeDirection iChangeDirection2 = this.mChangeDirection;
                        if (iChangeDirection2 != null) {
                            iChangeDirection2.clickAt(motionEvent.getX(), motionEvent.getY());
                        }
                    }
                }
            }
            return true;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (motionEvent.getAction() == 0) {
            this.mCurrentTouchedArea = calculatePosition(x, y);
            PointF pointF2 = this.mCurrentTouchedPoint;
            pointF2.x = x;
            pointF2.y = y;
        } else if (motionEvent.getAction() == 1) {
            this.mCurrentTouchedArea = -1;
        } else if (motionEvent.getAction() == 2) {
            float f2 = y - this.mCurrentTouchedPoint.y;
            float f3 = x - this.mCurrentTouchedPoint.x;
            float f4 = this.mLeft;
            float f5 = f4 + f3;
            float f6 = this.mRight;
            float f7 = f6 + f3;
            float f8 = this.mTop;
            float f9 = f8 + f2;
            float f10 = this.mBottom;
            float f11 = f10 + f2;
            boolean z = false;
            switch (this.mCurrentTouchedArea) {
                case 0:
                    float f12 = this.mRatio;
                    if (f12 > 0.0f) {
                        f9 = f8 + (f3 / f12);
                    }
                    if (this.mRatio >= 0.0f) {
                        if (f9 < this.mBottom && f9 >= 0.0f && f5 < this.mRight && f5 >= 0.0f) {
                            this.mTop = f9;
                            this.mLeft = f5;
                            invalidate();
                            break;
                        }
                    } else {
                        if (f9 < this.mBottom && f9 >= 0.0f) {
                            this.mTop = f9;
                            z = true;
                        }
                        if (f5 < this.mRight && f5 >= 0.0f) {
                            this.mLeft = f5;
                            z = true;
                        }
                        if (z) {
                            invalidate();
                            break;
                        }
                    }
                    break;
                case 1:
                    float f13 = this.mRatio;
                    if (f13 > 0.0f) {
                        f9 = f8 - (f3 / f13);
                    }
                    if (this.mRatio >= 0.0f) {
                        if (f9 < this.mBottom && f9 >= 0.0f && f7 > this.mLeft && f7 <= ((float) getWidth())) {
                            this.mTop = f9;
                            this.mRight = f7;
                            invalidate();
                            break;
                        }
                    } else {
                        if (f9 < this.mBottom && f9 >= 0.0f) {
                            this.mTop = f9;
                            z = true;
                        }
                        if (f7 > this.mLeft && f7 <= ((float) getWidth())) {
                            this.mRight = f7;
                            z = true;
                        }
                        if (z) {
                            invalidate();
                            break;
                        }
                    }
                    break;
                case 2:
                    float f14 = this.mRatio;
                    if (f14 > 0.0f) {
                        f11 = f10 - (f3 / f14);
                    }
                    if (this.mRatio >= 0.0f) {
                        if (f11 > this.mTop && f11 <= ((float) getHeight()) && f5 < this.mRight && f5 >= 0.0f) {
                            this.mBottom = f11;
                            this.mLeft = f5;
                            invalidate();
                            break;
                        }
                    } else {
                        if (f11 > this.mTop && f11 <= ((float) getHeight())) {
                            this.mBottom = f11;
                            z = true;
                        }
                        if (f5 < this.mRight && f5 >= 0.0f) {
                            this.mLeft = f5;
                            z = true;
                        }
                        if (z) {
                            invalidate();
                            break;
                        }
                    }
                    break;
                case 3:
                    float f15 = this.mRatio;
                    if (f15 > 0.0f) {
                        f11 = f10 + (f3 / f15);
                    }
                    if (this.mRatio >= 0.0f) {
                        if (f11 > this.mTop && f11 <= ((float) getHeight()) && f7 > this.mLeft && f7 <= ((float) getWidth())) {
                            this.mBottom = f11;
                            this.mRight = f7;
                            invalidate();
                            break;
                        }
                    } else {
                        if (f11 > this.mTop && f11 <= ((float) getHeight())) {
                            this.mBottom = f11;
                            z = true;
                        }
                        if (f7 > this.mLeft && f7 <= ((float) getWidth())) {
                            this.mRight = f7;
                            z = true;
                        }
                        if (z) {
                            invalidate();
                            break;
                        }
                    }
                    break;
                case 4:
                    float f16 = this.mRatio;
                    if (f16 > 0.0f) {
                        float f17 = f3 * 0.5f;
                        f9 = f8 + (f17 / f16);
                        f11 = f10 - (f17 / f16);
                    }
                    if (f5 < this.mRight && f5 >= 0.0f) {
                        if (this.mRatio <= 0.0f || f9 < 0.0f || f9 >= f11 || f11 > ((float) getHeight())) {
                            if (this.mRatio < 0.0f) {
                                this.mLeft = f5;
                                invalidate();
                                break;
                            }
                        } else {
                            this.mLeft = f5;
                            this.mTop = f9;
                            this.mBottom = f11;
                            invalidate();
                            break;
                        }
                    }
                    break;
                case 5:
                    float f18 = this.mRatio;
                    if (f18 > 0.0f) {
                        f5 = ((f2 * f18) / 2.0f) + f4;
                        f7 = f6 - ((f2 * f18) / 2.0f);
                    }
                    if (f9 < this.mBottom && f9 >= 0.0f) {
                        if (this.mRatio <= 0.0f || f5 < 0.0f || f5 >= f7 || f7 > ((float) getWidth())) {
                            if (this.mRatio < 0.0f) {
                                this.mTop = f9;
                                invalidate();
                                break;
                            }
                        } else {
                            this.mTop = f9;
                            this.mLeft = f5;
                            this.mRight = f7;
                            invalidate();
                            break;
                        }
                    }
                    break;
                case 6:
                    float f19 = this.mRatio;
                    if (f19 > 0.0f) {
                        float f20 = f3 * 0.5f;
                        f9 = f8 - (f20 / f19);
                        f11 = f10 + (f20 / f19);
                    }
                    if (f7 > this.mLeft && f7 <= ((float) getWidth())) {
                        if (this.mRatio <= 0.0f || f9 < 0.0f || f9 >= f11 || f11 > ((float) getHeight())) {
                            if (this.mRatio < 0.0f) {
                                this.mRight = f7;
                                invalidate();
                                break;
                            }
                        } else {
                            this.mRight = f7;
                            this.mTop = f9;
                            this.mBottom = f11;
                            invalidate();
                            break;
                        }
                    }
                    break;
                case 7:
                    float f21 = this.mRatio;
                    if (f21 > 0.0f) {
                        float f22 = f2 * 0.5f;
                        f5 = f4 - (f22 * f21);
                        f7 = f6 + (f22 * f21);
                    }
                    if (f11 > this.mTop && f11 <= ((float) getHeight())) {
                        if (this.mRatio <= 0.0f || f5 < 0.0f || f5 >= f7 || f7 > ((float) getWidth())) {
                            if (this.mRatio < 0.0f) {
                                this.mBottom = f11;
                                invalidate();
                                break;
                            }
                        } else {
                            this.mBottom = f11;
                            this.mLeft = f5;
                            this.mRight = f7;
                            invalidate();
                            break;
                        }
                    }
                    break;
                case 8:
                    if (f9 < 0.0f || f11 > ((float) getHeight()) || f5 < 0.0f || f7 > ((float) getWidth())) {
                        if (f9 < 0.0f || f11 > ((float) getHeight())) {
                            if (f5 >= 0.0f && f7 <= ((float) getWidth())) {
                                this.mLeft = f5;
                                this.mRight = f7;
                                invalidate();
                                break;
                            }
                        } else {
                            this.mTop = f9;
                            this.mBottom = f11;
                            invalidate();
                            break;
                        }
                    } else {
                        this.mTop = f9;
                        this.mBottom = f11;
                        this.mLeft = f5;
                        this.mRight = f7;
                        invalidate();
                        break;
                    }
                    break;
            }
            PointF pointF3 = this.mCurrentTouchedPoint;
            pointF3.x = x;
            pointF3.y = y;
        }
        return true;
    }

    public Bitmap cropImage(Bitmap bitmap) {
        RectF cropArea = getCropArea();
        float scaleRatio = getScaleRatio();
        Rect rect = new Rect((int) (cropArea.left * scaleRatio), (int) (cropArea.top * scaleRatio), (int) (cropArea.right * scaleRatio), (int) (cropArea.bottom * scaleRatio));
        Bitmap createBitmap = Bitmap.createBitmap((int) ((cropArea.right * scaleRatio) - (cropArea.left * scaleRatio)), (int) ((cropArea.bottom * scaleRatio) - (scaleRatio * cropArea.top)), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawBitmap(bitmap, rect, new RectF(0.0f, 0.0f, (float) createBitmap.getWidth(), (float) createBitmap.getHeight()), paint);
        return createBitmap;
    }

    private int calculatePosition(float f, float f2) {
        float f3 = this.mLeft;
        float f4 = this.mFingerWidth;
        if (f > f3 + f4 && f < this.mRight - f4 && f2 > this.mTop + f4 && f2 < this.mBottom - f4) {
            return 8;
        }
        float f5 = this.radius;
        float f6 = f5 * f5;
        float f7 = this.mLeft;
        float f8 = (f - f7) * (f - f7);
        float f9 = this.mTop;
        if (f8 + ((f2 - f9) * (f2 - f9)) < f6) {
            return 0;
        }
        float f10 = this.mRight;
        if (((f - f10) * (f - f10)) + ((f2 - f9) * (f2 - f9)) < f6) {
            return 1;
        }
        float f11 = (f - f10) * (f - f10);
        float f12 = this.mBottom;
        if (f11 + ((f2 - f12) * (f2 - f12)) < f6) {
            return 3;
        }
        if (((f - f7) * (f - f7)) + ((f2 - f12) * (f2 - f12)) < f6) {
            return 2;
        }
        float f13 = this.mFingerWidth;
        if (f2 > f9 - f13 && f2 < f9 + f13 && f > f7 && f < f10) {
            return 5;
        }
        if (f2 > this.mTop && f2 < this.mBottom) {
            float f14 = this.mRight;
            float f15 = this.mFingerWidth;
            if (f > f14 - f15 && f < f14 + f15) {
                return 6;
            }
        }
        float f16 = this.mBottom;
        float f17 = this.mFingerWidth;
        if (f2 > f16 - f17 && f2 < f16 + f17 && f > this.mLeft && f < this.mRight) {
            return 7;
        }
        if (f2 <= this.mTop || f2 >= this.mBottom) {
            return -1;
        }
        float f18 = this.mLeft;
        float f19 = this.mFingerWidth;
        return (f <= f18 - f19 || f >= this.mRight + f19) ? -1 : 4;
    }

    private void initClipArea() {
        this.mTop = 50.0f;
        this.mLeft = 50.0f;
        this.mBottom = 200.0f;
        this.mRight = 200.0f;
        this.mMaskColor = getContext().getResources().getColor(R.color.gray_mask_color);
        this.mCircleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo_editor_circle_small);
        this.mPaint = new Paint();
        this.mMinTouchDist = (int) ((getContext().getResources().getDisplayMetrics().density * 15.0f) + 0.5f);
        this.mFingerWidth = getResources().getDimension(R.dimen.photo_editor_finger_width);
    }

    public static int findDirectionMovement(PointF pointF, PointF pointF2) {
        float f = pointF2.x - pointF.x;
        float f2 = pointF2.y - pointF.y;
        float abs = Math.abs(f);
        float abs2 = Math.abs(f2);
        return f2 * f >= 0.0f ? f < 0.0f ? abs >= abs2 ? 2 : 0 : abs >= abs2 ? 3 : 1 : f < 0.0f ? abs >= abs2 ? 2 : 1 : abs >= abs2 ? 3 : 0;
    }

    public void setChangeDirection(IChangeDirection iChangeDirection) {
        this.mChangeDirection = iChangeDirection;
    }
}
