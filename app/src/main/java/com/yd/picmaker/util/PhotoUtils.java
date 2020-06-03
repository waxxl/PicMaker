package com.yd.picmaker.util;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import androidx.core.view.MotionEventCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
//import dauroi.photoeditor.blur.StackBlurManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PhotoUtils {
    public static final String ASSET_PREFIX = "assets://";
    public static final String DRAWABLE_PREFIX = "drawable://";
    public static final String EDITED_WHITE_IMAGE_SUFFIX = "_white.jpg";
    public static final int FLIP_HORIZONTAL = 2;
    public static final int FLIP_VERTICAL = 1;

    public static void addImageToGallery(String str, Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        contentValues.put("mime_type", "image/png");
        contentValues.put("_data", str);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    public static Bitmap fillBackgroundColorToImage(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(i);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint());
        return createBitmap;
    }

    public static Bitmap rotateImage(Bitmap bitmap, float f, boolean z) {
        if (f == 0.0f) {
            return bitmap;
        }
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        if (z) {
            matrix.postScale(1.0f, -1.0f);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap rotateImage(Bitmap bitmap, float f) {
        return rotateImage(bitmap, f, false);
    }

    public static int getCameraPhotoOrientation(Context context, String str) {
        try {
            int attributeInt = new ExifInterface(new File(str).getAbsolutePath()).getAttributeInt("Orientation", 1);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt == 6 || attributeInt != 8) {
                return 90;
            }
            return 270;
        } catch (Exception e) {
            e.printStackTrace();
            return 90;
        }
    }

    public static void loadImageWithGlide(Context context, ImageView imageView, String str) {
        if (str != null && str.length() > 1) {
            if (str.startsWith("http://") || str.startsWith("https://")) {
                Glide.with(context).load(str).diskCacheStrategy(DiskCacheStrategy.DATA).into(imageView);
            } else if (str.startsWith(DRAWABLE_PREFIX)) {
                try {
                    Glide.with(context).load(Integer.valueOf(Integer.parseInt(str.substring(11)))).into(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (str.startsWith(ASSET_PREFIX)) {
                Glide.with(context).load(Uri.parse("file:///android_asset/".concat(str.substring(9)))).into(imageView);
            } else {
                Glide.with(context).load(new File(str)).into(imageView);
            }
        }
    }

    public static Bitmap decodePNGImage(Context context, String str) {
        if (str.startsWith(DRAWABLE_PREFIX)) {
            try {
                return BitmapFactory.decodeResource(context.getResources(), Integer.parseInt(str.substring(11)));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (!str.startsWith(ASSET_PREFIX)) {
            return BitmapFactory.decodeFile(str);
        } else {
            try {
                return BitmapFactory.decodeStream(context.getAssets().open(str.substring(9)));
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    public static Bitmap flip(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        if (i == 1) {
            matrix.preScale(1.0f, -1.0f);
        } else if (i != 2) {
            return null;
        } else {
            matrix.preScale(-1.0f, 1.0f);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap blurImage(Bitmap bitmap, float f) {
        //return new StackBlurManager(bitmap).processNatively((int) f);
        return null;
    }

    public static Bitmap fastblur(Bitmap bitmap, int i) {
        int[] iArr;
        int i2 = i;
        Bitmap copy = bitmap.copy(bitmap.getConfig(), true);
        if (i2 < 1) {
            return null;
        }
        int width = copy.getWidth();
        int height = copy.getHeight();
        int i3 = width * height;
        int[] iArr2 = new int[i3];
        if (copy.isRecycled()) {
            return null;
        }
        copy.getPixels(iArr2, 0, width, 0, 0, width, height);
        int i4 = width - 1;
        int i5 = height - 1;
        int i6 = i2 + i2 + 1;
        int[] iArr3 = new int[i3];
        int[] iArr4 = new int[i3];
        int[] iArr5 = new int[i3];
        int[] iArr6 = new int[Math.max(width, height)];
        int i7 = (i6 + 1) >> 1;
        int i8 = i7 * i7;
        int i9 = i8 * 256;
        int[] iArr7 = new int[i9];
        for (int i10 = 0; i10 < i9; i10++) {
            iArr7[i10] = i10 / i8;
        }
        int[][] iArr8 = (int[][]) Array.newInstance(int.class, new int[]{i6, 3});
        int i11 = i2 + 1;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        while (i12 < height) {
            Bitmap bitmap2 = copy;
            int i15 = -i2;
            int i16 = 0;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            int i23 = 0;
            int i24 = 0;
            while (i15 <= i2) {
                int i25 = i5;
                int i26 = height;
                int i27 = iArr2[i13 + Math.min(i4, Math.max(i15, 0))];
                int[] iArr9 = iArr8[i15 + i2];
                iArr9[0] = (i27 & 16711680) >> 16;
                iArr9[1] = (i27 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr9[2] = i27 & 255;
                int abs = i11 - Math.abs(i15);
                i16 += iArr9[0] * abs;
                i17 += iArr9[1] * abs;
                i18 += iArr9[2] * abs;
                if (i15 > 0) {
                    i22 += iArr9[0];
                    i23 += iArr9[1];
                    i24 += iArr9[2];
                } else {
                    i19 += iArr9[0];
                    i20 += iArr9[1];
                    i21 += iArr9[2];
                }
                i15++;
                height = i26;
                i5 = i25;
            }
            int i28 = i5;
            int i29 = height;
            int i30 = i2;
            int i31 = 0;
            while (i31 < width) {
                iArr3[i13] = iArr7[i16];
                iArr4[i13] = iArr7[i17];
                iArr5[i13] = iArr7[i18];
                int i32 = i16 - i19;
                int i33 = i17 - i20;
                int i34 = i18 - i21;
                int[] iArr10 = iArr8[((i30 - i2) + i6) % i6];
                int i35 = i19 - iArr10[0];
                int i36 = i20 - iArr10[1];
                int i37 = i21 - iArr10[2];
                if (i12 == 0) {
                    iArr = iArr7;
                    iArr6[i31] = Math.min(i31 + i2 + 1, i4);
                } else {
                    iArr = iArr7;
                }
                int i38 = iArr2[i14 + iArr6[i31]];
                iArr10[0] = (i38 & 16711680) >> 16;
                iArr10[1] = (i38 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr10[2] = i38 & 255;
                int i39 = i22 + iArr10[0];
                int i40 = i23 + iArr10[1];
                int i41 = i24 + iArr10[2];
                i16 = i32 + i39;
                i17 = i33 + i40;
                i18 = i34 + i41;
                i30 = (i30 + 1) % i6;
                int[] iArr11 = iArr8[i30 % i6];
                i19 = i35 + iArr11[0];
                i20 = i36 + iArr11[1];
                i21 = i37 + iArr11[2];
                i22 = i39 - iArr11[0];
                i23 = i40 - iArr11[1];
                i24 = i41 - iArr11[2];
                i13++;
                i31++;
                iArr7 = iArr;
            }
            int[] iArr12 = iArr7;
            i14 += width;
            i12++;
            copy = bitmap2;
            height = i29;
            i5 = i28;
        }
        Bitmap bitmap3 = copy;
        int i42 = i5;
        int i43 = height;
        int[] iArr13 = iArr7;
        int i44 = 0;
        while (i44 < width) {
            int i45 = -i2;
            int i46 = i45 * width;
            int i47 = 0;
            int i48 = 0;
            int i49 = 0;
            int i50 = 0;
            int i51 = 0;
            int i52 = 0;
            int i53 = 0;
            int i54 = 0;
            int i55 = 0;
            while (i45 <= i2) {
                int[] iArr14 = iArr6;
                int max = Math.max(0, i46) + i44;
                int[] iArr15 = iArr8[i45 + i2];
                iArr15[0] = iArr3[max];
                iArr15[1] = iArr4[max];
                iArr15[2] = iArr5[max];
                int abs2 = i11 - Math.abs(i45);
                i47 += iArr3[max] * abs2;
                i48 += iArr4[max] * abs2;
                i49 += iArr5[max] * abs2;
                if (i45 > 0) {
                    i53 += iArr15[0];
                    i54 += iArr15[1];
                    i55 += iArr15[2];
                } else {
                    i50 += iArr15[0];
                    i51 += iArr15[1];
                    i52 += iArr15[2];
                }
                int i56 = i42;
                if (i45 < i56) {
                    i46 += width;
                }
                i45++;
                i42 = i56;
                iArr6 = iArr14;
            }
            int[] iArr16 = iArr6;
            int i57 = i42;
            int i58 = i44;
            int i59 = i54;
            int i60 = i55;
            int i61 = 0;
            int i62 = i2;
            int i63 = i53;
            int i64 = i52;
            int i65 = i51;
            int i66 = i50;
            int i67 = i49;
            int i68 = i48;
            int i69 = i47;
            int i70 = i43;
            while (i61 < i70) {
                iArr2[i58] = (iArr2[i58] & -16777216) | (iArr13[i69] << 16) | (iArr13[i68] << 8) | iArr13[i67];
                int i71 = i69 - i66;
                int i72 = i68 - i65;
                int i73 = i67 - i64;
                int[] iArr17 = iArr8[((i62 - i2) + i6) % i6];
                int i74 = i66 - iArr17[0];
                int i75 = i65 - iArr17[1];
                int i76 = i64 - iArr17[2];
                if (i44 == 0) {
                    iArr16[i61] = Math.min(i61 + i11, i57) * width;
                }
                int i77 = iArr16[i61] + i44;
                iArr17[0] = iArr3[i77];
                iArr17[1] = iArr4[i77];
                iArr17[2] = iArr5[i77];
                int i78 = i63 + iArr17[0];
                int i79 = i59 + iArr17[1];
                int i80 = i60 + iArr17[2];
                i69 = i71 + i78;
                i68 = i72 + i79;
                i67 = i73 + i80;
                i62 = (i62 + 1) % i6;
                int[] iArr18 = iArr8[i62];
                i66 = i74 + iArr18[0];
                i65 = i75 + iArr18[1];
                i64 = i76 + iArr18[2];
                i63 = i78 - iArr18[0];
                i59 = i79 - iArr18[1];
                i60 = i80 - iArr18[2];
                i58 += width;
                i61++;
                i2 = i;
            }
            i44++;
            i2 = i;
            i42 = i57;
            i43 = i70;
            iArr6 = iArr16;
        }
        bitmap3.setPixels(iArr2, 0, width, 0, 0, width, i43);
        return bitmap3;
    }

    public static float calculateScaleRatio(int i, int i2, int i3, int i4) {
        return Math.max(((float) i) / ((float) i3), ((float) i2) / ((float) i4));
    }

    public static int[] calculateThumbnailSize(int i, int i2, int i3, int i4) {
        int[] iArr = new int[2];
        float f = (float) i;
        float f2 = f / ((float) i3);
        float f3 = (float) i2;
        float max = Math.max(f2, f3 / ((float) i4));
        if (max == f2) {
            iArr[0] = i3;
            iArr[1] = (int) (f3 / max);
        } else {
            iArr[0] = (int) (f / max);
            iArr[1] = i4;
        }
        return iArr;
    }

    public static Bitmap cleanImage(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= width) {
                i2 = 0;
                break;
            } else if (!isTransparentColumn(bitmap, i2)) {
                break;
            } else {
                i2++;
            }
        }
        int i3 = width - 1;
        while (true) {
            if (i3 <= i2) {
                break;
            } else if (!isTransparentColumn(bitmap, i3)) {
                width = i3;
                break;
            } else {
                i3--;
            }
        }
        int i4 = 0;
        while (true) {
            if (i4 >= height) {
                break;
            } else if (!isTransparentRow(bitmap, i4)) {
                i = i4;
                break;
            } else {
                i4++;
            }
        }
        int i5 = height - 1;
        while (true) {
            if (i5 <= i) {
                break;
            } else if (!isTransparentRow(bitmap, i5)) {
                height = i5;
                break;
            } else {
                i5--;
            }
        }
        return Bitmap.createBitmap(bitmap, i2, i, (width - i2) + 1, (height - i) + 1);
    }

    private static boolean isTransparentRow(Bitmap bitmap, int i) {
        for (int i2 = 0; i2 < bitmap.getWidth(); i2++) {
            if (bitmap.getPixel(i2, i) != 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isTransparentColumn(Bitmap bitmap, int i) {
        for (int i2 = 0; i2 < bitmap.getHeight(); i2++) {
            if (bitmap.getPixel(i, i2) != 0) {
                return false;
            }
        }
        return true;
    }

    public static Bitmap transparentPadding(Bitmap bitmap, float f) throws OutOfMemoryError {
        int i;
        int i2;
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int i3 = (int) (((float) width) / f);
            int i4 = (i3 - height) / 2;
            if (i4 < 0) {
                i = (int) (((float) height) * f);
                i2 = Math.max((i - width) / 2, 0);
                i4 = 0;
            } else {
                i = width;
                height = i3;
                i2 = 0;
            }
            Bitmap createBitmap = Bitmap.createBitmap(i, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawBitmap(bitmap, (float) i2, (float) i4, new Paint());
            return createBitmap;
        } catch (OutOfMemoryError e) {
            throw e;
        }
    }

    public static Bitmap createBitmapMask(ArrayList<PointF> arrayList, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(-16777216);
        Path path = new Path();
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            if (i3 == 0) {
                path.moveTo(arrayList.get(i3).x, arrayList.get(i3).y);
            } else {
                path.lineTo(arrayList.get(i3).x, arrayList.get(i3).y);
            }
        }
        canvas.drawPath(path, paint);
        canvas.clipPath(path);
        return createBitmap;
    }

    public static Bitmap cropImage(Bitmap bitmap, Bitmap bitmap2, Matrix matrix) {
        Canvas canvas = new Canvas();
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(createBitmap);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        canvas.drawBitmap(bitmap, matrix, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode((Xfermode) null);
        return createBitmap;
    }

    public static Bitmap cropImage(Bitmap bitmap, Bitmap bitmap2) {
        Canvas canvas = new Canvas();
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(createBitmap);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode((Xfermode) null);
        return createBitmap;
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap bitmap2;
        int i;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            bitmap2 = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        if (bitmap.getWidth() > bitmap.getHeight()) {
            i = bitmap.getHeight() / 2;
        } else {
            i = bitmap.getWidth() / 2;
        }
        float f = (float) i;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-16777216);
        canvas.drawCircle(f, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmap2;
    }

    public static Bitmap getResizedBitmap(Uri uri, Context context) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            bitmap = null;
        }
        Bitmap bitmap2 = bitmap;
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();
        int i = (height > 4000 || width > 4000) ? 6 : (height > 3000 || width > 3000) ? 4 : (height > 2000 || width > 2000) ? 3 : (height > 1000 || width > 1000) ? 2 : 1;
        Matrix matrix = new Matrix();
        matrix.postScale(((float) (width / i)) / ((float) width), ((float) (height / i)) / ((float) height));
        return Bitmap.createBitmap(bitmap2, 0, 0, width, height, matrix, false);
    }

}
