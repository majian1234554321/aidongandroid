package com.leyuan.aidong.ui.mine.activity;

import android.content.Intent;
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
public class SelectCouponActivity extends BaseActivity implements SelectCouponAdapter.CouponListener {
    private ImageView ivBack;
    private TextView tvNotUse;
    private SelectCouponAdapter couponAdapter;
    private List<CouponBean> couponBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);
        if(getIntent() != null){
            couponBeanList = getIntent().getParcelableArrayListExtra("couponList");
        }
        initView();
        setListener();
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvNotUse = (TextView) findViewById(R.id.tv_not_use);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_coupon);
        couponAdapter = new SelectCouponAdapter(this);
        recyclerView.setAdapter(couponAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        couponAdapter.setData(couponBeanList);
    }

    private void setListener(){
        couponAdapter.setCouponListener(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvNotUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                CouponBean temp = new CouponBean();
                temp.setDiscount("0");
                intent.putExtra("coupon",temp);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    public void onCouponClicked(int position) {
        CouponBean couponBean = couponBeanList.get(position);
        Intent intent = new Intent();
        intent.putExtra("coupon",couponBean);
        setResult(RESULT_OK,intent);
        finish();
    }
}
