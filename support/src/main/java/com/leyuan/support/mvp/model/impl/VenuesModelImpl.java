package com.leyuan.support.mvp.model.impl;


import com.leyuan.support.entity.VenuesBean;
import com.leyuan.support.entity.VenuesDetailBean;
import com.leyuan.support.http.HttpResultFunc;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.api.VenuesService;
import com.leyuan.support.mvp.model.VenuesModel;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    public void getVenues(Subscriber<List<VenuesBean>> subscriber, int page) {
        venuesService.getVenues(page)
                .map(new HttpResultFunc<List<VenuesBean>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getVenuesDetail(Subscriber<VenuesDetailBean> subscriber, int id) {
        venuesService.getVenuesDetail(id)
                .map(new HttpResultFunc<VenuesDetailBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }
}
