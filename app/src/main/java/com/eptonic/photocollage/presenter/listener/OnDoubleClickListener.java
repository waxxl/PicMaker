package com.eptonic.photocollage.presenter.listener;

import com.eptonic.photocollage.ui.mutitouchh.MultiTouchEntity;
import com.eptonic.photocollage.ui.view.PhotoView;

public interface OnDoubleClickListener {
    void onBackgroundDoubleClick();

    void onPhotoViewDoubleClick(PhotoView photoView, MultiTouchEntity multiTouchEntity);
}