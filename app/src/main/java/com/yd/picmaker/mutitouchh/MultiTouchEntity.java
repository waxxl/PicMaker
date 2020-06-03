package com.yd.picmaker.mutitouchh;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiTouchEntity implements Parcelable {
    protected static final int GRAB_AREA_SIZE = 40;
    private static final int UI_MODE_ANISOTROPIC_SCALE = 2;
    private static final int UI_MODE_ROTATE = 1;
    protected float mAngle;
    protected float mCenterX;
    protected float mCenterY;
    protected int mDisplayHeight;
    protected int mDisplayWidth;
    protected boolean mFirstLoad = true;
    protected float mGrabAreaX1;
    protected float mGrabAreaX2;
    protected float mGrabAreaY1;
    protected float mGrabAreaY2;
    protected int mHeight;
    protected boolean mIsGrabAreaSelected = false;
    protected boolean mIsLatestSelected = false;
    protected float mMaxX;
    protected float mMaxY;
    protected float mMinX;
    protected float mMinY;
    protected transient Paint mPaint = new Paint();
    protected float mScaleX;
    protected float mScaleY;
    protected float mStartMidX;
    protected float mStartMidY;
    protected int mUIMode = 1;
    protected int mWidth;
    List<PointF> mappedPoints = new ArrayList();
    Matrix matrix = new Matrix();
    float[] point = new float[2];

    public int describeContents() {
        return 0;
    }

    public abstract void draw(Canvas canvas);

    public abstract void load(Context context);

    public abstract void load(Context context, float f, float f2);

    public abstract void unload();

    public MultiTouchEntity() {
    }

    public MultiTouchEntity(Resources resources) {
        getMetrics(resources);
    }

    /* access modifiers changed from: protected */
    public void getMetrics(Resources resources) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        this.mDisplayWidth = resources.getConfiguration().orientation == 2 ? Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels) : Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
        this.mDisplayHeight = resources.getConfiguration().orientation == 2 ? Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) : Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public boolean setPos(MultiTouchController.PositionAndScale positionAndScale) {
        float f;
        float f2;
        if ((this.mUIMode & 2) != 0) {
            f = positionAndScale.getScaleX();
        } else {
            f = positionAndScale.getScale();
        }
        float f3 = f;
        if ((this.mUIMode & 2) != 0) {
            f2 = positionAndScale.getScaleY();
        } else {
            f2 = positionAndScale.getScale();
        }
        return setPos(positionAndScale.getXOff(), positionAndScale.getYOff(), f3, f2, positionAndScale.getAngle());
    }

    /* access modifiers changed from: protected */
    public boolean setPos(float f, float f2, float f3, float f4, float f5) {
        float[] calculateHalfDrawableSize = calculateHalfDrawableSize(f3, f4);
        float f6 = calculateHalfDrawableSize[0];
        float f7 = calculateHalfDrawableSize[1];
        this.mMinX = f - f6;
        this.mMinY = f2 - f7;
        this.mMaxX = f6 + f;
        this.mMaxY = f7 + f2;
        float f8 = this.mMaxX;
        this.mGrabAreaX1 = f8 - 40.0f;
        float f9 = this.mMaxY;
        this.mGrabAreaY1 = f9 - 40.0f;
        this.mGrabAreaX2 = f8;
        this.mGrabAreaY2 = f9;
        this.mCenterX = f;
        this.mCenterY = f2;
        this.mScaleX = f3;
        this.mScaleY = f4;
        this.mAngle = f5;
        return true;
    }

    /* access modifiers changed from: protected */
    public float[] calculateHalfDrawableSize(float f, float f2) {
        return new float[]{((float) (this.mWidth / 2)) * f, ((float) (this.mHeight / 2)) * f2};
    }

    public boolean containsPoint(float f, float f2) {
        return f >= this.mMinX && f <= this.mMaxX && f2 >= this.mMinY && f2 <= this.mMaxY;
    }

    public boolean contain(float f, float f2) {
        this.matrix.reset();
        this.matrix.setRotate((this.mAngle * 180.0f) / 3.1415927f, (this.mMaxX + this.mMinX) / 2.0f, (this.mMaxY + this.mMinY) / 2.0f);
        this.mappedPoints.clear();
        float[] fArr = this.point;
        fArr[0] = this.mMinX;
        fArr[1] = this.mMinY;
        this.matrix.mapPoints(fArr);
        List<PointF> list = this.mappedPoints;
        float[] fArr2 = this.point;
        list.add(new PointF(fArr2[0], fArr2[1]));
        float[] fArr3 = this.point;
        fArr3[0] = this.mMaxX;
        fArr3[1] = this.mMinY;
        this.matrix.mapPoints(fArr3);
        List<PointF> list2 = this.mappedPoints;
        float[] fArr4 = this.point;
        list2.add(new PointF(fArr4[0], fArr4[1]));
        float[] fArr5 = this.point;
        fArr5[0] = this.mMaxX;
        fArr5[1] = this.mMaxY;
        this.matrix.mapPoints(fArr5);
        List<PointF> list3 = this.mappedPoints;
        float[] fArr6 = this.point;
        list3.add(new PointF(fArr6[0], fArr6[1]));
        float[] fArr7 = this.point;
        fArr7[0] = this.mMinX;
        fArr7[1] = this.mMaxY;
        this.matrix.mapPoints(fArr7);
        List<PointF> list4 = this.mappedPoints;
        float[] fArr8 = this.point;
        list4.add(new PointF(fArr8[0], fArr8[1]));
        return contains(this.mappedPoints, new PointF(f, f2));
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

    public boolean grabAreaContainsPoint(float f, float f2) {
        return f >= this.mGrabAreaX1 && f <= this.mGrabAreaX2 && f2 >= this.mGrabAreaY1 && f2 <= this.mGrabAreaY2;
    }

    public void reload(Context context) {
        this.mFirstLoad = false;
        load(context, this.mCenterX, this.mCenterY);
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public float getCenterX() {
        return this.mCenterX;
    }

    public float getCenterY() {
        return this.mCenterY;
    }

    public float getScaleX() {
        return this.mScaleX;
    }

    public float getScaleY() {
        return this.mScaleY;
    }

    public float getAngle() {
        return this.mAngle;
    }

    public float getMinX() {
        return this.mMinX;
    }

    public float getMaxX() {
        return this.mMaxX;
    }

    public float getMinY() {
        return this.mMinY;
    }

    public float getMaxY() {
        return this.mMaxY;
    }

    public void setIsGrabAreaSelected(boolean z) {
        this.mIsGrabAreaSelected = z;
    }

    public boolean isGrabAreaSelected() {
        return this.mIsGrabAreaSelected;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBooleanArray(new boolean[]{this.mFirstLoad, this.mIsGrabAreaSelected, this.mIsLatestSelected});
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mDisplayWidth);
        parcel.writeInt(this.mDisplayHeight);
        parcel.writeFloat(this.mCenterX);
        parcel.writeFloat(this.mCenterY);
        parcel.writeFloat(this.mScaleX);
        parcel.writeFloat(this.mScaleY);
        parcel.writeFloat(this.mAngle);
        parcel.writeFloat(this.mMinX);
        parcel.writeFloat(this.mMaxX);
        parcel.writeFloat(this.mMinY);
        parcel.writeFloat(this.mMaxY);
        parcel.writeFloat(this.mGrabAreaX1);
        parcel.writeFloat(this.mGrabAreaY1);
        parcel.writeFloat(this.mGrabAreaX2);
        parcel.writeFloat(this.mGrabAreaY2);
        parcel.writeFloat(this.mStartMidX);
        parcel.writeFloat(this.mStartMidY);
        parcel.writeInt(this.mUIMode);
    }

    public void readFromParcel(Parcel parcel) {
        boolean[] zArr = new boolean[3];
        parcel.readBooleanArray(zArr);
        this.mFirstLoad = zArr[0];
        this.mIsGrabAreaSelected = zArr[1];
        this.mIsLatestSelected = zArr[2];
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
        this.mDisplayWidth = parcel.readInt();
        this.mDisplayHeight = parcel.readInt();
        this.mCenterX = parcel.readFloat();
        this.mCenterY = parcel.readFloat();
        this.mScaleX = parcel.readFloat();
        this.mScaleY = parcel.readFloat();
        this.mAngle = parcel.readFloat();
        this.mMinX = parcel.readFloat();
        this.mMaxX = parcel.readFloat();
        this.mMinY = parcel.readFloat();
        this.mMaxY = parcel.readFloat();
        this.mGrabAreaX1 = parcel.readFloat();
        this.mGrabAreaY1 = parcel.readFloat();
        this.mGrabAreaX2 = parcel.readFloat();
        this.mGrabAreaY2 = parcel.readFloat();
        this.mStartMidX = parcel.readFloat();
        this.mStartMidY = parcel.readFloat();
        this.mUIMode = parcel.readInt();
    }
}