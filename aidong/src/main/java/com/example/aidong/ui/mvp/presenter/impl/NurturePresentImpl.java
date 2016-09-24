package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong.entity.NurtureBean;
import com.example.aidong.entity.data.NurtureData;
import com.example.aidong.http.subscriber.CommonSubscriber;
import com.example.aidong.http.subscriber.RefreshSubscriber;
import com.example.aidong.http.subscriber.RequestMoreSubscriber;
import com.example.aidong.ui.mvp.model.NurtureModel;
import com.example.aidong.ui.mvp.model.impl.NurtureModelImpl;
import com.example.aidong.ui.mvp.presenter.NurturePresent;
import com.example.aidong.ui.mvp.view.NurtureActivityView;
import com.example.aidong.utils.Constant;
import com.example.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public class NurturePresentImpl implements NurturePresent {
    private Context context;
    private NurtureModel nurtureModel;
    private NurtureActivityView nurtureActivityView;
    private List<NurtureBean> nurtureBeanList = new ArrayList<>();

    public NurturePresentImpl(NurtureActivityView nurtureActivityView, Context context) {
        this.context = context;
        this.nurtureActivityView = nurtureActivityView;
        nurtureModel = new NurtureModelImpl();
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout) {
        nurtureModel.getNurtures(new CommonSubscriber<NurtureData>(switcherLayout) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(null != nurtureDataBean){
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
    public void pullToRefreshData() {
        nurtureModel.getNurtures(new RefreshSubscriber<NurtureData>(context) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(null != nurtureDataBean){
                    nurtureBeanList = nurtureDataBean.getNutrition();
                }
                if(!nurtureBeanList.isEmpty()){
                    nurtureActivityView.updateRecyclerView(nurtureBeanList);
                }
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page) {
        nurtureModel.getNurtures(new RequestMoreSubscriber<NurtureData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(null != nurtureDataBean){
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
}
