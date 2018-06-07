package com.example.aidong.ui.mvp.model;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.DynamicsData;
import com.example.aidong .entity.data.UserInfoData;
import com.example.aidong .entity.user.MineInfoBean;
import com.example.aidong .entity.user.PrivacySettingData;

import rx.Subscriber;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public interface UserInfoModel {

    void getUserInfo(Subscriber<UserInfoData> subscriber, String idongId);

    void updateUserInfo(Subscriber<UserInfoData> subscriber,
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
                        String frequency);

    void getUserDynamic(Subscriber<DynamicsData> subscriber,String id, int page);

    void getMineInfo(Subscriber<MineInfoBean> subscriber);

    void getMyselfUserInfo(Subscriber<UserInfoData> getMyselfUserInfo);


    void updatePassword(Subscriber<BaseBean> subscriber, String oldPassword,
                        String new_password, String confirm_password);

    void hideSelf(Subscriber<BaseBean> subscriber, String isHide);

    void hideSelf(Subscriber<BaseBean> subscriber);

    void getHideSetting(Subscriber<PrivacySettingData> subscriber);
}
