package com.yd.photoeditor.particle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.yd.photoeditor.R;

public class Particle {
    public static final int ALIVE = 0;
    public static final int DEAD = 1;
    private static Bitmap mBase;
    private float mAge;
    private int mAlpha;
    private final Bitmap mBitmap;
    private final float mLifetime;
    private final Paint mPaint;
    private int mState = 0;
    private float mX;
    private double mXV;
    private float mY;
    private double mYV;

    private double randomDouble(double d, double d2) {
        return d + ((d2 - d) * Math.random());
    }

    public boolean isAlive() {
        return this.mState == 0;
    }

    public Particle(int i, int i2, int i3, int i4, int i5, Context context) {
        this.mX = (float) i;
        this.mY = (float) i2;
        if (mBase == null) {
            mBase = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
        }
        double width = mBase.getWidth();
        double d = i5;
        double randomDouble = randomDouble(1.01d, d);
        Double.isNaN(width);
        double height = mBase.getHeight();
        double randomDouble2 = randomDouble(1.01d, d);
        Double.isNaN(height);
        this.mBitmap = Bitmap.createScaledBitmap(mBase, (int) (width * randomDouble), (int) (height * randomDouble2), true);
        this.mLifetime = (float) i3;
        this.mAge = 0.0f;
        this.mAlpha = 255;
        double d2 = i4 * 2;
        double randomDouble3 = randomDouble(0.0d, d2);
        double d3 = i4;
        Double.isNaN(d3);
        this.mXV = randomDouble3 - d3;
        double randomDouble4 = randomDouble(0.0d, d2);
        Double.isNaN(d3);
        this.mYV = randomDouble4 - d3;
        this.mPaint = new Paint();
        double d4 = this.mXV;
        double d5 = this.mYV;
        if ((d4 * d4) + (d5 * d5) > ((double) (i4 * i4))) {
            this.mXV = d4 * 0.7d;
            this.mYV = d5 * 0.7d;
        }
    }

    public void update() {
        if (this.mState != 1) {
            double d = this.mX;
            double d2 = this.mXV;
            Double.isNaN(d);
            this.mX = (float) (d + d2);
            double d3 = this.mY;
            double d4 = this.mYV;
            Double.isNaN(d3);
            this.mY = (float) (d3 + d4);
            if (this.mAlpha <= 0) {
                this.mState = 1;
            } else {
                this.mAge += 1.0f;
                this.mAlpha = (int) (255.0f - (((this.mAge / this.mLifetime) * 2.0f) * 255.0f));
                this.mPaint.setAlpha(this.mAlpha);
            }
            if (this.mAge >= this.mLifetime) {
                this.mState = 1;
            }
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.mBitmap, this.mX, this.mY, this.mPaint);
    }
}
