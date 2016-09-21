package com.example.aidong.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.CampaignAdapter;
import com.leyuan.support.entity.CampaignBean;
import com.leyuan.support.mvp.presenter.CampaignPresent;
import com.leyuan.support.mvp.presenter.impl.CampaignActivityPresentImpl;
import com.leyuan.support.mvp.view.CampaignFragmentView;
import com.leyuan.support.widget.customview.SwitcherLayout;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 往期活动
 * Created by song on 2016/8/19.
 */
public class PastCampaignActivity extends BaseActivity implements CampaignFragmentView {

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private CampaignPresent campaignActivityPresent;

    private int currPage = 1;
    private List<CampaignBean> data;
    private CampaignAdapter campaignAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_campaign);
        pageSize = 10;
        campaignActivityPresent = new CampaignActivityPresentImpl(this,this);

        initSwipeRefreshLayout();
        initRecyclerView();
        campaignActivityPresent.commonLoadData(switcherLayout);
    }

    private void initSwipeRefreshLayout(){
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,findViewById(R.id.refreshLayout));
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                campaignActivityPresent.pullToRefreshData();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_campaign);
        data = new ArrayList<>();
        campaignAdapter = new CampaignAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(campaignAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && !data.isEmpty()) {
                campaignActivityPresent.requestMoreData(recyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<CampaignBean> campaignBeanList){
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        for(int i=0;i<10;i++){
            data.addAll(campaignBeanList);
        }
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
