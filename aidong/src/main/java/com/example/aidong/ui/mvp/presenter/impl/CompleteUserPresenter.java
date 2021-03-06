package com.example.aidong.ui.mvp.presenter.impl;

import android.app.Activity;

import com.example.aidong .entity.model.result.LoginResult;
import com.example.aidong .http.subscriber.IsLoginSubscriber;
import com.example.aidong .ui.App;
import com.example.aidong .ui.mvp.model.impl.RegisterModel;
import com.example.aidong .ui.mvp.view.CompleteUserViewInterface;

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

    private Subscriber<LoginResult> completeSubcribe = new IsLoginSubscriber<LoginResult>(mContext) {

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

    private Subscriber<LoginResult> subscriberUserAvatar= new IsLoginSubscriber<LoginResult>(mContext) {

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
