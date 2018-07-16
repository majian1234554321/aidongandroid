package com.example.aidong.entity;

import android.graphics.Color;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * 预约详情
 * Created by song on 2016/9/1.
 */
public class AppointmentDetailBean {
    private String id;              //订单号
    private boolean is_vip;
    private String link_id;             //关联课程或者活动编号
    private String appointment_type;    //订单类型 course-课程 campaign-活动
    private String name;             //产品名称
    private String sub_name;             //副标题
    @SerializedName("address")
    private String cover2;


    public String landmark;

    private String cover;             //产品封面
    private AppointInfo appointment;    //预约信息
    private PayOrderBean pay;            //支付信息
    private PayInfo payInfo;
    private String remark;
    public String campaign_detail;


    public class AppointInfo {
        private String name;            //预约人
        private String mobile;          //手机
        private String gym;             //上课场馆
        private String class_time;      //上课教室
        private String classroom;       //上课时间
        private String address;         //上课地址
        private String organizer;
        public String landmark;
        public String amount;
        private String status;
        private String verify_no; //"核销码"
        private String verify_status;//"核销状态 undo-未核销 done-已核销",
        String lat;
        String lng;
        String organizer_mobile;
        private ArrayList<String> spec_value;

        public String getVerify_no() {
            return verify_no + "";
        }

        public void setVerify_no(String verify_no) {
            this.verify_no = verify_no;
        }

        public String getVerify_status() {
            return verify_status;
        }

        public void setVerify_status(String verify_status) {
            this.verify_status = verify_status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getGym() {
            return gym;
        }

        public void setGym(String gym) {
            this.gym = gym;
        }

        public String getClassTime() {
            return class_time;
        }

        public void setClass_time(String class_time) {
            this.class_time = class_time;
        }

        public String getSpec_value() {
            StringBuilder builder = new StringBuilder();
            if (spec_value != null) {
                for (String s : spec_value) {
                    builder.append(s).append(" ");
                }
            } else {
                builder.append(class_time);
            }
            return builder.toString();
        }

        public String getClassroom() {
            return classroom;
        }

        public void setClassroom(String classroom) {
            this.classroom = classroom;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOrganizer() {
            return organizer;
        }

        public void setOrganizer(String organizer) {
            this.organizer = organizer;
        }

        public int getverifyColor() {
            if ("undo".equals(getVerify_status()))
                return Color.parseColor("#000000");
            return Color.parseColor("#ebebeb");
        }

        public int getverifyColorQr() {
            if ("undo".equals(getVerify_status()))
                return 0xFF000000;
            return 0xFFebebeb;
        }

        public boolean isVerified() {
            return "done".equals(getVerify_status());
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getOrganizer_mobile() {
            return organizer_mobile;
        }

        public void setOrganizer_mobile(String organizer_mobile) {
            this.organizer_mobile = organizer_mobile;
        }
    }

    public class PayInfo {
        private String total;
        private String coin;
        private String integral;
        private String coupon;
        private String created_at;
        private String pay_type;
        private String pay_option;
        private String status;
        private String limitTime;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
            this.coupon = coupon;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPay_option() {
            return pay_option;
        }

        public void setPay_option(String pay_option) {
            this.pay_option = pay_option;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLimitTime() {
            return limitTime;
        }

        public void setLimitTime(String limitTime) {
            this.limitTime = limitTime;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppointment_type() {
        return appointment_type;
    }

    public void setAppointment_type(String appointment_type) {
        this.appointment_type = appointment_type;
    }

    public String getLinkId() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public AppointInfo getAppoint() {
        return appointment;
    }

    public void setAppointment(AppointInfo appointment) {
        this.appointment = appointment;
    }

    public String getSub_name() {
        return sub_name;
    }

    public PayOrderBean getPay() {
        return pay;
    }

    public void setPay(PayOrderBean pay) {
        this.pay = pay;
    }

    public PayInfo getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PayInfo payInfo) {
        this.payInfo = payInfo;
    }

    public boolean is_vip() {
        return is_vip;
    }

    public void setIs_vip(boolean is_vip) {
        this.is_vip = is_vip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
