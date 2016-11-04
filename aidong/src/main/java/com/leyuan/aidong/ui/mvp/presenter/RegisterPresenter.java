package com.leyuan.aidong.ui.mvp.presenter;

import android.content.Context;

import com.leyuan.aidong.entity.user.User;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.mvp.model.RegisterModel;
import com.leyuan.aidong.ui.mvp.model.interfaces.RegisterModelInterface;
import com.leyuan.aidong.ui.mvp.presenter.interfaces.RegisterPresenterInterface;
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
    public void regitserIdentify(String mobile) {
        mRegisterModelInterface.regitserIdentify(new BaseSubscriber<User>(mContext) {
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
            public void onNext(User s) {
                mRegisterViewInterface.getIdentifyCode(true);
            }
        }, mobile);

    }

    public void foundIdentify(String mobile) {
        mRegisterModelInterface.foundIdentify(new BaseSubscriber<User>(mContext) {
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
            public void onNext(User s) {
                mRegisterViewInterface.getIdentifyCode(true);
            }
        }, mobile);

    }

    @Override
    public void checkIdentify(String mobile, String code, String password, String re_password) {
        mRegisterModelInterface.checkIdentify(new BaseSubscriber<User>(mContext) {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mRegisterViewInterface.register(false);
//            }

            @Override
            public void onNext(User user) {
                mRegisterViewInterface.register(true);
//                App.mInstance.setUser(user);
            }
        }, mobile, code, password, re_password);
    }
}
