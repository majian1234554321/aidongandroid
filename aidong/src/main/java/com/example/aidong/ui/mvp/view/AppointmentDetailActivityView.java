package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.AppointmentDetailBean;
import com.example.aidong .entity.BaseBean;

/**
 * 预约详情
 * Created by song on 2016/8/30.
 */
public interface AppointmentDetailActivityView {

    /**
     * 设置预约详情
     * @param appointmentDetailBean 预约详情实体
     */
    void setAppointmentDetail(AppointmentDetailBean appointmentDetailBean);

    void cancelAppointmentResult(BaseBean baseBean);


    /**
     * 确认订单
     * @param baseBean
     */
    void confirmAppointmentResult(BaseBean baseBean);

    /**
     * 删除订单
     * @param baseBean
     */
    void deleteAppointmentResult(BaseBean baseBean);
}
