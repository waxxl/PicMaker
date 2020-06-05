package com.yd.picmaker.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yd.picmaker.PicMakerApplication;
import com.yd.picmaker.R;
import com.yd.picmaker.adapter.SaveAdapter;
import com.yd.picmaker.model.PicStickerSaveData;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class SaveResultActivity extends BaseActivity {
    RecyclerView mSaveRecycler;

    @Override
    public int getId() {
        return R.layout.activity_save;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        setTitle(R.string.tab_save);
        mSaveRecycler = findViewById(R.id.save_recycler);
        QueryBuilder<PicStickerSaveData> builder = PicMakerApplication.getDaoSession().queryBuilder(PicStickerSaveData.class);
        List<PicStickerSaveData> datas = builder.build().list();
        mSaveRecycler.setHasFixedSize(false);
        mSaveRecycler.setLayoutManager(new GridLayoutManager(this, 3,RecyclerView.VERTICAL,false));
        mSaveRecycler.setAdapter(new SaveAdapter(datas));
    }
}
