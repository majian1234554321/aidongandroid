package com.example.aidong.activity.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.aidong.BaseActivity;
import com.example.aidong.BaseApp;
import com.example.aidong.R;
import com.example.aidong.activity.discover.adapter.UserAdapter;
import com.leyuan.support.entity.UserBean;
import com.leyuan.support.mvp.presenter.DiscoverUserPresent;
import com.leyuan.support.mvp.presenter.impl.DiscoverUserPresentImpl;
import com.leyuan.support.mvp.view.DiscoverUserActivityView;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现-用户
 * Created by song on 2016/8/29.
 */
public class DiscoverUserActivity extends BaseActivity implements DiscoverUserActivityView{
    private Toolbar toolbar;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private DiscoverUserPresent userPresent;

    private int currPage = 1;
    private List<UserBean> data;
    private UserAdapter userAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_user);
        pageSize = 20;
        userPresent = new DiscoverUserPresentImpl(this,this);

        initToolbar();
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
    }

    private void initSwipeRefreshLayout(){
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                //userPresent.pullToRefreshData(recyclerView,BaseApp.lat,BaseApp.lon,"","");
            }
        });

     /* refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                userPresent.pullToRefreshData(recyclerView,BaseApp.lat,BaseApp.lon,"","");
            }
        });*/
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_user);
        data = new ArrayList<>();
        userAdapter = new UserAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && !data.isEmpty()) {
                userPresent.requestMoreData(recyclerView,BaseApp.lat,BaseApp.lon,"","",pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<UserBean> userList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        for(int i=0;i<10;i++){
            data.addAll(userList);
        }
        userAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
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
