package com.yd.picmaker.Listener;

import android.view.MotionEvent;

public interface OnFrameTouchListener {
    void onFrameDoubleClick(MotionEvent motionEvent);

    void onFrameTouch(MotionEvent motionEvent);
}
