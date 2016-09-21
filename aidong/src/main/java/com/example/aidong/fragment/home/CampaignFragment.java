package com.example.aidong.fragment.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.CampaignAdapter;
import com.leyuan.support.entity.CampaignBean;
import com.leyuan.support.mvp.presenter.CampaignPresent;
import com.leyuan.support.mvp.presenter.impl.CampaignPresentImpl;
import com.leyuan.support.mvp.view.CampaignFragmentView;
import com.leyuan.support.widget.customview.SwitcherLayout;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

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

    /**
     * 设置传递给Fragment的参数
     * @param type 活动类型
     */
    public void setArguments(String type){
        Bundle bundle=new Bundle();
        bundle.putString("type", type);
        this.setArguments(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campaign,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        if (getArguments().containsKey(type)) {
            type = getArguments().getString(type);
        }
        pageSize = 10;
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
            if (data != null && !data.isEmpty()) {
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
