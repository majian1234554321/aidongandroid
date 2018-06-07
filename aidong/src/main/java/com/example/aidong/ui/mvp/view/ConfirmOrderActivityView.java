package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.AddressBean;
import com.example.aidong .entity.CouponBean;

import java.util.List;

/**
 * 确认订单
 * Created by song on 2017/2/8.
 */
public interface ConfirmOrderActivityView {

    /**
     * 获取默认地址结果
     * @param addressBean
     */
    void setDefaultAddressResult(AddressBean addressBean);

    /**
     * 获取可用优惠券结果
     * @param couponBeanList
     */
    void setSpecifyGoodsCouponResult(List<CouponBean> couponBeanList);

  //  void setRefreshCartDataResult(List<ShopBean> list);
}
