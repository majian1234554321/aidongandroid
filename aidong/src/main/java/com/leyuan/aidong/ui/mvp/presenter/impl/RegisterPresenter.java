package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.RegisterModel;
import com.leyuan.aidong.ui.mvp.model.interfaces.RegisterModelInterface;
import com.leyuan.aidong.ui.mvp.presenter.RegisterPresenterInterface;
import com.leyuan.aidong.ui.mvp.view.RegisterViewInterface;


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
    public void regitserIdentify(String mobile, String imageIndentify) {
        mRegisterModelInterface.regitserIdentify(new BaseSubscriber<UserCoach>(mContext) {
            //            @Override
            //            public void onCompleted() {
            //
            //            }
            //
            //            @Override
                        public void onError(Throwable e) {
                           super.onError(e);
                            mRegisterViewInterface.getIdentifyCode(false);
                        }

            @Override
            public void onNext(UserCoach s) {
                mRegisterViewInterface.getIdentifyCode(true);
            }
        }, mobile);

    }

    public void foundIdentify(String mobile) {
        mRegisterModelInterface.foundIdentify(new BaseSubscriber<UserCoach>(mContext) {
            //            @Override
            //            public void onCompleted() {
            //
            //            }
            //
            //            @Override
            //            public void onError(Throwable e) {
            //                e.printStackTrace();
            //                mRegisterViewInterface.getIdentifyCode(false);
            //            }

            @Override
            public void onNext(UserCoach s) {
                mRegisterViewInterface.getIdentifyCode(true);
            }
        }, mobile);

    }

    @Override
    public void checkIdentify(String mobile, String code, String password) {
        mRegisterModelInterface.checkIdentify(new BaseSubscriber<UserCoach>(mContext) {
//            @Override
//            public void onCompleted() {
//
//            }
//
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mRegisterViewInterface.register(false);
            }

            @Override
            public void onNext(UserCoach user) {
                mRegisterViewInterface.register(true);
                App.mInstance.setUser(user);
            }
        }, mobile, code, password);
    }

    @Override
    public void bindingCaptcha(String mobile) {
        mRegisterModelInterface.bindingCaptcha(new BaseSubscriber<UserCoach>(mContext) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(UserCoach user) {
            }
        }, mobile);
    }

    @Override
    public void checkCaptchaImage(String mobile, String captcha) {
        mRegisterModelInterface.checkCaptchaImage(new BaseSubscriber<UserCoach>(mContext) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(UserCoach user) {

            }
        }, mobile,captcha);

    }
}
