package com.yd.picmaker.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.yd.picmaker.R;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BaseActivity";
    private View mToolBar;
    private ImageView mBack,mFunc;
    private TextView mTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected void initToolBar() {
        try {
            mToolBar = findViewById(R.id.toolbar);
            mBack = findViewById(R.id.back);
            mFunc = findViewById(R.id.func);
            mTitle = findViewById(R.id.title);
            mBack.setOnClickListener(this);
            mFunc.setOnClickListener(this::onClick);
        } catch (Exception e) {
            Log.e(TAG, "BaseActivity initToolBar failed");
        }

    }

    public void setTitle(String title) {
        if(mTitle != null) {
            mTitle.setText(title);
        }
    }

    protected void setFunction(int drawable) {
        if(mFunc != null) {
            mFunc.setImageResource(drawable);
            mFunc.setVisibility(View.VISIBLE);
            mFunc.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    public ImageView getFunc() {
        return mFunc;
    }
}
