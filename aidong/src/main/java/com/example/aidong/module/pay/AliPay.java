package com.example.aidong.module.pay;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.example.aidong .entity.PayOptionBean;
import com.example.aidong .entity.model.PayResult;

/**
 * 支付宝支付
 * Created by song on 2016/11/14.
 */
public class AliPay implements PayInterface {

    // 商户PID
    //public static final String PARTNER = "2088811587560157";

    // 商户收款账号
    //public static final String SELLER = "2908831266@qq.com";


    private Context context;

    private PayListener payListener;

    public AliPay(Context context, PayListener payListener) {
        this.context = context;
        this.payListener = payListener;
    }

    @Override
    public void payOrder(PayOptionBean bean) {
        if (null != bean) {
            final String orderInfo = bean.getAlipay().getPayString();
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    try {
                        // 构造PayTask 对象
                        PayTask aliPay = new PayTask((Activity)context);
                        // 调用支付接口，获取支付结果
                        final String result = aliPay.pay(orderInfo);
                        if (null != payListener) {
                            if (!TextUtils.isEmpty(result)) {
                                final PayResult payResult = new PayResult(result);
                                final String code = payResult.getResultStatus();
                                switch (code) {
                                    case "9000": // 判断resultStatus 为“9000”则代表支付成功
                                        ((Activity)context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                payListener.onSuccess("9000", payResult);
                                            }
                                        });
                                        break;

                                    default:
                                        ((Activity) context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                payListener.onFail(code, result);
                                            }
                                        });
                                        break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    }
}
