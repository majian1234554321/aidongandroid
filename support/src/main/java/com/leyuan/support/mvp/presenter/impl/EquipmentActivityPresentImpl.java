package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.EquipmentBean;
import com.leyuan.support.entity.data.EquipmentData;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.EquipmentModel;
import com.leyuan.support.mvp.model.impl.EquipmentModelImpl;
import com.leyuan.support.mvp.presenter.EquipmentActivityPresent;
import com.leyuan.support.mvp.view.EquipmentActivityView;
import com.leyuan.support.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 装备
 * Created by song on 2016/8/15.
 */
public class EquipmentActivityPresentImpl implements EquipmentActivityPresent{
    private Context context;
    private EquipmentModel equipmentModel;
    private EquipmentActivityView equipmentActivityView;
    private List<EquipmentBean> equipmentBeanList = new ArrayList<>();

    public EquipmentActivityPresentImpl(Context context,EquipmentActivityView equipmentActivityView) {
        this.context = context;
        this.equipmentActivityView = equipmentActivityView;
        equipmentModel = new EquipmentModelImpl();
    }

    @Override
    public void pullToRefreshData(RecyclerView recyclerView) {
        equipmentModel.getEquipments(new RefreshSubscriber<EquipmentData>(context,recyclerView) {
            @Override
            public void onNext(EquipmentData equipmentData) {
                if(equipmentData != null){
                    equipmentBeanList = equipmentData.getEquipment();
                    equipmentActivityView.updateRecyclerView(equipmentBeanList);
                }

                if(equipmentBeanList != null && equipmentBeanList.size() > 0){
                    equipmentActivityView.showRecyclerView();
                    equipmentActivityView.hideListEmptyView();
                } else {
                    equipmentActivityView.showListEmptyView();
                    equipmentActivityView.hideRecyclerView();
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
