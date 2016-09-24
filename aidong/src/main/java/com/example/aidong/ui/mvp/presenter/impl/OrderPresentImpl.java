package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong.entity.OrderBean;
import com.example.aidong.entity.OrderDetailBean;
import com.example.aidong.entity.data.OrderData;
import com.example.aidong.entity.data.OrderDetailData;
import com.example.aidong.http.subscriber.CommonSubscriber;
import com.example.aidong.http.subscriber.RefreshSubscriber;
import com.example.aidong.http.subscriber.RequestMoreSubscriber;
import com.example.aidong.ui.mvp.model.OrderModel;
import com.example.aidong.ui.mvp.model.impl.OrderModelImpl;
import com.example.aidong.ui.mvp.presenter.OrderPresent;
import com.example.aidong.ui.mvp.view.OrderDetailActivityView;
import com.example.aidong.ui.mvp.view.OrderFragmentView;
import com.example.aidong.utils.Constant;
import com.example.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 * Created by song on 2016/9/1.
 */
public class OrderPresentImpl implements OrderPresent {
    private Context context;
    private OrderModel orderModel;

    //订单列表View层对象
    private OrderFragmentView orderFragmentView;
    private List<OrderBean> orderBeanList;

    //订单详情View层对象
    private OrderDetailActivityView orderDetailActivityView;

    public OrderPresentImpl(Context context, OrderFragmentView view) {
        this.context = context;
        this.orderFragmentView = view;
        orderBeanList = new ArrayList<>();
        if(orderModel == null){
            orderModel = new OrderModelImpl();
        }
    }

    public OrderPresentImpl(Context context, OrderDetailActivityView view) {
        this.context = context;
        this.orderDetailActivityView = view;
        if(orderModel == null){
            orderModel = new OrderModelImpl();
        }
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

    @Override
    public void getOrderDetail(String id, final SwitcherLayout switcherLayout) {
        orderModel.getOrderDetail(new CommonSubscriber<OrderDetailData>(switcherLayout) {
            @Override
            public void onNext(OrderDetailData orderDetailData) {
                OrderDetailBean orderDetailBean = null;
                if(orderDetailData != null ){
                    orderDetailBean = orderDetailData.getItem();
                }
                if(orderDetailBean != null){
                    switcherLayout.showContentLayout();
                    orderDetailActivityView.setOrderDetail(orderDetailBean);
                }else {
                    switcherLayout.showEmptyLayout();
                }
            }
        },id);
    }
}
