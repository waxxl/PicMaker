package com.yd.picmaker.template;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.MotionEvent;

public class MultiTouchHandler implements Parcelable {
    public static final Parcelable.Creator<MultiTouchHandler> CREATOR = new C23901();
    private static final int DRAG = 1;
    private static final int NONE = 0;
    private static final int ZOOM = 2;
    private PointF mCheckingPosition;

    /* renamed from: mD */
    private float f4274mD;
    private boolean mEnableRotation;
    private boolean mEnableTranslateX;
    private boolean mEnableTranslateY;
    private boolean mEnableZoom;
    private float[] mLastEvent;
    private Matrix mMatrix;
    private float mMaxPositionOffset;
    private PointF mMid;
    private int mMode;
    private float mNewRot;
    private float mOldDist;
    private PointF mOldImagePosition;
    private Matrix mSavedMatrix;
    private float mScale;
    private Matrix mScaleMatrix;
    private Matrix mScaleSavedMatrix;
    private PointF mStart;

    public int describeContents() {
        return 0;
    }

    /* synthetic */ MultiTouchHandler(Parcel parcel, C23901 r2) {
        this(parcel);
    }

    public MultiTouchHandler() {
        this.mMatrix = new Matrix();
        this.mSavedMatrix = new Matrix();
        this.mMode = 0;
        this.mStart = new PointF();
        this.mMid = new PointF();
        this.mOldDist = 1.0f;
        this.f4274mD = 0.0f;
        this.mNewRot = 0.0f;
        this.mLastEvent = null;
        this.mEnableRotation = false;
        this.mEnableZoom = true;
        this.mEnableTranslateX = true;
        this.mEnableTranslateY = true;
        this.mScale = 1.0f;
        this.mScaleMatrix = new Matrix();
        this.mScaleSavedMatrix = new Matrix();
        this.mMaxPositionOffset = -1.0f;
        this.mOldImagePosition = new PointF(0.0f, 0.0f);
        this.mCheckingPosition = new PointF(0.0f, 0.0f);
    }

    public void setMatrix(Matrix matrix) {
        this.mMatrix.set(matrix);
        this.mSavedMatrix.set(matrix);
        this.mScaleMatrix.reset();
        this.mScaleSavedMatrix.reset();
    }

    public void setMatrices(Matrix matrix, Matrix matrix2) {
        this.mMatrix.set(matrix);
        this.mSavedMatrix.set(matrix);
        this.mScaleMatrix.set(matrix2);
        this.mScaleSavedMatrix.set(matrix2);
    }

    public void reset() {
        this.mMatrix.reset();
        this.mSavedMatrix.reset();
        this.mMode = 0;
        this.mStart.set(0.0f, 0.0f);
        this.mMid.set(0.0f, 0.0f);
        this.mOldDist = 1.0f;
        this.f4274mD = 0.0f;
        this.mNewRot = 0.0f;
        this.mLastEvent = null;
        this.mEnableRotation = false;
        this.mScaleMatrix.reset();
        this.mScaleSavedMatrix.reset();
    }

    public void setMaxPositionOffset(float f) {
        this.mMaxPositionOffset = f;
    }

