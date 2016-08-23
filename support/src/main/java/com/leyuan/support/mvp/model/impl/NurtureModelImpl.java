package com.leyuan.support.mvp.model.impl;

import com.leyuan.support.entity.NurtureDataBean;
import com.leyuan.support.entity.NurtureDetailBean;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.NurtureService;
import com.leyuan.support.mvp.model.NurtureModel;

import rx.Subscriber;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public class NurtureModelImpl implements NurtureModel {
    private NurtureService nurtureService;

    public NurtureModelImpl() {
        nurtureService = RetrofitHelper.createApi(NurtureService.class);
    }

    @Override
    public void getNurtures(Subscriber<NurtureDataBean> subscriber, int page) {
        nurtureService.getNurtures(page)
                .compose(RxHelper.<NurtureDataBean>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getNurtureDetail(Subscriber subscriber, int id) {
        nurtureService.getNurtureDetail(id)
                .compose(RxHelper.<NurtureDetailBean>transform())
                .subscribe(subscriber);
    }
}
