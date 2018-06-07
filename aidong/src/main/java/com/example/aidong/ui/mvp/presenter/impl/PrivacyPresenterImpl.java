package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.user.PrivacySettingData;
import com.example.aidong .http.subscriber.IsLoginSubscriber;
import com.example.aidong .ui.mvp.model.UserInfoModel;
import com.example.aidong .ui.mvp.model.impl.UserInfoModelImpl;
import com.example.aidong .ui.mvp.view.PrivacyActivityView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.SharePrefUtils;

/**
 * Created by user on 2017/3/24.
 */
public class PrivacyPresenterImpl {
    private static final String HIDE = "isHide";
    private UserInfoModel model;
    private Context context;
    private PrivacyActivityView view;

    public PrivacyPresenterImpl(Context context, PrivacyActivityView view) {
        this.context = context;
        this.view = view;
        model = new UserInfoModelImpl();
    }

    public boolean getIsHide() {
        return SharePrefUtils.getBoolean(context, HIDE, true);
    }

    @Deprecated
    public void hideSelf(final int hide) {
        model.hideSelf(new IsLoginSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean.getStatus() == Constant.OK) {
                    view.onHideSelfSuccess(true);
                    SharePrefUtils.putBoolean(context, HIDE, hide == 0);
                } else {
                    view.onHideSelfSuccess(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.onHideSelfSuccess(false);
            }
        }, hide + "");
    }

    public void hideSelf() {
        model.hideSelf(new IsLoginSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean.getStatus() == Constant.OK) {
                    view.onHideSelfSuccess(true);
                } else {
                    view.onHideSelfSuccess(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.onHideSelfSuccess(false);
            }
        });
    }

    public void getHideSetting() {
        model.getHideSetting(new IsLoginSubscriber<PrivacySettingData>(context) {
            @Override
            public void onNext(PrivacySettingData privacySettingData) {
                if (privacySettingData != null && privacySettingData.getSetting() != null) {
                    view.onGetHideSetting(privacySettingData.getSetting().isHide());
                } else {
                    view.onGetHideSetting(false);
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.onGetHideSetting(false);
            }
        });
    }
}
