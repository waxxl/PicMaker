package com.yd.picmaker.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import com.yd.picmaker.R;
import java.io.File;

public class SaveOneActivity extends BaseActivity implements View.OnClickListener {
    public static final String IMAGE_URI_KEY = "imageUri";
    Uri uri;
    private ImageView content;

    @Override
    public int getId() {
        return R.layout.activity_save_one;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        setFunction(R.drawable.delete);
        setTitle(R.string.tab_save);
        uri = getIntent().getParcelableExtra(IMAGE_URI_KEY);
        content = findViewById(R.id.content);
        content.setImageURI(uri);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.func:
                File file = new File(uri.getPath());
                if(file.exists()) {
                    file.delete();
                }
                finish();
            break;
        }
    }
}
