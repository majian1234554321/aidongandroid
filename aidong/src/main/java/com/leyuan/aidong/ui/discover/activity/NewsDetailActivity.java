package com.leyuan.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.zzhoujay.richtext.RichText;

/**
 * 资讯详情
 * Created by song on 2016/10/17.
 */
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivBack;
    private ImageView ivShare;

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
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvDate = (TextView) findViewById(R.id.tv_date);
        TextView tvNews = (TextView) findViewById(R.id.tv_news);
        tvTitle.setText(title);
        tvDate.setText(date);
        if(!TextUtils.isEmpty(body)){
            RichText.from(body).into(tvNews);
        }
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
