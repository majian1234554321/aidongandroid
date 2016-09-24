package com.example.aidong.ui.mvp.view;

import com.example.aidong.entity.VenuesDetailBean;

/**
 * 场馆
 * Created by song on 2016/8/30.
 */
public interface VenuesDetailActivityView {

    /**
     * 设置场馆详情
     * @param venuesDetailBean 场馆详情实体
     */
    void setCourseDetail(VenuesDetailBean venuesDetailBean);


    /**
     * 显示无网络界面
     */
    void showErrorView();
}
