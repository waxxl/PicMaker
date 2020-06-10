package com.yd.photoeditor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import java.util.ArrayList;
import java.util.List;

public class FingerPaintView extends View {
    public static final int DRAW_EFFECT_BLUR = 1;
    public static final int DRAW_EFFECT_EMBOSS = 2;
    public static final int DRAW_EFFECT_ERASE = 4;
    public static final int DRAW_EFFECT_NORMAL = 0;
    public static final int DRAW_EFFECT_SRC_A_TOP = 3;
    private static final float TOUCH_TOLERANCE = 4.0f;
    private MaskFilter mBlur;
    private int mColor = SupportMenu.CATEGORY_MASK;
    private int mEffect = 0;
    private MaskFilter mEmboss;
    private List<FingerPath> mFingerPathList = new ArrayList();
    private Paint mPaint;
    private float mPaintSize = 10.0f;
    private Path mTempPath;
    private float mX;
    private float mY;

    private void touchUp() {
    }

    public FingerPaintView(Context context) {
        super(context);
        init();
    }

    public FingerPaintView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public FingerPaintView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public void saveInstanceState(Bundle bundle) {
        bundle.putFloat("com.yd.photoeditor.view.FingerPaintView.mX", this.mX);
        bundle.putFloat("com.yd.photoeditor.view.FingerPaintView.mY", this.mY);
        bundle.putParcelableArrayList("com.yd.photoeditor.view.FingerPaintView.mFingerPathList", (ArrayList) this.mFingerPathList);
        bundle.putFloat("com.yd.photoeditor.view.FingerPaintView.mPaintSize", this.mPaintSize);
        bundle.putInt("com.yd.photoeditor.view.FingerPaintView.mColor", this.mColor);
        bundle.putInt("com.yd.photoeditor.view.FingerPaintView.mEffect", this.mEffect);
    }

    public void restoreInstanceState(Bundle bundle) {
        this.mX = bundle.getFloat("com.yd.photoeditor.view.FingerPaintView.mX", this.mX);
        this.mY = bundle.getFloat("com.yd.photoeditor.view.FingerPaintView.mY", this.mY);
        ArrayList parcelableArrayList = bundle.getParcelableArrayList("com.yd.photoeditor.view.FingerPaintView.mFingerPathList");
        if (parcelableArrayList != null) {
            this.mFingerPathList = parcelableArrayList;
        }
        this.mPaintSize = bundle.getFloat("com.yd.photoeditor.view.FingerPaintView.mPaintSize", this.mPaintSize);
        this.mColor = bundle.getInt("com.yd.photoeditor.view.FingerPaintView.mColor", this.mColor);
        this.mEffect = bundle.getInt("com.yd.photoeditor.view.FingerPaintView.mEffect", this.mEffect);
    }

    private void init() {
        this.mTempPath = new Path();
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setDither(true);
        this.mPaint.setColor(this.mColor);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setStrokeWidth(this.mPaintSize);
        this.mEmboss = new EmbossMaskFilter(new float[]{1.0f, 1.0f, 1.0f}, 0.4f, 6.0f, 3.5f);
        this.mBlur = new BlurMaskFilter(8.0f, BlurMaskFilter.Blur.NORMAL);
    }

    public void setPaintColor(int i) {
        this.mColor = i;
        this.mPaint.setColor(i);
    }

    public void setPaintSize(float f) {
        this.mPaintSize = f;
        this.mPaint.setStrokeWidth(this.mPaintSize);
    }

    public int getEffect() {
        return this.mEffect;
    }

    public void clear() {
        this.mFingerPathList.clear();
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (FingerPath next : this.mFingerPathList) {
            this.mTempPath.reset();
            this.mPaint.setColor(next.color);
            this.mPaint.setStrokeWidth(next.size);
            setPaintEffect(next.effect);
            int size = next.pointList.size();
            if (size > 0) {
                this.mTempPath.moveTo(next.pointList.get(0).x, next.pointList.get(0).y);
            }
            for (int i = 1; i < size; i++) {
                this.mTempPath.lineTo(next.pointList.get(i).x, next.pointList.get(i).y);
            }
            canvas.drawPath(this.mTempPath, this.mPaint);
        }
    }

