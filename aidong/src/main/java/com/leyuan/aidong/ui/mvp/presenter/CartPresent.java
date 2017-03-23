package com.leyuan.aidong.ui.mvp.presenter;

import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.widget.SwitcherLayout;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public interface CartPresent {

    /**
     * 第一次正常加载数据
     * @param switcherLayout
     */
    void commonLoadData(SwitcherLayout switcherLayout);

    /**
     * 下拉刷新
     */
    void pullToRefreshData();

    /**
     * 删除购物车中单类型商品
     * @param id 商品id
     */
    void deleteSingleGoods(String id,int shopPosition,int goodsPosition);

    /**
     * 删除购物车中多类型商品
     * @param ids 不同商品id 多个以逗号隔开
     */
    void deleteMultiGoods(String ids);

    /**
     * 更新商品数量
     * @param id 商品id
     * @param mount 数量
     */
    void updateGoodsCount(String id, int mount, int shopPosition, int goodsPosition);

    void updateGoodsDeliveryInfo(String id,String gymId);

    /**
     * 添加商品到购物车
     * @param skuCode 商品sku码
     * @param amount 数量
     */
    void addCart(String skuCode, int amount,String gymId,String recommendId);

    /**
     * 购物车结算
     * @param id 结算的购物车条目
     * @param integral 积分
     * @param coin 爱币
     * @param coupon 优惠券
     * @param payType 支付类型
     * @param pickUpId 快递地址id
     * @param pickUpDate 自提时间
     */
    void payCart( String integral, String coin, String coupon, String payType, String pickUpId,
                  String pickUpDate,PayInterface.PayListener payListener,String... id);

}
