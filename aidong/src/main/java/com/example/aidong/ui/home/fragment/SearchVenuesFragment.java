package com.example.aidong.ui.home.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.R;
import com.example.aidong .adapter.discover.VenuesAdapter;
import com.example.aidong .entity.VenuesBean;
import com.example.aidong .ui.BasePageFragment;
import com.example.aidong .ui.mvp.presenter.SearchPresent;
import com.example.aidong .ui.mvp.presenter.impl.SearchPresentImpl;
import com.example.aidong .ui.mvp.view.SearchVenuesFragmentView;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 场馆搜索结果
 * Created by song on 2016/9/12.
 */
public class SearchVenuesFragment extends BasePageFragment implements SearchVenuesFragmentView {

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<VenuesBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private VenuesAdapter venuesAdapter;

    private SearchPresent present;
    private String  keyword ;
    private boolean needLoad = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        present = new SearchPresentImpl(getContext(),this);
        Bundle bundle = getArguments();
        if(bundle != null){
            keyword = bundle.getString("keyword");
            needLoad = bundle.getBoolean("needLoad");
        }
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        return view;
    }

    @Override
    public void fetchData() {
        if(needLoad) {
            present.commonLoadVenuesData(switcherLayout, keyword);
        }
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        //setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.pullToRefreshVenuesData(keyword);
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadVenuesData(switcherLayout,keyword);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_result);
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
                present.requestMoreVenuesData(recyclerView,keyword,pageSize,currPage);
            }
        }
    };

    @Override
    public void onRecyclerViewRefresh(List<VenuesBean> venuesBeanList) {
        data.clear();
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        data.addAll(venuesBeanList);
        venuesAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecyclerViewLoadMore(List<VenuesBean> venuesBeanList) {
        data.addAll(venuesBeanList);
        venuesAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(getContext(),R.layout.empty_search,null);
        switcherLayout.addCustomView(view,"empty");
        switcherLayout.showCustomLayout("empty");
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    public void refreshData(String keyword){
        data.clear();
        this.keyword = keyword;
        present.commonLoadVenuesData(switcherLayout,keyword);
    }
}
