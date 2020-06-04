package com.yd.photoeditor.actions;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yd.photoeditor.R;
import com.yd.photoeditor.config.ALog;
import com.yd.photoeditor.config.Constants;
import com.yd.photoeditor.database.table.ItemPackageTable;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;
import com.yd.photoeditor.listener.ApplyFilterListener;
import com.yd.photoeditor.model.FilterInfo;
import com.yd.photoeditor.model.ItemInfo;
import com.yd.photoeditor.task.ApplyFilterTask;
import com.yd.photoeditor.ui.activity.ImageProcessingActivity;
import com.yd.photoeditor.utils.PhotoUtils;

import java.util.ArrayList;

public class EffectAction extends PackageAction {
    private static final String TAG = EffectAction.class.getSimpleName();

    public String getActionName() {
        return "EffectAction";
    }

    public EffectAction(ImageProcessingActivity imageProcessingActivity) {
        super(imageProcessingActivity, ItemPackageTable.FILTER_TYPE);
    }

    public View inflateMenuView() {
        this.mRootActionView = LayoutInflater.from(mActivity).inflate(R.layout.photo_editor_action_effect, (ViewGroup) null);
        return this.mRootActionView;
    }

    public void attach() {
        mActivity.attachBottomRecycler(0);
        mMenuItems = mActivity.mEffectInfos;
        super.attach();
        mActivity.getNormalImageView().setVisibility(View.GONE);
        mActivity.getImageProcessingView().setVisibility(View.VISIBLE);
        mActivity.attachMaskView((View) null);
    }

    public void restoreInstanceState(Bundle bundle) {
        super.restoreInstanceState(bundle);
        ALog.d(TAG, "restoreInstanceState");
    }

    public void onActivityResume() {
        super.onActivityResume();
        ALog.d(TAG, "onActivityResume");
        mActivity.attachMaskView((View) null);
    }

    public void apply(final boolean done) {
        if (isAttached()) {
            new ApplyFilterTask(mActivity, new ApplyFilterListener() {
                public void onFinishFiltering() {
                   // ((ItemInfo) mMenuItems.get(mCurrentPosition)).setSelected(false);
//                    mCurrentPosition = 0;
//                    mCurrentPackageId = 0;
                    mCurrentPackageFolder = null;
                    //mActivity.applyFilter(new ImageFilter());
                    //((ItemInfo) mMenuItems.get(0)).setSelected(true);
                    //mMenuAdapter.notifyDataSetChanged();
                    if (done) {
                        done();
                    }
                }

                public Bitmap applyFilter() {
                    try {
//                        mActivity.getImageProcessingView().setDrawingCacheEnabled(true);
//                        mActivity.getImageProcessingView().buildDrawingCache();
//                        Bitmap bitmap = mActivity.getImageProcessingView().getDrawingCache();

                        return mActivity.getImageProcessingView().getImageProcessor().getBitmapWithFilterApplied();

                        //return ImageProcessor.getFiltratedBitmap(mActivity.getImage(), (((FilterInfo) mMenuItems.get(mCurrentPosition))).getImageFilter());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }).execute(new Void[0]);
        }
    }


    public void selectNormalItem(int i) {
        this.mActivity.applyFilter(((FilterInfo) ((ItemInfo) this.mMenuItems.get(i))).getImageFilter());
    }

    /* access modifiers changed from: protected */
    public ArrayList<ItemInfo> loadNormalItems(int[] datas) {
        ArrayList<ItemInfo> arrayList = new ArrayList<>();
        ArrayList<FilterInfo> itemInfos = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            int id = datas[i];
            FilterInfo info = new FilterInfo();
            info.setThumbnail(PhotoUtils.DRAWABLE_PREFIX + id);
            info.setSelected(false);
            info.setShowingType(0);
            itemInfos.add(info);
            info.setCmd(Constants.EFFECTS_CMDS[i]);
        }
        arrayList.addAll(itemInfos);
        return arrayList;
    }
}
