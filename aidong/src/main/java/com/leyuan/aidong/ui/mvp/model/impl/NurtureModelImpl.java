package com.leyuan.aidong.ui.mvp.model.impl;

import android.content.Context;

import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.data.NurtureData;
import com.leyuan.aidong.entity.data.NurtureDetailData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.NurtureService;
import com.leyuan.aidong.ui.mvp.model.NurtureModel;
import com.leyuan.aidong.utils.SystemInfoUtils;

import java.util.ArrayList;

import rx.Subscriber;

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
    public void getNurtures(Subscriber<NurtureData> subscriber, int page, String brandId, String sort,String gymId) {
        nurtureService.getNurtures(page,brandId,sort,gymId)
                .compose(RxHelper.<NurtureData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getFoodAndBeverage(Subscriber<NurtureData> subscriber, int page, String brandId, String sort,String gymId) {
        nurtureService.getFoodAndBeverage(page,brandId,sort,gymId)
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
    public void getDeliveryVenues(Subscriber<VenuesData> subscriber, String skuCode, int page,String gymId,String landmark) {
        nurtureService.getDeliveryVenues(skuCode,page,gymId,landmark)
                .compose(RxHelper.<VenuesData>transform())
                .subscribe(subscriber);

    }

    @Override
    public void buyNurtureImmediately(Subscriber<PayOrderData> subscriber, String skuCode, int amount,
                                      String coupon, String integral, String coin, String payType,
                                      String pickUpWay, String pickUpId, String pickUpDate) {
        nurtureService.buyNurtureImmediately(skuCode,amount,coupon,integral,coin,payType,pickUpWay,
                pickUpId,pickUpDate)
                .compose(RxHelper.<PayOrderData>transform())
                .subscribe(subscriber);
    }
}
