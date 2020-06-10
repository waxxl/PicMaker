package com.yd.photoeditor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.yd.photoeditor.R;
import java.util.ArrayList;
import java.util.List;

public class CaptionImageView extends ImageView {
    public static final String BASE_FONT_ASSET_FOLDER = "fonts/";
    public static final int MAX_CHARACTER_COUNT = 128;
    public static final int MAX_TEXT_LINES = 3;
    private static final int MIN_TOUCH_DIST_DP = 15;
    public static final int MOVE_DOWN = 1;
    public static final int MOVE_LEFT = 2;
    public static final int MOVE_RIGHT = 3;
    public static final int MOVE_UP = 0;
    private float mBottomMarginCaption;
    private OnChangeDirectionListener mChangeDirectionListener;
    private OnDrawCaptionListener mDrawCaptionListener;
    private boolean mDrawStroke;
    private float mFirstTextSize;
    private String mFontName;
    private Typeface mFontTf;
    private boolean mIsMeme;
    private PointF mLastTouch;
    private float mLeftRightMarginCaption;
    private float mMaxTextSize = 48.0f;
    private float mMinTextSize = 12.0f;
    private int mMinTouchDist;
    private float mOriginalTextSize = 24.0f;
    private float mSecondTextSize;
    private int mStrokeColor;
    private final Paint mStrokePaint;
    private float mStrokeWidth;
    private String mText = "";
    private String mText2;
    private int mTextColor = -1;
    private final Paint mTextPaint;
    protected float mTextX;
    protected float mTextX2;
    protected float mTextY;
    protected float mTextY2;
    private float mTopMarginCaption;

    public interface OnChangeDirectionListener {
        void changeDirection(int i);

        void clickAt(float f, float f2);
    }

    public interface OnDrawCaptionListener {
        void textTooLong(int i, float f);
    }

    public CaptionImageView(Context context) {
        super(context);
        float f = this.mOriginalTextSize;
        this.mFirstTextSize = f;
        this.mSecondTextSize = f;
        this.mFontTf = Typeface.DEFAULT;
        this.mTextPaint = new Paint();
        this.mTopMarginCaption = 10.0f;
        this.mBottomMarginCaption = 10.0f;
        this.mLeftRightMarginCaption = 10.0f;
        this.mStrokeWidth = 4.0f;
        this.mMinTouchDist = 15;
        this.mLastTouch = new PointF();
        this.mStrokePaint = new Paint();
        this.mStrokeColor = -16777216;
        this.mDrawStroke = true;
        this.mIsMeme = true;
        this.mText2 = "";
        this.mTextX = 0.0f;
        this.mTextY = 0.0f;
        this.mTextX2 = 0.0f;
        this.mTextY2 = 20.0f;
        this.mFontName = "";
        init();
    }

