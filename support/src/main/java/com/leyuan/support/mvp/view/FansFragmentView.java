package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.UserBean;

import java.util.List;

/**
 * 粉丝
 * Created by song on 2016/8/19.
 */
public interface FansFragmentView {

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