    public Bitmap drawImage(Bitmap bitmap, float f) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        for (FingerPath next : this.mFingerPathList) {
            this.mTempPath.reset();
            this.mPaint.setColor(next.color);
            this.mPaint.setStrokeWidth(next.size * f);
            setPaintEffect(next.effect);
            int size = next.pointList.size();
            if (size > 0) {
                this.mTempPath.moveTo(next.pointList.get(0).x * f, next.pointList.get(0).y * f);
            }
            for (int i = 1; i < size; i++) {
                this.mTempPath.lineTo(next.pointList.get(i).x * f, next.pointList.get(i).y * f);
            }
            canvas.drawPath(this.mTempPath, this.mPaint);
        }
        Bitmap createBitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(createBitmap2);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        canvas.drawBitmap(createBitmap, 0.0f, 0.0f, paint);
        createBitmap.recycle();
        return createBitmap2;
    }

    private void touchStart(float f, float f2) {
        FingerPath fingerPath = new FingerPath();
        fingerPath.color = this.mColor;
        fingerPath.size = this.mPaintSize;
        fingerPath.effect = this.mEffect;
        fingerPath.pointList.add(new PointF(f, f2));
        this.mFingerPathList.add(fingerPath);
        this.mX = f;
        this.mY = f2;
    }

    private void touchMove(float f, float f2) {
        float abs = Math.abs(f - this.mX);
        float abs2 = Math.abs(f2 - this.mY);
        if (abs >= TOUCH_TOLERANCE || abs2 >= TOUCH_TOLERANCE) {
            if (this.mFingerPathList.size() > 0) {
                List<FingerPath> list = this.mFingerPathList;
                list.get(list.size() - 1).pointList.add(new PointF((this.mX + f) / 2.0f, (this.mY + f2) / 2.0f));
            }
            this.mX = f;
            this.mY = f2;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            touchStart(x, y);
            invalidate();
        } else if (action == 1) {
            touchUp();
            invalidate();
        } else if (action == 2) {
            touchMove(x, y);
            invalidate();
        }
        return true;
    }

    public void setPaintEffect(int i) {
        this.mEffect = i;
        this.mPaint.setMaskFilter(null);
        this.mPaint.setXfermode(null);
        this.mPaint.setAlpha(255);
        if (i == 1) {
            MaskFilter maskFilter = this.mPaint.getMaskFilter();
            MaskFilter maskFilter2 = this.mBlur;
            if (maskFilter != maskFilter2) {
                this.mPaint.setMaskFilter(maskFilter2);
            } else {
                this.mPaint.setMaskFilter(null);
            }
        } else if (i == 2) {
            MaskFilter maskFilter3 = this.mPaint.getMaskFilter();
            MaskFilter maskFilter4 = this.mEmboss;
            if (maskFilter3 != maskFilter4) {
                this.mPaint.setMaskFilter(maskFilter4);
            } else {
                this.mPaint.setMaskFilter(null);
            }
        } else if (i == 3) {
            this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            this.mPaint.setAlpha(128);
        } else if (i == 4) {
            this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
    }

    static class FingerPath implements Parcelable {
        public static final Creator<FingerPath> CREATOR = new Creator<FingerPath>() {
            public FingerPath createFromParcel(Parcel parcel) {
                return new FingerPath(parcel);
            }

            public FingerPath[] newArray(int i) {
                return new FingerPath[i];
            }
        };
        int color;
        int effect;
        List<PointF> pointList;
        float size;

        public int describeContents() {
            return 0;
        }

        private FingerPath() {
            this.pointList = new ArrayList();
        }

        private FingerPath(Parcel parcel) {
            this.pointList = new ArrayList();
            Parcelable[] readParcelableArray = parcel.readParcelableArray(PointF.class.getClassLoader());
            for (Parcelable parcelable : readParcelableArray) {
                this.pointList.add((PointF) parcelable);
            }
            this.color = parcel.readInt();
            this.size = parcel.readFloat();
            this.effect = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            PointF[] pointFArr = new PointF[this.pointList.size()];
            for (int i2 = 0; i2 < this.pointList.size(); i2++) {
                pointFArr[i2] = this.pointList.get(i2);
            }
            parcel.writeParcelableArray(pointFArr, i);
            parcel.writeInt(this.color);
            parcel.writeFloat(this.size);
            parcel.writeInt(this.effect);
        }
    }
}
