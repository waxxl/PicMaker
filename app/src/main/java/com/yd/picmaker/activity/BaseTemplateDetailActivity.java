package com.yd.picmaker.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yd.picmaker.Listener.OnDoubleClickListener;
import com.yd.picmaker.R;
import com.yd.picmaker.model.TemplateItem;
import com.yd.picmaker.mutitouchh.ImageEntity;
import com.yd.picmaker.mutitouchh.MultiTouchEntity;
import com.yd.picmaker.quickAction.QuickAction;
import com.yd.picmaker.template.PhotoItem;
import com.yd.picmaker.util.Constants;
import com.yd.picmaker.util.ImageUtils;
import com.yd.picmaker.util.PhotoUtils;
import com.yd.picmaker.util.ResultContainer;
import com.yd.picmaker.util.TemplateImageUtils;
import com.yd.picmaker.view.HorizontalPreviewTemplateAdapter;
import com.yd.picmaker.view.PhotoView;
import com.yd.photoeditor.utils.DateTimeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseTemplateDetailActivity extends BasePhotoActivity implements HorizontalPreviewTemplateAdapter.OnPreviewTemplateClickListener, OnDoubleClickListener {
    private static final int ID_CANCEL = 3;
    private static final int ID_DELETE = 2;
    private static final int ID_EDIT = 1;
    private static final String PREF_NAME = "templateDetailPref";
    protected static final int RATIO_FIT = 1;
    protected static final int RATIO_GOLDEN = 2;
    private static final String RATIO_KEY = "ratio";
    protected static final int RATIO_SQUARE = 0;
    private static final String TAG = BaseTemplateDetailActivity.class.getSimpleName();

    protected View mAddImageView;
    protected Animation mAnimation;
    private boolean mClickedShareButton = false;
    protected RelativeLayout mContainerLayout;
    private Dialog mGuideDialog;
    private View mGuideView;
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

    public void onCreate(Bundle bundle) {
        ArrayList<String> stringArrayList;
        super.onCreate(bundle);
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(R.string.collage);
        }
        mImageInTemplateCount = getIntent().getIntExtra(TemplateActivity.EXTRA_IMAGE_IN_TEMPLATE_COUNT, 0);
        mIsFrameImage = getIntent().getBooleanExtra(TemplateActivity.EXTRA_IS_FRAME_IMAGE, true);
        int intExtra = getIntent().getIntExtra(TemplateActivity.EXTRA_SELECTED_TEMPLATE_INDEX, 0);
        ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra(TemplateActivity.EXTRA_IMAGE_PATHS);

        mContainerLayout = findViewById(R.id.containerLayout);
        mTemplateView = findViewById(R.id.templateView);
        mPhotoView = new PhotoView(this);
        mPhotoView.setOnDoubleClickListener(this);
        createQuickAction();
        loadFrameImages(mIsFrameImage);
        this.mSelectedTemplateItem = this.mTemplateItemList.get(intExtra);
        this.mSelectedTemplateItem.setSelected(true);
        if (stringArrayListExtra != null) {
            Log.d("xxl", "stringArrayListExtra.size()  "  + stringArrayListExtra.size()  + "mSelectedTemplateItem.getPhotoItemList().size()   " +
                    mSelectedTemplateItem.getPhotoItemList().size());
            int min2 = Math.min(stringArrayListExtra.size(), mSelectedTemplateItem.getPhotoItemList().size());
            for (int i = 0; i < min2; i++) {
                mSelectedTemplateItem.getPhotoItemList().get(i).imagePath = stringArrayListExtra.get(i);
            }
        }

        mTemplateAdapter = new HorizontalPreviewTemplateAdapter(mTemplateItemList, this);
        mTemplateView.setHasFixedSize(false);
        mTemplateView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mTemplateView.setAdapter(this.mTemplateAdapter);
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
    }

    private void loadFrameImages(boolean z) {
        ArrayList arrayList = new ArrayList();
        //FrameImageUtils.loadFrameImages(this)
        arrayList.addAll(TemplateImageUtils.loadTemplates());
        this.mTemplateItemList = new ArrayList<>();
        if (this.mImageInTemplateCount > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                TemplateItem templateItem = (TemplateItem) it.next();
                if (templateItem.getPhotoItemList().size() == this.mImageInTemplateCount) {
                    this.mTemplateItemList.add(templateItem);
                }
            }
            return;
        }
        this.mTemplateItemList.addAll(arrayList);
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
        ImageEntity imageEntity = this.mSelectedEntity;
        this.mStickerQuickAction.show(photoView, (int) imageEntity.getCenterX(), (int) this.mSelectedEntity.getCenterY());
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



    public void clickInfoView() {
        View view = this.mGuideView;
        if (view != null) {
            view.startAnimation(this.mAnimation);
        }
        this.mGuideDialog.show();
    }

    private void asyncSaveAndShare() {
        new AsyncTask<Void, Void, File>() {
            Dialog dialog;
            String errMsg;

            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
                BaseTemplateDetailActivity baseTemplateDetailActivity = BaseTemplateDetailActivity.this;
                this.dialog = ProgressDialog.show(baseTemplateDetailActivity, baseTemplateDetailActivity.getString(R.string.app_name), BaseTemplateDetailActivity.this.getString(R.string.creating));
            }

            /* access modifiers changed from: protected */
            public File doInBackground(Void... voidArr) {
                try {
                    Bitmap createOutputImage = BaseTemplateDetailActivity.this.createOutputImage();
                    String concat = DateTimeUtils.getCurrentDateTime().replaceAll(":", "-").concat(".png");
                    File file = new File(ImageUtils.OUTPUT_COLLAGE_FOLDER);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File file2 = new File(file, concat);
                    createOutputImage.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file2));
                    PhotoUtils.addImageToGallery(file2.getAbsolutePath(), BaseTemplateDetailActivity.this);
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

            /* access modifiers changed from: protected */
            public void onPostExecute(File file) {
                super.onPostExecute(file);

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

    public void resultStickers(Uri[] uriArr) {
        super.resultPickMultipleImages(uriArr);
        int length = uriArr.length;
        for (int i = 0; i < length; i++) {
            double d = i;
            Double.isNaN(d);
            ImageEntity imageEntity = new ImageEntity(uriArr[i], getResources());
            imageEntity.setInitScaleFactor(0.25d);
            imageEntity.load(this, (float) ((this.mPhotoView.getWidth() - imageEntity.getWidth()) / 2), (float) ((this.mPhotoView.getHeight() - imageEntity.getHeight()) / 2), (float) ((d * 3.141592653589793d) / 20.0d));
            this.mPhotoView.addImageEntity(imageEntity);
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