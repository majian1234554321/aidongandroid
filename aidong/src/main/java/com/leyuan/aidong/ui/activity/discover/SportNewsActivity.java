package com.leyuan.aidong.ui.activity.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.NewsBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.discover.adapter.SportsNewsAdapter;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 运动之窗
 * Created by song on 2016/10/17.
 */
public class SportNewsActivity extends BaseActivity{
    private SimpleTitleBar titleBar;
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rvNews;

    private List<NewsBean> date;
    private SportsNewsAdapter newsAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_sport_news);

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
    }

    private void initSwipeRefreshLayout(){
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    private void initRecyclerView(){
        rvNews = (RecyclerView)findViewById(R.id.rv_news);
        date = new ArrayList<>();
        newsAdapter = new SportsNewsAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(newsAdapter);
        rvNews.setAdapter(wrapperAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(this));
    }
}
