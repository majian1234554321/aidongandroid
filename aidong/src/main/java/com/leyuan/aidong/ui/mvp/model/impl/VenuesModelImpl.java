package com.leyuan.aidong.ui.mvp.model.impl;


import android.content.Context;

import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.GymBrandBean;
import com.leyuan.aidong.entity.data.CoachData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.entity.data.VenuesDetailData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.VenuesService;
import com.leyuan.aidong.ui.mvp.model.VenuesModel;
import com.leyuan.aidong.utils.SystemInfoUtils;

import java.util.List;

import rx.Subscriber;

/**
 * 场馆
 * Created by Song on 2016/8/2.
 */
public class VenuesModelImpl implements VenuesModel {
    private Context context;
    private VenuesService venuesService;

    public VenuesModelImpl(Context context) {
        this.context = context;
        venuesService =  RetrofitHelper.createApi(VenuesService.class);
    }

    @Override
    public List<GymBrandBean> getGymBrand() {
        return SystemInfoUtils.getGymBrand(context);
    }

    @Override
    public List<DistrictBean> getBusinessCircle() {
        return SystemInfoUtils.getLandmark(context);
    }

    @Override
    public void getVenues(Subscriber<VenuesData> subscriber, int page) {
        venuesService.getVenues(page)
                .compose(RxHelper.<VenuesData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getVenuesDetail(Subscriber<VenuesDetailData> subscriber, String id) {
        venuesService.getVenuesDetail(id)
                .compose(RxHelper.<VenuesDetailData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCoaches(Subscriber<CoachData> subscriber, String id) {
        venuesService.getCoaches(id)
                .compose(RxHelper.<CoachData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCourses(Subscriber<CourseData> subscriber, String id) {
        venuesService.getCourses(id)
                .compose(RxHelper.<CourseData>transform())
                .subscribe(subscriber);
    }
}
