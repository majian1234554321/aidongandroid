package com.leyuan.aidong.module.pay;

import android.app.Activity;
import android.content.Context;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.PayOptionBean;
import com.leyuan.aidong.utils.ThirdClientValid;
import com.leyuan.aidong.utils.ToastGlobal;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信支付
 */
public class WeiXinPay implements PayInterface {

    private IWXAPI msgApi;
    public static PayListener payListener;
    public static String appId;

    public WeiXinPay(Context context, PayListener payListener) {
        //String appId = context.getString(R.string.weixingAppID);
        msgApi = WXAPIFactory.createWXAPI(context, null);
        //msgApi.registerApp(appId);
        this.payListener = payListener;

    }

    @Override
    public void payOrder(PayOptionBean payOptionBean) {


        if (payOptionBean != null) {
            PayReq payReq = new PayReq();
            appId = payOptionBean.getWx().getAppid();
            payReq.appId = appId;
            payReq.partnerId = payOptionBean.getWx().getPartnerid();
            payReq.prepayId = payOptionBean.getWx().getPrepayid();
            payReq.nonceStr = payOptionBean.getWx().getNoncestr();
            payReq.timeStamp = payOptionBean.getWx().getTimestamp();
            payReq.packageValue = payOptionBean.getWx().get_package();
            payReq.sign = payOptionBean.getWx().getSign();
            msgApi.registerApp(appId);
            msgApi.sendReq(payReq);
        }
    }
}

