package com.leyuan.aidong.module.thirdpartylogin;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by huyushuai on 2016/12/2.
 */

public class WXLogin {
    public final static String WX_APP_ID = "wx365ab323b9269d30";
    private IWXAPI api;
    Context context;

    public WXLogin(Context context) {
        this.context = context;
        api = WXAPIFactory.createWXAPI(context, WX_APP_ID, true);
        api.registerApp(WX_APP_ID);
    }

    public void loginWithWX(Context context) {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(context, WX_APP_ID, false);
        }
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "没有安装微信", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Constant.WX_LOGIN_SUCCESS_ACTION);
            intent.putExtra(Constant.STATE, 4);
            intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            context.sendBroadcast(intent);
            Logger.i("login ", " sendBroadcast");


            return;
        }
        api.registerApp(WX_APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "com.lht.bridge.session";
        api.sendReq(req);
    }


}
