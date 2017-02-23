package com.leyuan.aidong.ui.home.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DeliveryBean;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.TransitionHelper;


/**
 * 配送信息
 * Created by song on 2016/9/22.
 */
public class DeliveryInfoActivity extends BaseActivity implements View.OnClickListener{
    private static final int CODE_SELECT_VENUES = 1;
    private static final String EXPRESS = "0";
    private static final String SELF_DELIVERY = "1";

    private ImageView tvBack;
    private TextView tvFinish;
    private TextView tvExpress;
    private TextView tvSelfDelivery;
    private LinearLayout deliveryLayout;
    private LinearLayout llDeliveryAddress;
    private TextView tvVenues;
    private TextView tvVenuesAddress;

    private String id;
    private String type;
    private DeliveryBean deliveryBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        setContentView(R.layout.activity_delivery_info);

        if(getIntent() != null){
            id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");
            deliveryBean = getIntent().getParcelableExtra("deliveryBean");
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
        tvVenues = (TextView) findViewById(R.id.tv_shop);
        tvVenuesAddress = (TextView) findViewById(R.id.tv_shop_address);
        if(deliveryBean != null) {
            tvVenues.setText(deliveryBean.getInfo().getName());
            tvVenuesAddress.setText(deliveryBean.getInfo().getAddress());
            tvVenuesAddress.setVisibility(View.VISIBLE);
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
                animationFinish();
                break;
            case R.id.tv_finish:
                Intent result = new Intent();
                result.putExtra("deliveryBean",deliveryBean);
                setResult(RESULT_OK,result);
                animationFinish();
                break;
            case R.id.tv_express:
                tvExpress.setTextColor(Color.parseColor("#ffffff"));
                tvSelfDelivery.setTextColor(Color.parseColor("#000000"));
                tvExpress.setBackgroundResource(R.drawable.shape_solid_corner_black);
                tvSelfDelivery.setBackgroundResource(R.drawable.shape_solid_corner_white);
                deliveryLayout.setVisibility(View.GONE);
                deliveryBean.setType(EXPRESS);
                break;
            case R.id.tv_self_delivery:
                tvExpress.setTextColor(Color.parseColor("#000000"));
                tvSelfDelivery.setTextColor(Color.parseColor("#ffffff"));
                tvExpress.setBackgroundResource(R.drawable.shape_solid_corner_white);
                tvSelfDelivery.setBackgroundResource(R.drawable.shape_solid_corner_black);
                deliveryLayout.setVisibility(View.VISIBLE);
                deliveryBean.setType(SELF_DELIVERY);
                break;
            case R.id.ll_delivery_address:
                Intent intent = new Intent(this,SelfDeliveryVenuesActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("id",id);
                final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, false);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
                startActivityForResult(intent, CODE_SELECT_VENUES,optionsCompat.toBundle());
                break;
            default:
                break;
            
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null && requestCode == CODE_SELECT_VENUES){
            VenuesBean venuesBean = data.getParcelableExtra("venues");
            tvVenues.setText(venuesBean.getName());
            tvVenuesAddress.setText(venuesBean.getAddress());
            deliveryBean.setInfo(venuesBean);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(200);
        slide.setSlideEdge(Gravity.END);
        slide.excludeTarget(android.R.id.statusBarBackground,true);
        getWindow().setEnterTransition(slide);
    }
}
