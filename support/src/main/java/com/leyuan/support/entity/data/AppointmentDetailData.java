package com.leyuan.support.entity.data;

import com.leyuan.support.entity.AppointmentDetailBean;

/**
 * 预约详情
 * Created by song on 2016/9/1.
 */
public class AppointmentDetailData {
    private AppointmentDetailBean appoint;

    public AppointmentDetailBean getAppoint() {
        return appoint;
    }

    public void setAppoint(AppointmentDetailBean appoint) {
        this.appoint = appoint;
    }

    @Override
    public String toString() {
        return "AppointmentDetailData{" +
                "appoint=" + appoint +
                '}';
    }
}
