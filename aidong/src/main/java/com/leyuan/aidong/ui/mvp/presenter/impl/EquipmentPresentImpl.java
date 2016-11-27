package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.EquipmentBean;
import com.leyuan.aidong.entity.data.EquipmentData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.EquipmentModel;
import com.leyuan.aidong.ui.mvp.model.impl.EquipmentModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.EquipmentPresent;
import com.leyuan.aidong.ui.mvp.view.EquipmentActivityView;
import com.leyuan.aidong.ui.mvp.view.GoodsFilterActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 装备
 * Created by song on 2016/8/15.
 */
public class EquipmentPresentImpl implements EquipmentPresent{
    private Context context;
    private EquipmentModel equipmentModel;
    private EquipmentActivityView equipmentActivityView;
    private GoodsFilterActivityView filterActivityView;
    private List<EquipmentBean> equipmentBeanList;

    public EquipmentPresentImpl(Context context, EquipmentActivityView equipmentActivityView) {
        this.context = context;
        this.equipmentActivityView = equipmentActivityView;
        equipmentModel = new EquipmentModelImpl(context);
        equipmentBeanList = new ArrayList<>();
    }

    public EquipmentPresentImpl(Context context, GoodsFilterActivityView filterActivityView) {
        this.context = context;
        this.filterActivityView = filterActivityView;
        equipmentModel = new EquipmentModelImpl(context);
        equipmentBeanList = new ArrayList<>();
    }

    @Override
    public void getCategory() {
        equipmentActivityView.setCategory(equipmentModel.getCategory());
    }

    @Override
    public void commonLoadRecommendData(final SwitcherLayout switcherLayout) {
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
    public void pullToRefreshRecommendData() {
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
    public void requestMoreRecommendData(RecyclerView recyclerView, final int pageSize, int page) {
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

    @Override
    public void commonLoadEquipmentData(final SwitcherLayout switcherLayout) {
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
        },Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshEquipmentData() {
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
        },Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreEquipmentData(RecyclerView recyclerView, final int pageSize, int page) {
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
        },page);
    }
}
