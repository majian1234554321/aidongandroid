package com.leyuan.aidong.utils;

import android.util.Log;


public final class Logger {

    /**
     * 日志级别 显示级别参考 android.util.Log的级别 配置0全部显示，配置大于7全不显示
     */
    public static final int LEVLE = 0;

    public static void i(String msg) {
        if (Urls.debug) {
            Log.i("aidong", msg);
        }
    }

    public static void v(String tag, String msg) {
        if (Urls.debug) {
            if (LEVLE <= Log.VERBOSE)
                Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (Urls.debug) {
            if (LEVLE <= Log.VERBOSE)
                Log.v(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (Urls.debug) {
            if (LEVLE <= Log.DEBUG)
                Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (Urls.debug) {
            if (LEVLE <= Log.DEBUG)
                Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (Urls.debug) {
            if (LEVLE <= Log.INFO)
                Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (Urls.debug) {
            if (LEVLE <= Log.INFO)
                Log.i(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (Urls.debug) {
            if (LEVLE <= Log.WARN)
                Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (Urls.debug) {
            if (LEVLE <= Log.WARN)
                Log.w(tag, msg, tr);
        }
    }

    public static void w(String tag, Throwable tr) {
        if (Urls.debug) {
            if (LEVLE <= Log.WARN)
                Log.w(tag, tr.getMessage(), tr);
        }
    }

    public static void e(String tag, String msg) {
        if (Urls.debug) {
            if (LEVLE <= Log.ERROR)
                Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (Urls.debug) {
            if (LEVLE <= Log.ERROR)
                Log.e(tag, msg, tr);
        }
    }

    public static void e(String tag, Throwable tr) {
        if (Urls.debug) {
            if (LEVLE <= Log.ERROR)
                Log.e(tag, tr.getMessage(), tr);
        }
    }

    public static void wtf(String tag, String msg) {
        if (Urls.debug) {
            if (LEVLE <= Log.ASSERT)
                Log.wtf(tag, msg);
        }
    }

    public static void wtf(String tag, Throwable tr) {
        if (Urls.debug) {
            if (LEVLE <= Log.ASSERT)
                Log.wtf(tag, tr);
        }
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        if (Urls.debug) {
            if (LEVLE <= Log.ASSERT)
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
