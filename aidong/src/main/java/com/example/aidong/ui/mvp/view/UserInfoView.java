package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.UserBean;

import java.util.List;

/**
 * Created by user on 2018/2/2.
 */
public interface UserInfoView {

    void onGetUserData(List<UserBean> followings);



}
