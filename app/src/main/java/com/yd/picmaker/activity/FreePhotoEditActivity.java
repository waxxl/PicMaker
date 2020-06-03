package com.yd.picmaker.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.yd.picmaker.Listener.FreeEditInterface;
import com.yd.picmaker.R;
import com.yd.picmaker.manager.FreePhotoUiManager;
import com.yd.picmaker.util.PermissionUtils;
import com.yd.photoeditor.ui.activity.ImageProcessingActivity;

public class FreePhotoEditActivity extends BaseActivity implements FreeEditInterface {
    private FreePhotoUiManager mFreePhotoUiManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_edit);
        initToolBar();
        setTitle(getString(R.string.tab_freely));
        setFunction(R.drawable.save);
        mFreePhotoUiManager = new FreePhotoUiManager(this, this);
        mFreePhotoUiManager.initView();
        mFreePhotoUiManager.initTab();
        PermissionUtils.checkPermissionsGranted(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFreePhotoUiManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSelectRecyclerClose() {

    }

    @Override
    public void onTabSelect(int pos) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onSave() {

    }

}
