package com.example.aidong.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.adapter.HomeRecycleViewAdapter;
import com.example.aidong.view.HomeHeaderView;
import com.leyuan.support.entity.HomeBean;
import com.leyuan.support.mvp.presenter.HomeFragmentPresent;
import com.leyuan.support.mvp.presenter.impl.HomeFragmentPresentImpl;
import com.leyuan.support.mvp.view.HomeFragmentView;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * @author song
 */
public class HomePageFragment extends BaseFragment implements HomeFragmentView{
    public static final int PAGE_SIZE = 20;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private int currPage = 1;
    private ArrayList<HomeBean> data = new ArrayList<>();
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private HomeRecycleViewAdapter homeAdapter;
    private HomeFragmentPresent present;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        present = new HomeFragmentPresentImpl(getContext(),this);
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
        data = new ArrayList<>();
        homeAdapter = new HomeRecycleViewAdapter(getActivity());
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(homeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recyclerView, new HomeHeaderView(getContext()));
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.sr_refresh);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                present.pullToRefreshData(recyclerView);
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                present.pullToRefreshData(recyclerView);
            }
        });
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && !data.isEmpty()) {
                present.requestMoreData(recyclerView,PAGE_SIZE,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<HomeBean> homeBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(homeBeanList);
        homeAdapter.setData(data);
        headerAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void hideEmptyView() {

    }

    @Override
    public void showRecyclerView() {

    }

    @Override
    public void hideRecyclerView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
