package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.user.MineInfoBean;
import com.leyuan.aidong.http.subscriber.IsLoginSubscriber;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.UserInfoModel;
import com.leyuan.aidong.ui.mvp.model.impl.UserInfoModelImpl;
import com.leyuan.aidong.ui.mvp.view.MineInfoView;

/**
 * Created by user on 2017/3/23.
 */
public class MineInfoPresenterImpl {
    private Context context;
    private MineInfoView view;
    private UserInfoModel model;

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
            }
        });
    }

}
