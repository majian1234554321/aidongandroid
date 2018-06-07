package com.example.aidong.entity;

/**
 * 调起支付需要的信息实体
 * Created by song on 2017/3/25.
 */

public class PayOptionBean {

    private ALiPayBean alipay;
    private WeiXinPayBean wx;

    public WeiXinPayBean getWx() {
        return wx;
    }

    public void setWx(WeiXinPayBean wx) {
        this.wx = wx;
    }

    public ALiPayBean getAlipay() {
        return alipay;
    }

    public void setAlipay(ALiPayBean alipay) {
        this.alipay = alipay;
    }
}
