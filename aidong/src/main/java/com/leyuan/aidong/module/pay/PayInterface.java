package com.leyuan.aidong.module.pay;


/**
 * 支付接口
 */
public interface PayInterface {

    /**
     * 支付
     * @param object 发起支付需要的参数
     */
    void payOrder(Object object);

    /**
     * 支付结果回调接口
     */
     interface PayListener {

        void fail(String code, Object object);

        void success(String code, Object object);
    }
}

