package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.UiManager;

import java.util.ArrayList;

/**
 * Created by user on 2017/5/13.
 */
public class CouponNewcomerActivity extends BaseActivity implements View.OnClickListener {

    ArrayList<CouponBean> coupons;


    private ImageView imgClose;
    private TextView txtCouponNum;
    private RecyclerView recyclerView;
    private RelativeLayout layoutCheckImmediately;

    public static void start(Context context, ArrayList<CouponBean> coupons) {
        Intent intent = new Intent(context, CouponNewcomerActivity.class);
        intent.putExtra("coupons", coupons);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coupons = (ArrayList<CouponBean>) getIntent().getSerializableExtra("coupons");

        setContentView(R.layout.activity_newcomer_coupon);

        imgClose = (ImageView) findViewById(R.id.img_close);
        txtCouponNum = (TextView) findViewById(R.id.txt_coupon_num);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutCheckImmediately = (RelativeLayout) findViewById(R.id.layout_check_immediately);

        imgClose.setOnClickListener(this);
        layoutCheckImmediately.setOnClickListener(this);
        if (coupons != null ) {
            txtCouponNum.setText("恭喜您获得"+coupons.size()+"张优惠券,快去看看吧!");
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.layout_check_immediately:
                UiManager.activityJump(this, CouponActivity.class);
                break;
        }
    }
}
