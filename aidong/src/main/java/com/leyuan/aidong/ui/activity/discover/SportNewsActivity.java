package com.leyuan.aidong.ui.activity.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.NewsBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.discover.adapter.SportsNewsAdapter;
import com.leyuan.aidong.ui.mvp.presenter.DiscoverPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DiscoverPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SportNewsActivityView;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 运动之窗
 * Created by song on 2016/10/17.
 */
public class SportNewsActivity extends BaseActivity implements SportNewsActivityView{
    private SimpleTitleBar titleBar;
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rvNews;

    private int currPage = 1;
    private List<NewsBean> data;
    private SportsNewsAdapter newsAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private DiscoverPresent discoverPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_news);
        discoverPresent = new DiscoverPresentImpl(this,this);

        titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initSwipeRefreshLayout();
        initRecyclerView();

        newsAdapter.setData(null);
        wrapperAdapter.notifyDataSetChanged();

        discoverPresent.commonLoadNewsData(switcherLayout);
    }

    private void initSwipeRefreshLayout(){
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                discoverPresent.pullToRefreshNewsData();
            }
        });
    }

    private void initRecyclerView(){
        rvNews = (RecyclerView)findViewById(R.id.rv_news);
        data = new ArrayList<>();
        newsAdapter = new SportsNewsAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(newsAdapter);
        rvNews.setAdapter(wrapperAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        rvNews.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                discoverPresent.requestMoreNewsData(rvNews,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<NewsBean> newsBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(newsBeanList);
        newsAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(rvNews, LoadingFooter.State.TheEnd);
    }
}
