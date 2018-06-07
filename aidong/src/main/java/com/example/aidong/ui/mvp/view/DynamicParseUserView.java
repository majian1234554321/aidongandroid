package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.UserBean;

import java.util.List;

/**
 * Created by user on 2017/3/22.
 */
public interface DynamicParseUserView {
    void onGetUserData(List<UserBean> like, int page);

    void addFollowResult(BaseBean baseBean);

    void cancelFollowResult(BaseBean baseBean);
}
