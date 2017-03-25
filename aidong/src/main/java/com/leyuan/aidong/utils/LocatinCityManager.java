package com.leyuan.aidong.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.widget.dialog.BaseDialog;
import com.leyuan.aidong.widget.dialog.ButtonCancelListener;
import com.leyuan.aidong.widget.dialog.ButtonOkListener;
import com.leyuan.aidong.widget.dialog.DialogDoubleButton;

/**
 * Created by user on 2017/3/20.
 */
public class LocatinCityManager {

    public static void checkLocationCity(Context context) {
        String localCity = App.getInstance().getLocationCity();
        boolean localCityIsOpen = false;
        if (localCity == null) {
            return;
        }
        if (SystemInfoUtils.getOpenCity(context).contains(localCity)) {
            localCityIsOpen = true;
        }
        boolean isFirstCheckLocation = SharePrefUtils.getString(context, "cityLocationLast", null) == null;
        if (isFirstCheckLocation) {
            if (localCityIsOpen) {
                App.getInstance().setSelectedCity(localCity);
            } else {
                showNoOpenDialog(context);
            }
        } else if (localCityIsOpen && !TextUtils.equals(localCity, SharePrefUtils.getString(context, "cityLocationLast", null))) {
            showSelectCityDialog(context, localCity);
        }
        SharePrefUtils.putString(context, "cityLocationLast", localCity);
    }

    private static void showSelectCityDialog(Context context, final String cityName) {
        new DialogDoubleButton(context)
                .setContentDesc("您当前定位城市为" + cityName + ",是否切换至" + cityName)
                .setBtnOkListener(new ButtonOkListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        App.getInstance().setSelectedCity(cityName);
                        dialog.dismiss();
                    }
                })
                .setBtnCancelListener(new ButtonCancelListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        dialog.dismiss();
                    }
                })

                .show();

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
