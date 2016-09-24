package com.example.aidong.ui.mvp.view;

import com.example.aidong.entity.CouponBean;

import java.util.List;

/**
 * 优惠劵
 * Created by song on 2016/8/31.
 */
public interface CouponFragmentView {

    /**
     * 更新列表
     * @param couponBeanList CouponBean集合
     */
    void updateRecyclerView(List<CouponBean> couponBeanList);

    /**
     * 显示空值界面
     */
    void showEmptyView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
