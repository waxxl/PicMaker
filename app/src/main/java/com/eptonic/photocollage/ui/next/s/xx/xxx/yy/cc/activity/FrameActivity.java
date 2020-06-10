package com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eptonic.photocollage.R;
import com.eptonic.photocollage.adapter.TemplateViewHolder;
import com.eptonic.photocollage.model.TemplateItem;
import com.eptonic.photocollage.ui.wrapper.dah.ghjf.gfhs.fhsj.ghsj.hfjsh.fhsj.fhsj.hfjs.fdj.sjhfs.fgsj.jgks.vbb.ActivityHandler;
import com.eptonic.photocollage.ui.wrapper.dah.ghjf.gfhs.fhsj.ghsj.hfjsh.fhsj.fhsj.hfjs.fdj.sjhfs.fgsj.jgks.vbb.uiManager.FrameManager;
import com.eptonic.photocollage.ui.wrapper.dah.interfaces.TemplateInterface;
import com.eptonic.photocollage.util.PermissionUtils;
public class FrameActivity extends MyBaseActivity implements TemplateViewHolder.OnTemplateItemClickListener {
    public static final int REQUEST_SELECT_PHOTO = 0x2010;
    private TemplateItem mTemplateItem;
    private FrameManager frameManager;
    private TemplateInterface frameManagerProxy;


    @Override
    public int getId() {
        return R.layout.activity_template;
    }

    @Override
    public void initView() {
        ActivityHandler activityHandler = new ActivityHandler();
        frameManager = new FrameManager(this, this::onTemplateItemClick);
        frameManagerProxy = (TemplateInterface) activityHandler.newProxyInstance(frameManager);
        frameManagerProxy.initView();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        frameManagerProxy.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted 授予权限
                    Intent intent = new Intent(this, GalleryActivity.class);
                    intent.putExtra(GalleryActivity.MAX_IMAGE_COUNT, mTemplateItem.getPhotoItemList().size());
                    intent.putExtra(GalleryActivity.IS_MAX_IMAGE_COUNT, true);
                    startActivityForResult(intent, REQUEST_SELECT_PHOTO);
                } else {
                    gotoAppSetting();
                }
                break;
            default:
                break;


        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void gotoAppSetting() {
        Toast.makeText(this, getString(R.string.permission_denied_and_guide_to_setting), Toast.LENGTH_LONG).show();
        PermissionUtils.goAppSetting(this);
    }

    @Override
    public void onTemplateItemClick(TemplateItem templateItem) {
        mTemplateItem = templateItem;
        frameManagerProxy.onTemplateItemClick(templateItem);
    }
}