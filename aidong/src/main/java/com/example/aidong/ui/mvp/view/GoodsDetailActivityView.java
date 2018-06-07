package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.CouponBean;
import com.example.aidong .entity.GoodsDetailBean;

import java.util.List;

/**
 * 商品详情 包含装备、健康餐饮、营养品
 * Created by song on 2016/9/22.
 */
public interface GoodsDetailActivityView {


    /**
     * 设置商品详情
     * @param goodsDetailBean GoodsDetailBean
     */
    void  setGoodsDetail(GoodsDetailBean goodsDetailBean);

    void showErrorView();

    void setGoodsDetailCoupon(List<CouponBean> coupons);
}
