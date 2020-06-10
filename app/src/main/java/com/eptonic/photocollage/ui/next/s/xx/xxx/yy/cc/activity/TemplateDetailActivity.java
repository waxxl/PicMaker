package com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.eptonic.photocollage.PhotoCollageApplication;
import com.eptonic.photocollage.R;
import com.eptonic.photocollage.model.TemplateItem;
import com.eptonic.photocollage.presenter.listener.RecyclerItemClickListener;
import com.eptonic.photocollage.ui.view.frame.FrameImageView;
import com.eptonic.photocollage.ui.view.frame.FramePhotoLayout;
import com.eptonic.photocollage.util.Constants;
import com.eptonic.photocollage.util.ImageUtils;
import com.yd.photoeditor.vv.FileUtils;
import com.yd.photoeditor.vv.ImageDecoder;

import java.io.File;
import java.util.ArrayList;

public class TemplateDetailActivity extends PCBaseTemplateDetailActivity implements FramePhotoLayout.OnQuickActionClickListener {
    private static final float DEFAULT_SPACE = ImageUtils.pxFromDp(PhotoCollageApplication.getContext(), 2.0f);
    public static final float MAX_CORNER = ImageUtils.pxFromDp(PhotoCollageApplication.getContext(), 60.0f);
    private static final float MAX_CORNER_PROGRESS = 200.0f;
    public static final float MAX_SPACE = ImageUtils.pxFromDp(PhotoCollageApplication.getContext(), 30.0f);
    private static final float MAX_SPACE_PROGRESS = 300.0f;
    private static final int REQUEST_SELECT_PHOTO = 99;
    Boolean Switcher = false;
    private ImageView crossBtn;
    int itemCat;
    private LinearLayout itemsPanel;
    private int mBackgroundColor = -1;
    private Bitmap mBackgroundImage;
    private Uri mBackgroundUri = null;
    public float mCorner = 0.0f;
    private SeekBar mCornerBar;
    public FramePhotoLayout mTemplatePhotoLayout;
    private Bundle mSavedInstanceState;
    private FrameImageView mSelectedFrameImageView;
    public float mSpace = DEFAULT_SPACE;
    private SeekBar mSpaceBar;
    private ViewGroup mSpaceLayout;
    public RecyclerView recyclerViewStick;

    public boolean isShowingAllTemplates() {
        return false;
    }

    @Override
    public int getId() {
        return R.layout.activity_frame_detail;
    }

    @Override
    public void initView() {

    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle(R.string.tab_template);
        recyclerViewStick = (RecyclerView) findViewById(R.id.recycleViewStick);
        itemsPanel = (LinearLayout) findViewById(R.id.items_panel);
        crossBtn = (ImageView) findViewById(R.id.cross_btn);
        crossBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showRecyclerViewFragment(false);
            }
        });
