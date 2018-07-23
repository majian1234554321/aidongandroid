package com.example.aidong.ui.home.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.ui.BaseFragment;
import com.example.aidong.ui.discover.activity.ImageShowActivity;
import com.example.aidong.ui.home.activity.ShowWebImageActivity;
import com.example.aidong.utils.Logger;
import com.example.aidong.widget.richtext.RichWebView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            WebSettings webSettings = webView.getSettings();
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            webSettings.setUseWideViewPort(true); //设定支持viewport
            webSettings.setLoadWithOverviewMode(true);   //自适应屏幕
          //  webSettings.builtInZoomControls = true;
           // webSettings.displayZoomControls = false;
            webSettings.setSupportZoom(false);//设定支持缩放
            webSettings.setTextSize(WebSettings.TextSize.LARGEST);


          //  webView.setInitialScale(25);




            String[] mImageUrls = returnImageUrlsFromHtml(content);
            webView.addJavascriptInterface(new MJavascriptInterface(activity, mImageUrls), "imagelistener");
            webView.setWebViewClient(new MyTextViewWebViewClient());


        }
    }


    public String[] returnImageUrlsFromHtml(String desr) {
        List<String> imageSrcList = new ArrayList<String>();
        String htmlCode = desr;
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic|\\b)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("//s+")[0] : m.group(2);
            imageSrcList.add(src);
        }
        if (imageSrcList == null || imageSrcList.size() == 0) {
            Log.e("imageSrcList", "资讯中未匹配到图片链接");
            return null;
        }
        return imageSrcList.toArray(new String[imageSrcList.size()]);
    }


    public class MyTextViewWebViewClient extends WebViewClient {
        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            addImageClickListener(view);//待网页加载完全后设置图片点击的监听方法
        }

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        private void addImageClickListener(WebView webView) {
            webView.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName(\"img\"); " +
                    "for(var i=0;i<objs.length;i++) " +
                    "{"
                    + " objs[i].onclick=function() " +
                    " { "
                    + "  window.imagelistener.openImage(this.src); " +//通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接
                    " } " +
                    "}" +
                    "})()");


            webView.loadUrl("javascript:var imgs=document.getElementsByTagName('img');for(var i=0;i<imgs.length;i++){imgs[i].style.width='100%'; imgs[i].style.height='auto';};void(0);");


        }
    }


    public class MJavascriptInterface {
        private Activity mContext;
        private String[] imageUrls;

        public MJavascriptInterface(Activity context, String[] imageUrls) {
            this.mContext = context;
            this.imageUrls = imageUrls;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            //以下跳转你自己的大图预览页面即可
            ImageView imageView = new ImageView(mContext);

            ImageShowActivity.startImageActivity(mContext, imageView, img);
        }
    }

}
