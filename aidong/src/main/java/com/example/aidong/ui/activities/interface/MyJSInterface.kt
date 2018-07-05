package com.example.aidong.ui.activities.`interface`

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import com.example.aidong.wxapi.WXEntryActivity
import com.facebook.stetho.common.LogUtil
import com.google.gson.JsonObject
import com.example.aidong .config.ConstantUrl
import com.example.aidong .ui.App
import com.example.aidong .ui.home.activity.MapActivity
import com.example.aidong .ui.mine.activity.account.LoginActivity
import com.example.aidong .utils.DeviceManager
import com.example.aidong .utils.constant.PayType
import org.json.JSONObject


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

    @JavascriptInterface
    fun shareToWeChat(json: String?) {

        val jsonObject = JSONObject(json)
        WXEntryActivity.start(mContext, jsonObject.getString("shareTitle"), jsonObject.getString("shareDescription"), jsonObject.getString("shareThumb"), jsonObject.getString("shareUrl"), false)

    }

    @JavascriptInterface
    fun shareToFriendCircle(json:String?) {
        Log.i("TAG",json)
      val jsonObject =   JSONObject(json)
        WXEntryActivity.start(mContext, jsonObject.getString("shareTitle"), jsonObject.getString("shareDescription"), jsonObject.getString("shareThumb"), jsonObject.getString("shareUrl"), true)
    }




    fun releaseNews() {

    }

    fun hasLogined(): Boolean {
        return App.getInstance().isLogin
    }

    fun pay(payType: Int, sing: String) {

    }
}