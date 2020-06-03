package com.yd.photoeditor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.yd.photoeditor.R;
import com.yd.photoeditor.particle.Explosion;

@SuppressLint("AppCompatCustomView")
public class TouchBlurView extends ImageView {
    private static final int FRAME_RATE = 30;
    private static final int NUM_PARTICLES = 25;
    private Explosion mExplosion;
    private int mExplosionImageHeight = 0;
    private int mExplosionImageWidth = 0;
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public Runnable mRunner = new Runnable() {
        public void run() {
            TouchBlurView.this.mHandler.removeCallbacks(TouchBlurView.this.mRunner);
            TouchBlurView.this.invalidate();
        }
    };
    private OnTouchBlurListener mTouchBlurListener;

    public interface OnTouchBlurListener {
        void onTouchBlur(float f, float f2);
    }

    public TouchBlurView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public TouchBlurView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public TouchBlurView(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.mHandler = new Handler();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.edit, options);
        this.mExplosionImageWidth = options.outHeight;
        this.mExplosionImageHeight = options.outWidth;
    }

    public void setTouchBlurListener(OnTouchBlurListener onTouchBlurListener) {
        this.mTouchBlurListener = onTouchBlurListener;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Explosion explosion = this.mExplosion;
        if (explosion == null || explosion.isDead()) {
            Explosion explosion2 = this.mExplosion;
            if (explosion2 != null) {
                explosion2.isDead();
            }
        } else {
            this.mExplosion.update(canvas);
            this.mHandler.removeCallbacks(this.mRunner);
            this.mHandler.postDelayed(this.mRunner, 30);
        }
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0) {
            return super.onTouchEvent(motionEvent);
        }
        Explosion explosion = this.mExplosion;
        if (explosion == null || explosion.isDead()) {
            this.mExplosion = new Explosion(25, ((int) motionEvent.getX()) - (this.mExplosionImageWidth / 2), ((int) motionEvent.getY()) - (this.mExplosionImageHeight / 2), getContext());
            this.mHandler.removeCallbacks(this.mRunner);
            this.mHandler.post(this.mRunner);
        }
        OnTouchBlurListener onTouchBlurListener = this.mTouchBlurListener;
        if (onTouchBlurListener == null) {
            return true;
        }
        onTouchBlurListener.onTouchBlur(motionEvent.getX(), motionEvent.getY());
        return true;
    }
}
