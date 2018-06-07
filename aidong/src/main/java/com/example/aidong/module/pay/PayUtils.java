package com.example.aidong.module.pay;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.aidong .entity.PayOptionBean;
import com.example.aidong .entity.PayOrderBean;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.constant.PayType;

/**
 * 支付工具类
 * Created by song on 2017/4/6.
 */
public class PayUtils {
    public static void pay(Context context, @NonNull PayOrderBean payOrderBean, @NonNull PayInterface.PayListener listener) {
        if (!"purchased".equals(payOrderBean.getStatus())) {
            PayInterface payInterface = Constant.PAY_ALI.equals(payOrderBean.getPayType())
                    ? new AliPay(context, listener) : new WeiXinPay(context, listener);
            payInterface.payOrder(payOrderBean.getpayOption());
        } else {
            listener.onFree();
        }
    }

    public static void pay(Context context, @PayType String payType, @NonNull PayOptionBean payOptionBean, @NonNull PayInterface.PayListener listener) {
        PayInterface payInterface = Constant.PAY_ALI.equals(payType) ? new AliPay(context, listener) : new WeiXinPay(context, listener);
        payInterface.payOrder(payOptionBean);
    }

}
