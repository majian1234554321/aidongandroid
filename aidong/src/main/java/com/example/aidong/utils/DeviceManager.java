package com.example.aidong.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by user on 2017/1/4.
 */

public class DeviceManager {

    //手机号码
    public static String getLine1Number(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getLine1Number();
    }

    //deviceId
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getDeviceId();
    }

    //运营商名称
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getNetworkOperatorName();
    }

    //sim卡序列号
    public static String getSimSerialNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getSimSerialNumber();
    }

    //IMSI
    public static String getSubscriberId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getSubscriberId();
    }

    //sim卡所在国家
    public static String getNetworkCountryIso(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getNetworkCountryIso();
    }

    //运营商编号。
    public static String getNetworkOperator(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return tm.getNetworkOperator();
    }

    //android 获取当前手机型号
    public static String getPhoneModel(Context context) {
        Build bd = new Build();
        return bd.MODEL;
    }

    //android 获取当前手机品牌
    public static String getPhoneProduct(Context context) {
        Build bd = new Build();
        return bd.PRODUCT;
    }

    /**
     * 获取手机Android API等级（22、23 ...）
     *
     * @return
     */
    public static int getBuildLevel() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     *
     * @return
     */
    public static String getBuildVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取当前App进程的id
     *
     * @return
     */
    public static int getAppProcessId() {
        return android.os.Process.myPid();
    }


    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getPhoneBrand() {
        return Build.BRAND;
    }

    //android 获取屏幕分辩率
    public static String getMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        int h = dm.heightPixels;
        int w = dm.widthPixels;
        return h + "*" + w;
    }

    //android获取当前时区
    public static String getTimeZone(Context context) {
        TimeZone tz = TimeZone.getDefault();
        String s = tz.getID();
        System.out.println(s);
        return s;
    }

    //android获取当前日期时间
//    public static String getDateAndTime(Context context) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
//        String str = formatter.format(curDate);
//        return str;
//    }

    //获取手机系统语言 0中文简体 1其它
    public static String getLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return "0";
        else
            return "1";
    }

    /**
     * 获取网络类型
     */
//    public static int getNetWorkType(Context context) {
//        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
//
//        if (networkInfo != null && networkInfo.isConnected()) {
//            String type = networkInfo.getTypeName();
//
//            if (type.equalsIgnoreCase("WIFI")) {
////                return SyncStateContract.Constants.NETTYPE_WIFI;
//            } else if (type.equalsIgnoreCase("MOBILE")) {
//                NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//                if (mobileInfo != null) {
//                    switch (mobileInfo.getType()) {
//                        case ConnectivityManager.TYPE_MOBILE:// 手机网络
//                            switch (mobileInfo.getSubtype()) {
//                                case TelephonyManager.NETWORK_TYPE_UMTS:
//                                case TelephonyManager.NETWORK_TYPE_EVDO_0:
//                                case TelephonyManager.NETWORK_TYPE_EVDO_A:
//                                case TelephonyManager.NETWORK_TYPE_HSDPA:
//                                case TelephonyManager.NETWORK_TYPE_HSUPA:
//                                case TelephonyManager.NETWORK_TYPE_HSPA:
//                                case TelephonyManager.NETWORK_TYPE_EVDO_B:
//                                case TelephonyManager.NETWORK_TYPE_EHRPD:
//                                case TelephonyManager.NETWORK_TYPE_HSPAP:
////                                    return AVConstants.NETTYPE_3G;
//                                case TelephonyManager.NETWORK_TYPE_CDMA:
//                                case TelephonyManager.NETWORK_TYPE_GPRS:
//                                case TelephonyManager.NETWORK_TYPE_EDGE:
//                                case TelephonyManager.NETWORK_TYPE_1xRTT:
//                                case TelephonyManager.NETWORK_TYPE_IDEN:
////                                    return AVConstants.NETTYPE_2G;
//                                case TelephonyManager.NETWORK_TYPE_LTE:
////                                    return AVConstants.NETTYPE_4G;
//                                default:
////                                    return AVConstants.NETTYPE_NONE;
//                            }
//                    }
//                }
//            }
//        }
//    }

//        return AVConstants.NETTYPE_NONE;
//}


    /*
     * 网络连接是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
