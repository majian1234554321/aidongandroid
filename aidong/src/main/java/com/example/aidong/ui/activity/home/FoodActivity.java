package com.example.aidong.ui.activity.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.ui.activity.home.adapter.FoodAdapter;
import com.example.aidong.ui.activity.home.adapter.RecommendVenuesAdapter;
import com.example.aidong.ui.activity.home.adapter.SamplePagerAdapter;
import com.example.aidong.entity.FoodAndVenuesBean;
import com.example.aidong.entity.FoodBean;
import com.example.aidong.entity.VenuesBean;
import com.example.aidong.ui.mvp.presenter.FoodPresenter;
import com.example.aidong.ui.mvp.presenter.impl.FoodPresentImpl;
import com.example.aidong.ui.mvp.view.FoodActivityView;
import com.example.aidong.widget.customview.SwitcherLayout;
import com.example.aidong.widget.customview.ViewPagerIndicator;
import com.example.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.example.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康餐饮
 * Created by song on 2016/8/18.
 */
public class FoodActivity extends BaseActivity implements FoodActivityView{
    // header
    private View headerView;
    private ViewPager viewPager;
    private RecyclerView venuesRecyclerView;
    private List<VenuesBean> venuesList;
    private RecommendVenuesAdapter venuesAdapter;

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView foodRecyclerView;

    private int currPage = 1;
    private ArrayList<FoodBean> foodList = new ArrayList<>();
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private FoodAdapter foodAdapter;
    private FoodPresenter present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        pageSize = 20;
        present = new FoodPresentImpl(this,this);
        initHeaderView();
        initSwipeRefreshLayout();
        initRecyclerView();
        present.commonLoadData(switcherLayout);
    }

    private void initHeaderView(){
        headerView = View.inflate(this,R.layout.header_food,null);
        headerView.setLayoutParams(new LinearLayout.LayoutParams
                (LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        viewPager = (ViewPager) headerView.findViewById(R.id.vP_food);
        venuesRecyclerView = (RecyclerView)headerView.findViewById(R.id.rv_recommend_venue);
        ViewPagerIndicator indicator = (ViewPagerIndicator)headerView.findViewById(R.id.vp_indicator);
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);

        venuesList = new ArrayList<>();
        venuesAdapter = new RecommendVenuesAdapter(this);
        venuesRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        venuesRecyclerView.setAdapter(venuesAdapter);
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                present.pullToRefreshData();
            }
        });
    }

    private void initRecyclerView() {
        foodRecyclerView = (RecyclerView)findViewById(R.id.rv_food);
        foodList = new ArrayList<>();
        foodAdapter = new FoodAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(foodAdapter);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodRecyclerView.setAdapter(wrapperAdapter);
        foodRecyclerView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(foodRecyclerView, headerView);
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (foodList != null && !foodList.isEmpty()) {
                present.requestMoreData(foodRecyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(FoodAndVenuesBean foodAndVenuesBean) {
        if(refreshLayout.isRefreshing()){
            venuesList.clear();
            foodList.clear();
            refreshLayout.setRefreshing(false);
        }
        venuesList.addAll(foodAndVenuesBean.getPick_up_gym());
        venuesList.add(foodAndVenuesBean.getPick_up_gym().get(0));
        foodList.addAll(foodAndVenuesBean.getFood());
        venuesAdapter.setData(venuesList);
        foodAdapter.setData(foodList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        switcherLayout.showEmptyLayout();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(foodRecyclerView, LoadingFooter.State.TheEnd);
    }
}