    public CaptionImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        float f = this.mOriginalTextSize;
        this.mFirstTextSize = f;
        this.mSecondTextSize = f;
        this.mFontTf = Typeface.DEFAULT;
        this.mTextPaint = new Paint();
        this.mTopMarginCaption = 10.0f;
        this.mBottomMarginCaption = 10.0f;
        this.mLeftRightMarginCaption = 10.0f;
        this.mStrokeWidth = 4.0f;
        this.mMinTouchDist = 15;
        this.mLastTouch = new PointF();
        this.mStrokePaint = new Paint();
        this.mStrokeColor = -16777216;
        this.mDrawStroke = true;
        this.mIsMeme = true;
        this.mText2 = "";
        this.mTextX = 0.0f;
        this.mTextY = 0.0f;
        this.mTextX2 = 0.0f;
        this.mTextY2 = 20.0f;
        this.mFontName = "";
        init();
    }

    public CaptionImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        float f = this.mOriginalTextSize;
        this.mFirstTextSize = f;
        this.mSecondTextSize = f;
        this.mFontTf = Typeface.DEFAULT;
        this.mTextPaint = new Paint();
        this.mTopMarginCaption = 10.0f;
        this.mBottomMarginCaption = 10.0f;
        this.mLeftRightMarginCaption = 10.0f;
        this.mStrokeWidth = 4.0f;
        this.mMinTouchDist = 15;
        this.mLastTouch = new PointF();
        this.mStrokePaint = new Paint();
        this.mStrokeColor = -16777216;
        this.mDrawStroke = true;
        this.mIsMeme = true;
        this.mText2 = "";
        this.mTextX = 0.0f;
        this.mTextY = 0.0f;
        this.mTextX2 = 0.0f;
        this.mTextY2 = 20.0f;
        this.mFontName = "";
        init();
    }

    public void restoreInstanceState(Bundle bundle) {
        String string2 = bundle.getString("com.yd.photoeditor.view.CaptionImageView.mFontName");
        if (string2 != null && string2.length() > 0) {
            this.mFontName = string2;
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() >= 5 && getHeight() >= 5) {
            if (!this.mIsMeme) {
                this.mFirstTextSize = drawCenterCaption(canvas, getWidth(), getHeight(), this.mLeftRightMarginCaption, this.mText);
                return;
            }
            this.mFirstTextSize = drawTopCaption(canvas, getWidth(), getHeight(), this.mTopMarginCaption, this.mLeftRightMarginCaption, this.mText);
            this.mSecondTextSize = drawBottomCaption(canvas, getWidth(), getHeight(), this.mBottomMarginCaption, this.mLeftRightMarginCaption, this.mText2);
        }
    }

    public Bitmap getTextBitmap(Bitmap bitmap, float f) {
        this.mStrokeWidth *= f;
        this.mTextPaint.setTextSize(this.mFirstTextSize * f);
        this.mStrokePaint.setTextSize(this.mFirstTextSize * f);
        this.mStrokePaint.setStrokeWidth(this.mStrokeWidth);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f2 = this.mTopMarginCaption;
        float f3 = this.mLeftRightMarginCaption * f;
        float f4 = this.mBottomMarginCaption;
        this.mMinTextSize *= f;
        this.mMaxTextSize *= f;
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mTextPaint);
        if (!this.mIsMeme) {
            drawCenterCaption(canvas, createBitmap.getWidth(), createBitmap.getHeight(), f3, this.mText);
        } else {
            Canvas canvas2 = canvas;
            float f5 = f3;
            drawTopCaption(canvas2, createBitmap.getWidth(), createBitmap.getHeight(), f2, f5, this.mText);
            this.mTextPaint.setTextSize(this.mSecondTextSize * f);
            this.mStrokePaint.setTextSize(this.mSecondTextSize * f);
            drawBottomCaption(canvas2, createBitmap.getWidth(), createBitmap.getHeight(), f4, f5, this.mText2);
        }
        return createBitmap;
    }

    private void initPaints() {
        this.mStrokePaint.setColor(this.mStrokeColor);
        this.mStrokePaint.setTextAlign(Paint.Align.LEFT);
        this.mStrokePaint.setTextSize(this.mOriginalTextSize);
        this.mStrokePaint.setTypeface(this.mFontTf);
        this.mStrokePaint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setStrokeWidth(this.mStrokeWidth);
        this.mStrokePaint.setAntiAlias(true);
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setTextAlign(Paint.Align.LEFT);
        this.mTextPaint.setTextSize(this.mOriginalTextSize);
        this.mTextPaint.setTypeface(this.mFontTf);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setShadowLayer(6.0f, 3.0f, 3.0f, -16777216);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mLastTouch.x = motionEvent.getX();
            this.mLastTouch.y = motionEvent.getY();
        } else if (action == 1) {
            PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
            float f = ((pointF.x - this.mLastTouch.x) * (pointF.x - this.mLastTouch.x)) + ((pointF.y - this.mLastTouch.y) * (pointF.y - this.mLastTouch.y));
            int i = this.mMinTouchDist;
            if (f >= ((float) (i * i))) {
                int findDirectionMovement = findDirectionMovement(this.mLastTouch, pointF);
                OnChangeDirectionListener onChangeDirectionListener = this.mChangeDirectionListener;
                if (onChangeDirectionListener != null) {
                    onChangeDirectionListener.changeDirection(findDirectionMovement);
                }
            } else {
                OnChangeDirectionListener onChangeDirectionListener2 = this.mChangeDirectionListener;
                if (onChangeDirectionListener2 != null) {
                    onChangeDirectionListener2.clickAt(motionEvent.getX(), motionEvent.getY());
                }
            }
        }
        return true;
    }

    public void loadFontFromAsset(String str) {
        this.mFontTf = Typeface.createFromAsset(getContext().getAssets(), BASE_FONT_ASSET_FOLDER.concat(str));
        this.mTextPaint.setTypeface(this.mFontTf);
        this.mStrokePaint.setTypeface(this.mFontTf);
        invalidate();
        this.mFontName = str;
    }

    public void setText(String str) {
        this.mText = str;
        invalidate();
    }

    public void setTextColor(int i) {
        this.mTextColor = i;
        this.mTextPaint.setColor(i);
        invalidate();
    }

    public void setStrokeTextColor(int i) {
        this.mStrokeColor = i;
        this.mStrokePaint.setColor(i);
        invalidate();
    }

    public void setTextSize(float f) {
        this.mOriginalTextSize = f;
        float f2 = this.mOriginalTextSize;
        this.mFirstTextSize = f2;
        this.mSecondTextSize = f2;
        this.mTextPaint.setTextSize(f);
        this.mStrokePaint.setTextSize(f);
        invalidate();
    }

    public void setFontTf(Typeface typeface) {
        this.mFontTf = typeface;
        this.mTextPaint.setTypeface(typeface);
        this.mStrokePaint.setTypeface(typeface);
        invalidate();
    }

    public void setIsMeme(boolean z) {
        this.mIsMeme = z;
        invalidate();
    }

    public boolean isMeme() {
        return this.mIsMeme;
    }

    public void setTextPosition(float f, float f2) {
        this.mTextX = f;
        this.mTextY = f2;
        invalidate();
    }

    public void setText2Position(float f, float f2) {
        this.mTextX2 = f;
        this.mTextY2 = f2;
        invalidate();
    }

    public String getText2() {
        return this.mText2;
    }

    public void clearAllTexts() {
        this.mText = "";
        this.mText2 = "";
        invalidate();
    }

    public float getTextSize() {
        return this.mOriginalTextSize;
    }

    public String getFontName() {
        return this.mFontName;
    }

    public void setAllTextPositions(float f, float f2, float f3, float f4) {
        this.mTextX = f;
        this.mTextY = f2;
        this.mTextX2 = f3;
        this.mTextY2 = f4;
        invalidate();
    }

    public void setText2(String str) {
        this.mText2 = str;
        invalidate();
    }

    public void setAllTexts(String str, String str2) {
        this.mText = str;
        this.mText2 = str2;
        invalidate();
    }

    public String getText() {
        return this.mText;
    }

    public Typeface getFontTf() {
        return this.mFontTf;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public void setOnChangeDirectionListener(OnChangeDirectionListener onChangeDirectionListener) {
        this.mChangeDirectionListener = onChangeDirectionListener;
    }

    public void setOnDrawCaptionListener(OnDrawCaptionListener onDrawCaptionListener) {
        this.mDrawCaptionListener = onDrawCaptionListener;
    }

    public float drawTopCaption(Canvas canvas, int i, int i2, float f, float f2, String str) {
        Canvas canvas2 = canvas;
        String str2 = str;
        if (str2 == null || str.length() == 0) {
            return -1.0f;
        }
        if (str.length() > 128) {
            OnDrawCaptionListener onDrawCaptionListener = this.mDrawCaptionListener;
            if (onDrawCaptionListener != null) {
                onDrawCaptionListener.textTooLong(str.length(), this.mOriginalTextSize);
            }
            return this.mOriginalTextSize;
        }
        List<String> findWords = findWords(str);
        float f3 = this.mOriginalTextSize;
        new Paint().setTextSize(this.mOriginalTextSize);
        float f4 = (float) i;
        int i3 = (int) (f4 - (f2 * 2.0f));
        if (findWords.size() > 0) {
            f3 = findTextSize(this.mTextPaint, findWords, 3, (float) i3, ((float) i2) / 4.0f, this.mMinTextSize, this.mMaxTextSize);
            if (f3 > 0.0f) {
                this.mTextPaint.setTextSize(f3);
                this.mStrokePaint.setTextSize(f3);
            } else {
                OnDrawCaptionListener onDrawCaptionListener2 = this.mDrawCaptionListener;
                if (onDrawCaptionListener2 != null) {
                    onDrawCaptionListener2.textTooLong(str.length(), f3);
                }
                this.mTextPaint.setTextSize(this.mOriginalTextSize);
                this.mStrokePaint.setTextSize(this.mOriginalTextSize);
                return f3;
            }
        }
        Rect rect = new Rect();
        this.mTextPaint.getTextBounds(str2, 0, str.length(), rect);
        int height = rect.height();
        this.mTextPaint.getTextBounds("m", 0, 1, rect);
        double height2 = rect.height();
        Double.isNaN(height2);
        double d = height;
        Double.isNaN(d);
        int i4 = (int) ((height2 * 0.5d) + d);
        int size = findWords.size();
        float f5 = f;
        int i5 = 0;
        boolean z = true;
        while (i5 < size) {
            int i6 = size - 1;
            while (true) {
                if (i6 < i5) {
                    i6 = i5;
                    break;
                }
                if (this.mTextPaint.measureText(toNormalSentence(findWords, i5, i6)) < ((float) i3)) {
                    break;
                }
                i6--;
            }
            if (z) {
                f5 += (float) height;
                z = false;
            } else {
                f5 += (float) i4;
            }
            String normalSentence = toNormalSentence(findWords, i5, i6);
            float measureText = (f4 - this.mTextPaint.measureText(normalSentence)) / 2.0f;
            if (this.mDrawStroke) {
                canvas2.drawText(normalSentence, measureText, f5, this.mStrokePaint);
            }
            canvas2.drawText(normalSentence, measureText, f5, this.mTextPaint);
            i5 = i6 + 1;
        }
        this.mTextPaint.setTextSize(this.mOriginalTextSize);
        this.mStrokePaint.setTextSize(this.mOriginalTextSize);
        return f3;
    }

    public float drawCenterCaption(Canvas canvas, int i, int i2, float f, String str) {
        Canvas canvas2 = canvas;
        int i3 = i2;
        String str2 = str;
        if (str2 == null || str.length() == 0) {
            return -1.0f;
        }
        if (str.length() > 256) {
            OnDrawCaptionListener onDrawCaptionListener = this.mDrawCaptionListener;
            if (onDrawCaptionListener != null) {
                onDrawCaptionListener.textTooLong(str.length(), this.mOriginalTextSize);
            }
            return this.mOriginalTextSize;
        }
        List<String> findWords = findWords(str);
        new Paint().setTextSize(this.mOriginalTextSize);
        float f2 = (float) i;
        int i4 = (int) (f2 - (f * 2.0f));
        float f3 = this.mOriginalTextSize;
        if (findWords.size() > 0) {
            if (str.length() <= 128) {
                f3 = findTextSize(this.mTextPaint, findWords, 3, (float) i4, ((float) i3) / 4.0f, this.mMinTextSize, this.mMaxTextSize);
            } else {
                f3 = findTextSize(this.mTextPaint, findWords, 6, (float) i4, ((float) i3) / 2.0f, this.mMinTextSize, this.mMaxTextSize);
            }
            if (f3 < 0.0f) {
                OnDrawCaptionListener onDrawCaptionListener2 = this.mDrawCaptionListener;
                if (onDrawCaptionListener2 != null) {
                    onDrawCaptionListener2.textTooLong(str.length(), f3);
                }
                return f3;
            }
            this.mTextPaint.setTextSize(f3);
            this.mStrokePaint.setTextSize(f3);
        }
        Rect rect = new Rect();
        int i5 = 0;
        this.mTextPaint.getTextBounds(str2, 0, str.length(), rect);
        int height = rect.height();
        this.mTextPaint.getTextBounds("m", 0, 1, rect);
        double height2 = rect.height();
        Double.isNaN(height2);
        double d = height;
        Double.isNaN(d);
        int i6 = (int) ((height2 * 0.5d) + d);
        float f4 = (float) i4;
        float findSentenceHeight = ((((float) i3) - findSentenceHeight(this.mTextPaint, f4, str2)) / 2.0f) + ((float) height);
        int size = findWords.size();
        while (i5 < size) {
            int i7 = size - 1;
            while (true) {
                if (i7 < i5) {
                    i7 = i5;
                    break;
                }
                if (this.mTextPaint.measureText(toNormalSentence(findWords, i5, i7)) < f4) {
                    break;
                }
                i7--;
            }
            String normalSentence = toNormalSentence(findWords, i5, i7);
            float measureText = (f2 - this.mTextPaint.measureText(normalSentence)) / 2.0f;
            if (this.mDrawStroke) {
                canvas2.drawText(normalSentence, measureText, findSentenceHeight, this.mStrokePaint);
            }
            canvas2.drawText(normalSentence, measureText, findSentenceHeight, this.mTextPaint);
            findSentenceHeight += (float) i6;
            i5 = i7 + 1;
        }
        this.mTextPaint.setTextSize(this.mOriginalTextSize);
        this.mStrokePaint.setTextSize(this.mOriginalTextSize);
        return f3;
    }

    public float drawBottomCaption(Canvas canvas, int i, int i2, float f, float f2, String str) {
        Canvas canvas2 = canvas;
        int i3 = i2;
        String str2 = str;
        if (str2 == null || str.length() == 0) {
            return -1.0f;
        }
        if (str.length() > 128) {
            OnDrawCaptionListener onDrawCaptionListener = this.mDrawCaptionListener;
            if (onDrawCaptionListener != null) {
                onDrawCaptionListener.textTooLong(str.length(), this.mOriginalTextSize);
            }
            return this.mOriginalTextSize;
        }
        List<String> findWords = findWords(str);
        float f3 = this.mOriginalTextSize;
        new Paint().setTextSize(this.mOriginalTextSize);
        float f4 = (float) i;
        int i4 = (int) (f4 - (f2 * 2.0f));
        if (findWords.size() > 0) {
            f3 = findTextSize(this.mTextPaint, findWords, 3, (float) i4, ((float) i3) / 4.0f, this.mMinTextSize, this.mMaxTextSize);
            if (f3 > 0.0f) {
                this.mTextPaint.setTextSize(f3);
                this.mStrokePaint.setTextSize(f3);
            } else {
                OnDrawCaptionListener onDrawCaptionListener2 = this.mDrawCaptionListener;
                if (onDrawCaptionListener2 != null) {
                    onDrawCaptionListener2.textTooLong(str.length(), f3);
                }
                this.mTextPaint.setTextSize(this.mOriginalTextSize);
                this.mStrokePaint.setTextSize(this.mOriginalTextSize);
                return f3;
            }
        }
        Rect rect = new Rect();
        this.mTextPaint.getTextBounds(str2, 0, str.length(), rect);
        int height = rect.height();
        this.mTextPaint.getTextBounds("m", 0, 1, rect);
        double height2 = rect.height();
        Double.isNaN(height2);
        double d = height;
        Double.isNaN(d);
        int i5 = (int) ((height2 * 0.5d) + d);
        int size = findWords.size();
        int i6 = size - 1;
        float f5 = ((float) i3) - f;
        int i7 = i6;
        while (i7 >= 0) {
            int i8 = 0;
            while (true) {
                if (i8 > i7) {
                    i8 = i7;
                    break;
                }
                if (this.mTextPaint.measureText(toNormalSentence(findWords, i8, i7)) < ((float) i4)) {
                    break;
                }
                i8++;
            }
            f5 -= (float) i5;
            i7 = i8 - 1;
        }
        float f6 = (float) i5;
        float f7 = f5 + f6;
        int i9 = 0;
        while (i9 < size) {
            int i10 = i6;
            while (true) {
                if (i10 < i9) {
                    i10 = i9;
                    break;
                }
                if (this.mTextPaint.measureText(toNormalSentence(findWords, i9, i10)) < ((float) i4)) {
                    break;
                }
                i10--;
            }
            String normalSentence = toNormalSentence(findWords, i9, i10);
            float measureText = (f4 - this.mTextPaint.measureText(normalSentence)) / 2.0f;
            if (this.mDrawStroke) {
                canvas2.drawText(normalSentence, measureText, f7, this.mStrokePaint);
            }
            canvas2.drawText(normalSentence, measureText, f7, this.mTextPaint);
            f7 += f6;
            i9 = i10 + 1;
        }
        this.mTextPaint.setTextSize(this.mOriginalTextSize);
        this.mStrokePaint.setTextSize(this.mOriginalTextSize);
        return f3;
    }

    public static int findTextLineCount(Paint paint, List<String> list, float f) {
        int size = list.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            int i3 = size - 1;
            while (true) {
                if (i3 < i) {
                    break;
                } else if (paint.measureText(toNormalSentence(list, i, i3)) < f) {
                    i = i3;
                    break;
                } else {
                    i3--;
                }
            }
            i++;
            i2++;
        }
        return i2;
    }

    public static float findTextSizeWithEnter(Paint paint, String str, int i, float f, float f2, float f3, float f4) {
        float f5 = f3;
        for (String findWords : findLines(str)) {
            float findTextSize = findTextSize(paint, findWords(findWords), 1, f, f2, f3, f4);
            if (findTextSize < 0.0f) {
                findTextSize = f3;
            }
            if (findTextSize < f5) {
                f5 = findTextSize;
            }
        }
        return f5;
    }

    public static float findTextSize(Paint paint, List<String> list, int i, float f, float f2, float f3, float f4) {
        float textSize = paint.getTextSize();
        float f5 = f3;
        while (f3 <= f4) {
            paint.setTextSize(f3);
            if (findTextLineCount(paint, list, f) > i || findSentenceHeight(paint, f, list) > f2) {
                break;
            }
            f5 = f3;
            f3 = 1.0f + f3;
        }
        paint.setTextSize(f5);
        int findTextLineCount = findTextLineCount(paint, list, f);
        paint.setTextSize(textSize);
        if (findTextLineCount > i) {
            return -1.0f;
        }
        return f5;
    }

    public static List<String> findLines(String str) {
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) != 10) {
                sb.append(str.charAt(i));
            }
            if ((str.charAt(i) == 10 || i == length - 1) && sb.length() > 0) {
                arrayList.add(sb.toString());
                sb = new StringBuilder();
            }
        }
        return arrayList;
    }

    public static float findSentenceHeight(Paint paint, float f, float f2, List<String> list) {
        return findSentenceHeight(paint, f, f2, toNormalSentence(list, 0, list.size() - 1));
    }

    public static float findSentenceHeight(Paint paint, float f, List<String> list) {
        return findSentenceHeight(paint, f, toNormalSentence(list, 0, list.size() - 1));
    }

    public static float findSentenceHeight(Paint paint, float f, String str) {
        List<String> findWords = findWords(str);
        Rect rect = new Rect();
        int i = 0;
        paint.getTextBounds(str, 0, str.length(), rect);
        int height = rect.height();
        paint.getTextBounds("m", 0, 1, rect);
        double height2 = rect.height();
        Double.isNaN(height2);
        double d = height;
        Double.isNaN(d);
        int i2 = (int) ((height2 * 0.5d) + d);
        int size = findWords.size();
        float f2 = 0.0f;
        while (i < size) {
            int i3 = size - 1;
            while (true) {
                if (i3 < i) {
                    break;
                } else if (paint.measureText(toNormalSentence(findWords, i, i3)) <= f) {
                    i = i3;
                    break;
                } else {
                    i3--;
                }
            }
            f2 += (float) i2;
            i++;
        }
        return f2;
    }

    public static float findSentenceHeight(Paint paint, float f, float f2, String str) {
        List<String> findWords = findWords(str);
        Rect rect = new Rect();
        int i = 0;
        paint.getTextBounds(str, 0, str.length(), rect);
        int height = rect.height();
        paint.getTextBounds("m", 0, 1, rect);
        double height2 = rect.height();
        Double.isNaN(height2);
        double d = height;
        Double.isNaN(d);
        int i2 = (int) ((height2 * 0.5d) + d);
        int width = (int) ((f - (f2 * 2.0f)) - ((float) rect.width()));
        int size = findWords.size();
        float f3 = 0.0f;
        while (i < size) {
            int i3 = size - 1;
            while (true) {
                if (i3 < i) {
                    break;
                } else if (paint.measureText(toNormalSentence(findWords, i, i3)) < ((float) width)) {
                    i = i3;
                    break;
                } else {
                    i3--;
                }
            }
            f3 += (float) i2;
            i++;
        }
        return f3;
    }

    public float findSentenceHeight(int i, float f, String str) {
        return findSentenceHeight(this.mTextPaint, (float) i, f, str);
    }

    public static List<String> findWords(String str) {
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt != ' ') {
                sb.append(charAt);
            } else if (sb.length() > 0) {
                arrayList.add(sb.toString());
                sb = new StringBuilder();
            }
        }
        if (sb.length() > 0) {
            arrayList.add(sb.toString());
        }
        return arrayList;
    }

    public static String toNormalSentence(List<String> list) {
        String str = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            str = str.concat(" ").concat(list.get(i));
        }
        return str;
    }

    public static String toNormalSentence(List<String> list, int i, int i2) {
        String str = list.get(i);
        while (true) {
            i++;
            if (i > i2) {
                return str;
            }
            str = str.concat(" ").concat(list.get(i));
        }
    }

    public static int findDirectionMovement(PointF pointF, PointF pointF2) {
        float f = pointF2.x - pointF.x;
        float f2 = pointF2.y - pointF.y;
        float abs = Math.abs(f);
        float abs2 = Math.abs(f2);
        return f2 * f >= 0.0f ? f < 0.0f ? abs >= abs2 ? 2 : 0 : abs >= abs2 ? 3 : 1 : f < 0.0f ? abs >= abs2 ? 2 : 1 : abs >= abs2 ? 3 : 0;
    }

    public void resetAllValues() {
        this.mText = "";
        this.mTextColor = -1;
        this.mOriginalTextSize = getContext().getResources().getDimension(R.dimen.photo_editor_text_default_size);
        this.mFontTf = Typeface.DEFAULT;
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setTypeface(this.mFontTf);
        this.mTextPaint.setTextSize(this.mOriginalTextSize);
        this.mStrokeColor = -16777216;
        this.mStrokePaint.setColor(this.mTextColor);
        this.mStrokePaint.setTypeface(this.mFontTf);
        this.mStrokePaint.setTextSize(this.mOriginalTextSize);
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
        }
        this.mOriginalTextSize = getContext().getResources().getDimension(R.dimen.photo_editor_text_default_size);
        float f = this.mOriginalTextSize;
        this.mFirstTextSize = f;
        this.mSecondTextSize = f;
        this.mMinTextSize = getContext().getResources().getDimension(R.dimen.photo_editor_text_min_size);
        this.mMaxTextSize = getContext().getResources().getDimension(R.dimen.photo_editor_text_max_size);
        initPaints();
        float f2 = getContext().getResources().getDisplayMetrics().density;
        this.mTopMarginCaption = getResources().getDimension(R.dimen.photo_editor_top_margin_caption);
        this.mLeftRightMarginCaption = getResources().getDimension(R.dimen.photo_editor_left_right_margin_caption);
        this.mBottomMarginCaption = getResources().getDimension(R.dimen.photo_editor_bottom_margin_caption);
        this.mStrokeWidth = getResources().getDimension(R.dimen.photo_editor_stroke_width);
        this.mMinTouchDist = (int) ((f2 * 15.0f) + 0.5f);
    }
}
