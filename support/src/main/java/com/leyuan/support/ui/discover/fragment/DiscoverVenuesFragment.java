package com.leyuan.support.ui.discover.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.support.R;
import com.leyuan.support.adapter.DiscoverVenuesAdapter;
import com.leyuan.support.entity.VenuesBean;
import com.leyuan.support.mvp.presenter.DiscoverVenuesFragmentPresent;
import com.leyuan.support.mvp.presenter.impl.DiscoverVenuesFragmentPresentImpl;
import com.leyuan.support.mvp.view.DiscoverVenuesFragmentView;
import com.leyuan.support.ui.BaseFragment;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现-场馆
 * Created by Song on 2016/8/2.
 */
public class DiscoverVenuesFragment extends BaseFragment implements DiscoverVenuesFragmentView {

    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private DiscoverVenuesFragmentPresent venuesFragmentPresent;
    private DiscoverVenuesAdapter venuesAdapter;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private List<VenuesBean> data;
    private int currPage = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        venuesFragmentPresent = new DiscoverVenuesFragmentPresentImpl(getContext(),this);
        view =  inflater.inflate(R.layout.fragment_discover_venues,null);
        initRefreshLayout();
        initRecyclerView();
        return view;
    }


    private void initRecyclerView() {
        data = new ArrayList<>();
        venuesAdapter = new DiscoverVenuesAdapter();
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(venuesAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_venues);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
       // RecyclerViewUtils.setHeaderView(mRecyclerView, new HomeHeadView(mContext));
    }


    private void initRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.clear();
                venuesFragmentPresent.pullToRefreshData(recyclerView);
            }
        });
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && !data.isEmpty()) {
                venuesFragmentPresent.requestMoreData(recyclerView,20,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<VenuesBean> venuesList) {
        recyclerView.addOnScrollListener(onScrollListener);
        data.addAll(venuesList);
        venuesAdapter.setData(data);
        headerAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }


}
