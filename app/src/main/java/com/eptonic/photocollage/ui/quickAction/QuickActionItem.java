package com.eptonic.photocollage.ui.quickAction;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class QuickActionItem {
    private int actionId;
    private Drawable icon;
    private boolean selected;
    private boolean sticky;
    private Bitmap thumb;
    private String title;

    public QuickActionItem(int i, String str, Drawable drawable) {
        this.actionId = -1;
        this.title = str;
        this.icon = drawable;
        this.actionId = i;
    }

    public QuickActionItem() {
        this(-1, null, null);
    }

    public QuickActionItem(int i, String str) {
        this(i, str, null);
    }

    public QuickActionItem(Drawable drawable) {
        this(-1, null, drawable);
    }

    public QuickActionItem(int i, Drawable drawable) {
        this(i, null, drawable);
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setIcon(Drawable drawable) {
        this.icon = drawable;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setActionId(int i) {
        this.actionId = i;
    }

    public int getActionId() {
        return this.actionId;
    }

    public void setSticky(boolean z) {
        this.sticky = z;
    }

    public boolean isSticky() {
        return this.sticky;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setThumb(Bitmap bitmap) {
        this.thumb = bitmap;
    }

    public Bitmap getThumb() {
        return this.thumb;
    }
}