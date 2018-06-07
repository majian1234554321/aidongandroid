package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.UserBean;

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
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();

    void showEmptyView();

    void addFollowResult(BaseBean baseBean);

    void cancelFollowResult(BaseBean baseBean);
}
