package com.yd.photoeditor.vv;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsProvider;
import android.util.Log;
import com.yd.photoeditor.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.provider.DocumentsContract.Document.COLUMN_LAST_MODIFIED;

public class LocalStorageProvider extends DocumentsProvider {
    public static final String AUTHORITY = "com.ianhanniballake.localstorage.documents";
    private static final String[] DEFAULT_DOCUMENT_PROJECTION = {"document_id", "_display_name", "flags", "mime_type", "_size", COLUMN_LAST_MODIFIED};
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
        return null;
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
