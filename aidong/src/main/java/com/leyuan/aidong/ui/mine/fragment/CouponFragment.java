package com.leyuan.aidong.ui.mine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.mine.activity.CouponExchangeActivity;
import com.leyuan.aidong.adapter.mine.CouponAdapter;
import com.leyuan.aidong.ui.mvp.presenter.CouponPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CouponPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CouponFragmentView;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠劵
 * Created by song on 2016/8/31.
 */
public class CouponFragment extends BaseFragment implements CouponFragmentView{
    public static final String VALID = "valid";
    public static final String USED = "used";
    public static final String EXPIRED = "expired";
    private String type;

    private View headerView;
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private TextView tvExchange;

    private int currPage = 1;
    private List<CouponBean> data;
    private CouponAdapter couponAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private CouponPresent present;

    public static CouponFragment newInstance(String type){
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        CouponFragment fragment = new CouponFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        present = new CouponPresentImpl(getContext(),this);
        Bundle bundle = getArguments();
        if(bundle != null){
            type = bundle.getString("type");
        }
        return inflater.inflate(R.layout.fragment_coupon,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
       // initHeaderView();
        initSwipeRefreshLayout(view);
        initSwitcherLayout();
        initRecyclerView(view);
        present.commonLoadData(switcherLayout,type);
        tvExchange = (TextView) view.findViewById(R.id.tv_exchange);
        tvExchange.setVisibility(VALID.equals(type)?View.VISIBLE:View.GONE);
        tvExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CouponExchangeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initHeaderView(){
        headerView = View.inflate(getContext(),R.layout.header_coupon,null);
        headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CouponExchangeActivity.class);
                startActivity(intent);
            }
        });
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
        switcherLayout = new SwitcherLayout(getContext(),refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadData(switcherLayout,type);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_coupon);
        data = new ArrayList<>();
        couponAdapter = new CouponAdapter(getContext(),type);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(couponAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);

        //RecyclerViewUtils.setHeaderView(recyclerView,headerView);
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
    public void updateRecyclerView(List<CouponBean> couponBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(couponBeanList);
        couponAdapter.setData(data);
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
