package com.yd.picmaker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.yd.photoeditor.model.Language;

public class ImageTemplate extends ItemInfo {
    public static final Parcelable.Creator<ImageTemplate> CREATOR = new Parcelable.Creator<ImageTemplate>() {
        public ImageTemplate createFromParcel(Parcel parcel) {
            return new ImageTemplate(parcel);
        }

        public ImageTemplate[] newArray(int i) {
            return new ImageTemplate[i];
        }
    };
    private String mChild;
    private Language[] mNames;
    private long mPackageId;
    private String mPreview;
    private String mTemplate;

    public ImageTemplate() {
    }

    public void setPackageId(long j) {
        this.mPackageId = j;
    }

    public long getPackageId() {
        return this.mPackageId;
    }

    public void setPreview(String str) {
        this.mPreview = str;
    }

    public String getPreview() {
        return this.mPreview;
    }

    public void setTemplate(String str) {
        this.mTemplate = str;
    }

    public String getTemplate() {
        return this.mTemplate;
    }

    public void setChild(String str) {
        this.mChild = str;
    }

    public String getChild() {
        return this.mChild;
    }

    public Language[] getLanguages() {
        return this.mNames;
    }

    public void setLanguages(Language[] languageArr) {
        this.mNames = languageArr;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        Language[] languageArr = this.mNames;
        int length = (languageArr == null || languageArr.length <= 0) ? 0 : languageArr.length;
        parcel.writeInt(length);
        Language[] languageArr2 = this.mNames;
        if (languageArr2 != null && length > 0) {
            parcel.writeTypedArray(languageArr2, i);
        }
        parcel.writeLong(this.mPackageId);
        parcel.writeString(this.mPreview);
        parcel.writeString(this.mTemplate);
        parcel.writeString(this.mChild);
    }

    protected ImageTemplate(Parcel parcel) {
        super(parcel);
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.mNames = new Language[readInt];
            parcel.readTypedArray(this.mNames, Language.CREATOR);
        }
        this.mPackageId = parcel.readLong();
        this.mPreview = parcel.readString();
        this.mTemplate = parcel.readString();
        this.mChild = parcel.readString();
    }
}