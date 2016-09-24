package com.example.aidong.entity.data;

import com.example.aidong.entity.AppointmentBean;

import java.util.ArrayList;

/**
 * 预约
 * Created by song on 2016/9/1.
 */
public class AppointmentData {
    private ArrayList<AppointmentBean> appointment;

    public ArrayList<AppointmentBean> getAppointment() {
        return appointment;
    }

    public void setAppointment(ArrayList<AppointmentBean> appointment) {
        this.appointment = appointment;
    }

    @Override
    public String toString() {
        return "AppointmentData{" +
                "appointment=" + appointment +
                '}';
    }
}
