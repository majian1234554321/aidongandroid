package com.leyuan.aidong.ui.activity.discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;

/**
 * 资讯详情
 * Created by song on 2016/10/17.
 */
public class NewsDetailActivity extends BaseActivity{
    private ImageView ivBack;
    private ImageView ivShare;
    private TextView tvTitle;
    private TextView tvDate;
    private WebView wvNews;

    public static void start(Context context) {
        Intent starter = new Intent(context, NewsDetailActivity.class);
        //starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initView();
        wvNews.loadUrl("http://www.jianshu.com/p/556510db02b9");
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDate = (TextView) findViewById(R.id.tv_date);
        wvNews = (WebView) findViewById(R.id.wv_news);
    }
}
