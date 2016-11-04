package com.leyuan.aidong.ui.mvp.presenter;

import android.content.Context;

import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.ChangePasswordModel;
import com.leyuan.aidong.ui.mvp.view.ChangePasswordViewInterface;


public class ChangePasswordPresenter {

    private Context context;
    private ChangePasswordViewInterface viewInterface;
    private ChangePasswordModel mModel;

    public ChangePasswordPresenter(Context context, ChangePasswordViewInterface viewInterface) {
        this.context = context;
        this.viewInterface = viewInterface;
        mModel = new ChangePasswordModel();
    }

    public void changePassword( String password, String new_password, String re_passsword) {
       mModel.changePassword(new BaseSubscriber<LoginResult>(context) {
           @Override
           public void onNext(LoginResult loginResult) {
               if(loginResult!=null && loginResult.getUser()!=null){
                   viewInterface.onChangePasswordResult(loginResult.getUser());
                   App.mInstance.setUser(loginResult.getUser());
               }else{
                   viewInterface.onChangePasswordResult(null);
               }
           }
       },password,new_password,re_passsword);
    }
}
