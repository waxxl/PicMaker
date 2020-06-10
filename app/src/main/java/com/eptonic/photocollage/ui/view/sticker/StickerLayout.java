package com.eptonic.photocollage.ui.view.sticker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.eptonic.photocollage.presenter.listener.EditEventListener;
import com.eptonic.photocollage.R;

import java.util.Arrays;

public class StickerLayout extends StickerView {
    private EditIconEvent editIconEvent;

    public void setEditListener(EditEventListener listener) {
        if(editIconEvent != null) {
            editIconEvent.setListener(listener);
        }
    }

    public void setStickerIcons(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
    }

    public StickerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setStickerIcons();
    }

    private void setStickerIcons() {
        BitmapStickerIcon bitmapStickerIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(), R.drawable.close), BitmapStickerIcon.LEFT_TOP);
        bitmapStickerIcon.setIconEvent(new DeleteIconEvent());
        BitmapStickerIcon bitmapStickerIcon2 = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(), R.drawable.zoom), BitmapStickerIcon.RIGHT_BOTOM);
        bitmapStickerIcon2.setIconEvent(new ZoomIconEvent());
        BitmapStickerIcon bitmapStickerIcon3 = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(), R.drawable.edit), BitmapStickerIcon.RIGHT_TOP);
        editIconEvent = new EditIconEvent();
        bitmapStickerIcon3.setIconEvent(editIconEvent);
        BitmapStickerIcon bitmapStickerIcon4 = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(), R.drawable.layer), BitmapStickerIcon.LEFT_BOTTOM);
        bitmapStickerIcon4.setIconEvent(new StickerButtonsClickListener());
        setIcons(Arrays.asList(bitmapStickerIcon, bitmapStickerIcon2, bitmapStickerIcon3, bitmapStickerIcon4));
        setBackgroundColor(-1);
        setLocked(false);
        setConstrained(true);
    }

    public void addImageSticker(int res) {
        addSticker(new DrawableSticker(ContextCompat.getDrawable(getContext(), res)));
    }

    public Sticker addMaskColorSticker(Bitmap bitmap, int i) {
        //TODO something error
        addSticker(new DrawableSticker(new BitmapDrawable(getContext().getResources(), bitmap))/*.setColorMask(i)*/);
        return null;
    }

    public Sticker addMaskImageSticker(Bitmap bitmap, Bitmap bitmap2) {
        addSticker(new DrawableSticker(new BitmapDrawable(getContext().getResources(), bitmap))/*.setBitmapMask(bitmap)*/);
        return null;
    }

    public void addImageSticker(Bitmap bitmap) {
        addSticker(new DrawableSticker(new BitmapDrawable(getContext().getResources(), bitmap)));
    }

    public void addTextSticker(String str) {
        TextSticker textSticker = new TextSticker(getContext());
        textSticker.setText(str);
        textSticker.resizeText();
        addSticker(textSticker);
    }

    public void addTextSticker(String str, int i) {
        TextSticker textSticker = new TextSticker(getContext());
        textSticker.setText(str);
        textSticker.setDrawable(ContextCompat.getDrawable(getContext(), i));
        textSticker.resizeText();
        addSticker(textSticker);
    }

    public void addTextSticker(String str, Bitmap bitmap) {
        TextSticker textSticker = new TextSticker(getContext());
        textSticker.setText(str);
        textSticker.setDrawable(new BitmapDrawable(getResources(), bitmap));
        textSticker.resizeText();
        addSticker(textSticker);
    }

    private class StickerButtonsClickListener implements StickerIconEvent {
        public void onActionDown(StickerView stickerView, MotionEvent motionEvent) {
        }

        public void onActionMove(StickerView stickerView, MotionEvent motionEvent) {
        }

        private StickerButtonsClickListener() {
        }

        public void onActionUp(StickerView stickerView, MotionEvent motionEvent) {
            Sticker currentSticker = stickerView.getCurrentSticker();
            if (currentSticker instanceof TextSticker) {
                Drawable drawable = currentSticker.getDrawable();
                TextSticker textSticker = (TextSticker) currentSticker;
                String text = textSticker.getText();
                int textColor = textSticker.getTextColor();
                Typeface typeFace = textSticker.getTypeFace();
                StickerLayout.this.remove(currentSticker);
                Matrix matrix = currentSticker.getMatrix();
                TextSticker textSticker2 = new TextSticker(StickerLayout.this.getContext());
                textSticker2.setDrawable(drawable);
                textSticker2.setText(text);
                textSticker2.setTypeface(typeFace);
                textSticker2.setTextColor(textColor);
                StickerLayout.this.addSticker(textSticker2);
                textSticker2.resizeText();
                textSticker2.setMatrix(matrix);
            } else if (currentSticker instanceof DrawableSticker) {
                Drawable drawable2 = currentSticker.getDrawable();
                StickerLayout.this.remove(currentSticker);
                Matrix matrix2 = currentSticker.getMatrix();
                DrawableSticker drawableSticker = new DrawableSticker(drawable2);
                StickerLayout.this.addSticker(drawableSticker);
                drawableSticker.setMatrix(matrix2);
            }
        }
    }
}
