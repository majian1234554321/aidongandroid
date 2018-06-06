package com.leyuan.aidong.ui.activities.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.leyuan.aidong.R
import com.leyuan.aidong.config.UrlConfig
import com.leyuan.aidong.ui.App
import com.leyuan.aidong.ui.BaseFragment
import com.leyuan.aidong.ui.activities.`interface`.MyJSInterface
import com.leyuan.aidong.utils.GlideLoader
import com.leyuan.aidong.utils.Logger
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView

import kotlinx.android.synthetic.main.invitationfragment.*
import java.util.HashMap

class InvitationFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(inflater.context, R.layout.invitationfragment, null)
    }


    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface", "JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlideLoader.getInstance().displayDrawableGifImage(R.drawable.loading, img_loading)



        iv_back.setOnClickListener{
            (context as Activity).finish()
        }

        mWebView.clearCache(true)

        val webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true

        webSettings.allowFileAccess = true// 设置允许访问文件数据 v

        webSettings.setSupportZoom(true)

         webSettings.builtInZoomControls = true


        webSettings.domStorageEnabled = true
        webSettings.loadWithOverviewMode = true

        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS

        webSettings.databaseEnabled = true
        webSettings.useWideViewPort = true

        mWebView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress > 70) {
                    img_loading.setVisibility(View.GONE)
                }
            }

            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)

            }
        })




        Logger.i(TAG, "mWebView.loadUrl start")
        mWebView.addJavascriptInterface(MyJSInterface(context), "android")



        val map = HashMap<String, String>()
        map["mobile"] = App.getInstance().user.mobile
        mWebView.loadUrl("${UrlConfig.BASE_URL_MEMBER_CARD}activity/inviting",map)




    }

    companion object {
        fun newInstance(): InvitationFragment {

            val invitationFragment = InvitationFragment()


            return invitationFragment
        }
    }
}