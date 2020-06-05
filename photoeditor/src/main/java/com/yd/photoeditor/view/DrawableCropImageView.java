package com.yd.photoeditor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.core.internal.view.SupportMenu;
import com.yd.photoeditor.config.ALog;
import com.yd.photoeditor.utils.PhotoUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class DrawableCropImageView extends ImageView {
    private static final String TAG = DrawableCropImageView.class.getSimpleName();
    private Bitmap mBitmap;
    /* access modifiers changed from: private */
    public Bitmap mCameraBitmap;
    private ArrayList<PointF> mCameraPointList = new ArrayList<>();
    private int mCameraX = 0;
    private int mCameraY = 0;
    /* access modifiers changed from: private */
    public int mCircleRadius = 0;
    /* access modifiers changed from: private */
    public boolean mEndDrawing = false;
    /* access modifiers changed from: private */
    public boolean mFingerDrawingMode = true;
    private int mOffsetRadius = 10;
    /* access modifiers changed from: private */
    public OnDrawMaskListener mOnDrawMaskListener;
    private final Paint mPaint = new Paint();
    private final Path mPath = new Path();
    /* access modifiers changed from: private */
    public ArrayList<PointF> mPointList = new ArrayList<>();
    private final OnTouchListener mTouchListener = new OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (!DrawableCropImageView.this.mFingerDrawingMode) {
                return false;
            }
            if (motionEvent.getAction() == 0) {
                boolean unused = DrawableCropImageView.this.mEndDrawing = false;
                DrawableCropImageView.this.mPointList.clear();
                DrawableCropImageView.this.mPointList.add(new PointF(motionEvent.getX(), motionEvent.getY()));
                if (DrawableCropImageView.this.mCameraBitmap != null) {
                    DrawableCropImageView.this.mCameraBitmap.recycle();
                }
                DrawableCropImageView drawableCropImageView = DrawableCropImageView.this;
                Bitmap unused2 = drawableCropImageView.mCameraBitmap = drawableCropImageView.createCircleCameraBitmap((int) motionEvent.getX(), (int) motionEvent.getY(), DrawableCropImageView.this.mCircleRadius);
                DrawableCropImageView.this.invalidate();
                if (DrawableCropImageView.this.mOnDrawMaskListener != null) {
                    DrawableCropImageView.this.mOnDrawMaskListener.onStartDrawing();
                }
            } else if (motionEvent.getAction() == 2) {
                DrawableCropImageView.this.mPointList.add(new PointF(motionEvent.getX(), motionEvent.getY()));
                if (DrawableCropImageView.this.mCameraBitmap != null) {
                    DrawableCropImageView.this.mCameraBitmap.recycle();
                }
                DrawableCropImageView drawableCropImageView2 = DrawableCropImageView.this;
                Bitmap unused3 = drawableCropImageView2.mCameraBitmap = drawableCropImageView2.createCircleCameraBitmap((int) motionEvent.getX(), (int) motionEvent.getY(), DrawableCropImageView.this.mCircleRadius);
                DrawableCropImageView.this.invalidate();
            } else {
                if (DrawableCropImageView.this.mCameraBitmap != null) {
                    DrawableCropImageView.this.mCameraBitmap.recycle();
                    Bitmap unused4 = DrawableCropImageView.this.mCameraBitmap = null;
                }
                boolean unused5 = DrawableCropImageView.this.mEndDrawing = true;
                DrawableCropImageView.this.invalidate();
                if (DrawableCropImageView.this.mOnDrawMaskListener != null) {
                    DrawableCropImageView.this.mOnDrawMaskListener.onFinishDrawing();
                }
            }
            return true;
        }
    };

    public interface OnDrawMaskListener {
        void onFinishDrawing();

        void onStartDrawing();
    }

    private boolean insideCircle(float f, float f2, float f3, float f4, float f5) {
        float f6 = f - f3;
        float f7 = f2 - f4;
        return ((double) ((f6 * f6) + (f7 * f7))) < ((double) (f5 * f5));
    }

    public DrawableCropImageView(Context context) {
        super(context);
        init();
    }

    public DrawableCropImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public DrawableCropImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }


    public void restoreInstanceState(Bundle bundle) {
        this.mOffsetRadius = bundle.getInt("com.yd.photoeditor.view.DrawableCropImageView.mOffsetRadius", this.mOffsetRadius);
        this.mCircleRadius = bundle.getInt("com.yd.photoeditor.view.DrawableCropImageView.mCircleRadius", this.mCircleRadius);
        this.mCameraY = bundle.getInt("com.yd.photoeditor.view.DrawableCropImageView.mCameraY", this.mCameraY);
        this.mCameraX = bundle.getInt("com.yd.photoeditor.view.DrawableCropImageView.mCameraX", this.mCameraX);
        ArrayList<PointF> parcelableArrayList = bundle.getParcelableArrayList("com.yd.photoeditor.view.DrawableCropImageView.mPointList");
        if (parcelableArrayList != null) {
            this.mPointList = parcelableArrayList;
        }
        ArrayList<PointF> parcelableArrayList2 = bundle.getParcelableArrayList("com.yd.photoeditor.view.DrawableCropImageView.mCameraPointList");
        if (parcelableArrayList2 != null) {
            this.mCameraPointList = parcelableArrayList2;
        }
        this.mFingerDrawingMode = bundle.getBoolean("com.yd.photoeditor.view.DrawableCropImageView.mFingerDrawingMode", this.mFingerDrawingMode);
        this.mEndDrawing = bundle.getBoolean("com.yd.photoeditor.view.DrawableCropImageView.mEndDrawing", this.mEndDrawing);
        setFingerDrawingMode(true);
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    private void init() {
        setOnTouchListener(this.mTouchListener);
        this.mOffsetRadius = (int) pxFromDp(50.0f);
        this.mCircleRadius = (int) pxFromDp(60.0f);
    }

    public void setOnDrawMaskListener(OnDrawMaskListener onDrawMaskListener) {
        this.mOnDrawMaskListener = onDrawMaskListener;
    }

    public void setImagePainterTouchListener() {
        setOnTouchListener(this.mTouchListener);
    }

    private void setCameraPosition(float f, float f2) {
        int i = this.mCircleRadius;
        if (f2 < ((float) (i * 2))) {
            this.mCameraY = (int) pxFromDp(3.0f);
            if (f > ((float) (getWidth() / 2))) {
                this.mCameraX = (int) pxFromDp(3.0f);
            } else {
                this.mCameraX = (int) (((float) (getWidth() - (this.mCircleRadius * 2))) - pxFromDp(3.0f));
            }
        } else {
            this.mCameraY = (int) ((f2 - ((float) (i * 2))) - ((float) this.mOffsetRadius));
            this.mCameraX = (int) (f - ((float) i));
        }
    }

    public void setFingerDrawingMode(boolean z) {
        this.mFingerDrawingMode = z;
        invalidate();
    }

    public boolean isFingerDrawingMode() {
        return this.mFingerDrawingMode;
    }

    public void clear() {
        this.mPointList.clear();
        this.mEndDrawing = false;
        this.mFingerDrawingMode = true;
        invalidate();
    }

    public Bitmap cropImage(boolean z) {
        Bitmap createBitmapMask;
        if (this.mPointList.isEmpty() || (createBitmapMask = createBitmapMask(this.mPointList)) == null) {
            return null;
        }
        Bitmap cropImage = cropImage(createBitmapMask, z);
        Bitmap cleanImage = PhotoUtils.cleanImage(cropImage);
        if (cropImage != cleanImage && !cropImage.isRecycled()) {
            cropImage.recycle();
        }
        return cleanImage;
    }

    private Bitmap cropImage(Bitmap bitmap, boolean z) {
        if (this.mBitmap == null) {
            return null;
        }
        float width = ((float) getWidth()) / ((float) getHeight());
        Bitmap transparentPadding = PhotoUtils.transparentPadding(this.mBitmap, width);
        Bitmap bitmap2 = this.mBitmap;
        if (transparentPadding != bitmap2 && z) {
            bitmap2.recycle();
            this.mBitmap = null;
        }
        Bitmap transparentPadding2 = PhotoUtils.transparentPadding(bitmap, width);
        if (transparentPadding2 != bitmap) {
            bitmap.recycle();
        }
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(transparentPadding2, transparentPadding.getWidth(), transparentPadding.getHeight(), true);
        if (createScaledBitmap != transparentPadding2) {
            transparentPadding2.recycle();
        }
        return cropImage(transparentPadding, createScaledBitmap, true);
    }

    private Bitmap createBitmapMask(ArrayList<PointF> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(-16777216);
        Path path = new Path();
        for (int i = 0; i < arrayList.size(); i++) {
            if (i == 0) {
                path.moveTo(arrayList.get(i).x, arrayList.get(i).y);
            } else {
                path.lineTo(arrayList.get(i).x, arrayList.get(i).y);
            }
        }
        canvas.drawPath(path, paint);
        canvas.clipPath(path);
        return createBitmap;
    }

    private Bitmap cropImage(Bitmap bitmap, Bitmap bitmap2, boolean z) {
        Canvas canvas = new Canvas();
        Bitmap createBitmap = Bitmap.createBitmap(bitmap2.getWidth(), bitmap2.getHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(createBitmap);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode(null);
        if (z) {
            bitmap.recycle();
            bitmap2.recycle();
        }
        return createBitmap;
    }

    /* access modifiers changed from: protected */
    public Bitmap createCircleCameraBitmap(int i, int i2, int i3) {
        float f = (float) i;
        float f2 = (float) i2;
        setCameraPosition(f, f2);
        if (i3 <= 0 || getWidth() < 10) {
            return null;
        }
        float calculateScaleRatio = calculateScaleRatio(this.mBitmap.getWidth(), this.mBitmap.getHeight());
        int[] calculateThumbnailSize = calculateThumbnailSize(this.mBitmap.getWidth(), this.mBitmap.getHeight());
        float width = ((float) (getWidth() - calculateThumbnailSize[0])) / 2.0f;
        float height = ((float) (getHeight() - calculateThumbnailSize[1])) / 2.0f;
        String str = TAG;
        ALog.d(str, "createCircleCameraBitmap, dx=" + width + ", dy=" + height);
        float f3 = f - width;
        float f4 = (float) i3;
        float f5 = f2 - height;
        Rect rect = new Rect((int) ((f3 - f4) * calculateScaleRatio), (int) ((f5 - f4) * calculateScaleRatio), (int) ((f3 + f4) * calculateScaleRatio), (int) ((f5 + f4) * calculateScaleRatio));
        int i4 = i3 * 2;
        int i5 = (int) (((float) i4) * calculateScaleRatio);
        Bitmap createBitmap = Bitmap.createBitmap(i5, i5, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        float f6 = calculateScaleRatio * f4;
        canvas.drawCircle(f6, f6, f6, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(this.mBitmap, rect, new Rect(0, 0, createBitmap.getWidth(), createBitmap.getHeight()), paint);
        this.mCameraPointList.clear();
        Iterator<PointF> it = this.mPointList.iterator();
        while (it.hasNext()) {
            PointF next = it.next();
            if (insideCircle(next.x, next.y, f, f2, f4)) {
                this.mCameraPointList.add(new PointF((next.x - f) + ((float) this.mCameraX) + f4, (next.y - f2) + ((float) this.mCameraY) + f4));
            } else if (!this.mCameraPointList.isEmpty()) {
                this.mCameraPointList.add(new PointF(-1.0f, -1.0f));
            }
        }
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(createBitmap, i4, i4, true);
        if (createBitmap != createScaledBitmap) {
            createBitmap.recycle();
        }
        return createScaledBitmap;
    }

    public float calculateScaleRatio(int i, int i2) {
        return Math.max(((float) i) / ((float) getWidth()), ((float) i2) / ((float) getHeight()));
    }

    public int[] calculateThumbnailSize(int i, int i2) {
        int[] iArr = new int[2];
        float f = (float) i;
        float width = f / ((float) getWidth());
        float f2 = (float) i2;
        float max = Math.max(width, f2 / ((float) getHeight()));
        if (max == width) {
            iArr[0] = getWidth();
            iArr[1] = (int) (f2 / max);
        } else {
            iArr[0] = (int) (f / max);
            iArr[1] = getHeight();
        }
        return iArr;
    }

    /* access modifiers changed from: protected */
    public Bitmap createRectangleCameraBitmap(int i, int i2, int i3) {
        Rect rect = new Rect(i - i3, i2 - i3, i + i3, i2 + i3);
        int i4 = i3 * 2;
        Bitmap createBitmap = Bitmap.createBitmap(i4, i4, Bitmap.Config.ARGB_8888);
        BitmapShader bitmapShader = new BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(bitmapShader);
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        new Canvas(createBitmap).drawBitmap(this.mBitmap, rect, new RectF(0.0f, 0.0f, (float) createBitmap.getWidth(), (float) createBitmap.getHeight()), paint);
        return createBitmap;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.mPointList.isEmpty() && this.mFingerDrawingMode) {
            Paint paint = new Paint();
            paint.setColor(SupportMenu.CATEGORY_MASK);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3.0f);
            Path path = new Path();
            for (int i = 0; i < this.mPointList.size(); i++) {
                if (i == 0) {
                    path.moveTo(this.mPointList.get(i).x, this.mPointList.get(i).y);
                } else {
                    path.lineTo(this.mPointList.get(i).x, this.mPointList.get(i).y);
                }
            }
            if (this.mEndDrawing) {
                path.lineTo(this.mPointList.get(0).x, this.mPointList.get(0).y);
                this.mFingerDrawingMode = false;
            }
            Bitmap bitmap = this.mCameraBitmap;
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, (float) this.mCameraX, (float) this.mCameraY, this.mPaint);
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                int i2 = this.mCameraX;
                int i3 = this.mCircleRadius;
                canvas.drawCircle((float) (i2 + i3), (float) (this.mCameraY + i3), (float) i3, paint);
                this.mPath.reset();
                boolean z = true;
                for (int i4 = 0; i4 < this.mCameraPointList.size(); i4++) {
                    if (this.mCameraPointList.get(i4).x > -1.0f && this.mCameraPointList.get(i4).y > -1.0f && z) {
                        this.mPath.moveTo(this.mCameraPointList.get(i4).x, this.mCameraPointList.get(i4).y);
                        z = false;
                    } else if (this.mCameraPointList.get(i4).x <= -1.0f || this.mCameraPointList.get(i4).y <= -1.0f) {
                        paint.setColor(SupportMenu.CATEGORY_MASK);
                        canvas.drawPath(this.mPath, paint);
                        this.mPath.reset();
                        z = true;
                    } else {
                        this.mPath.lineTo(this.mCameraPointList.get(i4).x, this.mCameraPointList.get(i4).y);
                    }
                }
                if (this.mEndDrawing) {
                    this.mPath.lineTo(this.mCameraPointList.get(0).x, this.mCameraPointList.get(0).y);
                    this.mFingerDrawingMode = false;
                }
                paint.setColor(SupportMenu.CATEGORY_MASK);
                canvas.drawPath(this.mPath, paint);
            }
            paint.setColor(SupportMenu.CATEGORY_MASK);
            canvas.drawPath(path, paint);
        }
    }

    public float dpFromPx(float f) {
        return f / getContext().getResources().getDisplayMetrics().density;
    }

    public float pxFromDp(float f) {
        return f * getContext().getResources().getDisplayMetrics().density;
    }
}
