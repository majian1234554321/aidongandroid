package com.leyuan.aidong.ui.home.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.CampaignAdapter;
import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.ui.mvp.presenter.CampaignPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CampaignPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CampaignFragmentView;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动
 * Created by song on 2016/9/9.
 */
public class CampaignFragment extends BaseFragment implements CampaignFragmentView {
    public static final String FREE = "free";
    public static final String PAY = "pay";

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private CampaignPresent campaignPresent;

    private int currPage = 1;
    private List<CampaignBean> data;
    private CampaignAdapter campaignAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private String type;

    public static CampaignFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        CampaignFragment fragment = new CampaignFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campaign,container,false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        if (getArguments() != null) {
            type = getArguments().getString("type");
        }
        campaignPresent = new CampaignPresentImpl(getActivity(),this);
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        campaignPresent.commonLoadData(switcherLayout);
    }

    private void initSwipeRefreshLayout(View view){
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getActivity(),refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                campaignPresent.pullToRefreshData();
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_campaign);
        data = new ArrayList<>();
        campaignAdapter = new CampaignAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(campaignAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                campaignPresent.requestMoreData(recyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<CampaignBean> campaignBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(campaignBeanList);
        campaignAdapter.setData(data);
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
