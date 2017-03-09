package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.model.UserCoach;

import java.util.List;

/**
 * Created by user on 2017/3/4.
 */
public interface EmChatView {
    void onGetUserInfo(List<UserCoach> profile);
}
