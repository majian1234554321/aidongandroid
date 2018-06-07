package com.example.aidong.entity.course;

/**
 * Created by user on 2017/11/24.
 */
public class CourseQueueResult {
    String action;
    CourseQueueBean queue;
    CourseAppointBean appointment;

    public CourseQueueBean getQueue() {
        return queue;
    }

    public void setQueue(CourseQueueBean queue) {
        this.queue = queue;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public CourseAppointBean getAppointment() {
        return appointment;
    }

    public void setAppointment(CourseAppointBean appointment) {
        this.appointment = appointment;
    }
}
