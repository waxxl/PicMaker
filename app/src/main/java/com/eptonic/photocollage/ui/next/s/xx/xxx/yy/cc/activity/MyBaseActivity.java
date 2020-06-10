package com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eptonic.photocollage.R;

public abstract class MyBaseActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MyBaseActivity";
    private View mToolBar;
    private ImageView mBack, mRight;
    private TextView mTitle;

    public abstract int getId();

    public void initData(){};

    public abstract void initView();

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
        initData();
        initView();
    }

    public void initToolBar() {
        try {
            mToolBar = findViewById(R.id.bar_tool_x);
            mBack = findViewById(R.id.back_press);
            mRight = findViewById(R.id.toolbar_f);
            mTitle = findViewById(R.id.title_tv);
            mBack.setOnClickListener(this);
            mRight.setOnClickListener(this);
        } catch (Exception e) {
            Log.e(TAG, "BaseActivity initToolBar failed");
        }
    }

    public void setTitle(String title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    public void setTitle(int title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    public void setRight(int drawable) {
        if (mRight != null) {
            mRight.setImageResource(drawable);
            mRight.setVisibility(View.VISIBLE);
            mRight.setOnClickListener(this);
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
            case R.id.back_press:
                finish();
                break;
        }
    }

    public ImageView getFunc() {
        return mRight;
    }
}
