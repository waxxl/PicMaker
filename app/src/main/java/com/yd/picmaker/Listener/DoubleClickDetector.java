package com.yd.picmaker.Listener;

import android.view.MotionEvent;

public class DoubleClickDetector {
    private static final long DOUBLE_CLICK_TIME_INTERVAL = 700;
    private float mOldX = 0.0f;
    private float mOldY = 0.0f;
    private int mSelectedCount = 0;
    private long mSelectedTime = System.currentTimeMillis();
    private float mTouchAreaInterval = 10.0f;

    public void reset() {
        this.mSelectedCount = 0;
        this.mSelectedTime = System.currentTimeMillis();
        this.mOldX = 0.0f;
        this.mOldY = 0.0f;
    }

    public void setTouchAreaInterval(float f) {
        this.mTouchAreaInterval = f;
    }

    public boolean doubleClick(MotionEvent motionEvent) {
        if ((motionEvent.getAction() & 255) != 0) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (this.mSelectedCount == 0) {
            this.mSelectedCount = 1;
            this.mSelectedTime = currentTimeMillis;
            this.mOldX = motionEvent.getX();
            this.mOldY = motionEvent.getY();
        } else {
            if (currentTimeMillis - this.mSelectedTime < DOUBLE_CLICK_TIME_INTERVAL) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                float f = this.mOldX;
                float f2 = this.mTouchAreaInterval;
                if (f + f2 > x && f - f2 < x) {
                    float f3 = this.mOldY;
                    if (f3 + f2 > y && f3 - f2 < y) {
                        this.mSelectedCount++;
                    }
                }
                this.mSelectedCount = 1;
                this.mSelectedTime = currentTimeMillis;
                this.mOldX = motionEvent.getX();
                this.mOldY = motionEvent.getY();
            } else {
                this.mSelectedCount = 1;
                this.mSelectedTime = currentTimeMillis;
                this.mOldX = motionEvent.getX();
                this.mOldY = motionEvent.getY();
            }
            if (this.mSelectedCount == 2) {
                this.mSelectedCount = 0;
                this.mOldX = 0.0f;
                this.mOldY = 0.0f;
                return true;
            }
        }
        return false;
    }
}