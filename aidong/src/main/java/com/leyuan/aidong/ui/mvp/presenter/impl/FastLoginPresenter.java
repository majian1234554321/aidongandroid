package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.subscriber.IsLoginSubscriber;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.impl.FastLoginModel;
import com.leyuan.aidong.ui.mvp.view.FastLoginViewInterface;


public class FastLoginPresenter {
    private Context context;
    private FastLoginViewInterface viewInterface;
    private FastLoginModel model;

    public FastLoginPresenter(FastLoginViewInterface viewInterface, Context context) {
        this.viewInterface = viewInterface;
        this.context = context;
        model = new FastLoginModel();
    }

    public void getIdentify(String mobile) {
        model.getIdentify(new IsLoginSubscriber<LoginResult>(context) {
            @Override
            public void onNext(LoginResult loginResult) {
                viewInterface.onGetIdentify(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewInterface.onGetIdentify(false);
            }
        }, mobile);
    }

    public void login(String mobile, String code) {
        model.fastLogin(new IsLoginSubscriber<LoginResult>(context) {
            @Override
            public void onNext(LoginResult loginResult) {
                if(loginResult!=null && loginResult.getUser()!=null){
                    App.mInstance.setUser(loginResult.getUser());
                    viewInterface.onLoginResult(true);
                }else{
                    viewInterface.onLoginResult(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        },mobile,code);
    }
}
