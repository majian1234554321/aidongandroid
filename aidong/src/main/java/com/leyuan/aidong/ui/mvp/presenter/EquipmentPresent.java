package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.widget.SwitcherLayout;

/**
 * 装备
 * Created by song on 2016/8/15.
 */
public interface EquipmentPresent {

    /**
     * 第一次进入界面加载装备列表数据
     * @param switcherLayout SwitcherLayout
     */
    void commonLoadEquipmentData(SwitcherLayout switcherLayout,String brandId, String sort);

    /**
     * 下拉刷新装备列表数据
     */
    void pullToRefreshEquipmentData(String brandId,String sort);


    /**
     * 上拉加载更多装备列表数据
     * @param recyclerView RecyclerView
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreEquipmentData(RecyclerView recyclerView, int pageSize, int page,String brandId, String sort);

    /**
     * 立即购买
     * @param skuCode skuCode
     * @param amount 数量
     * @param coupon  优惠券
     * @param integral 积分
     * @param coin  爱币
     * @param payType 支付方式
     * @param pickUpWay 取货方式(0-快递 1-自提)
     * @param pickUpId 快递地址id
     * @param pickUpDate 自提时间
     * @param payListener payListener
     */
    void buyEquipmentImmediately(String skuCode, int amount, String coupon, String integral,
                               String coin, String payType, String pickUpWay, String pickUpId,
                               String pickUpDate,PayInterface.PayListener payListener);
}
