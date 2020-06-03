package com.yd.photoeditor.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yd.photoeditor.config.ALog;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DatabaseManager.DB_NAME, (SQLiteDatabase.CursorFactory) null, DATABASE_VERSION);
        this.mContext = context;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        ALog.e(TAG, "onCreate()-db.getVersion=" + sQLiteDatabase.getVersion());
        try {
            sQLiteDatabase.beginTransaction();
            String[] strArr = {"create_filter"};
            for (String runSqlScript : strArr) {
                DatabaseManager.runSqlScript(mContext, sQLiteDatabase, runSqlScript);
            }
            sQLiteDatabase.setTransactionSuccessful();
            Log.i(TAG, "onCreate() runAssetSqlScript() CALLED!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sQLiteDatabase.endTransaction();
        }

    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        String str = TAG;
        ALog.i(str, "IN onUpgrade() oldVersion=" + i + ", newVersion=" + i2);
        try {
            sQLiteDatabase.beginTransaction();
            DatabaseManager.runSqlScript(this.mContext, sQLiteDatabase, "upgrade_db.sql");
            sQLiteDatabase.setTransactionSuccessful();
            ALog.i(TAG, "IN onUpgrade() runAssetSqlScript() CALLED!");
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            sQLiteDatabase.endTransaction();
            throw th;
        }
        sQLiteDatabase.endTransaction();
    }
}
