package com.example.aidong.ui.mvp.view;

import com.example.aidong.entity.UserBean;

import java.util.List;

/**
 * 搜索用户
 * Created by song on 2016/9/1.
 */
public interface SearchUserFragmentView {
    /**
     * 更新列表
     * @param userBeanList UserBean
     */
    void updateRecyclerView(List<UserBean> userBeanList);

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
