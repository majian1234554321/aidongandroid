package com.leyuan.aidong.ui.home.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.WebViewActivity;
import com.leyuan.aidong.ui.discover.activity.VenuesDetailActivity;
import com.leyuan.aidong.ui.home.activity.ActivityCircleDetailActivity;
import com.leyuan.aidong.ui.home.activity.CourseListActivityNew;
import com.leyuan.aidong.ui.home.activity.GoodsBrandRecommendActivity;
import com.leyuan.aidong.ui.home.activity.GoodsDetailActivity;
import com.leyuan.aidong.ui.home.activity.GoodsListActivity;
import com.leyuan.aidong.ui.video.activity.VideoDetailActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DeviceManager;
import com.leyuan.aidong.utils.FormatUtil;


/**
 * 商城
 * Created by song on 2017/4/12.
 */
public class StoreHtmlFiveFragment extends BaseFragment {

    BroadcastReceiver selectCityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    };
    private WebView mWebView;
    private boolean isFirst;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_SELECTED_CITY);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selectCityReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_html_five, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWebView(view);
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(View view) {
        mWebView = (WebView) view.findViewById(R.id.webView);

        mWebView.clearCache(true);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据 v

        webSettings.setSupportZoom(true);

        webSettings.setBuiltInZoomControls(true);
        //        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.setDomStorageEnabled(true);

        webSettings.setDatabaseEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @SuppressLint("AddJavascriptInterface")
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                mWebView.addJavascriptInterface(new MyJSInterface(getContext()), "android");

                if (isFirst) {
                    isFirst = false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                        mWebView.evaluateJavascript("javascript:jpushId('" + App.getInstance().getJPushId() + "')", new ValueCallback<String>() {
//                            @Override
//                            public void onReceiveValue(String value) {
//                                LogUtil.i(TAG, "jpushId onReceiveValue = " + value);
//                            }
//                        });
                    }else {
//                        mWebView.loadUrl("javascript:jpushId('" + App.getInstance().getJPushId() + "')");
                    }
                }
            }

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                LogUtil.i(TAG,"over url = " + url);
////                view.loadUrl(url);
//
//                return false;
//            }
        });

        mWebView.loadUrl("http://m1.aidong.me/html/course.html#a?device=android&version=" +
                App.getInstance().getVersionName() + "&deviceName=" + DeviceManager.getPhoneBrand());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(selectCityReceiver);
    }

    class MyJSInterface {
        Context mContext;

        public MyJSInterface(Context context) {
            mContext = context;
        }


        @JavascriptInterface
        public String getJpushID() {

            return App.getInstance().getjPushId();
        }

        @JavascriptInterface
        public String getDevice() {
            return "android";
        }

        @JavascriptInterface
        public String getVersion() {

            return App.getInstance().getVersionName();
        }

        @JavascriptInterface
        public String getDeviceName() {

            return DeviceManager.getPhoneBrand();
        }

        /**
         * 跳转目标页类型,#web_inner-内嵌网页 web_out-外部网页 venues-场馆详情页 course_list-课程列表 campaign-活动详情页 goods-商品详情
         */
        @JavascriptInterface
        public void toTargetActivity(String type, String... link ) {

            switch (type) {
                case "web_inner":
                    WebViewActivity.start(getActivity(), link[0], link[1]);
                    break;
                case "web_out":
                    Uri uri = Uri.parse(link[0]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;
                case "venues":
                    VenuesDetailActivity.start(getActivity(), link[0]);
                    break;
                case "course_list":
                    CourseListActivityNew.start(getActivity(),link[0]);
                    break;
                case "campaign":

                    ActivityCircleDetailActivity.start(getActivity(), link[0]);
//                    CampaignDetailActivity.start(getActivity(), link[0]);
                    break;
                case "goods":
                    GoodsDetailActivity.start(getActivity(), link[0], link[1]);
                    break;
                case "video":
                    VideoDetailActivity.start(getActivity(), FormatUtil.parseInt(link[0]), FormatUtil.parseInt(link[1]),0);
                    break;
                case "goodsRecommend":
                    GoodsBrandRecommendActivity.start(getActivity(), link[0],link[1]);
                    break;
                case "goodsList":
                    GoodsListActivity.start(getActivity(), link[0],FormatUtil.parseInt(link[1]));
                    break;

                default:
                    break;
            }
        }
    }
}
