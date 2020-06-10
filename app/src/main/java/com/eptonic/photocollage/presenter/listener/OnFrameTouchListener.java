package com.eptonic.photocollage.presenter.listener;

import android.view.MotionEvent;

public interface OnFrameTouchListener {
    void onFrameDoubleClick(MotionEvent motionEvent);

    void onFrameTouch(MotionEvent motionEvent);
}
