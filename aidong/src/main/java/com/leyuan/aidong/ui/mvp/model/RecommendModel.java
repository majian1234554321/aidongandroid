package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.data.GoodsData;

import rx.Subscriber;

/**
 * 推荐
 * Created by song on 2017/1/19.
 */
public interface RecommendModel {
    /**
     * 获取推荐商品列表
     * @param subscriber Subscriber
     * @param type 推荐类型(nutrition:营养品 equipment:装备 cart-购物车)
     * @param page 页码
     */
    void getRecommendGoods(Subscriber<GoodsData> subscriber, String type, int page);
}
