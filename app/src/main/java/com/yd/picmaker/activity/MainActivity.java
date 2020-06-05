package com.yd.picmaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.yd.picmaker.R;
import com.yd.picmaker.dialog.ExitDialog;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TextView mFreely, mTemplate, mFrame, mSave;
    private ExitDialog mExitDialog;

    @Override
    public int getId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFreely = findViewById(R.id.freely);
        mTemplate = findViewById(R.id.template);
        mFrame = findViewById(R.id.frame);
        mSave = findViewById(R.id.save);
        mFreely.setOnClickListener(this::onClick);
        mTemplate.setOnClickListener(this::onClick);
        mFrame.setOnClickListener(this::onClick);
        mSave.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.freely:
                gotoActivity(FreePhotoEditActivity.class);
                break;
            case R.id.template:
                gotoActivity(TemplateActivity.class);
                break;
            case R.id.save:
                gotoActivity(SaveResultActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivityForResult(new Intent(this, ExitActivity.class), 0x11);
    }

    private void gotoActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 0x11) {
            finish();
            System.exit(0);
        }
    }
}
