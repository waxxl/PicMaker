package com.yd.picmaker.resut;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;

import com.yd.picmaker.resut.utils.Reflect;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;


public class ResUtils {
    public static String absExtF = "";
    public static List<WeakReference<AssetManager>> newAssertManagers = new LinkedList<>();
    public static WeakHashMap<Resources, WeakReference<AssetManager>> res2Cookie = new WeakHashMap<>();
    public static WeakHashMap<AssetManager, Integer> asset2Cookie = new WeakHashMap<>();
    public static int sdkv = Build.VERSION.SDK_INT;

    public static AssetManager buildOnlyExtAsset() throws InstantiationException, IllegalAccessException {
        AssetManager nam = AssetManager.class.newInstance();
        newAssertManagers.add(new WeakReference<>(nam));
        addExtPath(nam);
//        cookie = Reflect.on(nam).call("addAssetPath", MAPP.extF+"2").get();
//        System.out.println("buildOnlyExtAsset 2 cookie : " + cookie);
        return nam;
    }

    public static int addExtPath(AssetManager nam) {
        if(asset2Cookie.containsKey(nam)) { //加過了
            System.out.println("addExtPath 1 asset2Cookie.containsKey(nam) = true :  assert: " + nam+ " cookie: " + asset2Cookie.get(nam));
            return asset2Cookie.get(nam);
        }
        int cookie = Reflect.on(nam).call("addAssetPath", absExtF).get();
        asset2Cookie.put(nam, cookie);
        System.out.println("addExtPath new 2  assert: " + nam+ " cookie: " + asset2Cookie.get(nam));
        return cookie;
    }

    public static AssetManager newExtPathAssert(){
        try {
            AssetManager am = AssetManager.class.newInstance();
            addExtPath(am);
            return am;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void replaceExtAssert(Resources res){
        AssetManager am = newExtPathAssert();
        //TODO
        res.updateConfiguration(res.getConfiguration(),res.getDisplayMetrics());
    }

    public static void replaceAssert(Resources res, AssetManager am){
        //mAsset
        //resouceImpl.mAsset
//        Reflect.on(res).set("mAsset")
    }

    public static void tryInsertAsset(Resources res){
        if(asset2Cookie.containsKey(res.getAssets()))
            return;
        addExtPath(res.getAssets());
        res.updateConfiguration(res.getConfiguration(),res.getDisplayMetrics());
    }
}
