package com.leyuan.aidong.ui.activity.discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;

/**
 * 资讯详情
 * Created by song on 2016/10/17.
 */
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivBack;
    private ImageView ivShare;
    private TextView tvTitle;
    private TextView tvDate;
    private WebView wvNews;

    private String title;
    private String date;
    private String body;

    public static void start(Context context,String title,String date,String body) {
        Intent starter = new Intent(context, NewsDetailActivity.class);
        starter.putExtra("title",title);
        starter.putExtra("date",date);
        starter.putExtra("body",body);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        if(getIntent() != null){
            title = getIntent().getStringExtra("title");
            date = getIntent().getStringExtra("date");
            body = getIntent().getStringExtra("body");
        }

        initView();
        setListener();
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDate = (TextView) findViewById(R.id.tv_date);
        wvNews = (WebView) findViewById(R.id.wv_news);
        tvTitle.setText(title);
        tvDate.setText(date);
        wvNews.loadUrl("http://www.jianshu.com/p/556510db02b9");
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                break;
            default:
                break;
        }
    }
}
