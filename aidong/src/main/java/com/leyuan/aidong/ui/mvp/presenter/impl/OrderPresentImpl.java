package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.OrderBean;
import com.leyuan.aidong.entity.OrderDetailBean;
import com.leyuan.aidong.entity.data.OrderData;
import com.leyuan.aidong.entity.data.OrderDetailData;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.OrderModel;
import com.leyuan.aidong.ui.mvp.model.impl.OrderModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.OrderPresent;
import com.leyuan.aidong.ui.mvp.view.OrderDetailActivityView;
import com.leyuan.aidong.ui.mvp.view.OrderFeedbackView;
import com.leyuan.aidong.ui.mvp.view.OrderFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

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

    private OrderFeedbackView orderFeedbackView;

    public OrderPresentImpl(Context context, OrderFeedbackView view) {
        this.context = context;
        this.orderFeedbackView = view;
        if (orderModel == null) {
            orderModel = new OrderModelImpl();
        }
    }

    public OrderPresentImpl(Context context, OrderFragmentView view) {
        this.context = context;
        this.orderFragmentView = view;
        orderBeanList = new ArrayList<>();
        if (orderModel == null) {
            orderModel = new OrderModelImpl();
        }
    }

    public OrderPresentImpl(Context context, OrderDetailActivityView view) {
        this.context = context;
        this.orderDetailActivityView = view;
        if (orderModel == null) {
            orderModel = new OrderModelImpl();
        }
    }

    @Override
    public void commonLoadData(final String list) {
        orderModel.getOrders(new BaseSubscriber<OrderData>(context) {
            @Override
            public void onNext(OrderData orderData) {
                if (orderData != null && orderData.getOrder() != null) {
                    orderBeanList = orderData.getOrder();
                }

                if (orderBeanList.isEmpty()) {
                    orderFragmentView.showEmptyView();
                } else {
                    orderFragmentView.onRecyclerViewRefresh(orderBeanList);
                }
            }
        }, list, Constant.PAGE_FIRST);
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout, String list) {
        orderModel.getOrders(new CommonSubscriber<OrderData>(switcherLayout) {
            @Override
            public void onNext(OrderData orderData) {
                if (orderData != null && orderData.getOrder() != null) {
                    orderBeanList = orderData.getOrder();
                }
                if (orderBeanList.isEmpty()) {
                    orderFragmentView.showEmptyView();
                } else {
                    switcherLayout.showContentLayout();
                    orderFragmentView.onRecyclerViewRefresh(orderBeanList);
                }
            }
        }, list, Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshData(String list) {
        orderModel.getOrders(new RefreshSubscriber<OrderData>(context) {
            @Override
            public void onNext(OrderData orderData) {
                if (orderData != null && orderData.getOrder() != null) {
                    orderFragmentView.onRecyclerViewRefresh(orderData.getOrder());
                }
            }
        }, list, Constant.PAGE_FIRST);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, String list, final int pageSize, int page) {
        orderModel.getOrders(new RequestMoreSubscriber<OrderData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(OrderData orderData) {
                if (orderData != null && orderData.getOrder() != null) {
                    orderBeanList = orderData.getOrder();
                }
                if (!orderBeanList.isEmpty()) {
                    orderFragmentView.onRecyclerViewLoadMore(orderBeanList);
                }
                //没有更多数据了显示到底提示
                if (orderBeanList.size() < pageSize) {
                    orderFragmentView.showEndFooterView();
                }
            }
        }, list, page);
    }


    @Override
    public void getOrderDetail(String id) {
        orderModel.getOrderDetail(new BaseSubscriber<OrderDetailData>(context) {
            @Override
            public void onNext(OrderDetailData orderDetailData) {
                OrderDetailBean orderDetailBean = null;
                if (orderDetailData != null && orderDetailData.getOrder() != null) {
                    orderDetailBean = orderDetailData.getOrder();
                }
                if (orderDetailBean != null) {
                    orderDetailActivityView.setOrderDetail(orderDetailBean);
                }
            }
        },id);
    }

    @Override
    public void getOrderDetail(final SwitcherLayout switcherLayout,String id) {
        orderModel.getOrderDetail(new CommonSubscriber<OrderDetailData>(switcherLayout) {
            @Override
            public void onNext(OrderDetailData orderDetailData) {
                OrderDetailBean orderDetailBean = null;
                if (orderDetailData != null && orderDetailData.getOrder() != null) {
                    orderDetailBean = orderDetailData.getOrder();
                }
                if (orderDetailBean != null) {
                    switcherLayout.showContentLayout();
                    orderDetailActivityView.setOrderDetail(orderDetailBean);
                } else {
                    switcherLayout.showEmptyLayout();
                }
            }
        }, id);
    }

    @Override
    public void cancelOrder(String id) {
        orderModel.cancelOrder(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if(orderFragmentView != null) {
                    orderFragmentView.cancelOrderResult(baseBean);
                }
                if(orderDetailActivityView != null) {
                    orderDetailActivityView.cancelOrderResult(baseBean);
                }
            }
        }, id);
    }

    @Override
    public void confirmOrder(String id) {
        orderModel.confirmOrder(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if(orderFragmentView != null) {
                    orderFragmentView.confirmOrderResult(baseBean);
                }
                if(orderDetailActivityView != null) {
                    orderDetailActivityView.confirmOrderResult(baseBean);
                }
            }
        }, id);
    }

    @Override
    public void deleteOrder(String id) {
        orderModel.deleteOrder(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if(orderFragmentView != null) {
                    orderFragmentView.deleteOrderResult(baseBean);
                }
                if(orderDetailActivityView != null) {
                    orderDetailActivityView.deleteOrderResult(baseBean);
                }
            }
        }, id);
    }

    @Override
    public void feedbackOrder(String id, String type, String[] items, String content, String[] image, String address) {
        orderModel.feedbackOrder(new BaseSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (orderFeedbackView != null) {
                    orderFeedbackView.onFeedbackResult(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (orderFeedbackView != null) {
                    orderFeedbackView.onFeedbackResult(false);
                }
            }
        }, id, type, items, content, image, address);
    }
}