    public void touch(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    int i = this.mMode;
                    if (i == 1) {
                        this.mMatrix.set(this.mSavedMatrix);
                        this.mScaleMatrix.set(this.mScaleSavedMatrix);
                        this.mCheckingPosition.set(this.mOldImagePosition.x, this.mOldImagePosition.y);
                        float x = motionEvent.getX() - this.mStart.x;
                        float y = motionEvent.getY() - this.mStart.y;
                        this.mCheckingPosition.x += x;
                        this.mCheckingPosition.y += y;
                        float f = 0.0f;
                        if (!this.mEnableTranslateX) {
                            if (this.mCheckingPosition.y > this.mMaxPositionOffset) {
                                float f2 = this.mCheckingPosition.y;
                                float f3 = this.mMaxPositionOffset;
                                y -= f2 - f3;
                                this.mCheckingPosition.y = f3;
                            } else if (this.mCheckingPosition.y < (-this.mMaxPositionOffset)) {
                                float f4 = this.mCheckingPosition.y;
                                float f5 = this.mMaxPositionOffset;
                                y -= f4 + f5;
                                this.mCheckingPosition.y = -f5;
                            }
                            x = 0.0f;
                        }
                        if (this.mEnableTranslateY) {
                            f = y;
                        } else if (this.mCheckingPosition.x > this.mMaxPositionOffset) {
                            float f6 = this.mCheckingPosition.x;
                            float f7 = this.mMaxPositionOffset;
                            x -= f6 - f7;
                            this.mCheckingPosition.x = f7;
                        } else if (this.mCheckingPosition.x < (-this.mMaxPositionOffset)) {
                            float f8 = this.mCheckingPosition.x;
                            float f9 = this.mMaxPositionOffset;
                            x -= f8 + f9;
                            this.mCheckingPosition.x = -f9;
                        }
                        this.mMatrix.postTranslate(x, f);
                        Matrix matrix = this.mScaleMatrix;
                        float f10 = this.mScale;
                        matrix.postTranslate(x * f10, f * f10);
                        return;
                    } else if (i == 2 && this.mEnableZoom) {
                        float spacing = spacing(motionEvent);
                        if (spacing > 10.0f) {
                            this.mMatrix.set(this.mSavedMatrix);
                            this.mScaleMatrix.set(this.mScaleSavedMatrix);
                            float f11 = spacing / this.mOldDist;
                            this.mMatrix.postScale(f11, f11, this.mMid.x, this.mMid.y);
                            this.mScaleMatrix.postScale(f11, f11, this.mMid.x * this.mScale, this.mMid.y * this.mScale);
                        }
                        if (this.mEnableRotation && this.mLastEvent != null && motionEvent.getPointerCount() == 2) {
                            this.mNewRot = rotation(motionEvent);
                            midPoint(this.mMid, motionEvent);
                            float f12 = this.mNewRot - this.f4274mD;
                            this.mMatrix.postRotate(f12, this.mMid.x, this.mMid.y);
                            this.mScaleMatrix.postRotate(f12, this.mMid.x * this.mScale, this.mMid.y * this.mScale);
                            return;
                        }
                        return;
                    } else {
                        return;
                    }
                } else if (action == 5) {
                    this.mOldDist = spacing(motionEvent);
                    if (this.mOldDist > 10.0f) {
                        this.mSavedMatrix.set(this.mMatrix);
                        this.mScaleSavedMatrix.set(this.mScaleMatrix);
                        midPoint(this.mMid, motionEvent);
                        this.mMode = 2;
                    }
                    this.mLastEvent = new float[4];
                    this.mLastEvent[0] = motionEvent.getX(0);
                    this.mLastEvent[1] = motionEvent.getX(1);
                    this.mLastEvent[2] = motionEvent.getY(0);
                    this.mLastEvent[3] = motionEvent.getY(1);
                    this.f4274mD = rotation(motionEvent);
                    return;
                } else if (action != 6) {
                    return;
                }
            }
            this.mMode = 0;
            this.mLastEvent = null;
            return;
        }
        this.mSavedMatrix.set(this.mMatrix);
        this.mScaleSavedMatrix.set(this.mScaleMatrix);
        this.mStart.set(motionEvent.getX(), motionEvent.getY());
        this.mOldImagePosition.set(this.mCheckingPosition.x, this.mCheckingPosition.y);
        this.mMode = 1;
        this.mLastEvent = null;
    }

    public Matrix getMatrix() {
        return this.mMatrix;
    }

    public Matrix getScaleMatrix() {
        return this.mScaleMatrix;
    }

    public void setScale(float f) {
        this.mScale = f;
    }

    public void setEnableRotation(boolean z) {
        this.mEnableRotation = z;
    }

    public void setEnableZoom(boolean z) {
        this.mEnableZoom = z;
    }

    public void setEnableTranslateX(boolean z) {
        this.mEnableTranslateX = z;
    }

    public void setEnableTranslateY(boolean z) {
        this.mEnableTranslateY = z;
    }

    private float spacing(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((x * x) + (y * y));
    }

    private void midPoint(PointF pointF, MotionEvent motionEvent) {
        pointF.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
    }

    private float rotation(MotionEvent motionEvent) {
        return (float) Math.toDegrees(Math.atan2(motionEvent.getY(0) - motionEvent.getY(1), motionEvent.getX(0) - motionEvent.getX(1)));
    }

    /* renamed from: dauroi.photoeditor.view.MultiTouchHandler$1 */
    static class C23901 implements Parcelable.Creator<MultiTouchHandler> {
        C23901() {
        }

        public MultiTouchHandler createFromParcel(Parcel parcel) {
            return new MultiTouchHandler(parcel, null);
        }

        public MultiTouchHandler[] newArray(int i) {
            return new MultiTouchHandler[i];
        }
    }

    private MultiTouchHandler(Parcel parcel) {
        this.mMatrix = new Matrix();
        this.mSavedMatrix = new Matrix();
        this.mMode = 0;
        this.mStart = new PointF();
        this.mMid = new PointF();
        this.mOldDist = 1.0f;
        this.f4274mD = 0.0f;
        this.mNewRot = 0.0f;
        this.mLastEvent = null;
        this.mEnableRotation = false;
        this.mEnableZoom = true;
        this.mEnableTranslateX = true;
        this.mEnableTranslateY = true;
        this.mScale = 1.0f;
        this.mScaleMatrix = new Matrix();
        this.mScaleSavedMatrix = new Matrix();
        this.mMaxPositionOffset = -1.0f;
        this.mOldImagePosition = new PointF(0.0f, 0.0f);
        this.mCheckingPosition = new PointF(0.0f, 0.0f);
        float[] fArr = new float[9];
        parcel.readFloatArray(fArr);
        this.mMatrix = new Matrix();
        this.mMatrix.setValues(fArr);
        float[] fArr2 = new float[9];
        parcel.readFloatArray(fArr2);
        this.mSavedMatrix = new Matrix();
        this.mSavedMatrix.setValues(fArr2);
        this.mMode = parcel.readInt();
        this.mStart = parcel.readParcelable(PointF.class.getClassLoader());
        this.mMid = parcel.readParcelable(PointF.class.getClassLoader());
        this.mOldDist = parcel.readFloat();
        this.f4274mD = parcel.readFloat();
        this.mNewRot = parcel.readFloat();
        boolean[] zArr = new boolean[4];
        parcel.readBooleanArray(zArr);
        this.mEnableRotation = zArr[0];
        this.mEnableZoom = zArr[1];
        this.mEnableTranslateX = zArr[2];
        this.mEnableTranslateY = zArr[3];
        this.mScale = parcel.readFloat();
        float[] fArr3 = new float[9];
        parcel.readFloatArray(fArr3);
        this.mScaleMatrix = new Matrix();
        this.mScaleMatrix.setValues(fArr3);
        float[] fArr4 = new float[9];
        parcel.readFloatArray(fArr4);
        this.mScaleSavedMatrix = new Matrix();
        this.mScaleSavedMatrix.setValues(fArr4);
        this.mMaxPositionOffset = parcel.readFloat();
        this.mOldImagePosition = parcel.readParcelable(PointF.class.getClassLoader());
        this.mCheckingPosition = parcel.readParcelable(PointF.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        float[] fArr = new float[9];
        this.mMatrix.getValues(fArr);
        parcel.writeFloatArray(fArr);
        float[] fArr2 = new float[9];
        this.mSavedMatrix.getValues(fArr2);
        parcel.writeFloatArray(fArr2);
        parcel.writeInt(this.mMode);
        parcel.writeParcelable(this.mStart, i);
        parcel.writeParcelable(this.mMid, i);
        parcel.writeFloat(this.mOldDist);
        parcel.writeFloat(this.f4274mD);
        parcel.writeFloat(this.mNewRot);
        parcel.writeBooleanArray(new boolean[]{this.mEnableRotation, this.mEnableZoom, this.mEnableTranslateX, this.mEnableTranslateY});
        parcel.writeFloat(this.mScale);
        float[] fArr3 = new float[9];
        this.mScaleMatrix.getValues(fArr3);
        parcel.writeFloatArray(fArr3);
        float[] fArr4 = new float[9];
        this.mScaleSavedMatrix.getValues(fArr4);
        parcel.writeFloatArray(fArr4);
        parcel.writeFloat(this.mMaxPositionOffset);
        parcel.writeParcelable(this.mOldImagePosition, i);
        parcel.writeParcelable(this.mCheckingPosition, i);
    }
}
