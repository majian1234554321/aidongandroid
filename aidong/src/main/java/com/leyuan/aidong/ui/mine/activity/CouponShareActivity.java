package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.wxapi.WXEntryActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ShareBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.ShareActivityQQ;
import com.leyuan.aidong.ui.WeiboResponseActivity;
import com.leyuan.aidong.utils.HtmlToStringUtils;
import com.leyuan.aidong.utils.Logger;

/**
 * Created by user on 2017/5/13.
 */
public class CouponShareActivity extends BaseActivity implements View.OnClickListener {
    private TextView txtShareTo;
    private ImageView imgWeichat;
    private ImageView imgWeiFriend;
    private ImageView imgQq;
    private ImageView imgWeibo;
    private ImageButton img_close;

    private String title;
    private String content;
    private String imageUrl;
    private String webUrl;

    public static void start(Context context, ShareBean shareBean) {
        start(context, shareBean.getTitle(), shareBean.getContent(), shareBean.getImageUrl(), shareBean.getWebUrl());
    }

    public static void start(Context context, String title, String content, String imageUrl, String webUrl) {
        Intent intent = new Intent(context, CouponShareActivity.class);
        intent.putExtra("title", title);
        String html = HtmlToStringUtils.delHTMLTag(content);
        intent.putExtra("content", html.substring(0, html.length() > 30 ? 30 : html.length()));
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("webUrl", webUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_coupon);
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        imageUrl = getIntent().getStringExtra("imageUrl");
        webUrl = getIntent().getStringExtra("webUrl");

        Logger.i("share", "title = " + title + ",  content = " + content + "， image url = " + imageUrl + ", webUrl = " + webUrl);

        txtShareTo = (TextView) findViewById(R.id.txt_share_to);
        imgWeichat = (ImageView) findViewById(R.id.img_weichat);
        imgWeiFriend = (ImageView) findViewById(R.id.img_wei_friend);
        imgQq = (ImageView) findViewById(R.id.img_qq);
        imgWeibo = (ImageView) findViewById(R.id.img_weibo);
        img_close = (ImageButton) findViewById(R.id.img_close);

        imgWeichat.setOnClickListener(this);
        imgWeiFriend.setOnClickListener(this);
        imgQq.setOnClickListener(this);
        imgWeibo.setOnClickListener(this);
        img_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_weichat:
                WXEntryActivity.start(this, title, content, imageUrl, webUrl, false);
                break;
            case R.id.img_wei_friend:
                WXEntryActivity.start(this, title, content, imageUrl, webUrl, true);
                break;
            case R.id.img_qq:
                ShareActivityQQ.start(this, title, content, imageUrl, webUrl);
                break;
            case R.id.img_weibo:
                WeiboResponseActivity.start(this, title, content, imageUrl, webUrl);
                break;
            case R.id.img_close:
                finish();
                break;
        }
    }

}
