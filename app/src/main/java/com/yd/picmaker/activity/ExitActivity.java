package com.yd.picmaker.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yd.picmaker.R;

public class ExitActivity extends BaseActivity {
    @Override
    public int getId() {
        return R.layout.activity_exit;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
