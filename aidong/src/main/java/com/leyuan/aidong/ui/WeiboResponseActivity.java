package com.leyuan.aidong.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.leyuan.aidong.R;
import com.leyuan.aidong.module.weibo.WeiBoConstants;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastUtil;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;

import static com.leyuan.aidong.ui.App.context;

/**
 * Created by user on 2017/3/15.
 */
public class WeiboResponseActivity extends Activity implements IWeiboHandler.Response {


    private IWeiboShareAPI mWeiboShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, WeiBoConstants.APP_KEY);

        mWeiboShareAPI.registerApp();
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }
        Logger.i("share  WeiboResponseActivity onCreate");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.i("share  WeiboResponseActivity onNewIntent");
        mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        Logger.i("share  WeiboResponseActivity ", "weibo share baseResponse.errCode = ");
        if (baseResponse != null) {

            switch (baseResponse.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    ToastUtil.showConsecutiveShort(R.string.share_ok);
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    ToastUtil.showConsecutiveShort(R.string.share_cancel);
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    ToastUtil.showConsecutiveShort(R.string.share_fail);
                    break;
            }
        }
        finish();
    }
}
