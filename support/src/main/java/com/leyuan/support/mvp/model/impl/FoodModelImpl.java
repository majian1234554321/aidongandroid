package com.leyuan.support.mvp.model.impl;

import com.leyuan.support.entity.FoodBean;
import com.leyuan.support.entity.FoodDetailBean;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.FoodService;
import com.leyuan.support.mvp.model.FoodModel;

import java.util.List;

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
    public void getFoods(Subscriber<List<FoodBean>> subscriber, int page) {
        foodService.getFoods(page)
                .compose(RxHelper.<List<FoodBean>>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getFoodDetail(Subscriber<FoodDetailBean> subscriber, int id) {
        foodService.getFoodDetail(id)
                .compose(RxHelper.<FoodDetailBean>transform())
                .subscribe(subscriber);
    }
}
