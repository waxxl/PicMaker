package com.yd.photoeditor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import com.yd.photoeditor.R;
import com.yd.photoeditor.config.ALog;

public class PreviewDrawingView extends View {
    private Paint mDrawPaint;
    private Path mDrawPath;
    private int mPaintColor = SupportMenu.CATEGORY_MASK;
    private float mPaintSize = 30.0f;

    public PreviewDrawingView(Context context) {
        super(context);
        setupDrawing();
    }

    public PreviewDrawingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setupDrawing();
    }

    public PreviewDrawingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setupDrawing();
    }

    private void setupDrawing() {
        this.mDrawPath = new Path();
        this.mDrawPaint = new Paint();
        this.mDrawPaint.setColor(this.mPaintColor);
        this.mDrawPaint.setAntiAlias(true);
        this.mPaintSize = getContext().getResources().getDimension(R.dimen.photo_editor_min_finger_width);
        this.mDrawPaint.setStrokeWidth(this.mPaintSize);
        this.mDrawPaint.setStyle(Paint.Style.STROKE);
        this.mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mDrawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void init(int i, int i2) {
        ALog.d("PreviewDrawingView", "init, widthView=" + i + ", heightView=" + i2);
        this.mDrawPath.reset();
        for (int i3 = 10; i3 < 90; i3++) {
            float f = ((float) i3) * (((float) i) / 100.0f);
            double d = i3;
            Double.isNaN(d);
            double d2 = i2 / 3;
            double cos = Math.cos((float) (d * 0.031415926535897934d));
            Double.isNaN(d2);
            float f2 = ((float) (d2 * cos)) + ((float) (i2 / 2));
            if (i3 == 10) {
                this.mDrawPath.moveTo(f, f2);
            } else {
                this.mDrawPath.lineTo(f, f2);
            }
        }
        invalidate();
    }

    public void setPaintColor(int i) {
        this.mPaintColor = i;
        this.mDrawPaint.setColor(this.mPaintColor);
        invalidate();
    }

    public void setPaintSize(float f) {
        this.mPaintSize = f;
        this.mDrawPaint.setStrokeWidth(this.mPaintSize);
        invalidate();
    }

    public float getPaintSize() {
        return this.mPaintSize;
    }

    public int getPaintColor() {
        return this.mPaintColor;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.drawPath(this.mDrawPath, this.mDrawPaint);
    }
}
