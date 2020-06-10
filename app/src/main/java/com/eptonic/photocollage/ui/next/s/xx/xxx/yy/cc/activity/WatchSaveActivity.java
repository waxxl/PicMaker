package com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.eptonic.photocollage.R;
import java.io.File;

public class WatchSaveActivity extends MyBaseActivity implements View.OnClickListener {
    public static final String IMAGE_URI_KEY = "image_uri_key";
    private Uri uri;
    private ImageView content;

    @Override
    public int getId() {
        return R.layout.activity_save_one;
    }

    @Override
    public void initView() {
        initToolBar();
        setRight(R.drawable.delete);
        setTitle(R.string.tab_save);
        uri = getIntent().getParcelableExtra(IMAGE_URI_KEY);
        content = findViewById(R.id.content);
        content.setImageURI(uri);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.toolbar_f:
                File file = new File(uri.getPath());
                if(file.exists()) {
                    file.delete();
                }
                finish();
            break;
        }
    }
}
