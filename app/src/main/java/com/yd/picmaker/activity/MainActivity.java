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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if(mExitDialog == null) {
            ExitDialog.Builder builder = new ExitDialog.Builder(this);
            mExitDialog = builder.create();
        }
        if(!mExitDialog.isShowing()) {
            mExitDialog.show();
        }
    }

    private void gotoActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
