package com.leyuan.aidong.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.SelectCouponAdapter;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择优惠券
 * Created by song on 2016/12/21.
 */
public class SelectCouponActivity extends BaseActivity{
    private List<CouponBean> couponBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);
        if(getIntent() != null){
            couponBeanList = getIntent().getParcelableArrayListExtra("couponList");
        }
        initView();
    }

    private void initView(){
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        TextView tvNotUse = (TextView) findViewById(R.id.tv_not_use);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_coupon);
        SelectCouponAdapter couponAdapter = new SelectCouponAdapter(this);
        recyclerView.setAdapter(couponAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        couponAdapter.setData(couponBeanList);
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
