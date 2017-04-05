package com.leyuan.aidong.ui.mine.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.OrderAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.OrderBean;
import com.leyuan.aidong.ui.BaseLazyFragment;
import com.leyuan.aidong.ui.mine.activity.OrderDetailActivity;
import com.leyuan.aidong.ui.mvp.presenter.OrderPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.OrderPresentImpl;
import com.leyuan.aidong.ui.mvp.view.OrderFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 * Created by song on 2016/8/31.
 */
public class OrderFragment extends BaseLazyFragment implements OrderFragmentView{
    public static final String ALL = "all";
    public static final String UN_PAID = "pending";
    public static final String PAID = "purchased";
    public static final String FINISH = "confirmed";
    private String type;

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<OrderBean> data;
    private OrderAdapter orderAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private OrderPresent present;

    public static OrderFragment newInstance(String type){
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initView() {
        present = new OrderPresentImpl(getContext(),this);
        Bundle bundle = getArguments();
        if(bundle != null){
            type = bundle.getString("type");
        }
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_order,null);
        initSwipeRefreshLayout(view);
        initSwitcherLayout();
        initRecyclerView(view);
        return view;
    }

    @Override
    public void fetchData() {
        present.commonLoadData(switcherLayout,type);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.pullToRefreshData(type);
            }
        });
    }

    private void initSwitcherLayout(){
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadData(switcherLayout,type);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_order);
        data = new ArrayList<>();
        orderAdapter = new OrderAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(orderAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        orderAdapter.setOrderListener(new OrderCallback());
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                present.requestMoreData(recyclerView,type,pageSize,currPage);
            }
        }
    };

    @Override
    public void onRecyclerViewRefresh(List<OrderBean> orderBeanList) {
        switcherLayout.showContentLayout();
        data.clear();
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        data.addAll(orderBeanList);
        orderAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecyclerViewLoadMore(List<OrderBean> orderBeanList) {
        data.addAll(orderBeanList);
        orderAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }


    @Override
    public void showEmptyView() {
        switcherLayout.showEmptyLayout();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    private class OrderCallback implements OrderAdapter.OrderListener{

        @Override
        public void onPayOrder(String id) {
            OrderDetailActivity.start(getContext(),id);
        }

        @Override
        public void onCancelOrder(String id) {
            present.cancelOrder(id);
        }

        @Override
        public void onDeleteOrder(String id) {
            present.deleteOrder(id);
        }

        @Override
        public void onConfirmOrder(String id) {
            present.cancelOrder(id);
        }


        @Override
        public void onBuyAgain() {

        }

        @Override
        public void onRefreshOrderStatus() {
            present.commonLoadData(type);
        }
    }


    @Override
    public void cancelOrderResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            present.commonLoadData(type);
            Toast.makeText(getContext(),"取消成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),"取消失败" + baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void confirmOrderResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            present.commonLoadData(type);
            Toast.makeText(getContext(),"确认成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),"确认失败" + baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void deleteOrderResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            present.commonLoadData(type);
            Toast.makeText(getContext(),"删除成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),"删除失败" + baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
