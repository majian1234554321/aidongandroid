package com.leyuan.support.mvp.model.impl;

import com.leyuan.support.entity.BrandBean;
import com.leyuan.support.entity.HomeBean;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.HomeService;
import com.leyuan.support.mvp.model.HomeModel;

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
    public void getRecommendList(Subscriber<HomeBean> subscriber, int page) {
        homeService.getRecommendList(page)
                .compose(RxHelper.<HomeBean>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getBrandDetail(Subscriber<BrandBean> subscriber, int id, int page) {
        homeService.getTypeDetail(id,page)
                .compose(RxHelper.<BrandBean>transform())
                .subscribe(subscriber);

    }
}
