package com.leyuan.aidong.ui.activity.mine.lovecoin;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.common.BaseUrlLink;
import com.leyuan.aidong.R;

public class LoveCoinHelpActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_coin_help);
        initView();
        initData();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        webView = (WebView) findViewById(R.id.webView);
    }

    private void initData() {
        img_back.setOnClickListener(this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
//        if (TextUtils.equals("http://m1.aidong.me/aidong9", Urls.BASE_URL_TEXT)) {
//            webView.loadUrl(Urls.BASE_URL_TEST_INTEGRAL + "/html/help-coin.html");
//        } else {
            webView.loadUrl(BaseUrlLink.URL + "/html/help-coin.html");
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
