package com.leyuan.aidong.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 与网络相关的工具类
 */
public class NetworkUtil {
    private NetworkUtil()
    {
		/* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context)
    {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity)
        {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected())
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }


    /**
     * 判断是不是2/3G网络状态
     *
     * @param paramContext
     * @return
     */
    public static boolean isMobile(Context paramContext) {
        return "1".equals(getNetType(paramContext)[0]);
    }

    /**
     * 网络是否可用
     *
     * @param paramContext
     * @return
     */
    public static boolean isNetAvailable(Context paramContext) {
        if ("1".equals(getNetType(paramContext)[0]) || "2".equals(getNetType(paramContext)[0])) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前网络状态 返回2代表wifi,1代表2G/3G
     *
     * @param paramContext
     * @return
     */
    public static String[] getNetType(Context paramContext) {
        String[] arrayOfString = {"Unknown", "Unknown"};
        PackageManager localPackageManager = paramContext.getPackageManager();
        if (localPackageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE", paramContext.getPackageName()) != 0) {
            arrayOfString[0] = "Unknown";
            return arrayOfString;
        }

        ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext.getSystemService("connectivity");
        if (localConnectivityManager == null) {
            arrayOfString[0] = "Unknown";
            return arrayOfString;
        }

        NetworkInfo localNetworkInfo1 = localConnectivityManager.getNetworkInfo(1);
        if (localNetworkInfo1 != null && localNetworkInfo1.getState() == NetworkInfo.State.CONNECTED) {
            arrayOfString[0] = "2";
            return arrayOfString;
        }

        NetworkInfo localNetworkInfo2 = localConnectivityManager.getNetworkInfo(0);
        if (localNetworkInfo2 != null && localNetworkInfo2.getState() == NetworkInfo.State.CONNECTED) {
            arrayOfString[0] = "1";
            arrayOfString[1] = localNetworkInfo2.getSubtypeName();
            return arrayOfString;
        }

        return arrayOfString;
    }
}
