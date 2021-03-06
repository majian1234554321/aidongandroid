package com.example.aidong.ui.activities.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import com.example.aidong.R
import com.example.aidong.entity.BaseBean
import com.example.aidong.module.photopicker.boxing.Boxing
import com.example.aidong.module.photopicker.boxing.model.entity.BaseMedia
import com.example.aidong.module.photopicker.boxing.model.entity.impl.VideoMedia
import com.example.aidong.ui.App

import com.example.aidong.ui.BaseFragment
import com.example.aidong.ui.activities.`interface`.MyJSInterface
import com.example.aidong.ui.discover.activity.PublishDynamicActivity
import com.example.aidong.ui.mvp.presenter.impl.FollowPresentImpl
import com.example.aidong.ui.mvp.view.FollowView
import com.example.aidong.utils.Constant
import com.example.aidong.utils.Constant.REQUEST_PUBLISH_DYNAMIC
import com.example.aidong.utils.FormatUtil
import com.example.aidong.utils.GlideLoader
import com.example.aidong.utils.Logger
import com.iknow.android.TrimmerActivity
import com.iknow.android.utils.TrimVideoUtil


import kotlinx.android.synthetic.main.invitationfragment2.*


class DetailsActivityH5Fragment : BaseFragment(), FollowView {
    override fun addFollowResult(baseBean: BaseBean<*>?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        //ToastGlobal.showShortConsecutive(baseBean?.getMessage())
        if (baseBean?.status == 1) {
            mWebView.loadUrl("javascript:followCallback(1)")
        } else {
            mWebView.loadUrl("javascript:followCallback(0)")
        }


    }

    override fun cancelFollowResult(baseBean: BaseBean<*>?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // ToastGlobal.showShortConsecutive(baseBean?.getMessage())

        if (baseBean?.status == 1) {
            mWebView.loadUrl("javascript:followCallback(0)")
        } else {
            mWebView.loadUrl("javascript:followCallback(1)")
        }
    }


    val args = "参数"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(inflater.context, R.layout.invitationfragment2, null)
    }

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val url = arguments?.getString("id")
        GlideLoader.getInstance().displayDrawableGifImage(R.drawable.loading, img_loading)

        mWebView.clearCache(true)

        val webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true

        webSettings.allowFileAccess = true// 设置允许访问文件数据 v


        webSettings.domStorageEnabled = true


        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN

        webSettings.setUserAgentString(System.getProperty("http.agent"));
        //  webSettings.defaultZoom = WebSettings.ZoomDensity.CLOSE

        webSettings.useWideViewPort = true;//设定支持viewport
        webSettings.loadWithOverviewMode = true;   //自适应屏幕
        webSettings.builtInZoomControls = true;
        webSettings.displayZoomControls = false;
        webSettings.setSupportZoom(false);//设定支持缩放
        // webSettings.textSize = WebSettings.TextSize.NORMAL;
        webSettings.textZoom = 100

         mWebView.setInitialScale(25)

        // mWebView.setIn

        webSettings.databaseEnabled = true







		// User settings
        webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setUseWideViewPort(true);//关键点

		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

		webSettings.setDisplayZoomControls(false);
		webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
		webSettings.setAllowFileAccess(true); // 允许访问文件
		webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
		webSettings.setSupportZoom(true); // 支持缩放

		webSettings.setLoadWithOverviewMode(true);

		val metrics =  DisplayMetrics()
		  activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		  val mDensity = metrics.densityDpi;
		  Log.d("maomao", "densityDpi = " + mDensity);
		  if (mDensity == 240) {
		   webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		  } else if (mDensity == 160) {
		     webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
		  } else if(mDensity == 120) {
		   webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
		  }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
		   webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		  }else if (mDensity == DisplayMetrics.DENSITY_TV){
		   webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		  }else{
              webSettings.setTextSize(WebSettings.TextSize.SMALLER);
		  }


		/**
		 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
		 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
		 */
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING;






        mWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress > 30 && rl != null) {
                    rl.visibility = View.GONE
                }
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
            }


        }



        mWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)


//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    webView.evaluateJavascript("javascript:jpushId('" + App.getInstance().getjPushId() + "')", { value -> LogUtil.i(TAG, "jpushId onReceiveValue = $value") })
//                } else {
//                    webView.loadUrl("javascript:jpushId('" + App.getInstance().getjPushId() + "')")
//                }
                //   mWebView.loadUrl("javascript:followCallback(1)")

            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)

                Log.i("TAGSSSS", error.toString())
            }
        }


        // webView.loadUrl(url)


        if (App.getInstance().isLogin) {
            mWebView.loadUrl(url?.plus("?token=${App.getInstance().token}"))
            //mWebView.loadUrl("http://192.168.0.7:3002/market/campaigns/940?token=${App.getInstance().token}")
        } else {
            mWebView.loadUrl(url)
            //  mWebView.loadUrl("http://192.168.0.7:3002/market/campaigns/940?token=${App.getInstance().token}")
        }

        mWebView.addJavascriptInterface(MyJSInterface(context, mWebView, url, this), "android")


    }


    companion object {
        fun newInstance(id: String?): DetailsActivityH5Fragment {
            val detailsActivityH5Fragment = DetailsActivityH5Fragment()


            val bundle = Bundle()
            bundle.putString("id", id)
            detailsActivityH5Fragment.arguments = bundle


            return detailsActivityH5Fragment
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (mWebView != null) {
            mWebView.destroy()
        }
    }


    lateinit var selectedMedia: ArrayList<BaseMedia>

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constant.REQUEST_SELECT_PHOTO && data != null) {
            PublishDynamicActivity.startForResult(this, requestCode == Constant.REQUEST_SELECT_PHOTO,
                    Boxing.getResult(data), Constant.REQUEST_PUBLISH_DYNAMIC)

        } else if (requestCode == Constant.REQUEST_SELECT_VIDEO && data != null) {
            selectedMedia = Boxing.getResult(data) as ArrayList<BaseMedia>
            if (selectedMedia.size > 0) {
                var duration = TrimVideoUtil.VIDEO_MAX_DURATION

                if (selectedMedia.get(0) is VideoMedia) {
                    val media = selectedMedia[0] as VideoMedia
                    duration = (FormatUtil.parseLong(media.getmDuration()) / 1000 + 1).toInt()
                    Logger.i("TrimmerActivity", "onActivityResult media.getDuration() = " + media.duration)
                }
                Logger.i("TrimmerActivity", "onActivityResult  durantion = $duration")

                TrimmerActivity.startForResult(this, selectedMedia[0].path, duration, Constant.REQUEST_VIDEO_TRIMMER)

            }

        } else if (requestCode == Constant.REQUEST_VIDEO_TRIMMER && data != null) run {
            Logger.i("contest video ", "requestCode == Constant.REQUEST_VIDEO_TRIMMER = ")
            if (selectedMedia.isNotEmpty()) {
                selectedMedia[0].path = data.getStringExtra(Constant.VIDEO_PATH)
                PublishDynamicActivity.startForResult(DetailsActivityH5Fragment@ this, requestCode == Constant.REQUEST_SELECT_PHOTO,
                        selectedMedia, REQUEST_PUBLISH_DYNAMIC)
            }
        }
        else if (requestCode == REQUEST_PUBLISH_DYNAMIC) {
            // Toast.makeText(activity, "发布成功了继续下一步动作调用h5方法或者重新加载页面", Toast.LENGTH_LONG).show()
        }
    }


}