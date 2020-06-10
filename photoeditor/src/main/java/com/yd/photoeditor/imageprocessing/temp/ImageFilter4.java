package com.yd.photoeditor.imageprocessing.temp;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.yd.photoeditor.imageprocessing.filter.ImageRender;
import java.io.File;

public class ImageFilter4 extends GLSurfaceView {
    private ImageRender mFilter;
    private ImageFilter3 mImageFilter3;
    private float mRatio = 0.0f;

    public void saveToPictures(String str, String str2, ImageFilter3.OnPictureSavedListener onPictureSavedListener) {
    }

    public ImageFilter4(Context context) {
        super(context);
        init();
    }

    public ImageFilter4(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        this.mImageFilter3 = new ImageFilter3(getContext());
        this.mImageFilter3.setGLSurfaceView(this);
    }

    public ImageFilter3 getImageProcessor() {
        return this.mImageFilter3;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (this.mRatio == 0.0f) {
            super.onMeasure(i, i2);
            return;
        }
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        float f = (float) size;
        float f2 = this.mRatio;
        float f3 = (float) size2;
        if (f / f2 < f3) {
            size2 = Math.round(f / f2);
        } else {
            size = Math.round(f3 * f2);
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(size2, MeasureSpec.EXACTLY));
    }

    public void setScaleType(ImageFilter3.ScaleType scaleType) {
        this.mImageFilter3.setScaleType(scaleType);
    }

    public void setBackground(float f, float f2, float f3, float f4) {
        this.mImageFilter3.setBackground(f, f2, f3, f4);
    }

    public void setRatio(float f) {
        this.mRatio = f;
        requestLayout();
        this.mImageFilter3.deleteImage();
    }

    public void setFilter(ImageRender imageRender) {
        this.mFilter = imageRender;
        this.mImageFilter3.setFilter(imageRender);
        requestRender();
    }

    public ImageRender getFilter() {
        return this.mFilter;
    }

    public void setImage(Bitmap bitmap) {
        this.mImageFilter3.setImage(bitmap);
    }

    public void setImage(Uri uri) {
        this.mImageFilter3.setImage(uri);
    }

    public void setImage(File file) {
        this.mImageFilter3.setImage(file);
    }
}
