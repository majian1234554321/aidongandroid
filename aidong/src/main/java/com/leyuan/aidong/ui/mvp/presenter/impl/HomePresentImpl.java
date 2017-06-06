package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.HomeBean;
import com.leyuan.aidong.entity.data.BrandData;
import com.leyuan.aidong.entity.data.HomeData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.HomeModel;
import com.leyuan.aidong.ui.mvp.model.impl.HomeModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.HomePresent;
import com.leyuan.aidong.ui.mvp.view.BrandActivityView;
import com.leyuan.aidong.ui.mvp.view.HomeFragmentView;
import com.leyuan.aidong.ui.mvp.view.LocationActivityView;
import com.leyuan.aidong.ui.mvp.view.StoreFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by song on 2016/8/13.
 */
public class HomePresentImpl implements HomePresent {
    private Context context;
    private HomeModel homeModel;

    private HomeFragmentView homeFragmentView;            //首页View层实体
    private BrandActivityView brandDetailActivityView;    //品牌详情View层实体
    private LocationActivityView locationActivityView;    //切换地址view层实体
    private StoreFragmentView storeFragmentView;

    private List<HomeBean> homeBeanList;

    public HomePresentImpl(Context context, HomeFragmentView view) {
        this.context = context;
        this.homeFragmentView = view;
        homeBeanList = new ArrayList<>();
        if(homeModel == null){
            homeModel = new HomeModelImpl(context);
        }
    }

    public HomePresentImpl(Context context, StoreFragmentView view) {
        this.context = context;
        this.storeFragmentView = view;
        homeBeanList = new ArrayList<>();
        if(homeModel == null){
            homeModel = new HomeModelImpl(context);
        }
    }

    public HomePresentImpl(Context context, BrandActivityView view) {
        this.context = context;
        this.brandDetailActivityView = view;
        if(homeModel == null){
            homeModel = new HomeModelImpl(context);
        }
    }

    public HomePresentImpl(Context context, LocationActivityView view) {
        this.context = context;
        this.locationActivityView = view;
        if(homeModel == null){
            homeModel = new HomeModelImpl(context);
        }
    }

    @Override
    public void getHomePopupBanner() {
        homeFragmentView.updatePopupBanner(homeModel.getHomePopupBanners());
    }

    @Override
    public void getOpenCity() {
        locationActivityView.setOpenCity(homeModel.getOpenCity());
    }

    @Override
    public void getHomeBanners() {

        homeFragmentView.updateBanner(homeModel.getHomeBanners());
    }

    @Override
    public void getStoreBanners() {
        storeFragmentView.updateBanners(homeModel.getStoreBanners());
    }

    @Override
    public void getSportHistory() {
        homeFragmentView.updateSportsHistory(homeModel.getSportsHistory());
    }

    @Override
    public void getCourseCategoryList() {
        homeFragmentView.updateCourseCategory(homeModel.getCourseCategory());
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout,String type) {
        homeModel.getRecommendList(new CommonSubscriber<HomeData>(context,switcherLayout) {
            @Override
            public void onNext(HomeData homeData) {
                if(homeData != null && homeData.getHome() != null){
                    homeBeanList = homeData.getHome();
                }
                switcherLayout.showContentLayout();
                if(homeFragmentView != null) {
                    homeFragmentView.updateRecyclerView(homeBeanList);
                }
                if(storeFragmentView != null){
                    storeFragmentView.updateRecyclerView(homeBeanList);
                }
            }
        },Constant.PAGE_FIRST,type);
    }

    @Override
    public void pullToRefreshHomeData(String type) {
        homeModel.getRecommendList(new RefreshSubscriber<HomeData>(context) {
            @Override
            public void onNext(HomeData homeBean) {
                if(homeBean != null && homeBean.getHome() != null){
                    if(homeFragmentView != null) {
                        homeFragmentView.updateRecyclerView(homeBean.getHome());
                    }
                    if(storeFragmentView != null){
                        storeFragmentView.updateRecyclerView(homeBean.getHome());
                    }
                }else {
                    if(homeFragmentView != null) {
                        homeFragmentView.showEmptyView();
                    }
                    if(storeFragmentView != null){
                        storeFragmentView.showEmptyView();
                    }
                }
            }
        }, Constant.PAGE_FIRST,type);
    }

    @Override
    public void requestMoreHomeData(RecyclerView recyclerView, final int pageSize, int page,String type) {
        homeModel.getRecommendList(new RequestMoreSubscriber<HomeData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(HomeData homeBean) {
                if(homeBean != null && homeBean.getHome()!= null){
                    if(homeFragmentView != null) {
                        homeFragmentView.updateRecyclerView(homeBean.getHome());
                    }
                    if(storeFragmentView != null){
                        storeFragmentView.updateRecyclerView(homeBean.getHome());
                    }
                }

                //没有更多数据了显示到底提示
                if(homeBean != null && homeBean.getHome()!= null && homeBean.getHome().size() < pageSize){
                    homeFragmentView.showEndFooterView();
                }
            }
        }, page,type);
    }

    @Override
    public void pullToRefreshBrandData(String id) {
        homeModel.getBrandDetail(new RefreshSubscriber<BrandData>(context) {
            @Override
            public void onNext(BrandData goodsBean) {
                if(goodsBean != null)
                brandDetailActivityView.updateRecyclerView(goodsBean.getItem());
            }
        },id, Constant.PAGE_FIRST);
    }

    @Override
    public void requestMorBrandeData(RecyclerView recyclerView, final int pageSize, int page, String id) {
        homeModel.getBrandDetail(new RequestMoreSubscriber<BrandData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(BrandData brandData) {
                if(brandData != null && brandData.getItem() != null) {
                    brandDetailActivityView.updateRecyclerView(brandData.getItem());
                }
                if(brandData != null && brandData.getItem().size() < pageSize){
                    brandDetailActivityView.showEndFooterView();
                }
            }
        },id,page);
    }
}
