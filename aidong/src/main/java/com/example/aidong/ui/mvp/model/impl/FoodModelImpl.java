package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong.entity.FoodAndVenuesBean;
import com.example.aidong.entity.data.FoodDetailData;
import com.example.aidong.http.RetrofitHelper;
import com.example.aidong.http.RxHelper;
import com.example.aidong.http.api.FoodService;
import com.example.aidong.ui.mvp.model.FoodModel;

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
}
