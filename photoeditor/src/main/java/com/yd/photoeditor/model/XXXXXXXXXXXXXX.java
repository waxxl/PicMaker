package com.yd.photoeditor.model;

public class XXXXXXXXXXXXXX {
    private long mId;
    private boolean mSelected = false;
    private String mSeectedNail;
    private int typex = 0;
    private String unused;
    private String mThumbnail;
    private String feiw;

    public XXXXXXXXXXXXXX() {
    }

    public void setId(long j) {
        this.mId = j;
    }

    public long getId() {
        return this.mId;
    }

    public void setTitle(String str) {
        this.feiw = str;
    }

    public String getTitle() {
        return this.feiw;
    }

    public void setSelected(boolean z) {
        this.mSelected = z;
    }

    public boolean isSelected() {
        return this.mSelected;
    }

    public void setShowingType(int i) {
        this.typex = i;
    }

    public int getShowingType() {
        return this.typex;
    }

    public void setThumbnail(String str) {
        this.mThumbnail = str;
    }

    public String getThumbnail() {
        return this.mThumbnail;
    }

    public void setSelectedThumbnail(String str) {
        this.mSeectedNail = str;
    }

    public String getSelectedThumbnail() {
        return this.mSeectedNail;
    }

}
