package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ExpressResultBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.OrderPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.OrderPresentImpl;
import com.leyuan.aidong.ui.mvp.view.ExpressInfoActivityView;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.stepview.StepView;

import java.util.regex.Pattern;

/**
 * 快递详情
 * Created by song on 17/06/01.
 */
public class ExpressInfoActivity extends BaseActivity implements ExpressInfoActivityView, StepView.BindViewListener {
    private static final String PATTERN_PHONE = "((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-3]|[5-9]))\\d{8}";
    private static final String SCHEME_TEL = "tel:";

    private SimpleTitleBar titleBar;
    private ImageView ivCover;
    private TextView tvCount;
    private TextView tvCompany;
    private TextView tvNumber;
    private StepView stepView;

    private String orderId;
    private OrderPresent orderPresent;

    public static void start(Context context,String orderId) {
        Intent starter = new Intent(context, ExpressInfoActivity.class);
        starter.putExtra("orderId",orderId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_detail);
        orderPresent = new OrderPresentImpl(this,this);
        if(getIntent() != null){
            orderId = getIntent().getStringExtra("orderId");
        }
        initView();
        setListener();
        orderPresent.getExpressInfo(orderId);
    }

    private void initView(){
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        ivCover = (ImageView) findViewById(R.id.iv_cover);
        tvCount = (TextView) findViewById(R.id.tv_count);
        tvCompany = (TextView) findViewById(R.id.tv_company);
        tvNumber = (TextView) findViewById(R.id.tv_number);
        stepView = (StepView) findViewById(R.id.stepView);
    }

    private void setListener(){
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        stepView.setBindViewListener(this);
    }

    @Override
    public void updateExpressInfo(String cover,ExpressResultBean bean) {
        GlideLoader.getInstance().displayImage(cover,ivCover);
        tvCompany.setText(bean.type);
        tvNumber.setText(bean.number);
        stepView.setData(bean.list);
    }

    @Override
    public void onBindView(TextView itemMsg, TextView itemDate, Object data) {
        ExpressResultBean.ExpressListBean desc = (ExpressResultBean.ExpressListBean) data;
        itemMsg.setText(formatPhoneNumber(itemMsg, desc.status));
        itemDate.setText(desc.time);
    }

    private SpannableStringBuilder formatPhoneNumber(TextView textView, String source) {
        // 若要部分 SpannableString 可点击，需要如下设置
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        // 将要格式化的 String 构建成一个 SpannableStringBuilder
        SpannableStringBuilder value = new SpannableStringBuilder(source);

        // 使用正则表达式匹配电话
        Linkify.addLinks(value, Pattern.compile(PATTERN_PHONE), SCHEME_TEL);

        // 获取上面到所有 addLinks 后的匹配部分(这里一个匹配项被封装成了一个 URLSpan 对象)
        URLSpan[] urlSpans = value.getSpans(0, value.length(), URLSpan.class);
        for (final URLSpan urlSpan : urlSpans) {
            if (urlSpan.getURL().startsWith(SCHEME_TEL)) {
                int start = value.getSpanStart(urlSpan);
                int end = value.getSpanEnd(urlSpan);
                value.removeSpan(urlSpan);
                value.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        String phone = urlSpan.getURL().replace(SCHEME_TEL, "");
                        AlertDialog.Builder builder = new AlertDialog.Builder(ExpressInfoActivity.this);
                        builder.setMessage("是否拨打电话：" + phone);
                        builder.setNegativeButton("取消", null);
                        builder.setPositiveButton("确定", null);
                        builder.create().show();
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.parseColor("#3f8de2"));
                        ds.setUnderlineText(true);
                    }
                }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return value;
    }
}
