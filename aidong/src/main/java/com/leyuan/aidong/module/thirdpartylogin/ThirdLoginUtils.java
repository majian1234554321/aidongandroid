package com.leyuan.aidong.module.thirdpartylogin;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by user on 2016/12/7.
 */

public class ThirdLoginUtils {

    public static final int LOGIN_WEIXIN = 1;
    public static final int LOGIN_QQ = 2;
    public static final int LOGIN_WEIBO = 3;

    private QQLogin qqLogin;
    private WeiBoLogin weiBoLogin;
    private WXLogin wxLogin;
    private Activity context;

    public ThirdLoginUtils(Activity context, OnThirdPartyLogin listner) {
        this.context = context;
        qqLogin = new QQLogin(context,listner);
        weiBoLogin = new WeiBoLogin(context,listner);
        wxLogin = new WXLogin(context);
    }

    public void loginThirdParty(int loginMode) {
        switch (loginMode){
            case LOGIN_WEIXIN:
             wxLogin.loginWithWX(context);
                break;
            case LOGIN_WEIBO:
                weiBoLogin.loginAllInOne();
                break;
            case LOGIN_QQ:
                qqLogin.login();
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        qqLogin.onActivityResult(requestCode,resultCode,data);
        weiBoLogin.onActivityResult(requestCode,resultCode,data);
    }


    public interface OnThirdPartyLogin {
        void onThridLogin(String sns, String code);
    }
}
