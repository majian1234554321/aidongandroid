package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CartIdBean;
import com.example.aidong .entity.ExpressBean;
import com.example.aidong .entity.OrderBean;
import com.example.aidong .entity.OrderDetailBean;
import com.example.aidong .entity.OrderDetailExpressBean;
import com.example.aidong .entity.ShareData;
import com.example.aidong .entity.data.OrderData;
import com.example.aidong .entity.data.OrderDetailData;
import com.example.aidong .http.subscriber.BaseSubscriber;
import com.example.aidong .http.subscriber.CommonSubscriber;
import com.example.aidong .http.subscriber.IsLoginSubscriber;
import com.example.aidong.http.subscriber.Progress2Subscriber;
import com.example.aidong .http.subscriber.ProgressSubscriber;
import com.example.aidong .http.subscriber.RefreshSubscriber;
import com.example.aidong .http.subscriber.RequestMoreSubscriber;
import com.example.aidong .ui.mvp.model.OrderModel;
import com.example.aidong .ui.mvp.model.impl.OrderModelImpl;
import com.example.aidong .ui.mvp.presenter.OrderPresent;
import com.example.aidong .ui.mvp.view.ExpressInfoActivityView;
import com.example.aidong .ui.mvp.view.OrderDetailActivityView;
import com.example.aidong .ui.mvp.view.OrderFeedbackView;
import com.example.aidong .ui.mvp.view.OrderFragmentView;
import com.example.aidong .utils.Constant;
import com.example.aidong .widget.SwitcherLayout;

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
    private ExpressInfoActivityView expressInfoActivityView;

    private OrderFeedbackView orderFeedbackView;
    private ShareData.ShareCouponInfo shareInfo = new ShareData().new ShareCouponInfo();

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

    public OrderPresentImpl(Context context, ExpressInfoActivityView view) {
        this.context = context;
        this.expressInfoActivityView = view;
        if (orderModel == null) {
            orderModel = new OrderModelImpl();
        }
    }

    @Override
    public void commonLoadData(final String list) {
        orderModel.getOrders(new IsLoginSubscriber<OrderData>(context) {
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
        orderModel.getOrders(new CommonSubscriber<OrderData>(context, switcherLayout) {
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
        orderModel.getOrderDetail(new IsLoginSubscriber<OrderDetailData>(context) {
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
        }, id);
    }

    @Override
    public void getOrderDetail(final SwitcherLayout switcherLayout, String id) {
        orderModel.getOrderDetail(new CommonSubscriber<OrderDetailData>(context, switcherLayout) {
            @Override
            public void onNext(OrderDetailData orderDetailData) {
                OrderDetailBean orderDetailBean = null;
                if (orderDetailData != null && orderDetailData.getOrder() != null) {
                    orderDetailBean = orderDetailData.getOrder();
                }
                if (orderDetailBean != null) {
                    switcherLayout.showContentLayout();
                    orderDetailActivityView.setOrderDetail(orderDetailBean);
                    shareInfo.setNo(orderDetailBean.getId());
                    shareInfo.setCreatedAt(orderDetailBean.getCreatedAt());
                } else {
                    switcherLayout.showEmptyLayout();
                }
            }
        }, id);
    }

    @Override
    public void cancelOrder(String id) {
        orderModel.cancelOrder(new Progress2Subscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (orderFragmentView != null) {
                    orderFragmentView.cancelOrderResult(baseBean);
                }
                if (orderDetailActivityView != null) {
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
                if (orderFragmentView != null) {
                    orderFragmentView.confirmOrderResult(baseBean);
                }
                if (orderDetailActivityView != null) {
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
                if (orderFragmentView != null) {
                    orderFragmentView.deleteOrderResult(baseBean);
                }
                if (orderDetailActivityView != null) {
                    orderDetailActivityView.deleteOrderResult(baseBean);
                }
            }
        }, id);
    }

    @Override
    public void feedbackOrder(String id, String type, String[] items, String content, String[] image, String address) {
        orderModel.feedbackOrder(new IsLoginSubscriber<BaseBean>(context) {
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

    @Override
    public void reBuyOrder(String orderId) {
        orderModel.reBuyOrder(new Progress2Subscriber<CartIdBean>(context) {
            @Override
            public void onNext(CartIdBean cartIdBean) {
                if (orderDetailActivityView != null) {
                    orderDetailActivityView.reBuyOrderResult(cartIdBean.getCart_ids());
                }
                if (orderFragmentView != null) {
                    orderFragmentView.reBuyOrderResult(cartIdBean.getCart_ids());
                }
            }
        }, orderId);
    }

    @Override
    public void getExpressInfo(String orderId) {
        orderModel.getExpressInfo(new BaseSubscriber<ExpressBean>(context) {

            @Override
            public void onStart() {
                super.onStart();
                expressInfoActivityView.showLoadingView();
            }

            @Override
            public void onNext(ExpressBean expressBean) {
                if (expressBean.express != null && expressBean.express.result != null && expressBean.express.result.list != null) {
                    expressInfoActivityView.hideLoadingView();
                    expressInfoActivityView.updateExpressInfo(expressBean.cover,
                            expressBean.express_name, expressBean.express.result);
                }else {
                    expressInfoActivityView.showEmptyView();
                }
            }
        }, orderId);
    }

    @Override
    public void getOrderDetailExpressInfo(String orderId) {
        orderModel.getOrderDetailExpressInfo(new BaseSubscriber<OrderDetailExpressBean>(context) {
            @Override
            public void onNext(OrderDetailExpressBean expressBean) {
                if (orderDetailActivityView != null) {
                    orderDetailActivityView.getExpressInfoResult(expressBean.getExpress().getStatus(),
                            expressBean.getExpress().getTime());
                }
            }
        }, orderId);
    }

    @Override
    public ShareData.ShareCouponInfo getShareInfo() {
        return shareInfo;
    }
}
