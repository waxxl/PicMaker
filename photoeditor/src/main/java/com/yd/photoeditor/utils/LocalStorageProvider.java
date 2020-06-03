package com.yd.photoeditor.utils;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsProvider;
import android.util.Log;
import com.yd.photoeditor.R;
import com.yd.photoeditor.database.table.BaseTable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LocalStorageProvider extends DocumentsProvider {
    public static final String AUTHORITY = "com.ianhanniballake.localstorage.documents";
    private static final String[] DEFAULT_DOCUMENT_PROJECTION = {"document_id", "_display_name", "flags", "mime_type", "_size", BaseTable.COLUMN_LAST_MODIFIED};
    private static final String[] DEFAULT_ROOT_PROJECTION = {"root_id", "flags", "title", "document_id", "icon", "available_bytes"};

    public boolean onCreate() {
        return true;
    }

    public Cursor queryRoots(String[] strArr) throws FileNotFoundException {
        if (strArr == null) {
            strArr = DEFAULT_ROOT_PROJECTION;
        }
        MatrixCursor matrixCursor = new MatrixCursor(strArr);
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        MatrixCursor.RowBuilder newRow = matrixCursor.newRow();
        newRow.add("root_id", externalStorageDirectory.getAbsolutePath());
        newRow.add("document_id", externalStorageDirectory.getAbsolutePath());
        newRow.add("title", "title");
        newRow.add("flags", 3);
        newRow.add("icon", Integer.valueOf(R.drawable.ic_launcher_background));
        newRow.add("available_bytes", Long.valueOf(externalStorageDirectory.getFreeSpace()));
        return matrixCursor;
    }

    public String createDocument(String str, String str2, String str3) throws FileNotFoundException {
        File file = new File(str, str3);
        try {
            file.createNewFile();
            return file.getAbsolutePath();
        } catch (IOException unused) {
            String simpleName = LocalStorageProvider.class.getSimpleName();
            Log.e(simpleName, "Error creating new file " + file);
            return null;
        }
    }


    public android.content.res.AssetFileDescriptor openDocumentThumbnail(String r9, android.graphics.Point r10, CancellationSignal r11) throws FileNotFoundException {
        /*
            r8 = this;
            java.lang.String r11 = "Error closing thumbnail"
            java.lang.Class<com.yd.photoeditor.utils.LocalStorageProvider> r0 = com.yd.photoeditor.utils.LocalStorageProvider.class
            android.graphics.BitmapFactory$Options r1 = new android.graphics.BitmapFactory$Options
            r1.<init>()
            r2 = 1
            r1.inJustDecodeBounds = r2
            android.graphics.BitmapFactory.decodeFile(r9, r1)
            int r3 = r10.y
            int r3 = r3 * 2
            int r10 = r10.x
            int r10 = r10 * 2
            int r4 = r1.outHeight
            int r5 = r1.outWidth
            r1.inSampleSize = r2
            if (r4 > r3) goto L_0x0021
            if (r5 <= r10) goto L_0x0033
        L_0x0021:
            int r4 = r4 / 2
            int r5 = r5 / 2
        L_0x0025:
            int r2 = r1.inSampleSize
            int r2 = r4 / r2
            if (r2 > r3) goto L_0x00a1
            int r2 = r1.inSampleSize
            int r2 = r5 / r2
            if (r2 <= r10) goto L_0x0033
            goto L_0x00a1
        L_0x0033:
            r10 = 0
            r1.inJustDecodeBounds = r10
            android.graphics.Bitmap r9 = android.graphics.BitmapFactory.decodeFile(r9, r1)
            r10 = 0
            java.lang.String r1 = "thumbnail"
            android.content.Context r2 = r8.getContext()     // Catch:{ IOException -> 0x0076, all -> 0x0074 }
            java.io.File r2 = r2.getCacheDir()     // Catch:{ IOException -> 0x0076, all -> 0x0074 }
            java.io.File r1 = java.io.File.createTempFile(r1, r10, r2)     // Catch:{ IOException -> 0x0076, all -> 0x0074 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0076, all -> 0x0074 }
            r2.<init>(r1)     // Catch:{ IOException -> 0x0076, all -> 0x0074 }
            android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ IOException -> 0x0072 }
            r4 = 90
            r9.compress(r3, r4, r2)     // Catch:{ IOException -> 0x0072 }
            r2.close()     // Catch:{ IOException -> 0x0059 }
            goto L_0x0061
        L_0x0059:
            r9 = move-exception
            java.lang.String r10 = r0.getSimpleName()
            android.util.Log.e(r10, r11, r9)
        L_0x0061:
            android.content.res.AssetFileDescriptor r9 = new android.content.res.AssetFileDescriptor
            r10 = 268435456(0x10000000, float:2.5243549E-29)
            android.os.ParcelFileDescriptor r3 = android.os.ParcelFileDescriptor.open(r1, r10)
            r4 = 0
            r6 = -1
            r2 = r9
            r2.<init>(r3, r4, r6)
            return r9
        L_0x0072:
            r9 = move-exception
            goto L_0x0078
        L_0x0074:
            r9 = move-exception
            goto L_0x0092
        L_0x0076:
            r9 = move-exception
            r2 = r10
        L_0x0078:
            java.lang.String r1 = r0.getSimpleName()     // Catch:{ all -> 0x0090 }
            java.lang.String r3 = "Error writing thumbnail"
            android.util.Log.e(r1, r3, r9)     // Catch:{ all -> 0x0090 }
            if (r2 == 0) goto L_0x008f
            r2.close()     // Catch:{ IOException -> 0x0087 }
            goto L_0x008f
        L_0x0087:
            r9 = move-exception
            java.lang.String r0 = r0.getSimpleName()
            android.util.Log.e(r0, r11, r9)
        L_0x008f:
            return r10
        L_0x0090:
            r9 = move-exception
            r10 = r2
        L_0x0092:
            if (r10 == 0) goto L_0x00a0
            r10.close()     // Catch:{ IOException -> 0x0098 }
            goto L_0x00a0
        L_0x0098:
            r10 = move-exception
            java.lang.String r0 = r0.getSimpleName()
            android.util.Log.e(r0, r11, r10)
        L_0x00a0:
            throw r9
        L_0x00a1:
            int r2 = r1.inSampleSize
            int r2 = r2 * 2
            r1.inSampleSize = r2
            goto L_0x0025
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yd.photoeditor.utils.LocalStorageProvider.openDocumentThumbnail(java.lang.String, android.graphics.Point, android.os.CancellationSignal):android.content.res.AssetFileDescriptor");
    }

    public Cursor queryChildDocuments(String str, String[] strArr, String str2) throws FileNotFoundException {
        if (strArr == null) {
            strArr = DEFAULT_DOCUMENT_PROJECTION;
        }
        MatrixCursor matrixCursor = new MatrixCursor(strArr);
        for (File file : new File(str).listFiles()) {
            if (!file.getName().startsWith(FileUtils.HIDDEN_PREFIX)) {
                includeFile(matrixCursor, file);
            }
        }
        return matrixCursor;
    }

    public Cursor queryDocument(String str, String[] strArr) throws FileNotFoundException {
        if (strArr == null) {
            strArr = DEFAULT_DOCUMENT_PROJECTION;
        }
        MatrixCursor matrixCursor = new MatrixCursor(strArr);
        includeFile(matrixCursor, new File(str));
        return matrixCursor;
    }

    private void includeFile(MatrixCursor matrixCursor, File file) throws FileNotFoundException {
        MatrixCursor.RowBuilder newRow = matrixCursor.newRow();
        newRow.add("document_id", file.getAbsolutePath());
        newRow.add("_display_name", file.getName());
        String documentType = getDocumentType(file.getAbsolutePath());
        newRow.add("mime_type", documentType);
        int i = file.canWrite() ? 6 : 0;
        if (documentType.startsWith("image/")) {
            i |= 1;
        }
        newRow.add("flags", Integer.valueOf(i));
        newRow.add("_size", Long.valueOf(file.length()));
        newRow.add(BaseTable.COLUMN_LAST_MODIFIED, Long.valueOf(file.lastModified()));
    }


    public String getDocumentType(String r3) throws FileNotFoundException {
        /*
            r2 = this;
            java.io.File r0 = new java.io.File
            r0.<init>(r3)
            boolean r3 = r0.isDirectory()
            if (r3 == 0) goto L_0x000e
            java.lang.String r3 = "vnd.android.document/directory"
            return r3
        L_0x000e:
            java.lang.String r3 = r0.getName()
            r1 = 46
            int r3 = r3.lastIndexOf(r1)
            if (r3 < 0) goto L_0x002f
            java.lang.String r0 = r0.getName()
            int r3 = r3 + 1
            java.lang.String r3 = r0.substring(r3)
            android.webkit.MimeTypeMap r0 = android.webkit.MimeTypeMap.getSingleton()
            java.lang.String r3 = r0.getMimeTypeFromExtension(r3)
            if (r3 == 0) goto L_0x002f
            return r3
        L_0x002f:
            java.lang.String r3 = "application/octet-stream"
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yd.photoeditor.utils.LocalStorageProvider.getDocumentType(java.lang.String):java.lang.String");
    }

    public void deleteDocument(String str) throws FileNotFoundException {
        new File(str).delete();
    }

    public ParcelFileDescriptor openDocument(String str, String str2, CancellationSignal cancellationSignal) throws FileNotFoundException {
        File file = new File(str);
        if (str2.indexOf(119) != -1) {
            return ParcelFileDescriptor.open(file, 805306368);
        }
        return ParcelFileDescriptor.open(file, 268435456);
    }
}
