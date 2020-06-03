package com.yd.photoeditor.model;

public class CropInfo extends ItemInfo {
    private String mBackground;
    private String mForeground;
    private long mPackageId;

    public void setPackageId(long j) {
        this.mPackageId = j;
    }

    public long getPackageId() {
        return this.mPackageId;
    }

    public String getForeground() {
        return this.mForeground;
    }

    public String getBackground() {
        return this.mBackground;
    }

    public void setForeground(String str) {
        this.mForeground = str;
    }

    public void setBackground(String str) {
        this.mBackground = str;
    }
}
