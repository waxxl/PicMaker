package com.yd.photoeditor.actions;

import android.graphics.Bitmap;

import com.yd.photoeditor.ui.activity.ImageProcessingActivity;

public abstract class BlurAction extends BaseAction {
    protected static final int EXCLUDE_BLURRED_SIZE = 60;
    public static final int FAST_BLUR_RADIUS = 30;
    protected Bitmap mBlurredImage;

    public BlurAction(ImageProcessingActivity imageProcessingActivity) {
        super(imageProcessingActivity);
    }

    public void onDetach() {
        super.onDetach();
        recycleImages();
    }

    /* access modifiers changed from: protected */
    public void recycleImages() {
        Bitmap bitmap = this.mBlurredImage;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mBlurredImage.recycle();
            this.mBlurredImage = null;
        }
    }
}
