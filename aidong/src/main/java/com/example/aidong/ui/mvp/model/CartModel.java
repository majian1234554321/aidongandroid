package com.example.aidong.ui.mvp.model;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.PayOrderData;
import com.example.aidong .entity.data.ShopData;

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
    void addCart(Subscriber<BaseBean> subscriber,String skuCode,int mount,String gymId,String recommendId);

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
    void updateDeliveryInfo(Subscriber<BaseBean> subscriber, String id, int mount, String gymId);

    /**
     * 更新商品发货信息
     * @param subscriber Subscriber
     * @param id 商品id
     * @param gym_id 自体门店地址 快递的话为0
     */
    void updateDeliveryInfo(Subscriber<BaseBean> subscriber, String id,String count,  String gym_id);

    /**
     * 结算购物车
     * @param subscriber Subscriber
     * @param id 结算条目id
     * @param integral 积分
     * @param coin 爱币
     * @param coupon 优惠券
     * @param payType 支付类型
     * @param pickUpId 快递地址id
     */
    void payCart(Subscriber<PayOrderData> subscriber, String integral, String coin ,
                 String coupon, String payType, String pickUpId,String pickUpDate,String... id);
}
