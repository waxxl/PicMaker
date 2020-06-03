package com.yd.picmaker.Listener;


import com.yd.photoeditor.model.ItemPackageInfo;

public interface OnDownloadedPackageClickListener {
    void onDeleteButtonClick(int i, ItemPackageInfo itemPackageInfo);

    void onItemClick(int i, ItemPackageInfo itemPackageInfo);
}
