package com.example.aidong.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.aidong.R;
import com.example.aidong.module.pay.weixinpay.WXPayUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, WXPayUtil.APP_ID);

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
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                sendBroadcast(new Intent("pay_fankui"));
                //                modify_status(WXPayUtil.orderId, WXPayUtil.out_trade_no);
            } else {


                //                if (ReservationAndPurchaseActivity.daijinjuanshiyongtishi == true) {
                //                    ToastTools.showToast(WXPayEntryActivity.this, "代金券已被锁定，请五分钟后使用");
                //                } else if (ConfirmPurchaseActivity.zhekoujuanshiyongtishi == true) {
                //                    ToastTools.showToast(WXPayEntryActivity.this, "折扣券已被锁定，请五分钟后使用");
                //                } else if (ConfirmPurchaseActivity.duihuangjuanshiyongtishi == true) {
                //                    ToastTools.showToast(WXPayEntryActivity.this, "兑换券已被锁定，请五分钟后使用");
                //                } else if (ConfirmPurchaseActivity.daijinjuanshiyongtishi == true) {
                //                    ToastTools.showToast(WXPayEntryActivity.this, "代金券已被锁定，请五分钟后使用");
                //                } else {
                //                    ToastTools.showToast(WXPayEntryActivity.this, "支付失败");
                //
                //                }
                //                sendBroadcast(new Intent("zhifuquxiao"));

            }
        }
        finish();
    }

}