package com.eptonic.photocollage.ui.view.frame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.eptonic.photocollage.ui.view.template.MultiTouchHandler;
import com.eptonic.photocollage.ui.view.template.PhotoItem;
import com.eptonic.photocollage.util.GeometryUtils;
import com.eptonic.photocollage.util.ImageUtils;
import com.eptonic.photocollage.util.ResultContainer;
import com.yd.photoeditor.vv.ImageDecoder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FrameImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final String TAG = FrameImageView.class.getSimpleName();
    private int mBackgroundColor = -1;
    private final Path mBackgroundPath = new Path();
    private final Path mClearPath = new Path();
    private final List<PointF> mConvertedClearPoints = new ArrayList();
    private final List<PointF> mConvertedPoints = new ArrayList();
    private float mCorner = 0.0f;
    private boolean mEnableTouch = true;
    private final GestureDetector mGestureDetector;
    private Bitmap mImage;
    private final Matrix mImageMatrix;
    /* access modifiers changed from: private */
    public OnImageClickListener mOnImageClickListener;
    private RelativeLayout.LayoutParams mOriginalLayoutParams;
    private float mOutputScale = 1.0f;
    private final Paint mPaint;
    private final Path mPath = new Path();
    private final Rect mPathRect = new Rect(0, 0, 0, 0);
    private final PhotoItem mPhotoItem;
    private final List<PointF> mPolygon = new ArrayList();
    private final Matrix mScaleMatrix;
    private boolean mSelected = true;
    private float mSpace = 0.0f;
    private MultiTouchHandler mTouchHandler;
    private float mViewHeight;
    private float mViewWidth;

    public interface OnImageClickListener {
        void onDoubleClickImage(FrameImageView frameImageView);

        void onLongClickImage(FrameImageView frameImageView);
    }

    @SuppressLint("NewApi")
    public FrameImageView(final Context context, PhotoItem photoItem) {
        super(context);
        this.mPhotoItem = photoItem;
        if (photoItem.imagePath != null && photoItem.imagePath.length() > 0) {
            this.mImage = ResultContainer.getInstance().getImage(photoItem.imagePath);
            Bitmap bitmap = this.mImage;
            if (bitmap == null || bitmap.isRecycled()) {
                try {
//                    mImage = ImageDecoder.decodeBitmap(ImageDecoder.createSource(new File(photoItem.imagePath)));
                    mImage = ImageDecoder.decodeFileToBitmap(photoItem.imagePath);
                } catch (OutOfMemoryError | Exception o) {

                }
                ResultContainer.getInstance().putImage(photoItem.imagePath, this.mImage);
                Log.d(TAG, "create FrameImageView, decode image");
            } else {
                Log.d(TAG, "create FrameImageView, use decoded image");
            }
        }
        this.mPaint = new Paint();
        this.mPaint.setFilterBitmap(true);
        this.mPaint.setAntiAlias(true);
        setScaleType(ScaleType.MATRIX);
        setLayerType(View.LAYER_TYPE_SOFTWARE, this.mPaint);
        this.mImageMatrix = new Matrix();
        this.mScaleMatrix = new Matrix();
        this.mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            public void onLongPress(MotionEvent motionEvent) {
                if (FrameImageView.this.mOnImageClickListener != null) {
                    FrameImageView.this.mOnImageClickListener.onLongClickImage(FrameImageView.this);
                }
            }

            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (FrameImageView.this.mOnImageClickListener == null) {
                    return true;
                }
                FrameImageView.this.mOnImageClickListener.onDoubleClickImage(FrameImageView.this);
                return true;
            }
        });
    }

    public void saveInstanceState(Bundle bundle) {
        int i = this.mPhotoItem.index;
        float[] fArr = new float[9];
        this.mImageMatrix.getValues(fArr);
        bundle.putFloatArray("mImageMatrix_" + i, fArr);
        float[] fArr2 = new float[9];
        this.mScaleMatrix.getValues(fArr2);
        bundle.putFloatArray("mScaleMatrix_" + i, fArr2);
        bundle.putFloat("mViewWidth_" + i, this.mViewWidth);
        bundle.putFloat("mViewHeight_" + i, this.mViewHeight);
        bundle.putFloat("mOutputScale_" + i, this.mOutputScale);
        bundle.putFloat("mCorner_" + i, this.mCorner);
        bundle.putFloat("mSpace_" + i, this.mSpace);
        bundle.putInt("mBackgroundColor_" + i, this.mBackgroundColor);
    }

    public void restoreInstanceState(Bundle bundle) {
        int i = this.mPhotoItem.index;
        float[] floatArray = bundle.getFloatArray("mImageMatrix_" + i);
        if (floatArray != null) {
            this.mImageMatrix.setValues(floatArray);
        }
        float[] floatArray2 = bundle.getFloatArray("mScaleMatrix_" + i);
        if (floatArray2 != null) {
            this.mScaleMatrix.setValues(floatArray2);
        }
        this.mViewWidth = bundle.getFloat("mViewWidth_" + i, 1.0f);
        this.mViewHeight = bundle.getFloat("mViewHeight_" + i, 1.0f);
        this.mOutputScale = bundle.getFloat("mOutputScale_" + i, 1.0f);
        this.mCorner = bundle.getFloat("mCorner_" + i, 0.0f);
        this.mSpace = bundle.getFloat("mSpace_" + i, 0.0f);
        this.mBackgroundColor = bundle.getInt("mBackgroundColor_" + i, -1);
        this.mTouchHandler.setMatrices(this.mImageMatrix, this.mScaleMatrix);
        this.mTouchHandler.setScale(this.mOutputScale);
        setSpace(this.mSpace, this.mCorner);
    }

    public void swapImage(FrameImageView frameImageView) {
        if (this.mImage != null && frameImageView.getImage() != null) {
            Bitmap image = frameImageView.getImage();
            frameImageView.setImage(this.mImage);
            this.mImage = image;
            String str = frameImageView.getPhotoItem().imagePath;
            frameImageView.getPhotoItem().imagePath = this.mPhotoItem.imagePath;
            this.mPhotoItem.imagePath = str;
            resetImageMatrix();
            frameImageView.resetImageMatrix();
        }
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

    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        invalidate();
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

    public Matrix getImageMatrix() {
        return this.mImageMatrix;
    }

    public float getViewWidth() {
        return this.mViewWidth;
    }

    public float getViewHeight() {
        return this.mViewHeight;
    }

    public void init(float f, float f2, float f3, float f4, float f5) {
        this.mViewWidth = f;
        this.mViewHeight = f2;
        this.mOutputScale = f3;
        this.mSpace = f4;
        this.mCorner = f5;
        Bitmap bitmap = this.mImage;
        if (bitmap != null) {
            this.mImageMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(f, f2, (float) bitmap.getWidth(), (float) this.mImage.getHeight()));
            this.mScaleMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(f * f3, f2 * f3, (float) this.mImage.getWidth(), (float) this.mImage.getHeight()));
        }
        this.mTouchHandler = new MultiTouchHandler();
        this.mTouchHandler.setMatrices(this.mImageMatrix, this.mScaleMatrix);
        this.mTouchHandler.setScale(f3);
        this.mTouchHandler.setEnableRotation(true);
        setSpace(this.mSpace, this.mCorner);
    }

    public void init(float f, float f2, float f3) {
        init(f, f2, f3, 0.0f, 0.0f);
    }

    public float getSpace() {
        return this.mSpace;
    }

    public float getCorner() {
        return this.mCorner;
    }

    public void setSpace(float f, float f2) {
        this.mSpace = f;
        this.mCorner = f2;
        setSpace(this.mViewWidth, this.mViewHeight, this.mPhotoItem, this.mConvertedPoints, this.mConvertedClearPoints, this.mPath, this.mClearPath, this.mBackgroundPath, this.mPolygon, this.mPathRect, f, f2);
        invalidate();
    }

    private static void setSpace(float f, float f2, PhotoItem photoItem, List<PointF> list, List<PointF> list2, Path path, Path path2, Path path3, List<PointF> list3, Rect rect, float f3, float f4) {
        List<PointF> list4;
        float f5 = f;
        float f6 = f2;
        PhotoItem photoItem2 = photoItem;
        List<PointF> list5 = list;
        List<PointF> list6 = list2;
        Path path4 = path2;
        float f7 = f3;
        float f8 = f4;
        if (photoItem2.pointList != null && list.isEmpty()) {
            Iterator<PointF> it = photoItem2.pointList.iterator();
            while (it.hasNext()) {
                PointF next = it.next();
                PointF pointF = new PointF(next.x * f5, next.y * f6);
                list5.add(pointF);
                if (photoItem2.shrinkMap != null) {
                    photoItem2.shrinkMap.put(pointF, photoItem2.shrinkMap.get(next));
                }
            }
        }
        if (photoItem2.clearAreaPoints != null && photoItem2.clearAreaPoints.size() > 0) {
            path2.reset();
            if (list2.isEmpty()) {
                Iterator<PointF> it2 = photoItem2.clearAreaPoints.iterator();
                while (it2.hasNext()) {
                    PointF next2 = it2.next();
                    list6.add(new PointF(next2.x * f5, next2.y * f6));
                }
            }
            GeometryUtils.createPathWithCircleCorner(path4, list6, f8);
        } else if (photoItem2.clearPath != null) {
            path2.reset();
            buildRealClearPath(f, f2, photoItem, path4, f8);
        }
        if (photoItem2.path != null) {
            buildRealPath(f, f2, photoItem, path, f3, f4);
            list3.clear();
        } else {
            if (photoItem2.shrinkMethod == 1) {
                list4 = GeometryUtils.shrinkPathCollage_3_3(list5, findCenterPointIndex(photoItem), f7, photoItem2.bound);
            } else if (photoItem2.shrinkMethod == 2 && photoItem2.shrinkMap != null) {
                list4 = GeometryUtils.shrinkPathCollageUsingMap(list5, f7, photoItem2.shrinkMap);
            } else if (photoItem2.shrinkMethod == 5 && photoItem2.shrinkMap != null) {
                list4 = GeometryUtils.commonShrinkPath(list5, f7, photoItem2.shrinkMap);
            } else if (photoItem2.disableShrink) {
                list4 = GeometryUtils.shrinkPath(list5, 0.0f, photoItem2.bound);
            } else {
                list4 = GeometryUtils.shrinkPath(list5, f7, photoItem2.bound);
            }
            list3.clear();
            list3.addAll(list4);
            GeometryUtils.createPathWithCircleCorner(path, list4, f8);
            if (photoItem2.hasBackground) {
                path3.reset();
                GeometryUtils.createPathWithCircleCorner(path3, list5, f8);
            }
        }
        rect.set(0, 0, 0, 0);
    }

    private static int findCenterPointIndex(PhotoItem photoItem) {
        int i;
        int i2 = 0;
        if (photoItem.bound.left == 0.0f && photoItem.bound.top == 0.0f) {
            i = 0;
            float f = 1.0f;
            while (i2 < photoItem.pointList.size()) {
                PointF pointF = photoItem.pointList.get(i2);
                if (pointF.x > 0.0f && pointF.x < 1.0f && pointF.y > 0.0f && pointF.y < 1.0f && pointF.x < f) {
                    f = pointF.x;
                    i = i2;
                }
                i2++;
            }
        } else {
            i = 0;
            float f2 = 0.0f;
            while (i2 < photoItem.pointList.size()) {
                PointF pointF2 = photoItem.pointList.get(i2);
                if (pointF2.x > 0.0f && pointF2.x < 1.0f && pointF2.y > 0.0f && pointF2.y < 1.0f && pointF2.x > f2) {
                    f2 = pointF2.x;
                    i = i2;
                }
                i2++;
            }
        }
        return i;
    }

    private static void buildRealPath(float f, float f2, PhotoItem photoItem, Path path, float f3, float f4) {
        float f5;
        float f6;
        float f7;
        float f8;
        if (photoItem.path != null) {
            RectF rectF = new RectF();
            photoItem.path.computeBounds(rectF, true);
            float width = rectF.width();
            float height = rectF.height();
            float f9 = f3 * 2.0f;
            path.set(photoItem.path);
            Matrix matrix = new Matrix();
            if (photoItem.fitBound) {
                float f10 = f9 * 2.0f;
                f5 = (photoItem.pathScaleRatio * ((photoItem.pathRatioBound.width() * f) - f10)) / width;
                f6 = (photoItem.pathScaleRatio * ((photoItem.pathRatioBound.height() * f2) - f10)) / height;
            } else {
                float f11 = f9 * 2.0f;
                f5 = Math.min((photoItem.pathScaleRatio * (f2 - f11)) / height, (photoItem.pathScaleRatio * (f - f11)) / width);
                f6 = f5;
            }
            matrix.postScale(f5, f6);
            path.transform(matrix);
            RectF rectF2 = new RectF();
            if (photoItem.cornerMethod == 1) {
                path.computeBounds(rectF2, true);
                GeometryUtils.createRegularPolygonPath(path, Math.min(rectF2.width(), rectF2.height()), 6, f4);
                path.computeBounds(rectF2, true);
            } else if (photoItem.cornerMethod == 2) {
                path.computeBounds(rectF2, true);
                GeometryUtils.createRectanglePath(path, rectF2.width(), rectF2.height(), f4);
                path.computeBounds(rectF2, true);
            } else {
                path.computeBounds(rectF2, true);
            }
            if (photoItem.shrinkMethod == 3 || photoItem.shrinkMethod == 4) {
                matrix.reset();
                matrix.postTranslate((f / 2.0f) - (rectF2.width() / 2.0f), (f2 / 2.0f) - (rectF2.height() / 2.0f));
                path.transform(matrix);
                return;
            }
            if (photoItem.pathAlignParentRight) {
                f7 = ((photoItem.pathRatioBound.right * f) - rectF2.width()) - (f9 / f5);
                f8 = photoItem.pathRatioBound.top;
            } else {
                f7 = (photoItem.pathRatioBound.left * f) + (f9 / f5);
                f8 = photoItem.pathRatioBound.top;
            }
            float f12 = (f8 * f2) + (f9 / f6);
            if (photoItem.pathInCenterHorizontal) {
                f7 = (f / 2.0f) - (rectF2.width() / 2.0f);
            }
            if (photoItem.pathInCenterVertical) {
                f12 = (f2 / 2.0f) - (rectF2.height() / 2.0f);
            }
            matrix.reset();
            matrix.postTranslate(f7, f12);
            path.transform(matrix);
        }
    }

    private static Path buildRealClearPath(float f, float f2, PhotoItem photoItem, Path path, float f3) {
        float f4;
        float f5;
        float f6 = 0;
        float f7;
        float f8;
        float height;
        if (photoItem.clearPath == null) {
            return null;
        }
        RectF rectF = new RectF();
        photoItem.clearPath.computeBounds(rectF, true);
        float width = rectF.width();
        float height2 = rectF.height();
        path.set(photoItem.clearPath);
        Matrix matrix = new Matrix();
        if (photoItem.fitBound) {
            f4 = ((photoItem.clearPathScaleRatio * f) * photoItem.clearPathRatioBound.width()) / width;
            f5 = ((photoItem.clearPathScaleRatio * f2) * photoItem.clearPathRatioBound.height()) / height2;
        } else {
            f4 = Math.min((photoItem.clearPathScaleRatio * f2) / height2, (photoItem.clearPathScaleRatio * f) / width);
            f5 = f4;
        }
        matrix.postScale(f4, f5);
        path.transform(matrix);
        RectF rectF2 = new RectF();
        if (photoItem.cornerMethod == 1) {
            path.computeBounds(rectF2, true);
            GeometryUtils.createRegularPolygonPath(path, Math.min(rectF2.width(), rectF2.height()), 6, f3);
            path.computeBounds(rectF2, true);
        } else if (photoItem.cornerMethod == 2) {
            path.computeBounds(rectF2, true);
            GeometryUtils.createRectanglePath(path, rectF2.width(), rectF2.height(), f3);
            path.computeBounds(rectF2, true);
        } else {
            path.computeBounds(rectF2, true);
        }
        if (photoItem.shrinkMethod == 3) {
            if (photoItem.clearPathRatioBound.left > 0.0f) {
                f7 = f - (rectF2.width() / 2.0f);
            } else {
                f7 = (-rectF2.width()) / 2.0f;
            }
            f8 = f2 / 2.0f;
            height = rectF2.height();
        } else {
            if (photoItem.centerInClearBound) {
                f7 = ((f / 2.0f) - (rectF2.width() / 2.0f)) + (photoItem.clearPathRatioBound.left * f);
                f6 = ((f2 / 2.0f) - (rectF2.height() / 2.0f)) + (photoItem.clearPathRatioBound.top * f2);
            } else {
                float f9 = photoItem.clearPathRatioBound.left * f;
                float f10 = photoItem.clearPathRatioBound.top * f2;
                f7 = photoItem.clearPathInCenterHorizontal ? (f / 2.0f) - (rectF2.width() / 2.0f) : f9;
                if (photoItem.clearPathInCenterVertical) {
                    f8 = f2 / 2.0f;
                    height = rectF2.height();
                } else {
                    f6 = f10;
                }
            }
            matrix.reset();
            matrix.postTranslate(f7, f6);
            path.transform(matrix);
            return path;
        }
        f6 = f8 - (height / 2.0f);
        matrix.reset();
        matrix.postTranslate(f7, f6);
        path.transform(matrix);
        return path;
    }

    @SuppressLint("NewApi")
    public void setImagePath(String str) {
        this.mPhotoItem.imagePath = str;
        recycleImage();
        try {
            //this.mImage = ImageDecoder.decodeBitmap(ImageDecoder.createSource(new File(str)));
            mImage = ImageDecoder.decodeFileToBitmap(str);
            this.mImageMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(this.mViewWidth, this.mViewHeight, (float) this.mImage.getWidth(), (float) this.mImage.getHeight()));
            this.mScaleMatrix.set(ImageUtils.createMatrixToDrawImageInCenterView(this.mOutputScale * this.mViewWidth, this.mOutputScale * this.mViewHeight, (float) this.mImage.getWidth(), (float) this.mImage.getHeight()));
            this.mTouchHandler.setMatrices(this.mImageMatrix, this.mScaleMatrix);
            invalidate();
            ResultContainer.getInstance().putImage(this.mPhotoItem.imagePath, this.mImage);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
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
        recycleImage();
        invalidate();
    }

    public void recycleImage() {
        Bitmap bitmap = this.mImage;
        if (bitmap != null) {
            bitmap.recycle();
            this.mImage = null;
            System.gc();
        }
    }

    public PointF getCenterPolygon() {
        List<PointF> list = this.mPolygon;
        if (list == null || list.size() <= 0) {
            return null;
        }
        PointF pointF = new PointF();
        for (PointF next : this.mPolygon) {
            pointF.x += next.x;
            pointF.y += next.y;
        }
        pointF.x /= (float) this.mPolygon.size();
        pointF.y /= (float) this.mPolygon.size();
        return pointF;
    }

    private void drawCenterLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(5.0f);
        paint.setColor(-16777216);
        paint.setStyle(Paint.Style.STROKE);
        float f = this.mViewHeight;
        float f2 = f / 2.0f;
        Canvas canvas2 = canvas;
        Paint paint2 = paint;
        canvas2.drawLine(0.0f, f / 2.0f, this.mViewWidth, f2, paint2);
        canvas2.drawRect(0.0f, 0.0f, this.mViewWidth, this.mViewHeight, paint2);
    }

    public boolean isSelected(float f, float f2) {
        boolean contains = GeometryUtils.contains(this.mPolygon, new PointF(f, f2));
        String str = TAG;
        Log.d(str, "isSelected, x=" + f + ", y=" + f2 + ", result=" + contains);
        return contains;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawImage(canvas, this.mPath, this.mPaint, this.mPathRect, this.mImage, this.mImageMatrix, (float) getWidth(), (float) getHeight(), this.mBackgroundColor, this.mBackgroundPath, this.mClearPath, this.mPolygon);
    }

    public void drawOutputImage(Canvas canvas) {
        float f = this.mViewWidth;
        float f2 = this.mOutputScale;
        float f3 = f * f2;
        float f4 = f2 * this.mViewHeight;
        Path path = new Path();
        Path path2 = new Path();
        Path path3 = new Path();
        Rect rect = new Rect();
        ArrayList arrayList = new ArrayList();
        PhotoItem photoItem = this.mPhotoItem;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        float f5 = this.mSpace;
        float f6 = this.mOutputScale;
        setSpace(f3, f4, photoItem, arrayList2, arrayList3, path, path2, path3, arrayList, rect, f5 * f6, this.mCorner * f6);
        drawImage(canvas, path, this.mPaint, rect, this.mImage, this.mScaleMatrix, f3, f4, this.mBackgroundColor, path3, path2, arrayList);
    }

    private static void drawImage(Canvas canvas, Path path, Paint paint, Rect rect, Bitmap bitmap, Matrix matrix, float f, float f2, int i, Path path2, Path path3, List<PointF> list) {
        Canvas canvas2 = canvas;
        Path path4 = path;
        Paint paint2 = paint;
        Rect rect2 = rect;
        Bitmap bitmap2 = bitmap;
        Path path5 = path2;
        Path path6 = path3;
        List<PointF> list2 = list;
        if (bitmap2 != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap2, matrix, paint2);
        }
        if (rect2.left == rect2.right) {
            canvas.save();
            canvas.clipPath(path);
            rect2.set(canvas.getClipBounds());
            canvas.restore();
        }
        canvas.save();
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawARGB(0, 0, 0, 0);
        paint2.setColor(-16777216);
        paint2.setStyle(Paint.Style.FILL);
        Paint paint3 = paint;
        canvas.drawRect(0.0f, 0.0f, f, (float) rect2.top, paint3);
        float f3 = f2;
        canvas.drawRect(0.0f, 0.0f, (float) rect2.left, f3, paint3);
        float f4 = f;
        canvas.drawRect((float) rect2.right, 0.0f, f4, f3, paint3);
        canvas.drawRect(0.0f, (float) rect2.bottom, f4, f3, paint3);
        paint2.setXfermode(null);
        canvas.restore();
        canvas.save();
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawARGB(0, 0, 0, 0);
        paint2.setColor(-16777216);
        paint2.setStyle(Paint.Style.FILL);
        Path.FillType fillType = path.getFillType();
        path4.setFillType(Path.FillType.INVERSE_WINDING);
        canvas.drawPath(path, paint);
        paint2.setXfermode(null);
        canvas.restore();
        path4.setFillType(fillType);
        if (path6 != null) {
            canvas.save();
            paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawARGB(0, 0, 0, 0);
            paint2.setColor(-16777216);
            paint2.setStyle(Paint.Style.FILL);
            canvas.drawPath(path6, paint2);
            paint2.setXfermode(null);
            canvas.restore();
        }
        if (path5 != null) {
            canvas.save();
            paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
            canvas.drawARGB(0, 0, 0, 0);
            paint2.setColor(i);
            paint2.setStyle(Paint.Style.FILL);
            canvas.drawPath(path5, paint2);
            paint2.setXfermode(null);
            canvas.restore();
        }
        if (list2 != null && list.isEmpty()) {
            list2.add(new PointF((float) rect2.left, (float) rect2.top));
            list2.add(new PointF((float) rect2.right, (float) rect2.top));
            list2.add(new PointF((float) rect2.right, (float) rect2.bottom));
            list2.add(new PointF((float) rect2.left, (float) rect2.bottom));
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Bitmap bitmap;
        if (!this.mEnableTouch) {
            return super.onTouchEvent(motionEvent);
        }
        if (motionEvent.getAction() == 0) {
            this.mSelected = GeometryUtils.contains(this.mPolygon, new PointF(motionEvent.getX(), motionEvent.getY()));
        }
        if (!this.mSelected) {
            return super.onTouchEvent(motionEvent);
        }
        if (motionEvent.getAction() == 1) {
            this.mSelected = false;
        }
        this.mGestureDetector.onTouchEvent(motionEvent);
        if (!(this.mTouchHandler == null || (bitmap = this.mImage) == null || bitmap.isRecycled())) {
            this.mTouchHandler.touch(motionEvent);
            this.mImageMatrix.set(this.mTouchHandler.getMatrix());
            this.mScaleMatrix.set(this.mTouchHandler.getScaleMatrix());
            invalidate();
        }
        return true;
    }

    public void setEnableTouch(boolean z) {
        this.mEnableTouch = z;
    }
}
