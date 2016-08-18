package com.example.aidong.activity.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.FoodAdapter;
import com.example.aidong.activity.home.adapter.SamplePagerAdapter;
import com.leyuan.support.entity.FoodBean;
import com.leyuan.support.mvp.presenter.FoodActivityPresenter;
import com.leyuan.support.mvp.presenter.impl.FoodActivityPresentImpl;
import com.leyuan.support.mvp.view.FoodActivityView;
import com.leyuan.support.widget.customview.ViewPagerIndicator;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康餐饮
 * Created by song on 2016/8/18.
 */
public class FoodActivity extends BaseActivity implements FoodActivityView{

    private View headerView;
    private ViewPager viewPager;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private int currPage = 1;
    private ArrayList<FoodBean> data = new ArrayList<>();
    private HeaderAndFooterRecyclerViewAdapter wrapAdapter;
    private FoodAdapter foodAdapter;
    private FoodActivityPresenter present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        pageSize = 20;
        present = new FoodActivityPresentImpl(this,this);
        initHeaderView();
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
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

    private void initHeaderView(){
        headerView = View.inflate(this,R.layout.header_food,null);
        viewPager = (ViewPager) headerView.findViewById(R.id.vP_food);
        ViewPagerIndicator indicator = (ViewPagerIndicator)headerView.findViewById(R.id.vp_indicator);
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_food);
        data = new ArrayList<>();
        foodAdapter = new FoodAdapter();
        wrapAdapter = new HeaderAndFooterRecyclerViewAdapter(foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recyclerView, headerView);
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && !data.isEmpty()) {
                present.requestMoreData(recyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<FoodBean> foodBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(data);
       // foodAdapter.setData(data);
        wrapAdapter.notifyDataSetChanged();
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
