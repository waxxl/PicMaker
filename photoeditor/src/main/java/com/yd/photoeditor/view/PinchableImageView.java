package com.yd.photoeditor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class PinchableImageView extends ImageView {
    private MultiTouchHandler mTouchHandler;

    public PinchableImageView(Context context) {
        super(context);
        setScaleType(ScaleType.FIT_CENTER);
    }

    public PinchableImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setScaleType(ScaleType.FIT_CENTER);
    }

    public PinchableImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setScaleType(ScaleType.FIT_CENTER);
    }

    public void reset() {
        this.mTouchHandler = null;
        setScaleType(ScaleType.FIT_CENTER);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mTouchHandler == null && getWidth() > 5 && getHeight() > 5) {
            setScaleType(ScaleType.MATRIX);
            this.mTouchHandler = new MultiTouchHandler();
            this.mTouchHandler.setMatrix(getImageMatrix());
            this.mTouchHandler.setEnableRotation(true);
        }
        MultiTouchHandler multiTouchHandler = this.mTouchHandler;
        if (multiTouchHandler == null) {
            return super.onTouchEvent(motionEvent);
        }
        multiTouchHandler.touch(motionEvent);
        setImageMatrix(this.mTouchHandler.getMatrix());
        return true;
    }

    public int[] calculateThumbnailSize(int i, int i2) {
        int[] iArr = new int[2];
        float f = (float) i;
        float width = f / ((float) getWidth());
        float f2 = (float) i2;
        float max = Math.max(width, f2 / ((float) getHeight()));
        if (max == width) {
            iArr[0] = getWidth();
            iArr[1] = (int) (f2 / max);
        } else {
            iArr[0] = (int) (f / max);
            iArr[1] = getHeight();
        }
        return iArr;
    }
}
