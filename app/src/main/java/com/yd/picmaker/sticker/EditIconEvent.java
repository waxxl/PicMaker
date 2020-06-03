package com.yd.picmaker.sticker;

import android.content.Context;
import android.view.MotionEvent;

import com.yd.picmaker.Listener.EditEventListener;
import com.yd.photoeditor.ui.activity.ImageProcessingActivity;

public class EditIconEvent implements StickerIconEvent {
    EditEventListener listener;

    public void setListener(EditEventListener listener) {
        this.listener = listener;
    }
    @Override
    public void onActionDown(StickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionMove(StickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionUp(StickerView stickerView, MotionEvent event) {
        Context context = stickerView.getContext();
        if (stickerView.getCurrentSticker() instanceof TextSticker) {
            if (context != null) {

            }
        } else if (stickerView.getCurrentSticker() instanceof DrawableSticker) {
            if (context != null) {
                if(listener != null) {
                    listener.onImageEdit();
                }
            }

        }

    }
}
