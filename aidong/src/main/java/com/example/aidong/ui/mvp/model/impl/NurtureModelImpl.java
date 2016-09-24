package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong.entity.data.NurtureData;
import com.example.aidong.entity.data.NurtureDetailData;
import com.example.aidong.http.RetrofitHelper;
import com.example.aidong.http.RxHelper;
import com.example.aidong.http.api.NurtureService;
import com.example.aidong.ui.mvp.model.NurtureModel;

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
    public void getNurtures(Subscriber<NurtureData> subscriber, int page) {
        nurtureService.getNurtures(page)
                .compose(RxHelper.<NurtureData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getNurtureDetail(Subscriber<NurtureDetailData> subscriber, String id) {
        nurtureService.getNurtureDetail(id)
                .compose(RxHelper.<NurtureDetailData>transform())
                .subscribe(subscriber);
    }
}
