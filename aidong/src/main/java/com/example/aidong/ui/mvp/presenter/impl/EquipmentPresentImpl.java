package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong.entity.EquipmentBean;
import com.example.aidong.entity.data.EquipmentData;
import com.example.aidong.http.subscriber.CommonSubscriber;
import com.example.aidong.http.subscriber.RefreshSubscriber;
import com.example.aidong.http.subscriber.RequestMoreSubscriber;
import com.example.aidong.ui.mvp.model.EquipmentModel;
import com.example.aidong.ui.mvp.model.impl.EquipmentModelImpl;
import com.example.aidong.ui.mvp.presenter.EquipmentPresent;
import com.example.aidong.ui.mvp.view.EquipmentActivityView;
import com.example.aidong.utils.Constant;
import com.example.aidong.widget.customview.SwitcherLayout;

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
