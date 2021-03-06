package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.UserBean;

import java.util.List;

/**
 * 搜索用户
 * Created by song on 2016/9/1.
 */
public interface SearchUserFragmentView {

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();

    void onRecyclerViewRefresh(List<UserBean> userBeanList);

    void onRecyclerViewLoadMore(List<UserBean> userBeanList);


    void showEmptyView();

    void addFollowResult(BaseBean baseBean);

    void cancelFollowResult(BaseBean baseBean);
}
