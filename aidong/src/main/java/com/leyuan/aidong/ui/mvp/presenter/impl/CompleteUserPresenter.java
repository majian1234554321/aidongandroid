package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.app.Activity;

import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.mvp.model.RegisterModel;
import com.leyuan.aidong.ui.mvp.view.CompleteUserViewInterface;

import java.util.Map;

import rx.Subscriber;

/**
 * Created by user on 2016/11/17.
 */
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

    private Subscriber<UserCoach> completeSubcribe = new BaseSubscriber<UserCoach>(mContext) {
        @Override
        public void onError(Throwable e) {
            super.onError(e);
            mViewInterface.OnCompletUserInfoCallBack(false);
        }

        @Override
        public void onNext(UserCoach userCoach) {
            mViewInterface.OnCompletUserInfoCallBack(true);

        }
    };
}
