package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.app.Activity;

import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.impl.RegisterModel;
import com.leyuan.aidong.ui.mvp.view.CompleteUserViewInterface;

import java.util.Map;

import rx.Subscriber;

public class CompleteUserPresenter {
    private Activity mContext;
    private CompleteUserViewInterface mViewInterface;
    private RegisterModel registerModel;


    public CompleteUserPresenter(Activity mContext, CompleteUserViewInterface mViewInterface) {
        this.mContext = mContext;
        this.mViewInterface = mViewInterface;
        registerModel = new RegisterModel();
    }


    public void  completeUserInfo(Map<String,String> params,String filePath){
       registerModel.completeUserInfo(completeSubcribe,params,filePath);
   }
    public void  completeUserAvatarUpdate(String filePath){
        registerModel.userAvatarUpload(completeSubcribe,filePath);
    }

    private Subscriber<LoginResult> completeSubcribe = new BaseSubscriber<LoginResult>(mContext) {

        @Override
        public void onStart() {
            super.onStart();
            mViewInterface.onUploadStart();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            mViewInterface.onCompletUserInfoCallBack(false);
        }

        @Override
        public void onNext(LoginResult loginResult) {
            mViewInterface.onCompletUserInfoCallBack(true);
            if(loginResult != null && loginResult.getUser()!=null){
                App.mInstance.setUser(loginResult.getUser());
            }

        }
    };

    private Subscriber<LoginResult> subscriberUserAvatar= new BaseSubscriber<LoginResult>(mContext) {

        @Override
        public void onStart() {
            super.onStart();
            mViewInterface.onUploadStart();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            mViewInterface.onCompletUserInfoCallBack(false);
        }

        @Override
        public void onNext(LoginResult loginResult) {
            mViewInterface.onCompletUserInfoCallBack(true);
            if(loginResult != null && loginResult.getUser()!=null){
                App.mInstance.setUser(loginResult.getUser());
            }
        }
    };
}
