package com.yd.picmaker.mutitouchh;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.yd.picmaker.R;
import com.yd.photoeditor.utils.ImageDecoder;

public class ImageEntity extends MultiTouchEntity {
    public static final Parcelable.Creator<ImageEntity> CREATOR = new Parcelable.Creator<ImageEntity>() {
        public ImageEntity createFromParcel(Parcel parcel) {
            return new ImageEntity(parcel);
        }

        public ImageEntity[] newArray(int i) {
            return new ImageEntity[i];
        }
    };
    private int mBorderColor = -16711936;
    private float mBorderSize = 3.0f;
    private RectF mBoundRect = new RectF();
    private boolean mDrawImageBorder = false;
    private boolean mDrawShadow = false;
    private transient Drawable mDrawable;
    private final GradientDrawable mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0, -7829368});
    private Uri mImageUri = null;
    private double mInitScaleFactor = 0.25d;
    private final Paint mPaint = new Paint(1);
    private int mResourceId = -1;
    private int mShadowSize = 0;
    private boolean mSticker = true;

    public ImageEntity(int i, Resources resources) {
        super(resources);
        this.mResourceId = i;
        this.mImageUri = null;
        loadConfigs(resources);
    }

    public ImageEntity(Uri uri, Resources resources) {
        super(resources);
        this.mImageUri = uri;
        this.mResourceId = -1;
        loadConfigs(resources);
    }

    public ImageEntity(ImageEntity imageEntity, Resources resources) {
        super(resources);
        this.mDrawable = imageEntity.mDrawable;
        this.mResourceId = imageEntity.mResourceId;
        this.mScaleX = imageEntity.mScaleX;
        this.mScaleY = imageEntity.mScaleY;
        this.mCenterX = imageEntity.mCenterX;
        this.mCenterY = imageEntity.mCenterY;
        this.mAngle = imageEntity.mAngle;
        this.mImageUri = imageEntity.mImageUri;
        loadConfigs(resources);
    }

    public void loadConfigs(Resources resources) {
        this.mBorderSize = resources.getDimension(R.dimen.image_border_size);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(this.mBorderSize);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setDither(true);
    }

    public void setBorderColor(int i) {
        this.mBorderColor = i;
    }

    public void setDrawImageBorder(boolean z) {
        this.mDrawImageBorder = z;
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public boolean isDrawImageBorder() {
        return this.mDrawImageBorder;
    }

    public void setSticker(boolean z) {
        this.mSticker = z;
    }

    public boolean isSticker() {
        return this.mSticker;
    }

    public void setShadowSize(int i) {
        this.mShadowSize = i;
    }

    public void setDrawShadow(boolean z) {
        this.mDrawShadow = z;
    }

    public void setInitScaleFactor(double d) {
        this.mInitScaleFactor = d;
    }

    public void setBorderSize(float f) {
        this.mBorderSize = f;
        this.mPaint.setStrokeWidth(this.mBorderSize);
    }

    public Uri getImageUri() {
        return this.mImageUri;
    }

    public void setImageUri(Context context, Uri uri) {
        unload();
        this.mImageUri = uri;
        load(context);
    }

    public int getResourceId() {
        return this.mResourceId;
    }

    public Drawable getDrawable() {
        return this.mDrawable;
    }

    public void draw(Canvas canvas) {
        draw(canvas, 1.0f);
    }

    public void draw(Canvas canvas, float f) {
        Bitmap bitmap;
        canvas.save();
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            if (!(drawable instanceof BitmapDrawable) || ((bitmap = ((BitmapDrawable) drawable).getBitmap()) != null && !bitmap.isRecycled())) {
                float f2 = ((this.mMaxX + this.mMinX) * f) / 2.0f;
                float f3 = ((this.mMaxY + this.mMinY) * f) / 2.0f;
                this.mDrawable.setBounds((int) (this.mMinX * f), (int) (this.mMinY * f), (int) (this.mMaxX * f), (int) (this.mMaxY * f));
                canvas.translate(f2, f3);
                canvas.rotate((this.mAngle * 180.0f) / 3.1415927f);
                canvas.translate(-f2, -f3);
                if (this.mDrawShadow && !this.mSticker && this.mShadowSize > 1) {
                    drawShadow(canvas, f);
                }
                this.mDrawable.draw(canvas);
                canvas.restore();
            }
        }
    }

    private void drawShadow(Canvas canvas, float f) {
        this.mGradientDrawable.setBounds((int) ((this.mMinX + ((float) this.mShadowSize)) * f), (int) ((this.mMinY + ((float) this.mShadowSize)) * f), (int) ((this.mMaxX + ((float) this.mShadowSize)) * f), (int) (f * (this.mMaxY + ((float) this.mShadowSize))));
        this.mGradientDrawable.setCornerRadius(5.0f);
        this.mGradientDrawable.draw(canvas);
    }

    public boolean isNull() {
        return this.mImageUri == null && this.mResourceId <= 0;
    }

    public void unload() {
        Bitmap bitmap;
        Drawable drawable = this.mDrawable;
        if ((drawable instanceof BitmapDrawable) && drawable != null && (bitmap = ((BitmapDrawable) drawable).getBitmap()) != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        this.mDrawable = null;
    }

    public void load(Context context) {
        getMetrics(context.getResources());
        if (this.mDrawable == null) {
            this.mDrawable = createDrawableFromPrimaryInfo(context);
        }
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            this.mWidth = drawable.getIntrinsicWidth();
            this.mHeight = this.mDrawable.getIntrinsicHeight();
            setPos(this.mCenterX, this.mCenterY, this.mScaleX, this.mScaleY, this.mAngle);
        } else if (this.mImageUri != null) {
            resetPrimaryInfo();
        }
    }

    public void load(Context context, float f, float f2, float f3) {
        float f4;
        float f5;
        float f6;
        float f7;
        getMetrics(context.getResources());
        this.mStartMidX = f;
        this.mStartMidY = f2;
        if (this.mDrawable == null) {
            this.mDrawable = createDrawableFromPrimaryInfo(context);
        }
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            this.mWidth = drawable.getIntrinsicWidth();
            this.mHeight = this.mDrawable.getIntrinsicHeight();
            if (this.mFirstLoad) {
                double min = ((float) Math.min(this.mDisplayWidth, this.mDisplayHeight)) / ((float) Math.max(this.mWidth, this.mHeight));
                double d = this.mInitScaleFactor;
                Double.isNaN(min);
                this.mAngle = f3;
                this.mFirstLoad = false;
                f5 = (float) (min * d);
                f4 = f5;
                f7 = f;
                f6 = f2;
            } else {
                float f8 = this.mCenterX;
                float f9 = this.mCenterY;
                f5 = this.mScaleX;
                f7 = f8;
                f6 = f9;
                f4 = this.mScaleY;
            }
            setPos(f7, f6, f5, f4, this.mAngle);
        } else if (this.mImageUri != null) {
            resetPrimaryInfo();
        }
    }

    public void load(Context context, float f, float f2) {
        load(context, f, f2, 0.0f);
    }

    public Drawable createDrawableFromPrimaryInfo(Context context) {
        Resources resources = context.getResources();
        Uri uri = this.mImageUri;
        if (uri != null) {
            return ImageDecoder.decodeUriToDrawable(context, uri);
        }
        int i = this.mResourceId;
        if (i > 0) {
            return resources.getDrawable(i);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void resetPrimaryInfo() {
        this.mImageUri = null;
        this.mResourceId = -1;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeDouble(this.mInitScaleFactor);
        parcel.writeParcelable(this.mImageUri, i);
        parcel.writeInt(this.mResourceId);
        parcel.writeBooleanArray(new boolean[]{this.mDrawImageBorder, this.mSticker});
        parcel.writeInt(this.mBorderColor);
        parcel.writeFloat(this.mBorderSize);
        parcel.writeParcelable(this.mBoundRect, i);
    }

    public void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.mInitScaleFactor = parcel.readDouble();
        this.mImageUri = parcel.readParcelable(Uri.class.getClassLoader());
        this.mResourceId = parcel.readInt();
        boolean[] zArr = new boolean[2];
        parcel.readBooleanArray(zArr);
        this.mDrawImageBorder = zArr[0];
        this.mSticker = zArr[1];
        this.mBorderColor = parcel.readInt();
        this.mBorderSize = parcel.readFloat();
        this.mBoundRect = parcel.readParcelable(RectF.class.getClassLoader());
    }

    protected ImageEntity(Parcel parcel) {
        readFromParcel(parcel);
    }
}