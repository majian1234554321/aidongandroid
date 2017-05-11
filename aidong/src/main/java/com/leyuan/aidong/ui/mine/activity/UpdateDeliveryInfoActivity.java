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
import com.leyuan.aidong.entity.GoodsBean;
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
import static com.leyuan.aidong.utils.Constant.REQUEST_UPDATE_DELIVERY;

/**
 * 确认订单界面修改配送信息
 * Created by song on 2017/2/8.
 */
public class UpdateDeliveryInfoActivity extends BaseActivity implements View.OnClickListener,
        UpdateDeliveryInfoAdapter.SelfDeliveryShopListener ,UpdateDeliveryInfoActivityView{

    private ImageView ivBack;
    private UpdateDeliveryInfoAdapter deliveryInfoAdapter;
    private List<UpdateDeliveryBean> data = new ArrayList<>();
    private int position;
    private VenuesBean venuesBean;
    private CartPresent cartPresent;

    private ArrayList<ShopBean> updatedShopList = new ArrayList<>();

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
            updatedShopList.add(shopBean);
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
        intent.putExtra("deliveryBean",data.get(position).getDeliveryInfo());
        startActivityForResult(intent,REQUEST_UPDATE_DELIVERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(intent != null){
            if(requestCode == REQUEST_UPDATE_DELIVERY){
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
            updateShopList(bean);
        }
    }

    @Override
    public void setExpressResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            UpdateDeliveryBean bean = data.get(position);
            DeliveryBean deliveryBean = new DeliveryBean();
            deliveryBean.setType(DELIVERY_EXPRESS);
            VenuesBean venuesBean = new VenuesBean();
            venuesBean.setName("仓库发货");
            deliveryBean.setInfo(venuesBean);
            bean.setDeliveryInfo(deliveryBean);
            deliveryInfoAdapter.notifyDataSetChanged();
            updateShopList(bean);
        }
    }

    @Override
    public void onBackPressed() {
        finishWithSendResult();
    }

    private void updateShopList(UpdateDeliveryBean bean){

        //1.移除该商品
        int shopIndex = 0;
        int goodsIndex = 0;
        for (int i = 0; i < updatedShopList.size(); i++) {
            for (int j = 0; j < updatedShopList.get(i).getItem().size(); j++) {
                if(updatedShopList.get(i).getItem().get(j).getId().equals(bean.getGoods().getId())){
                    shopIndex = i;
                    goodsIndex = j;
                }
            }
        }
        List<GoodsBean> goodsItems = updatedShopList.get(shopIndex).getItem();
        goodsItems.remove(goodsIndex);

        //2.若该商品的自提场馆已经存在,将该商品添加到该自提场馆
        boolean isExist = false;
        for (int i = 0; i < updatedShopList.size(); i++) {
            if(DELIVERY_EXPRESS.equals(updatedShopList.get(i).getPickUp().getType())
                    &&DELIVERY_EXPRESS .equals(bean.getDeliveryInfo().getType())){
                isExist = true;
                updatedShopList.get(i).getItem().add(bean.getGoods());
                break;
            }else if(DELIVERY_SELF.equals(updatedShopList.get(i).getPickUp().getType())
                    &&DELIVERY_SELF .equals(bean.getDeliveryInfo().getType())
                    && updatedShopList.get(i).getPickUp().getInfo().getName()
                    .equals(bean.getDeliveryInfo().getInfo().getName())){
                isExist = true;
                updatedShopList.get(i).getItem().add(bean.getGoods());
                break;
            }
        }

        //3.该商品的自提场馆不存在,新建该商店并添加到商店列表
        if(!isExist){
            ShopBean shopBean = new ShopBean();
            List<GoodsBean> goodsBeanList = new ArrayList<>();
            goodsBeanList.add(bean.getGoods());
            shopBean.setItem(goodsBeanList);
            shopBean.setPickUp(bean.getDeliveryInfo());
            updatedShopList.add(shopBean);
        }
    }

    private void finishWithSendResult(){
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("shopList", updatedShopList);
        setResult(RESULT_OK,intent);

        finish();
    }
}
