package com.example.aidong.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.leyuan.aidong.utils.Logger;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WeixinRegisterReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
		api.registerApp("wx365ab323b9269d30");

		Logger.i("WeixinRegisterReceiver","onReceive");
	}
}
