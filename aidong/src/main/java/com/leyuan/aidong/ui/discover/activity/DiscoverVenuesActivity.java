package com.leyuan.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.example.aidong.R;
import com.leyuan.aidong.adapter.discover.VenuesAdapter;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.discover.view.VenuesFilterView;
import com.leyuan.aidong.ui.home.activity.SearchActivity;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.DiscoverVenuesActivityView;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现-场馆
 * Created by song on 2016/8/29.
 */
public class DiscoverVenuesActivity extends BaseActivity implements DiscoverVenuesActivityView {
    private static final int HIDE_THRESHOLD = 80;

    private int scrolledDistance;
    private boolean filterViewVisible = true;

    private ImageView ivBack;
    private ImageView ivSearch;

    private RecyclerView recyclerView;
    private CustomRefreshLayout refreshLayout;
    private SwitcherLayout switcherLayout;
    private VenuesFilterView filterView;

    private int currPage = 1;
    private VenuesAdapter venuesAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private ArrayList<VenuesBean> data = new ArrayList<>();

    private VenuesPresent venuesPresent;
    private String brand_id;
    private String landmark;
    private String gymTypes;
    private  String bussiness_area;


    public static void start(Context context, @Nullable String brand_id) {
        Intent intent = new Intent(context, DiscoverVenuesActivity.class);
        intent.putExtra("brand_id", brand_id);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_venues);
        venuesPresent = new VenuesPresentImpl(this, this);
        brand_id = getIntent().getStringExtra("brand_id");
        initView();
        setListener();
        venuesPresent.getGymBrand();
        venuesPresent.getBusinessCircle();
        venuesPresent.getGymTypes();
        venuesPresent.commonLoadData(switcherLayout, brand_id, landmark,bussiness_area, gymTypes);
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        initSwipeRefreshLayout();
        initRecyclerView();
        initFilterView();
    }

    private void setListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiscoverVenuesActivity.this, SearchActivity.class));
            }
        });
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this, refreshLayout);
        refreshLayout.setProgressViewOffset(true, 50,100);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

    }

    private void refreshData() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        venuesPresent.pullToRefreshData(brand_id, landmark,bussiness_area, gymTypes);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_venues);
        data = new ArrayList<>();
        venuesAdapter = new VenuesAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(venuesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private void initFilterView() {
        filterView = (VenuesFilterView) findViewById(R.id.filter_view);
        filterView.setOnFilterClickListener(new VenuesFilterView.OnFilterClickListener() {
            @Override
            public void onBrandItemClick(String brandId) {
                brand_id = brandId;
                refreshData();
            }

            @Override
            public void onBusinessCircleItemClick(String area,String address) {
                landmark = address;
                bussiness_area = area;
                refreshData();
            }

            @Override
            public void onTypeItemClick(String typeStr) {
                gymTypes = typeStr;
                refreshData();
            }
        });
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                venuesPresent.requestMoreData(recyclerView, pageSize, currPage, brand_id, landmark,bussiness_area, gymTypes);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (scrolledDistance > HIDE_THRESHOLD && filterViewVisible) {           //手指向上滑动
                filterView.animate().translationY(-filterView.getHeight()).setInterpolator
                        (new AccelerateInterpolator(2)).start();
                filterViewVisible = false;
                scrolledDistance = 0;
            } else if (scrolledDistance < -HIDE_THRESHOLD && !filterViewVisible) {   //手指向下滑动
                filterView.animate().translationY(0).setInterpolator
                        (new DecelerateInterpolator(2)).start();
                scrolledDistance = 0;
                filterViewVisible = true;
            }
            if ((filterViewVisible && dy > 0) || (!filterViewVisible && dy < 0)) {
                scrolledDistance += dy;
            }
        }
    };

    @Override
    public void setGymBrand(List<CategoryBean> gymBrandBeanList) {
        filterView.setBrandList(gymBrandBeanList);
        if(brand_id !=null){
            filterView.selectCategory(brand_id);
        }
    }

    @Override
    public void setBusinessCircle(List<DistrictBean> circleBeanList) {
        filterView.setCircleList(circleBeanList);
    }

    @Override
    public void setGymTypes(List<String> gymTypesList) {
        filterView.setTypeList(gymTypesList);
    }

    @Override
    public void onRefreshData(List<VenuesBean> venuesBeanList) {
        switcherLayout.showContentLayout();
        data.clear();
        refreshLayout.setRefreshing(false);
        data.addAll(venuesBeanList);
        venuesAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreData(List<VenuesBean> venuesBeanList) {
        refreshLayout.setRefreshing(false);
        data.addAll(venuesBeanList);
        venuesAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(this,R.layout.empty_venues,null);
        switcherLayout.addCustomView(view,"empty");
        switcherLayout.showCustomLayout("empty");
    }

}
