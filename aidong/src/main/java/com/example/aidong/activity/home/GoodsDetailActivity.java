package com.example.aidong.activity.home;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.SamplePagerAdapter;
import com.example.aidong.view.SlideDetailsLayout;
import com.leyuan.support.widget.customview.ViewPagerIndicator;

/**
 * 商品详情
 * Created by song on 2016/9/12.
 */
public class GoodsDetailActivity extends BaseActivity{
    private SlideDetailsLayout detailsLayout;

    private ViewPager viewPager;
    private ViewPagerIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);


        viewPager = (ViewPager) findViewById(R.id.vp_photo);
        indicator = (ViewPagerIndicator) findViewById(R.id.vp_indicator);
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);

        final WebView webView = (WebView) findViewById(R.id.web_view);
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            new Object() {
                public void setLoadWithOverviewMode(boolean overview) {
                    settings.setLoadWithOverviewMode(overview);
                }
            }.setLoadWithOverviewMode(true);
        }

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("http://www.jianshu.com/p/fc923690463f");

            }
        });
    }
}
