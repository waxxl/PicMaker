package com.eptonic.photocollage.model;

import java.util.ArrayList;
import java.util.List;

public class GalleryAlbum {
    private long mAlbumId;
    private String mAlbumName;
    private final List<String> mImageList = new ArrayList();
    private String mTakenDate;

    public GalleryAlbum(long j, String str) {
        this.mAlbumId = j;
        this.mAlbumName = str;
    }

    public void setAlbumId(long j) {
        this.mAlbumId = j;
    }

    public void setAlbumName(String str) {
        this.mAlbumName = str;
    }

    public void setTakenDate(String str) {
        this.mTakenDate = str;
    }

    public List<String> getImageList() {
        return this.mImageList;
    }

    public long getAlbumId() {
        return this.mAlbumId;
    }

    public String getAlbumName() {
        return this.mAlbumName;
    }

    public String getTakenDate() {
        return this.mTakenDate;
    }

    @Override
    public String toString() {
        return "GalleryAlbum{" +
                "mAlbumId=" + mAlbumId +
                ", mAlbumName='" + mAlbumName + '\'' +
                ", mImageList=" + mImageList +
                ", mTakenDate='" + mTakenDate + '\'' +
                '}';
    }
}
