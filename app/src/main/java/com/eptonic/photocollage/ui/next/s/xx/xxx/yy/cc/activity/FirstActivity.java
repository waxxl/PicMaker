package com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.eptonic.photocollage.R;

public class FirstActivity extends MyBaseActivity {
    private static final int START_NEXT = 1;
    private static final int TIME_DELAY = 1500;
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case START_NEXT:
                    Intent intent = new Intent(FirstActivity.this, MainActivityPhotoCollage.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    public int getId() {
        return R.layout.activity_start_x_page;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.removeMessages(START_NEXT);
        mHandler.sendEmptyMessageDelayed(START_NEXT, TIME_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
