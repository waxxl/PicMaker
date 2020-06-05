package com.yd.picmaker.resut;

import android.content.Context;
import android.content.SharedPreferences;

public class SPHelper {
    public static long getLastGenExtVersion(Context c){
        return getSp(c).getLong("v",-1);
    }

    public static void setLastGenExtVersion(Context c, long v){
        getSp(c).edit().putLong("v", v).apply();
    }

    public static long getLastGenExtSize(Context c){
        return getSp(c).getLong("s",-1);
    }

    public static void setLastGenExtSize(Context c, long s){
        getSp(c).edit().putLong("s",s).apply();
    }

    private static SharedPreferences getSp(Context c) {
        return c.getSharedPreferences("", Context.MODE_PRIVATE);
    }
}
