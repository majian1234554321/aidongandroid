package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.UserBean;

import java.util.List;

/**
 * Created by user on 2018/2/2.
 */
public interface CircleView {




    void loadMoreData(List<UserBean> followings);
}
