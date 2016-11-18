package com.leyuan.aidong.module.pay;

import android.content.Context;
import android.text.TextUtils;
import android.util.Xml;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.PayOrderBean;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;

/**
 * 微信支付
 */
public class WeiXingPay implements PayInterface {
	private String appid = "";
	private IWXAPI msgApi;
	public static PayListener payListener;

	
	public WeiXingPay(Context context, PayListener payListener) {
		appid = context.getString(R.string.weixingAppID);
		msgApi = WXAPIFactory.createWXAPI(context, null);
		msgApi.registerApp(appid);
		this.payListener = payListener;
	}
	
	@Override
	public void payOrder(Object object) {
		PayOrderBean payOrderBean = (PayOrderBean) object;
		if (null != payOrderBean) {
			String str = payOrderBean.getRequest();
			if (!TextUtils.isEmpty(str)) {
				PayReq payReq = decodeXml(str);
				if (null != payReq) {
					msgApi.registerApp(appid);
					msgApi.sendReq(payReq);
				}
			}
		}
	}

	private PayReq decodeXml(String content) {
		try {
			PayReq payReq = new PayReq();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					String nodeName = parser.getName();
					if (!"xml".equals(nodeName)) {
						switch (nodeName) {
						case "appid":
							payReq.appId = parser.nextText();
							break;
						case "noncestr":
							payReq.nonceStr = parser.nextText();
							break;
						case "package":
							payReq.packageValue = parser.nextText();
							break;
						case "partnerid":
							payReq.partnerId = parser.nextText();
							break;
						case "prepayid":
							payReq.prepayId = parser.nextText();
							break;
						case "sign":
							payReq.sign = parser.nextText();
							break;
						case "timestamp":
							payReq.timeStamp = parser.nextText();
							break;
						default:
							break;
						}
					}
					break;

				case XmlPullParser.END_TAG:
					break;
				}
				event = parser.next();
			}
			return payReq;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

