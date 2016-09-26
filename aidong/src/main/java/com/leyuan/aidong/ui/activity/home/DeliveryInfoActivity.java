package com.leyuan.aidong.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.home.view.ChooseTimePopupWindow;

/**
 * 配送信息
 * Created by song on 2016/9/22.
 */
public class DeliveryInfoActivity extends BaseActivity implements View.OnClickListener{

    private ImageView tvBack;
    private TextView tvFinish;
    private TextView tvExpress;
    private TextView tvSelfDelivery;

    private LinearLayout deliveryLayout;
    private LinearLayout llDeliveryAddress;
    private TextView tvDeliveryTime;

    private ChooseTimePopupWindow timePopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_info);
        initView();
        setListener();
    }

    private void initView(){
        tvBack = (ImageView) findViewById(R.id.tv_back);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        tvExpress = (TextView) findViewById(R.id.tv_express);
        tvSelfDelivery = (TextView) findViewById(R.id.tv_self_delivery);
        deliveryLayout = (LinearLayout)findViewById(R.id.ll_self_delivery);
        llDeliveryAddress = (LinearLayout) findViewById(R.id.ll_delivery_address);
        tvDeliveryTime = (TextView) findViewById(R.id.tv_delivery_time);
    }

    private void setListener(){
        tvBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        tvExpress.setOnClickListener(this);
        tvSelfDelivery.setOnClickListener(this);
        llDeliveryAddress.setOnClickListener(this);
        tvDeliveryTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_finish:
                break;
            case R.id.tv_express:
                deliveryLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_self_delivery:
                deliveryLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_delivery_address:
                Intent intent = new Intent(this,SelfDeliveryShopActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_delivery_time:

                if(timePopupWindow == null){
                    timePopupWindow = new ChooseTimePopupWindow(this);
                }
                timePopupWindow.showAtLocation(findViewById(R.id.ll_root), Gravity.BOTTOM,0,0);
                break;
            default:
                break;
        }
    }
}
