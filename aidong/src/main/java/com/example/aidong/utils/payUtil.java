package com.example.aidong.utils;

import android.app.Activity;

import com.example.aidong.alipay.Payextends;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/6/16.
 */
public class payUtil {
    private DecimalFormat df = new DecimalFormat("0.00");

    public void aliPay(Activity activity, String name, String description, String price, String partner, String seller, String private_key, String orderId) {
        Payextends payextends = new Payextends(activity);

        Double dunble = Double.parseDouble(price);

        payextends.pay(name, description, df.format(dunble) + "", partner, seller, private_key, orderId);

    }


}
