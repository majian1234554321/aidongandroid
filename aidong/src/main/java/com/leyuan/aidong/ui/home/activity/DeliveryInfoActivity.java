package com.leyuan.aidong.ui.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DeliveryBean;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.TransitionHelper;

import static com.leyuan.aidong.utils.Constant.DELIVERY_EXPRESS;
import static com.leyuan.aidong.utils.Constant.DELIVERY_SELF;


/**
 * 配送信息
 * Created by song on 2016/9/22.
 */
public class DeliveryInfoActivity extends BaseActivity implements View.OnClickListener{
    private static final int CODE_SELECT_VENUES = 1;

    private ImageView tvBack;
    private TextView tvFinish;
    private TextView tvExpress;
    private TextView tvSelfDelivery;
    private LinearLayout deliveryLayout;
    private LinearLayout llDeliveryAddress;
    private TextView tvVenuesName;
    private TextView tvVenuesAddress;

    private String goodsId;
    private String goodsType;
    private DeliveryBean deliveryBean;
    private String gymId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_info);
        setSlideAnimation();
        if(getIntent() != null){
            goodsId = getIntent().getStringExtra("goodsId");
            goodsType = getIntent().getStringExtra("goodsType");
            deliveryBean = getIntent().getParcelableExtra("deliveryBean");
            gymId = deliveryBean.getInfo().getId();
        }
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
        tvVenuesName = (TextView) findViewById(R.id.tv_shop);
        tvVenuesAddress = (TextView) findViewById(R.id.tv_shop_address);
        if(deliveryBean != null) {
            if(deliveryBean.getType().equals(DELIVERY_EXPRESS)){
                setExpressSelected();
            }else {
                setSelfDeliverySelected();
            }
        }
    }

    private void setListener(){
        tvBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        tvExpress.setOnClickListener(this);
        tvSelfDelivery.setOnClickListener(this);
        llDeliveryAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                compatFinish();
                break;
            case R.id.tv_finish:
                Intent result = new Intent();
                result.putExtra("deliveryBean",deliveryBean);
                setResult(RESULT_OK,result);
                compatFinish();
                break;
            case R.id.tv_express:
                setExpressSelected();
                break;
            case R.id.tv_self_delivery:
                setSelfDeliverySelected();
                break;
            case R.id.ll_delivery_address:
                Intent intent = new Intent(this,SelfDeliveryVenuesActivity.class);
                intent.putExtra("goodsType",goodsType);
                intent.putExtra("goodsId", goodsId);
                intent.putExtra("deliveryBean",deliveryBean);
                final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, false);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
                startActivityForResult(intent, CODE_SELECT_VENUES,optionsCompat.toBundle());
                break;
            default:
                break;
        }
    }

    private void setExpressSelected(){
        tvExpress.setTextColor(Color.parseColor("#ffffff"));
        tvSelfDelivery.setTextColor(Color.parseColor("#000000"));
        tvExpress.setBackgroundResource(R.drawable.shape_solid_corner_black);
        tvSelfDelivery.setBackgroundResource(R.drawable.shape_solid_corner_white);
        deliveryLayout.setVisibility(View.GONE);
        deliveryBean.getInfo().setId(null);
        deliveryBean.setType(DELIVERY_EXPRESS);
    }


    private void setSelfDeliverySelected(){
        tvExpress.setTextColor(Color.parseColor("#000000"));
        tvSelfDelivery.setTextColor(Color.parseColor("#ffffff"));
        tvExpress.setBackgroundResource(R.drawable.shape_solid_corner_white);
        tvSelfDelivery.setBackgroundResource(R.drawable.shape_solid_corner_black);
        deliveryLayout.setVisibility(View.VISIBLE);
        deliveryBean.setType(DELIVERY_SELF);
        if(TextUtils.isEmpty(gymId)){
            tvVenuesName.setText(getString(R.string.please_select));
            tvVenuesAddress.setVisibility(View.GONE);
        }else {
            deliveryBean.getInfo().setId(gymId);
            tvVenuesName.setText(deliveryBean.getInfo().getName());
            tvVenuesAddress.setText(deliveryBean.getInfo().getAddress());
            tvVenuesAddress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null && requestCode == CODE_SELECT_VENUES){
            VenuesBean venuesBean = data.getParcelableExtra("venues");
            tvVenuesName.setText(venuesBean.getName());
            tvVenuesAddress.setText(venuesBean.getAddress());
            tvVenuesAddress.setVisibility(View.VISIBLE);
            deliveryBean.setInfo(venuesBean);
            gymId = venuesBean.getId();
        }
    }
}
