package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.BrandBean;
import com.leyuan.aidong.entity.HomeBean;
import com.leyuan.aidong.entity.data.BannerData;
import com.leyuan.aidong.entity.data.HomeData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.HomeModel;
import com.leyuan.aidong.ui.mvp.model.impl.HomeModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.HomePresent;
import com.leyuan.aidong.ui.mvp.view.BrandActivityView;
import com.leyuan.aidong.ui.mvp.view.HomeFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

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

    private List<HomeBean> homeBeanList;

    public HomePresentImpl(Context context, HomeFragmentView view) {
        this.context = context;
        this.homeFragmentView = view;
        homeBeanList = new ArrayList<>();
        if(homeModel == null){
            homeModel = new HomeModelImpl();
        }
    }

    public HomePresentImpl(Context context, BrandActivityView view) {
        this.context = context;
        this.brandDetailActivityView = view;
        homeBeanList = new ArrayList<>();
        if(homeModel == null){
            homeModel = new HomeModelImpl();
        }
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout) {
        homeModel.getRecommendList(new CommonSubscriber<HomeData>(switcherLayout) {
            @Override
            public void onNext(HomeData homeData) {
                if(homeData != null && homeData.getHome() != null){
                    homeBeanList = homeData.getHome();
                }
                switcherLayout.showContentLayout();
                homeFragmentView.updateRecyclerView(homeBeanList);
             /*   if(!homeBeanList.isEmpty()){
                    homeFragmentView.updateRecyclerView(homeBeanList);
                    switcherLayout.showContentLayout();
                } else{
                    switcherLayout.showEmptyLayout();
                }*/

            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshHomeData() {
        homeModel.getRecommendList(new RefreshSubscriber<HomeData>(context) {
            @Override
            public void onNext(HomeData homeBean) {
                if(homeBean != null && homeBean.getHome() != null){
                    homeFragmentView.updateRecyclerView(homeBean.getHome());
                }
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreHomeData(RecyclerView recyclerView, final int pageSize, int page) {
        homeModel.getRecommendList(new RequestMoreSubscriber<HomeData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(HomeData homeBean) {
                if(homeBean != null && homeBean.getHome()!= null){
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
                if (bannerData != null && bannerData.getBanners() != null)
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