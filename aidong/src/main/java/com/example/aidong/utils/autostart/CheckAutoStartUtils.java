package com.example.aidong.utils.autostart;

import android.app.Activity;
import android.content.Context;

import com.example.aidong .utils.SharePrefUtils;

/**
 * Created by user on 2017/6/8.
 */
public class CheckAutoStartUtils {
    public static boolean isNeedCheck(Context context) {
        long currentTime = System.currentTimeMillis();
        long checkInterval = 60 * 24 * 60 * 60 * 1000;
        return currentTime - SharePrefUtils.getLastCheckAutoStartTime(context) > checkInterval;
    }

    public static void skipToAutoStartView(Activity context) {
        AutoStartManager.toAutoStartView(context);
        SharePrefUtils.putCheckAutoStartTime(context);
    }
}
