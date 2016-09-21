package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.OrderBean;

import java.util.List;

/**
 * 订单列表
 * Created by song on 2016/9/1.
 */
public interface OrderFragmentView {
    /**
     * 更新列表
     * @param orderBeanList OrderBean
     */
    void updateRecyclerView(List<OrderBean> orderBeanList);

    /**
     * 显示空值界面布局
     */
    void showEmptyView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
