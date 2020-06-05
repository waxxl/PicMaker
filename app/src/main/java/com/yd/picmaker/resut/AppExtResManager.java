package com.yd.picmaker.resut;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

import com.yd.picmaker.BuildConfig;
import com.yd.picmaker.resut.utils.EDCoder;
import com.yd.picmaker.resut.utils.Reflect;
import com.yd.picmaker.resut.utils.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

//import android.app.MResM;
//import android.app.LoadedApk;
//import android.app.ResourcesManager;
//import com.gggg.restest.BuildConfig;

public class AppExtResManager extends ContextWrapper {
    public static final String TAG = "AppExtResManager";
    static Application instance;
    public String absExtFile;
    public static final String extFileName = "ext";

    public AppExtResManager(Context base) {
        super(base);
        absExtFile = getFilesDir().getAbsolutePath() + File.separator + extFileName;
        ResUtils.absExtF = absExtFile;
    }

    public static void resTest(Resources testRes, Context c) {
        Log.d(TAG, "========== resTest() called with: testRes = [" + testRes + "]");
        return;
//
//        try {
//            int val = testRes.getInteger(R.integer.my_int1);
//            Log.i(TAG, "GGG my_int1 " + R.integer.my_int1 + " val: " + val);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Object res = testRes.getLayout(R.layout.activity_main2);
//            Log.i(TAG, "GGG activity_main2 " + R.layout.activity_main2 + " res: " + res);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Object drawObj = testRes.getDrawable(R.drawable.aaaa);
//            Log.i(TAG, "GGG aaaa " + R.drawable.aaaa + " drawObj: " + drawObj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Object drawObj = testRes.getDrawable(R.drawable.ic_launcher_background);
//            Log.i(TAG, "GGG ic_launcher_background " + R.drawable.ic_launcher_background + " drawObj: " + drawObj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            String val = testRes.getString(R.string.app_name);
//            Log.i(TAG, "GGG app_name " + R.string.app_name + " val: " + val);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        XmlResourceParser myxml = null;
//        try {
//            myxml = testRes.getXml(R.xml.myxml);
//            Log.i(TAG, "GGG myxml " + myxml + " name:" + myxml.getName());
//            int type = -1;
//            while ((type = myxml.next()) != END_DOCUMENT) {
//                if (type == START_TAG)
//                    Log.i(TAG, "GGG tag name :" + myxml.getName());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Object o1 = testRes.getString(android.R.string.copy);
//            Log.i(TAG, "GGG android.R.string.copy o1:" + o1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            Object o1 = testRes.getDrawable(android.R.drawable.ic_menu_edit);
//            Log.i(TAG, "GGG android.R.drawable.ic_menu_edit o1:" + o1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            Object o1 = testRes.getLayout(android.R.layout.activity_list_item);
//            Log.i(TAG, "GGG android.R.layout.activity_list_item o1:" + o1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Typeface tt = Typeface.createFromAsset(testRes.getAssets(), "myfont.ttf");
//            Log.i(TAG, "GGG FONT " + tt);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Log.i(TAG, "GGG testRes.getAssets().list(\"\") : " + Arrays.toString(testRes.getAssets().list("")));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            LayoutInflater li = LayoutInflater.from(c); // 无效，从serivce cache 中拿的
//            View am2 = li.inflate(R.layout.activity_main2, null);
//            Log.i(TAG, "GGG resTest: li.inflate(R.layout.activity_main2, null) : " + am2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            Resources.Theme th = testRes.newTheme();
//            Log.i(TAG, "GGG resTest: testRes.newTheme() : " + th);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        //OK
//        try {
//            String metaName = "mymeta10";
//            getMeta(c, metaName);
//            getMeta(c, "mymeta11");
//            getMeta(c, "mymeta12");
//            getMeta(c, "mymeta13");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //FIXME
//        showNotify();
    }

    private static void getMeta(Context c, String metaName) throws PackageManager.NameNotFoundException {
        //            ActivityInfo aaa = c.getPackageManager().getActivityInfo(c.getApplicationContext(), PackageManager.GET_META_DATA);
//            Object val = aaa.metaData.get("mymeta");

//        ComponentName cn = new ComponentName(c, NormalAct.class);
//        ActivityInfo ai = c.getPackageManager().getActivityInfo(cn, PackageManager.GET_META_DATA);
//        Object mymetaVal = ai.metaData.get(metaName);
//        Log.i(TAG, "GGG metaName: "+metaName+" val:" + mymetaVal);
    }

