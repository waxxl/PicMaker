package com.eptonic.photocollage.ui.wrapper.dah.ghjf.gfhs.fhsj.ghsj.hfjsh.fhsj.fhsj.hfjs.fdj.sjhfs.fgsj.jgks.vbb.uiManager;

import android.app.Dialog;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eptonic.photocollage.R;
import com.eptonic.photocollage.model.TemplateItem;
import com.eptonic.photocollage.presenter.listener.OnDoubleClickListener;
import com.eptonic.photocollage.ui.mutitouchh.ImageEntity;
import com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity.PCBaseTemplateDetailActivity;
import com.eptonic.photocollage.ui.quickAction.QuickAction;
import com.eptonic.photocollage.ui.view.HorizontalPreviewTemplateAdapter;
import com.eptonic.photocollage.ui.view.PhotoView;
import com.eptonic.photocollage.ui.wrapper.dah.interfaces.BaseTemplateDetailInterface;
import com.eptonic.photocollage.util.Constants;
import com.eptonic.photocollage.util.FrameImageUtils;
import com.eptonic.photocollage.util.ImageUtils;
import com.eptonic.photocollage.util.TemplateUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseTemplateManager implements BaseTemplateDetailInterface {
    private PCBaseTemplateDetailActivity activity;
    HorizontalPreviewTemplateAdapter.OnPreviewTemplateClickListener onPreviewTemplateClickListener;
    OnDoubleClickListener onDoubleClickListener;
    View.OnClickListener onClickListener;
    protected View mAddImageView;
    protected Animation mAnimation;
    private boolean mClickedShareButton = false;
    protected RelativeLayout mContainerLayout;
    private Dialog mGuideDialog;
    private int mImageInTemplateCount = 0;
    private boolean mIsFrameImage = false;
    protected int mItemType = 2;
    protected int mLayoutRatio = 0;
    protected float mOutputScale = 1.0f;
    protected PhotoView mPhotoView;

    private Dialog mRatioDialog;
    public ImageEntity mSelectedEntity = null;
    protected List<String> mSelectedPhotoPaths = new ArrayList();
    protected TemplateItem mSelectedTemplateItem;
    public QuickAction mStickerQuickAction;
    protected HorizontalPreviewTemplateAdapter mTemplateAdapter;
    protected ArrayList<TemplateItem> mTemplateItemList = new ArrayList<>();
    protected RecyclerView mTemplateView;
    public QuickAction mTextQuickAction;

    public BaseTemplateManager(PCBaseTemplateDetailActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate() {
        activity.initToolBar();
        activity.setRight(R.drawable.save);
        mImageInTemplateCount = activity.getIntent().getIntExtra(Constants.EXTRA_IMAGE_IN_TEMPLATE_COUNT, 0);
        mIsFrameImage = activity.getIntent().getBooleanExtra(Constants.EXTRA_IS_FRAME_IMAGE, true);
        int intExtra = activity.getIntent().getIntExtra(Constants.EXTRA_SELECTED_TEMPLATE_INDEX, 0);
        String title = activity.getIntent().getStringExtra(Constants.EXTRA_TITLE);
        ArrayList<String> stringArrayListExtra = activity.getIntent().getStringArrayListExtra(Constants.EXTRA_IMAGE_PATHS);

        mContainerLayout = activity.findViewById(R.id.containerLayout);
        mTemplateView = activity.findViewById(R.id.templateView);
        mPhotoView = new PhotoView(activity);
        mPhotoView.setOnDoubleClickListener(activity);
        loadFrameImages(mIsFrameImage);
        for (TemplateItem item : mTemplateItemList) {
            if (!TextUtils.isEmpty(title)) {
                if (item.getTitle().equals(title)) {
                    item.setSelected(true);
                    mSelectedTemplateItem = item;
                    break;
                }
            }
        }
        if (mSelectedTemplateItem == null) {
            mSelectedTemplateItem = mTemplateItemList.get(0);
        }
        if (stringArrayListExtra != null) {
            int min2 = Math.min(stringArrayListExtra.size(), mSelectedTemplateItem.getPhotoItemList().size());
            for (int i = 0; i < min2; i++) {
                mSelectedTemplateItem.getPhotoItemList().get(i).imagePath = stringArrayListExtra.get(i);
            }
        }

        mTemplateAdapter = new HorizontalPreviewTemplateAdapter(mTemplateItemList, activity);
        mTemplateView.setHasFixedSize(false);
        mTemplateView.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        mTemplateView.setAdapter(mTemplateAdapter);
        mContainerLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                mOutputScale = ImageUtils.calculateOutputScaleFactor(mContainerLayout.getWidth(), mContainerLayout.getHeight());
                activity.buildLayout(mSelectedTemplateItem);
                if (Build.VERSION.SDK_INT >= 16) {
                    mContainerLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mContainerLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
        ArrayList<TemplateItem> arrayList = this.mTemplateItemList;
        if (arrayList != null && intExtra >= 0 && intExtra < arrayList.size()) {
            this.mTemplateView.scrollToPosition(intExtra);
        }
        if (stringArrayListExtra.size() > 0) {
            activity.resultSticker(Uri.fromFile(new File(stringArrayListExtra.get(0))));
        }
    }

    private void loadFrameImages(boolean isFrame) {
        ArrayList<TemplateItem> arrayList = new ArrayList();
        if (isFrame) {
            arrayList.addAll(TemplateUtils.loadFrameImages(activity));
        } else {
            arrayList.addAll(FrameImageUtils.loadFrames());
        }
        mTemplateItemList = new ArrayList<>();
        if (mImageInTemplateCount > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                TemplateItem templateItem = (TemplateItem) it.next();
                if (templateItem.getPhotoItemList().size() == mImageInTemplateCount) {
                    mTemplateItemList.add(templateItem);
                }
            }
            return;
        }
        mTemplateItemList.addAll(arrayList);
    }
}
