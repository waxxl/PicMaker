package com.yd.photoeditor.database.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.yd.photoeditor.model.CropInfo;
import java.util.ArrayList;
import java.util.List;

public class CropTable extends BaseTable {
    public static final String COLUMN_FOREGROUND = "foreground";
    public static final String COLUMN_MASK = "background";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PACKAGE_ID = "package_id";
    public static final String COLUMN_SELECTED_THUMBNAIL = "selected_thumbnail";
    public static final String COLUMN_THUMBNAIL = "thumbnail";
    private static final String SQL_DATABASE_CREATE = "create table Crop(id INTEGER PRIMARY KEY AUTOINCREMENT, name text,thumbnail text,selected_thumbnail text,foreground text,background text,package_id integer,last_modified text,status text);";
    public static final String TABLE_NAME = "Crop";

    public static void createTable(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(SQL_DATABASE_CREATE);
    }

    public static void upgradeTable(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS Crop");
    }

    public CropTable(Context context) {
        super(context);
    }

    public long insert(CropInfo cropInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", cropInfo.getTitle());
        contentValues.put("thumbnail", cropInfo.getThumbnail());
        contentValues.put("selected_thumbnail", cropInfo.getSelectedThumbnail());
        contentValues.put("foreground", cropInfo.getForeground());
        contentValues.put("background", cropInfo.getBackground());
        contentValues.put("package_id", Long.valueOf(cropInfo.getPackageId()));
        if (cropInfo.getLastModified() == null || cropInfo.getLastModified().length() < 1) {
            cropInfo.setLastModified(getCurrentDateTime());
        }
        contentValues.put(BaseTable.COLUMN_LAST_MODIFIED, cropInfo.getLastModified());
        if (cropInfo.getStatus() == null || cropInfo.getStatus().length() < 1) {
            cropInfo.setStatus("active");
        }
        contentValues.put("status", cropInfo.getStatus());
        long insert = getDatabase(mContext).insert(TABLE_NAME, (String) null, contentValues);
        cropInfo.setId(insert);
        return insert;
    }

    public int update(CropInfo cropInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", cropInfo.getTitle());
        contentValues.put("thumbnail", cropInfo.getThumbnail());
        contentValues.put("selected_thumbnail", cropInfo.getSelectedThumbnail());
        contentValues.put("foreground", cropInfo.getForeground());
        contentValues.put("background", cropInfo.getBackground());
        contentValues.put("package_id", Long.valueOf(cropInfo.getPackageId()));
        contentValues.put(BaseTable.COLUMN_LAST_MODIFIED, getCurrentDateTime());
        if (cropInfo.getStatus() == null || cropInfo.getStatus().length() < 1) {
            cropInfo.setStatus("active");
        }
        contentValues.put("status", cropInfo.getStatus());
        return getDatabase(mContext).update(TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(cropInfo.getId())});
    }

    public CropInfo getRow(long j, String str) {
        Cursor query = getDatabase(mContext).query(TABLE_NAME, (String[]) null, "package_id = ? AND status = ? AND UPPER(name) = UPPER(?)", new String[]{String.valueOf(j), "active", str}, (String) null, (String) null, (String) null);
        if (query == null) {
            return null;
        }
        List<CropInfo> list = toList(query);
        query.close();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<CropInfo> getAllRows() {
        Cursor query = getDatabase(mContext).query(TABLE_NAME, (String[]) null, "status = ? ", new String[]{"active"}, (String) null, (String) null, (String) null);
        if (query == null) {
            return null;
        }
        List<CropInfo> list = toList(query);
        query.close();
        return list;
    }

    public List<CropInfo> getAllRows(long j) {
//        Cursor query = getDatabase(mContext).query(TABLE_NAME, (String[]) null, "package_id = ? AND status = ? ", new String[]{String.valueOf(j), "active"}, (String) null, (String) null, (String) null);
//        if (query == null) {
//            return null;
//        }
//        List<CropInfo> list = toList(query);
//        query.close();

        CropInfo cropInfo = new CropInfo();
        cropInfo.setBackground("assets://images/crop_ic_balloon.png");
        cropInfo.setForeground("assets://images/crop_ic_balloon.png");
        cropInfo.setPackageId(0);
        ArrayList<CropInfo> list2 = new ArrayList<>();
        list2.add(cropInfo);
        return list2;
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
        return getDatabase(mContext).delete(TABLE_NAME, "package_id=?", new String[]{String.valueOf(j)});
    }

    private CropInfo cursorToCropInfo(Cursor cursor) {
        CropInfo cropInfo = new CropInfo();
        cropInfo.setId(cursor.getLong(cursor.getColumnIndex("id")));
        cropInfo.setLastModified(cursor.getString(cursor.getColumnIndex(BaseTable.COLUMN_LAST_MODIFIED)));
        cropInfo.setStatus(cursor.getString(cursor.getColumnIndex("status")));
        cropInfo.setTitle(cursor.getString(cursor.getColumnIndex("name")));
        cropInfo.setThumbnail(cursor.getString(cursor.getColumnIndex("thumbnail")));
        cropInfo.setSelectedThumbnail(cursor.getString(cursor.getColumnIndex("selected_thumbnail")));
        cropInfo.setForeground(cursor.getString(cursor.getColumnIndex("foreground")));
        cropInfo.setBackground(cursor.getString(cursor.getColumnIndex("background")));
        cropInfo.setPackageId((long) cursor.getInt(cursor.getColumnIndex("package_id")));
        return cropInfo;
    }

    private List<CropInfo> toList(Cursor cursor) {
        ArrayList arrayList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(cursorToCropInfo(cursor));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        return arrayList;
    }
}
