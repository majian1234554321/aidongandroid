package com.example.aidong.fragment.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.activity.mine.adapter.OrderAdapter;
import com.leyuan.support.entity.OrderBean;
import com.leyuan.support.mvp.presenter.OrderFragmentPresent;
import com.leyuan.support.mvp.presenter.impl.OrderFragmentPresentImpl;
import com.leyuan.support.mvp.view.OrderFragmentView;
import com.leyuan.support.widget.customview.SwitcherLayout;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 * Created by song on 2016/8/31.
 */
public class OrderFragment extends BaseFragment implements OrderFragmentView{
    public static final String ALL = "all";
    public static final String UN_PAID = "unpaid";
    public static final String SELF_DELIVERY = "self-delivery";
    public static final String EXPRESS_DELIVERY = "express-delivery";

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<OrderBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private OrderAdapter orderAdapter;

    private OrderFragmentPresent present;
    private String type;

    /**
     * 设置传递给Fragment的参数
     * @param type 订单类型
     */
    public void setArguments(String type){
        Bundle bundle=new Bundle();
        bundle.putString(type, type);
        OrderFragment.this.setArguments(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order,null);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        if (getArguments().containsKey(type)) {
            type = getArguments().getString(type);
        }
        type = ALL;
        pageSize = 20;
        present = new OrderFragmentPresentImpl(getContext(),this);
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        present.commonLoadData(switcherLayout,type);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.pullToRefreshData(type);
            }
        });

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
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
    public void updateRecyclerView(List<OrderBean> orderBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
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
}
