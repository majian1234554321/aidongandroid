package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.UserInfoData;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public interface UserInfoActivityView {
    void updateUserInfo(UserInfoData userInfoData);

    void addFollowResult(BaseBean baseBean);

    void cancelFollowResult(BaseBean baseBean);
}
