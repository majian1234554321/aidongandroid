package com.leyuan.aidong.module.pay;

import android.content.Context;

import com.leyuan.aidong.entity.PayOrderBean;
import com.leyuan.aidong.utils.Constant;

/**
 * 支付工具类
 * Created by song on 2017/4/6.
 */
public class PayUtils {
    public static void pay(Context context, PayOrderBean payOrderBean, PayInterface.PayListener listener) {
        PayInterface payInterface = Constant.PAY_ALI.equals(payOrderBean.getPayType())
                ? new AliPay(context,listener) : new WeiXinPay(context,listener);
        payInterface.payOrder(payOrderBean.getpayOption());
    }
}
