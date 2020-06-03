package com.yd.photoeditor.database.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.yd.photoeditor.model.FilterInfo;
import java.util.ArrayList;
import java.util.List;

public class FilterTable extends BaseTable {
    public static final String COLUMN_CMD = "cmd";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PACKAGE_ID = "package_id";
    public static final String COLUMN_SELECTED_THUMBNAIL = "selected_thumbnail";
    public static final String COLUMN_THUMBNAIL = "thumbnail";
    private static final String SQL_DATABASE_CREATE = "create table Filter(id INTEGER PRIMARY KEY AUTOINCREMENT, name text,thumbnail text,selected_thumbnail text,cmd text,package_id integer,last_modified text,status text);";
    public static final String TABLE_NAME = "Filter";

    public static void createTable(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(SQL_DATABASE_CREATE);
    }

    public static void upgradeTable(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS Filter");
    }

    public FilterTable(Context context) {
        super(context);
    }

    public FilterInfo get(long j, String str) {
        Cursor query = getDatabase(mContext).query(TABLE_NAME, (String[]) null, "package_id = ? AND status = ? AND UPPER(name) = UPPER(?)", new String[]{String.valueOf(j), "active", str}, (String) null, (String) null, (String) null);
        if (query == null || !query.moveToFirst()) {
            return null;
        }
        FilterInfo cursorToFilterInfo = cursorToFilterInfo(query);
        query.close();
        return cursorToFilterInfo;
    }

    public long insert(FilterInfo filterInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", filterInfo.getTitle());
        contentValues.put("thumbnail", filterInfo.getThumbnail());
        contentValues.put("selected_thumbnail", filterInfo.getSelectedThumbnail());
        contentValues.put(COLUMN_CMD, filterInfo.getCmd());
        contentValues.put("package_id", Long.valueOf(filterInfo.getPackageId()));
        if (filterInfo.getLastModified() == null || filterInfo.getLastModified().length() < 1) {
            filterInfo.setLastModified(getCurrentDateTime());
        }
        contentValues.put(BaseTable.COLUMN_LAST_MODIFIED, filterInfo.getLastModified());
        if (filterInfo.getStatus() == null || filterInfo.getStatus().length() < 1) {
            filterInfo.setStatus("active");
        }
        contentValues.put("status", filterInfo.getStatus());
        long insert = getDatabase(mContext).insert(TABLE_NAME, (String) null, contentValues);
        filterInfo.setId(insert);
        return insert;
    }

    public int update(FilterInfo filterInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", filterInfo.getTitle());
        contentValues.put("thumbnail", filterInfo.getThumbnail());
        contentValues.put("selected_thumbnail", filterInfo.getSelectedThumbnail());
        contentValues.put(COLUMN_CMD, filterInfo.getCmd());
        contentValues.put("package_id", Long.valueOf(filterInfo.getPackageId()));
        contentValues.put(BaseTable.COLUMN_LAST_MODIFIED, getCurrentDateTime());
        if (filterInfo.getStatus() == null || filterInfo.getStatus().length() < 1) {
            filterInfo.setStatus("active");
        }
        contentValues.put("status", filterInfo.getStatus());
        return getDatabase(mContext).update(TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(filterInfo.getId())});
    }

    public List<FilterInfo> getAllRows() {
        Cursor query = getDatabase(mContext).query(TABLE_NAME, (String[]) null, "status = ? ", new String[]{"active"}, (String) null, (String) null, (String) null);
        if (query == null) {
            return null;
        }
        List<FilterInfo> list = toList(query);
        query.close();
        return list;
    }

    public List<FilterInfo> getAllRows(long j) {
//        Cursor query = getDatabase(mContext).query(TABLE_NAME, (String[]) null, "package_id = ? AND status = ? ", new String[]{String.valueOf(j), "active"}, (String) null, (String) null, (String) null);
//        if (query == null) {
//            return null;
//        }
//        List<FilterInfo> list = toList(query);
//        query.close();



        return new ArrayList<>();
    }

    public int changeStatus(long j, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BaseTable.COLUMN_LAST_MODIFIED, getCurrentDateTime());
        contentValues.put("status", str);
        return getDatabase(mContext).update(TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(j)});
    }

    public int markDeleted(long j) {
        return changeStatus(j, "deleted");
    }

    public int deleteAllItemInPackage(long j) {
        return getDatabase(mContext).delete(TABLE_NAME, "package_id = ?", new String[]{String.valueOf(j)});
    }

    private FilterInfo cursorToFilterInfo(Cursor cursor) {
        FilterInfo filterInfo = new FilterInfo();
        filterInfo.setId(cursor.getLong(cursor.getColumnIndex("id")));
        filterInfo.setLastModified(cursor.getString(cursor.getColumnIndex(BaseTable.COLUMN_LAST_MODIFIED)));
        filterInfo.setStatus(cursor.getString(cursor.getColumnIndex("status")));
        filterInfo.setTitle(cursor.getString(cursor.getColumnIndex("name")));
        filterInfo.setThumbnail(cursor.getString(cursor.getColumnIndex("thumbnail")));
        filterInfo.setSelectedThumbnail(cursor.getString(cursor.getColumnIndex("selected_thumbnail")));
        filterInfo.setCmd(cursor.getString(cursor.getColumnIndex(COLUMN_CMD)));
        filterInfo.setPackageId((long) cursor.getInt(cursor.getColumnIndex("package_id")));
        return filterInfo;
    }

    private List<FilterInfo> toList(Cursor cursor) {
        ArrayList arrayList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(cursorToFilterInfo(cursor));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        return arrayList;
    }
}
