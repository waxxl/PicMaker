package com.yd.photoeditor.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemPackageInfo extends ItemInfo {
    public static final Creator<ItemPackageInfo> CREATOR = new Creator<ItemPackageInfo>() {
        public ItemPackageInfo createFromParcel(Parcel parcel) {
            return new ItemPackageInfo(parcel);
        }

        public ItemPackageInfo[] newArray(int i) {
            return new ItemPackageInfo[i];
        }
    };
    private String mBillingId;
    private String mFolder;
    private String mType;
    private String m_id;

    public void setFolder(String str) {
        this.mFolder = str;
    }

    public String getFolder() {
        return this.mFolder;
    }

    public String getIdString() {
        return this.m_id;
    }

    public void setIdString(String str) {
        this.m_id = str;
    }

    public void setType(String str) {
        this.mType = str;
    }

    public String getType() {
        return this.mType;
    }

    public void setBillingId(String str) {
        this.mBillingId = str;
    }

    public String getBillingId() {
        return this.mBillingId;
    }

    public ItemPackageInfo() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.m_id);
        parcel.writeString(this.mType);
        parcel.writeString(this.mFolder);
        parcel.writeString(this.mBillingId);
    }

    protected ItemPackageInfo(Parcel parcel) {
        super(parcel);
        this.m_id = parcel.readString();
        this.mType = parcel.readString();
        this.mFolder = parcel.readString();
        this.mBillingId = parcel.readString();
    }
}
