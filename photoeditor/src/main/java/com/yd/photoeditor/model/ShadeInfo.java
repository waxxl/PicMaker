package com.yd.photoeditor.model;

public class ShadeInfo extends ItemInfo {
    private String mImage;
    private Language[] mNames;
    private long mPackageId;
    private String mType;

    public void setForeground(String str) {
        this.mImage = str;
    }

    public void setShadeType(String str) {
        this.mType = str;
    }

    public void setPackageId(long j) {
        this.mPackageId = j;
    }

    public String getForeground() {
        return this.mImage;
    }

    public String getShadeType() {
        return this.mType;
    }

    public long getPackageId() {
        return this.mPackageId;
    }

    public Language[] getLanguages() {
        return this.mNames;
    }
}
