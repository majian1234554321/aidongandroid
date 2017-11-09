package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.subscriber.IsLoginSubscriber;
import com.leyuan.aidong.module.thirdpartylogin.ThirdLoginUtils;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.LoginModelInterface;
import com.leyuan.aidong.ui.mvp.model.impl.LoginModel;
import com.leyuan.aidong.ui.mvp.presenter.LoginPresenterInterface;
import com.leyuan.aidong.ui.mvp.view.LoginAutoView;
import com.leyuan.aidong.ui.mvp.view.LoginExitView;
import com.leyuan.aidong.ui.mvp.view.LoginViewInterface;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.Logger;

public class LoginPresenter implements LoginPresenterInterface {

    private Context context;
    private LoginViewInterface loginViewInterface;
    private LoginAutoView loginAutoListener;
    private LoginModelInterface loginModel;
    //    private QQLogin qqLogin;
    private ThirdLoginUtils thirdLoginUtils;
    private LoginExitView exitLoginListener;

    public LoginPresenter(Activity context, ThirdLoginUtils.OnThirdPartyLogin thirdLoginListner) {
        this.context = context;
        loginModel = new LoginModel();
        thirdLoginUtils = new ThirdLoginUtils(context, thirdLoginListner);

//        qqLogin = new QQLogin((Activity) context, thirdPartyLoginListener);
    }

    public LoginPresenter(Context context) {
        this.context = context;
        loginModel = new LoginModel();
    }


    public void setLoginAutoListener(LoginAutoView loginAutoListener) {
        this.loginAutoListener = loginAutoListener;
    }

    public void setLoginViewInterface(LoginViewInterface loginViewInterface) {
        this.loginViewInterface = loginViewInterface;
    }

    public void setExitLoginListener(LoginExitView exitLoginListener) {
        this.exitLoginListener = exitLoginListener;
    }

    @Override
    public void login(String accout, String password) {
        loginModel.login(new IsLoginSubscriber<LoginResult>(context) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (loginViewInterface != null)
                    loginViewInterface.loginResult(null, null);
            }

            @Override
            public void onNext(LoginResult user) {
                App.getInstance().setUser(user.getUser());
                if (loginViewInterface != null) {
                    if (TextUtils.isEmpty(user.getUser().getMobile())) {
                        loginViewInterface.needBindingPhone(user.getUser(), user.getCoupons());
                    } else {
                        loginViewInterface.loginResult(user.getUser(), user.getCoupons());
                    }
                }

            }
        }, accout, password);

    }

    public void loginThirdParty(int mode) {
        thirdLoginUtils.loginThirdParty(mode);
    }

    @Override
    public void loginSns(String sns, String access) {
        loginModel.loginSns(new IsLoginSubscriber<LoginResult>(context) {

            @Override
            public void onStart() {
                super.onStart();
                Logger.i("loginSns", "onStart");
            }

            @Override
            public void onCompleted() {
                Logger.i("loginSns", "onCompleted");
            }
                @Override
            public void onError(Throwable e) {
                super.onError(e);
                Logger.i("loginSns", "onError");
                if (loginViewInterface != null)
                    loginViewInterface.loginResult(null, null);
                //                loginViewInterface.loginResult(false);
            }

            @Override
            public void onNext(LoginResult user) {
                Logger.i("loginSns", "onNext");
                Logger.i("LoginResult jsonobject",user.getProfile_info().toString());
                App.getInstance().setUser(user.getUser());
                if (loginViewInterface != null) {
                    if (TextUtils.isEmpty(user.getUser().getMobile())) {
                        loginViewInterface.snsNeedBindingPhone(user);
                    } else {
                        loginViewInterface.loginResult(user.getUser(), user.getCoupons());
                    }
                }
            }
        }, sns, access);

    }

    @Override
    public void autoLogin() {
        loginModel.autoLogin(new IsLoginSubscriber<LoginResult>(context) {
            @Override
            public void onNext(LoginResult user) {
                App.mInstance.setUser(user.getUser());
                if (loginAutoListener != null)
                    loginAutoListener.onAutoLoginResult(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (loginAutoListener != null)
                    loginAutoListener.onAutoLoginResult(false);
            }
        });

    }

    @Override
    public void exitLogin() {
        loginModel.exitLogin(new IsLoginSubscriber<LoginResult>(context) {
            @Override
            public void onNext(LoginResult user) {
                if (exitLoginListener != null)
                    exitLoginListener.onExitLoginResult(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (exitLoginListener != null)
                    exitLoginListener.onExitLoginResult(false);
            }
        });

    }

    public void onActivityResultData(int requestCode, int resultCode, Intent data) {
        thirdLoginUtils.onActivityResult(requestCode, resultCode, data);
    }

    private final ThirdLoginUtils.OnThirdPartyLogin thirdLoginListner = new ThirdLoginUtils.OnThirdPartyLogin() {
        @Override
        public void onThridLoginStart(String sns, String code) {
            DialogUtils.showDialog(context, "", true);
            loginSns(sns, code);
        }
    };

}
