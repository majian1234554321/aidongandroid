package com.example.aidong.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.NurtureAdapter;
import com.example.aidong.activity.home.adapter.NurtureCategoryAdapter;
import com.leyuan.support.entity.NurtureBean;
import com.leyuan.support.mvp.presenter.NurtureActivityPresent;
import com.leyuan.support.mvp.presenter.impl.NurtureActivityPresentImpl;
import com.leyuan.support.mvp.view.NurtureActivityView;
import com.leyuan.support.widget.customview.SimpleTitleBar;
import com.leyuan.support.widget.customview.SwitcherLayout;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.leyuan.support.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品界面
 * @author song
 */
public class NurtureActivity extends BaseActivity implements NurtureActivityView{
    private SimpleTitleBar titleBar;
    private RecyclerView categoryRecyclerView;

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recommendRecyclerView;

    private int currPage = 1;
    private List<NurtureBean> nurtureList;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private NurtureAdapter nurtureAdapter;
    private NurtureActivityPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurture);
        pageSize = 20;
        present = new NurtureActivityPresentImpl(this,this);

        initTopLayout();
        initSwipeRefreshLayout();
        initRecommendRecyclerView();

        present.commonLoadData(switcherLayout);
    }

    private void initTopLayout(){
        titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        categoryRecyclerView = (RecyclerView)findViewById(R.id.rv_category);
        NurtureCategoryAdapter categoryAdapter = new NurtureCategoryAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);
        titleBar.setBackListener(new SimpleTitleBar.OnBackClickListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RecyclerViewStateUtils.resetFooterViewState(recommendRecyclerView);
                currPage = 1;
                present.pullToRefreshData();
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadData(switcherLayout);
            }
        });
    }


    private void initRecommendRecyclerView() {
        recommendRecyclerView = (RecyclerView)findViewById(R.id.rv_recommend);
        nurtureList = new ArrayList<>();
        nurtureAdapter = new NurtureAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(nurtureAdapter);
        recommendRecyclerView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter)recommendRecyclerView.getAdapter(), manager.getSpanCount()));
        recommendRecyclerView.setLayoutManager(manager);
        recommendRecyclerView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recommendRecyclerView,View.inflate(this,R.layout.header_nurture_or_equipment,null));
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (nurtureList != null && !nurtureList.isEmpty()) {
                present.requestMoreData(recommendRecyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<NurtureBean> nurtureBeanList) {
        if(refreshLayout.isRefreshing()){
            nurtureList.clear();
            refreshLayout.setRefreshing(false);
        }
        nurtureList.addAll(nurtureBeanList);
        nurtureAdapter.setData(nurtureList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recommendRecyclerView, LoadingFooter.State.TheEnd);
    }
}
