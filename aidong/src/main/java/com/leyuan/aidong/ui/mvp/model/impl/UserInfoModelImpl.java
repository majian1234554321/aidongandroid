package com.leyuan.aidong.ui.mvp.model.impl;

import android.content.Context;

import com.leyuan.aidong.entity.data.DynamicsData;
import com.leyuan.aidong.entity.data.UserInfoData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.UserInfoService;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.UserInfoModel;
import com.leyuan.aidong.utils.Logger;

import rx.Subscriber;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public class UserInfoModelImpl implements UserInfoModel {
    private UserInfoService userInfoService;

    public UserInfoModelImpl(Context context) {
        userInfoService = RetrofitHelper.createApi(UserInfoService.class);
    }

    @Override
    public void getUserInfo(Subscriber<UserInfoData> subscriber, String idongId) {
        userInfoService.getUserInfo(idongId)
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

        if (App.mInstance.isLogin()) {
            userInfoService.updateUserInfo(name, avatar, gender, birthday, signature, tag, sport, province,
                    city, area, height, weight, bust, waist, hip, charm_site, frequency)
                    .compose(RxHelper.<UserInfoData>transform())
                    .subscribe(subscriber);
        } else {

            Logger.i("login", "updateUserInfoWithToken token = " + App.mInstance.getToken());
            userInfoService.updateUserInfoWithToken(App.mInstance.getToken(),
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
}
