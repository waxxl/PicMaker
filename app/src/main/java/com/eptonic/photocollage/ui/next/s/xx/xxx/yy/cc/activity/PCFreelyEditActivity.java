package com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity;

import android.content.Intent;
import androidx.annotation.Nullable;

import com.eptonic.photocollage.ui.wrapper.dah.ghjf.gfhs.fhsj.ghsj.hfjsh.fhsj.fhsj.hfjs.fdj.sjhfs.fgsj.jgks.vbb.ActivityHandler;
import com.eptonic.photocollage.ui.wrapper.dah.interfaces.FreeEditInterfaces;
import com.google.android.material.tabs.TabLayout;
import com.eptonic.photocollage.presenter.listener.FreeEditInterface;
import com.eptonic.photocollage.R;
import com.eptonic.photocollage.ui.wrapper.dah.ghjf.gfhs.fhsj.ghsj.hfjsh.fhsj.fhsj.hfjs.fdj.sjhfs.fgsj.jgks.vbb.uiManager.FreePhotoUiManager;

public class PCFreelyEditActivity extends MyBaseActivity implements FreeEditInterface {
    private FreeEditInterfaces mInterfaces;
    public FreeEditInterfaces mFreePhotoUiManagerProxy;
    private ActivityHandler activityHandler;

    @Override
    public int getId() {
        return R.layout.activity_free_edit;
    }

    @Override
    public void initView() {
        activityHandler = new ActivityHandler();
        mInterfaces= new FreePhotoUiManager(this, this);
        mFreePhotoUiManagerProxy = (FreeEditInterfaces) activityHandler.newProxyInstance(mInterfaces);
        mFreePhotoUiManagerProxy.initView2();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFreePhotoUiManagerProxy.onActivityResult(requestCode, resultCode, data);
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
