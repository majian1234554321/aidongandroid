package com.example.aidong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.activity.home.CampaignActivity;
import com.example.aidong.activity.home.CourseActivity;
import com.example.aidong.activity.home.EquipmentActivity;
import com.example.aidong.activity.home.FoodActivity;
import com.example.aidong.activity.home.GoodsDetailActivity;
import com.example.aidong.activity.home.NurtureActivity;
import com.example.aidong.activity.home.GoodsFilterActivity;
import com.example.aidong.activity.home.adapter.BannerAdapter;
import com.example.aidong.activity.home.adapter.HomeRecycleViewAdapter;
import com.leyuan.support.entity.BannerBean;
import com.leyuan.support.entity.HomeBean;
import com.leyuan.support.mvp.presenter.HomePresent;
import com.leyuan.support.mvp.presenter.impl.HomePresentImpl;
import com.leyuan.support.mvp.view.HomeFragmentView;
import com.leyuan.support.widget.customview.ViewPagerIndicator;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * @author song
 */
public class HomeFragment extends BaseFragment implements HomeFragmentView,View.OnClickListener{
    private TextView tvLocation;
    private ImageView ivSearch;

    private View headerView;
    private ViewPager viewPager;
    private BannerAdapter bannerAdapter;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private int currPage = 1;
    private ArrayList<HomeBean> data = new ArrayList<>();
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private HomeRecycleViewAdapter homeAdapter;
    private HomePresent present;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageSize = 20;
        present = new HomePresentImpl(getContext(),this);

        tvLocation = (TextView)view.findViewById(R.id.tv_location);
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        initHeaderView();
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        setListener();
    }


    private void initHeaderView(){
        headerView = View.inflate(getContext(),R.layout.header_home,null);
        viewPager = (ViewPager) headerView.findViewById(R.id.vp_home);
        ViewPagerIndicator indicator = (ViewPagerIndicator)headerView.findViewById(R.id.vp_indicator);
        bannerAdapter = new BannerAdapter(getContext());
        //HomeViewPagerAdapter pagerAdapter = new HomeViewPagerAdapter(imageList);
        viewPager.setAdapter(bannerAdapter);
        indicator.setViewPager(viewPager);
        bannerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        headerView.findViewById(R.id.tv_food).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FoodActivity.class);
                startActivity(intent);
            }
        });

        headerView.findViewById(R.id.tv_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CampaignActivity.class);
                startActivity(intent);
            }
        });

        headerView.findViewById(R.id.tv_nurture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NurtureActivity.class);
                startActivity(intent);
            }
        });
        headerView.findViewById(R.id.tv_equipment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EquipmentActivity.class);
                startActivity(intent);
            }
        });

        headerView.findViewById(R.id.tv_course).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CourseActivity.class);
                startActivity(intent);
            }
        });

        headerView.findViewById(R.id.tv_competition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                startActivity(intent);
            }
        });

        present.getBanners();
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.sr_refresh);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                present.pullToRefreshData();
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                present.pullToRefreshData();
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
        data = new ArrayList<>();
        homeAdapter = new HomeRecycleViewAdapter(getActivity());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(homeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recyclerView, headerView);
    }

    private void setListener(){
        tvLocation.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
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
    public void updateBanner(List<BannerBean> bannerBeanList) {
        bannerAdapter.setData(bannerBeanList);
    }

    @Override
    public void updateRecyclerView(List<HomeBean> homeBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(homeBeanList);
        if(!data.isEmpty()){
            homeAdapter.setData(data);
            wrapperAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search:
                Intent intent = new Intent(getContext(), GoodsFilterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
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
