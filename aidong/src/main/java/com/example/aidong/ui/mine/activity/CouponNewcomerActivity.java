package com.example.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.mine.CouponNewcomerAdapter;
import com.example.aidong .entity.CouponBean;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mvp.presenter.impl.CouponPresentImpl;
import com.example.aidong .ui.mvp.view.CouponFragmentView;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.UiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/5/13.
 */
public class CouponNewcomerActivity extends BaseActivity implements View.OnClickListener {

    ArrayList<CouponBean> coupons;


    private ImageView imgClose;
    private TextView txtCouponNum;
    private RecyclerView recyclerView;
    private RelativeLayout layoutCheckImmediately;
    private CouponNewcomerAdapter couponAdapter;

    @Deprecated
    public static void start(Context context, ArrayList<CouponBean> coupons) {
        Intent intent = new Intent(context, CouponNewcomerActivity.class);
        intent.putExtra("coupons", coupons);
        context.startActivity(intent);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CouponNewcomerActivity.class);
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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         couponAdapter = new CouponNewcomerAdapter(this, coupons);
        recyclerView.setAdapter(couponAdapter);


        CouponPresentImpl couponPresent = new CouponPresentImpl(this, new CouponFragmentView() {

            @Override
            public void updateRecyclerView(List<CouponBean> couponBeanList) {
                Logger.i("coupon","couponList == " +(couponBeanList==null?"null":couponBeanList.size()+""));

                if(couponBeanList!=null&& !couponBeanList.isEmpty()){
                    txtCouponNum.setText("恭喜您获得"+couponBeanList.size()+"张优惠券,快去看看吧!");
                    couponAdapter.setData(couponBeanList);
                    couponAdapter.notifyDataSetChanged();

                }else {
                    finish();
                }

            }

            @Override
            public void showEmptyView() {

            }

            @Override
            public void showEndFooterView() {

            }
        });

        couponPresent.requestCouponData("valid");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.layout_check_immediately:
                UiManager.activityJump(this, CouponActivity.class);
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
