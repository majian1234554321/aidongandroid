package com.example.aidong.ui.mvp.model;

import com.example.aidong.entity.BaseBean;
import com.example.aidong.entity.GoodsBean;

import java.util.List;

import rx.Subscriber;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public interface CartModel {

    /**
     * 获取购物车列表
     * @param subscriber Subscriber
     */
    void getCart(Subscriber<List<GoodsBean>> subscriber);

    /**
     * 添加到购物车
     * @param subscriber Subscriber
     * @param sku 商品sku码
     * @param mount 数量
     */
    void addCart(Subscriber<BaseBean> subscriber,String sku,int mount);

    /**
     * 删除购物车
     * @param subscriber Subscriber
     * @param ids 商品id 多个以逗号隔开
     */
    void deleteCart(Subscriber<BaseBean> subscriber,String ids);

    /**
     * 更新商品数量
     * @param subscriber Subscriber
     * @param id 商品id
     * @param mount 数量
     */
    void updateCart(Subscriber<BaseBean> subscriber,String id,int mount);
}
