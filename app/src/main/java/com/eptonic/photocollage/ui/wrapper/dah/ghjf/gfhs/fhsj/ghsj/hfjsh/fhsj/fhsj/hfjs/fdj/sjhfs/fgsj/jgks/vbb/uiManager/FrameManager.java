package com.eptonic.photocollage.ui.wrapper.dah.ghjf.gfhs.fhsj.ghsj.hfjsh.fhsj.fhsj.hfjs.fdj.sjhfs.fgsj.jgks.vbb.uiManager;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eptonic.photocollage.R;
import com.eptonic.photocollage.adapter.TemplateAdapter;
import com.eptonic.photocollage.adapter.TemplateViewHolder;
import com.eptonic.photocollage.model.TemplateItem;
import com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity.FrameActivity;
import com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity.FrameDetailActivity;
import com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity.GalleryActivity;
import com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity.PCBaseTemplateDetailActivity;
import com.eptonic.photocollage.ui.wrapper.dah.interfaces.TemplateInterface;
import com.eptonic.photocollage.util.Constants;
import com.eptonic.photocollage.util.FrameImageUtils;
import com.eptonic.photocollage.util.PermissionUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class FrameManager implements TemplateInterface {
    private FrameActivity activity;
    private TemplateViewHolder.OnTemplateItemClickListener listener;
    private RecyclerView templateRecycler;
    private final ArrayList<TemplateItem> mTemplateItemList = new ArrayList<>();
    private final ArrayList<TemplateItem> mAllTemplateItemList = new ArrayList<>();
    private final int mImageInTemplateCount = 2;
    private TemplateItem mTemplateItem;
    public FrameManager(FrameActivity activity, TemplateViewHolder.OnTemplateItemClickListener listener) {
        this.activity = activity;
        this.listener = listener;
    }
    @Override
    public void initView() {
        activity.initToolBar();
        activity.setTitle(activity.getString(R.string.tab_frame));
        templateRecycler = activity.findViewById(R.id.template_recycler);
        loadTemplateImages();
        templateRecycler.setHasFixedSize(false);
        templateRecycler.setLayoutManager(new GridLayoutManager(activity, 3));
        TemplateAdapter adapter = new TemplateAdapter(activity, 1, mAllTemplateItemList, listener);
        templateRecycler.setAdapter(adapter);
    }

    @Override
    public void loadTemplateImages() {
        mAllTemplateItemList.clear();
        mAllTemplateItemList.addAll(FrameImageUtils.loadFrames());
        mTemplateItemList.clear();
        if (mImageInTemplateCount > 0) {
            Iterator<TemplateItem> it = this.mAllTemplateItemList.iterator();
            while (it.hasNext()) {
                TemplateItem next = it.next();
                if (next.getPhotoItemList().size() == mImageInTemplateCount) {
                    mTemplateItemList.add(next);
                }
            }
            return;
        }
        mTemplateItemList.addAll(mAllTemplateItemList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (FrameActivity.REQUEST_SELECT_PHOTO == requestCode && resultCode == activity.RESULT_OK) {
            TemplateItem templateItem = mAllTemplateItemList.get(0);
            Intent intent = new Intent(activity, FrameDetailActivity.class);
            intent.putExtra(Constants.EXTRA_IMAGE_IN_TEMPLATE_COUNT, templateItem.getPhotoItemList().size());
            intent.putExtra(Constants.EXTRA_IS_FRAME_IMAGE, false);
            intent.putExtra(Constants.EXTRA_SELECTED_TEMPLATE_INDEX, 1);

            if(mTemplateItem != null) {
                intent.putExtra(Constants.EXTRA_TITLE, mTemplateItem.getTitle());
            }

            intent.putExtra(Constants.EXTRA_IMAGE_PATHS, data.getStringArrayListExtra(GalleryActivity.SELECT_ITEMS));
            activity.startActivity(intent);
        }
    }

    @Override
    public void onTemplateItemClick(TemplateItem templateItem) {
        mTemplateItem = templateItem;
        Intent intent = new Intent(activity, GalleryActivity.class);
        intent.putExtra(GalleryActivity.MAX_IMAGE_COUNT, templateItem.getPhotoItemList().size());
        intent.putExtra(GalleryActivity.IS_MAX_IMAGE_COUNT, true);
        if (PermissionUtils.checkPermissionsGranted(activity)) {
            activity.startActivityForResult(intent, FrameActivity.REQUEST_SELECT_PHOTO);
        }
    }
}
