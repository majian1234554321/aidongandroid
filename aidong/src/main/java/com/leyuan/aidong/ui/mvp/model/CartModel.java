package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.entity.data.ShopData;

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
    void getCart(Subscriber<ShopData> subscriber);

    /**
     * 添加到购物车
     * @param subscriber Subscriber
     * @param skuCode 商品sku码
     * @param mount 数量
     */
    void addCart(Subscriber<BaseBean> subscriber,String skuCode,int mount,String gymId);

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

    /**
     * 结算购物车
     * @param subscriber Subscriber
     * @param id 结算条目id
     * @param integral 积分
     * @param coin 爱币
     * @param coupon 优惠券
     * @param payType 支付类型
     * @param pickUpId 自提或地址id
     */
    void payCart(Subscriber<PayOrderData> subscriber, String integral, String coin ,
                 String coupon, String payType, String pickUpId,String... id);
}
