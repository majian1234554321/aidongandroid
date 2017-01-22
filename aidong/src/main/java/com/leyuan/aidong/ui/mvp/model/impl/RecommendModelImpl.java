package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.data.GoodsData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.RecommendService;
import com.leyuan.aidong.ui.mvp.model.RecommendModel;

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
