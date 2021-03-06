package com.example.aidong.entity.course;

import com.example.aidong .entity.ALiPayBean;
import com.example.aidong .entity.PayOptionBean;
import com.example.aidong .entity.WeiXinPayBean;

/**
 * Created by user on 2017/11/23.
 */
public class CourseAppointResult {

    CourseAppointBean appointment;
    PayCourseOptionBean payment;
    PayOptionBean realPayment;
    CouponCourseShareBean share;

    public PayOptionBean getPayment() {
        if(payment != null&& realPayment == null){
            realPayment = new PayOptionBean();
            realPayment.setWx(payment.wx);
            ALiPayBean aLiPayBean = new ALiPayBean();
            aLiPayBean.setPay_string(payment.alipay);
            realPayment.setAlipay(aLiPayBean);
        }
        return realPayment;
    }


    public CourseAppointBean getAppointment() {
        return appointment;
    }

    public void setAppointment(CourseAppointBean appointment) {
        this.appointment = appointment;
    }

    private class PayCourseOptionBean {
         String alipay;
         WeiXinPayBean wx;

        public String getAlipay() {
            return alipay;
        }

        public WeiXinPayBean getWx() {
            return wx;
        }
    }

    public CouponCourseShareBean getShare() {
        return share;
    }

    public void setShare(CouponCourseShareBean share) {
        this.share = share;
    }
}
