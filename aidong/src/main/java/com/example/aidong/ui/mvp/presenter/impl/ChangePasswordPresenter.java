package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .http.subscriber.BaseSubscriber;
import com.example.aidong .ui.App;
import com.example.aidong .ui.mvp.model.impl.ChangePasswordModel;
import com.example.aidong .ui.mvp.view.ChangePasswordViewInterface;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.ToastGlobal;


public class ChangePasswordPresenter {

    private Context context;
    private ChangePasswordViewInterface viewInterface;
    private ChangePasswordModel mModel;

    public ChangePasswordPresenter(Context context, ChangePasswordViewInterface viewInterface) {
        this.context = context;
        this.viewInterface = viewInterface;
        mModel = new ChangePasswordModel();
    }

    public void changePassword(String password, String new_password, String re_passsword) {
        mModel.changePassword(new BaseSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean.getStatus() == Constant.OK) {
                    App.getInstance().exitLogin();
                    viewInterface.onChangePasswordResult(true);
                } else {
                    if(baseBean.getMessage() != null){
                        ToastGlobal.showShort(baseBean.getMessage());
                    }
                    viewInterface.onChangePasswordResult(false);

                }


//               if (loginResult != null && loginResult.getUser() != null) {
//                   viewInterface.onChangePasswordResult(loginResult.getUser());
//                   App.mInstance.setUser(loginResult.getUser());
//               } else {
//                   viewInterface.onChangePasswordResult(null);
//               }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewInterface.onChangePasswordResult(false);

            }
        }, password, new_password, re_passsword);
    }
}
