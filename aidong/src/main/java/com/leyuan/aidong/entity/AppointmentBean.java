package com.leyuan.aidong.entity;

/**
 * 预约
 * Created by song on 2016/9/1.
 */
public class AppointmentBean {
    private String id;                      //订单号
    private String appointment_type;        //订单类型 course-课程 campaign-活动
    private String link_id;                 //关联课程或者活动编号
    private String name;                    //产品名称
    private String sub_name;                //副标题
    private String cover;                   //产品封面
    private String start;                   //开始时间
    private String price;                   //产品价格
    private String pay_amount;              //实付款总额
    private String status;                  //订单状态
    private String little_time;             //订单剩余支付时间
    private String created_at;
    private String no;
    private int pay_type;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppointmentType() {
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLittleTime() {

        return little_time;
    }

    public void setLittle_time(String little_time) {
        this.little_time = little_time;
    }




    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
