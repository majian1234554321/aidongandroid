package com.leyuan.aidong.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.leyuan.aidong.R;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.WeiXinPay;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, getString(R.string.weixingAppID));
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = resp.errCode;
            PayInterface.PayListener payListener = WeiXinPay.payListener;
            switch (code) {
                case 0:		//成功
                    if (null != payListener) {
                        payListener.success(code + "", resp);
                    }
                    break;

                default:
                    if (null != payListener) {
                        payListener.fail(code + "", resp);
                    }
                    break;
            }
            finish();
        }
    }

}