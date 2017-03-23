package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.SwitcherLayout;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public interface UserInfoPresent {
    void getUserInfo(String id);

    void getUserInfo(SwitcherLayout switcherLayout, String id);

    void commonLoadDynamic(String id);

    void requestMoreDynamic(String id, RecyclerView recyclerView, int pageSize, int page);


    void updateUserInfo(String name, String avatar, String gender, String birthday, String signature, String province, String city,
                        String area, String height, String weight, String frequency);


    void addFollow(String userId);
    void addLike(String userId,int position);

    void cancelFollow(String userId);
    void cancelLike(String userId,int position);

    void release();
}
