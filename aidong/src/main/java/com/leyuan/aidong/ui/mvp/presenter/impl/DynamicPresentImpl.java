package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.DynamicModel;
import com.leyuan.aidong.ui.mvp.model.impl.DynamicModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.view.SportCircleFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.List;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public class DynamicPresentImpl implements DynamicPresent{
    private Context context;
    private DynamicModel dynamicModel;
    private SportCircleFragmentView sportCircleFragmentView;

    public DynamicPresentImpl(Context context, SportCircleFragmentView sportCircleFragmentView) {
        this.context = context;
        this.sportCircleFragmentView = sportCircleFragmentView;
        if(dynamicModel == null){
            dynamicModel = new DynamicModelImpl();
        }
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout) {
        dynamicModel.getDynamics(new CommonSubscriber<List<DynamicBean>>(switcherLayout) {
            @Override
            public void onNext(List<DynamicBean> dynamicBeanList) {
                if (dynamicBeanList != null && !dynamicBeanList.isEmpty()) {
                    switcherLayout.showContentLayout();
                    sportCircleFragmentView.updateRecyclerView(dynamicBeanList);
                } else {
                    switcherLayout.showEmptyLayout();
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData() {
        dynamicModel.getDynamics(new RefreshSubscriber<List<DynamicBean>>(context) {
            @Override
            public void onNext(List<DynamicBean> dynamicBeanList) {
                if (dynamicBeanList != null && !dynamicBeanList.isEmpty()) {
                    sportCircleFragmentView.updateRecyclerView(dynamicBeanList);
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int size, int page) {
        dynamicModel.getDynamics(new RequestMoreSubscriber<List<DynamicBean>>(context,recyclerView,size) {
            @Override
            public void onNext(List<DynamicBean> dynamicBeanList) {
                if (dynamicBeanList != null && !dynamicBeanList.isEmpty()) {
                    sportCircleFragmentView.updateRecyclerView(dynamicBeanList);
                }
                //没有更多数据了显示到底提示
                if (dynamicBeanList != null && dynamicBeanList.size() < size) {
                    sportCircleFragmentView.showEndFooterView();
                }
            }
        },page);
    }
}
