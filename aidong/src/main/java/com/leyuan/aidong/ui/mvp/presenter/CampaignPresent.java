package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 活动
 * Created by song on 2016/8/18.
 */
public interface CampaignPresent {

    /**
     * 第一次正常加载数据活动列表数据
     * @param switcherLayout SwitcherLayout
     */
    void commonLoadData(SwitcherLayout switcherLayout);

    /**
     * 下拉刷新活动列表数据
     */
    void pullToRefreshData();

    /**
     * 上拉加载更多活动列表数据
     * @param recyclerView 执行刷新的RecyclerView
     * @param pageSize 每页刷新的数据量
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView, int pageSize, int page);

    /**
     * 获取活动详情信息
     * @param id 活动id
     */
    void getCampaignDetail(String id);

    /**
     * 购买活动
     * @param id 活动id
     * @param couponId 优惠券id
     * @param integral 积分
     * @param payType 支付类型
     * @param contactName 联系人
     * @param contactMobile 联系人电话
     */
    void buyCampaign(String id,String couponId,float integral,String payType,String contactName,String contactMobile);
}
