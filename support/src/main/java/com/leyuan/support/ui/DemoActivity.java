package com.leyuan.support.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.support.R;
import com.leyuan.support.adapter.DemoAdapter;
import com.leyuan.support.entity.DemoBean;
import com.leyuan.support.mvp.presenter.DemoActivityPresent;
import com.leyuan.support.mvp.presenter.impl.DemoActivityPresentImpl;
import com.leyuan.support.mvp.view.DemoActivityView;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.FixLinearLayoutManager;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;


public class DemoActivity extends AppCompatActivity implements DemoActivityView {
    public static final int PAGE_SIZE = 50;

    private int currPage = 1;
    private List<DemoBean> data;
    private DemoAdapter demoAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private DemoActivityPresent present;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView)findViewById(R.id.rv_venues);
        present = new DemoActivityPresentImpl(this);
        initRefreshLayout();
        initRecyclerView();
    }

    private void initRecyclerView() {
        data = new ArrayList<>();
        demoAdapter = new DemoAdapter();
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(demoAdapter);
        LinearLayoutManager layoutManager = new FixLinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }


    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(R.color.orange,R.color.red,R.color.black,R.color.gray);
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


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && !data.isEmpty()) {
                present.requestMoreData(recyclerView,PAGE_SIZE,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<DemoBean> demoList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(demoList);
        demoAdapter.setData(data);
        headerAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void hideEmptyView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showRecyclerView() {

    }

    @Override
    public void hideRecyclerView() {

    }
}