//        this.mAddImageDialog.findViewById(R.id.dividerTextView).setVisibility(0);
//        this.mAddImageDialog.findViewById(R.id.alterBackgroundView).setVisibility(0);
//        this.mAddImageDialog.findViewById(R.id.dividerBackgroundPhotoView).setVisibility(0);
//        this.mAddImageDialog.findViewById(R.id.alterBackgroundColorView).setVisibility(0);
        mSpaceLayout = (ViewGroup) findViewById(R.id.spaceLayout);
        mSpaceBar = (SeekBar) findViewById(R.id.spaceBar);
        mSpaceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                mSpace = (TemplateDetailActivity.MAX_SPACE * ((float) seekBar.getProgress())) / TemplateDetailActivity.MAX_SPACE_PROGRESS;
                if (mTemplatePhotoLayout != null) {
                    mTemplatePhotoLayout.setSpace(TemplateDetailActivity.this.mSpace, TemplateDetailActivity.this.mCorner);
                }
            }
        });
        mCornerBar = (SeekBar) findViewById(R.id.cornerBar);
        mCornerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                float unused = TemplateDetailActivity.this.mCorner = (TemplateDetailActivity.MAX_CORNER * ((float) seekBar.getProgress())) / TemplateDetailActivity.MAX_CORNER_PROGRESS;
                if (mTemplatePhotoLayout != null) {
                    mTemplatePhotoLayout.setSpace(TemplateDetailActivity.this.mSpace, TemplateDetailActivity.this.mCorner);
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        boolean onCreateOptionsMenu = super.onCreateOptionsMenu(menu);
        //menu.findItem(R.id.action_ratio).setVisible(true);
        return onCreateOptionsMenu;
    }

    public void onBackgroundColorButtonClick() {

    }


    public Bitmap createOutputImage() throws OutOfMemoryError {
        try {
            Bitmap createImage = this.mTemplatePhotoLayout.createImage();
            Bitmap createBitmap = Bitmap.createBitmap(createImage.getWidth(), createImage.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint(1);
            if (this.mBackgroundImage == null || this.mBackgroundImage.isRecycled()) {
                canvas.drawColor(this.mBackgroundColor);
            } else {
                canvas.drawBitmap(this.mBackgroundImage, new Rect(0, 0, this.mBackgroundImage.getWidth(), this.mBackgroundImage.getHeight()), new Rect(0, 0, createBitmap.getWidth(), createBitmap.getHeight()), paint);
            }
            canvas.drawBitmap(createImage, 0.0f, 0.0f, paint);
            createImage.recycle();
//            Bitmap image = this.mPhotoView.getImage(this.mOutputScale);
//            canvas.drawBitmap(image, 0.0f, 0.0f, paint);
//            image.recycle();
            System.gc();
            return createBitmap;
        } catch (OutOfMemoryError e) {
            throw e;
        }
    }

    public void buildLayout(TemplateItem templateItem) {
        this.mTemplatePhotoLayout = new FramePhotoLayout(this, templateItem.getPhotoItemList());
        this.mTemplatePhotoLayout.setQuickActionClickListener(this);
        Bitmap bitmap = this.mBackgroundImage;
        if (bitmap == null || bitmap.isRecycled()) {
            this.mContainerLayout.setBackgroundColor(this.mBackgroundColor);
        } else if (Build.VERSION.SDK_INT >= 16) {
            this.mContainerLayout.setBackground(new BitmapDrawable(getResources(), this.mBackgroundImage));
        } else {
            this.mContainerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), this.mBackgroundImage));
        }
        int width = this.mContainerLayout.getWidth();
        int height = this.mContainerLayout.getHeight();
        if (this.mLayoutRatio == 0) {
            if (width > height) {
                width = height;
            } else {
                height = width;
            }
        } else if (this.mLayoutRatio == 2) {
            if (width <= height) {
                double d = (double) width;
                Double.isNaN(d);
                double d2 = d * 1.61803398875d;
                double d3 = (double) height;
                if (d2 >= d3) {
                    Double.isNaN(d3);
                    width = (int) (d3 / 1.61803398875d);
                } else {
                    height = (int) d2;
                }
            } else if (height <= width) {
                double d4 = (double) height;
                Double.isNaN(d4);
                double d5 = d4 * 1.61803398875d;
                double d6 = (double) width;
                if (d5 >= d6) {
                    Double.isNaN(d6);
                    height = (int) (d6 / 1.61803398875d);
                } else {
                    width = (int) d5;
                }
            }
        }
        this.mOutputScale = ImageUtils.calculateOutputScaleFactor(width, height);
        this.mTemplatePhotoLayout.build(width, height, this.mOutputScale, this.mSpace, this.mCorner);
        Bundle bundle = this.mSavedInstanceState;
        if (bundle != null) {
            this.mTemplatePhotoLayout.restoreInstanceState(bundle);
            this.mSavedInstanceState = null;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        layoutParams.addRule(13);
        this.mContainerLayout.removeAllViews();
        this.mContainerLayout.addView(this.mTemplatePhotoLayout, layoutParams);
//        this.mContainerLayout.removeView(this.mPhotoView);
//        this.mContainerLayout.addView(this.mPhotoView, layoutParams);
        this.mSpaceBar.setProgress((int) ((this.mSpace * MAX_SPACE_PROGRESS) / MAX_SPACE));
        this.mCornerBar.setProgress((int) ((this.mCorner * MAX_CORNER_PROGRESS) / MAX_CORNER));
    }

    public void onEditActionClick(FrameImageView frameImageView) {
        this.mSelectedFrameImageView = frameImageView;
        if (frameImageView.getImage() != null && frameImageView.getPhotoItem().imagePath != null && frameImageView.getPhotoItem().imagePath.length() > 0) {
            requestEditingImage(Uri.fromFile(new File(frameImageView.getPhotoItem().imagePath)));
        }
    }

    @Override
    public void onChangeActionClick(FrameImageView frameImageView) {
        this.mSelectedFrameImageView = frameImageView;
        requestPhoto();
    }

    public void resultEditImage(Uri uri) {
        FrameImageView frameImageView = this.mSelectedFrameImageView;
        if (frameImageView != null) {
            frameImageView.setImagePath(FileUtils.getPath(this, uri));
        }
    }

    public void resultFromPhotoEditor(Uri uri) {
        FrameImageView frameImageView = this.mSelectedFrameImageView;
        if (frameImageView != null) {
            frameImageView.setImagePath(FileUtils.getPath(this, uri));
        }
    }

    private void recycleBackgroundImage() {
        Bitmap bitmap = this.mBackgroundImage;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mBackgroundImage.recycle();
            this.mBackgroundImage = null;
            System.gc();
        }
    }

    public void resultBackground(Uri uri) {
        recycleBackgroundImage();
        this.mBackgroundUri = uri;
        this.mBackgroundImage = ImageDecoder.decodeUriToBitmap(this, uri);
        if (Build.VERSION.SDK_INT >= 16) {
            mContainerLayout.setBackground(new BitmapDrawable(getResources(), this.mBackgroundImage));
        } else {
            mContainerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), this.mBackgroundImage));
        }
    }

    public void onColorChanged(int i) {
        recycleBackgroundImage();
        this.mBackgroundColor = i;
        this.mTemplatePhotoLayout.setBackgroundColor(this.mBackgroundColor);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == REQUEST_SELECT_PHOTO && i2 == RESULT_OK) {
            ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("selectedImages");
            if (this.mSelectedFrameImageView != null && stringArrayListExtra != null && !stringArrayListExtra.isEmpty()) {
                this.mSelectedFrameImageView.setImagePath(stringArrayListExtra.get(0));
                return;
            }
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    public void onBackgroundPhotoButtonClick() {
        super.onBackgroundPhotoButtonClick();
        //this.mAddImageDialog.dismiss();
        showRecyclerViewFragment(true);
        //setRecyclerViewAdapter(Constants.BACKGROUNDS, Constants.ITEMCATS[1]);
    }

    public void onStickerButtonClick() {
        super.onStickerButtonClick();
        //this.mAddImageDialog.dismiss();
        showRecyclerViewFragment(true);
        //setRecyclerViewAdapter(Constants.STICKERS, Constants.ITEMCATS[0]);
    }

    public void setRecyclerViewAdapter(int[] iArr, int i) {
        this.itemCat = i;
        this.recyclerViewStick.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        //this.recyclerViewStick.setAdapter(new MyAdapter(iArr, this));
        RecyclerView recyclerView = this.recyclerViewStick;
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            public void onLongItemClick(View view, int i) {
            }

            public void onItemClick(View view, int i) {
                if (TemplateDetailActivity.this.itemCat == 0) {
                    TemplateDetailActivity.this.resultStickerDrawable(i);
                } else if (TemplateDetailActivity.this.itemCat == 1) {
                    TemplateDetailActivity.this.resultBackgroundDrawable(i);
                }
            }
        }));
    }

    public void showRecyclerViewFragment(Boolean bool) {
        this.Switcher = bool;
        if (this.Switcher.booleanValue()) {
            this.itemsPanel.setVisibility(View.VISIBLE);
            this.Switcher = false;
            return;
        }
        this.itemsPanel.setVisibility(View.GONE);
        this.Switcher = true;
    }

    public void resultBackgroundDrawable(int i) {
        this.mBackgroundImage = BitmapFactory.decodeResource(getResources(), Constants.BACKGROUNDS[i]);
        this.mTemplatePhotoLayout.setBackgroundResource(Constants.BACKGROUNDS[i]);
    }

    public void finish() {
        recycleBackgroundImage();
        FramePhotoLayout framePhotoLayout = this.mTemplatePhotoLayout;
        if (framePhotoLayout != null) {
            framePhotoLayout.recycleImages();
        }
        super.finish();
    }
}