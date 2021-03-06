package com.example.aidong.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.example.aidong .module.pay.PayInterface;
import com.example.aidong .module.pay.WeiXinPay;
import com.example.aidong .ui.BaseActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, WeiXinPay.appId);
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
                    Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                    if (null != payListener) {
                        payListener.onSuccess(code + "", resp);

                    }
                    break;

                default:
                    Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();

                    if (null != payListener) {
                        payListener.onFail(code + "", resp);
                    }
                    break;
            }
            finish();
        }
    }

}