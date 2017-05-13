package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.ui.BaseActivity;

import java.util.ArrayList;

/**
 * Created by user on 2017/5/13.
 */
public class CouponNewcomerActivity extends BaseActivity {

    ArrayList<CouponBean> coupons;

    public static void start(Context context, ArrayList<CouponBean> coupons) {
        Intent intent = new Intent(context, CouponNewcomerActivity.class);
        intent.putExtra("coupons", coupons);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coupons = (ArrayList<CouponBean>) getIntent().getSerializableExtra("coupons");
        setContentView(R.layout.activity_newcomer_coupon);

    }
}
