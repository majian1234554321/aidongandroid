package com.leyuan.aidong.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.leyuan.aidong.utils.Logger;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String APP_ID = "wx365ab323b9269d30";
	private IWXAPI api;
	private String code_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, APP_ID, true);
		api.handleIntent(getIntent(),this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq baseReq) {

	}

	@Override
	public void onResp(BaseResp resp) {
		Logger.i("share","resp.errCode " +resp.errCode);
		String result = null;
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				result = "发送成功";
				code_code = ((SendAuth.Resp) resp).code;
				//此处进行数据请求，请求用户信息
				Toast.makeText(this, result+code_code, Toast.LENGTH_LONG).show();
				finish();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				result = "发送取消";
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				finish();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				result = "发送被拒绝";
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				finish();
				break;
			default:
				result = "发送返回";
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				finish();
				break;
		}
//		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}
}
