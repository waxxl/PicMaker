package com.eptonic.photocollage;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.eptonic.photocollage.model.DaoMaster;
import com.eptonic.photocollage.model.DaoSession;
import com.eptonic.photocollage.resEncryption.AppExtResManager;
import com.eptonic.photocollage.util.Constants;

public class PhotoCollageApplication extends Application {
    private static Context mContext;
    private static DaoSession mDaoSession;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppExtResManager ap = new AppExtResManager(this);
        ap.init1(this);
        mContext = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    public static Context getContext() {
        return mContext;
    }

    private void initGreenDao() {
        //创建数据库mydb.db
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME);
        //获取可写数据库
        SQLiteDatabase database = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(database);
        //获取Dao对象管理者
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
}
