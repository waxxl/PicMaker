package com.eptonic.photocollage.presenter.listener;

import com.google.android.material.tabs.TabLayout;

public interface FreeEditInterface {
    void onSelectRecyclerClose();

    void onTabSelect(int pos);

    void onTabUnselected(TabLayout.Tab tab);

    void onSave();
}