package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.RegisterModelInterface;
import com.leyuan.aidong.ui.mvp.model.impl.RegisterModel;
import com.leyuan.aidong.ui.mvp.presenter.RegisterPresenterInterface;
import com.leyuan.aidong.ui.mvp.view.RegisterViewInterface;
import com.leyuan.aidong.utils.LogAidong;


public class RegisterPresenter implements RegisterPresenterInterface {

    private Context mContext;
    private RegisterModelInterface mRegisterModelInterface;
    private RegisterViewInterface mRegisterViewInterface;

    public RegisterPresenter(Context context, RegisterViewInterface mRegisterViewInterface) {
        mContext = context;
        this.mRegisterViewInterface = mRegisterViewInterface;
        mRegisterModelInterface = new RegisterModel();
    }

    @Override
    public void regitserIdentify(String mobile) {
        mRegisterModelInterface.regitserIdentify(new BaseSubscriber<UserCoach>(mContext) {

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
        mRegisterModelInterface.foundIdentify(new BaseSubscriber<UserCoach>(mContext) {

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
        mRegisterModelInterface.checkIdentify(new BaseSubscriber<UserCoach>(mContext) {
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
            public void onNext(UserCoach user) {
                if (user != null)
                    App.mInstance.setToken(user.getToken());
                mRegisterViewInterface.register(true);

            }
        }, token, code, password);
    }

    @Override
    public void bindingCaptcha(String mobile) {
        mRegisterModelInterface.bindingCaptcha(new BaseSubscriber<UserCoach>(mContext) {

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
                if (user != null)
                    App.mInstance.setToken(user.getToken());
                mRegisterViewInterface.onGetIdentifyCode(true);
            }
        }, mobile);
    }

    @Override
    public void checkCaptchaImage(final String mobile, String captcha) {
        mRegisterModelInterface.checkCaptchaImage(new BaseSubscriber<UserCoach>(mContext) {
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

}
