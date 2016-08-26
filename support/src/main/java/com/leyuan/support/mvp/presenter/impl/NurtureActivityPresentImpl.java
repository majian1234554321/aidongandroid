package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.NurtureBean;
import com.leyuan.support.entity.data.NurtureData;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.NurtureModel;
import com.leyuan.support.mvp.model.impl.NurtureModelImpl;
import com.leyuan.support.mvp.presenter.NurtureActivityPresent;
import com.leyuan.support.mvp.view.NurtureActivityView;
import com.leyuan.support.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public class NurtureActivityPresentImpl implements NurtureActivityPresent{
    private Context context;
    private NurtureModel nurtureModel;
    private NurtureActivityView nurtureActivityView;
    private List<NurtureBean> nurtureBeanList = new ArrayList<>();

    public NurtureActivityPresentImpl(NurtureActivityView nurtureActivityView, Context context) {
        this.context = context;
        this.nurtureActivityView = nurtureActivityView;
        nurtureModel = new NurtureModelImpl();
    }

    @Override
    public void pullToRefreshData(RecyclerView recyclerView) {
        nurtureModel.getNurtures(new RefreshSubscriber<NurtureData>(context,recyclerView) {
            @Override
            public void onNext(NurtureData nurtureDataBean) {
                if(null != nurtureDataBean){
                    nurtureBeanList = nurtureDataBean.getNutrition();
                }

                if(nurtureBeanList.isEmpty()){
                    nurtureActivityView.showEmptyView();
                    nurtureActivityView.hideRecyclerView();
                }else {
                    nurtureActivityView.hideEmptyView();
                    nurtureActivityView.showRecyclerView();
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
                if(nurtureBeanList != null && nurtureBeanList.size() < pageSize){
                    nurtureActivityView.showEndFooterView();
                }
            }
        },page);
    }
}
