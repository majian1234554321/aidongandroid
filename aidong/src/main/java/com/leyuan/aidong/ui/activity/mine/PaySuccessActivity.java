package com.leyuan.aidong.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.ui.activity.home.adapter.RecommendAdapter;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;

/**
 * 支付成功
 * Created by song on 2016/9/23.
 */
public class PaySuccessActivity extends BaseActivity implements View.OnClickListener {
    private SimpleTitleBar titleBar;
    private TextView tvHome;
    private TextView tvOrder;
    private RecyclerView recyclerView;
    private RecommendAdapter recommendAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        initView();
        setListener();
    }

    private void initView() {
        View headerView = View.inflate(this,R.layout.header_pay_success,null);
        tvHome = (TextView) headerView.findViewById(R.id.tv_home);
        tvOrder = (TextView) headerView.findViewById(R.id.tv_order);
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recommend);
        recommendAdapter = new RecommendAdapter(this,"1");
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(recommendAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter)
                recyclerView.getAdapter(), manager.getSpanCount()));
        recyclerView.setLayoutManager(manager);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RecyclerViewUtils.setHeaderView(recyclerView,headerView);
    }

    private void setListener(){
        tvHome.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
        titleBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_home:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_order:
                startActivity(new Intent(this, OrderActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
