package com.yd.photoeditor.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class FileUtils {
    private static final boolean DEBUG = false;
    public static final String HIDDEN_PREFIX = ".";
    public static final String MIME_TYPE_APP = "application/*";
    public static final String MIME_TYPE_AUDIO = "audio/*";
    public static final String MIME_TYPE_IMAGE = "image/*";
    public static final String MIME_TYPE_TEXT = "text/*";
    public static final String MIME_TYPE_VIDEO = "video/*";
    static final String TAG = "FileUtils";
    public static final String TEMP_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath().concat("/Android/data/com.yd.photoeditor/Temp");
    public static Comparator<File> sComparator = new Comparator<File>() {
        public int compare(File file, File file2) {
            return file.getName().toLowerCase().compareTo(file2.getName().toLowerCase());
        }
    };
    public static FileFilter sDirFilter = new FileFilter() {
        public boolean accept(File file) {
            return file.isDirectory() && !file.getName().startsWith(FileUtils.HIDDEN_PREFIX);
        }
    };
    public static FileFilter sFileFilter = new FileFilter() {
        public boolean accept(File file) {
            return file.isFile() && !file.getName().startsWith(FileUtils.HIDDEN_PREFIX);
        }
    };

    public static String getExtension(String str) {
        if (str == null) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf(HIDDEN_PREFIX);
        return lastIndexOf >= 0 ? str.substring(lastIndexOf) : "";
    }

    public static boolean isLocal(String str) {
        return str != null && !str.startsWith("http://") && !str.startsWith("https://");
    }

    public static boolean isMediaUri(Uri uri) {
        return "media".equalsIgnoreCase(uri.getAuthority());
    }

    public static Uri getUri(File file) {
        if (file != null) {
            return Uri.fromFile(file);
        }
        return null;
    }

    public static File getPathWithoutFilename(File file) {
        if (file == null) {
            return null;
        }
        if (file.isDirectory()) {
            return file;
        }
        String name = file.getName();
        String absolutePath = file.getAbsolutePath();
        String substring = absolutePath.substring(0, absolutePath.length() - name.length());
        if (substring.endsWith("/")) {
            substring = substring.substring(0, substring.length() - 1);
        }
        return new File(substring);
    }

    public static String getMimeType(File file) {
        String extension = getExtension(file.getName());
        return extension.length() > 0 ? MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1)) : "application/octet-stream";
    }

    public static String getMimeType(Context context, Uri uri) {
        return getMimeType(new File(getPath(context, uri)));
    }

    public static boolean isLocalStorageDocument(Uri uri) {
        return LocalStorageProvider.AUTHORITY.equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public static String getDataColumn(Context r8, Uri r9, String r10, String[] r11) {
        /*
            r0 = 1
            java.lang.String[] r3 = new java.lang.String[r0]
            java.lang.String r0 = "_data"
            r1 = 0
            r3[r1] = r0
            r7 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch:{ all -> 0x0033 }
            r6 = 0
            r2 = r9
            r4 = r10
            r5 = r11
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0033 }
            if (r8 == 0) goto L_0x002d
            boolean r9 = r8.moveToFirst()     // Catch:{ all -> 0x002b }
            if (r9 == 0) goto L_0x002d
            int r9 = r8.getColumnIndexOrThrow(r0)     // Catch:{ all -> 0x002b }
            java.lang.String r9 = r8.getString(r9)     // Catch:{ all -> 0x002b }
            if (r8 == 0) goto L_0x002a
            r8.close()
        L_0x002a:
            return r9
        L_0x002b:
            r9 = move-exception
            goto L_0x0035
        L_0x002d:
            if (r8 == 0) goto L_0x0032
            r8.close()
        L_0x0032:
            return r7
        L_0x0033:
            r9 = move-exception
            r8 = r7
        L_0x0035:
            if (r8 == 0) goto L_0x003a
            r8.close()
        L_0x003a:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yd.photoeditor.utils.FileUtils.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    public static String getPath(Context context, Uri uri) {
        Uri uri2 = null;
        if (!(Build.VERSION.SDK_INT >= 19) || !DocumentsContract.isDocumentUri(context, uri)) {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return getDataColumn(context, uri, null, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else if (isLocalStorageDocument(uri)) {
            return DocumentsContract.getDocumentId(uri);
        } else {
            if (isExternalStorageDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(split[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
            } else if (isMediaDocument(uri)) {
                String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                String str = split2[0];
                if ("image".equals(str)) {
                    uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(str)) {
                    uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(str)) {
                    uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                return getDataColumn(context, uri2, "_id=?", new String[]{split2[1]});
            }
        }
        return null;
    }

    public static File getFile(Context context, Uri uri) {
        String path;
        if (uri == null || (path = getPath(context, uri)) == null || !isLocal(path)) {
            return null;
        }
        return new File(path);
    }

    public static String getReadableFileSize(int i) {
        float f;
        DecimalFormat decimalFormat = new DecimalFormat("###.#");
        String str = " KB";
        if (i > 1024) {
            f = (float) (i / 1024);
            if (f > 1024.0f) {
                f /= 1024.0f;
                if (f > 1024.0f) {
                    f /= 1024.0f;
                    str = " GB";
                } else {
                    str = " MB";
                }
            }
        } else {
            f = 0.0f;
        }
        return decimalFormat.format(f) + str;
    }

    public static Bitmap getThumbnail(Context context, File file) {
        return getThumbnail(context, getUri(file), getMimeType(file));
    }

    public static Bitmap getThumbnail(Context context, Uri uri) {
        return getThumbnail(context, uri, getMimeType(context, uri));
    }

    public static Bitmap getThumbnail(Context r8, Uri r9, String r10) {
        /*
            boolean r0 = isMediaUri(r9)
            r1 = 0
            if (r0 != 0) goto L_0x000f
            java.lang.String r8 = "FileUtils"
            java.lang.String r9 = "You can only retrieve thumbnails for images and videos."
            android.util.Log.e(r8, r9)
            return r1
        L_0x000f:
            if (r9 == 0) goto L_0x005e
            android.content.ContentResolver r8 = r8.getContentResolver()
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r2 = r8
            r3 = r9
            android.database.Cursor r9 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x005a, all -> 0x0052 }
            boolean r0 = r9.moveToFirst()     // Catch:{ Exception -> 0x0050, all -> 0x004e }
            if (r0 == 0) goto L_0x0048
            r0 = 0
            int r0 = r9.getInt(r0)     // Catch:{ Exception -> 0x0050, all -> 0x004e }
            java.lang.String r2 = "video"
            boolean r2 = r10.contains(r2)     // Catch:{ Exception -> 0x0050, all -> 0x004e }
            r3 = 1
            if (r2 == 0) goto L_0x003a
            long r4 = (long) r0     // Catch:{ Exception -> 0x0050, all -> 0x004e }
            android.graphics.Bitmap r8 = android.provider.MediaStore.Video.Thumbnails.getThumbnail(r8, r4, r3, r1)     // Catch:{ Exception -> 0x0050, all -> 0x004e }
        L_0x0038:
            r1 = r8
            goto L_0x0048
        L_0x003a:
            java.lang.String r2 = "image/*"
            boolean r10 = r10.contains(r2)     // Catch:{ Exception -> 0x0050, all -> 0x004e }
            if (r10 == 0) goto L_0x0048
            long r4 = (long) r0     // Catch:{ Exception -> 0x0050, all -> 0x004e }
            android.graphics.Bitmap r8 = android.provider.MediaStore.Images.Thumbnails.getThumbnail(r8, r4, r3, r1)     // Catch:{ Exception -> 0x0050, all -> 0x004e }
            goto L_0x0038
        L_0x0048:
            if (r9 == 0) goto L_0x005e
        L_0x004a:
            r9.close()
            goto L_0x005e
        L_0x004e:
            r8 = move-exception
            goto L_0x0054
        L_0x0050:
            goto L_0x005b
        L_0x0052:
            r8 = move-exception
            r9 = r1
        L_0x0054:
            if (r9 == 0) goto L_0x0059
            r9.close()
        L_0x0059:
            throw r8
        L_0x005a:
            r9 = r1
        L_0x005b:
            if (r9 == 0) goto L_0x005e
            goto L_0x004a
        L_0x005e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yd.photoeditor.utils.FileUtils.getThumbnail(android.content.Context, android.net.Uri, java.lang.String):android.graphics.Bitmap");
    }

    public static Intent createGetContentIntent() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        intent.addCategory("android.intent.category.OPENABLE");
        return intent;
    }

    public static void cleanTempFiles() {
        File[] listFiles = new File(TEMP_FOLDER).listFiles();
        if (listFiles != null) {
            for (File delete : listFiles) {
                delete.delete();
            }
        }
    }

    public static boolean saveToFile(InputStream inputStream, File file) {
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[2048];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.flush();
                    inputStream.close();
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

    public static void downloadFile(String str, String str2) throws IOException {
        URLConnection openConnection = new URL(str).openConnection();
        openConnection.connect();
        InputStream inputStream = openConnection.getInputStream();
        new File(str2).getParentFile().mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(str2));
        byte[] bArr = new byte[2048];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
                return;
            }
        }
    }

    public static String sha128s(String str) throws NoSuchAlgorithmException {
        byte[] digest = MessageDigest.getInstance("SHA_1").digest(str.getBytes());
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : digest) {
            stringBuffer.append(Integer.toString((b & 255) + 256, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File deleteFile : listFiles) {
                    deleteFile(deleteFile);
                }
                file.delete();
                return;
            }
            file.delete();
            return;
        }
        file.delete();
    }

    public static void unzip(String str, String str2) {
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            extractFolder(new File(str), file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void extractFolder(File file, File file2) throws IOException {
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            File file3 = new File(file2.getAbsolutePath(), zipEntry.getName());
            file3.getParentFile().mkdirs();
            if (!zipEntry.isDirectory()) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                byte[] bArr = new byte[2048];
                if (!file3.exists()) {
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file3), 2048);
                    while (true) {
                        int read = bufferedInputStream.read(bArr, 0, 2048);
                        if (read == -1) {
                            break;
                        }
                        bufferedOutputStream.write(bArr, 0, read);
                    }
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                    bufferedInputStream.close();
                }
            }
        }
        zipFile.close();
    }

    public static String saveBitmapToFile(Bitmap bitmap, String str) {
        File file = new File(str);
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateMD5(String r6, long r7, long r9) throws IOException {
        /*
            r0 = 0
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ NoSuchAlgorithmException -> 0x0059, IOException -> 0x004d, all -> 0x0040 }
            java.io.File r2 = new java.io.File     // Catch:{ NoSuchAlgorithmException -> 0x0059, IOException -> 0x004d, all -> 0x0040 }
            r2.<init>(r6)     // Catch:{ NoSuchAlgorithmException -> 0x0059, IOException -> 0x004d, all -> 0x0040 }
            java.lang.String r6 = "r"
            r1.<init>(r2, r6)     // Catch:{ NoSuchAlgorithmException -> 0x0059, IOException -> 0x004d, all -> 0x0040 }
            r1.seek(r7)     // Catch:{ NoSuchAlgorithmException -> 0x005a, IOException -> 0x004e, all -> 0x003e }
            r6 = 2048(0x800, float:2.87E-42)
            byte[] r6 = new byte[r6]     // Catch:{ NoSuchAlgorithmException -> 0x005a, IOException -> 0x004e, all -> 0x003e }
            java.lang.String r7 = "MD5"
            java.security.MessageDigest r7 = java.security.MessageDigest.getInstance(r7)     // Catch:{ NoSuchAlgorithmException -> 0x005a, IOException -> 0x004e, all -> 0x003e }
            r2 = 0
        L_0x001c:
            int r8 = r1.read(r6)     // Catch:{ NoSuchAlgorithmException -> 0x005a, IOException -> 0x004e, all -> 0x003e }
            if (r8 <= 0) goto L_0x002d
            int r4 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r4 >= 0) goto L_0x002d
            r4 = 0
            r7.update(r6, r4, r8)     // Catch:{ NoSuchAlgorithmException -> 0x005a, IOException -> 0x004e, all -> 0x003e }
            long r4 = (long) r8     // Catch:{ NoSuchAlgorithmException -> 0x005a, IOException -> 0x004e, all -> 0x003e }
            long r2 = r2 + r4
            goto L_0x001c
        L_0x002d:
            byte[] r6 = r7.digest()     // Catch:{ NoSuchAlgorithmException -> 0x005a, IOException -> 0x004e, all -> 0x003e }
            java.lang.String r6 = byteArrayToHexString(r6)     // Catch:{ NoSuchAlgorithmException -> 0x005a, IOException -> 0x004e, all -> 0x003e }
            r1.close()     // Catch:{ IOException -> 0x0039 }
            goto L_0x003d
        L_0x0039:
            r7 = move-exception
            r7.printStackTrace()
        L_0x003d:
            return r6
        L_0x003e:
            r6 = move-exception
            goto L_0x0042
        L_0x0040:
            r6 = move-exception
            r1 = r0
        L_0x0042:
            if (r1 == 0) goto L_0x004c
            r1.close()     // Catch:{ IOException -> 0x0048 }
            goto L_0x004c
        L_0x0048:
            r7 = move-exception
            r7.printStackTrace()
        L_0x004c:
            throw r6
        L_0x004d:
            r1 = r0
        L_0x004e:
            if (r1 == 0) goto L_0x0058
            r1.close()     // Catch:{ IOException -> 0x0054 }
            goto L_0x0058
        L_0x0054:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0058:
            return r0
        L_0x0059:
            r1 = r0
        L_0x005a:
            if (r1 == 0) goto L_0x0064
            r1.close()     // Catch:{ IOException -> 0x0060 }
            goto L_0x0064
        L_0x0060:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0064:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yd.photoeditor.utils.FileUtils.generateMD5(java.lang.String, long, long):java.lang.String");
    }

    public static String generateMD5(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        try {
            byte[] bArr = new byte[2048];
            MessageDigest instance = MessageDigest.getInstance("MD5");
            while (true) {
                int read = inputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                instance.update(bArr, 0, read);
            }
            String byteArrayToHexString = byteArrayToHexString(instance.digest());
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return byteArrayToHexString;
        } catch (NoSuchAlgorithmException unused) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            return null;
        } catch (IOException unused2) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static String byteArrayToHexString(byte[] bArr) {
        String str = "";
        for (int i = 0; i < bArr.length; i++) {
            str = str + Integer.toString((bArr[i] & 255) + 256, 16).substring(1);
        }
        return str.toLowerCase();
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
}
