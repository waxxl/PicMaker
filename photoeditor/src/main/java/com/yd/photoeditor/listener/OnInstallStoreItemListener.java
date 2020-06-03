package com.yd.photoeditor.listener;

import com.yd.photoeditor.model.ItemPackageInfo;

public interface OnInstallStoreItemListener {
    void onFinishInstalling(ItemPackageInfo itemPackageInfo, boolean z);

    void onStartDownloading(ItemPackageInfo itemPackageInfo);
}
