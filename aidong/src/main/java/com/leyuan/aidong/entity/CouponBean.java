package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 优惠劵
 * Created by song on 2016/8/31.
 */
public class CouponBean implements Parcelable {
    private String id;                  //优惠券编号
    private String coupon_type;   // 专用券，通用券，品类券　
    private String limit_category;     //优惠券限制类型,#common-通用 course-课程类 campaign-活动类 nutrition-营养品类 equipment-装备类
    private String name;                //优惠券名字
    private String discount;            //抵扣金额  抵扣金额或者折扣
    private String min;                 //订单最小金额可用
    private String start_date;          //有效期-开始时间
    private String end_date;            //有效期－结束时间
    private String use_date;            //使用时间
    private String introduce;           //优惠劵展开描述信息
    private String status;
    //废弃
    private String limit_ext_id;        //#0-品类券 其他值为专用券　
    private String type;                //优惠券类型,#0-优惠券 1-折扣券
    private String user_coupon_id;//用户优惠券编号，为避免同一ID优惠券多次选择
    private String actual;
    private String discountNumber;
    private String discountSign;

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_coupon_id() {
//        return user_coupon_id;
        return id;
    }

    public void setUser_coupon_id(String user_coupon_id) {
        this.user_coupon_id = user_coupon_id;
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

    public String getDiscountNumber() {

        if (discount != null && discount.length() > 0) {
            discountNumber = discount.substring(1);
        } else {
            discountNumber = "0";
        }
        return discountNumber;
    }

    public String getDiscountSign() {

        if (discount != null && discount.length() > 0) {
            discountSign = discount.substring(0, 1);
        } else {
            discountSign = "";
        }
        return discountSign;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMin() {
        return min;
    }

    public String getCouponDesc() {
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

    public String getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(String coupon_type) {
        this.coupon_type = coupon_type;
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

    public CouponBean() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(coupon_type);
        dest.writeString(limit_category);
        dest.writeString(name);
        dest.writeString(discount);
        dest.writeString(min);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeString(use_date);
        dest.writeString(introduce);
        dest.writeString(status);
        dest.writeString(limit_ext_id);
        dest.writeString(type);
        dest.writeString(user_coupon_id);
        dest.writeString(actual);
    }

    protected CouponBean(Parcel in) {
        id = in.readString();
        coupon_type = in.readString();
        limit_category = in.readString();
        name = in.readString();
        discount = in.readString();
        min = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        use_date = in.readString();
        introduce = in.readString();
        status = in.readString();
        limit_ext_id = in.readString();
        type = in.readString();
        user_coupon_id = in.readString();
        actual = in.readString();
    }

    public static final Creator<CouponBean> CREATOR = new Creator<CouponBean>() {
        @Override
        public CouponBean createFromParcel(Parcel in) {
            return new CouponBean(in);
        }

        @Override
        public CouponBean[] newArray(int size) {
            return new CouponBean[size];
        }
    };
}