    //只是在每个asset上加入新的assetPath
    public void init1(Application app) {
        instance = app;
        System.out.println("============ AppExtResManager 1  tryDecodeFile  app:" + app);
        tryDecodeFile();
        System.out.println("============ AppExtResManager 2  initAssert ===============");
        initAssert();
//        restTest1();
//        insertResManager(); //可能有危險。。。比如影響到其他本身就需要多apk包的應用的正常使用 , 不使用；通過加入spliteResDir[] 讓所有new 的res 加入 ext
        modifyLoadedApk(); //= this.getResouce() = getBaseContext().mResouce = loadedApk.mRes
        System.out.println("============ AppExtResManager 3 attachBaseContext（）  end ");
    }

    //另外的方案，完全不读取主apk的资源，全部只从ext中读取
    //替换所有asset对象

    private void initAssert() {
        //這個Res = Application.mRes = LoadedApk.mRes
        ResUtils.tryInsertAsset(getResources()); //Application.baseContext = contextImpl.getResouce()
        ResUtils.tryInsertAsset(instance.getResources()); //Application.baseContext = contextImpl.getResouce()
    }

    private void modifyLoadedApk() {
        Object at = Reflect.onClass("android.app.ActivityThread").get("sCurrentActivityThread");  //ActivityThread
        ArrayMap<String, WeakReference<Object>> mPackages = Reflect.on(at).get("mPackages");  //loadedApk
        WeakReference<Object> wrla = mPackages.get(getPackageName());
        Object la = wrla.get();
        if (la == null) {
            Log.e("TAG", "modifyLoadedApk: LoadedApk is null.");
            return;
        }
//        try {
//            String mainBase = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).sourceDir;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        //改這個有意義？
        modifyLoadedApkmResList(at, la); //DEBUG 判 base.apk 名稱
        modifyMSplitResDirs(la);
    }

    private void modifyMSplitResDirs(Object loadedApk) {
        String[] mSplitResDirs = Reflect.on(loadedApk).get("mSplitResDirs"); //TODO 其他類似變量的正常用途，和其他備選方案，和適配
        if (mSplitResDirs == null) {
            mSplitResDirs = new String[]{absExtFile};
        } else {
            for (String resDir : mSplitResDirs) {
                if (resDir != null && resDir.equals(absExtFile))
                    return; //如果已經加過了，就不再加了
            }
            String[] tmp = new String[mSplitResDirs.length + 1];
            System.arraycopy(mSplitResDirs, 0, tmp, 0, mSplitResDirs.length);
            tmp[tmp.length - 1] = absExtFile;
            mSplitResDirs = tmp;
            tmp = null;
        }
        Reflect.on(loadedApk).set("mSplitResDirs", mSplitResDirs);
    }

    private void modifyLoadedApkmResList(Object at, Object la) {
        Resources lares = null;
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
//            lares = la.getResources(at);
//        } else {
            lares = Reflect.on(la).get("mResources");
//        }
        if (lares == null)
            return;
        ResUtils.tryInsertAsset(lares);  //後面的 activity？ service 都用這個 loadedApk的 mRes對象？
        lares = null;
    }


    private void insertResManager() {
        //可能debug的時候提示替換成功了，某些方法也確實調進來了
        //但是 performLuancheActivity的時候，為activity創建一個 overrideConfiguration不爲null的 res時
        //就調用不到我們的 getResTop 。。。。（） 方法，雖然 ResouceManager對象顯示的是我們的res對象
        // 調不到！？！？！？！？！？？！？？？？！？！？！？！  函數簽名不對？ 還是？ invoke-vitral 的機制是？
        //所以更換方案，在 mSplitResDirs 裏注入我們的 ext
        //mSplitResDirs
        // 為hook gettoplevelresouce，在靜態代理中加入 addAssetPath
        //        resouce manager
//        ResourcesManager rm = ResourcesManager.getInstance();
//        MResM mresm = new MResM();
//        mresm.setInner(rm);
//        System.out.println("rm " + rm);
//        //1.resource manager
//        Reflect.onClass("android.app.ResourcesManager").set("sResourcesManager", mresm);
//        //2.activity thread
//        ActivityThread at = Reflect.onClass(ActivityThread.class).get("sCurrentActivityThread");
//        Reflect.on(at).set("mResourcesManager", mresm);
//        //3.this contextImpl
//        Reflect.on(getBaseContext()).set("mResourcesManager", mresm);
    }

    public void restTest1() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration cf = getResources().getConfiguration();

