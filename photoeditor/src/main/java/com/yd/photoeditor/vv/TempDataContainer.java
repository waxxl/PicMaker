package com.yd.photoeditor.vv;

import com.yd.photoeditor.listener.OnInstallStoreItemListener;
import java.util.ArrayList;
import java.util.List;

public class TempDataContainer {
    private static TempDataContainer instance;
    private final List<OnInstallStoreItemListener> mOnInstallStoreItemListeners = new ArrayList();

    public static TempDataContainer getInstance() {
        if (instance == null) {
            instance = new TempDataContainer();
        }
        return instance;
    }

    private TempDataContainer() {
    }

    public List<OnInstallStoreItemListener> getOnInstallStoreItemListeners() {
        return this.mOnInstallStoreItemListeners;
    }

    public void clear() {
        this.mOnInstallStoreItemListeners.clear();
    }
}
