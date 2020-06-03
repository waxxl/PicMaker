package com.yd.picmaker.model;
import com.yd.picmaker.template.PhotoItem;
import com.yd.picmaker.view.PhotoLayout;

import java.util.ArrayList;
import java.util.List;

public class TemplateItem extends ImageTemplate {
    private String mHeader;
    private boolean mIsAds = false;
    private boolean mIsHeader = false;
    private List<PhotoItem> mPhotoItemList = new ArrayList();
    private int mSectionFirstPosition;
    private int mSectionManager;

    public TemplateItem() {
    }

    public TemplateItem(ImageTemplate imageTemplate) {
        setLanguages(imageTemplate.getLanguages());
        setPackageId(imageTemplate.getPackageId());
        setPreview(imageTemplate.getPreview());
        setTemplate(imageTemplate.getTemplate());
        setChild(imageTemplate.getChild());
        setTitle(imageTemplate.getTitle());
        setThumbnail(imageTemplate.getThumbnail());
        setSelectedThumbnail(imageTemplate.getSelectedThumbnail());
        setSelected(imageTemplate.isSelected());
        setShowingType(imageTemplate.getShowingType());
        setLastModified(imageTemplate.getLastModified());
        setStatus(imageTemplate.getStatus());
        setId(imageTemplate.getId());
        this.mPhotoItemList = PhotoLayout.parseImageTemplate(imageTemplate);
    }

    public void setHeader(String str) {
        this.mHeader = str;
    }

    public String getHeader() {
        return this.mHeader;
    }

    public List<PhotoItem> getPhotoItemList() {
        return this.mPhotoItemList;
    }

    public void setSectionFirstPosition(int i) {
        this.mSectionFirstPosition = i;
    }

    public int getSectionFirstPosition() {
        return this.mSectionFirstPosition;
    }

    public void setSectionManager(int i) {
        this.mSectionManager = i;
    }

    public int getSectionManager() {
        return this.mSectionManager;
    }

    public boolean isHeader() {
        return this.mIsHeader;
    }

    public void setIsHeader(boolean z) {
        this.mIsHeader = z;
    }

    public void setIsAds(boolean z) {
        this.mIsAds = z;
    }

    public boolean isAds() {
        return this.mIsAds;
    }
}