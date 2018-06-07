package com.example.aidong.ui.mine.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.mine.CouponAdapter;
import com.example.aidong .entity.CouponBean;
import com.example.aidong .ui.BaseFragment;
import com.example.aidong .ui.mine.activity.CouponExchangeActivity;
import com.example.aidong .ui.mvp.presenter.CouponPresent;
import com.example.aidong .ui.mvp.presenter.impl.CouponPresentImpl;
import com.example.aidong .ui.mvp.view.CouponFragmentView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.Logger;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠劵
 * Created by song on 2016/8/31.
 */
public class CouponFragment extends BaseFragment implements CouponFragmentView {
    public static final String VALID = "valid";
    public static final String USED = "used";
    public static final String EXPIRED = "expired";
    private String type;

    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private TextView tvExchange;

    private int currPage = 1;
    private List<CouponBean> data;
    private CouponAdapter couponAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private CouponPresentImpl present;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshLayout.setRefreshing(true);
            currPage = 1;
            RecyclerViewStateUtils.resetFooterViewState(recyclerView);
            present.pullToRefreshData(type);
        }
    };

    public static CouponFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        CouponFragment fragment = new CouponFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        present = new CouponPresentImpl(getContext(), this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        }
        if (VALID.equals(type)) {
            IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_EXCHANGE_COUPON_SUCCESS);
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, filter);
        }

        return inflater.inflate(R.layout.fragment_coupon, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initSwipeRefreshLayout(view);
        initSwitcherLayout();
        initRecyclerView(view);
        present.commonLoadData(switcherLayout, type);
        tvExchange = (TextView) view.findViewById(R.id.tv_exchange);
        tvExchange.setVisibility(VALID.equals(type) ? View.VISIBLE : View.GONE);
        tvExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CouponExchangeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.pullToRefreshData(type);
            }
        });
    }

    private void initSwitcherLayout() {
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadData(switcherLayout, type);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_coupon);
        data = new ArrayList<>();
        couponAdapter = new CouponAdapter(getContext(), type);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(couponAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(couponAdapter);
//        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                present.requestMoreData(recyclerView, type, pageSize, currPage);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void updateRecyclerView(List<CouponBean> couponBeanList) {
        Logger.i("coupon", "updateRecyclerView couponBeanList = " + couponBeanList.size());
        switcherLayout.showContentLayout();
        if (refreshLayout.isRefreshing()) {
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(couponBeanList);

        Logger.i("coupon", "updateRecyclerView data = " + data.size());
        couponAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(getContext(), R.layout.empty_coupon, null);
        switcherLayout.addCustomView(view, "empty");
        switcherLayout.showCustomLayout("empty");
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (VALID.equals(type)) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        }

    }
}
