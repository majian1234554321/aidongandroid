package com.example.aidong.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.leyuan.aidong.ui.mine.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.LoginPresenter;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.UiManager;
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
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.handleIntent(getIntent(), this);
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

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
                    //此处进行数据请求，请求用户信息
//                    loginPresenter.loginSns("weixin",code_code);

                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.WX_LOGIN_CODE, code_code);
                    UiManager.activityJump(this, bundle, LoginActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
                    Logger.i("share", " activityJump loginactivity");


                } else {
                    Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
                }


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
