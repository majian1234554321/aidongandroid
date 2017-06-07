package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.impl.ChangePasswordModel;
import com.leyuan.aidong.ui.mvp.view.ChangePasswordViewInterface;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.ToastGlobal;


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
