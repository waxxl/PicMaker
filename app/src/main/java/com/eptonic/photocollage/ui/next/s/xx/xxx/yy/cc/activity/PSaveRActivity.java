package com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eptonic.photocollage.PhotoCollageApplication;
import com.eptonic.photocollage.R;
import com.eptonic.photocollage.adapter.SaveAdapter;
import com.eptonic.photocollage.model.PhotoStickerSaveData;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class PSaveRActivity extends MyBaseActivity {
    RecyclerView mSaveRecycler;

    @Override
    public int getId() {
        return R.layout.activity_save;
    }

    @Override
    public void initView() {
        initToolBar();
        setTitle(R.string.tab_save);
        mSaveRecycler = findViewById(R.id.pic_maker_save_recycler);
        QueryBuilder<PhotoStickerSaveData> builder = PhotoCollageApplication.getDaoSession().queryBuilder(PhotoStickerSaveData.class);
        List<PhotoStickerSaveData> datas = builder.build().list();
        mSaveRecycler.setHasFixedSize(false);
        mSaveRecycler.setLayoutManager(new GridLayoutManager(this, 3,RecyclerView.VERTICAL,false));
        mSaveRecycler.setAdapter(new SaveAdapter(datas));
    }
}
