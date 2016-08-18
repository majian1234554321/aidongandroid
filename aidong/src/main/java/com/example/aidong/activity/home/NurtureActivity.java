package com.example.aidong.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品界面
 * @author song
 */
public class NurtureActivity extends BaseActivity implements NurtureActivityView{
    private SimpleTitleBar titleBar;
    private RecyclerView categoryRecyclerView;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recommendRecyclerView;

    private int currPage = 1;
    private List<NurtureBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapAdapter;
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
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                present.pullToRefreshData(recommendRecyclerView);
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                present.pullToRefreshData(recommendRecyclerView);
            }
        });
    }


    private void initRecommendRecyclerView() {
        recommendRecyclerView = (RecyclerView)findViewById(R.id.rv_food);
        data = new ArrayList<>();
        nurtureAdapter = new NurtureAdapter();
        wrapAdapter = new HeaderAndFooterRecyclerViewAdapter(nurtureAdapter);
        recommendRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recommendRecyclerView.setAdapter(wrapAdapter);
        recommendRecyclerView.addOnScrollListener(onScrollListener);
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && !data.isEmpty()) {
                present.requestMoreData(recommendRecyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<com.leyuan.support.entity.NurtureBean> nurtureBeanList) {

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

    }
}
