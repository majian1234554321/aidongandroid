package com.leyuan.aidong.module.thirdpartylogin;

import android.content.Context;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by huyushuai on 2016/12/2.
 */

public class WXLogin {
    public final static String WX_APP_ID = "wx365ab323b9269d30";
    private IWXAPI api;
    public  WXLogin(Context context){
        api = WXAPIFactory.createWXAPI(context, WX_APP_ID, true);
        api.registerApp(WX_APP_ID);
    }

    public void loginWithWX(Context context) {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(context, WX_APP_ID, false);
        }
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        api.registerApp(WX_APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "com.lht.bridge.session";
        api.sendReq(req);
    }





}
