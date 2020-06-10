package com.yd.photoeditor.vv;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static final String BIG_D_FOLDER = Environment.getExternalStorageDirectory().toString().concat("/Android/data/com.codetho.photocollage");
    public static final String FILE_FOLDER = BIG_D_FOLDER.concat("/files");
    public static final String BACKGROUND_FOLDER = FILE_FOLDER.concat("/background");
    public static final String ROOT_EDITED_IMAGE_FOLDER = FILE_FOLDER.concat("/edited");
    public static final String CROP_FOLDER = FILE_FOLDER.concat("/crop");
    public static final String EDITED_IMAGE_FOLDER = ROOT_EDITED_IMAGE_FOLDER.concat("/images");
    public static final String EDITED_IMAGE_THUMBNAIL_FOLDER = ROOT_EDITED_IMAGE_FOLDER.concat("/thumbnails");
    public static final String FILTER_FOLDER = FILE_FOLDER.concat("/filter");
    public static final String FRAME_FOLDER = FILE_FOLDER.concat("/frame");

    public static final String STICKER_FOLDER = FILE_FOLDER.concat("/sticker");
    public static final String TAG = Utils.class.getSimpleName();
    public static final String TEMP_FOLDER = BIG_D_FOLDER.concat("/Temp");

    public static void clearCachedImage(String str) {
    }

    public static File savedAndRemoveCachedImage(String str) {
        return null;
    }

    private Utils() {
    }

    public static File copyFileFromAsset(Context context, String str, String str2, boolean z) {
        try {
            File file = new File(str.concat("/").concat(new File(str2).getName()));
            if (!file.exists() || file.length() == 0 || z) {
                InputStream open = context.getAssets().open(str2);
                file.getParentFile().mkdirs();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[2048];
                while (true) {
                    int read = open.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                open.close();
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public static float dpFromPx(Context context, float f) {
        return f / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(Context context, float f) {
        return f * context.getResources().getDisplayMetrics().density;
    }

    public static boolean copyFile(File file, File file2) {
        try {
            file2.getParentFile().mkdirs();
            FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] bArr = new byte[2048];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.flush();
                    fileInputStream.close();
                    fileOutputStream.close();
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void enableStrictMode() {
        if (hasGingerbread()) {
            StrictMode.ThreadPolicy.Builder penaltyLog = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog();
            StrictMode.VmPolicy.Builder penaltyLog2 = new StrictMode.VmPolicy.Builder().detectAll().penaltyLog();
            if (hasHoneycomb()) {
                penaltyLog.penaltyFlashScreen();
            }
            StrictMode.setThreadPolicy(penaltyLog.build());
            StrictMode.setVmPolicy(penaltyLog2.build());
        }
    }

    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= 8;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= 9;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= 11;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= 12;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static String stringTransform(String str, int i) {
        char[] charArray = str.toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            charArray[i2] = (char) (charArray[i2] ^ i);
        }
        return String.valueOf(charArray);
    }
}
