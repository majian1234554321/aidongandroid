package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.UserBean;

import java.util.List;

/**
 * 关注
 * Created by song on 2016/8/19.
 */
public interface FollowFragmentView {
    /**
     * 更新列表
     * @param userBeanList
     */
    void updateRecyclerView(List<UserBean> userBeanList);

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();


}
