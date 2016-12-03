package com.leyuan.aidong.ui.activity.discover;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.discover.adapter.UserAdapter;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.mvp.presenter.DiscoverPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DiscoverPresentImpl;
import com.leyuan.aidong.ui.mvp.view.DiscoverUserActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现-用户
 * Created by song on 2016/8/29.
 */
public class DiscoverUserActivity extends BaseActivity implements DiscoverUserActivityView, View.OnClickListener {
    private DrawerLayout drawerLayout;
    private LinearLayout filterLayout;
    private ImageView ivBack;
    private TextView tvFilter;

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private DiscoverPresent userPresent;

    private int currPage = 1;
    private List<UserBean> data;
    private UserAdapter userAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_user);
        pageSize = 20;
        userPresent = new DiscoverPresentImpl(this,this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        filterLayout = (LinearLayout) findViewById(R.id.ll_filter);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvFilter = (TextView) findViewById(R.id.tv_filter);
        initSwipeRefreshLayout();
        initRecyclerView();
        //userPresent.commonLoadRecommendData(switcherLayout,0.00,0.00,"","");

        ivBack.setOnClickListener(this);
        tvFilter.setOnClickListener(this);

    }

    private void initSwipeRefreshLayout(){
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                //userPresent.pullToRefreshHomeData(recyclerView,BaseApp.lat,BaseApp.lon,"","");
            }
        });
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
                userPresent.requestMoreUserData(recyclerView, App.lat, App.lon,"","",pageSize,currPage);
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
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_filter:
                drawerLayout.openDrawer(filterLayout);
                break;
            default:
                break;
        }
    }
}
