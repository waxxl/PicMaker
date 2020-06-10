package com.yd.photoeditor.vv;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;

public class ImageDecoder {
    public static int SAMPLER_SIZE = 512;

    public static Bitmap decodeAsset(Context context, String str) {
        try {
            return decodeStreamToBitmap(context.getAssets().open(str));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeUriToBitmap(Context context, Uri uri) throws OutOfMemoryError {
        if (uri == null) {
            return null;
        }
        try {
            String path = FileUtils.getPath(context, uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int i2 = options.outWidth;
            int i3 = options.outHeight;
            while (true) {
                if (i2 / 2 <= 512) {
                    break;
                } else if (i3 / 2 <= 512) {
                    break;
                } else {
                    i2 /= 2;
                    i3 /= 2;
                    i *= 2;
                }
            }
            options.inSampleSize = i;
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            throw e2;
        }
    }

    public static Bitmap decodeBlobToBitmap(byte[] bArr) throws OutOfMemoryError {
        if (bArr == null) {
            return null;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            int i2 = options.outWidth;
            int i3 = options.outHeight;
            while (true) {
                if (i2 / 2 <= 512) {
                    break;
                } else if (i3 / 2 <= 512) {
                    break;
                } else {
                    i2 /= 2;
                    i3 /= 2;
                    i *= 2;
                }
            }
            options.inSampleSize = i;
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            throw e2;
        }
    }

    public static Drawable decodeBlobToDrawable(byte[] bArr, int i, int i2, Resources resources) throws OutOfMemoryError {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            options.inSampleSize = calculateInSampleSize(options, i, i2);
            options.inJustDecodeBounds = false;
            return new BitmapDrawable(resources, BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            throw e2;
        }
    }

    public static Bitmap decodeBlobToBitmap(byte[] bArr, int i, int i2, Resources resources) throws OutOfMemoryError {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            options.inSampleSize = calculateInSampleSize(options, i, i2);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            throw e2;
        }
    }

    public static Drawable decodeBlobToDrawble(byte[] bArr, Resources resources) throws OutOfMemoryError {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            int i2 = options.outWidth;
            int i3 = options.outHeight;
            while (true) {
                if (i2 / 2 <= 512) {
                    break;
                } else if (i3 / 2 <= 512) {
                    break;
                } else {
                    i2 /= 2;
                    i3 /= 2;
                    i *= 2;
                }
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = i;
            return new BitmapDrawable(resources, BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            throw e2;
        }
    }

    public static Drawable decodeStreamToDrawble(InputStream inputStream, Resources resources) throws OutOfMemoryError {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i = 1;
            options.inJustDecodeBounds = true;
            Rect rect = new Rect();
            BitmapFactory.decodeStream(inputStream, rect, options);
            int i2 = options.outWidth;
            int i3 = options.outHeight;
            while (true) {
                if (i2 / 2 <= 512) {
                    break;
                } else if (i3 / 2 <= 512) {
                    break;
                } else {
                    i2 /= 2;
                    i3 /= 2;
                    i *= 2;
                }
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = i;
            return new BitmapDrawable(resources, BitmapFactory.decodeStream(inputStream, rect, options));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            throw e2;
        }
    }

    public static Bitmap decodeFileToBitmap(String str) throws OutOfMemoryError {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            int i2 = options.outWidth;
            int i3 = options.outHeight;
            while (true) {
                if (i2 / 2 <= 512) {
                    break;
                } else if (i3 / 2 <= 512) {
                    break;
                } else {
                    i2 /= 2;
                    i3 /= 2;
                    i *= 2;
                }
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = i;
            return BitmapFactory.decodeFile(str, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            throw e2;
        }
    }

    public static Bitmap decodeStreamToBitmap(InputStream inputStream) throws OutOfMemoryError {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i = 1;
            options.inJustDecodeBounds = true;
            Rect rect = new Rect();
            BitmapFactory.decodeStream(inputStream, rect, options);
            int i2 = options.outWidth;
            int i3 = options.outHeight;
            while (true) {
                if (i2 / 2 <= 512) {
                    break;
                } else if (i3 / 2 <= 512) {
                    break;
                } else {
                    i2 /= 2;
                    i3 /= 2;
                    i *= 2;
                }
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = i;
            return BitmapFactory.decodeStream(inputStream, rect, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            throw e2;
        }
    }

    public static Bitmap decodeStreamToBitmap(InputStream inputStream, int i, int i2) throws OutOfMemoryError {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Rect rect = new Rect();
            BitmapFactory.decodeStream(inputStream, rect, options);
            options.inSampleSize = calculateInSampleSize(options, i, i2);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(inputStream, rect, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            throw e2;
        }
    }

    public static Drawable decodeStreamToDrawable(InputStream inputStream, int i, int i2, Resources resources) throws OutOfMemoryError {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Rect rect = new Rect();
            BitmapFactory.decodeStream(inputStream, rect, options);
            options.inSampleSize = calculateInSampleSize(options, i, i2);
            options.inJustDecodeBounds = false;
            return new BitmapDrawable(resources, BitmapFactory.decodeStream(inputStream, rect, options));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            throw e2;
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources resources, int i, int i2, int i3) throws OutOfMemoryError {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(resources, i, options);
            options.inSampleSize = calculateInSampleSize(options, i2, i3);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(resources, i, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            throw e2;
        }
    }

    public static Drawable decodeSampledDrawableFromResource(Resources resources, int i, int i2, int i3) throws OutOfMemoryError {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(resources, i, options);
            options.inSampleSize = calculateInSampleSize(options, i2, i3);
            options.inJustDecodeBounds = false;
            return new BitmapDrawable(resources, BitmapFactory.decodeResource(resources, i, options));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            throw e2;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 <= i2 && i4 <= i) {
            return 1;
        }
        int round = Math.round(((float) i3) / ((float) i2));
        int round2 = Math.round(((float) i4) / ((float) i));
        return round < round2 ? round : round2;
    }

    public static BitmapDrawable decodeUriToDrawable(Context context, Uri uri) {
        try {
            String path = FileUtils.getPath(context, uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i = 1;
            options.inJustDecodeBounds = true;
            //BitmapFactory.decodeFile(path, options);
            int i2 = options.outWidth;
            int i3 = options.outHeight;
            int i4 = SAMPLER_SIZE;
            while (true) {
                if (i2 / 2 <= i4) {
                    break;
                } else if (i3 / 2 <= i4) {
                    break;
                } else {
                    i2 /= 2;
                    i3 /= 2;
                    i *= 2;
                }
            }
            options.inSampleSize = i;
            options.inJustDecodeBounds = false;
            return new BitmapDrawable(context.getResources(), BitmapFactory.decodeFile(path, options));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            return null;
        }
    }

}
