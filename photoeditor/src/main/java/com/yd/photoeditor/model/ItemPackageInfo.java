package com.yd.photoeditor.model;

import android.os.Parcel;

public class ItemPackageInfo extends XXXXXXXXXXXXXX {

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
}
