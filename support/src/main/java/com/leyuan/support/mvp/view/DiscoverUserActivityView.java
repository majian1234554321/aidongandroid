package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.UserBean;

import java.util.List;

/**
 * 发现-人
 * Created by song on 2016/8/29.
 */
public interface DiscoverUserActivityView {
    /**
     * 更新列表
     * @param userList
     */
    void updateRecyclerView(List<UserBean> userList);

    /**
     * 显示空值界面
     */
    void showEmptyView();

    /**
     * 隐藏空值界面
     */
    void hideEmptyView();

    /**
     * 整体界面显示无网络界面
     * 对于有缓存的界面空实现该方法即可
     */
    void showErrorView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
