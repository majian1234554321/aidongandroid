package com.example.aidong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.activity.home.CampaignActivity;
import com.example.aidong.activity.home.FoodActivity;
import com.example.aidong.activity.home.NurtureActivity;
import com.example.aidong.activity.home.adapter.HomeRecycleViewAdapter;
import com.example.aidong.activity.home.adapter.SamplePagerAdapter;
import com.leyuan.support.entity.HomeBean;
import com.leyuan.support.mvp.presenter.HomeFragmentPresent;
import com.leyuan.support.mvp.presenter.impl.HomeFragmentPresentImpl;
import com.leyuan.support.mvp.view.HomeFragmentView;
import com.leyuan.support.widget.customview.ViewPagerIndicator;
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
public class HomeFragment extends BaseFragment implements HomeFragmentView{

    private View headerView;
    private ViewPager viewPager;


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
        pageSize = 20;
        present = new HomeFragmentPresentImpl(getContext(),this);
        initHeaderView();
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
    }

    private void initHeaderView(){
        headerView = View.inflate(getContext(),R.layout.header_home,null);
        viewPager = (ViewPager) headerView.findViewById(R.id.vp_home);
        ViewPagerIndicator indicator = (ViewPagerIndicator)headerView.findViewById(R.id.vp_indicator);
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter();
        //HomeViewPagerAdapter pagerAdapter = new HomeViewPagerAdapter(imageList);
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);
        pagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        headerView.findViewById(R.id.tv_food).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FoodActivity.class);
                startActivity(intent);
            }
        });

        headerView.findViewById(R.id.tv_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CampaignActivity.class);
                startActivity(intent);
            }
        });

        headerView.findViewById(R.id.tv_nurture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NurtureActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
        data = new ArrayList<>();
        homeAdapter = new HomeRecycleViewAdapter(getActivity());
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(homeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recyclerView, headerView);
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
                present.requestMoreData(recyclerView,pageSize,currPage);
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
