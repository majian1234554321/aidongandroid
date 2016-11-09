package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.data.DiscoverNewsData;
import com.leyuan.aidong.entity.data.DiscoverUserData;
import com.leyuan.aidong.entity.data.DiscoverVenuesData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.DiscoverService;
import com.leyuan.aidong.ui.mvp.model.DiscoverModel;

import rx.Subscriber;

/**
 * 发现
 * Created by song on 2016/8/29.
 */
public class DiscoverModelImpl implements DiscoverModel{
    private DiscoverService discoverService;

    public DiscoverModelImpl() {
        discoverService = RetrofitHelper.createApi(DiscoverService.class);
    }

    @Override
    public void getVenues(Subscriber<DiscoverVenuesData> subscriber, double lat, double lng, int page) {
        discoverService.getVenues(lat,lng,page)
                .compose(RxHelper.<DiscoverVenuesData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getUsers(Subscriber<DiscoverUserData> subscriber, double lat, double lng, int page, String gender, String type) {
        discoverService.getUsers(lat,lng,page,gender,type)
                .compose(RxHelper.<DiscoverUserData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getNews(Subscriber<DiscoverNewsData> subscriber, int page) {
        discoverService.getNews(page)
                .compose(RxHelper.<DiscoverNewsData>transform())
                .subscribe(subscriber);
    }
}
