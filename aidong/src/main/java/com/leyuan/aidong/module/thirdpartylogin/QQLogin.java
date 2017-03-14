package com.leyuan.aidong.module.thirdpartylogin;

import android.app.Activity;
import android.content.Intent;

import com.leyuan.aidong.R;
import com.leyuan.aidong.utils.Logger;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2016/12/2.
 */

//    应用调用Andriod_SDK接口时，如果要成功接收到回调，需要在调用接口的Activity的onActivityResult方法中增加如下代码：
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Tencent.onActivityResultData(requestCode,resultCode,data,listener);
//    }

public class QQLogin {

    private Tencent mTencent;
    private Activity context;
    private BaseUiListener mUiListener;
    private ThirdLoginUtils.OnThirdPartyLogin listener;

    public QQLogin(Activity context, ThirdLoginUtils.OnThirdPartyLogin listener) {
        mTencent = Tencent.createInstance(context.getString(R.string.qq_id), context.getApplicationContext());
        this.context = context;
        this.listener = listener;
        mUiListener = new BaseUiListener();
    }

    public void login() {
        mTencent.login(context, "all", mUiListener);
    }

    public BaseUiListener getUiListener() {
        return mUiListener;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, mUiListener);
    }

    public void release() {
        context = null;
        mUiListener = null;
    }

    class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            JSONObject jsonObject = (JSONObject) o;
            try {
                String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                //OPENID,作为唯一身份标识
                String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);


                Logger.i("login", "token = " + token + ", expires = " + expires + ", openid = " + openId);
                listener.onThridLoginStart("qq", token);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(UiError e) {

        }

        @Override
        public void onCancel() {

        }
    }


}
