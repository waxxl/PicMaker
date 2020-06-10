package com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity;

import android.view.View;
import android.widget.TextView;
import com.eptonic.photocollage.R;

public class ExitActivity extends MyBaseActivity {
    @Override
    public int getId() {
        return R.layout.activity_exit;
    }

    @Override
    public void initView() {
        TextView cancel = findViewById(R.id.cancel);
        TextView exit = findViewById(R.id.exit);
        cancel.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.exit:
                setResult(RESULT_OK);
                finish();
                break;
            default:
                break;
        }
    }

}
