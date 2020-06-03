package com.yd.picmaker.Listener;

import com.yd.picmaker.mutitouchh.MultiTouchEntity;
import com.yd.picmaker.view.PhotoView;

public interface OnDoubleClickListener {
    void onBackgroundDoubleClick();

    void onPhotoViewDoubleClick(PhotoView photoView, MultiTouchEntity multiTouchEntity);
}