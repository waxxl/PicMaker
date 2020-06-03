package com.yd.photoeditor;

import android.app.Application;
import android.content.Context;

public class PhotoEditorApp extends Application {
    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context = this;
    }

    public static Context getAppContext() {
        return context;
    }
}
