package com.leyuan.aidong.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.leyuan.aidong.entity.user.MineInfoBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.presenter.impl.MineInfoPresenterImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.SystemPresentImpl;
import com.leyuan.aidong.ui.mvp.view.MineInfoView;
import com.leyuan.aidong.ui.mvp.view.SystemView;
import com.leyuan.aidong.widget.dialog.BaseDialog;
import com.leyuan.aidong.widget.dialog.ButtonCancelListener;
import com.leyuan.aidong.widget.dialog.ButtonOkListener;
import com.leyuan.aidong.widget.dialog.DialogDoubleButton;

import java.util.List;

import static com.leyuan.aidong.ui.App.context;
import static com.leyuan.aidong.utils.SystemInfoUtils.getOpenCity;

/**
 * Created by user on 2017/3/20.
 */
public class LocatinCityManager {


    public static void checkLocationCityFirstly(String cityLocation) {
        boolean isFirstCheckLocation = SharePrefUtils.getString(context, "cityLocationLast", null) == null;
        if (SharePrefUtils.getString(context, "cityLocationLast", null) == null) {
            List<String> openCitys = SystemInfoUtils.getOpenCity(context);
            if (openCitys != null && openCitys.contains(cityLocation)) {
                App.getInstance().setSelectedCity(cityLocation);
            }
        }
    }

    public static void checkLocationCity(Context context) {
        String localCity = App.getInstance().getLocationCity();
        boolean localCityIsOpen = false;
        if (localCity == null) {
            return;
        }
        if (getOpenCity(context).contains(localCity)) {
            localCityIsOpen = true;
        }
        boolean isFirstCheckLocation = SharePrefUtils.getString(context, "cityLocationLast", null) == null;
        if (isFirstCheckLocation) {
            if (localCityIsOpen) {
                setSelectedCityAndRefreshData(localCity, context);
            } else {
                showNoOpenDialog(context);
            }
        } else if (localCityIsOpen && !TextUtils.equals(localCity, SharePrefUtils.getString(context, "cityLocationLast", null))
                && !TextUtils.equals(localCity, App.getInstance().getSelectedCity())) {
            showSelectCityDialog(context, localCity);
        }
        SharePrefUtils.putString(context, "cityLocationLast", localCity);
    }

    private static void setSelectedCityAndRefreshData(String localCity, final Context context) {
        if (TextUtils.equals(App.getInstance().getSelectedCity(), localCity)) return;


        App.getInstance().setSelectedCity(localCity);
        final MineInfoPresenterImpl presenter = new MineInfoPresenterImpl(context, new MineInfoView() {
            @Override
            public void onGetMineInfo(MineInfoBean mineInfoBean) {
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_SELECTED_CITY));
            }

        });
        final SystemPresentImpl systemPresent = new SystemPresentImpl(context);
        systemPresent.setSystemView(new SystemView() {
            @Override
            public void onGetSystemConfiguration(boolean b) {
                if (App.getInstance().isLogin()) {
                    presenter.getMineInfo();
                } else {
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_SELECTED_CITY));
                }
            }
        });

        systemPresent.getSystemInfoSelected(Constant.OS);
    }


    private static void showSelectCityDialog(final Context context, final String cityName) {
        new DialogDoubleButton(context)
                .setContentDesc("您当前定位城市为" + cityName + ",是否切换至" + cityName)
                .setBtnOkListener(new ButtonOkListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        setSelectedCityAndRefreshData(cityName,context);
//                        App.getInstance().setSelectedCity(cityName);
                        dialog.dismiss();
                    }
                })
                .setBtnCancelListener(new ButtonCancelListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();

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
