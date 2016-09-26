package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.BrandBean;
import com.leyuan.aidong.entity.data.BannerData;
import com.leyuan.aidong.entity.data.HomeData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.HomeService;
import com.leyuan.aidong.ui.mvp.model.HomeModel;

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
