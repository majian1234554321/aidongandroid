package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.OrderBean;
import com.leyuan.support.entity.data.OrderData;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.OrderModel;
import com.leyuan.support.mvp.model.impl.OrderModelImpl;
import com.leyuan.support.mvp.presenter.OrderFragmentPresent;
import com.leyuan.support.mvp.view.OrderFragmentView;
import com.leyuan.support.util.Constant;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 * Created by song on 2016/9/1.
 */
public class OrderFragmentPresentImpl implements OrderFragmentPresent{
    private Context context;
    private OrderModel orderModel;
    private OrderFragmentView orderFragmentView;
    List<OrderBean> orderBeanList = new ArrayList<>();

    public OrderFragmentPresentImpl(Context context, OrderFragmentView orderFragmentView) {
        this.context = context;
        this.orderFragmentView = orderFragmentView;
        orderModel = new OrderModelImpl();
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout,String list) {
        orderModel.getOrders(new CommonSubscriber<OrderData>(switcherLayout) {
            @Override
            public void onNext(OrderData orderData) {
                if(orderData != null){
                    orderBeanList = orderData.getOrder();
                }
                if(orderBeanList.isEmpty()){
                    orderFragmentView.showEmptyView();
                }else{
                    switcherLayout.showContentLayout();
                    orderFragmentView.updateRecyclerView(orderBeanList);
                }
            }
        },list,Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData(String list) {
        orderModel.getOrders(new RefreshSubscriber<OrderData>(context) {
            @Override
            public void onNext(OrderData orderData) {
                if(orderData != null && !orderData.getOrder().isEmpty()){
                    orderFragmentView.updateRecyclerView(orderData.getOrder());
                }
            }
        },list, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, String list, final int pageSize, int page) {
        orderModel.getOrders(new RequestMoreSubscriber<OrderData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(OrderData orderData) {
                if(orderData != null){
                   orderBeanList = orderData.getOrder();
                }

                if(!orderBeanList.isEmpty()){
                    orderFragmentView.updateRecyclerView(orderBeanList);
                }

                //没有更多数据了显示到底提示
                if(orderBeanList.size() < pageSize){
                    orderFragmentView.showEndFooterView();
                }
            }
        },list,page);
    }
}
