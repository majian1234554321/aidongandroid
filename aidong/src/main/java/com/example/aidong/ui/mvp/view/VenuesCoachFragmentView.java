package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.CoachBean;

import java.util.List;

/**
 * 场馆详情-教练
 * Created by song on 2016/8/30.
 */
public interface VenuesCoachFragmentView {

    /**
     * 设置教练列表
     * @param coachBeanList 教练集合
     */
    void setCoaches(List<CoachBean> coachBeanList);

}
