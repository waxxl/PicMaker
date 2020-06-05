package com.yd.picmaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yd.picmaker.R;
import com.yd.picmaker.model.TemplateItem;
import com.yd.picmaker.template.PhotoItem;
import com.yd.picmaker.util.PermissionUtils;
import com.yd.picmaker.util.TemplateImageUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class TemplateActivity extends BaseActivity {
    public static final String EXTRA_IMAGE_IN_TEMPLATE_COUNT = "imageInTemplateCount";
    public static final String EXTRA_IMAGE_PATHS = "imagePaths";
    public static final String EXTRA_IS_FRAME_IMAGE = "frameImage";
    public static final String EXTRA_SELECTED_TEMPLATE_INDEX = "selectedTemplateIndex";
    private static final String KEY_HEADER_POSITIONING = "key_header_mode";
    private static final String KEY_MARGINS_FIXED = "key_margins_fixed";
    private static final int REQUEST_SELECT_PHOTO = 789;
    private RecyclerView templateRecycler;
    private final ArrayList<TemplateItem> mTemplateItemList = new ArrayList<>();
    private final ArrayList<TemplateItem> mAllTemplateItemList = new ArrayList<>();
    private final int mImageInTemplateCount = 2;

    @Override
    public int getId() {
        return R.layout.activity_template;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initToolBar();
        setTitle(getString(R.string.tab_template));
        templateRecycler = findViewById(R.id.template_recycler);
        loadTemplateImages();
        Intent intent =new Intent(this, GalleryAlbumActivity.class);
        intent.putExtra("imageCount", 2);
        intent.putExtra("isMaxImageCount", true);
        templateRecycler.setHasFixedSize(false);
        templateRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        //templateRecycler.setAdapter();
        if(PermissionUtils.checkPermissionsGranted(this)) {
            startActivityForResult(intent, REQUEST_SELECT_PHOTO);
        }
    }

    private void loadTemplateImages() {
        mAllTemplateItemList.clear();
        mAllTemplateItemList.addAll(TemplateImageUtils.loadTemplates());
        mTemplateItemList.clear();
        if (this.mImageInTemplateCount > 0) {
            Iterator<TemplateItem> it = this.mAllTemplateItemList.iterator();
            while (it.hasNext()) {
                TemplateItem next = it.next();
                if (next.getPhotoItemList().size() == mImageInTemplateCount) {
                    this.mTemplateItemList.add(next);
                }
            }
            return;
        }
        mTemplateItemList.addAll(this.mAllTemplateItemList);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_SELECT_PHOTO == requestCode && resultCode == RESULT_OK) {
            TemplateItem templateItem = mAllTemplateItemList.get(0);
            Intent intent = new Intent(this, TemplateDetailActivity.class);
            intent.putExtra(EXTRA_IMAGE_IN_TEMPLATE_COUNT, templateItem.getPhotoItemList().size());
            intent.putExtra(EXTRA_IS_FRAME_IMAGE, 2);
            intent.putExtra(EXTRA_SELECTED_TEMPLATE_INDEX, 2);

            ArrayList arrayList = new ArrayList();
            for (PhotoItem next2 : templateItem.getPhotoItemList()) {
                if (next2.imagePath == null) {
                    next2.imagePath = "";
                }
                arrayList.add(next2.imagePath);
            }
            intent.putExtra(EXTRA_IMAGE_PATHS, arrayList);
            startActivity(intent);
        }
    }

    private void gotoAppSetting() {
        Toast.makeText(this, getString(R.string.permission_denied_and_guide_to_setting), Toast.LENGTH_LONG).show();
        PermissionUtils.goAppSetting(this);
    }
}