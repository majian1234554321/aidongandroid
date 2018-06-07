package com.example.aidong.utils;

import android.util.Log;

import com.example.aidong .config.UrlConfig;

/**
 * Log统一管理类
 */
public class LogAidong {

    private LogAidong() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static final String TAG = "aidong";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (UrlConfig.debug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (UrlConfig.debug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (UrlConfig.debug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (UrlConfig.debug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (UrlConfig.debug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (UrlConfig.debug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (UrlConfig.debug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (UrlConfig.debug)
            Log.i(tag, msg);
    }
}