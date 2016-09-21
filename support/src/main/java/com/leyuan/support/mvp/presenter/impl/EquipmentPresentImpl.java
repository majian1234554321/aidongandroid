package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.EquipmentBean;
import com.leyuan.support.entity.data.EquipmentData;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.EquipmentModel;
import com.leyuan.support.mvp.model.impl.EquipmentModelImpl;
import com.leyuan.support.mvp.presenter.EquipmentPresent;
import com.leyuan.support.mvp.view.EquipmentActivityView;
import com.leyuan.support.util.Constant;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 装备
 * Created by song on 2016/8/15.
 */
public class EquipmentPresentImpl implements EquipmentPresent {
    private Context context;
    private EquipmentModel equipmentModel;
    private EquipmentActivityView equipmentActivityView;
    private List<EquipmentBean> equipmentBeanList;

    public EquipmentPresentImpl(Context context, EquipmentActivityView equipmentActivityView) {
        this.context = context;
        this.equipmentActivityView = equipmentActivityView;
        equipmentModel = new EquipmentModelImpl();
        equipmentBeanList = new ArrayList<>();
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout) {
        equipmentModel.getEquipments(new CommonSubscriber<EquipmentData>(switcherLayout) {
            @Override
            public void onNext(EquipmentData equipmentData) {
                if(equipmentData != null){
                    equipmentBeanList = equipmentData.getEquipment();
                }
                if(!equipmentBeanList.isEmpty()){
                    equipmentActivityView.updateRecyclerView(equipmentBeanList);
                    switcherLayout.showContentLayout();
                }else{
                    switcherLayout.showEmptyLayout();
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData() {
        equipmentModel.getEquipments(new RefreshSubscriber<EquipmentData>(context) {
            @Override
            public void onNext(EquipmentData equipmentData) {
                if( equipmentData != null){
                    equipmentBeanList = equipmentData.getEquipment();
                }
                if(!equipmentBeanList.isEmpty()){
                    equipmentActivityView.updateRecyclerView(equipmentBeanList);
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page) {
        equipmentModel.getEquipments(new RequestMoreSubscriber<EquipmentData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(EquipmentData equipmentData) {
                if(equipmentData != null){
                    equipmentBeanList = equipmentData.getEquipment();
                }
                if(!equipmentBeanList.isEmpty()){
                    equipmentActivityView.updateRecyclerView(equipmentBeanList);
                }
                //没有更多数据了显示到底提示
                if(equipmentBeanList != null && equipmentBeanList.size() < pageSize){
                    equipmentActivityView.showEndFooterView();
                }
            }
        },page);
    }
}
