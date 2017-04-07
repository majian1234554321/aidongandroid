package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.UpdateDeliveryInfoAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.DeliveryBean;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.entity.UpdateDeliveryBean;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.activity.SelfDeliveryVenuesActivity;
import com.leyuan.aidong.ui.mvp.presenter.CartPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CartPresentImpl;
import com.leyuan.aidong.ui.mvp.view.UpdateDeliveryInfoActivityView;
import com.leyuan.aidong.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.DELIVERY_EXPRESS;
import static com.leyuan.aidong.utils.Constant.DELIVERY_SELF;

/**
 * 确认订单界面修改配送信息
 * Created by song on 2017/2/8.
 */
public class UpdateDeliveryInfoActivity extends BaseActivity implements View.OnClickListener,
        UpdateDeliveryInfoAdapter.SelfDeliveryShopListener ,UpdateDeliveryInfoActivityView{
    private static final int REQUEST_SELECT_DELIVERY = 1;
    private ImageView ivBack;
    private UpdateDeliveryInfoAdapter deliveryInfoAdapter;
    private List<UpdateDeliveryBean> data = new ArrayList<>();
    private int position;
    private VenuesBean venuesBean;
    private CartPresent cartPresent;
    private boolean updated = false;

    public static void start(Context context, ShopBean shopBean) {
        Intent starter = new Intent(context, UpdateDeliveryInfoActivity.class);
        starter.putExtra("shopBean",shopBean);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delivery_info);
        cartPresent = new CartPresentImpl(this,this);
        if(getIntent() != null){
            ShopBean shopBean = getIntent().getParcelableExtra("shopBean");
            for (int i = 0; i < shopBean.getItem().size(); i++) {
                UpdateDeliveryBean bean = new UpdateDeliveryBean();
                bean.setGoods(shopBean.getItem().get(i));
                DeliveryBean deliveryBean = shopBean.getPickUp();
                bean.setDeliveryInfo(deliveryBean);
                data.add(bean);
            }
        }

        initView();
        setListener();
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        deliveryInfoAdapter = new UpdateDeliveryInfoAdapter(this);
        recyclerView.setAdapter(deliveryInfoAdapter);
        deliveryInfoAdapter.setData(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        deliveryInfoAdapter.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finishWithSendResult();
                break;
        }
    }

    @Override
    public void onExpressClick(int position) {
        this.position = position;
        cartPresent.updateGoodsDeliveryInfo(data.get(position).getGoods().getId(),DELIVERY_EXPRESS);
    }

    @Override
    public void onSelfDeliveryClick(int position) {
        this.position = position;
        Intent intent = new Intent(this,SelfDeliveryVenuesActivity.class);
        intent.putExtra("id",data.get(position).getGoods().getProductId());
        intent.putExtra("goodsType",data.get(position).getGoods().getProductType());
        startActivityForResult(intent,REQUEST_SELECT_DELIVERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(intent != null){
            if(requestCode == REQUEST_SELECT_DELIVERY){
                venuesBean = intent.getParcelableExtra("venues");
                String goodsId = data.get(position).getGoods().getId();
                String gymId = venuesBean.getId();
                cartPresent.updateGoodsDeliveryInfo(goodsId,gymId);
            }
        }
    }

    public void setSelfDeliveryResult(BaseBean baseBean){
        if(baseBean.getStatus() == Constant.OK){
            UpdateDeliveryBean bean = data.get(position);
            DeliveryBean deliveryBean = new DeliveryBean();
            deliveryBean.setInfo(venuesBean);
            deliveryBean.setType(DELIVERY_SELF);
            bean.setDeliveryInfo(deliveryBean);
            deliveryInfoAdapter.notifyDataSetChanged();
            updated = true;
        }
    }

    @Override
    public void setExpressResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            UpdateDeliveryBean bean = data.get(position);
            DeliveryBean deliveryBean = new DeliveryBean();
            deliveryBean.setType(DELIVERY_EXPRESS);
            bean.setDeliveryInfo(deliveryBean);
            deliveryInfoAdapter.notifyDataSetChanged();
            updated = true;
        }
    }

    @Override
    public void onBackPressed() {
        finishWithSendResult();
    }

    private void finishWithSendResult(){
        if(updated){
            setResult(RESULT_OK,null);
        }
        finish();
    }
}
