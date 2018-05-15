package com.leyuan.aidong.utils;

import android.util.Log;

import com.leyuan.aidong.config.UrlConfig;


public final class Logger {


    public static void i(String msg) {
        if (UrlConfig.logo) {
            Log.i("aidong", msg);
        }
    }

    public static void v(String tag, String msg) {
        if (UrlConfig.logo) {
                Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (UrlConfig.logo) {
                Log.v(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (UrlConfig.logo) {
                Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (UrlConfig.logo) {
                Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (UrlConfig.logo) {
                Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (UrlConfig.logo) {
                Log.i(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (UrlConfig.logo) {
                Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (UrlConfig.logo) {
                Log.w(tag, msg, tr);
        }
    }

    public static void w(String tag, Throwable tr) {
        if (UrlConfig.logo) {
                Log.w(tag, tr.getMessage(), tr);
        }
    }

    public static void e(String tag, String msg) {
        if (UrlConfig.logo) {
                Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (UrlConfig.logo) {
                Log.e(tag, msg, tr);
        }
    }

    public static void e(String tag, Throwable tr) {
        if (UrlConfig.logo) {
                Log.e(tag, tr.getMessage(), tr);
        }
    }

    public static void wtf(String tag, String msg) {
        if (UrlConfig.logo) {
                Log.wtf(tag, msg);
        }
    }

    public static void wtf(String tag, Throwable tr) {
        if (UrlConfig.logo) {
                Log.wtf(tag, tr);
        }
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        if (UrlConfig.logo) {
                Logger.wtf(tag, msg, tr);
        }
    }

    public static void largeLog(String tag, String content) {
        if (content.length() > 3500) {
            Logger.i(tag, content.substring(0, 3500));
            largeLog(tag, content.substring(3500));
        } else {
            Logger.i(tag, content);
        }
    }
}
