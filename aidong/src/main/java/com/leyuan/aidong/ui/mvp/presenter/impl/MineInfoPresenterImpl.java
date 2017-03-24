package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.user.MineInfoBean;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
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

    public MineInfoPresenterImpl(Context context, MineInfoView listner) {
        this.context = context;
        this.view = listner;
        model = new UserInfoModelImpl(context);
    }

    public void getMineInfo() {
        model.getMineInfo(new BaseSubscriber<MineInfoBean>(context) {
            @Override
            public void onNext(MineInfoBean mineInfoBean) {
                 view.onGetMineInfo(mineInfoBean);
            }
        });
    }

}
