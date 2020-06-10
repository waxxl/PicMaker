package com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eptonic.photocollage.presenter.listener.RecyclerItemClickListener;
import com.eptonic.photocollage.R;
import com.eptonic.photocollage.model.TemplateItem;
import com.eptonic.photocollage.ui.mutitouchh.MultiTouchEntity;
import com.eptonic.photocollage.ui.view.template.ItemImageView;
import com.eptonic.photocollage.ui.view.template.PhotoItem;
import com.eptonic.photocollage.ui.view.PhotoLayout;
import com.eptonic.photocollage.ui.view.PhotoView;
import com.eptonic.photocollage.ui.view.TransitionImageView;
import com.yd.photoeditor.vv.FileUtils;
import com.yd.photoeditor.vv.PhotoUtils;

import java.io.File;

public class FrameDetailActivity extends PCBaseTemplateDetailActivity implements PhotoLayout.OnQuickActionClickListener {
    private ImageView crossBtn;
    private LinearLayout itemsPanel;
    private TransitionImageView mBackgroundImageView;
    private PhotoLayout mPhotoLayout;
    private ItemImageView mSelectedItemImageView;
    private RecyclerView recyclerViewStick;

    @Override
    public int getId() {
        return R.layout.activity_template_detail;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle(R.string.tab_frame);
        for (PhotoItem next : mSelectedTemplateItem.getPhotoItemList()) {
            if (next.imagePath != null && next.imagePath.length() > 0) {
                this.mSelectedPhotoPaths.add(next.imagePath);
            }
        }
        recyclerViewStick = findViewById(R.id.recycleViewStick);
        itemsPanel = findViewById(R.id.items_panel);
        crossBtn = findViewById(R.id.cross_btn);
        crossBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        recyclerViewStick.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerViewStick.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerViewStick, new RecyclerItemClickListener.OnItemClickListener() {
            public void onLongItemClick(View view, int i) {
            }

            public void onItemClick(View view, int i) {
                resultStickerDrawable(i);
            }
        }));
    }

    public Bitmap createOutputImage() {
        Bitmap createImage = mPhotoLayout.createImage();
        Bitmap createBitmap = Bitmap.createBitmap(createImage.getWidth(), createImage.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(createImage, 0.0f, 0.0f, paint);
        createImage.recycle();
//        Bitmap image = mPhotoView.getImage(mOutputScale);
//        canvas.drawBitmap(image, 0.0f, 0.0f, paint);
//        image.recycle();
//        System.gc();
        return createBitmap;
    }

    public void onEditActionClick(ItemImageView itemImageView) {
        this.mSelectedItemImageView = itemImageView;
        if (itemImageView.getImage() != null && itemImageView.getPhotoItem().imagePath != null && itemImageView.getPhotoItem().imagePath.length() > 0) {
            requestEditingImage(Uri.fromFile(new File(itemImageView.getPhotoItem().imagePath)));
        }
    }

    public void onChangeActionClick(ItemImageView itemImageView) {
        this.mSelectedItemImageView = itemImageView;
        Dialog backgroundImageDialog = getBackgroundImageDialog();
        if (backgroundImageDialog != null) {
            //backgroundImageDialog.findViewById(R.id.alterBackgroundView).setVisibility(8);
        }
        requestPhoto();
    }

    public void onChangeBackgroundActionClick(TransitionImageView transitionImageView) {
        this.mBackgroundImageView = transitionImageView;
        Dialog backgroundImageDialog = getBackgroundImageDialog();
        if (backgroundImageDialog != null) {
            //backgroundImageDialog.findViewById(R.id.alterBackgroundView).setVisibility(0);
        }
        requestPhoto();
    }

    public void resultEditImage(Uri uri) {
        TransitionImageView transitionImageView = this.mBackgroundImageView;
        if (transitionImageView != null) {
            transitionImageView.setImagePath(FileUtils.getPath(this, uri));
            this.mBackgroundImageView = null;
            return;
        }
        ItemImageView itemImageView = this.mSelectedItemImageView;
        if (itemImageView != null) {
            itemImageView.setImagePath(FileUtils.getPath(this, uri));
        }
    }

    public void resultFromPhotoEditor(Uri uri) {
        TransitionImageView transitionImageView = this.mBackgroundImageView;
        if (transitionImageView != null) {
            transitionImageView.setImagePath(FileUtils.getPath(this, uri));
            this.mBackgroundImageView = null;
            return;
        }
        ItemImageView itemImageView = this.mSelectedItemImageView;
        if (itemImageView != null) {
            itemImageView.setImagePath(FileUtils.getPath(this, uri));
        }
    }

    public void resultBackground(Uri uri) {
        TransitionImageView transitionImageView = this.mBackgroundImageView;
        if (transitionImageView != null) {
            transitionImageView.setImagePath(FileUtils.getPath(this, uri));
            this.mBackgroundImageView = null;
            return;
        }
        ItemImageView itemImageView = this.mSelectedItemImageView;
        if (itemImageView != null) {
            itemImageView.setImagePath(FileUtils.getPath(this, uri));
        }
    }

    public void buildLayout(TemplateItem templateItem) {
        Bitmap bitmap;
        PhotoLayout photoLayout = this.mPhotoLayout;
        if (photoLayout != null) {
            bitmap = photoLayout.getBackgroundImage();
            mPhotoLayout.recycleImages(false);
        } else {
            bitmap = null;
        }
        Bitmap decodePNGImage = PhotoUtils.decodePNGImage(this, templateItem.getTemplate());
        int[] calculateThumbnailSize = calculateThumbnailSize(decodePNGImage.getWidth(), decodePNGImage.getHeight());
        mPhotoLayout = new PhotoLayout(this, templateItem.getPhotoItemList(), decodePNGImage);
        mPhotoLayout.setBackgroundImage(bitmap);
        mPhotoLayout.setQuickActionClickListener(this);
        mPhotoLayout.build(calculateThumbnailSize[0], calculateThumbnailSize[1], this.mOutputScale);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(calculateThumbnailSize[0], calculateThumbnailSize[1]);
        layoutParams.addRule(Gravity.CENTER);
        mContainerLayout.removeAllViews();
        mContainerLayout.addView(mPhotoLayout, layoutParams);
//        mContainerLayout.removeView(mPhotoView);
//        mContainerLayout.addView(mPhotoView, layoutParams);
    }

    @Override
    public void onPhotoViewDoubleClick(PhotoView photoView, MultiTouchEntity multiTouchEntity) {

    }
}
