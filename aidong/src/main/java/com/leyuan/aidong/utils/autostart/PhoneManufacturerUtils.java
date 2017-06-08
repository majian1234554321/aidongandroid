package com.leyuan.aidong.utils.autostart;

import android.os.Build;

import com.facebook.stetho.common.LogUtil;

/**
 * Created by user on 2017/6/8.
 */
public class PhoneManufacturerUtils {

    public static String getManufacturer() {
        if (Build.MANUFACTURER == null)
            return "";

        LogUtil.i("phone getManufacturer= ", Build.MANUFACTURER.toString());
        return Build.MANUFACTURER.trim().toLowerCase();
    }

    public static boolean isXiaoMi() {
        return getManufacturer().contains("mi");
    }

    public static boolean isHuaWei() {
        return getManufacturer().contains("wei");
    }

    public static boolean isSamSung() {
        return getManufacturer().contains("sam");
    }

    public static boolean isMeiZhu() {
        return getManufacturer().contains("mei");
    }

    public static boolean isVivo() {
        return getManufacturer().contains("vivo");
    }

    public static boolean isOppo() {
        return getManufacturer().contains("oppo");
    }

    public static boolean isLetv() {
        return getManufacturer().contains("letv");
    }
}
