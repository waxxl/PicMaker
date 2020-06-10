package com.yd.photoeditor.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Language  {

    private String mName;
    private String mValue;

    public int describeContents() {
        return 0;
    }

    public Language() {
    }

    public void setName(String str) {
        this.mName = str;
    }

    public String getName() {
        return this.mName;
    }

    public void setValue(String str) {
        this.mValue = str;
    }

    public String getValue() {
        return this.mValue;
    }
}
