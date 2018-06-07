package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong .entity.data.GoodsData;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.RecommendService;
import com.example.aidong .ui.mvp.model.RecommendModel;

import rx.Subscriber;

/**
 * 推荐
 * Created by song on 2017/1/19.
 */
public class RecommendModelImpl implements RecommendModel{
    private RecommendService recommendService;

    public RecommendModelImpl() {
        recommendService = RetrofitHelper.createApi(RecommendService.class);
    }

    @Override
    public void getRecommendGoods(Subscriber<GoodsData> subscriber, String type, int page) {
        recommendService.getRecommendGoods(type,page)
                .compose(RxHelper.<GoodsData>transform())
                .subscribe(subscriber);
    }
}
