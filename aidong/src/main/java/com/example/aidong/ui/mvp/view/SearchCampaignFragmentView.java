package com.example.aidong.ui.mvp.view;

import com.example.aidong.entity.CampaignBean;

import java.util.List;

/**
 * 搜索活动
 * Created by song on 2016/9/1.
 */
public interface SearchCampaignFragmentView {
    /**
     * 更新列表
     * @param campaignBeanList CampaignBean
     */
    void updateRecyclerView(List<CampaignBean> campaignBeanList);



    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
