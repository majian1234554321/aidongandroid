package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.UserInfoData;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public interface UserInfoActivityView {
    void updateUserInfo(UserInfoData userInfoData);

    void addFollowResult(BaseBean baseBean);

    void cancelFollowResult(BaseBean baseBean);
}
