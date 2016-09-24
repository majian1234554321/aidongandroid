package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong.entity.BrandBean;
import com.example.aidong.entity.data.BannerData;
import com.example.aidong.entity.data.HomeData;
import com.example.aidong.http.RetrofitHelper;
import com.example.aidong.http.RxHelper;
import com.example.aidong.http.api.HomeService;
import com.example.aidong.ui.mvp.model.HomeModel;

import rx.Subscriber;

/**
 * 首页
 * Created by song on 2016/8/13.
 */
public class HomeModelImpl implements HomeModel {
    private HomeService homeService;

    public HomeModelImpl() {
        homeService = RetrofitHelper.createApi(HomeService.class);
    }

    @Override
    public void getRecommendList(Subscriber<HomeData> subscriber, int page) {
        homeService.getRecommendList(page)
                .compose(RxHelper.<HomeData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getBrandDetail(Subscriber<BrandBean> subscriber, int id, int page) {
        homeService.getTypeDetail(id,page)
                .compose(RxHelper.<BrandBean>transform())
                .subscribe(subscriber);

    }

    @Override
    public void getBanners(Subscriber<BannerData> subscriber) {
        homeService.getBanners("homepage")
                .compose(RxHelper.<BannerData>transform())
                .subscribe(subscriber);
    }
}
