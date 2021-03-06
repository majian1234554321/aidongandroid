package com.example.aidong.ui.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.R;
import com.example.aidong .adapter.home.CampaignAdapter;
import com.example.aidong .entity.CampaignBean;
import com.example.aidong .ui.BaseFragment;
import com.example.aidong .ui.mvp.presenter.CampaignPresent;
import com.example.aidong .ui.mvp.presenter.impl.CampaignPresentImpl;
import com.example.aidong .ui.mvp.view.CampaignFragmentView;
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
 * 活动
 * Created by song on 2016/9/9.
 */
public class CampaignFragment extends BaseFragment implements CampaignFragmentView, OnRefreshListener {
    public static final String FREE = "free";
    public static final String PAY = "need_to_pay";

    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
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
        campaignPresent.commonLoadData(switcherLayout,type);
    }

    private void initSwipeRefreshLayout(View view){
        refreshLayout = (CustomRefreshLayout)view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getActivity(),refreshLayout);
        refreshLayout.setOnRefreshListener(this);
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

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        campaignPresent.pullToRefreshData(type);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                campaignPresent.requestMoreData(recyclerView,pageSize,currPage,type);
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
        View view = View.inflate(getContext(),R.layout.empty_campaign,null);
        CustomRefreshLayout refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout_empty);
        refreshLayout.setOnRefreshListener(this);
        switcherLayout.addCustomView(view,"empty");
        switcherLayout.showCustomLayout("empty");
    }


    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
