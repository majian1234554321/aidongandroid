package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong .entity.NurtureBean;
import com.example.aidong .entity.data.PayOrderData;
import com.example.aidong .http.subscriber.ProgressSubscriber;
import com.example.aidong .module.pay.PayInterface;
import com.example.aidong .module.pay.PayUtils;
import com.example.aidong .ui.mvp.model.NurtureModel;
import com.example.aidong .ui.mvp.model.impl.NurtureModelImpl;
import com.example.aidong .ui.mvp.presenter.NurturePresent;
import com.example.aidong .ui.mvp.view.GoodsFilterActivityView;
import com.example.aidong .widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
@Deprecated
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

    @Deprecated
    @Override
    public void getCategory() {
        //nurtureActivityView.setCategory(nurtureModel.getCategory());
    }

    @Override
    public void commendLoadNurtureData(final SwitcherLayout switcherLayout, String brandId, String sort,String gymId) {
//        nurtureModel.getNurtures(new CommonSubscriber<NurtureData>(context,switcherLayout) {
//            @Override
//            public void onNext(NurtureData nurtureDataBean) {
//                if(nurtureDataBean != null && nurtureDataBean.getNutrition() != null){
//                    nurtureBeanList = nurtureDataBean.getNutrition();
//                }
//                if(!nurtureBeanList.isEmpty()){
//                    switcherLayout.showContentLayout();
//                    filterActivityView.updateGoodsRecyclerView(nurtureBeanList);
//                }else{
//                    filterActivityView.showEmptyView();
//                }
//            }
//        },Constant.PAGE_FIRST,brandId,sort,gymId);
    }

    @Override
    public void pullToRefreshNurtureData(String brandId, String sort,String gymId) {
//        nurtureModel.getNurtures(new RefreshSubscriber<NurtureData>(context) {
//            @Override
//            public void onNext(NurtureData nurtureDataBean) {
//                if(nurtureDataBean != null && nurtureDataBean.getNutrition() != null){
//                    nurtureBeanList = nurtureDataBean.getNutrition();
//                }
//                if(!nurtureBeanList.isEmpty()){
//                    filterActivityView.updateGoodsRecyclerView(nurtureBeanList);
//                }else {
//                    filterActivityView.showEmptyView();
//                }
//            }
//        }, Constant.PAGE_FIRST,brandId,sort,gymId);
    }

    @Override
    public void requestMoreNurtureData(RecyclerView recyclerView, final int pageSize, int page,
                                       String brandId, String sort,String gymId) {
//        nurtureModel.getNurtures(new RequestMoreSubscriber<NurtureData>(context,recyclerView,pageSize) {
//            @Override
//            public void onNext(NurtureData nurtureDataBean) {
//                if(nurtureDataBean != null && nurtureDataBean.getNutrition() != null){
//                    nurtureBeanList = nurtureDataBean.getNutrition();
//                }
//                if(!nurtureBeanList.isEmpty()){
//                    filterActivityView.updateGoodsRecyclerView(nurtureBeanList);
//                }
//                //没有更多数据了显示到底提示
//                if( nurtureBeanList.size() < pageSize){
//                    filterActivityView.showEndFooterView();
//                }
//            }
//        },page,brandId,sort,gymId);
    }



    @Override
    public void buyNurtureImmediately(String skuCode, int amount, String coupon, String integral,
                                      String coin, String payType, String pickUpWay, String pickUpId,
                                      String pickUpDate,String pick_up_period,final PayInterface.PayListener listener) {
        nurtureModel.buyNurtureImmediately(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                PayUtils.pay(context,payOrderData.getOrder(),listener);
            }
        },skuCode,amount,coupon,integral,coin,payType,pickUpWay,pickUpId,pickUpDate,pick_up_period,"0");
    }
}
