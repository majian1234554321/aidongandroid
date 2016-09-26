package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.BrandBean;
import com.leyuan.aidong.entity.data.BannerData;
import com.leyuan.aidong.entity.data.HomeData;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.HomeModel;
import com.leyuan.aidong.ui.mvp.model.impl.HomeModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.HomePresent;
import com.leyuan.aidong.ui.mvp.view.BrandActivityView;
import com.leyuan.aidong.ui.mvp.view.HomeFragmentView;
import com.leyuan.aidong.utils.Constant;

/**
 * 首页
 * Created by song on 2016/8/13.
 */
public class HomePresentImpl implements HomePresent {
    private Context context;
    private HomeModel homeModel;

    private HomeFragmentView homeFragmentView;                  //首页View层实体
    private BrandActivityView brandDetailActivityView;    //品牌详情View层实体

    public HomePresentImpl(Context context, HomeFragmentView view) {
        this.context = context;
        this.homeFragmentView = view;
        if(homeModel == null){
            homeModel = new HomeModelImpl();
        }
    }

    public HomePresentImpl(Context context, BrandActivityView view) {
        this.context = context;
        this.brandDetailActivityView = view;
        if(homeModel == null){
            homeModel = new HomeModelImpl();
        }
    }

    @Override
    public void pullToRefreshHomeData() {
        homeModel.getRecommendList(new RefreshSubscriber<HomeData>(context) {
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
    public void requestMoreHomeData(RecyclerView recyclerView, final int pageSize, int page) {
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

    @Override
    public void getBanners() {
        homeModel.getBanners(new ProgressSubscriber<BannerData>(context,false) {
            @Override
            public void onNext(BannerData bannerData) {
                if (bannerData != null && bannerData.getBanners() != null && !bannerData.getBanners().isEmpty())
                homeFragmentView.updateBanner(bannerData.getBanners());
            }
        });
    }

    @Override
    public void pullToRefreshBrandData(int id) {
        homeModel.getBrandDetail(new RefreshSubscriber<BrandBean>(context) {
            @Override
            public void onNext(BrandBean brandBean) {
                brandDetailActivityView.updateRecyclerView(brandBean);
            }
        },id, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMorBrandeData(RecyclerView recyclerView, int pageSize, int page, int id) {
        homeModel.getBrandDetail(new RefreshSubscriber<BrandBean>(context) {
            @Override
            public void onNext(BrandBean brandBean) {
                /*if(brandBean != null && !brandBean.isEmpty()){
                    homeFragmentView.refreshData(homeBeanList);
                }

                //没有更多数据了显示到底提示
                if(homeBeanList != null && homeBeanList.size() < pageSize){
                    homeFragmentView.showEndFooterView();
                }*/
            }
        },id,page);
    }

}
