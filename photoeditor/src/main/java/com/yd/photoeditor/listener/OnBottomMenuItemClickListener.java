package com.yd.photoeditor.listener;

import com.yd.photoeditor.model.ItemInfo;

public interface OnBottomMenuItemClickListener {
    void onDeleteButtonClick(int i, ItemInfo itemInfo);

    void onEditorItemClick(int i, ItemInfo itemInfo);

    void onMenuItemLongClick(int i, ItemInfo itemInfo);
}
