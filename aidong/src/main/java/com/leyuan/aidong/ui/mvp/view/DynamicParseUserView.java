package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.UserBean;

import java.util.List;

/**
 * Created by user on 2017/3/22.
 */
public interface DynamicParseUserView {
    void onGetUserData(List<UserBean> like, int page);

    void addFollowResult(BaseBean baseBean);

    void cancelFollowResult(BaseBean baseBean);
}
