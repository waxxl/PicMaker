package com.eptonic.photocollage.ui.view.frame;

import android.graphics.Matrix;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class FrameEntity implements Parcelable {
    public static final Creator<FrameEntity> CREATOR = new Creator<FrameEntity>() {
        public FrameEntity createFromParcel(Parcel parcel) {
            return new FrameEntity(parcel);
        }

        public FrameEntity[] newArray(int i) {
            return new FrameEntity[i];
        }
    };
    private Uri mImage;
    private final Matrix mMatrix;

    public int describeContents() {
        return 0;
    }

    public FrameEntity() {
        this.mMatrix = new Matrix();
    }

    private FrameEntity(Parcel parcel) {
        this.mMatrix = new Matrix();
        float[] fArr = new float[9];
        parcel.readFloatArray(fArr);
        this.mMatrix.setValues(fArr);
        this.mImage = parcel.readParcelable(Uri.class.getClassLoader());
    }

    public void setImage(Uri uri) {
        this.mImage = uri;
    }

    public Uri getImage() {
        return this.mImage;
    }

    public void setMatrix(Matrix matrix) {
        this.mMatrix.set(matrix);
    }

    public Matrix getMatrix() {
        return new Matrix(this.mMatrix);
    }

    public void writeToParcel(Parcel parcel, int i) {
        float[] fArr = new float[9];
        this.mMatrix.getValues(fArr);
        parcel.writeFloatArray(fArr);
        parcel.writeParcelable(this.mImage, i);
    }
}
