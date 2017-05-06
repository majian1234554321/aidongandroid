package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.leyuan.aidong.entity.user.MineInfoBean;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.mvp.model.UserInfoModel;
import com.leyuan.aidong.ui.mvp.model.impl.UserInfoModelImpl;
import com.leyuan.aidong.ui.mvp.view.MineInfoView;
import com.leyuan.aidong.utils.Constant;

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
        model = new UserInfoModelImpl(context);
    }

    public MineInfoPresenterImpl(Context context, MineInfoView listner) {
        this.context = context;
        this.view = listner;
        model = new UserInfoModelImpl(context);
    }

    public void getMineInfo() {
        model.getMineInfo(new BaseSubscriber<MineInfoBean>(context) {
            @Override
            public void onNext(MineInfoBean mineInfoBean) {
                if(view != null) {
                    view.onGetMineInfo(mineInfoBean);
                }

                if(mineInfoBean.getGyms() != null && !mineInfoBean.getGyms().isEmpty()){
                    Constant.gyms = mineInfoBean.getGyms();
                }

                if(!TextUtils.isEmpty(mineInfoBean.getActivity())){
                    Constant.activity = mineInfoBean.getActivity();
                }
            }
        });
    }

}
