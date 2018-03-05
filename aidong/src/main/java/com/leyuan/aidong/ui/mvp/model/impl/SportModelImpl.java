package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.data.SportRecordData;
import com.leyuan.aidong.http.RetrofitCourseHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.SportService;

import rx.Subscriber;

/**
 * 课程
 * Created by song on 2016/8/13.
 */
public class SportModelImpl {
    private SportService courseService;

    public SportModelImpl() {
        courseService = RetrofitCourseHelper.createApi(SportService.class);
    }

    public void getSportRecord(Subscriber<SportRecordData> subscriber, String year, String month) {
        courseService.getSportRecord(year, month)
                .compose(RxHelper.<SportRecordData>transform())
                .subscribe(subscriber);
    }


}
