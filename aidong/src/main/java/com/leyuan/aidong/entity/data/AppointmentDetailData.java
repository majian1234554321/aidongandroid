package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.AppointmentDetailBean;

/**
 * 预约详情
 * Created by song on 2016/9/1.
 */
public class AppointmentDetailData {
    private AppointmentDetailBean order;

    public AppointmentDetailBean getAppoint() {
        return order;
    }

    public void setAppoint(AppointmentDetailBean appoint) {
        this.order = appoint;
    }

    @Override
    public String toString() {
        return "AppointmentDetailData{" +
                "appoint=" + order +
                '}';
    }
}
