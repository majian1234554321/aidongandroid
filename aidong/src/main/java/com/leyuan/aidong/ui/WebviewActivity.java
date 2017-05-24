package com.leyuan.aidong.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.utils.Logger;

/**
 * WebView
 * Created by song on 2016/10/18.v
 */
public class WebViewActivity extends BaseActivity{
    private static final java.lang.String TAG = "WebViewActivity";
    private WebView webView;
    private String title;
    private String url;

    public static void start(Context context,String title,String url) {
        Intent starter = new Intent(context, WebViewActivity.class);
        starter.putExtra("title",title);
        starter.putExtra("url",url);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        if(getIntent() != null){
             title = getIntent().getStringExtra("title");
             url = getIntent().getStringExtra("url");
            Logger.i(TAG,"url = " +url);
        }

        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        webView = (WebView) findViewById(R.id.web_view);

        ivBack.setOnClickListener(backListener);
        tvTitle.setText(title);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new AiDongWebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);

    }


    private class AiDongWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //switcherLayout.showLoadingLayout();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
          //  switcherLayout.showContentLayout();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
           // switcherLayout.showExceptionLayout();
        }
    }

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
               finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
