package com.yd.photoeditor.model;

public class EditedImageItem {
    private String mImage;
    private String mThumbnail;

    public void setImage(String str) {
        this.mImage = str;
    }

    public void setThumbnail(String str) {
        this.mThumbnail = str;
    }

    public String getImage() {
        return this.mImage;
    }

    public String getThumbnail() {
        return this.mThumbnail;
    }
}
