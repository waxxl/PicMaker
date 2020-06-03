package com.yd.photoeditor.database.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.yd.photoeditor.model.ImageTemplate;
import java.util.ArrayList;
import java.util.List;

public class ImageTemplateTable extends BaseTable {
    public static final String COLUMN_CHILD = "child";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PACKAGE_ID = "package_id";
    public static final String COLUMN_PREVIEW = "preview";
    public static final String COLUMN_SELECTED_THUMBNAIL = "selected_thumbnail";
    public static final String COLUMN_TEMPLATE = "template";
    public static final String COLUMN_THUMBNAIL = "thumbnail";
    private static final String SQL_DATABASE_CREATE = "create table ImageTemplate(id INTEGER PRIMARY KEY AUTOINCREMENT, name text,thumbnail text,selected_thumbnail text,preview text,template text,child text,package_id integer,last_modified text,status text);";
    public static final String TABLE_NAME = "ImageTemplate";

    public static void createTable(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(SQL_DATABASE_CREATE);
    }

    public static void upgradeTable(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ImageTemplate");
    }

    public ImageTemplateTable(Context context) {
        super(context);
    }

    public ImageTemplate get(long j, String str) {
        Cursor query = getDatabase(mContext).query(TABLE_NAME, (String[]) null, "package_id = ? AND status = ? AND UPPER(name) = UPPER(?)", new String[]{String.valueOf(j), "active", str}, (String) null, (String) null, (String) null);
        if (query == null || !query.moveToFirst()) {
            return null;
        }
        ImageTemplate cursorToShadeInfo = cursorToShadeInfo(query);
        query.close();
        return cursorToShadeInfo;
    }

    public long insert(ImageTemplate imageTemplate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", imageTemplate.getTitle());
        contentValues.put("thumbnail", imageTemplate.getThumbnail());
        contentValues.put("selected_thumbnail", imageTemplate.getSelectedThumbnail());
        contentValues.put(COLUMN_PREVIEW, imageTemplate.getPreview());
        contentValues.put(COLUMN_TEMPLATE, imageTemplate.getTemplate());
        contentValues.put(COLUMN_CHILD, imageTemplate.getChild());
        contentValues.put("package_id", Long.valueOf(imageTemplate.getPackageId()));
        if (imageTemplate.getLastModified() == null || imageTemplate.getLastModified().length() < 1) {
            imageTemplate.setLastModified(getCurrentDateTime());
        }
        contentValues.put(BaseTable.COLUMN_LAST_MODIFIED, imageTemplate.getLastModified());
        if (imageTemplate.getStatus() == null || imageTemplate.getStatus().length() < 1) {
            imageTemplate.setStatus("active");
        }
        contentValues.put("status", imageTemplate.getStatus());
        long insert = getDatabase(mContext).insert(TABLE_NAME, (String) null, contentValues);
        imageTemplate.setId(insert);
        return insert;
    }

    public int update(ImageTemplate imageTemplate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", imageTemplate.getTitle());
        contentValues.put("thumbnail", imageTemplate.getThumbnail());
        contentValues.put("selected_thumbnail", imageTemplate.getSelectedThumbnail());
        contentValues.put(COLUMN_PREVIEW, imageTemplate.getPreview());
        contentValues.put(COLUMN_TEMPLATE, imageTemplate.getTemplate());
        contentValues.put(COLUMN_CHILD, imageTemplate.getChild());
        contentValues.put("package_id", Long.valueOf(imageTemplate.getPackageId()));
        contentValues.put(BaseTable.COLUMN_LAST_MODIFIED, getCurrentDateTime());
        if (imageTemplate.getStatus() == null || imageTemplate.getStatus().length() < 1) {
            imageTemplate.setStatus("active");
        }
        contentValues.put("status", imageTemplate.getStatus());
        return getDatabase(mContext).update(TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(imageTemplate.getId())});
    }

    public List<ImageTemplate> getAllRows() {
        Cursor query = getDatabase(mContext).query(TABLE_NAME, (String[]) null, "status = ? ", new String[]{"active"}, (String) null, (String) null, (String) null);
        if (query == null) {
            return null;
        }
        List<ImageTemplate> list = toList(query);
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

    public int deleteAllItemInPackage(long j) {
        return getDatabase(mContext).delete(TABLE_NAME, "package_id=?", new String[]{String.valueOf(j)});
    }

    private ImageTemplate cursorToShadeInfo(Cursor cursor) {
        ImageTemplate imageTemplate = new ImageTemplate();
        imageTemplate.setId(cursor.getLong(cursor.getColumnIndex("id")));
        imageTemplate.setLastModified(cursor.getString(cursor.getColumnIndex(BaseTable.COLUMN_LAST_MODIFIED)));
        imageTemplate.setStatus(cursor.getString(cursor.getColumnIndex("status")));
        imageTemplate.setTitle(cursor.getString(cursor.getColumnIndex("name")));
        imageTemplate.setThumbnail(cursor.getString(cursor.getColumnIndex("thumbnail")));
        imageTemplate.setSelectedThumbnail(cursor.getString(cursor.getColumnIndex("selected_thumbnail")));
        imageTemplate.setPreview(cursor.getString(cursor.getColumnIndex(COLUMN_PREVIEW)));
        imageTemplate.setTemplate(cursor.getString(cursor.getColumnIndex(COLUMN_TEMPLATE)));
        imageTemplate.setChild(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD)));
        imageTemplate.setPackageId((long) cursor.getInt(cursor.getColumnIndex("package_id")));
        return imageTemplate;
    }

    private List<ImageTemplate> toList(Cursor cursor) {
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
