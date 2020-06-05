package com.yd.photoeditor.database.table;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.yd.photoeditor.PhotoEditorApp;
import com.yd.photoeditor.database.DatabaseManager;
import java.util.UUID;

public abstract class BaseTable {
    public static final String BOOL_N = "N";
    public static final String BOOL_Y = "Y";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LAST_MODIFIED = "last_modified";
    public static final String COLUMN_STATUS = "status";
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_DELETED = "deleted";
    public Context mContext;
    private SQLiteDatabase mDatabase;

    public static String convertBooleanToText(boolean z) {
        return z ? BOOL_Y : BOOL_N;
    }

    public static boolean convertTextToBoolean(String str) {
        return BOOL_Y.equals(str);
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static String genRandomId(SQLiteDatabase sQLiteDatabase) {
        Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT hex(randomblob(8))", null);
        if (rawQuery.moveToFirst()) {
            String string = rawQuery.getString(0);
            rawQuery.close();
            return string;
        }
        if (rawQuery != null) {
            rawQuery.close();
        }
        throw new RuntimeException("Cannot generate a random id string from database.");
    }

    public BaseTable(Context context) {
        this.mDatabase = DatabaseManager.getInstance(context).getDb();
        this.mContext = context;
    }

    public BaseTable(SQLiteDatabase sQLiteDatabase) {
        this.mDatabase = sQLiteDatabase;
        this.mContext = null;
    }

    public void setDb(SQLiteDatabase sQLiteDatabase) {
        this.mDatabase = sQLiteDatabase;
    }

    public Context getContext() {
        return this.mContext;
    }

    public SQLiteDatabase getDatabase(Context context) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            if (mContext != null) {
                this.mDatabase = DatabaseManager.getInstance(mContext).getDb();
            }
        }
        return this.mDatabase;
    }

    public String genRandom16BytesHexString() {
        Cursor rawQuery = getDatabase(mContext).rawQuery("SELECT hex(randomblob(16))", null);
        if (rawQuery.moveToFirst()) {
            String string = rawQuery.getString(0);
            rawQuery.close();
            return string;
        }
        if (rawQuery != null) {
            rawQuery.close();
        }
        return null;
    }

    public String genRandom24BytesHexString() {
        Cursor rawQuery = getDatabase(mContext).rawQuery("SELECT hex(randomblob(24))", null);
        if (rawQuery.moveToFirst()) {
            String string = rawQuery.getString(0);
            rawQuery.close();
            return string;
        }
        if (rawQuery != null) {
            rawQuery.close();
        }
        throw new RuntimeException("Cannot generate a random id string from database.");
    }

    public String genRandom32BytesHexString() {
        Cursor rawQuery = getDatabase(mContext).rawQuery("SELECT hex(randomblob(32))", null);
        if (rawQuery.moveToFirst()) {
            String string = rawQuery.getString(0);
            rawQuery.close();
            return string;
        }
        if (rawQuery != null) {
            rawQuery.close();
        }
        throw new RuntimeException("Cannot generate a random id string from database.");
    }

    public String genRandom8BytesHexString() {
        Cursor rawQuery = getDatabase(mContext).rawQuery("SELECT hex(randomblob(8))", null);
        if (rawQuery.moveToFirst()) {
            String string = rawQuery.getString(0);
            rawQuery.close();
            return string;
        }
        if (rawQuery != null) {
            rawQuery.close();
        }
        return null;
    }

    public String genRandomId() {
        return genRandomId(getDatabase(mContext));
    }

    public String getCurrentDateTime() {
        Cursor rawQuery = getDatabase(mContext).rawQuery("SELECT datetime('now')", null);
        if (rawQuery.moveToFirst()) {
            String string = rawQuery.getString(0);
            rawQuery.close();
            return string;
        }
        if (rawQuery != null) {
            rawQuery.close();
        }
        throw new RuntimeException("Cannot get current date and time from database.");
    }
}
