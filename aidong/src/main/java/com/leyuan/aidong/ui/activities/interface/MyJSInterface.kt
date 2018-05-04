package com.leyuan.aidong.ui.activities.`interface`

import android.content.Context
import android.webkit.JavascriptInterface
import com.facebook.stetho.common.LogUtil
import com.leyuan.aidong.ui.App
import com.leyuan.aidong.utils.DeviceManager
import com.leyuan.aidong.utils.constant.PayType


class MyJSInterface(var mContext: Context?) {


    fun getJpushID(): String {
        return App.getInstance().getjPushId()
    }

    fun showMap(lat: Double, lng: Double) {

    }


    fun releaseNews() {

    }

    fun hasLogined(): Boolean {
        return App.getInstance().isLogin
    }

    fun pay(payType: Int, sing: String) {

    }
}