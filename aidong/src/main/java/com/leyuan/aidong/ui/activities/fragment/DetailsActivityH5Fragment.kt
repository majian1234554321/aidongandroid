package com.leyuan.aidong.ui.activities.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.facebook.stetho.common.LogUtil
import com.leyuan.aidong.R
import com.leyuan.aidong.ui.App

import com.leyuan.aidong.ui.BaseFragment
import com.leyuan.aidong.ui.activities.`interface`.MyJSInterface

import kotlinx.android.synthetic.main.detailsactivityh5fragment.*


class DetailsActivityH5Fragment : BaseFragment() {


    val args = "参数"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(inflater.context, R.layout.detailsactivityh5fragment, null)
    }

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString("url")


        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true

        webSettings.allowFileAccess = true// 设置允许访问文件数据 v

        webSettings.setSupportZoom(false)

        webSettings.builtInZoomControls = false
        //        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.domStorageEnabled = true


        webSettings.databaseEnabled = true
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
            }
        }



        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript("javascript:jpushId('" + App.getInstance().getjPushId() + "')", { value -> LogUtil.i(TAG, "jpushId onReceiveValue = $value") })
                } else {
                    webView.loadUrl("javascript:jpushId('" + App.getInstance().getjPushId() + "')")
                }


                webView.loadUrl("javascript:sendMessage('$args')")

            }
        }



        webView.addJavascriptInterface(MyJSInterface(context), "android")
        webView.loadUrl(url)
    }


    companion object {
        fun newInstance(url: String): DetailsActivityH5Fragment {
            val detailsActivityH5Fragment = DetailsActivityH5Fragment()
            val arguments = detailsActivityH5Fragment.arguments
            arguments?.putString("url", url)
            return detailsActivityH5Fragment
        }
    }
}