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
import com.example.aidong.activity.discover.adapter.VenuesAdapter;
import com.leyuan.support.entity.VenuesBean;
import com.leyuan.support.mvp.presenter.OrderFragmentPresent;
import com.leyuan.support.mvp.view.SearchVenuesFragmentView;
import com.leyuan.support.widget.customview.SwitcherLayout;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 场馆搜索结果
 * Created by song on 2016/9/12.
 */
public class SearchVenuesFragment extends BaseFragment implements SearchVenuesFragmentView{

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<VenuesBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private VenuesAdapter venuesAdapter;

    private OrderFragmentPresent present;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_venues,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        pageSize = 20;
       // present = new OrderFragmentPresentImpl(this,this);
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
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
                //present.pullToRefreshData(type);
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //present.commonLoadData(switcherLayout,type);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_order);
        data = new ArrayList<>();
        venuesAdapter = new VenuesAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(venuesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                //present.requestMoreData(recyclerView,type,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<VenuesBean> venuesBeanList) {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
