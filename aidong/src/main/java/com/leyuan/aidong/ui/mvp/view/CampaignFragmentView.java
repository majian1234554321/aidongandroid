package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CampaignBean;

import java.util.List;

/**
 * 活动
 * Created by song on 2016/8/18.
 */
public interface CampaignFragmentView {
    /**
     * 更新列表
     * @param campaignBean CampaignBean
     */
    void updateRecyclerView(List<CampaignBean> campaignBean);

    /**
     * 显示空值界面布局
     */
    void showEmptyView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();




}
