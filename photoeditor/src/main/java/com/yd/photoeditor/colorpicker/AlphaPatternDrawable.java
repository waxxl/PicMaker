package com.yd.photoeditor.colorpicker;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class AlphaPatternDrawable extends Drawable {
    private Bitmap mBitmap;
    private final Paint mPaint = new Paint();
    private final Paint mPaintGray = new Paint();
    private final Paint mPaintWhite = new Paint();
    private int mRectangleSize = 10;
    private int numRectanglesHorizontal;
    private int numRectanglesVertical;

    @SuppressLint("WrongConstant")
    public int getOpacity() {
        return 0;
    }

    public AlphaPatternDrawable(int i) {
        this.mRectangleSize = i;
        this.mPaintWhite.setColor(-1);
        this.mPaintGray.setColor(-3421237);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.mBitmap, null, getBounds(), this.mPaint);
    }

    public void setAlpha(int i) {
        throw new UnsupportedOperationException("Alpha is not supported by this drawwable.");
    }

    public void setColorFilter(ColorFilter colorFilter) {
        throw new UnsupportedOperationException("ColorFilter is not supported by this drawwable.");
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        int height = rect.height();
        this.numRectanglesHorizontal = (int) Math.ceil(rect.width() / this.mRectangleSize);
        this.numRectanglesVertical = (int) Math.ceil(height / this.mRectangleSize);
        generatePatternBitmap();
    }

    private void generatePatternBitmap() {
        if (getBounds().width() > 0 && getBounds().height() > 0) {
            this.mBitmap = Bitmap.createBitmap(getBounds().width(), getBounds().height(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(this.mBitmap);
            Rect rect = new Rect();
            boolean z = true;
            for (int i = 0; i <= this.numRectanglesVertical; i++) {
                boolean z2 = z;
                for (int i2 = 0; i2 <= this.numRectanglesHorizontal; i2++) {
                    int i3 = this.mRectangleSize;
                    rect.top = i * i3;
                    rect.left = i3 * i2;
                    rect.bottom = rect.top + this.mRectangleSize;
                    rect.right = rect.left + this.mRectangleSize;
                    canvas.drawRect(rect, z2 ? this.mPaintWhite : this.mPaintGray);
                    z2 = !z2;
                }
                z = !z;
            }
        }
    }
}
