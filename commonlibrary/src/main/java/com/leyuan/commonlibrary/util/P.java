package com.leyuan.commonlibrary.util;

import android.util.Log;

/**
 * Created by hashbaz on 16/2/29.
 */
public class P {
    private static boolean debug = true;
    private static final String SHOOT_ME = "muabaobao";

    private enum Level {

        DEBUG,

        ERROR,

        INFO,

        WARNING,
    }

    public static void i(String tag, String info) {
        if (!debug) {
            return;
        }
        log(Level.INFO, tag, info);
    }

    public static void d(String tag, String info) {
        if (!debug) {
            return;
        }
        log(Level.DEBUG, tag, info);
    }

    public static void e(String tag, String info) {
        if (!debug) {
            return;
        }
        log(Level.ERROR, tag, info);
    }

    public static void w(String tag, String info) {
        if (!debug) {
            return;
        }
        log(Level.WARNING, tag, info);
    }

    private static void log(Level level, String tag, String info) {
        StringBuilder builder = new StringBuilder();
        builder.append(tag);
        builder.append(": ");
        builder.append(info);

        switch (level) {
            case DEBUG:
                Log.d(SHOOT_ME, builder.toString());
                break;

            case INFO:
                Log.i(SHOOT_ME, builder.toString());
                break;

            case ERROR:
                Log.e(SHOOT_ME, builder.toString());
                break;

            case WARNING:
                Log.w(SHOOT_ME, builder.toString());
                break;

            default:
                Log.i(SHOOT_ME, builder.toString());
                break;
        }
    }
}
