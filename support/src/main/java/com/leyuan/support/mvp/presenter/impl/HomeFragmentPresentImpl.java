package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.HomeBean;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.HomeModel;
import com.leyuan.support.mvp.model.impl.HomeModelImpl;
import com.leyuan.support.mvp.presenter.HomeFragmentPresent;
import com.leyuan.support.mvp.view.HomeFragmentView;
import com.leyuan.support.util.Constant;

import java.util.List;

/**
 * 首页
 * Created by song on 2016/8/13.
 */
public class HomeFragmentPresentImpl implements HomeFragmentPresent{
    private Context context;
    private HomeFragmentView homeFragmentView;
    private HomeModel homeModel;

    public HomeFragmentPresentImpl(Context context, HomeFragmentView homeFragmentView) {
        this.context = context;
        this.homeFragmentView = homeFragmentView;
        homeModel = new HomeModelImpl();
    }

    @Override
    public void pullToRefreshData(final RecyclerView recyclerView) {
        homeModel.getRecommendList(new RefreshSubscriber<List<HomeBean>>(context,recyclerView) {
            @Override
            public void onNext(List<HomeBean> homeBeanList) {
                if(homeBeanList != null && homeBeanList.isEmpty()){
                    homeFragmentView.showEmptyView();
                    homeFragmentView.hideRecyclerView();
                }else {
                    homeFragmentView.hideEmptyView();
                    homeFragmentView.showRecyclerView();
                    homeFragmentView.updateRecyclerView(homeBeanList);
                }
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page) {
        homeModel.getRecommendList(new RequestMoreSubscriber<List<HomeBean>>(context,recyclerView,pageSize) {
            @Override
            public void onNext(List<HomeBean> homeBeanList) {
                if(homeBeanList != null && !homeBeanList.isEmpty()){
                    homeFragmentView.updateRecyclerView(homeBeanList);
                }

                //没有更多数据了显示到底提示
                if(homeBeanList != null && homeBeanList.size() < pageSize){
                    homeFragmentView.showEndFooterView();
                }
            }
        }, page);
    }
}
