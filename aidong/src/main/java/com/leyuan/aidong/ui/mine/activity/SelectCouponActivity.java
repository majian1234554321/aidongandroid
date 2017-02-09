package com.leyuan.aidong.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.adapter.SelectCouponAdapter;

/**
 * 选择优惠券
 * Created by song on 2016/12/21.
 */
public class SelectCouponActivity extends BaseActivity{
    private ImageView ivBack;
    private TextView tvNotUse;
    private RecyclerView recyclerView;
    private SelectCouponAdapter couponAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);
        initView();
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvNotUse = (TextView) findViewById(R.id.tv_not_use);
        recyclerView = (RecyclerView) findViewById(R.id.rv_coupon);
        couponAdapter = new SelectCouponAdapter(this);
        recyclerView.setAdapter(couponAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvNotUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
