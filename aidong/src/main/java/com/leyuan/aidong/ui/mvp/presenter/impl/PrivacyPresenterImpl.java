package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.mvp.model.UserInfoModel;
import com.leyuan.aidong.ui.mvp.model.impl.UserInfoModelImpl;
import com.leyuan.aidong.ui.mvp.view.PrivacyActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.SharePrefUtils;

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
        model = new UserInfoModelImpl(context);
    }

    public boolean getIsHide() {
        return SharePrefUtils.getBoolean(context, HIDE, true);
    }

    public void hideSelf(final int hide) {
        model.hideSelf(new BaseSubscriber<BaseBean>(context) {
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
}
