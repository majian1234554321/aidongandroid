package com.example.aidong.utils;

import android.widget.Toast;

import com.example.aidong .ui.App;

/**
 * Created by user on 2017/3/27.
 */

public class ToastGlobal {

    private static Toast toast;

    public static void showShort(String message) {
        Toast.makeText(App.context,message,Toast.LENGTH_SHORT).show();
    }

    public static void showShort(int message) {
        Toast.makeText(App.context,message,Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String message) {
        Toast.makeText(App.context,message,Toast.LENGTH_LONG).show();
    }

    public static void showLong(int message) {
        Toast.makeText(App.context,message,Toast.LENGTH_LONG).show();
    }

    public static void showShortConsecutive (String message) {
        if (toast == null) {
            toast = Toast.makeText(App.context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }

    public static void showShortConsecutive (int message) {
        if (toast == null) {
            toast = Toast.makeText(App.context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }

    public static void showLongConsecutive (String message) {
        if (toast == null) {
            toast = Toast.makeText(App.context, "", Toast.LENGTH_LONG);
        }
        toast.setText(message);
        toast.show();
    }

    public static void showLongConsecutive (int message) {
        if (toast == null) {
            toast = Toast.makeText(App.context, "", Toast.LENGTH_LONG);
        }
        toast.setText(message);
        toast.show();
    }

}
