package com.example.aidong.entity.data;

import com.example.aidong .entity.AppointmentBean;

import java.util.ArrayList;

/**
 * 预约
 * Created by song on 2016/9/1.
 */
public class AppointmentData {
    private ArrayList<AppointmentBean> order;

    public ArrayList<AppointmentBean> getAppointment() {
        return order;
    }

    public void setAppointment(ArrayList<AppointmentBean> appointment) {
        this.order = appointment;
    }

    @Override
    public String toString() {
        return "AppointmentData{" +
                "order=" + order +
                '}';
    }
}
