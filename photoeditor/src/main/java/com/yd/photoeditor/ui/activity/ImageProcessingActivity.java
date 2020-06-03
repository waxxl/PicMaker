package com.yd.photoeditor.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yd.photoeditor.R;
import com.yd.photoeditor.actions.BaseAction;
import com.yd.photoeditor.actions.CropAction;
import com.yd.photoeditor.actions.EffectAction;
import com.yd.photoeditor.actions.RotationAction;
import com.yd.photoeditor.adapter.CustomMenuAdapter;
import com.yd.photoeditor.config.Constants;
import com.yd.photoeditor.imageprocessing.ImageProcessingView;
import com.yd.photoeditor.imageprocessing.ImageProcessor;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;
import com.yd.photoeditor.listener.OnDoneActionsClickListener;
import com.yd.photoeditor.model.ItemInfo;
import com.yd.photoeditor.utils.ImageDecoder;
import com.yd.photoeditor.utils.PhotoUtils;

import java.util.ArrayList;

public class ImageProcessingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ImageProcessingActivity";
    private static final int DEFAULT_SELECTED_ACTION_INDEX = 1;
    public static final String EXTRA_EDITING_IMAGE_PATH = "editingImagePath";
    public static final String EXTRA_FLIP_IMAGE = "flipImage";
    public static final String EXTRA_RETURN_EDITED_IMAGE_PATH = "editedImage";
    public static final String IMAGE_URI_KEY = "imageUri";
    public static final String IS_EDITING_IMAGE_KEY = "isEditingImage";
    public static final String ROTATION_KEY = "rotation";
    private ImageView mCancelButton;
    public OnDoneActionsClickListener mDoneActionsClickListener;
    private ImageView mDoneButton;
    private String mEditingImagePath = null;
    public ImageProcessingView mImageProcessingView;
    public Uri mImageUri;
    private boolean mIsEditingImage = false;
    private ImageView mNormalImageView;
    public RelativeLayout mPhotoViewLayout;
    private FrameLayout mImageLayout;
    private FrameLayout mBottomLayout;
    private RecyclerView mItemRecycler;
    private TextView mEffectTV, mCropTV, mRotateTV;
    private BaseAction mEffectAction, mCropAction, mRotateAction;
    private BaseAction[] mActions = new BaseAction[3];
    private CustomMenuAdapter mAdapter;

    public BaseAction mCurrentAction;
    public int mCurrentTopMenuPosition = 1;

    private ImageFilter mFilter;
    public Bitmap mImage;
    public int mPhotoViewWidth = 0;
    private int mPhotoViewHeight;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.photo_editor_activity_main);
        initView();
        initAction();
        mActions[1].attach();
        initInfo();
    }

    private void initInfo() {
        if (mPhotoViewLayout != null) {
            mPhotoViewLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    mPhotoViewWidth = mPhotoViewLayout.getWidth();
                    mPhotoViewHeight = mPhotoViewLayout.getHeight();
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    if (displayMetrics.heightPixels >= displayMetrics.widthPixels) {
                        if (mImageUri != null) {
                            Bitmap decodeUriToBitmap = ImageDecoder.decodeUriToBitmap(ImageProcessingActivity.this, mImageUri);
                            int intExtra = getIntent().getIntExtra(ImageProcessingActivity.ROTATION_KEY, 0);
                            boolean booleanExtra = getIntent().getBooleanExtra("flipImage", false);
                            if (intExtra > 0) {
                                mImage = PhotoUtils.rotateImage(decodeUriToBitmap, (float) intExtra, booleanExtra);
                                if (mImage != decodeUriToBitmap) {
                                    decodeUriToBitmap.recycle();
                                    System.gc();
                                }
                            } else {
                                mImage = decodeUriToBitmap;
                            }
                            mImageProcessingView.setImage(mImage);
                        }
                        selectAction(1);
                    }
                    if (Build.VERSION.SDK_INT >= 16) {
                        mPhotoViewLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        mPhotoViewLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });
        }
    }

    private void initView() {
        mImageUri = (Uri) getIntent().getParcelableExtra(IMAGE_URI_KEY);
        mIsEditingImage = getIntent().getBooleanExtra(IS_EDITING_IMAGE_KEY, false);
        mEditingImagePath = getIntent().getStringExtra(EXTRA_EDITING_IMAGE_PATH);
        mImageProcessingView = (ImageProcessingView) findViewById(R.id.imageProcessingView);
        mImageProcessingView.setScaleType(ImageProcessor.ScaleType.CENTER_INSIDE);
        setImageProcessingViewBackgroundColor();
        mNormalImageView = (ImageView) findViewById(R.id.sourceImage);
        mImageLayout = (FrameLayout) findViewById(R.id.imageViewLayout);
        mPhotoViewLayout = (RelativeLayout) findViewById(R.id.photoViewLayout);
        mBottomLayout = (FrameLayout) findViewById(R.id.bottomLayout);
        mBottomLayout.addView(View.inflate(this, R.layout.bottom_layout, null));
        mItemRecycler = mBottomLayout.findViewById(R.id.recycler);
        mEffectTV = mBottomLayout.findViewById(R.id.effect);
        mCropTV = mBottomLayout.findViewById(R.id.crop);
        mRotateTV = mBottomLayout.findViewById(R.id.rotate);

        mEffectTV.setOnClickListener(this);
        mCropTV.setOnClickListener(this::onClick);
        mRotateTV.setOnClickListener(this::onClick);
        mItemRecycler.setHasFixedSize(false);
        mItemRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        if (mAdapter == null) {
            mAdapter = new CustomMenuAdapter(this);
        }
        mItemRecycler.setAdapter(mAdapter);
        mDoneButton = findViewById(R.id.doneButton);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mDoneActionsClickListener != null)
                mDoneActionsClickListener.onDoneButtonClick();
            }
        });
        mCancelButton = findViewById(R.id.backButton);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void attachBottomMenu(View view) {
//        this.mBottomLayout.removeAllViews();
//        this.mBottomLayout.addView(view);
    }


    private void setImageProcessingViewBackgroundColor() {
        int color = getResources().getColor(R.color.white);
        mImageProcessingView.setBackground(((float) Color.red(color)) / 255.0f, ((float) Color.green(color)) / 255.0f, ((float) Color.blue(color)) / 255.0f, ((float) Color.alpha(color)) / 255.0f);
    }

    public void attachMaskView(View view) {
        mImageLayout.removeAllViews();
        if (view != null) {
            mImageLayout.addView(view);
            mImageLayout.setVisibility(View.VISIBLE);
            return;
        }
        mImageLayout.setVisibility(View.GONE);

    }

    public boolean applyFilter(ImageFilter imageFilter) {
        if (mPhotoViewWidth < 5 || mPhotoViewHeight < 5 || mFilter == imageFilter) {
            return false;
        }
        mFilter = imageFilter;
        mImageProcessingView.setFilter(imageFilter);
        mImageProcessingView.requestRender();
        return true;
    }

    public ImageFilter getFilter() {
        return mFilter;
    }

    public ArrayList<ItemInfo> mEffectInfos;
    public ArrayList<ItemInfo> mCropInfos;
    public ArrayList<ItemInfo> mRotateInfos;

    public void attachBottomRecycler(int actionIndex) {
        switch (actionIndex) {
            case 0:
                mEffectInfos = setAdapterData(Constants.CROPS);
                break;
            case 1:
                mCropInfos = ((CropAction)mCropAction).loadNormalItems(0);
                break;
            case 2:
                mRotateInfos = setAdapterData(Constants.ROTATES);
                break;
        }
    }

    public ArrayList<ItemInfo> setAdapterData(int[] datas) {
        ArrayList<ItemInfo> itemInfos = new ArrayList<>();
        for (int id : datas) {
            ItemInfo info = new ItemInfo();
            info.setThumbnail(PhotoUtils.DRAWABLE_PREFIX + id);
            info.setSelected(false);
            info.setShowingType(0);
            itemInfos.add(info);
        }
        mAdapter.setData(itemInfos);
        return itemInfos;
    }

    public ArrayList<ItemInfo> getItemInfo(int actionIndex) {
        switch (actionIndex) {
            case 0:
                return mEffectInfos;
            case 1:
                return mCropInfos;
            case 2:
                return mRotateInfos;
        }
        return null;
    }

    public void setCurrentAction(BaseAction baseAction) {
        mCurrentAction = baseAction;
    }

    public BaseAction getCurrentAction() {
        return mCurrentAction;
    }

    public int getPhotoViewWidth() {
        return mPhotoViewWidth;
    }

    public int getPhotoViewHeight() {
        return mPhotoViewHeight;
    }

    public void setImage(Bitmap bitmap, boolean z) {
        Bitmap bitmap2;
        if (bitmap != null && !bitmap.isRecycled()) {
            if (z && (bitmap2 = mImage) != null && bitmap2 != bitmap && !bitmap2.isRecycled()) {
                mImage.recycle();
                mImage = null;
                System.gc();
            }
            mImageProcessingView.getImageProcessor().deleteImage();
            mImage = bitmap;
            mImageProcessingView.setImage(mImage);
        }
    }

    public Bitmap getImage() {
        Uri uri;
        Bitmap bitmap = mImage;
        if ((bitmap == null || bitmap.isRecycled()) && (uri = mImageUri) != null) {
            mImage = ImageDecoder.decodeUriToBitmap(this, uri);
        }
        return mImage;
    }

    public int getImageWidth() {
        Bitmap bitmap = mImage;
        if (bitmap == null || bitmap.isRecycled()) {
            return 0;
        }
        return mImage.getWidth();
    }

    public int getImageHeight() {
        Bitmap bitmap = mImage;
        if (bitmap == null || bitmap.isRecycled()) {
            return 0;
        }
        return mImage.getHeight();
    }

    public float calculateScaleRatio() {
        return calculateScaleRatio(getImageWidth(), getImageHeight());
    }

    public int[] calculateThumbnailSize() {
        return calculateThumbnailSize(getImageWidth(), getImageHeight());
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.effect) {
            if (mEffectAction == null) {
                mEffectAction = new EffectAction(this);
            }
            mCurrentAction = mEffectAction;
            mCurrentTopMenuPosition = 0;
        } else if (id == R.id.crop) {
            if (mCropAction == null) {
                mCropAction = new CropAction(this);
            }
            mCurrentAction = mCropAction;
            mCurrentTopMenuPosition = 1;
            mCropAction.attach();

        } else if (id == R.id.rotate) {
            if (mRotateAction == null) {
                mRotateAction = new RotationAction(this);
            }
            mCurrentAction = mRotateAction;
            mCurrentTopMenuPosition = 2;
            mCropAction.attach();
        }
    }

    public ImageView getNormalImageView() {
        return mNormalImageView;
    }

    public RotationAction getRotationAction() {
        if (mRotateAction == null) {
            mRotateAction = new RotationAction(this);
        }
        return (RotationAction) mRotateAction;
    }

    public ImageProcessingView getImageProcessingView() {
        return mImageProcessingView;
    }

    public void setDoneActionsClickListener(OnDoneActionsClickListener listener) {
        mDoneActionsClickListener = listener;
    }

    public void showProgress(boolean b) {

    }

    public void hideAllMenus() {

    }

    public String getEditingImagePath() {
        return mEditingImagePath;
    }

    public boolean isEditingImage() {
        return mIsEditingImage;
    }

    public CropAction getCropAction() {
        return (CropAction) mCropAction;
    }

    public void showAllMenus() {

    }

    public void selectAction(int action) {
        switch (action) {
            case 0:

                break;
            case 1:
                mCropTV.setSelected(true);
                mEffectTV.setSelected(false);
                mRotateTV.setSelected(false);
                break;
            case 2:
                break;
        }
    }

    private void initAction() {
        if (mEffectAction == null) {
            mEffectAction = new EffectAction(this);
        }
        if (mCropAction == null) {
            mCropAction = new CropAction(this);
        }
        if (mRotateAction == null) {
            mRotateAction = new RotationAction(this);
        }
        mActions[0] = mEffectAction;
        mActions[1] = mCropAction;
        mActions[2] = mRotateAction;
    }
}
