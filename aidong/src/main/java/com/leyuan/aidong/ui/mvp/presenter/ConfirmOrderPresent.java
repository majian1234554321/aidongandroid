package com.leyuan.aidong.ui.mvp.presenter;

import com.leyuan.aidong.entity.ShareData;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.widget.SwitcherLayout;

/**
 * 确定订单
 * Created by song on 2016/9/1.
 */
public interface ConfirmOrderPresent {


    /**
     * 获取默认地址
     *
     * @param switcherLayout SwitcherLayout
     */
    void getDefaultAddress(SwitcherLayout switcherLayout);

    /**
     * 获取指定商品可用优惠券
     *
     * @param from
     * @param id
     */
    void getSpecifyGoodsCoupon(String from, String... id);


    void getGoodsAvailableCoupon(String... items);

    /**
     * 立即购买装备
     *
     * @param skuCode     skuCode
     * @param amount      数量
     * @param coupon      优惠券
     * @param integral    积分
     * @param coin        爱币
     * @param payType     支付方式
     * @param pickUpWay   取货方式(0-快递 1-自提)
     * @param pickUpId    快递地址id
     * @param pickUpDate  自提时间
     * @param payListener payListener
     */
    void buyEquipmentImmediately(String skuCode, int amount, String coupon, String integral,
                                 String coin, String payType, String pickUpWay, String pickUpId,
                                 String pickUpDate, PayInterface.PayListener payListener);


    /**
     * 立即购买营养品
     *
     * @param skuCode     skuCode
     * @param amount      数量
     * @param coupon      优惠券
     * @param integral    积分
     * @param coin        爱币
     * @param payType     支付方式
     * @param pickUpWay   取货方式(0-快递 1-自提)
     * @param pickUpId    快递地址id
     * @param pickUpDate  自提时间
     */
    void buyNurtureImmediately(String skuCode, int amount, String coupon, String integral,
                               String coin, String payType, String pickUpWay, String pickUpId,
                               String pickUpDate, String pick_up_period, String is_food, PayInterface.PayListener listener);

    void buyGoodsImmediately(String type, String skuCode, int amount, String coupon, String integral,
                             String coin, String payType, String pickUpWay, String pickUpId,
                             String pickUpDate, String pick_up_period, String is_food, PayInterface.PayListener listener);

    /**
     * 购物车结算
     *
     * @param id         结算的购物车条目
     * @param integral   积分
     * @param coin       爱币
     * @param coupon     优惠券
     * @param payType    支付类型
     * @param pickUpId   快递地址id
     * @param pickUpDate 自提时间
     */
    void payCart(String integral, String coin, String coupon, String payType, String pickUpId,
                             String pickUpDate, PayInterface.PayListener payListener, String... id);


    void refreshCartData();

    ShareData.ShareCouponInfo getShareInfo();


}
