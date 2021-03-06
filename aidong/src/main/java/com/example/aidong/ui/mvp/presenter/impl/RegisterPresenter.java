package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.example.aidong .entity.model.UserCoach;
import com.example.aidong .entity.user.CheckIdentifyResult;
import com.example.aidong .http.subscriber.IsLoginSubscriber;
import com.example.aidong .ui.App;
import com.example.aidong .ui.mvp.model.RegisterModelInterface;
import com.example.aidong .ui.mvp.model.impl.RegisterModel;
import com.example.aidong .ui.mvp.presenter.RegisterPresenterInterface;
import com.example.aidong .ui.mvp.view.RegisterViewInterface;
import com.example.aidong .utils.LogAidong;
import com.example.aidong .utils.Logger;


public class RegisterPresenter implements RegisterPresenterInterface {

    private Context mContext;
    private RegisterModelInterface mRegisterModelInterface;
    private RegisterViewInterface mRegisterViewInterface;
    private String token;
    private String bindingMobile;

    public RegisterPresenter(Context context, RegisterViewInterface mRegisterViewInterface) {
        mContext = context;
        this.mRegisterViewInterface = mRegisterViewInterface;
        mRegisterModelInterface = new RegisterModel();
    }

    @Override
    public void regitserIdentify(String mobile) {
        mRegisterModelInterface.regitserIdentify(new IsLoginSubscriber<UserCoach>(mContext) {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mRegisterViewInterface.onGetIdentifyCode(false);
            }

            @Override
            public void onNext(UserCoach s) {
                LogAidong.i("onNext token = ", "" + s.getToken());
                if (s != null)
                    App.mInstance.setToken(s.getToken());
                mRegisterViewInterface.onGetIdentifyCode(true);
            }
        }, mobile);

    }

    public void foundIdentify(String mobile) {
        mRegisterModelInterface.foundIdentify(new IsLoginSubscriber<UserCoach>(mContext) {

            @Override
            public void onStart() {
                super.onStart();
                mRegisterViewInterface.onRequestStart();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mRegisterViewInterface.onGetIdentifyCode(false);
            }

            @Override
            public void onNext(UserCoach s) {
                LogAidong.i("onNext token = ", "" + s.getToken());
                if (s != null)
                    App.mInstance.setToken(s.getToken());
                mRegisterViewInterface.onGetIdentifyCode(true);
            }
        }, mobile);

    }

    @Override
    public void checkIdentify(String token, String code, String password) {
        mRegisterModelInterface.checkIdentify(new IsLoginSubscriber<CheckIdentifyResult>(mContext) {
            @Override
            public void onStart() {
                super.onStart();
                mRegisterViewInterface.onRequestStart();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mRegisterViewInterface.register(false);
            }

            @Override
            public void onNext(CheckIdentifyResult user) {
                if (user != null) {
                    App.getInstance().setToken(user.getToken());

                    Logger.i("login", "checkIdentify token = " + user.getToken());
                }

                mRegisterViewInterface.register(true);

            }
        }, token, code, password);
    }


    @Override
    public void checkIdentifyRegister(String token, String code, String password) {
        mRegisterModelInterface.checkIdentifyRegister(new IsLoginSubscriber<CheckIdentifyResult>(mContext) {
            @Override
            public void onStart() {
                super.onStart();
                mRegisterViewInterface.onRequestStart();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
//                mRegisterViewInterface.register(false);
                mRegisterViewInterface.onRegisterResult(null, null);
            }

            @Override
            public void onNext(CheckIdentifyResult user) {

                App.getInstance().setUser(user.getUser());
                mRegisterViewInterface.onRegisterResult(user.getUser(), user.getCoupons());

//                if (user != null) {
//                    App.getInstance().setToken(user.getToken());
//                    Logger.i("login", "checkIdentify token = " + user.getToken());
//                }
//                mRegisterViewInterface.register(true);
            }
        }, token, code, password);
    }

    @Override
    public void checkIdentifyBinding(String captcha) {
        mRegisterModelInterface.checkIdentify(new IsLoginSubscriber<CheckIdentifyResult>(mContext) {
            @Override
            public void onStart() {
                super.onStart();
                mRegisterViewInterface.onRequestStart();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mRegisterViewInterface.register(false);
            }

            @Override
            public void onNext(CheckIdentifyResult user) {
                if (bindingMobile != null) {
                    UserCoach userCoach = App.mInstance.getUser();
                    userCoach.setMobile(bindingMobile);
                    App.mInstance.setUser(userCoach);
                }
                mRegisterViewInterface.register(true);

            }
        }, token, captcha, null);
    }

    @Override
    public void checkIdentifyBindingSns(String captcha) {
        mRegisterModelInterface.checkIdentify(new IsLoginSubscriber<CheckIdentifyResult>(mContext) {
            @Override
            public void onStart() {
                super.onStart();
                mRegisterViewInterface.onRequestStart();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mRegisterViewInterface.register(false);
            }

            @Override
            public void onNext(CheckIdentifyResult user) {
                if (user.getUser() != null){
                    App.getInstance().setUser(user.getUser());
                }

//                if (bindingMobile != null) {
//                    UserCoach userCoach = App.mInstance.getUser();
//                    userCoach.setMobile(bindingMobile);
//                    App.mInstance.setUser(userCoach);
//                }
                mRegisterViewInterface.register(true);

            }
        }, token, captcha, null);
    }

    @Override
    public void bindingCaptcha(String mobile) {
        bindingMobile = mobile;
        mRegisterModelInterface.bindingCaptcha(new IsLoginSubscriber<UserCoach>(mContext) {

            @Override
            public void onStart() {
                super.onStart();
                mRegisterViewInterface.onRequestStart();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mRegisterViewInterface.onGetIdentifyCode(false);
            }

            @Override
            public void onNext(UserCoach user) {
                LogAidong.i("onNext token = ", "" + user.getToken());
                if (user != null) {
                    token = user.getToken();
                }
                mRegisterViewInterface.onGetIdentifyCode(true);
            }
        }, mobile);
    }

    @Override
    public void bindingCaptchaSns(String mobile, String profile_info, String type) {
        bindingMobile = mobile;
        mRegisterModelInterface.bindingCaptchaSns(new IsLoginSubscriber<UserCoach>(mContext) {

            @Override
            public void onStart() {
                super.onStart();
                mRegisterViewInterface.onRequestStart();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mRegisterViewInterface.onGetIdentifyCode(false);
            }

            @Override
            public void onNext(UserCoach user) {
                LogAidong.i("onNext token = ", "" + user.getToken());
                if (user != null) {
                    token = user.getToken();
                }
                mRegisterViewInterface.onGetIdentifyCode(true);
            }
        }, mobile, profile_info, type);
    }


    @Override
    public void unbindingCaptcha(String mobile) {
        mRegisterModelInterface.unbindingCaptcha(new IsLoginSubscriber<UserCoach>(mContext) {

            @Override
            public void onStart() {
                super.onStart();
                mRegisterViewInterface.onRequestStart();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mRegisterViewInterface.onGetIdentifyCode(false);
            }

            @Override
            public void onNext(UserCoach user) {
                LogAidong.i("onNext token = ", "" + user.getToken());
                if (user != null) {
                    token = user.getToken();
                }
                mRegisterViewInterface.onGetIdentifyCode(true);
            }
        }, mobile);
    }


    @Override
    public void checkCaptchaImage(final String mobile, String captcha) {
        mRegisterModelInterface.checkCaptchaImage(new IsLoginSubscriber<UserCoach>(mContext) {
            @Override
            public void onStart() {
                super.onStart();
                mRegisterViewInterface.onRequestStart();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mRegisterViewInterface.onCheckCaptchaImageResult(false, mobile);
            }

            @Override
            public void onNext(UserCoach user) {
                mRegisterViewInterface.onCheckCaptchaImageResult(true, mobile);

            }
        }, mobile, captcha);
    }

    @Override
    public String getBingdingMobile() {
        return bindingMobile;
    }

    @Override
    public String getToken() {
        return token;
    }
}
