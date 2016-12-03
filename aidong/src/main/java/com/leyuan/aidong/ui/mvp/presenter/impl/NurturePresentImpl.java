package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.NurtureBean;
import com.leyuan.aidong.entity.data.NurtureData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.NurtureModel;
import com.leyuan.aidong.ui.mvp.model.impl.NurtureModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.NurturePresent;
import com.leyuan.aidong.ui.mvp.view.GoodsFilterActivityView;
import com.leyuan.aidong.ui.mvp.view.NurtureActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public class NurturePresentImpl implements NurturePresent{
    private Context context;
    private NurtureModel nurtureModel;
    private NurtureActivityView nurtureActivityView;
    private GoodsFilterActivityView filterActivityView;
    private List<NurtureBean> nurtureBeanList = new ArrayList<>();

    public NurturePresentImpl(NurtureActivityView nurtureActivityView, Context context) {
        this.context = context;
        this.nurtureActivityView = nurtureActivityView;
        nurtureModel = new NurtureModelImpl(context);
    }

    public NurturePresentImpl(GoodsFilterActivityView filterActivityView, Context context) {
        this.context = context;
        this.filterActivityView = filterActivityView;
        nurtureModel = new NurtureModelImpl(context);
    }

    @Override
    public void getCategory() {
        nurtureActivityView.setCategory(nurtureModel.getCategory());
    }

    @Override
    public void commonLoadRecommendData(final SwitcherLayout switcherLayout) {
        nurtureModel.getNurtures(new CommonSubscriber<NurtureData>(switcherLayout) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(nurtureDataBean != null && nurtureDataBean.getNutrition() != null){
                    nurtureBeanList = nurtureDataBean.getNutrition();
                }
                if(!nurtureBeanList.isEmpty()){
                    nurtureActivityView.updateRecyclerView(nurtureBeanList);
                    switcherLayout.showContentLayout();
                }else{
                    switcherLayout.showEmptyLayout();
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshRecommendData() {
        nurtureModel.getNurtures(new RefreshSubscriber<NurtureData>(context) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(nurtureDataBean != null && nurtureDataBean.getNutrition() != null){
                    nurtureBeanList = nurtureDataBean.getNutrition();
                }
                if(!nurtureBeanList.isEmpty()){
                    nurtureActivityView.updateRecyclerView(nurtureBeanList);
                }
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreRecommendData(RecyclerView recyclerView, final int pageSize, int page) {
        nurtureModel.getNurtures(new RequestMoreSubscriber<NurtureData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(nurtureDataBean != null && nurtureDataBean.getNutrition() != null){
                    nurtureBeanList = nurtureDataBean.getNutrition();
                }
                if(!nurtureBeanList.isEmpty()){
                    nurtureActivityView.updateRecyclerView(nurtureBeanList);
                }
                //没有更多数据了显示到底提示
                if( nurtureBeanList.size() < pageSize){
                    nurtureActivityView.showEndFooterView();
                }
            }
        },page);
    }

    @Override
    public void commendLoadNurtureData(final SwitcherLayout switcherLayout) {
        nurtureModel.getNurtures(new CommonSubscriber<NurtureData>(switcherLayout) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(nurtureDataBean != null && nurtureDataBean.getNutrition() != null){
                    nurtureBeanList = nurtureDataBean.getNutrition();
                }
                if(!nurtureBeanList.isEmpty()){
                    filterActivityView.updateNurtureRecyclerView(nurtureBeanList);
                    switcherLayout.showContentLayout();
                }else{
                    switcherLayout.showEmptyLayout();
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshNurtureData() {
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
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreNurtureData(RecyclerView recyclerView, final int pageSize, int page) {
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
                    nurtureActivityView.showEndFooterView();
                }
            }
        },page);
    }
}
