package com.example.aidong.ui.activities.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

import com.example.aidong.R
import com.example.aidong.config.UrlConfig
import com.example.aidong.entity.BaseBean
import com.example.aidong.ui.App
import com.example.aidong.ui.BaseFragment
import com.example.aidong.ui.activities.`interface`.MyJSInterface
import com.example.aidong.ui.mvp.presenter.impl.FollowPresentImpl
import com.example.aidong.ui.mvp.view.FollowView
import com.example.aidong.utils.GlideLoader
import com.example.aidong.utils.Logger

import kotlinx.android.synthetic.main.invitationfragment.*
import java.util.HashMap

class InvitationFragment : BaseFragment() {
//    override fun cancelFollowResult(baseBean: BaseBean<*>?) {
//       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun addFollowResult(baseBean: BaseBean<*>?) {
//
//
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(inflater.context, R.layout.invitationfragment, null)
    }


    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface", "JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlideLoader.getInstance().displayDrawableGifImage(R.drawable.loading, img_loading)



        iv_back.setOnClickListener {
            (context as Activity).finish()
        }

        mWebView.clearCache(true)

        val webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true

        webSettings.allowFileAccess = true// 设置允许访问文件数据 v

        webSettings.setSupportZoom(false)

        webSettings.builtInZoomControls = false

        webSettings.domStorageEnabled = true
        webSettings.loadWithOverviewMode = true

        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN

        webSettings.databaseEnabled = true
        webSettings.useWideViewPort = true

        mWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress > 70 && ll != null) {
                    ll.visibility = View.GONE
                }
            }

            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)

                if (!TextUtils.isEmpty(title) && tv_title != null) {
                    tv_title.text = title
                }
            }
        }


        val map = HashMap<String, String>()
        map["mobile"] = App.getInstance().user.mobile
        mWebView.loadUrl("${UrlConfig.BASE_URL_MEMBER_CARD}activity/inviting", map)


        mWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                mWebView.loadUrl("javascript:var imgs=document.getElementsByClassName('main-box');for(var i=0;i<imgs.length;i++){imgs[i].style.width='100%';};void(0);")
                // mWebView.loadUrl("javascript:var width=document.getElementsByClassName('bigbox');width.style.width='640px';};void(0);")
                // mWebView.loadUrl("javascript:var imgs=document.getElementsByClassName('bigbox');for(var i=0;i<imgs.length;i++){imgs[i].style.width='100%';};void(0);")

                // mWebView.loadUrl("javascript:var imgs=document.getElementsByClassName('bigbox');for(var i=0;i<imgs.length;i++){imgs[i].style.width='100%';};void(0);")
            }
        }
        Logger.i(TAG, "mWebView.loadUrl start")
        mWebView.addJavascriptInterface(MyJSInterface(context), "android")

    }

    companion object {
        fun newInstance(): InvitationFragment {

            val invitationFragment = InvitationFragment()


            return invitationFragment
        }
    }
}