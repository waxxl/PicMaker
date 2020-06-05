package com.yd.picmaker.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yd.picmaker.R;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BaseActivity";
    private View mToolBar;
    private ImageView mBack, mFunc;
    private TextView mTitle;

    public abstract int getId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        try {
            setStatusBarColor(this, R.color.color_primary_transparent);
        } catch (Exception e) {
            Log.e(TAG, "setStatusBarColor failed");
        }

        setContentView(getId());
    }

    protected void initToolBar() {
        try {
            mToolBar = findViewById(R.id.toolbar);
            mBack = findViewById(R.id.back);
            mFunc = findViewById(R.id.func);
            mTitle = findViewById(R.id.title);
            mBack.setOnClickListener(this);
            mFunc.setOnClickListener(this);
        } catch (Exception e) {
            Log.e(TAG, "BaseActivity initToolBar failed");
        }
    }

    public void setTitle(String title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    protected void setFunction(int drawable) {
        if (mFunc != null) {
            mFunc.setImageResource(drawable);
            mFunc.setVisibility(View.VISIBLE);
            mFunc.setOnClickListener(this);
        }
    }

    public static void setStatusBarColor(Activity activity, int colorId) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getResources().getColor(colorId));
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
