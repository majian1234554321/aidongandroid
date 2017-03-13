package com.example.aidong.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    private static final String APP_ID = "wx365ab323b9269d30";
    private IWXAPI api;
    private String code_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Logger.i("share", "  public void onReq(BaseReq baseReq) {");
    }

    @Override
    public void onResp(BaseResp resp) {
        Logger.i("share", "resp.errCode " + resp.errCode + " type = " + resp.getType() + ", code = " + ((SendAuth.Resp) resp).code);
        String result = null;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "发送成功";
                code_code = ((SendAuth.Resp) resp).code;
                if (RETURN_MSG_TYPE_LOGIN == resp.getType()) {
                    Intent intent = new Intent();
                    intent.setAction(Constant.WX_LOGIN_SUCCESS_ACTION);
                    intent.putExtra(Constant.WX_LOGIN_CODE, code_code);
                    intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    sendBroadcast(intent);
                    Logger.i("login ", " sendBroadcast");
                } else {
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                break;
            default:
                result = "发送返回";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();

                break;
        }

        finish();
//		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}
