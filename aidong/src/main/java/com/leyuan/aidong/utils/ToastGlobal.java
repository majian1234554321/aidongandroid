package com.leyuan.aidong.utils;

import android.widget.Toast;

import com.leyuan.aidong.ui.App;

/**
 * Created by user on 2017/3/27.
 */

public class ToastGlobal {

    private static Toast toast;

    public static void showShort(String message) {
        if (toast == null) {
            toast = Toast.makeText(App.context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }

    public static void showShort(int message) {
        if (toast == null) {
            toast = Toast.makeText(App.context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }

    public static void showLong(String message) {
        if (toast == null) {
            toast = Toast.makeText(App.context, "", Toast.LENGTH_LONG);
        }
        toast.setText(message);
        toast.show();
    }

    public static void showLong(int message) {
        if (toast == null) {
            toast = Toast.makeText(App.context, "", Toast.LENGTH_LONG);
        }
        toast.setText(message);
        toast.show();
    }



}
