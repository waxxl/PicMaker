package com.eptonic.photocollage.ui.quickAction;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class PopupWindows {
    protected Drawable mBackground = null;
    protected Context mContext;
    protected View mRootView;
    protected PopupWindow mWindow;
    protected WindowManager mWindowManager;

    public void onDismiss() {
    }

    public void onShow() {
    }

    public PopupWindows(Context context) {
        mContext = context;
        mWindow = new PopupWindow(context);
        mWindow.setTouchInterceptor(new onToucheListener());
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    class onToucheListener implements View.OnTouchListener {
        onToucheListener() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != 4) {
                return false;
            }
            PopupWindows.this.mWindow.dismiss();
            return true;
        }
    }

    public void preShow() {
        if (this.mRootView != null) {
            onShow();
            Drawable drawable = this.mBackground;
            if (drawable == null) {
                this.mWindow.setBackgroundDrawable(new BitmapDrawable());
            } else {
                this.mWindow.setBackgroundDrawable(drawable);
            }
            mWindow.setWidth(-2);
            mWindow.setHeight(-2);
            mWindow.setTouchable(true);
            mWindow.setFocusable(true);
            mWindow.setOutsideTouchable(true);
            mWindow.setContentView(this.mRootView);
            return;
        }
        throw new IllegalStateException("setContentView was not called with a view to display.");
    }

    public void setBackgroundDrawable(Drawable drawable) {
        this.mBackground = drawable;
    }

    public void setContentView(View view) {
        this.mRootView = view;
        this.mWindow.setContentView(view);
    }

    public void setContentView(int i) {
        setContentView(((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(i, null));
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mWindow.setOnDismissListener(onDismissListener);
    }

    public void dismiss() {
        this.mWindow.dismiss();
    }
}