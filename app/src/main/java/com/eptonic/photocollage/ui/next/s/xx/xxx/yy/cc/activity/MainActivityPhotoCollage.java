package com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.eptonic.photocollage.R;

public class MainActivityPhotoCollage extends MyBaseActivity implements View.OnClickListener {
    private static final int EXIT_CODE = 0x1011;
    private TextView mFreely, mTemplate, mFrame, mSave;

    @Override
    public int getId() {
        return R.layout.activity_tab_select;
    }

    public void initView() {
        mFreely = findViewById(R.id.free_edit);
        mFreely.setOnClickListener(this::onClick);
        mTemplate = findViewById(R.id.item_template_edit);
        mTemplate.setOnClickListener(this::onClick);
        mFrame = findViewById(R.id.item_frame);
        mFrame.setOnClickListener(this::onClick);
        mSave = findViewById(R.id.item_save);
        mSave.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.free_edit:
                gotoActivity(PCFreelyEditActivity.class);
                break;
            case R.id.item_template_edit:
                gotoActivity(TemplateActivity.class);
                break;
            case R.id.item_save:
                gotoActivity(PSaveRActivity.class);
                break;
            case R.id.item_frame:
                gotoActivity(FrameActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivityForResult(new Intent(this, ExitActivity.class), EXIT_CODE);
    }

    private void gotoActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == EXIT_CODE) {
            finish();
            System.exit(0);
        }
    }
}
