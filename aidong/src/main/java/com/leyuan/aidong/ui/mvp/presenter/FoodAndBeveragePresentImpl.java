package com.leyuan.aidong.ui.mvp.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.NurtureBean;
import com.leyuan.aidong.entity.data.NurtureData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.PayUtils;
import com.leyuan.aidong.ui.mvp.model.NurtureModel;
import com.leyuan.aidong.ui.mvp.model.impl.NurtureModelImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsFilterActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/8/1.
 */
public class FoodAndBeveragePresentImpl {
    private Context context;
    private NurtureModel nurtureModel;
    private GoodsFilterActivityView filterActivityView;
    private List<NurtureBean> nurtureBeanList = new ArrayList<>();

    public FoodAndBeveragePresentImpl(GoodsFilterActivityView filterActivityView, Context context) {
        this.context = context;
        this.filterActivityView = filterActivityView;
        if (nurtureModel == null) {
            nurtureModel = new NurtureModelImpl(context);
        }
    }

    public void commendLoadFoodsData(final SwitcherLayout switcherLayout, String brandId, String sort, String gymId) {
        nurtureModel.getFoodAndBeverage(new CommonSubscriber<NurtureData>(context,switcherLayout) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(nurtureDataBean != null && nurtureDataBean.getFoods() != null){
                    nurtureBeanList = nurtureDataBean.getFoods();
                }
                if(!nurtureBeanList.isEmpty()){
                    switcherLayout.showContentLayout();
                    filterActivityView.updateGoodsRecyclerView(nurtureBeanList);
                }else{
                    filterActivityView.showEmptyView();
                }
            }
        }, Constant.PAGE_FIRST,brandId,sort,gymId);
    }

    public void pullToRefreshFoodsData(String brandId, String sort, String gymId) {
        nurtureModel.getFoodAndBeverage(new RefreshSubscriber<NurtureData>(context) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(nurtureDataBean != null && nurtureDataBean.getFoods() != null){
                    nurtureBeanList = nurtureDataBean.getFoods();
                }
                if(!nurtureBeanList.isEmpty()){
                    filterActivityView.updateGoodsRecyclerView(nurtureBeanList);
                }else {
                    filterActivityView.showEmptyView();
                }
            }
        }, Constant.PAGE_FIRST,brandId,sort,gymId);
    }

    public void requestMoreFoodsData(RecyclerView recyclerView, final int pageSize, int page,
                                     String brandId, String sort, String gymId) {
        nurtureModel.getFoodAndBeverage(new RequestMoreSubscriber<NurtureData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(nurtureDataBean != null && nurtureDataBean.getFoods() != null){
                    nurtureBeanList = nurtureDataBean.getFoods();
                }
                if(!nurtureBeanList.isEmpty()){
                    filterActivityView.updateGoodsRecyclerView(nurtureBeanList);
                }
                //没有更多数据了显示到底提示
                if( nurtureBeanList.size() < pageSize){
                    filterActivityView.showEndFooterView();
                }
            }
        },page,brandId,sort,gymId);
    }

    public void buyFoodsImmediately(String skuCode, int amount, String coupon, String integral,
                                      String coin, String payType, String pickUpWay, String pickUpId,
                                      String pickUpDate,final PayInterface.PayListener listener) {
        nurtureModel.buyNurtureImmediately(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                PayUtils.pay(context,payOrderData.getOrder(),listener);
            }
        },skuCode,amount,coupon,integral,coin,payType,pickUpWay,pickUpId,pickUpDate,null);
    }
}