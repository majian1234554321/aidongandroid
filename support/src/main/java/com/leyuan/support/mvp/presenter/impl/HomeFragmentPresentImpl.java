package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.data.HomeData;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.HomeModel;
import com.leyuan.support.mvp.model.impl.HomeModelImpl;
import com.leyuan.support.mvp.presenter.HomeFragmentPresent;
import com.leyuan.support.mvp.view.HomeFragmentView;
import com.leyuan.support.util.Constant;

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
        homeModel.getRecommendList(new RefreshSubscriber<HomeData>(context,recyclerView) {
            @Override
            public void onNext(HomeData homeBean) {
                if(homeBean != null && homeBean.getHome() != null &&!homeBean.getHome().isEmpty()){
                    homeFragmentView.hideEmptyView();
                    homeFragmentView.showRecyclerView();
                    homeFragmentView.updateRecyclerView(homeBean.getHome());
                }else {
                    homeFragmentView.showEmptyView();
                    homeFragmentView.hideRecyclerView();
                }
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page) {
        homeModel.getRecommendList(new RequestMoreSubscriber<HomeData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(HomeData homeBean) {
                if(homeBean != null && homeBean.getHome()!= null && !homeBean.getHome().isEmpty()){
                    homeFragmentView.updateRecyclerView(homeBean.getHome());
                }

                //没有更多数据了显示到底提示
                if(homeBean != null && homeBean.getHome()!= null && homeBean.getHome().size() < pageSize){
                    homeFragmentView.showEndFooterView();
                }
            }
        }, 1);
    }
}
