package com.leyuan.aidong.ui.fragment.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.home.adapter.CampaignAdapter;
import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.ui.mvp.presenter.SearchPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.SearchPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SearchCampaignFragmentView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动搜索结果
 * Created by song on 2016/9/12.
 */
public class SearchCampaignFragment extends BaseFragment implements SearchCampaignFragmentView {
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<CampaignBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private CampaignAdapter campaignAdapter;

    private SearchPresent present;
    private String keyword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            keyword = bundle.getString("keyword");
        }
        pageSize = 20;
        present = new SearchPresentImpl(getContext(),this);
        return inflater.inflate(R.layout.fragment_result,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        present.commonLoadCampaignData(switcherLayout,keyword);
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
                present.pullToRefreshCampaignData(keyword);
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadCampaignData(switcherLayout,keyword);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_result);
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
                present.requestMoreCampaignData(recyclerView,keyword,pageSize,currPage);
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
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
