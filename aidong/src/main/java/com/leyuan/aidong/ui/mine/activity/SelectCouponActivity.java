package com.leyuan.aidong.ui.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.adapter.mine.SelectCouponAdapter;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.Logger;

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
    private String totalGoodsPrice;
    private String selectedCouponId;
    private String selectedUserCouponId;

    public static void startForResult(Activity context, String totalGoodsPrice, @Nullable String selectedCouponId,@Nullable String selectedUserCouponId,
                                      @NonNull List<CouponBean> usableCoupons, int requestSelectCoupon) {
        Intent intent = new Intent(context, SelectCouponActivity.class);
        if (usableCoupons instanceof ArrayList) {
            intent.putParcelableArrayListExtra("couponList", (ArrayList<CouponBean>) usableCoupons);
        }
        intent.putExtra("totalGoodsPrice",totalGoodsPrice);
        intent.putExtra("selectedCouponId", selectedCouponId);
        intent.putExtra("selectedUserCouponId", selectedUserCouponId);
        context.startActivityForResult(intent, requestSelectCoupon);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);
        if (getIntent() != null) {
            couponBeanList = getIntent().getParcelableArrayListExtra("couponList");
            totalGoodsPrice = getIntent().getStringExtra("totalGoodsPrice");
            selectedCouponId = getIntent().getStringExtra("selectedCouponId");
            selectedUserCouponId = getIntent().getStringExtra("selectedUserCouponId");
        }
        Logger.i("coupon","onCreate selectedUserCouponId = " + selectedUserCouponId);
        initView();
        setListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvNotUse = (TextView) findViewById(R.id.tv_not_use);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_coupon);
        couponAdapter = new SelectCouponAdapter(this);
        recyclerView.setAdapter(couponAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        couponAdapter.setData(couponBeanList,selectedUserCouponId);
    }

    private void setListener() {
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
                temp.setActual("0");
                intent.putExtra("coupon", temp);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onCouponClicked(int position) {
        CouponBean couponBean = couponBeanList.get(position);

//        if (totalGoodsPrice != null) {
//            double discout = FormatUtil.parseDouble(couponBean.getDiscount());
//            double goodsPrice = FormatUtil.parseDouble(totalGoodsPrice);
//            if (TextUtils.equals(couponBean.getMin(), Constant.NEGATIVE_ONE)) {
//                //新手指定优惠券
//                couponBean.setDiscount(String.valueOf((goodsPrice - discout )));
//            } else {
//                couponBean.setDiscount(String.valueOf((goodsPrice - discout > 0 ? discout : goodsPrice)));
//            }
//        }

        couponBean.setDiscount(couponBean.getActual());
        Intent intent = new Intent();
        intent.putExtra("coupon", couponBean);
        setResult(RESULT_OK, intent);
        finish();
    }

}
