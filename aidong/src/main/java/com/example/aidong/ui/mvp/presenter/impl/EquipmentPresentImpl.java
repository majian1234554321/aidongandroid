package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong .entity.EquipmentBean;
import com.example.aidong .entity.data.PayOrderData;
import com.example.aidong .http.subscriber.ProgressSubscriber;
import com.example.aidong .module.pay.PayInterface;
import com.example.aidong .module.pay.PayUtils;
import com.example.aidong .ui.mvp.model.EquipmentModel;
import com.example.aidong .ui.mvp.model.impl.EquipmentModelImpl;
import com.example.aidong .ui.mvp.presenter.EquipmentPresent;
import com.example.aidong .ui.mvp.view.GoodsFilterActivityView;
import com.example.aidong .widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 装备
 * Created by song on 2016/8/15.
 */
@Deprecated
public class EquipmentPresentImpl implements EquipmentPresent{
    private Context context;
    private EquipmentModel equipmentModel;
    private GoodsFilterActivityView filterActivityView;
    private List<EquipmentBean> equipmentBeanList;

    public EquipmentPresentImpl(Context context, GoodsFilterActivityView filterActivityView) {
        this.context = context;
        this.filterActivityView = filterActivityView;
        equipmentModel = new EquipmentModelImpl(context);
        equipmentBeanList = new ArrayList<>();
    }


    @Override
    public void commonLoadEquipmentData(final SwitcherLayout switcherLayout, String brandId, String sort,String gymId) {
//        equipmentModel.getEquipments(new CommonSubscriber<EquipmentData>(context,switcherLayout) {
//            @Override
//            public void onNext(EquipmentData equipmentData) {
//                if(equipmentData != null){
//                    equipmentBeanList = equipmentData.getEquipment();
//                }
//                if(!equipmentBeanList.isEmpty()){
//                    filterActivityView.updateGoodsRecyclerView(equipmentBeanList);
//                    switcherLayout.showContentLayout();
//                }else{
//                    filterActivityView.showEmptyView();
//                }
//            }
//        },Constant.PAGE_FIRST,brandId,sort,gymId);
    }

    @Override
    public void pullToRefreshEquipmentData(String brandId, String sort,String gymId) {
//        equipmentModel.getEquipments(new RefreshSubscriber<EquipmentData>(context) {
//            @Override
//            public void onNext(EquipmentData equipmentData) {
//                if( equipmentData != null){
//                    equipmentBeanList = equipmentData.getEquipment();
//                }
//                if(!equipmentBeanList.isEmpty()){
//                    filterActivityView.updateGoodsRecyclerView(equipmentBeanList);
//                }else {
//                    filterActivityView.showEmptyView();
//                }
//            }
//        },Constant.PAGE_FIRST,brandId,sort,gymId);
    }

    @Override
    public void requestMoreEquipmentData(RecyclerView recyclerView, final int pageSize, int page,
                                         String brandId, String sort,String gymId) {
//        equipmentModel.getEquipments(new RequestMoreSubscriber<EquipmentData>(context,recyclerView,pageSize) {
//            @Override
//            public void onNext(EquipmentData equipmentData) {
//                if(equipmentData != null){
//                    equipmentBeanList = equipmentData.getEquipment();
//                }
//                if(!equipmentBeanList.isEmpty()){
//                    filterActivityView.updateGoodsRecyclerView(equipmentBeanList);
//                }
//                //没有更多数据了显示到底提示
//                if(equipmentBeanList != null && equipmentBeanList.size() < pageSize){
//                    filterActivityView.showEndFooterView();
//                }
//            }
//        },page,brandId,sort,gymId);
    }

    @Override
    public void buyEquipmentImmediately(String skuCode, int amount, String coupon, String integral,
                                        String coin, String payType, String pickUpWay, String pickUpId,
                                        String pickUpDate, final PayInterface.PayListener listener) {
        equipmentModel.buyEquipmentImmediately(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                PayUtils.pay(context,payOrderData.getOrder(),listener);
            }
        },skuCode,amount,coupon,integral,coin,payType,pickUpWay,pickUpId,pickUpDate);
    }
}
