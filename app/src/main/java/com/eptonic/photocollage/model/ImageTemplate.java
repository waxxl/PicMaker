package com.eptonic.photocollage.model;

import com.yd.photoeditor.model.Language;

public class ImageTemplate extends ItemInfo {

    private String mChild;
    private Language[] mNames;
    private long mPackageId;
    private String mPreview;
    private String mTemplate;

    public ImageTemplate() {
    }

    public void setPackageId(long j) {
        this.mPackageId = j;
    }

    public long getPackageId() {
        return this.mPackageId;
    }

    public void setPreview(String str) {
        this.mPreview = str;
    }

    public String getPreview() {
        return this.mPreview;
    }

    public void setTemplate(String str) {
        this.mTemplate = str;
    }

    public String getTemplate() {
        return this.mTemplate;
    }

    public void setChild(String str) {
        this.mChild = str;
    }

    public String getChild() {
        return this.mChild;
    }

    public Language[] getLanguages() {
        return this.mNames;
    }

    public void setLanguages(Language[] languageArr) {
        this.mNames = languageArr;
    }

}