package com.eptonic.photocollage.ui.view.template;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.eptonic.photocollage.util.ImageUtils;
import com.eptonic.photocollage.util.PhotoUtils;
import com.eptonic.photocollage.util.ResultContainer;
import com.yd.photoeditor.vv.ImageDecoder;

@SuppressLint("AppCompatCustomView")
public class ItemImageView extends ImageView {
    private static final String TAG = ItemImageView.class.getSimpleName();
    private boolean mEnableTouch = true;
    private final GestureDetector mGestureDetector;
    private Bitmap mImage;
    private final Matrix mImageMatrix;
    private Bitmap mMaskImage;
    private final Matrix mMaskMatrix;
    public OnImageClickListener mOnImageClickListener;
    private RelativeLayout.LayoutParams mOriginalLayoutParams;
    private float mOutputScale = 1.0f;
    private final Paint mPaint;
    private final PhotoItem mPhotoItem;
    private final Matrix mScaleMaskMatrix;
    private final Matrix mScaleMatrix;
    private MultiTouchHandler mTouchHandler;
    private float mViewHeight;
    private float mViewWidth;

    public interface OnImageClickListener {
        void onDoubleClickImage(ItemImageView itemImageView);

        void onLongClickImage(ItemImageView itemImageView);
    }

    public ItemImageView(Context context, PhotoItem photoItem) {
        super(context);
        this.mPhotoItem = photoItem;
        if (photoItem.imagePath != null && photoItem.imagePath.length() > 0) {
            mImage = ResultContainer.getInstance().getImage(photoItem.imagePath);
            Bitmap bitmap = mImage;
            if (bitmap == null || bitmap.isRecycled()) {
                mImage = ImageDecoder.decodeFileToBitmap(photoItem.imagePath);
                ResultContainer.getInstance().putImage(photoItem.imagePath, this.mImage);

            } else {
            }
        }
        if (photoItem.maskPath != null && photoItem.maskPath.length() > 0) {
            mMaskImage = ResultContainer.getInstance().getImage(photoItem.maskPath);
            Bitmap bitmap2 = mMaskImage;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                this.mMaskImage = PhotoUtils.decodePNGImage(context, photoItem.maskPath);
                ResultContainer.getInstance().putImage(photoItem.maskPath, this.mMaskImage);
                Log.d(TAG, "create ItemImageView, decode mask image");
            } else {
                Log.d(TAG, "create ItemImageView, use decoded mask image");
            }
        }
        mPaint = new Paint();
        mPaint.setFilterBitmap(true);
        mPaint.setAntiAlias(true);
        setScaleType(ImageView.ScaleType.MATRIX);
        setLayerType(View.LAYER_TYPE_HARDWARE, this.mPaint);
        mImageMatrix = new Matrix();
        mScaleMatrix = new Matrix();
        mMaskMatrix = new Matrix();
        mScaleMaskMatrix = new Matrix();
        mGestureDetector = new GestureDetector(getContext(), new GDListener());
    }

    class GDListener extends GestureDetector.SimpleOnGestureListener {
        GDListener() {
        }

        public void onLongPress(MotionEvent motionEvent) {
            if (mOnImageClickListener != null) {
                mOnImageClickListener.onLongClickImage(ItemImageView.this);
            }
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (mOnImageClickListener == null) {
                return true;
            }
            mOnImageClickListener.onDoubleClickImage(ItemImageView.this);
            return true;
        }
    }

    public void swapImage(ItemImageView itemImageView) {
        Bitmap image = itemImageView.getImage();
        itemImageView.setImage(this.mImage);
        this.mImage = image;
        String str = itemImageView.getPhotoItem().imagePath;
        itemImageView.getPhotoItem().imagePath = this.mPhotoItem.imagePath;
        this.mPhotoItem.imagePath = str;
        resetImageMatrix();
        itemImageView.resetImageMatrix();
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.mOnImageClickListener = onImageClickListener;
    }

    public void setOriginalLayoutParams(RelativeLayout.LayoutParams layoutParams) {
        this.mOriginalLayoutParams = new RelativeLayout.LayoutParams(layoutParams.width, layoutParams.height);
        this.mOriginalLayoutParams.leftMargin = layoutParams.leftMargin;
        this.mOriginalLayoutParams.topMargin = layoutParams.topMargin;
    }

    public RelativeLayout.LayoutParams getOriginalLayoutParams() {
        RelativeLayout.LayoutParams layoutParams = this.mOriginalLayoutParams;
        if (layoutParams == null) {
            return (RelativeLayout.LayoutParams) getLayoutParams();
        }
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(layoutParams.width, this.mOriginalLayoutParams.height);
        layoutParams2.leftMargin = this.mOriginalLayoutParams.leftMargin;
        layoutParams2.topMargin = this.mOriginalLayoutParams.topMargin;
        return layoutParams2;
    }

    public void setImage(Bitmap bitmap) {
        this.mImage = bitmap;
    }

    public PhotoItem getPhotoItem() {
        return this.mPhotoItem;
    }

    public Bitmap getImage() {
        return this.mImage;
    }

    public Bitmap getMaskImage() {
        return this.mMaskImage;
    }

    public Matrix getImageMatrix() {
        return this.mImageMatrix;
    }

    public Matrix getMaskMatrix() {
        return this.mMaskMatrix;
    }

    public Matrix getScaleMatrix() {
        return this.mScaleMatrix;
    }

    public Matrix getScaleMaskMatrix() {
        return this.mScaleMaskMatrix;
    }

    public float getViewWidth() {
        return this.mViewWidth;
    }

    public float getViewHeight() {
        return this.mViewHeight;
    }

    public void init(float f, float f2, float f3) {
        mViewWidth = f;
        mViewHeight = f2;
        mOutputScale = f3;
        Bitmap bitmap = mImage;
        if (bitmap != null) {
            mImageMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(f, f2, (float) bitmap.getWidth(), (float) this.mImage.getHeight()));
            mScaleMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(f3 * f, f3 * f2, (float) this.mImage.getWidth(), (float) this.mImage.getHeight()));
        }
        Bitmap bitmap2 = mMaskImage;
        if (bitmap2 != null) {
            mMaskMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(f, f2, (float) bitmap2.getWidth(), (float) this.mMaskImage.getHeight()));
            mScaleMaskMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(f * f3, f2 * f3, (float) this.mMaskImage.getWidth(), (float) this.mMaskImage.getHeight()));
        }
        this.mTouchHandler = new MultiTouchHandler();
        this.mTouchHandler.setMatrices(this.mImageMatrix, this.mScaleMatrix);
        this.mTouchHandler.setScale(f3);
        this.mTouchHandler.setEnableRotation(true);
        invalidate();
    }

    public void setImagePath(String str) {
        this.mPhotoItem.imagePath = str;
        recycleMainImage();
        this.mImage = ImageDecoder.decodeFileToBitmap(str);
        this.mImageMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(this.mViewWidth, this.mViewHeight, (float) this.mImage.getWidth(), (float) this.mImage.getHeight()));
        Matrix matrix = this.mScaleMatrix;
        float f = this.mOutputScale;
        matrix.set(ImageUtils.createMatrixToDrawImageInCenterView(this.mViewWidth * f, f * this.mViewHeight, (float) this.mImage.getWidth(), (float) this.mImage.getHeight()));
        this.mTouchHandler.setMatrices(this.mImageMatrix, this.mScaleMatrix);
        invalidate();
        ResultContainer.getInstance().putImage(this.mPhotoItem.imagePath, this.mImage);
    }

    public void resetImageMatrix() {
        this.mImageMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(this.mViewWidth, this.mViewHeight, (float) this.mImage.getWidth(), (float) this.mImage.getHeight()));
        Matrix matrix = this.mScaleMatrix;
        float f = this.mOutputScale;
        matrix.set(ImageUtils.createMatrixToDrawImageInCenterView(this.mViewWidth * f, f * this.mViewHeight, (float) this.mImage.getWidth(), (float) this.mImage.getHeight()));
        this.mTouchHandler.setMatrices(this.mImageMatrix, this.mScaleMatrix);
        invalidate();
    }

    public void clearMainImage() {
        this.mPhotoItem.imagePath = null;
        recycleMainImage();
        invalidate();
    }

    private void recycleMainImage() {
        Bitmap bitmap = this.mImage;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mImage.recycle();
            this.mImage = null;
            System.gc();
        }
    }

    private void recycleMaskImage() {
        Bitmap bitmap = this.mMaskImage;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mMaskImage.recycle();
            this.mMaskImage = null;
            System.gc();
        }
    }

    public void recycleImages(boolean z) {
        String str = TAG;
        Log.d(str, "recycleImages, recycleMainImage=" + z);
        if (z) {
            recycleMainImage();
        }
        recycleMaskImage();
    }

    public void onDraw(Canvas canvas) {
        Bitmap bitmap;
        super.onDraw(canvas);
        Bitmap bitmap2 = mImage;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = mMaskImage) != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(mImage, mImageMatrix, mPaint);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(mMaskImage, mMaskMatrix, mPaint);
            mPaint.setXfermode(null);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Bitmap bitmap;
        if (!mEnableTouch) {
            return super.onTouchEvent(motionEvent);
        }
        mGestureDetector.onTouchEvent(motionEvent);
        if (!(mTouchHandler == null || (bitmap = mImage) == null || bitmap.isRecycled())) {
            mTouchHandler.touch(motionEvent);
            mImageMatrix.set(mTouchHandler.getMatrix());
            mScaleMatrix.set(mTouchHandler.getScaleMatrix());
            invalidate();
        }
        return true;
    }

    public void setEnableTouch(boolean z) {
        mEnableTouch = z;
    }
}
