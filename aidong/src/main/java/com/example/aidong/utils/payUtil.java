package com.example.aidong.utils;

import android.app.Activity;

import com.example.aidong.alipay.Payextends;
import com.example.aidong.simcpux.WXPayUtil;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/6/16.
 */
class PayUtil {

    public static void aliPay(Activity activity, String name, String description, String price, String partner, String seller, String private_key, String orderId) {
        DecimalFormat df = new DecimalFormat("0.00");
        Payextends payextends = new Payextends(activity);
        Double dunble = Double.parseDouble(price);
        payextends.pay(name, description, df.format(dunble) + "", partner, seller, private_key, orderId);
    }

    public static void weixinPay(Activity activity, String price, String api_key, String orderId, String communname, String mch_id, String orderType) {
        DecimalFormat df = new DecimalFormat("0.00");
        Double dunble = Double.parseDouble(price);
        double f = Double.parseDouble(df.format(dunble)) * 100;
        int total_fee = (int) f;
        new WXPayUtil(activity, api_key, orderId).prePay(total_fee, communname, mch_id, orderType);
    }


}
