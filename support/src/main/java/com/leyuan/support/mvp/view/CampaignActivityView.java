package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.CampaignBean;

import java.util.List;

/**
 * 活动
 * Created by song on 2016/8/18.
 */
public interface CampaignActivityView {
    /**
     * 更新列表
     * @param campaignBean CampaignBean
     */
    void updateRecyclerView(List<CampaignBean> campaignBean);

    /**
     * 显示加载中的布局
     */
    void showLoadingView();

    /**
     * 显示有内容的布局
     */
    void showContentView();

    /**
     * 显示空值界面布局
     */
    void showEmptyView();

    /**
     * 整体界面显示无网络界面
     * 对于有缓存的界面空实现该方法即可
     */
    void showErrorView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();

    /**
     * 显示FooterView,提示加载错误
     */
    void showErrorFooterView();
}
