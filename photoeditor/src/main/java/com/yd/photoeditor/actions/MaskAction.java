package com.yd.photoeditor.actions;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.yd.photoeditor.R;
import com.yd.photoeditor.config.PLog;
import com.yd.photoeditor.ui.activity.ImageProcessingActivity;

public abstract class MaskAction extends PackageAction {
    private static final String TAG = MaskAction.class.getSimpleName();
    private View mBottomExtraView;
    protected View mImageMaskView;
    protected LayoutInflater mLayoutInflater;
    private View mLeftExtraView;
    protected View mMaskLayout;
    private View mRightExtraView;
    private View mTopExtraView;

    public abstract int getMaskLayoutRes();

    public MaskAction(ImageProcessingActivity imageProcessingActivity) {
        super(imageProcessingActivity, null);
    }

    public MaskAction(ImageProcessingActivity imageProcessingActivity, String str) {
        super(imageProcessingActivity, str);
    }

    public void onInit() {
        super.onInit();
        mLayoutInflater = LayoutInflater.from(this.mActivity);
        mMaskLayout = mLayoutInflater.inflate(getMaskLayoutRes(), null);
        mTopExtraView = mMaskLayout.findViewById(R.id.topView);
        mLeftExtraView = mMaskLayout.findViewById(R.id.leftView);
        mRightExtraView = mMaskLayout.findViewById(R.id.rightView);
        mBottomExtraView = mMaskLayout.findViewById(R.id.bottomView);
        mImageMaskView = mMaskLayout.findViewById(R.id.maskView);
    }

    public void adjustImageMaskLayout() {
        adjustImageMaskLayout(this.mActivity.getImageWidth(), this.mActivity.getImageHeight());
    }

    public void adjustImageMaskLayout(int i, int i2) {
        int[] calculateThumbnailSize = this.mActivity.calculateThumbnailSize(i, i2);
        int photoViewWidth = (this.mActivity.getPhotoViewWidth() - calculateThumbnailSize[0]) / 2;
        int photoViewHeight = (this.mActivity.getPhotoViewHeight() - calculateThumbnailSize[1]) / 2;
        if (photoViewWidth <= 0) {
            this.mLeftExtraView.setVisibility(View.GONE);
            this.mRightExtraView.setVisibility(View.GONE);
        } else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(photoViewWidth, -1);
            this.mLeftExtraView.setLayoutParams(layoutParams);
            this.mRightExtraView.setLayoutParams(layoutParams);
            this.mLeftExtraView.setVisibility(View.VISIBLE);
            this.mRightExtraView.setVisibility(View.VISIBLE);
        }
        if (photoViewHeight <= 0) {
            this.mTopExtraView.setVisibility(View.GONE);
            this.mBottomExtraView.setVisibility(View.GONE);
            return;
        }
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, photoViewHeight);
        this.mTopExtraView.setLayoutParams(layoutParams2);
        this.mBottomExtraView.setLayoutParams(layoutParams2);
        this.mTopExtraView.setVisibility(View.VISIBLE);
        this.mBottomExtraView.setVisibility(View.VISIBLE);
    }

    public void onActivityResume() {
        super.onActivityResume();
        PLog.d(TAG, "onActivityResume");
        if (isAttached()) {
            PLog.d(TAG, "mActivity.attachMaskView");
            this.mActivity.attachMaskView(this.mMaskLayout);
        }
    }
}
