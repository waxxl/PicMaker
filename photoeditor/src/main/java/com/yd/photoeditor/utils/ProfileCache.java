package com.yd.photoeditor.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ProfileCache {
    public static final String GCM_REGISTRATION_TOKEN = "gcmToken";
    public static final String KEY_COINS_AMOUNT = "Key_Coins_Amount";
    public static final String KEY_DISPLAY_NAME = "Key_Profile_Display_Name";
    private static final String KEY_EMAIL_ADDRESS = "Key_Profile_Email_Address";
    public static final String KEY_FOLLOWER_NUM = "Key_Follower_Num";
    public static final String KEY_FOLLOWING_NUM = "Key_Following_Num";
    public static final String KEY_PHOTO_URL = "Key_Profile_Photo_Url";
    public static final String KEY_USER_ID = "Key_User_Id";
    public static final String KEY_USER_NAME = "userName";
    private static final String PREF_ACCESS_TOKEN = "Pref_accessToken";
    private static final String USER_INFO_REFERENCE = "USER_INFO_REFERENCE";



    public static void saveToken(Context context, String str) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_ACCESS_TOKEN, str).commit();
    }

    public static String getToken(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_ACCESS_TOKEN, "");
    }

}