        try {
            Log.i("TAG", "restTest1:  1===== Resources(nam,dm,cf) + new AssetManager + noAddAssetPath  test ");
            AssetManager nam = AssetManager.class.newInstance();
            Resources mres = new Resources(nam, dm, cf);
            resTest(mres, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        getDrawable(R.drawable.ic_launcher_background)
        try {
            Log.i("TAG", "restTest1:  2===== Resources(nam,dm,cf) + new AssetManager + addAssetPath test ");
            AssetManager nam = AssetManager.class.newInstance();
            int cookie = Reflect.on(nam).call("addAssetPath", absExtFile).get();
            System.out.println("1 cookie : " + cookie);
            cookie = Reflect.on(nam).call("addAssetPath", absExtFile + "2").get();
            System.out.println("2 cookie : " + cookie);
            Resources mres2 = new Resources(nam, dm, cf);
            resTest(mres2, this);
//            MContext mContext = new MContext(getBaseContext());
//            mContext.setRes(mres2);
//            Reflect.on(getResources()).set("mAssets",nam);  //ResourcesImpl API
//            ResUtils.tryInsertAsset(getResources());
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Log.i("TAG", "restTest1:  3===== getResources() test ");
            resTest(getResources(), this);
//            MContext mContext = new MContext(getBaseContext());
//            mContext.setRes(mres2);
//            Reflect.on(getResources()).set("mAssets",nam);  //ResourcesImpl API
//            ResUtils.tryInsertAsset(getResources());
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Log.i("TAG", "restTest1:  4===== getResources() + addAssertPath()  + noUpdateConfiguration()  test ");
            ResUtils.addExtPath(getResources().getAssets());
            resTest(getResources(), this);
//            MContext mContext = new MContext(getBaseContext());
//            mContext.setRes(mres2);
//            Reflect.on(getResources()).set("mAssets",nam);  //ResourcesImpl API
//            ResUtils.tryInsertAsset(getResources());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //嘗試讓所有的layoutInflater 中的 res替換掉。
        //注意window中的 layoutInflater
//        try{
//            LayoutInflater li = LayoutInflater.from(this); // 无效，从serivce cache 中拿的
//            View am2 = li.inflate(R.layout.activity_main2, null);
//            Log.i("TAG", "am2: "+  am2);
////            th.obtainStyledAttributes()
////            com.android.internal.R.styleable.TextViewAppearance
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            Log.i("TAG", "restTest1:  5===== getResources() + UpdateConfiguration()  test ");
            getResources().updateConfiguration(getResources().getConfiguration(), getResources().getDisplayMetrics());
            resTest(getResources(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Resources testNewResNewAss(DisplayMetrics dm, Configuration cf) throws IllegalAccessException, InstantiationException {
        Log.w("TAG", "MAPP  1111 restTest1 new Res + new Assert");
        AssetManager nam = AssetManager.class.newInstance();
        int cookie = Reflect.on(nam).call("addAssetPath", absExtFile).get();
        System.out.println("1 cookie : " + cookie);
        cookie = Reflect.on(nam).call("addAssetPath", absExtFile + "2").get();
        System.out.println("2 cookie : " + cookie);
        Resources mres2 = new Resources(nam, dm, cf);
        resTest(mres2, this);

        Resources.Theme th = mres2.newTheme();
        System.out.println(th);

        return mres2;
    }


    public void tryDecodeFile() {
        try {
            long lastSize = SPHelper.getLastGenExtSize(this);
            long lastV = SPHelper.getLastGenExtVersion(getBaseContext());
            Log.i(TAG, "tryDecodeFile: lastSize:" + lastSize + "  lastV:" + lastV);

            boolean isNeedDecode = false;
            if (BuildConfig.VERSION_CODE > lastV)
                isNeedDecode = true;

            File extFile = getExtFile();
            if(extFile == null)
                isNeedDecode = true;
            else if (lastSize != extFile.length()) {
                extFile.delete();
                isNeedDecode = true;
            }

            if (isNeedDecode) {
                //正式版換成需要解密的版本
//                extractFile(instance);
                decodeFile();

                //record
                File extF = getExtFile();
                long fsize = extF.length();
                long versionCode = -1;
                versionCode = getVersionCode(this);
                Log.i(TAG, "tryDecodeFile: nowsize:" + fsize + "  nowV:" + versionCode);
                SPHelper.setLastGenExtVersion(this, versionCode);
                SPHelper.setLastGenExtSize(this, fsize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getExtFile() {
        File[] files = getFilesDir().listFiles();
        for (File f : files) {
            if (f.isDirectory())
                continue;
            if (f.getName().equals(extFileName)) {
                return f;
            }
        }
        return null;
    }

    private static long getVersionCode(Context c) throws PackageManager.NameNotFoundException {
//        PackageInfo pi = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
//        return pi.versionCode;
        return BuildConfig.VERSION_CODE;
        //TODO 兼容
    }

    private static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] b = new byte[1024 * 4];
        int len = 0;
        while ((len = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, len);
        }
    }

    private void extractFile(Context c) throws PackageManager.NameNotFoundException, IOException {
        //copy + decode
        PackageManager pm = c.getPackageManager();
        ApplicationInfo ai = pm.getApplicationInfo(c.getPackageName(), PackageManager.GET_META_DATA);
        OutputStream bofs = c.openFileOutput("ext", MODE_PRIVATE);
        InputStream is = c.getAssets().open("ext");
        copy(is, bofs);
        bofs.close();
        is.close();

        bofs = c.openFileOutput("ext2", MODE_PRIVATE);
        is = c.getAssets().open("ext");
        copy(is, bofs);
        bofs.close();
        is.close();
    }

    private long decodeFile() throws PackageManager.NameNotFoundException, IOException {
        //copy + decode
        PackageManager pm = getPackageManager();
        ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        File baseFile = new File(ai.publicSourceDir);
        OutputStream bofs = openFileOutput(extFileName, MODE_PRIVATE);
        ZipFile inZipFile = new ZipFile(baseFile);
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(baseFile));
        ZipOutputStream zipOutputStream = new ZipOutputStream(bofs);
        return copyZipContent(inZipFile, zipInputStream, zipOutputStream, new ExtResConfig());
    }

    boolean isInit = false;
    static int rv = 0;
    //需要再每个context里执行
//    public static void init3(Context context) {
//        System.out.println("init2()   context = " + context);
//        AssetManager am = context.getAssets();
////        if (!isInit) {
////            isInit = true;
//        try {
//            Method m = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
//            m.setAccessible(true);
//            Object res = m.invoke(am, context.getFilesDir().getAbsolutePath() + File.separator + "ext");
//            System.out.println("MAPP returnValue " + res);
//            if (res != null)
//                rv = (int) res;
////                mgetResourceValue = AssetManager.class.getDeclaredMethod("getResourceValue", Integer.TYPE, Integer.TYPE, TypedValue.class, Boolean.TYPE);
////                mgetResourceValue.setAccessible(true);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
////        }
////        return am;
//    }

    static long copyZipContent(ZipFile inZipFile, final ZipInputStream zis, final ZipOutputStream zos, ExtResConfig config) throws IOException {
        ZipEntry zisEntry;
        long totalSize = 0;
        Enumeration<? extends ZipEntry> entries =  inZipFile.entries();
        while(entries.hasMoreElements()){
            zisEntry = entries.nextElement();
//        while ((zisEntry = zis.getNextEntry()) != null) {
            String eName = zisEntry.getName();
            String outeName = eName;
            System.out.println("copyZipContent zisEntry.getName() " + eName);
            InputStream singleFileIs = inZipFile.getInputStream(zisEntry);

            //是否需要解密
            boolean isSkip = false;
            boolean isDoDecode = false;
            if ("resources.arsc".equals(eName)) {
                isSkip = true;
            } else if (eName != null) {
                if ((eName.startsWith(config.encResDirPath))) {
                    isDoDecode = true;   //TODO 移动位置  assets/ -> res/
                    outeName = eName.substring(config.encResDirPath.length(),eName.length() - 4);
                    Log.d(TAG, "copyZipContent:1 outeName " + outeName);
                    outeName = config.orgResDirPath + new String(EDCoder.decode(Base64.decode(outeName, Base64.DEFAULT)));
                    Log.d(TAG, "copyZipContent:2 outeName " + outeName);
                } else if (eName.equals(config.encArsc)) {
                    isDoDecode = true;
                    outeName = "resources.arsc";
                }
            }

            if (isSkip) {
                System.out.println("copyZipContent skip " + eName);
                continue;
            }

            ZipEntry zosEntry = new ZipEntry(outeName);
            zosEntry.setComment(zisEntry.getComment());
            zosEntry.setExtra(zisEntry.getExtra());
            zos.putNextEntry(zosEntry);
            //copy
            int orgFileSize = -1;
            if (isDoDecode)
                orgFileSize = StreamUtils.decodeCopy(singleFileIs, zos);
            else
                orgFileSize = StreamUtils.copy(singleFileIs, zos);
            totalSize += orgFileSize;
            System.out.println("copyZipContent eName->outeName: " + eName + "-> " + outeName + " decode:" + isDoDecode + " totalSize " + orgFileSize);
            zos.closeEntry();
        }
        zos.finish();

        return totalSize;
    }
}
