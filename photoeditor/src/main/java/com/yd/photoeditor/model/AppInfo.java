package com.yd.photoeditor.model;

public class AppInfo {
    private boolean mActive;
    private String mAppName;
    private String mAppleId;
    private String mPackageName;
    private String mThumbnail;
    private String mUrl;

    public String getAppName() {
        return this.mAppName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getAppleId() {
        return this.mAppleId;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public String getThumbnail() {
        return this.mThumbnail;
    }

    public boolean isActive() {
        return this.mActive;
    }
}
