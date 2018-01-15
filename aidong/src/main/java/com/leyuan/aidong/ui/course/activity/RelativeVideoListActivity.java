package com.leyuan.aidong.ui.course.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.video.RelativeViedeoAdapter;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import static com.leyuan.aidong.ui.App.context;

/**
 * Created by user on 2018/1/11.
 */
public class RelativeVideoListActivity extends BaseActivity{
    private SimpleTitleBar titleBar;
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView rvNews;

    private int currPage = 1;
    private RelativeViedeoAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_video);

        titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void initSwipeRefreshLayout(){
        refreshLayout = (CustomRefreshLayout)findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });
    }

    private void initRecyclerView(){
        rvNews = (RecyclerView)findViewById(R.id.rv_news);
        adapter = new RelativeViedeoAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        rvNews.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(context, 2);
        rvNews.setLayoutManager(manager);
        rvNews.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
        }
    };

}
