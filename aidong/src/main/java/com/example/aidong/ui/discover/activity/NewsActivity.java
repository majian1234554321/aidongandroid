package com.example.aidong.ui.discover.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aidong.R;
import com.example.aidong .adapter.discover.NewsAdapter;
import com.example.aidong .entity.NewsBean;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mvp.presenter.DiscoverPresent;
import com.example.aidong .ui.mvp.presenter.impl.DiscoverPresentImpl;
import com.example.aidong .ui.mvp.view.SportNewsActivityView;
import com.example.aidong .widget.SimpleTitleBar;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 运动之窗资讯列表
 * Created by song on 2016/10/17.
 */
public class NewsActivity extends BaseActivity implements SportNewsActivityView{
    private SimpleTitleBar titleBar;
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView rvNews;

    private int currPage = 1;
    private List<NewsBean> data;
    private NewsAdapter newsAdapter;
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
        discoverPresent.commonLoadNewsData(switcherLayout);
    }

    private void initSwipeRefreshLayout(){
        refreshLayout = (CustomRefreshLayout)findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                discoverPresent.pullToRefreshNewsData();
            }
        });
    }

    private void initRecyclerView(){
        rvNews = (RecyclerView)findViewById(R.id.rv_news);
        data = new ArrayList<>();
        newsAdapter = new NewsAdapter(this);
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
