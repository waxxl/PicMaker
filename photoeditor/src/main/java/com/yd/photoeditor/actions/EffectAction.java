package com.yd.photoeditor.actions;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yd.photoeditor.R;
import com.yd.photoeditor.config.ALog;
import com.yd.photoeditor.database.table.FilterTable;
import com.yd.photoeditor.database.table.ItemPackageTable;
import com.yd.photoeditor.imageprocessing.ImageProcessor;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;
import com.yd.photoeditor.listener.ApplyFilterListener;
import com.yd.photoeditor.model.FilterInfo;
import com.yd.photoeditor.model.ItemInfo;
import com.yd.photoeditor.task.ApplyFilterTask;
import com.yd.photoeditor.ui.activity.ImageProcessingActivity;
import com.yd.photoeditor.utils.Utils;
import java.util.List;

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
        super.attach();
        mActivity.attachBottomRecycler(0);
        mMenuItems = mActivity.mCropInfos;
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

    public void apply(final boolean z) {
        if (isAttached()) {
            new ApplyFilterTask(this.mActivity, new ApplyFilterListener() {
                public void onFinishFiltering() {
                    ((ItemInfo) EffectAction.this.mMenuItems.get(EffectAction.this.mCurrentPosition)).setSelected(false);
                    EffectAction effectAction = EffectAction.this;
                    effectAction.mCurrentPosition = 0;
                    effectAction.mCurrentPackageId = 0;
                    effectAction.mCurrentPackageFolder = null;
                    effectAction.mActivity.applyFilter(new ImageFilter());
                    ((ItemInfo) EffectAction.this.mMenuItems.get(0)).setSelected(true);
                    EffectAction.this.mMenuAdapter.notifyDataSetChanged();
                    if (z) {
                        EffectAction.this.done();
                    }
                }

                public Bitmap applyFilter() {
                    try {
                        return ImageProcessor.getFiltratedBitmap(EffectAction.this.mActivity.getImage(), ((FilterInfo) ((ItemInfo) EffectAction.this.mMenuItems.get(EffectAction.this.mCurrentPosition))).getImageFilter());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }).execute(new Void[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void selectNormalItem(int i) {
        this.mActivity.applyFilter(((FilterInfo) ((ItemInfo) this.mMenuItems.get(i))).getImageFilter());
    }

    /* access modifiers changed from: protected */
    public List<? extends ItemInfo> loadNormalItems(long j, String str) {
        List<FilterInfo> allRows = new FilterTable(this.mActivity).getAllRows(j);
        if (str != null && str.length() > 0) {
            for (FilterInfo next : allRows) {
                next.setThumbnail(Utils.FILTER_FOLDER.concat("/").concat(str).concat("/").concat(next.getThumbnail()));
                next.setPackageFolder(str);
            }
        }
        return allRows;
    }
}
