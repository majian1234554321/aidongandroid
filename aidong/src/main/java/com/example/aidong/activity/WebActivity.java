package com.example.aidong.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.aidong.R;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView)findViewById(R.id.web_view);
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);

        //wView.loadUrl("file:///android_asset/index.html");
        //wView.loadUrl("content://com.android.htmlfileprovider/sdcard/index.html");
        webView.loadUrl("http://192.168.50.106:4000/orders/mobile");
    }
}
