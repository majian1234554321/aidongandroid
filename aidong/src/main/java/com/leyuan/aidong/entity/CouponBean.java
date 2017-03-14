package com.leyuan.aidong.entity;

/**
 * 优惠劵
 * Created by song on 2016/8/31.
 */
public class CouponBean {
    private String id;                  //优惠券编号
    private String type;                //优惠券类型,#0-优惠券 1-折扣券
    private String limit_category;      //优惠券限制类型,#0-通用 1-课程类 2-餐饮类 3-活动类 4-营养品类 5-装备类 6-票务类
    private String limit_ext_id;        //#0-品类券 其他值为专用券　
    private String name;                //优惠券名字
    private String discount;            //抵扣金额
    private String min;                 //订单最小金额可用
    private String start_date;          //有效期-开始时间
    private String end_date;            //有效期－结束时间
    private String use_date;            //使用时间
    private String introduce;           //优惠劵展开描述信息
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLimitCategory() {
        return limit_category;
    }

    public void setLimitCategory(String limit_category) {
        this.limit_category = limit_category;
    }

    public String getLimitExtId() {
        return limit_ext_id;
    }

    public void setLimitExtId(String limit_ext_id) {
        this.limit_ext_id = limit_ext_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getStartDate() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEndDate() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getUse_date() {
        return use_date;
    }

    public void setUse_date(String use_date) {
        this.use_date = use_date;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "CouponBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", limit_category='" + limit_category + '\'' +
                ", limit_ext_id='" + limit_ext_id + '\'' +
                ", name='" + name + '\'' +
                ", discount='" + discount + '\'' +
                ", min='" + min + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", use_date='" + use_date + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
