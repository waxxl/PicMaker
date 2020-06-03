package com.yd.photoeditor.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemInfo implements Parcelable {
    public static final int ADD_ITEM_TYPE = 2;
    public static final Creator<ItemInfo> CREATOR = new Creator<ItemInfo>() {
        public ItemInfo createFromParcel(Parcel parcel) {
            return new ItemInfo(parcel);
        }

        public ItemInfo[] newArray(int i) {
            return new ItemInfo[i];
        }
    };
    public static final int CUSTOM_CROP_TYPE = 4;
    public static final int DRAW_CROP_TYPE = 5;
    public static final int NORMAL_ITEM_TYPE = 0;
    public static final int PACKAGE_ITEM_TYPE = 1;
    public static final int SQUARE_CROP_TYPE = 3;
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_DELETED = "deleted";
    private long mId;
    private String mLastModified;
    private boolean mSelected = false;
    private String mSelectedThumbnail;
    private int mShowingType = 0;
    private String mStatus;
    private String mThumbnail;
    private String mTitle;

    public int describeContents() {
        return 0;
    }

    public ItemInfo() {
    }

    public void setLastModified(String str) {
        this.mLastModified = str;
    }

    public void setId(long j) {
        this.mId = j;
    }

    public void setStatus(String str) {
        this.mStatus = str;
    }

    public String getStatus() {
        return this.mStatus;
    }

    public long getId() {
        return this.mId;
    }

    public String getLastModified() {
        return this.mLastModified;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setSelected(boolean z) {
        this.mSelected = z;
    }

    public boolean isSelected() {
        return this.mSelected;
    }

    public void setShowingType(int i) {
        this.mShowingType = i;
    }

    public int getShowingType() {
        return this.mShowingType;
    }

    public void setThumbnail(String str) {
        this.mThumbnail = str;
    }

    public String getThumbnail() {
        return this.mThumbnail;
    }

    public void setSelectedThumbnail(String str) {
        this.mSelectedThumbnail = str;
    }

    public String getSelectedThumbnail() {
        return this.mSelectedThumbnail;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mTitle);
        parcel.writeString(this.mThumbnail);
        parcel.writeString(this.mSelectedThumbnail);
        parcel.writeBooleanArray(new boolean[]{this.mSelected});
        parcel.writeInt(this.mShowingType);
        parcel.writeString(this.mLastModified);
        parcel.writeString(this.mStatus);
        parcel.writeLong(this.mId);
    }

    protected ItemInfo(Parcel parcel) {
        this.mTitle = parcel.readString();
        this.mThumbnail = parcel.readString();
        this.mSelectedThumbnail = parcel.readString();
        boolean[] zArr = new boolean[1];
        parcel.readBooleanArray(zArr);
        this.mSelected = zArr[0];
        this.mShowingType = parcel.readInt();
        this.mLastModified = parcel.readString();
        this.mStatus = parcel.readString();
        this.mId = parcel.readLong();
    }
}
