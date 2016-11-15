package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.AppointmentBean;

import java.util.List;

/**
 * 预约
 * Created by song on 2016/9/1.
 */
public interface AppointmentFragmentView {
    /**
     * 更新列表
     * @param appointmentBeanList
     */
    void updateRecyclerView(List<AppointmentBean> appointmentBeanList);

    /**
     * 显示空值界面
     */
    void showEmptyView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}