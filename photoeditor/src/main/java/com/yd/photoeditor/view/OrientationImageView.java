package com.yd.photoeditor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.yd.photoeditor.utils.PhotoUtils;

public class OrientationImageView extends View {
    public static final int FLIP_HORIZONTAL = 2;
    public static final int FLIP_VERTICAL = 1;
    private float mAngle = 0.0f;
    private Matrix mAppliedMatrix = new Matrix();
    private Bitmap mImage;
    private Matrix mMatrix = new Matrix();
    private float mMaxAngle = 0.0f;
    private float mOldX;
    private final Paint mPaint = new Paint();
    private float mThumbX;

    public OrientationImageView(Context context) {
        super(context);
        init();
    }

    public OrientationImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public OrientationImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public void saveInstanceState(Bundle bundle) {
        bundle.putFloat("com.yd.photoeditor.actions.RotationAction.mAngle", this.mAngle);
        bundle.putFloat("com.yd.photoeditor.actions.RotationAction.mThumbX", this.mThumbX);
        bundle.putFloat("com.yd.photoeditor.actions.RotationAction.mOldX", this.mOldX);
        bundle.putFloat("com.yd.photoeditor.actions.RotationAction.mMaxAngle", this.mMaxAngle);
        float[] fArr = new float[9];
        this.mMatrix.getValues(fArr);
        bundle.putFloatArray("com.yd.photoeditor.actions.RotationAction.mMatrix", fArr);
        float[] fArr2 = new float[9];
        this.mAppliedMatrix.getValues(fArr2);
        bundle.putFloatArray("com.yd.photoeditor.actions.RotationAction.mAppliedMatrix", fArr2);
    }

    public void restoreInstanceState(Bundle bundle) {
        this.mAngle = bundle.getFloat("com.yd.photoeditor.actions.RotationAction.mAngle", this.mAngle);
        this.mThumbX = bundle.getFloat("com.yd.photoeditor.actions.RotationAction.mThumbX", this.mThumbX);
        this.mOldX = bundle.getFloat("com.yd.photoeditor.actions.RotationAction.mOldX", this.mOldX);
        this.mMaxAngle = bundle.getFloat("com.yd.photoeditor.actions.RotationAction.mMaxAngle", this.mMaxAngle);
        float[] floatArray = bundle.getFloatArray("com.yd.photoeditor.actions.RotationAction.mMatrix");
        if (floatArray != null) {
            if (this.mMatrix == null) {
                this.mMatrix = new Matrix();
            }
            this.mMatrix.setValues(floatArray);
        }
        float[] floatArray2 = bundle.getFloatArray("com.yd.photoeditor.actions.RotationAction.mAppliedMatrix");
        if (floatArray2 != null) {
            if (this.mAppliedMatrix == null) {
                this.mAppliedMatrix = new Matrix();
            }
            this.mAppliedMatrix.setValues(floatArray2);
        }
    }

    public float getAngle() {
        return this.mAngle;
    }

    private void init() {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setFilterBitmap(true);
    }

    public void setImage(Bitmap bitmap) {
        this.mImage = bitmap;
        invalidate();
    }

    public Bitmap applyTransform() {
        Bitmap bitmap = this.mImage;
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.mImage.getHeight(), this.mAppliedMatrix, true);
    }

    public Bitmap getImage() {
        return this.mImage;
    }

    public void init(Bitmap bitmap, int i, int i2) {
        this.mMatrix.reset();
        this.mAppliedMatrix.reset();
        this.mAngle = 0.0f;
        this.mImage = bitmap;
        float f = (float) (i / 2);
        this.mThumbX = f;
        double d = i;
        double d2 = i2;
        Double.isNaN(d);
        Double.isNaN(d2);
        this.mMaxAngle = (float) (Math.toDegrees(Math.atan(d / d2)) * 2.0d);
        float calculateScaleRatio = PhotoUtils.calculateScaleRatio(this.mImage.getWidth(), this.mImage.getHeight(), i, i2);
        this.mMatrix.postTranslate(((float) (i - this.mImage.getWidth())) / 2.0f, ((float) (i2 - this.mImage.getHeight())) / 2.0f);
        float f2 = 1.0f / calculateScaleRatio;
        this.mMatrix.postScale(f2, f2, f, (float) (i2 / 2));
        invalidate();
    }

    public void flip(int i) {
        if (i == 1) {
            this.mMatrix.postScale(1.0f, -1.0f, (float) (getWidth() / 2), (float) (getHeight() / 2));
            this.mAppliedMatrix.postScale(1.0f, -1.0f, (float) (this.mImage.getWidth() / 2), (float) (this.mImage.getHeight() / 2));
        } else if (i == 2) {
            this.mMatrix.postScale(-1.0f, 1.0f, (float) (getWidth() / 2), (float) (getHeight() / 2));
            this.mAppliedMatrix.postScale(-1.0f, 1.0f, (float) (this.mImage.getWidth() / 2), (float) (this.mImage.getHeight() / 2));
        }
        invalidate();
    }

    public void rotate(float f) {
        this.mMatrix.postRotate(f, (float) (getWidth() / 2), (float) (getHeight() / 2));
        this.mAppliedMatrix.postRotate(f, (float) (this.mImage.getWidth() / 2), (float) (this.mImage.getHeight() / 2));
        this.mAngle += f;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = this.mImage;
        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(this.mImage, this.mMatrix, this.mPaint);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mOldX = motionEvent.getX();
            return true;
        } else if (action != 2) {
            return true;
        } else {
            float max = Math.max(Math.min(this.mThumbX + (motionEvent.getX() - this.mOldX), (float) getWidth()), 0.0f);
            float f = max - this.mThumbX;
            this.mThumbX = max;
            if (f != 0.0f) {
                rotate(-((this.mMaxAngle * f) / ((float) getWidth())));
            }
            this.mOldX = motionEvent.getX();
            return true;
        }
    }
}
