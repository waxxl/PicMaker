package com.yd.picmaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yd.picmaker.R;
import com.yd.picmaker.adapter.SelectedPicsAdapter;
import com.yd.picmaker.fragment.GalleryAlbumFragment;
import java.util.ArrayList;

public class GalleryAlbumActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "GalleryAlbumActivity";
    public static final String MAX_IMAGE_COUNT = "maxImageCount";
    public static final String IS_MAX_IMAGE_COUNT = "isMaxImageCount";
    public static final String SELECT_ITEMS = "selectCounts";
    private ArrayList<String> mSelects = new ArrayList<>();
    private View mToolBar;
    private ImageView mBack, mFunc;
    private TextView mTitle, mSelectCount;
    private RecyclerView mSelectedRecycler;
    private SelectedPicsAdapter mSelectPicsAdapter;
    private boolean isMaxImageCount = false;
    private int mMaxImageCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);
        initView();
        getFragmentManager().beginTransaction().replace(R.id.frame_container, new GalleryAlbumFragment()).commit();
    }

    private void initView() {
        initToolBar();
        setFunction(R.drawable.sure);
        setTitle(getString(R.string.gallery_albums));
        mSelectCount = findViewById(R.id.select_hint);
        mSelectedRecycler = findViewById(R.id.selected_pics);
        Intent intent = getIntent();
        if (intent != null) {
            isMaxImageCount = intent.getBooleanExtra(IS_MAX_IMAGE_COUNT, false);
            mMaxImageCount = intent.getIntExtra(MAX_IMAGE_COUNT, 2);
            mSelectCount.setText(getString(R.string.select_photo_hint, mMaxImageCount));
        }

        if(null == mSelectPicsAdapter) {
            mSelectPicsAdapter = new SelectedPicsAdapter();
        }
        mSelectedRecycler.setHasFixedSize(false);
        mSelectedRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mSelectedRecycler.setAdapter(mSelectPicsAdapter);
    }

    protected void initToolBar() {
        try {
            mToolBar = findViewById(R.id.toolbar);
            mBack = findViewById(R.id.back);
            mFunc = findViewById(R.id.func);
            mTitle = findViewById(R.id.title);
            mBack.setOnClickListener(this);
            mFunc.setOnClickListener(this::onClick);
        } catch (Exception e) {
            Log.e(TAG, "BaseActivity initToolBar failed");
        }

    }

    public void setTitle(String title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    protected void setFunction(int drawable) {
        if (mFunc != null) {
            mFunc.setImageResource(drawable);
            mFunc.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                setResult(RESULT_CANCELED);
                onBackPressed();
                break;
            case R.id.func:
                Intent intent = new Intent();
                intent.putStringArrayListExtra(SELECT_ITEMS, mSelects);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    public void addSelectPhotos(String pic) {
        if (mSelects != null) {
            if (!isMaxImageCount) {
                addData(pic);
            } else if (mSelects.size() < mMaxImageCount) {
                addData(pic);
            } else {
                Toast.makeText(this, getString(R.string.max_select_count, mMaxImageCount), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addData(String pic) {
        if(mSelectPicsAdapter != null) {
            mSelects.add(pic);
            mSelectPicsAdapter.addPic(pic);
            mSelectPicsAdapter.notifyItemInserted(mSelects.size() -1);
        }
    }

}
