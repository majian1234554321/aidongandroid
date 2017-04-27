package com.leyuan.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.module.share.SharePopupWindow;
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

    private SharePopupWindow sharePopupWindow;

    public static void start(Context context, String title, String date, String body) {
        Intent starter = new Intent(context, NewsDetailActivity.class);
        starter.putExtra("title", title);
        starter.putExtra("date", date);
        starter.putExtra("body", body);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            date = getIntent().getStringExtra("date");
            body = getIntent().getStringExtra("body");
        }

        initView();
        setListener();
        sharePopupWindow = new SharePopupWindow(this);
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvDate = (TextView) findViewById(R.id.tv_date);
        TextView tvNews = (TextView) findViewById(R.id.tv_news);
        tvTitle.setText(title);
        tvDate.setText(date);
        if (!TextUtils.isEmpty(body)) {
            RichText.from(body).placeHolder(R.drawable.place_holder_logo)
                    .error(R.drawable.place_holder_logo).into(tvNews);
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
                sharePopupWindow.showAtBottom(title, date, "http://function.aidong.me/image/2017/03/17/1fe8940455d788331ca43abeb164a455.jpg",
                        "http://www.baidu.com");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sharePopupWindow.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharePopupWindow.release();
    }
}
