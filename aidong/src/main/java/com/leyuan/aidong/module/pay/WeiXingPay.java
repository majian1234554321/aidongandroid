package com.leyuan.aidong.module.pay;

import android.content.Context;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.PayOrderBean;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信支付
 */
public class WeiXingPay implements PayInterface {
	private IWXAPI msgApi;
	public static PayListener payListener;

	
	public WeiXingPay(Context context, PayListener payListener) {
		String appId = context.getString(R.string.weixingAppID);
		msgApi = WXAPIFactory.createWXAPI(context, null);
		msgApi.registerApp(appId);
		this.payListener = payListener;
	}
	
	@Override
	public void payOrder(Object object) {
		PayOrderBean payOrderBean = (PayOrderBean) object;
		if (payOrderBean != null) {
			PayReq payReq = new PayReq();
			payReq.appId = payOrderBean.getPay_option().getAppid();
			payReq.partnerId = payOrderBean.getPay_option().getPartnerid();
			payReq.prepayId = payOrderBean.getPay_option().getPrepayid();
			payReq.nonceStr = payOrderBean.getPay_option().getNoncestr();
			payReq.timeStamp = payOrderBean.getPay_option().getTimestamp();
			payReq.packageValue = payOrderBean.getPay_option().get_package();
			payReq.sign = payOrderBean.getPay_option().getSign();
			msgApi.registerApp(payOrderBean.getPay_option().getAppid());
			msgApi.sendReq(payReq);
		}
	}
}

