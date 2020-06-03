package com.yd.picmaker.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yd.picmaker.PCApplication;
import com.yd.picmaker.R;
import com.yd.picmaker.adapter.SaveAdapter;
import com.yd.picmaker.model.StickerSaveData;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class SaveResultActivity extends BaseActivity {
    RecyclerView mSaveRecycler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        initToolBar();
        setTitle(R.string.tab_save);
        mSaveRecycler = findViewById(R.id.save_recycler);
        QueryBuilder<StickerSaveData> builder = PCApplication.getDaoSession().queryBuilder(StickerSaveData.class);
        List<StickerSaveData> datas = (List<StickerSaveData>) builder.build().list();
        mSaveRecycler.setHasFixedSize(false);
        mSaveRecycler.setLayoutManager(new GridLayoutManager(this, 3,RecyclerView.VERTICAL,false));
        mSaveRecycler.setAdapter(new SaveAdapter(datas));
    }
}
