package com.leyuan.aidong.widget.richtext;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by user on 2017/6/7.
 */

public class RichWebView extends WebView {
    public RichWebView(Context context) {
        this(context, null);
    }

    public RichWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSetting();
    }

    private void initSetting() {

        getSettings().setJavaScriptEnabled(true);
        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:(function(){"
                        + "var objs = document.getElementsByTagName('img'); "
                        + "for(var i=0;i<objs.length;i++)  " + "{"
                        + "var img = objs[i];   "
                        + "    img.style.width = '100%';   "
                        + "    img.style.height = 'auto';   "
                        + "}" + "})()");

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
    }

    public void setRichText(String content) {
        if (content == null) return;
        String htmlHeader;
        String htmlFooter;


        loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);

    }
}
