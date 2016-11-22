package com.leyuan.aidong.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 提交服务器生成订单实体
 * Created by song on 2016/11/14.
 */

public class PayOrderBean {
    private String no;
    private String total;
    private String pay_type;
    private String pay_amount;
    private String status;
    private List<String> item;
    private PayOptionBean pay_option;

    public static class PayOptionBean {
        private String pay_string;  //支付宝使用

        private String appid;       //微信
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String _package;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getPay_string() {
            return pay_string;
        }

        public void setPay_string(String pay_string) {
            this.pay_string = pay_string;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String get_package() {
            return _package;
        }

        public void set_package(String _package) {
            this._package = _package;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        @Override
        public String toString() {
            return "PayOptionBean{" +
                    "pay_string='" + pay_string + '\'' +
                    ", appid='" + appid + '\'' +
                    ", partnerid='" + partnerid + '\'' +
                    ", prepayid='" + prepayid + '\'' +
                    ", _package='" + _package + '\'' +
                    ", noncestr='" + noncestr + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", sign='" + sign + '\'' +
                    '}';
        }
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
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

    public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }

    public PayOptionBean getPay_option() {
        return pay_option;
    }

    public void setPay_option(PayOptionBean pay_option) {
        this.pay_option = pay_option;
    }

    @Override
    public String toString() {
        return "PayOrderBean{" +
                "no='" + no + '\'' +
                ", total='" + total + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", pay_amount='" + pay_amount + '\'' +
                ", status='" + status + '\'' +
                ", item=" + item +
                ", pay_option=" + pay_option +
                '}';
    }
}