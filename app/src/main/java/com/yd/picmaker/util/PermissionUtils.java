package com.yd.picmaker.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import com.yd.picmaker.PicMakerApplication;
import com.yd.picmaker.R;

import java.util.ArrayList;
import java.util.Iterator;

public class PermissionUtils {
    private static final String PERMISSION_PREF_NAME = "permissionPref";
    public static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 7889;
    private static final String REQUEST_SYSTEM_ALERT_WINDOW_PERMISSION_KEY = "REQUEST_SYSTEM_ALERT_WINDOW_PERMISSION";
    public static ArrayList<Permission> sPermissions;

    public static class Permission {
        public String displayName;
        public boolean isGranted;
        public String permissionName;

        public Permission(String str, String str2) {
            this.permissionName = str;
            this.displayName = str2;
        }

        public Permission(String str, String str2, boolean z) {
            this.permissionName = str;
            this.displayName = str2;
            this.isGranted = z;
        }
    }

    public static void checkAndRequestSystemAlertWindowPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23 && activity.getSharedPreferences(PERMISSION_PREF_NAME, 0).getBoolean(REQUEST_SYSTEM_ALERT_WINDOW_PERMISSION_KEY, false)) {
            requestSystemAlertWindowPermission(activity);
        }
    }

    public static void setRequestingSystemAlertWindowPermission(Context context, boolean z) {
        if (Build.VERSION.SDK_INT >= 23) {
            context.getSharedPreferences(PERMISSION_PREF_NAME, 0).edit().putBoolean(REQUEST_SYSTEM_ALERT_WINDOW_PERMISSION_KEY, z).commit();
        }
    }

    public static boolean requestSystemAlertWindowPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!(activity.checkSelfPermission("android.permission.SYSTEM_ALERT_WINDOW") == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                try {
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    public static void createPermissionsCheckListIfNeed() {
        if (Build.VERSION.SDK_INT >= 23) {
            Context appContext = PicMakerApplication.getContext();
            ArrayList<Permission> arrayList = sPermissions;
            if (arrayList == null || arrayList.size() == 0) {
                sPermissions = new ArrayList<>();
                sPermissions.add(new Permission("android.permission.WRITE_EXTERNAL_STORAGE", appContext.getString(R.string.permission_write_ex_storage)));
            }
            Iterator<Permission> it = sPermissions.iterator();
            while (it.hasNext()) {
                Permission next = it.next();
                next.isGranted = appContext.checkSelfPermission(next.permissionName) == PackageManager.PERMISSION_GRANTED;
            }
        }
    }

    public static boolean checkPermissionsGranted(Activity activity) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        createPermissionsCheckListIfNeed();
        String[] packageNameNotGrantedArray = getPackageNameNotGrantedArray(sPermissions);
        if (packageNameNotGrantedArray == null || packageNameNotGrantedArray.length <= 0) {
            return true;
        }
        String string = activity.getString(R.string.permission_guide);
        Iterator<Permission> it = sPermissions.iterator();
        while (it.hasNext()) {
            Permission next = it.next();
            if (!next.isGranted) {
                string = string + "\n    " + next.displayName;
            }
        }
        activity.requestPermissions(PermissionUtils.getPackageNameNotGrantedArray(PermissionUtils.sPermissions), PermissionUtils.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        return false;
    }

    /* access modifiers changed from: private */
    public static String[] getPackageNameNotGrantedArray(ArrayList<Permission> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator<Permission> it = arrayList.iterator();
        while (it.hasNext()) {
            Permission next = it.next();
            if (!next.isGranted) {
                arrayList2.add(next.permissionName);
            }
        }
        return (String[]) arrayList2.toArray(new String[arrayList2.size()]);
    }

    public static boolean isGrantedAllPermissions(Activity activity, int i, String[] strArr, int[] iArr) {
        for (int i2 : iArr) {
            if (i2 != 0) {
                Toast.makeText(activity, activity.getString(R.string.permission_denied_and_guide_to_setting), Toast.LENGTH_LONG).show();
                goAppSetting(activity);
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static void goAppSetting(Activity activity) {
        activity.startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", activity.getPackageName(), null)));
        activity.finish();
    }

    public static void clearCheckList() {
        ArrayList<Permission> arrayList = sPermissions;
        if (arrayList != null) {
            arrayList.clear();
            sPermissions = null;
        }
    }
}
