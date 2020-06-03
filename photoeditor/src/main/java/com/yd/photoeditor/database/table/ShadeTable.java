package com.yd.photoeditor.database.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.yd.photoeditor.model.ShadeInfo;
import java.util.ArrayList;
import java.util.List;

public class ShadeTable extends BaseTable {
    public static final String COLUMN_FOREGROUND = "foreground";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PACKAGE_ID = "package_id";
    public static final String COLUMN_SELECTED_THUMBNAIL = "selected_thumbnail";
    public static final String COLUMN_THUMBNAIL = "thumbnail";
    public static final String COLUMN_TYPE = "type";
    public static final String FRAME_TYPE = "frame";
    public static final String SHADE_TYPE = "shade";
    private static final String SQL_DATABASE_CREATE = "create table Shade(id INTEGER PRIMARY KEY AUTOINCREMENT, name text,thumbnail text,selected_thumbnail text,foreground text,type text,package_id integer,last_modified text,status text);";
    public static final String TABLE_NAME = "Shade";

    public static void createTable(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(SQL_DATABASE_CREATE);
    }

    public static void upgradeTable(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS Shade");
    }

    public ShadeTable(Context context) {
        super(context);
    }

    public ShadeInfo get(long j, String str, String str2) {
        Cursor query = getDatabase(mContext).query(TABLE_NAME, (String[]) null, "package_id = ? AND status = ? AND type = ? AND UPPER(name) = UPPER(?)", new String[]{String.valueOf(j), "active", str2, str}, (String) null, (String) null, (String) null);
        if (query == null || !query.moveToFirst()) {
            return null;
        }
        ShadeInfo cursorToShadeInfo = cursorToShadeInfo(query);
        query.close();
        return cursorToShadeInfo;
    }

    public long insert(ShadeInfo shadeInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", shadeInfo.getTitle());
        contentValues.put("thumbnail", shadeInfo.getThumbnail());
        contentValues.put("selected_thumbnail", shadeInfo.getSelectedThumbnail());
        contentValues.put("foreground", shadeInfo.getForeground());
        contentValues.put("type", shadeInfo.getShadeType());
        contentValues.put("package_id", Long.valueOf(shadeInfo.getPackageId()));
        if (shadeInfo.getLastModified() == null || shadeInfo.getLastModified().length() < 1) {
            shadeInfo.setLastModified(getCurrentDateTime());
        }
        contentValues.put(BaseTable.COLUMN_LAST_MODIFIED, shadeInfo.getLastModified());
        if (shadeInfo.getStatus() == null || shadeInfo.getStatus().length() < 1) {
            shadeInfo.setStatus("active");
        }
        contentValues.put("status", shadeInfo.getStatus());
        long insert = getDatabase(mContext).insert(TABLE_NAME, (String) null, contentValues);
        shadeInfo.setId(insert);
        return insert;
    }

    public int update(ShadeInfo shadeInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", shadeInfo.getTitle());
        contentValues.put("thumbnail", shadeInfo.getThumbnail());
        contentValues.put("selected_thumbnail", shadeInfo.getSelectedThumbnail());
        contentValues.put("foreground", shadeInfo.getForeground());
        contentValues.put("type", shadeInfo.getShadeType());
        contentValues.put("package_id", Long.valueOf(shadeInfo.getPackageId()));
        contentValues.put(BaseTable.COLUMN_LAST_MODIFIED, getCurrentDateTime());
        if (shadeInfo.getStatus() == null || shadeInfo.getStatus().length() < 1) {
            shadeInfo.setStatus("active");
        }
        contentValues.put("status", shadeInfo.getStatus());
        return getDatabase(mContext).update(TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(shadeInfo.getId())});
    }

    public List<ShadeInfo> getAllRows() {
        Cursor query = getDatabase(mContext).query(TABLE_NAME, (String[]) null, "status = ? ", new String[]{"active"}, (String) null, (String) null, (String) null);
        if (query == null) {
            return null;
        }
        List<ShadeInfo> list = toList(query);
        query.close();
        return list;
    }

    public List<ShadeInfo> getRows(long j, String str) {
        Cursor query = getDatabase(mContext).query(TABLE_NAME, (String[]) null, "package_id = ? AND status = ? AND type = ?", new String[]{String.valueOf(j), "active", str}, (String) null, (String) null, (String) null);
        if (query == null) {
            return null;
        }
        List<ShadeInfo> list = toList(query);
        query.close();
        return list;
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

    public int deleteAllItemInPackage(long j, String str) {
        return getDatabase(mContext).delete(TABLE_NAME, "package_id=? AND type=?", new String[]{String.valueOf(j), str});
    }

    private ShadeInfo cursorToShadeInfo(Cursor cursor) {
        ShadeInfo shadeInfo = new ShadeInfo();
        shadeInfo.setId(cursor.getLong(cursor.getColumnIndex("id")));
        shadeInfo.setLastModified(cursor.getString(cursor.getColumnIndex(BaseTable.COLUMN_LAST_MODIFIED)));
        shadeInfo.setStatus(cursor.getString(cursor.getColumnIndex("status")));
        shadeInfo.setTitle(cursor.getString(cursor.getColumnIndex("name")));
        shadeInfo.setThumbnail(cursor.getString(cursor.getColumnIndex("thumbnail")));
        shadeInfo.setSelectedThumbnail(cursor.getString(cursor.getColumnIndex("selected_thumbnail")));
        shadeInfo.setForeground(cursor.getString(cursor.getColumnIndex("foreground")));
        shadeInfo.setShadeType(cursor.getString(cursor.getColumnIndex("type")));
        shadeInfo.setPackageId((long) cursor.getInt(cursor.getColumnIndex("package_id")));
        return shadeInfo;
    }

    private List<ShadeInfo> toList(Cursor cursor) {
        ArrayList arrayList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(cursorToShadeInfo(cursor));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        return arrayList;
    }
}
