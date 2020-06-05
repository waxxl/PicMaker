package com.yd.photoeditor.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import com.yd.photoeditor.PhotoEditorApp;
import com.yd.photoeditor.config.ALog;
import com.yd.photoeditor.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static android.os.Environment.DIRECTORY_DCIM;

public class DatabaseManager {
    private static final Map<String, String[]> DB_MIGRATE_SCRIPT_NAMES_MAP = initializeDbMigrateScriptNamesMap();
    public static String DB_NAME = "photoeditor.db";
    public static final float DB_SCHEMA_VERSION = 1.0f;
    private static final String SQL_COMMENT = "--";
    private static final String TAG = DatabaseManager.class.getSimpleName();
    private static DatabaseManager instance;
    private final Context mCtx;
    private SQLiteDatabase mDb;
    private final File mDbFile;
    private String mFromDbSchemaVersion;

    public static String getDbFileFullPath(Context context) {
        if (context != null) {
            return getDbFileFullPath2(context);
        }
        throw new RuntimeException("no set context for DbHelper!");
    }

    public static String getDbFileFullPath2(Context context) {
        return context.getDatabasePath(DB_NAME).getAbsolutePath();
    }

    public static String getDbsDirPath(Context context) {
        String absolutePath = context.getDatabasePath(DB_NAME).getAbsolutePath();
        return absolutePath.substring(0, absolutePath.length() - DB_NAME.length());
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager(PhotoEditorApp.getAppContext());
        }
        return instance;
    }

    public static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    public static DatabaseManager getInstanceAfterKilledByOS(Context context) {
        instance = new DatabaseManager(context);
        return instance;
    }

    private static Map<String, String[]> initializeDbMigrateScriptNamesMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("2.5", new String[]{"migrate_db_from_2.5_to_3.1.sql", "migrate_db_from_3.1_to_3.2.sql", "migrate_db_from_3.2_to_3.3.sql", "migrate_db_from_3.3_to_3.4.sql", "migrate_db_from_3.4_to_3.5.sql", "fix_data_issues.sql", "migrate_db_from_3.5_to_3.6.sql", "create_FileEncryption_Db.sql", "migrate_db_from_3.6_to_3.7.sql"});
        String[] strArr = {"migrate_db_from_3.0_to_3.1.sql", "migrate_db_from_3.1_to_3.2.sql", "migrate_db_from_3.2_to_3.3.sql", "migrate_db_from_3.3_to_3.4.sql", "migrate_db_from_3.4_to_3.5.sql", "fix_data_issues.sql", "migrate_db_from_3.5_to_3.6.sql", "create_FileEncryption_Db.sql", "migrate_db_from_3.6_to_3.7.sql"};
        hashMap.put("3.0", strArr);
        hashMap.put("3.0", strArr);
        return hashMap;
    }

    public static byte[] loadBigBlob(SQLiteDatabase sQLiteDatabase, String str, String str2, int i, int i2) {
        if (i <= i2) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT SUBSTR(value, 1) FROM " + str + " WHERE id = ?", new String[]{str2});
            rawQuery.moveToFirst();
            byte[] blob = rawQuery.getBlob(0);
            rawQuery.close();
            return blob;
        }
        ByteBuffer allocate = ByteBuffer.allocate(i);
        int i3 = i;
        int i4 = 0;
        while (true) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT SUBSTR(value, ");
            int i5 = i4 + 1;
            sb.append(i4 * i2);
            sb.append(" , ");
            sb.append(i2);
            sb.append(") FROM ");
            sb.append(str);
            sb.append(" WHERE id = ?");
            Cursor rawQuery2 = sQLiteDatabase.rawQuery(sb.toString(), new String[]{str2});
            rawQuery2.moveToFirst();
            allocate.put(rawQuery2.getBlob(0));
            rawQuery2.close();
            i3 -= i2;
            if (i3 <= i2) {
                Cursor rawQuery3 = sQLiteDatabase.rawQuery("SELECT SUBSTR(value, " + (i5 * i2) + ") FROM " + str + " WHERE id = ?", new String[]{str2});
                rawQuery3.moveToFirst();
                allocate.put(rawQuery3.getBlob(0));
                rawQuery3.close();
                return allocate.array();
            }
            i4 = i5;
        }
    }

    public static void runSqlScript(Context context, SQLiteDatabase sqLiteDatabase, String str) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(str)));
        while (true) {
            String str2 = "";
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    String trim = readLine.trim();
                    if (trim.length() > 0 && !trim.startsWith(SQL_COMMENT)) {
                        str2 = str2 + " " + readLine;
                        if (str2.endsWith(";")) {
                            try {
                                sqLiteDatabase.execSQL(str2);
                                str2 = "";
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    bufferedReader.close();
                    System.gc();
                    break;
                }
            }
        }

    }

    private DatabaseManager(Context context) {
        if (context != null) {
            this.mCtx = context.getApplicationContext();
            this.mDbFile = context.getDatabasePath(DB_NAME);
            return;
        }
        throw new RuntimeException("PASSED Context is NULL!");
    }

    public synchronized void closeDb() {
        if (this.mDb != null && this.mDb.isOpen()) {
            this.mDb.close();
            this.mDb = null;
        }
    }

    public synchronized void createDb() {
        ALog.d(TAG, "createDb");
        this.mDb = new DatabaseHelper(this.mCtx).getWritableDatabase();
    }

    public synchronized void deleteDbFile() {
        closeDb();
        if (isDbFileExisted()) {
            this.mDbFile.delete();
        }
    }

    public void exportDatabases(Context context) {
        File[] listFiles = new File(getDbFileFullPath(context)).getParentFile().listFiles();
        if (listFiles != null) {
            String concat = Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM).toString().concat("/exportedDB");
            File filedir = new File(concat);
            if(filedir.exists()) {
                filedir.mkdir();
            }
            for (File file : listFiles) {
                try {
                    FileUtils.copyFile(file, new File(concat, file.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String genRandom2BytesHexString() {
        Cursor rawQuery = this.mDb.rawQuery("SELECT hex(randomblob(2))", null);
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

    public SQLiteDatabase getDb() {
        return this.mDb;
    }

    public String getDbSchemaVersion() {
        return getDbSchemaVersion(this.mDb);
    }

    public String getDbSchemaVersion(SQLiteDatabase sQLiteDatabase) {
        String str = "1.0";
        String[] strArr = {"db_schema_version"};
        try {
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT value FROM settings WHERE name = ?", strArr);
            if (!(rawQuery == null || !rawQuery.moveToFirst())) {
                str = str.trim();
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return str;
    }

    public boolean isDbFileExisted() {
        File databasePath = this.mCtx.getDatabasePath(DB_NAME);
        if (databasePath.exists() && databasePath.length() > 0) {
            return true;
        }
        databasePath.delete();
        return false;
    }

    public void migrateDb() throws IOException {
        if (this.mFromDbSchemaVersion == null) {
            this.mFromDbSchemaVersion = getDbSchemaVersion();
        }
        migrateDb(this.mFromDbSchemaVersion);
    }

    private void migrateDb(String str) throws IOException {
        String[] strArr = DB_MIGRATE_SCRIPT_NAMES_MAP.get(str);
        if (strArr != null && strArr.length > 0) {
            this.mDb.beginTransaction();
            for (String runSqlScript : strArr) {
                runSqlScript(runSqlScript);
            }
            this.mDb.setTransactionSuccessful();
            this.mDb.endTransaction();
        }
    }

    public boolean isOpenedDb() {
        SQLiteDatabase sQLiteDatabase = this.mDb;
        return sQLiteDatabase != null && sQLiteDatabase.isOpen();
    }

    public boolean needDbMigration() {
        this.mFromDbSchemaVersion = getDbSchemaVersion();
        return Float.parseFloat(this.mFromDbSchemaVersion) < 1.0f;
    }

    public synchronized boolean openDb() {
        ALog.d(TAG, "openDb");
        if (this.mDb != null && this.mDb.isOpen()) {
            return true;
        }
        try {
            this.mDb = SQLiteDatabase.openOrCreateDatabase(this.mDbFile, null);
            return this.mDb.isOpen();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void runSqlScript(String str) throws IOException {
        runSqlScript(this.mCtx, this.mDb, str);
    }

    public void upgradeBackupDb(SQLiteDatabase sQLiteDatabase) throws IOException {
        String[] strArr;
        String dbSchemaVersion = getDbSchemaVersion(sQLiteDatabase);
        if (Float.parseFloat(dbSchemaVersion) < 1.0f && (strArr = DB_MIGRATE_SCRIPT_NAMES_MAP.get(dbSchemaVersion)) != null && strArr.length > 0) {
            sQLiteDatabase.beginTransaction();
            for (String runSqlScript : strArr) {
                runSqlScript(this.mCtx, sQLiteDatabase, runSqlScript);
            }
            sQLiteDatabase.setTransactionSuccessful();
            sQLiteDatabase.endTransaction();
        }
    }
}
