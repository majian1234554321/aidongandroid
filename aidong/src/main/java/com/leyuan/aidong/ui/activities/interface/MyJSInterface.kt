package com.leyuan.aidong.ui.activities.`interface`

import android.content.Context
import android.webkit.JavascriptInterface
import com.facebook.stetho.common.LogUtil
import com.leyuan.aidong.ui.App
import com.leyuan.aidong.ui.home.activity.MapActivity
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity
import com.leyuan.aidong.utils.DeviceManager
import com.leyuan.aidong.utils.constant.PayType


class MyJSInterface(var mContext: Context?) {


    fun getJpushID(): String {
        return App.getInstance().getjPushId()
    }

    fun showMap(lat: Double, lng: Double) {
        if (App.getInstance().isLogin) {


//        MapActivity.start(mContext, store.getName(), store.getName(), store.getAddress(),
//                lat.toString(), lng.toString())
        } else {

        }
    }


    fun releaseNews() {

    }

    fun hasLogined(): Boolean {
        return App.getInstance().isLogin
    }

    fun pay(payType: Int, sing: String) {

    }
}