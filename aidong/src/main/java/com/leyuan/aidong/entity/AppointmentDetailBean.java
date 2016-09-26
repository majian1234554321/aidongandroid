package com.leyuan.aidong.entity;

/**
 * 预约详情
 * Created by song on 2016/9/1.
 */
public class AppointmentDetailBean {
    private String id;             //订单号
    private String type;           //订单类型 course-课程 campaign-活动
    private String total;          //订单总额
    private String coupon;         //优惠券优惠金额
    private String integral;       //积分优惠金额
    private String pay_type;       //付款方式 alipay-支付宝 wxpay-微信支付
    private String status;         //订单状态 -1-已取消 0-未支付 1-已支付 2-已确认
    private Item item;             //课程或活动实体
    private Contact contact;       //联系人

    class Item{
        private String id;          //活动或者课程ID
        private String name;        //活动或者课程名字
        private String cover;       //活动或者课程封面
        private String price;       //活动或者课程价格

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", cover='" + cover + '\'' +
                    ", price='" + price + '\'' +
                    '}';
        }
    }

    class Contact{
        private String name;
        private String mobile;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Contact{" +
                    "name='" + name + '\'' +
                    ", mobile='" + mobile + '\'' +
                    '}';
        }
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "AppointmentDetailBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", total='" + total + '\'' +
                ", coupon='" + coupon + '\'' +
                ", integral='" + integral + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", status='" + status + '\'' +
                ", item=" + item +
                ", contact=" + contact +
                '}';
    }
}
