package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.OrderBean;

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

    /**
     * 取消订单
     * @param baseBean
     */
    void cancelOrderResult(BaseBean baseBean);

    /**
     * 确认订单
     * @param baseBean
     */
    void confirmOrderResult(BaseBean baseBean);

    /**
     * 删除订单
     * @param baseBean
     */
    void deleteOrderResult(BaseBean baseBean);
}
