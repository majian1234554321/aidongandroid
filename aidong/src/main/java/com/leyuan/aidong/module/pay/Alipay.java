package com.leyuan.aidong.module.pay;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.leyuan.aidong.entity.PayOrderBean;
import com.leyuan.aidong.entity.model.PayResult;

/**
 * 支付宝支付
 * Created by song on 2016/11/14.
 */
public class Alipay implements PayInterface {

    // 商户PID
    //public static final String PARTNER = "2088811587560157";

    // 商户收款账号
    //public static final String SELLER = "2908831266@qq.com";

    public PayOrderBean bean;

    private Activity context;

    private PayListener payListener;

    public Alipay(Activity context, PayListener payListener) {
        this.context = context;
        this.payListener = payListener;
    }

    @Override
    public void payOrder(Object object) {
        bean = (PayOrderBean) object;
        if (null != bean) {
            final String orderInfo = bean.getRequest();
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    try {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(context);
                        // 调用支付接口，获取支付结果
                        final String result = alipay.pay(orderInfo);
                        if (null != payListener) {
                            if (!TextUtils.isEmpty(result)) {
                                final PayResult payResult = new PayResult(result);
                                final String code = payResult.getResultStatus();
                                switch (code) {
                                    case "9000": // 判断resultStatus 为“9000”则代表支付成功
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                payListener.success("9000", payResult);
                                            }
                                        });
                                        break;

                                    default:
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                payListener.fail(code, result);
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
