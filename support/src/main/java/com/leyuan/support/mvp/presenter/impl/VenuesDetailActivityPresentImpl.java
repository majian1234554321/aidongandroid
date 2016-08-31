package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.support.entity.data.VenuesDetailData;
import com.leyuan.support.http.subscriber.ProgressSubscriber;
import com.leyuan.support.mvp.model.VenuesModel;
import com.leyuan.support.mvp.model.impl.VenuesModelImpl;
import com.leyuan.support.mvp.presenter.VenuesDetailActivityPresent;
import com.leyuan.support.mvp.view.VenuesDetailActivityView;

/**
 * 场馆详情
 * Created by song on 2016/8/30.
 */
public class VenuesDetailActivityPresentImpl implements VenuesDetailActivityPresent{
    private Context context;
    private VenuesModel venuesModel;
    private VenuesDetailActivityView venuesDetailActivityView;

    public VenuesDetailActivityPresentImpl(Context context, VenuesDetailActivityView venuesDetailActivityView) {
        this.context = context;
        this.venuesDetailActivityView = venuesDetailActivityView;
        venuesModel = new VenuesModelImpl();
    }

    @Override
    public void getVenuesDetail(int id) {
        venuesModel.getVenuesDetail(new ProgressSubscriber<VenuesDetailData>(context) {
            @Override
            public void onNext(VenuesDetailData venuesDetailData) {
                if(venuesDetailData != null && venuesDetailData.getGym() != null){
                    venuesDetailActivityView.setCourseDetail(venuesDetailData.getGym());
                }
            }
        },id);
    }

}
