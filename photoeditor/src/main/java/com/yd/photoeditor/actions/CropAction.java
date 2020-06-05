package com.yd.photoeditor.actions;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.yd.photoeditor.R;
import com.yd.photoeditor.config.ALog;
import com.yd.photoeditor.config.Constants;
import com.yd.photoeditor.database.table.ItemPackageTable;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;
import com.yd.photoeditor.listener.ApplyFilterListener;
import com.yd.photoeditor.model.CropInfo;
import com.yd.photoeditor.model.ItemInfo;
import com.yd.photoeditor.task.ApplyFilterTask;
import com.yd.photoeditor.ui.activity.ImageProcessingActivity;
import com.yd.photoeditor.utils.PhotoUtils;
import com.yd.photoeditor.view.CropImageView;
import com.yd.photoeditor.view.DrawableCropImageView;
import com.yd.photoeditor.view.MultiTouchHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CropAction extends MaskAction implements View.OnTouchListener, DrawableCropImageView.OnDrawMaskListener {
    private static final String CROP_ACTION_PREF_NAME = "cropActionPref";
    private static final String SHOW_GUIDE_NAME = "showGuide";
    private static final String TAG = CropAction.class.getSimpleName();
    public SharedPreferences mCropActionPref;
    public DrawableCropImageView mDrawableCropImageView;
    private View mDrawableCropLayout;
    private CropImageView mRectangleCropMaskView;
    public Bundle mSavedInstanceCustomData;
    public Bundle mSavedInstanceDrawData;
    public Bundle mSavedInstanceSquareData;
    public MultiTouchHandler mTouchHandler;

    public String getActionName() {
        return "CropAction";
    }

    public CropAction(ImageProcessingActivity imageProcessingActivity) {
        super(imageProcessingActivity, ItemPackageTable.CROP_TYPE);
        mCropActionPref = imageProcessingActivity.getSharedPreferences(CROP_ACTION_PREF_NAME, 0);
    }

    public void onInit() {
        super.onInit();
        mSelectedItemIndexes = new HashMap();
    }

    public View inflateMenuView() {
        mRootActionView = mLayoutInflater.inflate(R.layout.photo_editor_action_crop, null);
        mRectangleCropMaskView = new CropImageView(mActivity);
        mRectangleCropMaskView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER));
        mRectangleCropMaskView.setPaintMode(true);
        mDrawableCropLayout = mLayoutInflater.inflate(R.layout.photo_editor_crop_mask_draw, null);
        mDrawableCropImageView = mDrawableCropLayout.findViewById(R.id.drawbleCropView);
        mDrawableCropImageView.setOnDrawMaskListener(this);
        mActivity.getNormalImageView().setOnTouchListener(this);
        mCurrentPosition = 3;
        return mRootActionView;
    }

    public void attach() {
        mActivity.attachBottomRecycler(1);
        mMenuItems = mActivity.mCropInfos;
        super.attach();

        ALog.d(TAG, "attach");
        mActivity.getNormalImageView().setVisibility(View.VISIBLE);
        mMaskLayout.setVisibility(View.VISIBLE);
        mActivity.applyFilter(new ImageFilter());
        if (mTouchHandler != null) {
            pinchImage();
        } else {
            mTouchHandler = new MultiTouchHandler();
            mTouchHandler.setEnableRotation(true);
            initSourceImageView();
        }
        mMaskLayout.post(new Runnable() {
            public void run() {
                mActivity.getImageProcessingView().setVisibility(View.GONE);
            }
        });
    }

    public void onDetach() {
        super.onDetach();
        mMaskLayout.setVisibility(View.GONE);
        mActivity.getNormalImageView().setVisibility(View.GONE);
        mActivity.getImageProcessingView().setVisibility(View.VISIBLE);
    }

    public void onActivityResume() {
        super.onActivityResume();
        ALog.d(TAG, "onActivityResume");
    }

    private void clickSquareView() {
        float f;
        Bundle bundle = mSavedInstanceSquareData;
        if (bundle != null) {
            mRectangleCropMaskView.restoreInstanceState(bundle);
            return;
        }
        mRectangleCropMaskView.setPaintMode(true);
        mRectangleCropMaskView.setBackgroundColor(0);
        float photoViewWidth = (float) mActivity.getPhotoViewWidth();
        float photoViewHeight = (float) mActivity.getPhotoViewHeight();
        float min = (float) ((int) Math.min(photoViewWidth, photoViewHeight));
        float f2 = min / 2.0f;
        float f3 = f2 / 1.0f;
        if (f3 > f2) {
            f = min * 1.0f;
        } else {
            f = f2;
            f2 = f3;
        }
        RectF rectF = new RectF();
        rectF.top = (photoViewHeight / 2.0f) - (f2 / 2.0f);
        rectF.bottom = rectF.top + f2;
        rectF.left = (photoViewWidth / 2.0f) - (f / 2.0f);
        rectF.right = rectF.left + f;
        mRectangleCropMaskView.setCropArea(rectF);
        mRectangleCropMaskView.setRatio(1.0f);
    }

    private void clickCustomView() {
        Bundle bundle = mSavedInstanceCustomData;
        if (bundle != null) {
            mRectangleCropMaskView.restoreInstanceState(bundle);
            return;
        }
        mRectangleCropMaskView.setPaintMode(true);
        mRectangleCropMaskView.setBackgroundColor(0);
        RectF rectF = new RectF();
        float photoViewWidth = (float) mActivity.getPhotoViewWidth();
        float photoViewHeight = (float) mActivity.getPhotoViewHeight();
        rectF.top = photoViewHeight / 3.0f;
        rectF.bottom = (photoViewHeight * 2.0f) / 3.0f;
        rectF.left = photoViewWidth / 3.0f;
        rectF.right = (photoViewWidth * 2.0f) / 3.0f;
        mRectangleCropMaskView.setCropArea(rectF);
        mRectangleCropMaskView.setRatio(-2.0f);
    }

    private void initSourceImageView() {
        Matrix matrix = new Matrix();
        float calculateScaleRatio = mActivity.calculateScaleRatio();
        int[] calculateThumbnailSize = mActivity.calculateThumbnailSize();
        float f = 1.0f / calculateScaleRatio;
        if(f == 0 || Float.isInfinite(f)) {
            return;
        }
        matrix.postScale(f, f, ((float) mActivity.getImageWidth()) / 2.0f, ((float) mActivity.getImageHeight()) / 2.0f);
        matrix.postTranslate(-(((float) (mActivity.getImageWidth() - calculateThumbnailSize[0])) / 2.0f), -(((float) (mActivity.getImageHeight() - calculateThumbnailSize[1])) / 2.0f));
        matrix.postTranslate(((float) (mActivity.getPhotoViewWidth() - calculateThumbnailSize[0])) / 2.0f, ((float) (mActivity.getPhotoViewHeight() - calculateThumbnailSize[1])) / 2.0f);
        mActivity.getNormalImageView().setImageMatrix(matrix);
        mTouchHandler.setMatrix(matrix);
        mTouchHandler.setScale(calculateScaleRatio);
    }

    public void reset() {
        mTouchHandler = null;
        mSavedInstanceCustomData = null;
        mSavedInstanceDrawData = null;
        mSavedInstanceSquareData = null;
    }

    public void apply(final boolean done) {
        if (isAttached()) {
            new ApplyFilterTask(mActivity, new ApplyFilterListener() {
                String errMsg = null;

                public void onFinishFiltering() {
                    String str = errMsg;
                    if (str != null && str.length() > 0) {
                        Toast.makeText(mActivity, errMsg, Toast.LENGTH_SHORT).show();
                    }
                    mTouchHandler = null;
                    mActivity.getNormalImageView().setImageBitmap(null);
                    CropAction cropAction = CropAction.this;
                    cropAction.mCurrentPosition = 3;
                    cropAction.mCurrentPackageId = 0;
                    cropAction.mSelectedItemIndexes.clear();
                    mCurrentPackageFolder = null;
                    if (done) {
                        done();
                    }
                    if (mActivity.getRotationAction() != null) {
                        mActivity.getRotationAction().reset();
                    }
                }

                public Bitmap applyFilter() {
                    try {
                        if (mCurrentPackageId == 0 && mCurrentPosition < 2) {
                            return rectangleCrop();
                        }
                        if (mCurrentPackageId == 0 && mCurrentPosition == 2) {
                            return cropDrawnImage();
                        }
                        return cropFrame(mCurrentPosition);
                    } catch (OutOfMemoryError e) {
                        errMsg = e.getMessage();
                        return null;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return null;
                    }
                }
            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public Bitmap cropDrawnImage() {
        return mDrawableCropImageView.cropImage(true);
    }

    public Bitmap cropFrame(int i) throws OutOfMemoryError {
        try {
            ItemInfo itemInfo = mMenuItems.get(i);
            if (itemInfo.getShowingType() != 0) {
                return null;
            }
            float photoViewWidth = ((float) mActivity.getPhotoViewWidth()) / ((float) mActivity.getPhotoViewHeight());
            Bitmap transparentPadding = PhotoUtils.transparentPadding(mActivity.getImage(), photoViewWidth);
            if (transparentPadding != mActivity.getImage()) {
                mActivity.getImage().recycle();
            }
            Bitmap decodePNGImage = PhotoUtils.decodePNGImage(mActivity, ((CropInfo) itemInfo).getBackground());
            Bitmap transparentPadding2 = PhotoUtils.transparentPadding(decodePNGImage, photoViewWidth);
            if (transparentPadding2 != decodePNGImage) {
                decodePNGImage.recycle();
                System.gc();
            }
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(transparentPadding2, transparentPadding.getWidth(), transparentPadding.getHeight(), true);
            if (createScaledBitmap != transparentPadding2) {
                transparentPadding2.recycle();
                System.gc();
            }
            Bitmap cropImage = PhotoUtils.cropImage(transparentPadding, createScaledBitmap, mTouchHandler.getScaleMatrix());
            if (cropImage != transparentPadding) {
                transparentPadding.recycle();
                System.gc();
            }
            Bitmap cleanImage = PhotoUtils.cleanImage(cropImage);
            if (cleanImage != cropImage) {
                cropImage.recycle();
                System.gc();
            }
            return cleanImage;
        } catch (OutOfMemoryError e) {
            throw e;
        }
    }

    public Bitmap rectangleCrop() {
        RectF cropArea = mRectangleCropMaskView.getCropArea();
        float calculateScaleRatio = mActivity.calculateScaleRatio();
        int[] calculateThumbnailSize = mActivity.calculateThumbnailSize();
        float photoViewWidth = (float) ((mActivity.getPhotoViewWidth() - calculateThumbnailSize[0]) / 2);
        float max = Math.max((cropArea.left - photoViewWidth) * calculateScaleRatio, 0.0f);
        float min = Math.min((cropArea.right - photoViewWidth) * calculateScaleRatio, (float) mActivity.getImageWidth());
        float photoViewHeight = (float) ((mActivity.getPhotoViewHeight() - calculateThumbnailSize[1]) / 2);
        float max2 = Math.max((cropArea.top - photoViewHeight) * calculateScaleRatio, 0.0f);
        return Bitmap.createBitmap(mActivity.getImage(), (int) max, (int) max2, (int) (min - max), (int) (Math.min((cropArea.bottom - photoViewHeight) * calculateScaleRatio, (float) mActivity.getImageHeight()) - max2));
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!isAttached()) {
            return false;
        }
        mTouchHandler.touch(motionEvent);
        pinchImage();
        return true;
    }

    private void pinchImage() {
        mActivity.getNormalImageView().setImageMatrix(mTouchHandler.getMatrix());
    }

    public void onStartDrawing() {

    }

    public void onFinishDrawing() {
    }

    public int getMaskLayoutRes() {
        return R.layout.photo_editor_mask_layout;
    }

    public void selectNormalItem(int i) {
        Bitmap bitmap;
        if (isAttached()) {
            ItemInfo itemInfo2 = mMenuItems.get(i);
            if (itemInfo2.getShowingType() == 3) {
                mActivity.getNormalImageView().setImageMatrix(new Matrix());
                mActivity.getNormalImageView().setOnTouchListener(null);
                mActivity.getNormalImageView().setScaleType(ImageView.ScaleType.FIT_CENTER);
                mActivity.attachMaskView(mRectangleCropMaskView);
                clickSquareView();
                mCurrentPosition = i;
            } else if (itemInfo2.getShowingType() == 4) {
                mActivity.getNormalImageView().setImageMatrix(new Matrix());
                mActivity.getNormalImageView().setOnTouchListener(null);
                mActivity.getNormalImageView().setScaleType(ImageView.ScaleType.FIT_CENTER);
                mActivity.attachMaskView(mRectangleCropMaskView);
                clickCustomView();
                mCurrentPosition = i;
            } else if (itemInfo2.getShowingType() == 5) {
                mActivity.getNormalImageView().setImageMatrix(new Matrix());
                mActivity.getNormalImageView().setOnTouchListener(null);
                mActivity.getNormalImageView().setScaleType(ImageView.ScaleType.FIT_CENTER);
                Bundle bundle = mSavedInstanceDrawData;
                if (bundle != null) {
                    mDrawableCropImageView.restoreInstanceState(bundle);
                    mDrawableCropImageView.setFingerDrawingMode(true);
                }
                mDrawableCropImageView.setBitmap(mActivity.getImage());
                mActivity.attachMaskView(mDrawableCropLayout);
                mCurrentPosition = i;
            } else if (itemInfo2.getShowingType() == 0) {
                mActivity.attachMaskView(mMaskLayout);
                Drawable background = mImageMaskView.getBackground();
                if (background != null && (background instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) background).getBitmap()) != null && !bitmap.isRecycled()) {
                    mImageMaskView.setBackgroundColor(0);
                    bitmap.recycle();
                    System.gc();
                }
                Bitmap decodePNGImage = PhotoUtils.decodePNGImage(mActivity, ((CropInfo) itemInfo2).getForeground());
                adjustImageMaskLayout(decodePNGImage.getWidth(), decodePNGImage.getHeight());
                if (Build.VERSION.SDK_INT < 16) {
                    mImageMaskView.setBackgroundDrawable(new BitmapDrawable(mActivity.getResources(), decodePNGImage));
                } else {
                    mImageMaskView.setBackground(new BitmapDrawable(mActivity.getResources(), decodePNGImage));
                }
                mActivity.getNormalImageView().setImageBitmap(mActivity.getImage());
                mActivity.getNormalImageView().setOnTouchListener(this);
                mActivity.getNormalImageView().setScaleType(ImageView.ScaleType.MATRIX);
                mActivity.getNormalImageView().post(new Runnable() {
                    public void run() {
                        if (mTouchHandler != null) {
                            mActivity.getNormalImageView().setImageMatrix(mTouchHandler.getMatrix());
                        }
                    }
                });
                mCurrentPosition = i;
            }
        }
    }

    public ArrayList<ItemInfo> loadNormalItems(long j) {
        ArrayList<CropInfo> allRows = new ArrayList<>(11);
        for (int i = 0; i < Constants.CROPS.length; i++) {
            CropInfo cropInfo = new CropInfo();
            cropInfo.setThumbnail(PhotoUtils.DRAWABLE_PREFIX + Constants.CROPS[i]);
            cropInfo.setForeground(PhotoUtils.DRAWABLE_PREFIX + Constants.CROPS_FG[i]);
            cropInfo.setBackground(PhotoUtils.DRAWABLE_PREFIX + Constants.CROPS[i]);
            allRows.add(cropInfo);
        }
        ArrayList arrayList = new ArrayList();
        if (j < 1) {
            ItemInfo itemInfo = new ItemInfo();
            itemInfo.setTitle(mActivity.getString(R.string.photo_editor_square));
            itemInfo.setThumbnail(PhotoUtils.DRAWABLE_PREFIX + R.drawable.square);
            itemInfo.setSelectedThumbnail(PhotoUtils.DRAWABLE_PREFIX + R.drawable.square);
            itemInfo.setShowingType(3);
            arrayList.add(0, itemInfo);
            ItemInfo itemInfo2 = new ItemInfo();
            itemInfo2.setTitle(mActivity.getString(R.string.photo_editor_custom));
            itemInfo2.setThumbnail(PhotoUtils.DRAWABLE_PREFIX + R.drawable.custom);
            itemInfo2.setSelectedThumbnail(PhotoUtils.DRAWABLE_PREFIX + R.drawable.custom);
            itemInfo2.setShowingType(4);
            arrayList.add(1, itemInfo2);
            ItemInfo itemInfo3 = new ItemInfo();
            itemInfo3.setTitle(mActivity.getString(R.string.photo_editor_draw));
            itemInfo3.setThumbnail(PhotoUtils.DRAWABLE_PREFIX + R.drawable.draw);
            itemInfo3.setSelectedThumbnail(PhotoUtils.DRAWABLE_PREFIX + R.drawable.draw);
            itemInfo3.setShowingType(5);
            arrayList.add(2, itemInfo3);
        }
        arrayList.addAll(allRows);
        return arrayList;
    }
}
