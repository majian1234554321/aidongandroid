package com.leyuan.support.mvp.model.impl;


import com.leyuan.support.entity.data.CoachData;
import com.leyuan.support.entity.data.CourseData;
import com.leyuan.support.entity.data.VenuesData;
import com.leyuan.support.entity.data.VenuesDetailData;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.VenuesService;
import com.leyuan.support.mvp.model.VenuesModel;

import rx.Subscriber;

/**
 * 场馆
 * Created by Song on 2016/8/2.
 */
public class VenuesModelImpl implements VenuesModel {
    private VenuesService venuesService;

    public VenuesModelImpl() {
        venuesService =  RetrofitHelper.createApi(VenuesService.class);
    }

    @Override
    public void getVenues(Subscriber<VenuesData> subscriber, int page) {
        venuesService.getVenues(page)
                .compose(RxHelper.<VenuesData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getVenuesDetail(Subscriber<VenuesDetailData> subscriber, int id) {
        venuesService.getVenuesDetail(id)
                .compose(RxHelper.<VenuesDetailData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCoaches(Subscriber<CoachData> subscriber, int id) {
        venuesService.getCoaches(id)
                .compose(RxHelper.<CoachData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCourses(Subscriber<CourseData> subscriber, int id) {
        venuesService.getCourses(id)
                .compose(RxHelper.<CourseData>transform())
                .subscribe(subscriber);
    }
}
