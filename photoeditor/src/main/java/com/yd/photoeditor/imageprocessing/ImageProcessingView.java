package com.yd.photoeditor.imageprocessing;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.View;
import com.yd.photoeditor.imageprocessing.ImageProcessor;
import com.yd.photoeditor.imageprocessing.filter.ImageFilter;
import java.io.File;

public class ImageProcessingView extends GLSurfaceView {
    private ImageFilter mFilter;
    private ImageProcessor mImageProcessor;
    private float mRatio = 0.0f;

    public void saveToPictures(String str, String str2, ImageProcessor.OnPictureSavedListener onPictureSavedListener) {
    }

    public ImageProcessingView(Context context) {
        super(context);
        init();
    }

    public ImageProcessingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        this.mImageProcessor = new ImageProcessor(getContext());
        this.mImageProcessor.setGLSurfaceView(this);
    }

    public ImageProcessor getImageProcessor() {
        return this.mImageProcessor;
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

    public void setScaleType(ImageProcessor.ScaleType scaleType) {
        this.mImageProcessor.setScaleType(scaleType);
    }

    public void setBackground(float f, float f2, float f3, float f4) {
        this.mImageProcessor.setBackground(f, f2, f3, f4);
    }

    public void setRatio(float f) {
        this.mRatio = f;
        requestLayout();
        this.mImageProcessor.deleteImage();
    }

    public void setFilter(ImageFilter imageFilter) {
        this.mFilter = imageFilter;
        this.mImageProcessor.setFilter(imageFilter);
        requestRender();
    }

    public ImageFilter getFilter() {
        return this.mFilter;
    }

    public void setImage(Bitmap bitmap) {
        this.mImageProcessor.setImage(bitmap);
    }

    public void setImage(Uri uri) {
        this.mImageProcessor.setImage(uri);
    }

    public void setImage(File file) {
        this.mImageProcessor.setImage(file);
    }
}
