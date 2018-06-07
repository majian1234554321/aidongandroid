package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.OrderBean;

import java.util.List;

/**
 * 订单列表
 * Created by song on 2016/9/1.
 */
public interface OrderFragmentView {

    /**
     * 刷新回调
     * @param orderBeanList
     */
    void onRecyclerViewRefresh(List<OrderBean> orderBeanList);

    /**
     * 加载更多回调
     * @param orderBeanList
     */
    void onRecyclerViewLoadMore(List<OrderBean> orderBeanList);

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

    /**
     * 再次购买
     * @param cartIds
     */
    void reBuyOrderResult(List<String> cartIds);
}
