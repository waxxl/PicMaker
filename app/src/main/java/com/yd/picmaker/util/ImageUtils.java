package com.yd.picmaker.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import androidx.core.view.MotionEventCompat;

import com.yd.picmaker.R;
import com.yd.photoeditor.utils.DateTimeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.media.session.PlaybackState.ACTION_PLAY_FROM_MEDIA_ID;
import static android.provider.DocumentsContract.EXTRA_ORIENTATION;

public class ImageUtils {
    public static final String FOLDER = "PhotoCollageDesignMatrix";
    private static final float MIN_OUTPUT_IMAGE_SIZE = 640.0f;
    public static final String OUTPUT_COLLAGE_FOLDER = Environment.getExternalStorageDirectory().toString().concat("/").concat(FOLDER);

    public static class MemoryInfo {
        public long availMem = 0;
        public long totalMem = 0;
    }

    public static MemoryInfo getMemoryInfo(Context context) {
        MemoryInfo memoryInfo = new MemoryInfo();
        ActivityManager.MemoryInfo memoryInfo2 = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(memoryInfo2);
        memoryInfo.availMem = memoryInfo2.availMem;
        if (Build.VERSION.SDK_INT >= 16) {
            memoryInfo.totalMem = memoryInfo2.totalMem;
        } else {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile("/proc/meminfo", "r");
                Matcher matcher = Pattern.compile("(\\d+)").matcher(randomAccessFile.readLine());
                String str = "";
                while (matcher.find()) {
                    str = matcher.group(1);
                }
                randomAccessFile.close();
                memoryInfo.totalMem = ((long) Double.parseDouble(str)) * ACTION_PLAY_FROM_MEDIA_ID;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return memoryInfo;
    }

    public static float calculateOutputScaleFactor(int i, int i2) {
        float min = ((float) Math.min(i, i2)) / MIN_OUTPUT_IMAGE_SIZE;
        float f = 1.0f;
        if (min < 1.0f && min > 0.0f) {
            f = 1.0f / min;
        }
        return f;
    }

    public static void saveAndShare(Context context, Bitmap bitmap) {
        try {
            String concat = DateTimeUtils.getCurrentDateTime().replaceAll(":", "-").concat(".png");
            File file = new File(OUTPUT_COLLAGE_FOLDER);
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, concat);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file2));
            PhotoUtils.addImageToGallery(file2.getAbsolutePath(), context);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/png");
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(file2.getAbsolutePath())));
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.photo_editor_share_image)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float dpFromPx(Context context, float f) {
        return f / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(Context context, float f) {
        return f * context.getResources().getDisplayMetrics().density;
    }

    public static Matrix createMatrixToDrawImageInCenterView(float f, float f2, float f3, float f4) {
        float max = Math.max(f / f3, f2 / f4);
        Matrix matrix = new Matrix();
        matrix.postTranslate((f - f3) / 2.0f, (f2 - f4) / 2.0f);
        matrix.postScale(max, max, f / 2.0f, f2 / 2.0f);
        return matrix;
    }

    public static long getUsedMemorySize() {
        try {
            Runtime runtime = Runtime.getRuntime();
            return runtime.totalMemory() - runtime.freeMemory();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void recycleView(View view) {
        Bitmap bitmap;
        if (view != null) {
            Drawable background = view.getBackground();
            view.setBackgroundColor(0);
            if (background != null && (background instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) background).getBitmap()) != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    public static void recycleImageView(ImageView imageView) {
        Bitmap bitmap;
        Bitmap bitmap2;
        if (imageView != null) {
            Drawable background = imageView.getBackground();
            Drawable drawable = imageView.getDrawable();
            imageView.setBackgroundColor(0);
            imageView.setImageBitmap(null);
            if (background != null && (background instanceof BitmapDrawable) && (bitmap2 = ((BitmapDrawable) background).getBitmap()) != null && !bitmap2.isRecycled()) {
                bitmap2.recycle();
            }
            if (drawable != null && (drawable instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) drawable).getBitmap()) != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    public static long getSizeInBytes(Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public static Bitmap loadBitmapFromView(View view) throws OutOfMemoryError {
        try {
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            Drawable background = view.getBackground();
            view.setBackgroundColor(0);
            view.layout(0, 0, measuredWidth, measuredHeight);
            Bitmap createBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            if (background != null) {
                background.draw(canvas);
            }
            view.draw(canvas);
            if (Build.VERSION.SDK_INT >= 16) {
                view.setBackground(background);
            } else {
                view.setBackgroundDrawable(background);
            }
            return createBitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void takeScreen(View view, String str) throws OutOfMemoryError {
        try {
            Bitmap loadBitmapFromView = loadBitmapFromView(view);
            File file = new File(str);
            file.getParentFile().mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            loadBitmapFromView.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (OutOfMemoryError e3) {
            e3.printStackTrace();
            throw e3;
        }
    }

    public static void saveBitmap(Bitmap bitmap, String str) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(str);
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e5) {
            e5.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getImageOrientation(Context context, String str) {
        int orientationFromExif = getOrientationFromExif(str);
        return orientationFromExif < 0 ? getOrientationFromMediaStore(context, Uri.fromFile(new File(str))) : orientationFromExif;
    }

    private static int getOrientationFromExif(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt("Orientation", 1);
            if (attributeInt == 1) {
                return 0;
            }
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt == 6) {
                return 90;
            }
            if (attributeInt != 8) {
                return -1;
            }
            return 270;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static int getOrientationFromMediaStore(Context context, Uri uri) {
        Cursor query;
        if (uri == null || (query = context.getContentResolver().query(uri, new String[]{EXTRA_ORIENTATION}, null, null, null)) == null || !query.moveToFirst()) {
            return -1;
        }
        int i = query.getInt(0);
        query.close();
        return i;
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

    public static Bitmap getCircularArea(Bitmap bitmap, int i, int i2, int i3) {
        int i4 = i3 * 2;
        Bitmap createBitmap = Bitmap.createBitmap(i4, i4, Bitmap.Config.ARGB_8888);
        Rect rect = new Rect();
        rect.top = i2 - i3;
        rect.bottom = rect.top + i4;
        rect.left = i - i3;
        rect.right = rect.left + i4;
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawCircle((float) (createBitmap.getWidth() / 2), (float) (createBitmap.getHeight() / 2), (float) i3, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, new Rect(0, 0, createBitmap.getWidth(), createBitmap.getHeight()), paint);
        return createBitmap;
    }

    public static Bitmap focus(Bitmap bitmap, Bitmap bitmap2, Rect rect, boolean z) {
        Bitmap bitmap3;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        Bitmap createBitmap = Bitmap.createBitmap(rect.right - rect.left, rect.bottom - rect.top, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, rect, new Rect(0, 0, createBitmap.getWidth(), createBitmap.getHeight()), paint);
        if (z) {
            bitmap3 = getCircularBitmap(createBitmap);
            createBitmap.recycle();
        } else {
            bitmap3 = createBitmap;
        }
        Bitmap createBitmap2 = Bitmap.createBitmap(bitmap2.getWidth(), bitmap2.getHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(createBitmap2);
        canvas.drawBitmap(bitmap3, new Rect(0, 0, bitmap3.getWidth(), bitmap3.getHeight()), rect, paint);
        bitmap3.recycle();
        return createBitmap2;
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        int i;
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        if (bitmap.getWidth() > bitmap.getHeight()) {
            i = bitmap.getHeight() / 2;
        } else {
            i = bitmap.getWidth() / 2;
        }
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawCircle((float) (bitmap.getWidth() / 2), (float) (bitmap.getHeight() / 2), (float) i, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }
}