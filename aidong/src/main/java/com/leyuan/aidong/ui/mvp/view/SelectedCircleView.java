package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CampaignBean;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/31.
 */
public interface SelectedCircleView {
    void onGetRecommendCircle(ArrayList<CampaignBean> items);

    void onSearchCircleResult(ArrayList<CampaignBean> items);
}