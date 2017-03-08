package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.UpdateDeliveryInfoAdapter;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.entity.UpdateDeliveryBean;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.activity.SelfDeliveryVenuesActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 确认订单界面修改配送信息
 * Created by song on 2017/2/8.
 */
public class UpdateDeliveryInfoActivity extends BaseActivity implements View.OnClickListener,
        UpdateDeliveryInfoAdapter.SelfDeliveryShopListener {
    private static final int REQUEST_SELECT_DELIVERY = 1;
    private ImageView ivBack;
    private TextView tvFinish;
    private UpdateDeliveryInfoAdapter deliveryInfoAdapter;
    private List<UpdateDeliveryBean> data = new ArrayList<>();
    private int position;

    public static void start(Context context, ShopBean shopBean) {
        Intent starter = new Intent(context, UpdateDeliveryInfoActivity.class);
        starter.putExtra("shopBean",shopBean);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delivery_info);
        if(getIntent() != null){
            ShopBean shopBean = getIntent().getParcelableExtra("shopBean");
            for (int i = 0; i < shopBean.getItem().size(); i++) {
                UpdateDeliveryBean bean = new UpdateDeliveryBean();
                bean.setGoods(shopBean.getItem().get(i));
                bean.setDeliveryInfo(shopBean.getPickUp());
                data.add(bean);
            }
        }

        initView();
        setListener();
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        deliveryInfoAdapter = new UpdateDeliveryInfoAdapter(this);
        recyclerView.setAdapter(deliveryInfoAdapter);
        deliveryInfoAdapter.setData(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        deliveryInfoAdapter.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:
                break;
        }
    }

    @Override
    public void onChangeShop(int position) {
        this.position = position;
        Intent intent = new Intent(this,SelfDeliveryVenuesActivity.class);
        intent.putExtra("id",data.get(position).getGoods().getId());
        intent.putExtra("goodsType",data.get(position).getGoods().getType());
        startActivityForResult(intent,REQUEST_SELECT_DELIVERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(intent != null){
            if(requestCode == REQUEST_SELECT_DELIVERY){
                VenuesBean venuesBean = intent.getParcelableExtra("venues");
                data.get(position).getDeliveryInfo().setInfo(venuesBean);
                deliveryInfoAdapter.notifyDataSetChanged();
            }
        }
    }
}
