package com.leyuan.aidong.ui.home.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseActivity;
import com.example.aidong.R;

/**
 * 商品确认
 * Created by song on 2016/9/22.
 */
public class ConfirmGoodsActivity extends BaseActivity implements View.OnClickListener{
    private ImageView tvBack;

    //口味和数量
    private RecyclerView rlGoodsTaste;
    private ImageView ivMinus;
    private TextView tvCount;
    private ImageView ivAdd;

    //配送方式
    private TextView tvExpress;
    private TextView tvSelfDelivery;
    private LinearLayout selfDeliveryLayout;
    private LinearLayout llDeliveryAddress;
    private TextView tvDeliveryTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_confrim);
        initView();
        setListener();
    }

    private void initView(){
        tvBack = (ImageView) findViewById(R.id.tv_back);
        rlGoodsTaste = (RecyclerView) findViewById(R.id.rl_goods_taste);
        ivMinus = (ImageView) findViewById(R.id.iv_minus);
        tvCount = (TextView) findViewById(R.id.tv_count);
        ivAdd = (ImageView) findViewById(R.id.iv_add);
        tvExpress = (TextView) findViewById(R.id.tv_express);
        tvSelfDelivery = (TextView) findViewById(R.id.tv_self_delivery);
        selfDeliveryLayout = (LinearLayout) findViewById(R.id.ll_self_delivery);
        llDeliveryAddress = (LinearLayout) findViewById(R.id.ll_delivery_address);
        tvDeliveryTime = (TextView) findViewById(R.id.tv_delivery_time);
    }

    private void setListener() {
        tvBack.setOnClickListener(this);
        ivMinus.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        tvExpress.setOnClickListener(this);
        tvSelfDelivery.setOnClickListener(this);
        llDeliveryAddress.setOnClickListener(this);
        tvDeliveryTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                break;
            default:
                break;
        }
    }
}
