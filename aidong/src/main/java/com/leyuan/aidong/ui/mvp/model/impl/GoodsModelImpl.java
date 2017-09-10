package com.leyuan.aidong.ui.mvp.model.impl;

import android.content.Context;

import com.leyuan.aidong.entity.data.GoodsData;
import com.leyuan.aidong.entity.data.GoodsDetailData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.GoodsService;

import rx.Subscriber;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public class GoodsModelImpl  {
    private Context context;
    private GoodsService nurtureService;

    public GoodsModelImpl(Context context) {
        this.context = context;
        nurtureService = RetrofitHelper.createApi(GoodsService.class);
    }

//    public ArrayList<CategoryBean> getCategory() {
//        return SystemInfoUtils.getNurtureCategory(context);
//    }

    public void getGoods(Subscriber<GoodsData> subscriber, String type, int page, String brandId, String sort, String gymId) {
        nurtureService.getGoods(type,page,brandId,sort,gymId)
                .compose(RxHelper.<GoodsData>transform())
                .subscribe(subscriber);
    }


    public void getGoodsDetail(Subscriber<GoodsDetailData> subscriber, String type, String id) {
        nurtureService.getGoodsDetail(type,id)
                .compose(RxHelper.<GoodsDetailData>transform())
                .subscribe(subscriber);
    }

    public void getDeliveryVenues(Subscriber<VenuesData> subscriber, String type, String skuCode, int page,String gymId,String landmark) {
        nurtureService.getDeliveryVenues(type,skuCode,page,gymId,landmark)
                .compose(RxHelper.<VenuesData>transform())
                .subscribe(subscriber);

    }

    public void buyNurtureImmediately(Subscriber<PayOrderData> subscriber,String type, String skuCode, int amount,
                                      String coupon, String integral, String coin, String payType,
                                      String pickUpWay, String pickUpId, String pickUpDate, String pick_up_period, String is_food) {
        nurtureService.buyGoodsImmediately(type,skuCode,amount,coupon,integral,coin,payType,pickUpWay,
                pickUpId,pickUpDate,pick_up_period,is_food)
                .compose(RxHelper.<PayOrderData>transform())
                .subscribe(subscriber);
    }
}
