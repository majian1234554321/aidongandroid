package com.leyuan.aidong.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 */

public class VersionManager {

    public static String getVersionName(Context mContext) {
        String version = "5.0.0";
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            version = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getVersionCode(Context context) {
        String versionCode = "1";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = String.valueOf(packInfo.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static boolean shouldUpdate(String newVersion, Context context) {
        String oldVersion = getVersionName(context);

        if (!TextUtils.isEmpty(newVersion) && !TextUtils.isEmpty(oldVersion)) {
            int[] oldNum = stringToInteger(oldVersion.split("\\."));
            int[] newNum = stringToInteger(newVersion.split("\\."));

            if (newNum.length > oldNum.length) {
                return true;
            }
            if (newNum.length < oldNum.length) {
                return false;
            }
            if (newNum.length >= 4 && oldNum.length >= 4) {
                if (newNum[3] > oldNum[3]) {
                    return true;
                } else {
                    return false;
                }
            }
            if (newNum.length >= 3 && oldNum.length >= 3) {
                if (newNum[0] > oldNum[0]) {
                    return true;
                } else if (newNum[0] == oldNum[0]) {
                    if (newNum[1] > oldNum[1]) {
                        return true;
                    } else if (newNum[1] == oldNum[1]) {
                        if (newNum[2] > oldNum[2]) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private static int[] stringToInteger(String[] version) {
        int[] verNum = new int[version.length];

        for (int i = 0; i < verNum.length; i++) {
            try {
                verNum[i] = Integer.parseInt(version[i]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
//			Log.i("mainactivity", "" +verNum[i]);
        }

        return verNum;
    }

    public static boolean mustUpdate(String newVersion, Context context) {
        String oldVersion = getVersionName(context);

        if (!TextUtils.isEmpty(newVersion) && !TextUtils.isEmpty(oldVersion)) {
            int[] oldNum = stringToInteger(oldVersion.split("\\."));
            int[] newNum = stringToInteger(newVersion.split("\\."));

            if (newNum[0] > oldNum[0])
                return true;
            if (newNum[1] - oldNum[1] > 1)
                return true;
        }
        return false;
    }
}
