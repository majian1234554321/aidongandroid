package com.example.aidong.ui.home.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.ui.BaseFragment;
import com.example.aidong.ui.home.activity.ShowWebImageActivity;
import com.example.aidong.utils.Logger;
import com.example.aidong.widget.richtext.RichWebView;

/**
 * 商品图文详情
 * Created by song on 2016/9/12.
 */
public class GoodsDetailFragment extends BaseFragment {
    private String content;
    private TextView tvContent;
    private RichWebView webView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            content = bundle.getString("detailText");
        }
        return inflater.inflate(R.layout.fragment_goods_detail, container, false);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        webView = (RichWebView) view.findViewById(R.id.web_view);

        if (!TextUtils.isEmpty(content)) {
            Logger.i("RichText", " body = " + content);
            webView.setRichText(content);

            webView.addJavascriptInterface(new JavascriptInterface(getContext()), "imagelistner");
            webView.setWebViewClient(new MyWebViewClient());


        }
    }


    //给webView添加js代码

    // 添加js交互接口类，并起别名 imagelistner  addJavaScriptInterface方式帮助我们从一个网页传递值到Android XML视图（反之亦然）。


    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    // js通信接口
    public class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        public void openImage(String img) {
            System.out.println(img);

            Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.setClass(context, ShowWebImageActivity.class);
            context.startActivity(intent);
            System.out.println(img);
        }
    }


    private class MyWebViewClient extends WebViewClient {

        //在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
        }

        //在页面加载结束时调用。
        @Override
        public void onPageFinished(WebView view, String url) {

            view.getSettings().setJavaScriptEnabled(true);

            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();

        }


        //在页面加载开始时调用。
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            super.onReceivedError(view, errorCode, description, failingUrl);

        }
    }


}
