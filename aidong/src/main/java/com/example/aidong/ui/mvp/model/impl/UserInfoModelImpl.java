package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.DynamicsData;
import com.example.aidong .entity.data.UserInfoData;
import com.example.aidong .entity.user.MineInfoBean;
import com.example.aidong .entity.user.PrivacySettingData;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.UserInfoService;
import com.example.aidong .ui.App;
import com.example.aidong .ui.mvp.model.UserInfoModel;
import com.example.aidong .utils.Logger;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public class UserInfoModelImpl implements UserInfoModel {
    private UserInfoService userInfoService;

    public UserInfoModelImpl() {
        userInfoService = RetrofitHelper.createApi(UserInfoService.class);
    }

    @Override
    public void getUserInfo(Subscriber<UserInfoData> subscriber, String idongId) {
        userInfoService.getUserInfo(idongId)
                .compose(RxHelper.<UserInfoData>transform())
                .subscribe(subscriber);
    }


    @Override
    public void getMyselfUserInfo(Subscriber<UserInfoData> subscriber) {
        userInfoService.getMyselfUserInfo()
                .compose(RxHelper.<UserInfoData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void updateUserInfo(Subscriber<UserInfoData> subscriber,
                               String name,
                               String avatar,
                               String gender,
                               String birthday,
                               String signature,
                               String tag,
                               String sport,
                               String province,
                               String city,
                               String area,
                               String height,
                               String weight,
                               String bust,
                               String waist,
                               String hip,
                               String charm_site,
                               String frequency) {

        if (App.getInstance().isLogin()) {
            userInfoService.updateUserInfo(name, avatar, gender, birthday, signature, tag, sport, province,
                    city, area, height, weight, bust, waist, hip, charm_site, frequency)
                    .compose(RxHelper.<UserInfoData>transform())
                    .subscribe(subscriber);
        } else {

            Logger.i("login", "updateUserInfoWithToken token = " + App.getInstance().getToken());
            userInfoService.updateUserInfoWithToken(App.getInstance().getToken(),
                    name, avatar, gender, birthday, signature, tag, sport, province,
                    city, area, height, weight, bust, waist, hip, charm_site, frequency)
                    .compose(RxHelper.<UserInfoData>transform())
                    .subscribe(subscriber);
        }


    }

    @Override
    public void getUserDynamic(Subscriber<DynamicsData> subscriber, String id, int page) {
        userInfoService.getUserDynamic(id, page)
                .compose(RxHelper.<DynamicsData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getMineInfo(Subscriber<MineInfoBean> subscriber) {
        userInfoService.getMineInfo()
                .compose(RxHelper.<MineInfoBean>transform())
                .subscribe(subscriber);
    }


    @Override
    public void updatePassword(Subscriber<BaseBean> subscriber, String oldPassword,
                               String new_password, String confirm_password) {
        userInfoService.updatePassword(oldPassword, new_password, confirm_password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Deprecated
    @Override
    public void hideSelf(Subscriber<BaseBean> subscriber, String isHide) {
        userInfoService.hideSelf()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void hideSelf(Subscriber<BaseBean> subscriber) {
        userInfoService.hideSelf()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    @Override
    public void getHideSetting(Subscriber<PrivacySettingData> subscriber) {
        userInfoService.getHideSetting()
                .compose(RxHelper.<PrivacySettingData>transform())
                .subscribe(subscriber);
    }

}
