package com.leyuan.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.NewsBean;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.richtext.RichWebView;

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

    private NewsBean bean;

    private SharePopupWindow sharePopupWindow;
    private RichWebView webView;

//    @Deprecated
//    private static void start(Context context, String title,String subtitle, String date, String body) {
//        Intent starter = new Intent(context, NewsDetailActivity.class);
//        starter.putExtra("title", title);
//        starter.putExtra("date", date);
//        starter.putExtra("body", body);
//        starter.putExtra("subtitle", subtitle);
//        context.startActivity(starter);
//    }

    public static void start(Context context, NewsBean bean) {
        Intent starter = new Intent(context, NewsDetailActivity.class);
        starter.putExtra("newsBean", bean);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        if (getIntent() != null) {
            bean = (NewsBean) getIntent().getSerializableExtra("newsBean");
            title = bean.getTitle();
            date = bean.getDatetime();
            body = bean.getBody();
        }

        initView();
        setListener();
        sharePopupWindow = new SharePopupWindow(this);
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView txt_title = (TextView) findViewById(R.id.txt_title);
        TextView tvDate = (TextView) findViewById(R.id.tv_date);

        webView = (RichWebView) findViewById(R.id.web_view);
//        TextView tvNews = (TextView) findViewById(R.id.tv_news);
        tvTitle.setText(title);
        tvDate.setText(date);

        if (bean != null && bean.isNotShare) {
            ivShare.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(body)) {

            Logger.i("RichText", " body = " + body);
            webView.setRichText(body);
            txt_title.setText(bean.getTitleAll());
            if (TextUtils.isEmpty(date)) {
                tvDate.setVisibility(View.GONE);
            }
//            RichText.from(body).placeHolder(R.drawable.place_holder_logo)
//                    .error(R.drawable.place_holder_logo).into(tvNews);
        }
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                sharePopupWindow.showAtBottom("爱动资讯", bean.getTitle(), bean.getCover(), ConstantUrl.URL_SHARE_NEWS + bean.getId());
                break;
            default:
                break;
        }
    }

}
