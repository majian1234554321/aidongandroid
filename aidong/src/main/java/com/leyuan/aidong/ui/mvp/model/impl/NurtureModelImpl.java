package com.leyuan.aidong.ui.mvp.model.impl;

import android.content.Context;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.data.NurtureData;
import com.leyuan.aidong.entity.data.NurtureDetailData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.NurtureService;
import com.leyuan.aidong.ui.mvp.model.NurtureModel;
import com.leyuan.aidong.utils.SystemInfoUtils;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public class NurtureModelImpl implements NurtureModel {
    private Context context;
    private NurtureService nurtureService;

    public NurtureModelImpl(Context context) {
        this.context = context;
        nurtureService = RetrofitHelper.createApi(NurtureService.class);
    }

    @Override
    public ArrayList<CategoryBean> getCategory() {
        return SystemInfoUtils.getNurtureCategory(context);
    }

    @Override
    public void getNurtures(Subscriber<NurtureData> subscriber, int page, String brandId,
                            String priceSort, String countSort, String heatSort) {
        nurtureService.getNurtures(page,brandId,priceSort,countSort,heatSort)
                .compose(RxHelper.<NurtureData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getNurtureDetail(Subscriber<NurtureDetailData> subscriber, String id) {
        nurtureService.getNurtureDetail(id)
                .compose(RxHelper.<NurtureDetailData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getDeliveryVenues(Subscriber<VenuesData> subscriber, String skuCode, int page) {
        nurtureService.getDeliveryVenues(skuCode,page)
                .compose(RxHelper.<VenuesData>transform())
                .subscribe(subscriber);

    }

    @Override
    public void buyNurtureImmediately(Subscriber<BaseBean> subscriber, String skuCode, int amount,
                                      String pickUp, String pickUpId) {
        nurtureService.buyNurtureImmediately(skuCode,amount,pickUp,pickUpId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }
}
