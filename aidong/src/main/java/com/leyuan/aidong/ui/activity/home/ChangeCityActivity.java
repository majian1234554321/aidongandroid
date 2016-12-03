package com.leyuan.aidong.ui.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.CityAdapter;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市切换界面
 * Created by song on 2016/8/23.
 */
public class ChangeCityActivity extends BaseActivity{
    private View header;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<String> data;
    private CityAdapter cityAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    //private HomePresent homePresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);

        initHeaderView();
        initSwipeRefreshLayout();
        initRecyclerView();
        List<String> openCity = SystemInfoUtils.getOpenCity(this);
        cityAdapter.setData(openCity);
        wrapperAdapter.notifyDataSetChanged();
    }

    private void initHeaderView(){
        header = View.inflate(this,R.layout.header_city,null);
    }


    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                //present.pullToRefreshHomeData(recyclerView);
            }
        });

      /*  refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                //present.pullToRefreshHomeData(recyclerView);
            }
        });*/
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_city);
        data = new ArrayList<>();
        cityAdapter = new CityAdapter();
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(cityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recyclerView, header);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && !data.isEmpty()) {
               // present.requestMoreHomeData(recyclerView,pageSize,currPage);
            }
        }
    };
}
