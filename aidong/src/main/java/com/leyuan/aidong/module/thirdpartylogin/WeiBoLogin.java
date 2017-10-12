package com.leyuan.aidong.module.thirdpartylogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.leyuan.aidong.R;
import com.leyuan.aidong.module.weibo.AccessTokenKeeper;
import com.leyuan.aidong.module.weibo.WeiBoConstants;
import com.leyuan.aidong.utils.Logger;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import java.text.SimpleDateFormat;

public class WeiBoLogin {

    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    private AuthInfo mAuthInfo;
    private Activity context;
    private ThirdLoginUtils.OnThirdPartyLogin listner;

    public WeiBoLogin(Activity context, ThirdLoginUtils.OnThirdPartyLogin listner) {
        this.context = context;
        this.listner = listner;

        mAuthInfo = new AuthInfo(context, WeiBoConstants.APP_KEY, WeiBoConstants.REDIRECT_URL, null);

        mSsoHandler = new SsoHandler(context, mAuthInfo);
//        mSsoHandler.authorize(new AuthListener());

        mAccessToken = AccessTokenKeeper.readAccessToken(context);
//        if (mAccessToken.isSessionValid()) {
//            updateTokenView(true);
//        }
    }

    public void loginSSO() {
        mSsoHandler.authorizeClientSso(new AuthListener());
    }

    public void loginWeb() {
        mSsoHandler.authorizeWeb(new AuthListener());
    }

    public void loginAllInOne() {
        mSsoHandler.authorize(new AuthListener());
    }

    /**
     * 显示当前 Token 信息。
     *
     * @param hasExisted 配置文件中是否已存在 token 信息并且合法
     */
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime()));
        String format = context.getString(R.string.weibosdk_demo_token_to_string_format_1);
        String.format(format, mAccessToken.getToken(), date);

        String message = String.format(format, mAccessToken.getToken(), date);
        if (hasExisted) {
            message = context.getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
        }
    }

    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {

            Logger.i("loginweibo", "AuthListener onComplete");
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            String token = mAccessToken.getToken();
            String uid = mAccessToken.getUid();//调后台接口的id

            Logger.i("loginweibo", "token = " + token + ", uid = " + uid);
            listner.onThridLoginStart("sina", token);
            //从这里获取用户输入的 电话号码信息
            String phoneNum = mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 显示 Token
//                updateTokenView(false);

                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(context, mAccessToken);
                Logger.i("loginweibo", "ccessTokenKeeper.writeAccessToken(context, mAccessToken);");

            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");

                Logger.i("loginweibo", "code = " + code);

            }

        }

        @Override
        public void onCancel() {
            listner.onThridLoginStart(null,null);
            Logger.i("loginweibo", "AuthListener onCancelJoin");

        }

        @Override
        public void onWeiboException(WeiboException e) {
            listner.onThridLoginStart(null,null);
            Logger.i("loginweibo", "AuthListener onWeiboException " + e.getMessage());
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
