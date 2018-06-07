package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.aidong .entity.user.MineInfoBean;
import com.example.aidong .http.subscriber.IsLoginSubscriber;
import com.example.aidong .ui.App;
import com.example.aidong .ui.mvp.model.UserInfoModel;
import com.example.aidong .ui.mvp.model.impl.UserInfoModelImpl;
import com.example.aidong .ui.mvp.view.MineInfoView;
import com.example.aidong .ui.mvp.view.OnRequestResponseCallBack;
import com.example.aidong .utils.Constant;

/**
 * Created by user on 2017/3/23.
 */
public class MineInfoPresenterImpl {
    private Context context;
    private MineInfoView view;
    private UserInfoModel model;
    private OnRequestResponseCallBack callBack;

    public Context getContext() {
        return context;
    }

    public MineInfoPresenterImpl(Context context) {
        this.context = context;
        model = new UserInfoModelImpl();
    }

    public MineInfoPresenterImpl(Context context, MineInfoView listner) {
        this.context = context;
        this.view = listner;
        model = new UserInfoModelImpl();
    }

    public void getMineInfo() {
        model.getMineInfo(new IsLoginSubscriber<MineInfoBean>(context) {
            @Override
            public void onNext(MineInfoBean mineInfoBean) {
                if (view != null) {
                    view.onGetMineInfo(mineInfoBean);
                }
                App.getInstance().saveMineInfoBean(mineInfoBean);
                if (callBack != null) {
                    callBack.onRequestResponse();
                }
                if (!TextUtils.isEmpty(mineInfoBean.getActivity())) {
                    Constant.activityOnLogin = mineInfoBean.getActivity();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (callBack != null) {
                    callBack.onRequestResponse();
                }
            }
        });
    }

    public void setOnRequestResponse(OnRequestResponseCallBack callBack) {
        this.callBack = callBack;

    }
}
