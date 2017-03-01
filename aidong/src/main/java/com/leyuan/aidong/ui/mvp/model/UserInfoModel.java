package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.DynamicsData;
import com.leyuan.aidong.entity.data.UserInfoData;

import rx.Subscriber;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public interface UserInfoModel {

    void getUserInfo(Subscriber<UserInfoData> subscriber, String idongId);

    void updateUserInfo(Subscriber<BaseBean> subscriber,
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
}