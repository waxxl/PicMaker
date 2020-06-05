package com.yd.photoeditor.database.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.yd.photoeditor.model.ItemPackageInfo;
import java.util.ArrayList;
import java.util.List;

public class ItemPackageTable extends BaseTable {
    public static final String BACKGROUND_TYPE = "background";
    public static final String COLUMN_FOLDER = "folder";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SELECTED_THUMBNAIL = "selected_thumbnail";
    public static final String COLUMN_TEXT_ID = "id_str";
    public static final String COLUMN_THUMBNAIL = "thumbnail";
    public static final String COLUMN_TYPE = "type";
    public static final String CROP_TYPE = "crop";
    public static final String FILTER_TYPE = "filter";
    public static final String FRAME_TYPE = "frame";
    public static final String SHADE_TYPE = "shade";
    private static final String SQL_DATABASE_CREATE = "create table ItemPackage(id INTEGER PRIMARY KEY AUTOINCREMENT, name text,thumbnail text,selected_thumbnail text,type text,id_str text,folder text,last_modified text,status text);";
    public static final String STICKER_TYPE = "sticker";
    public static final String TABLE_NAME = "ItemPackage";

    public static void createTable(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(SQL_DATABASE_CREATE);
    }

    public static void upgradeTable(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ItemPackage");
    }

    public ItemPackageTable(Context context) {
        super(context);
    }

    public long insert(ItemPackageInfo itemPackageInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", itemPackageInfo.getTitle());
        contentValues.put("thumbnail", itemPackageInfo.getThumbnail());
        contentValues.put("selected_thumbnail", itemPackageInfo.getSelectedThumbnail());
        contentValues.put("type", itemPackageInfo.getType());
        contentValues.put(COLUMN_FOLDER, itemPackageInfo.getFolder());
        contentValues.put(COLUMN_TEXT_ID, itemPackageInfo.getIdString());
        if (itemPackageInfo.getLastModified() == null || itemPackageInfo.getLastModified().length() < 1) {
            itemPackageInfo.setLastModified(getCurrentDateTime());
        }
        contentValues.put(BaseTable.COLUMN_LAST_MODIFIED, itemPackageInfo.getLastModified());
        if (itemPackageInfo.getStatus() == null || itemPackageInfo.getStatus().length() < 1) {
            itemPackageInfo.setStatus("active");
        }
        contentValues.put("status", itemPackageInfo.getStatus());
        long insert = getDatabase(mContext).insert(TABLE_NAME, null, contentValues);
        itemPackageInfo.setId(insert);
        return insert;
    }

    public int update(ItemPackageInfo itemPackageInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", itemPackageInfo.getTitle());
        contentValues.put("thumbnail", itemPackageInfo.getThumbnail());
        contentValues.put("selected_thumbnail", itemPackageInfo.getSelectedThumbnail());
        contentValues.put("type", itemPackageInfo.getType());
        contentValues.put(BaseTable.COLUMN_LAST_MODIFIED, getCurrentDateTime());
        contentValues.put(COLUMN_FOLDER, itemPackageInfo.getFolder());
        contentValues.put(COLUMN_TEXT_ID, itemPackageInfo.getIdString());
        if (itemPackageInfo.getStatus() == null || itemPackageInfo.getStatus().length() < 1) {
            itemPackageInfo.setStatus("active");
        }
        contentValues.put("status", itemPackageInfo.getStatus());
        return getDatabase(mContext).update(TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(itemPackageInfo.getId())});
    }

    public boolean hasItem(String str, boolean z) {
        Cursor cursor;
        String str2 = "SELECT id FROM ItemPackage WHERE id_str =?";
        if (z) {
            str2 = str2.concat(" AND ").concat("status").concat(" =?");
        }
        if (z) {
            cursor = getDatabase(mContext).rawQuery(str2, new String[]{str, "active"});
        } else {
            cursor = getDatabase(mContext).rawQuery(str2, new String[]{str});
        }
        if (cursor == null) {
            return false;
        }
        boolean moveToFirst = cursor.moveToFirst();
        cursor.close();
        return moveToFirst;
    }

    public ItemPackageInfo getRowWithName(String str) {
        Cursor query = getDatabase(mContext).query(TABLE_NAME, null, "status = ? AND UPPER(name) = UPPER(?)", new String[]{"active", str}, null, null, null);
        if (query == null) {
            return null;
        }
        List<ItemPackageInfo> list = toList(query);
        query.close();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public ItemPackageInfo getRowWithStoreId(String str) {
        Cursor query = getDatabase(mContext).query(TABLE_NAME, null, "id_str = ?", new String[]{str}, null, null, null);
        if (query == null) {
            return null;
        }
        List<ItemPackageInfo> list = toList(query);
        query.close();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<ItemPackageInfo> getAllRows() {
        Cursor query = getDatabase(mContext).query(TABLE_NAME, null, "status = ? ", new String[]{"active"}, null, null, null);
        if (query == null) {
            return null;
        }
        List<ItemPackageInfo> list = toList(query);
        query.close();
        return list;
    }

    public List<ItemPackageInfo> getRows(String str) {
//        Cursor query = getDatabase(mContext).query(TABLE_NAME, (String[]) null, "status = ? AND type = ?", new String[]{"active", str}, (String) null, (String) null, BaseTable.COLUMN_LAST_MODIFIED.concat(" DESC"));
//        if (query == null) {
//            return null;
//        }
//        List<ItemPackageInfo> list = toList(query);
//        query.close();
        return new ArrayList<ItemPackageInfo>();
    }

    public int changeStatus(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BaseTable.COLUMN_LAST_MODIFIED, getCurrentDateTime());
        contentValues.put("status", str2);
        return getDatabase(mContext).update(TABLE_NAME, contentValues, "id_str = ?", new String[]{str});
    }

    public int markDeleted(String str) {
        return changeStatus(str, "deleted");
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

    public int delete(long j) {
        return getDatabase(mContext).delete(TABLE_NAME, "id=?", new String[]{String.valueOf(j)});
    }

    public int delete(String str) {
        return getDatabase(mContext).delete(TABLE_NAME, "id_str=?", new String[]{str});
    }

    private ItemPackageInfo cursorToItemPackage(Cursor cursor) {
        ItemPackageInfo itemPackageInfo = new ItemPackageInfo();
        itemPackageInfo.setId(cursor.getLong(cursor.getColumnIndex("id")));
        itemPackageInfo.setLastModified(cursor.getString(cursor.getColumnIndex(BaseTable.COLUMN_LAST_MODIFIED)));
        itemPackageInfo.setStatus(cursor.getString(cursor.getColumnIndex("status")));
        itemPackageInfo.setTitle(cursor.getString(cursor.getColumnIndex("name")));
        itemPackageInfo.setThumbnail(cursor.getString(cursor.getColumnIndex("thumbnail")));
        itemPackageInfo.setSelectedThumbnail(cursor.getString(cursor.getColumnIndex("selected_thumbnail")));
        itemPackageInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
        itemPackageInfo.setFolder(cursor.getString(cursor.getColumnIndex(COLUMN_FOLDER)));
        itemPackageInfo.setIdString(cursor.getString(cursor.getColumnIndex(COLUMN_TEXT_ID)));
        return itemPackageInfo;
    }

    private List<ItemPackageInfo> toList(Cursor cursor) {
        ArrayList arrayList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(cursorToItemPackage(cursor));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        return arrayList;
    }
}
