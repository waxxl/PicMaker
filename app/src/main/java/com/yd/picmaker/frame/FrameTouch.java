package com.yd.picmaker.frame;


import com.yd.picmaker.Listener.OnFrameTouchListener;

public abstract class FrameTouch implements OnFrameTouchListener {
    private boolean mImageFrameMoving = false;

    public void setImageFrameMoving(boolean z) {
        this.mImageFrameMoving = z;
    }

    public boolean isImageFrameMoving() {
        return this.mImageFrameMoving;
    }
}
