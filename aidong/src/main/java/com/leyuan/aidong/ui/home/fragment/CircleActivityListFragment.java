package com.leyuan.aidong.ui.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.R;
import com.leyuan.aidong.adapter.home.CircleActivityListAdapter;
import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.mvp.presenter.CampaignPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CampaignPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CampaignFragmentView;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/5.
 */
public class CircleActivityListFragment extends BaseFragment implements CampaignFragmentView,OnRefreshListener {

    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private SwitcherLayout switcherLayout;
    private int currPage;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private CircleActivityListAdapter campaignAdapter;
    private CampaignPresent campaignPresent;
    private List<CampaignBean> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_circle_activity_list,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        campaignPresent = new CampaignPresentImpl(getActivity(),this);
        initSwipeRefreshLayout(view);
        initRecyclerView(view);

        initSwitcherLayout();
        campaignPresent.commonLoadData(switcherLayout,null);

    }


    private void initSwipeRefreshLayout(View view) {

        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);

    }

    private void initSwitcherLayout() {

        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_order);
        campaignAdapter = new CircleActivityListAdapter(getActivity());

        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(campaignAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                campaignPresent.requestMoreData(recyclerView,pageSize,currPage,null);
            }
        }
    };

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        campaignPresent.pullToRefreshData(null);
    }

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
