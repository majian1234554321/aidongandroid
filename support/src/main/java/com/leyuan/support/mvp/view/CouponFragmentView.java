package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.CouponBean;

import java.util.List;

/**
 * 可用优惠劵
 * Created by song on 2016/8/31.
 */
public interface CouponFragmentView {

    /**
     * 更新列表
     * @param couponBeanList
     */
    void updateRecyclerView(List<CouponBean> couponBeanList);

    /**
     * 显示空值界面
     */
    void showEmptyView();

    /**
     * 隐藏空值界面
     */
    void hideEmptyView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
