package com.leyuan.aidong.ui.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

    private ArrayList<ShopBean> allShopBeanList = new ArrayList<>();

    public static void startForResult(Activity context, ArrayList<ShopBean> allShopBeanList, int selectedPosition,int requestCode) {
        Intent starter = new Intent(context, UpdateDeliveryInfoActivity.class);
        starter.putExtra("selectedPosition",selectedPosition);
        starter.putParcelableArrayListExtra("allShopBeanList",allShopBeanList);
        context.startActivityForResult(starter,requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delivery_info);
        cartPresent = new CartPresentImpl(this,this);
        if(getIntent() != null){
            int selectedPosition = getIntent().getIntExtra("selectedPosition",0);
            allShopBeanList = getIntent().getParcelableArrayListExtra("allShopBeanList");
            ShopBean updateShopBean = allShopBeanList.get(selectedPosition);
            for (int i = 0; i < updateShopBean.getItem().size(); i++) {
                UpdateDeliveryBean bean = new UpdateDeliveryBean();
                bean.setGoods(updateShopBean.getItem().get(i));
                DeliveryBean deliveryBean = updateShopBean.getPickUp();
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
        if(isChangeDeliveryInfoLocal()) {
            changeExpressInfoLocal();
        }else{
            String cartId = data.get(position).getGoods().getId();
            String count = data.get(position).getGoods().getAmount();
            cartPresent.updateGoodsDeliveryInfo(cartId,count, DELIVERY_EXPRESS);
        }
    }

    @Override
    public void onSelfDeliveryClick(int position) {
        this.position = position;
        UpdateDeliveryBean bean = data.get(position);
        SelfDeliveryVenuesActivity.startForResult(this,bean.getGoods().getProductId(),
                bean.getGoods().getProductType(),bean.getDeliveryInfo(),REQUEST_UPDATE_DELIVERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(intent != null){
            if(requestCode == REQUEST_UPDATE_DELIVERY){
                venuesBean = intent.getParcelableExtra("venues");
                if(isChangeDeliveryInfoLocal()){
                   changeSelfDeliveryInfoLocal();
                }else {
                    String cartId = data.get(position).getGoods().getId();
                    String count = data.get(position).getGoods().getAmount();
                    String gymId = venuesBean.getId();
                    cartPresent.updateGoodsDeliveryInfo(cartId,count,gymId);
                }
            }
        }
    }

    public void setSelfDeliveryResult(BaseBean baseBean){
        if(baseBean.getStatus() == Constant.OK){
           changeSelfDeliveryInfoLocal();
        }
    }

    @Override
    public void setExpressResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            changeExpressInfoLocal();
        }
    }

    /**
     * 判断是从立即购买还是购物车中的数据改变自提信息
     * @return true : 立即购买本地改变自提信息
     */
    private boolean isChangeDeliveryInfoLocal(){
        return TextUtils.isEmpty(data.get(position).getGoods().getId());
    }


    private void changeExpressInfoLocal(){
        UpdateDeliveryBean bean = data.get(position);
        DeliveryBean deliveryBean = new DeliveryBean();
        deliveryBean.setType(DELIVERY_EXPRESS);
        VenuesBean venuesBean = new VenuesBean();
        venuesBean.setName("仓库发货");
        deliveryBean.setInfo(venuesBean);
        bean.setDeliveryInfo(deliveryBean);
        deliveryInfoAdapter.notifyItemChanged(position);
        updateShopList(bean);
    }


    private void changeSelfDeliveryInfoLocal(){
        UpdateDeliveryBean bean = data.get(position);
        DeliveryBean deliveryBean = new DeliveryBean();
        deliveryBean.setInfo(venuesBean);
        deliveryBean.setType(DELIVERY_SELF);
        bean.setDeliveryInfo(deliveryBean);
        deliveryInfoAdapter.notifyItemChanged(position);
        updateShopList(bean);
    }

    @Override
    public void onBackPressed() {
        finishWithSendResult();
    }

    private void updateShopList(UpdateDeliveryBean bean){

        //1.移除该商品,移除后若该商店无商品 移除该商店
        int shopIndex = 0;
        int goodsIndex = 0;
        for (int i = 0; i < allShopBeanList.size(); i++) {
            for (int j = 0; j < allShopBeanList.get(i).getItem().size(); j++) {
                if(!TextUtils.isEmpty(allShopBeanList.get(i).getItem().get(j).getId())
                        && !TextUtils.isEmpty(bean.getGoods().getId())
                        && allShopBeanList.get(i).getItem().get(j).getId().equals(bean.getGoods().getId())){
                    shopIndex = i;
                    goodsIndex = j;
                }
            }
        }
        List<GoodsBean> goodsItems = allShopBeanList.get(shopIndex).getItem();
        goodsItems.remove(goodsIndex);
        if(allShopBeanList.get(shopIndex).getItem().isEmpty()){
            allShopBeanList.remove(shopIndex);
        }


        //2.若该商品的自提场馆已经存在,将该商品添加到该自提场馆
        boolean isExist = false;
        for (int i = 0; i < allShopBeanList.size(); i++) {
            if(DELIVERY_EXPRESS.equals(allShopBeanList.get(i).getPickUp().getType())
                    &&DELIVERY_EXPRESS .equals(bean.getDeliveryInfo().getType())){
                isExist = true;
                allShopBeanList.get(i).getItem().add(bean.getGoods());
                break;
            }else if(DELIVERY_SELF.equals(allShopBeanList.get(i).getPickUp().getType())
                    &&DELIVERY_SELF .equals(bean.getDeliveryInfo().getType())
                    && allShopBeanList.get(i).getPickUp().getInfo().getName()
                    .equals(bean.getDeliveryInfo().getInfo().getName())){
                isExist = true;
                allShopBeanList.get(i).getItem().add(bean.getGoods());
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
            allShopBeanList.add(shopBean);
        }
    }

    private void finishWithSendResult(){
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("shopList",allShopBeanList);
        setResult(RESULT_OK,intent);

        finish();
    }
}
