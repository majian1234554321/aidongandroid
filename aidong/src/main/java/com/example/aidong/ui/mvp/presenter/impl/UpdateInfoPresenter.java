package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.example.aidong .entity.model.result.LoginResult;
import com.example.aidong .http.subscriber.IsLoginSubscriber;
import com.example.aidong .ui.App;
import com.example.aidong .ui.mvp.model.impl.UploadUserModel;
import com.example.aidong .ui.mvp.view.UpdateInfoViewInterface;


public class UpdateInfoPresenter {
    private Context context;
    private UpdateInfoViewInterface mInterface;
    private UploadUserModel mModel;

    public UpdateInfoPresenter(Context context, UpdateInfoViewInterface anInterface) {
        this.context = context;
        mInterface = anInterface;
        mModel = new UploadUserModel();
    }

    public void updateInfo(String filePath) {
        mModel.updateInfo(new IsLoginSubscriber<LoginResult>(context) {
            @Override
            public void onNext(LoginResult loginResult) {
                if (loginResult != null) {
//                    mInterface.onUpdateInfo(loginResult.getUser());
                    App.mInstance.setUser(loginResult.getUser());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mInterface.onUpdateInfo(null);
            }
        }, filePath);
    }
}
