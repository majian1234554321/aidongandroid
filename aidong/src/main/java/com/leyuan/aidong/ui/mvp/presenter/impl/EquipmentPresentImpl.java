package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.EquipmentBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.data.EquipmentData;
import com.leyuan.aidong.entity.data.GoodsData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.module.pay.AliPay;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.WeiXinPay;
import com.leyuan.aidong.ui.mvp.model.EquipmentModel;
import com.leyuan.aidong.ui.mvp.model.RecommendModel;
import com.leyuan.aidong.ui.mvp.model.impl.EquipmentModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.RecommendModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.EquipmentPresent;
import com.leyuan.aidong.ui.mvp.view.ConfirmOrderActivityView;
import com.leyuan.aidong.ui.mvp.view.EquipmentActivityView;
import com.leyuan.aidong.ui.mvp.view.GoodsFilterActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 装备
 * Created by song on 2016/8/15.
 */
public class EquipmentPresentImpl implements EquipmentPresent{
    private static final String TYPE = "equipment";
    private Context context;
    private EquipmentModel equipmentModel;
    private RecommendModel recommendModel;
    private EquipmentActivityView equipmentActivityView;
    private GoodsFilterActivityView filterActivityView;
    private ConfirmOrderActivityView confirmOrderActivityView;
    private List<EquipmentBean> equipmentBeanList;

    public EquipmentPresentImpl(Context context, EquipmentActivityView equipmentActivityView) {
        this.context = context;
        this.equipmentActivityView = equipmentActivityView;
        equipmentModel = new EquipmentModelImpl(context);
        equipmentBeanList = new ArrayList<>();
        if(recommendModel == null){
            recommendModel = new RecommendModelImpl();
        }
    }

    public EquipmentPresentImpl(Context context, GoodsFilterActivityView filterActivityView) {
        this.context = context;
        this.filterActivityView = filterActivityView;
        equipmentModel = new EquipmentModelImpl(context);
        equipmentBeanList = new ArrayList<>();
    }

    public EquipmentPresentImpl(ConfirmOrderActivityView confirmOrderActivityView, Context context) {
        this.context = context;
        this.confirmOrderActivityView = confirmOrderActivityView;
        if(equipmentModel == null) {
            equipmentModel = new EquipmentModelImpl(context);
        }
    }

    @Override
    public void getCategory() {
        equipmentActivityView.setCategory(equipmentModel.getCategory());
    }

    @Override
    public void commonLoadRecommendData(final SwitcherLayout switcherLayout) {
        recommendModel.getRecommendGoods(new CommonSubscriber<GoodsData>(switcherLayout) {
            @Override
            public void onNext(GoodsData goodsData) {
                List<GoodsBean> goodsList = new ArrayList<>();
                if(goodsData != null && goodsData.getProduct() != null){
                    goodsList = goodsData.getProduct();
                }
                if(!goodsList.isEmpty()){
                    equipmentActivityView.updateRecyclerView(goodsList);
                    switcherLayout.showContentLayout();
                }else{
                    switcherLayout.showEmptyLayout();
                }
            }
        },TYPE, Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshRecommendData() {
        recommendModel.getRecommendGoods(new RefreshSubscriber<GoodsData>(context) {
            @Override
            public void onNext(GoodsData goodsData) {
                List<GoodsBean> goodsList = new ArrayList<>();
                if(goodsData != null && goodsData.getProduct() != null){
                    goodsList = goodsData.getProduct();
                }
                if(!goodsList.isEmpty()){
                    equipmentActivityView.updateRecyclerView(goodsList);
                }else {
                    equipmentActivityView.showEmptyView();
                }
            }
        },TYPE,Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreRecommendData(RecyclerView recyclerView, final int pageSize, int page) {
        recommendModel.getRecommendGoods(new RequestMoreSubscriber<GoodsData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(GoodsData goodsData) {
                List<GoodsBean> goodsList = new ArrayList<>();
                if(goodsData != null && goodsData.getProduct() != null){
                    goodsList = goodsData.getProduct();
                }
                if(!goodsList.isEmpty()){
                    equipmentActivityView.updateRecyclerView(goodsList);
                }
                //没有更多数据了显示到底提示
                if( goodsList.size() < pageSize){
                    equipmentActivityView.showEndFooterView();
                }
            }
        },TYPE,page);
    }

    @Override
    public void commonLoadEquipmentData(final SwitcherLayout switcherLayout, String brandId,
                                        String priceSort, String countSort, String heatSort) {
        equipmentModel.getEquipments(new CommonSubscriber<EquipmentData>(switcherLayout) {
            @Override
            public void onNext(EquipmentData equipmentData) {
                if(equipmentData != null){
                    equipmentBeanList = equipmentData.getEquipment();
                }
                if(!equipmentBeanList.isEmpty()){
                    filterActivityView.updateEquipmentRecyclerView(equipmentBeanList);
                    switcherLayout.showContentLayout();
                }else{
                    switcherLayout.showEmptyLayout();
                }
            }
        },Constant.FIRST_PAGE,brandId,priceSort,countSort,heatSort);
    }

    @Override
    public void pullToRefreshEquipmentData(String brandId, String priceSort, String countSort, String heatSort) {
        equipmentModel.getEquipments(new RefreshSubscriber<EquipmentData>(context) {
            @Override
            public void onNext(EquipmentData equipmentData) {
                if( equipmentData != null){
                    equipmentBeanList = equipmentData.getEquipment();
                }
                if(!equipmentBeanList.isEmpty()){
                    filterActivityView.updateEquipmentRecyclerView(equipmentBeanList);
                }
            }
        },Constant.FIRST_PAGE,brandId,priceSort,countSort,heatSort);
    }

    @Override
    public void requestMoreEquipmentData(RecyclerView recyclerView, final int pageSize, int page,
                                         String brandId, String priceSort, String countSort, String heatSort) {
        equipmentModel.getEquipments(new RequestMoreSubscriber<EquipmentData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(EquipmentData equipmentData) {
                if(equipmentData != null){
                    equipmentBeanList = equipmentData.getEquipment();
                }
                if(!equipmentBeanList.isEmpty()){
                    filterActivityView.updateEquipmentRecyclerView(equipmentBeanList);
                }
                //没有更多数据了显示到底提示
                if(equipmentBeanList != null && equipmentBeanList.size() < pageSize){
                    equipmentActivityView.showEndFooterView();
                }
            }
        },page,brandId,priceSort,countSort,heatSort);
    }

    @Override
    public void buyEquipmentImmediately(String skuCode, int amount, String pickUp, String pickUpId,
                                        final PayInterface.PayListener listener) {
        equipmentModel.buyEquipmentImmediately(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                String payType = payOrderData.getOrder().getPayType();
                PayInterface payInterface = "alipay".equals(payType) ? new AliPay(context,listener)
                        : new WeiXinPay(context,listener);
                payInterface.payOrder(payOrderData.getOrder());
            }
        },skuCode,amount,pickUp,pickUpId);
    }
}
