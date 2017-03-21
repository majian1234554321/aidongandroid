package com.leyuan.aidong.module.pay;

import android.content.Context;

import com.leyuan.aidong.entity.PayOrderBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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
	public void payOrder(Object object) {
		PayOrderBean payOrderBean = (PayOrderBean) object;
		if (payOrderBean != null) {
			PayReq payReq = new PayReq();
			appId = payOrderBean.getpayOption().getAppid();
			payReq.appId = appId;
			payReq.partnerId = payOrderBean.getpayOption().getPartnerid();
			payReq.prepayId = payOrderBean.getpayOption().getPrepayid();
			payReq.nonceStr = payOrderBean.getpayOption().getNoncestr();
			payReq.timeStamp = payOrderBean.getpayOption().getTimestamp();
			payReq.packageValue = payOrderBean.getpayOption().get_package();
			payReq.sign = payOrderBean.getpayOption().getSign();
			msgApi.registerApp(appId);
			msgApi.sendReq(payReq);
		}
	}
}

