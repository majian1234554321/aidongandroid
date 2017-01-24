package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.FoodAndVenuesBean;
import com.leyuan.aidong.entity.data.FoodDetailData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.FoodService;
import com.leyuan.aidong.ui.mvp.model.FoodModel;

import rx.Subscriber;

/**
 * 健康餐饮
 * Created by song on 2016/8/15.
 */
public class FoodModelImpl implements FoodModel{
    private FoodService foodService;

    public FoodModelImpl() {
        foodService = RetrofitHelper.createApi(FoodService.class);
    }

    @Override
    public void getFoods(Subscriber<FoodAndVenuesBean> subscriber, int page) {
        foodService.getFoods(page)
                .compose(RxHelper.<FoodAndVenuesBean>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getFoodDetail(Subscriber<FoodDetailData> subscriber, String id) {
        foodService.getFoodDetail(id)
                .compose(RxHelper.<FoodDetailData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getDeliveryVenues(Subscriber<VenuesData> subscriber, String skuCode, int page) {
        foodService.getDeliveryVenues(skuCode,page)
                .compose(RxHelper.<VenuesData>transform())
                .subscribe(subscriber);
    }
}
