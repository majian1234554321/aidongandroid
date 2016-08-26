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
import com.leyuan.support.mvp.presenter.CampaignActivityPresent;
import com.leyuan.support.mvp.presenter.impl.CampaignActivityPresentImpl;
import com.leyuan.support.mvp.view.CampaignActivityView;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动
 * Created by song on 2016/8/19.
 */
public class CampaignActivity extends BaseActivity implements CampaignActivityView {


    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private CampaignActivityPresent campaignActivityPresent;

    private int currPage = 1;
    private List<CampaignBean> data;
    private CampaignAdapter campaignAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
        pageSize = 10;
        campaignActivityPresent = new CampaignActivityPresentImpl(this,this);


        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void initSwipeRefreshLayout(){
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                campaignActivityPresent.pullToRefreshData(recyclerView);
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                campaignActivityPresent.pullToRefreshData(recyclerView);
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
    public void showErrorView() {

    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
