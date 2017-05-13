package com.leyuan.aidong.module.pay;


import com.leyuan.aidong.entity.PayOptionBean;

/**
 * 支付接口
 */
public interface PayInterface {

    /**
     * 支付
     * @param object 发起支付需要的参数
     */
    void payOrder(PayOptionBean object);

    /**
     * 支付结果回调接口
     */
     interface PayListener {

        /**
         * 付款失败回调
         * @param code
         * @param object
         */
        void onFail(String code, Object object);

        /**
         * 付款成功回调
         * @param code
         * @param object
         */
        void onSuccess(String code, Object object);

        /**
         * 免费回调
         */
        void onFree();
    }
}

