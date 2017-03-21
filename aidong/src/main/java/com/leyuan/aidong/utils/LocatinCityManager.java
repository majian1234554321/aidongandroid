package com.leyuan.aidong.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.leyuan.aidong.ui.App;

/**
 * Created by user on 2017/3/20.
 */
public class LocatinCityManager {

    public static void checkLocationCity(Context context) {
        String localCity = App.getInstance().getLocationCity();
        String openCity = null;
        if (localCity == null) {
            return;
        }
        if (SystemInfoUtils.getOpenCity(context).contains(localCity)) {
            openCity = localCity;
        }
        boolean isFirstCheckLocation = SharePrefUtils.getBoolean(context, "isFirstCheckLocation", true);
        if (isFirstCheckLocation) {
            if (TextUtils.isEmpty(openCity)) {
                showNoOpenDialog(context);
            } else {
                App.getInstance().setSelectedCity(openCity);
            }
            SharePrefUtils.putBoolean(context, "isFirstCheckLocation", false);
        }
//        else if (openCity != null && !TextUtils.equals(openCity, App.getInstance().getSelectedCity())
//                && !TextUtils.equals(localCity, ))

    }

    private static void showNoOpenDialog(Context context) {
        new AlertDialog.Builder(context)
                .setMessage("您所在的城市尚未开通,系统将默认切换至上海")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.getInstance().setSelectedCity(Constant.DEFAULT_CITY);
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

}
