package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.NurtureBean;
import com.leyuan.aidong.entity.data.NurtureData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.module.pay.AliPay;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.PayUtils;
import com.leyuan.aidong.module.pay.WeiXinPay;
import com.leyuan.aidong.ui.mvp.model.NurtureModel;
import com.leyuan.aidong.ui.mvp.model.impl.NurtureModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.NurturePresent;
import com.leyuan.aidong.ui.mvp.view.GoodsFilterActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public class NurturePresentImpl implements NurturePresent{
    private Context context;
    private NurtureModel nurtureModel;
    private GoodsFilterActivityView filterActivityView;
    private List<NurtureBean> nurtureBeanList = new ArrayList<>();

    public NurturePresentImpl(GoodsFilterActivityView filterActivityView, Context context) {
        this.context = context;
        this.filterActivityView = filterActivityView;
        if(nurtureModel == null) {
            nurtureModel = new NurtureModelImpl(context);
        }
    }

    public NurturePresentImpl(Context context) {
        this.context = context;
        if(nurtureModel == null) {
            nurtureModel = new NurtureModelImpl(context);
        }
    }

    @Override
    public void getCategory() {
        //nurtureActivityView.setCategory(nurtureModel.getCategory());
    }

    @Override
    public void commendLoadNurtureData(final SwitcherLayout switcherLayout, String brandId, String sort) {
        nurtureModel.getNurtures(new CommonSubscriber<NurtureData>(switcherLayout) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(nurtureDataBean != null && nurtureDataBean.getNutrition() != null){
                    nurtureBeanList = nurtureDataBean.getNutrition();
                }
                if(!nurtureBeanList.isEmpty()){
                    switcherLayout.showContentLayout();
                    filterActivityView.updateNurtureRecyclerView(nurtureBeanList);
                }else{
                    switcherLayout.showEmptyLayout();
                }
            }
        },Constant.PAGE_FIRST,brandId,sort);
    }

    @Override
    public void pullToRefreshNurtureData(String brandId, String sort) {
        nurtureModel.getNurtures(new RefreshSubscriber<NurtureData>(context) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(nurtureDataBean != null && nurtureDataBean.getNutrition() != null){
                    nurtureBeanList = nurtureDataBean.getNutrition();
                }
                if(!nurtureBeanList.isEmpty()){
                    filterActivityView.updateNurtureRecyclerView(nurtureBeanList);
                }
            }
        }, Constant.PAGE_FIRST,brandId,sort);
    }

    @Override
    public void requestMoreNurtureData(RecyclerView recyclerView, final int pageSize, int page,
                                       String brandId, String sort) {
        nurtureModel.getNurtures(new RequestMoreSubscriber<NurtureData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(nurtureDataBean != null && nurtureDataBean.getNutrition() != null){
                    nurtureBeanList = nurtureDataBean.getNutrition();
                }
                if(!nurtureBeanList.isEmpty()){
                    filterActivityView.updateNurtureRecyclerView(nurtureBeanList);
                }
                //没有更多数据了显示到底提示
                if( nurtureBeanList.size() < pageSize){
                    filterActivityView.showEndFooterView();
                }
            }
        },page,brandId,sort);
    }

    @Override
    public void buyNurtureImmediately(String skuCode, int amount, String coupon, String integral,
                                      String coin, String payType, String pickUpWay, String pickUpId,
                                      String pickUpDate,final PayInterface.PayListener listener) {
        nurtureModel.buyNurtureImmediately(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                PayUtils.pay(context,payOrderData.getOrder(),listener);
            }
        },skuCode,amount,coupon,integral,coin,payType,pickUpWay,pickUpId,pickUpDate);
    }
}
