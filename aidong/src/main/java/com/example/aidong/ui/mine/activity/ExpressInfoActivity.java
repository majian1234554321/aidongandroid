package com.example.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.ExpressResultBean;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mvp.presenter.OrderPresent;
import com.example.aidong .ui.mvp.presenter.impl.OrderPresentImpl;
import com.example.aidong .ui.mvp.view.ExpressInfoActivityView;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .widget.SimpleTitleBar;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.stepview.StepView;

/**
 * 快递详情
 * Created by song on 17/06/01.
 */
public class ExpressInfoActivity extends BaseActivity implements ExpressInfoActivityView, StepView.BindViewListener {
    private SimpleTitleBar titleBar;
    private ImageView ivCover;
    private TextView tvCount;
    private TextView tvCompany;
    private TextView tvNumber;
    private SwitcherLayout switcherLayout;
    private LinearLayout contentLayout;
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
        contentLayout = (LinearLayout) findViewById(R.id.ll_content);
        switcherLayout = new SwitcherLayout(this,contentLayout);
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
    public void updateExpressInfo(String cover,String expressName,ExpressResultBean bean) {
        GlideLoader.getInstance().displayImage(cover,ivCover);
        tvCompany.setText(expressName);
        tvNumber.setText(bean.number);
        stepView.setData(bean.list);
    }

    @Override
    public void onBindView(TextView itemMsg, TextView itemDate, Object data) {
        ExpressResultBean.ExpressListBean desc = (ExpressResultBean.ExpressListBean) data;
        itemMsg.setText(desc.status);
        itemDate.setText(desc.time);
    }

    @Override
    public void showLoadingView() {
        switcherLayout.showLoadingLayout();
    }

    @Override
    public void hideLoadingView() {
        switcherLayout.showContentLayout();
    }

    @Override
    public void showEmptyView() {
        switcherLayout.showEmptyLayout();
    }
}
