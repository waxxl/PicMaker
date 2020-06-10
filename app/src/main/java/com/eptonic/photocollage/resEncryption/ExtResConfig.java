package com.eptonic.photocollage.resEncryption;


import com.eptonic.photocollage.BuildConfig;

public class ExtResConfig {
    public String orgAssetsDir = "assets";
    public String orgResDir = "res";
    public String orgResDirPath = orgResDir+"/";

    public String encArscFn = "r96";//BuildConfig.RE;
    public String encArsc = orgAssetsDir + "/" + encArscFn;

    public String encResDir = orgAssetsDir + "/" + "r93";
    public String encResDirPath = encResDir + "/";
    public static byte[] resKey = BuildConfig.RESKEY.getBytes();//BuildConfig.RK;
}
