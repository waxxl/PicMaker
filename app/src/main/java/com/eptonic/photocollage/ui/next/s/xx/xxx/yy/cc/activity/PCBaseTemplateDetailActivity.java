package com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eptonic.photocollage.PhotoCollageApplication;
import com.eptonic.photocollage.model.PhotoStickerSaveData;
import com.eptonic.photocollage.presenter.listener.OnDoubleClickListener;
import com.eptonic.photocollage.R;
import com.eptonic.photocollage.model.TemplateItem;
import com.eptonic.photocollage.ui.mutitouchh.ImageEntity;
import com.eptonic.photocollage.ui.mutitouchh.MultiTouchEntity;
import com.eptonic.photocollage.ui.quickAction.QuickAction;
import com.eptonic.photocollage.ui.view.template.PhotoItem;
import com.eptonic.photocollage.util.Constants;
import com.eptonic.photocollage.util.TemplateUtils;
import com.eptonic.photocollage.util.ImageUtils;
import com.eptonic.photocollage.util.PhotoUtils;
import com.eptonic.photocollage.util.ResultContainer;
import com.eptonic.photocollage.util.FrameImageUtils;
import com.eptonic.photocollage.ui.view.HorizontalPreviewTemplateAdapter;
import com.eptonic.photocollage.ui.view.PhotoView;
import com.yd.photoeditor.vv.DateTimeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class PCBaseTemplateDetailActivity extends BasePhotoActivity implements HorizontalPreviewTemplateAdapter.OnPreviewTemplateClickListener, OnDoubleClickListener, View.OnClickListener {
    private boolean mClickedShareButton = false;
    protected RelativeLayout mContainerLayout;
    private int mImageInTemplateCount = 0;
    private boolean mIsFrameImage = false;
    protected int mItemType = 2;
    protected int mLayoutRatio = 0;
    protected float mOutputScale = 1.0f;
    protected PhotoView mPhotoView;
    public ImageEntity mSelectedEntity = null;
    protected List<String> mSelectedPhotoPaths = new ArrayList();
    protected TemplateItem mSelectedTemplateItem;
    public QuickAction mStickerQuickAction;
    protected HorizontalPreviewTemplateAdapter mTemplateAdapter;
    protected ArrayList<TemplateItem> mTemplateItemList = new ArrayList<>();
    protected RecyclerView mTemplateView;
    public QuickAction mTextQuickAction;

    public abstract void buildLayout(TemplateItem templateItem);

    public abstract Bitmap createOutputImage();

    public boolean isShowingAllTemplates() {
        return true;
    }

    public void onBackgroundColorButtonClick() {
    }

    public void onBackgroundDoubleClick() {
    }

    public void onBackgroundPhotoButtonClick() {
    }

    public void onStickerButtonClick() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.toolbar_f:
                asyncSaveAndShare();
                break;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initToolBar();
        setRight(R.drawable.save);
        mImageInTemplateCount = getIntent().getIntExtra(Constants.EXTRA_IMAGE_IN_TEMPLATE_COUNT, 0);
        mIsFrameImage = getIntent().getBooleanExtra(Constants.EXTRA_IS_FRAME_IMAGE, true);
        int intExtra = getIntent().getIntExtra(Constants.EXTRA_SELECTED_TEMPLATE_INDEX, 0);
        String title = getIntent().getStringExtra(Constants.EXTRA_TITLE);
        ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra(Constants.EXTRA_IMAGE_PATHS);

        mContainerLayout = findViewById(R.id.containerLayout);
        mTemplateView = findViewById(R.id.templateView);
        mPhotoView = new PhotoView(this);
        mPhotoView.setOnDoubleClickListener(this);
        createQuickAction();
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

        mTemplateAdapter = new HorizontalPreviewTemplateAdapter(mTemplateItemList, this);
        mTemplateView.setHasFixedSize(false);
        mTemplateView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mTemplateView.setAdapter(mTemplateAdapter);
        mContainerLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                mOutputScale = ImageUtils.calculateOutputScaleFactor(mContainerLayout.getWidth(), mContainerLayout.getHeight());
                buildLayout(mSelectedTemplateItem);
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
            resultSticker(Uri.fromFile(new File(stringArrayListExtra.get(0))));
        }
    }

    private void loadFrameImages(boolean isFrame) {
        ArrayList<TemplateItem> arrayList = new ArrayList();
        if (isFrame) {
            arrayList.addAll(TemplateUtils.loadFrameImages(this));
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

    public void onPause() {
        super.onPause();
        this.mPhotoView.unloadImages();
    }

    public void onResume() {
        super.onResume();
        this.mPhotoView.loadImages(this);
        this.mPhotoView.invalidate();
        if (this.mClickedShareButton) {
            this.mClickedShareButton = false;
        }
    }

    private void createQuickAction() {

    }

    public void onPhotoViewDoubleClick(PhotoView photoView, MultiTouchEntity multiTouchEntity) {
        this.mSelectedEntity = (ImageEntity) multiTouchEntity;
        this.mStickerQuickAction.show(photoView, (int) mSelectedEntity.getCenterX(), (int) this.mSelectedEntity.getCenterY());
//        if (imageEntity instanceof TextEntity) {
//            this.mTextQuickAction.show(photoView, (int) imageEntity.getCenterX(), (int) this.mSelectedEntity.getCenterY());
//        } else {
//
//        }
    }

    public void onPreviewTemplateClick(TemplateItem templateItem) {
        this.mSelectedTemplateItem.setSelected(false);
        for (int i = 0; i < this.mSelectedTemplateItem.getPhotoItemList().size(); i++) {
            PhotoItem photoItem = this.mSelectedTemplateItem.getPhotoItemList().get(i);
            if (photoItem.imagePath != null && photoItem.imagePath.length() > 0) {
                if (i < this.mSelectedPhotoPaths.size()) {
                    this.mSelectedPhotoPaths.add(i, photoItem.imagePath);
                } else {
                    this.mSelectedPhotoPaths.add(photoItem.imagePath);
                }
            }
        }
        int min = Math.min(this.mSelectedPhotoPaths.size(), templateItem.getPhotoItemList().size());
        for (int i2 = 0; i2 < min; i2++) {
            PhotoItem photoItem2 = templateItem.getPhotoItemList().get(i2);
            if (photoItem2.imagePath == null || photoItem2.imagePath.length() < 1) {
                photoItem2.imagePath = this.mSelectedPhotoPaths.get(i2);
            }
        }
        this.mSelectedTemplateItem = templateItem;
        this.mSelectedTemplateItem.setSelected(true);
        this.mTemplateAdapter.notifyDataSetChanged();
        buildLayout(templateItem);
    }

    private void asyncSaveAndShare() {
        new AsyncTask<Void, Void, File>() {
            Dialog dialog;
            String errMsg;

            public void onPreExecute() {
                super.onPreExecute();
                PCBaseTemplateDetailActivity baseTemplateDetailActivity = PCBaseTemplateDetailActivity.this;
                this.dialog = ProgressDialog.show(baseTemplateDetailActivity, baseTemplateDetailActivity.getString(R.string.app_name), PCBaseTemplateDetailActivity.this.getString(R.string.creating));
            }

            public File doInBackground(Void... voidArr) {
                try {
                    Bitmap createOutputImage = PCBaseTemplateDetailActivity.this.createOutputImage();
                    String concat = DateTimeUtils.getCurrentDateTime().replaceAll(":", "-").concat(".png");
                    File file = new File(ImageUtils.OUTPUT_COLLAGE_FOLDER);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File file2 = new File(file, concat);
                    createOutputImage.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file2));
                    PhotoUtils.addImageToGallery(file2.getAbsolutePath(), PCBaseTemplateDetailActivity.this);
                    PhotoCollageApplication.getDaoSession().insert(new PhotoStickerSaveData(1, file2.getAbsolutePath()));
                    return file2;
                } catch (Exception e) {
                    e.printStackTrace();
                    this.errMsg = e.getMessage();
                    return null;
                } catch (OutOfMemoryError e2) {
                    e2.printStackTrace();
                    this.errMsg = e2.getMessage();
                    return null;
                }
            }

            public void onPostExecute(File file) {
                super.onPostExecute(file);
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (file != null && file.exists()) {
                    Uri uri = Uri.fromFile(file);
                    Intent intent = new Intent(PCBaseTemplateDetailActivity.this, WatchSaveActivity.class);
                    intent.putExtra(WatchSaveActivity.IMAGE_URI_KEY, uri);
                    startActivity(intent);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public float calculateScaleRatio(int i, int i2) {
        return Math.max(((float) i) / ((float) getPhotoViewWidth()), ((float) i2) / ((float) getPhotoViewHeight()));
    }

    public int[] calculateThumbnailSize(int i, int i2) {
        int[] iArr = new int[2];
        float f = (float) i;
        float photoViewWidth = f / ((float) getPhotoViewWidth());
        float f2 = (float) i2;
        float max = Math.max(photoViewWidth, f2 / ((float) getPhotoViewHeight()));
        if (max == photoViewWidth) {
            iArr[0] = getPhotoViewWidth();
            iArr[1] = (int) (f2 / max);
        } else {
            iArr[0] = (int) (f / max);
            iArr[1] = getPhotoViewHeight();
        }
        return iArr;
    }

    private int getPhotoViewWidth() {
        return this.mContainerLayout.getWidth();
    }

    private int getPhotoViewHeight() {
        return this.mContainerLayout.getHeight();
    }

    public void onTextButtonClick() {
        this.mItemType = 3;
        addTextItem();
    }

    public void resultSticker(Uri uri) {
        ImageEntity imageEntity = new ImageEntity(uri, getResources());
        imageEntity.setInitScaleFactor(0.25d);
        imageEntity.load(this, (float) ((mPhotoView.getWidth() - imageEntity.getWidth()) / 2), (float) ((this.mPhotoView.getHeight() - imageEntity.getHeight()) / 2), (float) ((1 * Math.PI) / 20.0d));
        mPhotoView.addImageEntity(imageEntity);
        if (ResultContainer.getInstance().getImageEntities() != null) {
            ResultContainer.getInstance().getImageEntities().add(imageEntity);
        }
    }

    public void resultStickers(Uri[] uriArr) {
        super.resultPickMultipleImages(uriArr);
        int length = uriArr.length;
        for (int i = 0; i < length; i++) {
            ImageEntity imageEntity = new ImageEntity(uriArr[i], getResources());
            imageEntity.setInitScaleFactor(0.25d);
            imageEntity.load(this, (float) ((mPhotoView.getWidth() - imageEntity.getWidth()) / 2), (float) ((this.mPhotoView.getHeight() - imageEntity.getHeight()) / 2), (float) ((i * 3.141592653589793d) / 20.0d));
            mPhotoView.addImageEntity(imageEntity);
            if (ResultContainer.getInstance().getImageEntities() != null) {
                ResultContainer.getInstance().getImageEntities().add(imageEntity);
            }
        }
    }

    public void resultAddTextItem(String str, int i, String str2) {

    }

    public void resultEditTextItem(String str, int i, String str2) {

    }

    public void pickSticker() {

    }

    public void resultStickerDrawable(int i) {
        ImageEntity imageEntity = new ImageEntity(Constants.STICKERS[i], getResources());
        imageEntity.load(this, (float) ((this.mPhotoView.getWidth() - imageEntity.getWidth()) / 2), (float) ((this.mPhotoView.getHeight() - imageEntity.getHeight()) / 2));
        imageEntity.setSticker(true);
        this.mPhotoView.addImageEntity(imageEntity);
        if (ResultContainer.getInstance().getImageEntities() != null) {
            ResultContainer.getInstance().getImageEntities().add(imageEntity);
        }
    }
}