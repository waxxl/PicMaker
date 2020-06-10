package com.eptonic.photocollage.ui.view.frame;


import com.eptonic.photocollage.presenter.listener.OnFrameTouchListener;

public abstract class FrameTouch implements OnFrameTouchListener {
    private boolean mImageFrameMoving = false;

    public void setImageFrameMoving(boolean z) {
        this.mImageFrameMoving = z;
    }

    public boolean isImageFrameMoving() {
        return this.mImageFrameMoving;
    }
}
