package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.data.CircleData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.CircleService;

import rx.Subscriber;

/**
 * Created by user on 2018/1/31.
 */

public class CircleModel {

    private CircleService circleService;

    public CircleModel() {
        this.circleService = RetrofitHelper.createApi(CircleService.class);
    }

    public void getRecommendCircle(Subscriber<CircleData> subscriber) {
        circleService.getRecommendCircle()
                .compose(RxHelper.<CircleData>transform())
                .subscribe(subscriber);
    }

    public void searchCircle(Subscriber<CircleData> subscriber, String name) {
        circleService.searchCircle(name)
                .compose(RxHelper.<CircleData>transform())
                .subscribe(subscriber);
    }
}
