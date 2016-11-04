package com.leyuan.aidong.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.home.adapter.FoodAdapter;
import com.leyuan.aidong.ui.activity.home.adapter.RecommendVenuesAdapter;
import com.leyuan.aidong.ui.activity.home.adapter.SamplePagerAdapter;
import com.leyuan.aidong.entity.FoodAndVenuesBean;
import com.leyuan.aidong.entity.FoodBean;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.mvp.presenter.FoodPresenter;
import com.leyuan.aidong.ui.mvp.presenter.impl.FoodPresentImpl;
import com.leyuan.aidong.ui.mvp.view.FoodActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.customview.ViewPagerIndicator;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康餐饮
 * Created by song on 2016/8/18.
 */
public class FoodActivity extends BaseActivity implements FoodActivityView, View.OnClickListener {
    private ImageView ivBack;
    private TextView tvSearch;

    // header
    private View headerView;
    private ViewPager viewPager;
    private TextView tvRecommend;
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
        initTop();
        initHeaderView();
        initSwipeRefreshLayout();
        initRecyclerView();
        present.commonLoadData(switcherLayout);
    }

    private void initTop(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvSearch = (TextView) findViewById(R.id.tv_search);
        ivBack.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
    }

    private void initHeaderView(){
        headerView = View.inflate(this,R.layout.header_food,null);
        headerView.setLayoutParams(new LinearLayout.LayoutParams
                (LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        viewPager = (ViewPager) headerView.findViewById(R.id.vP_food);
        tvRecommend = (TextView) headerView.findViewById(R.id.tv_recommend);
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
            if (foodList != null && foodList.size() >= pageSize) {
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
        if(foodAndVenuesBean.getPick_up_gym() != null){
            tvRecommend.setVisibility(View.VISIBLE);
            venuesList.addAll(foodAndVenuesBean.getPick_up_gym());
        }else {
            tvRecommend.setVisibility(View.GONE);
        }
        if(foodAndVenuesBean.getFood() != null){
           foodList.addAll(foodAndVenuesBean.getFood());
        }
        venuesAdapter.setData(venuesList);
        foodAdapter.setData(foodList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(foodRecyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                startActivity(new Intent(this,SearchActivity.class));
                break;
            default:
                break;
        }
    }
}
