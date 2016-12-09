package com.leyuan.aidong.module.thirdpartylogin;

import android.content.Context;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by huyushuai on 2016/12/2.
 */

public class WXLogin {

    private IWXAPI api;
    public  WXLogin(Context context){
        String appId = context.getString(R.string.weixingAppID);
        api = WXAPIFactory.createWXAPI(context, appId, true);
        api.registerApp(appId);
    }

    public void loginWithWX(Context context) {
        String appId = context.getString(R.string.weixingAppID);
        if (api == null) {
            api = WXAPIFactory.createWXAPI(context, appId, false);
        }
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        api.registerApp(appId);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "com.lht.bridge.session";
        api.sendReq(req);
    }





}
