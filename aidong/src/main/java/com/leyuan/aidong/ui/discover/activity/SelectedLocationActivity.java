package com.leyuan.aidong.ui.discover.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.example.aidong.R;
import com.leyuan.aidong.adapter.discover.SelectLocationAdapter;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.discover.view.SearchHeaderView;
import com.leyuan.aidong.ui.mvp.presenter.impl.SelectedLocationPrensentImpl;
import com.leyuan.aidong.ui.mvp.view.SelectedLocationView;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.CommonTitleLayout;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/9.
 */

public class SelectedLocationActivity extends BaseActivity implements SearchHeaderView.OnSearchListener, OnGetPoiSearchResultListener, SelectedLocationView {

    private CommonTitleLayout layoutTitle;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private SwitcherLayout switcherLayout;
    SelectLocationAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private PoiSearch mPoiSearch;
    SelectedLocationPrensentImpl prensent;
    ArrayList<VenuesBean> venuesArray = new ArrayList<>();

    boolean isSearch;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_location_activity);

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        prensent = new SelectedLocationPrensentImpl(this, this);

        initSwipeRefreshLayout();
        initRecyclerView();
        initSwitcherLayout();

        prensent.refreshVenuesNearly(currPage);
        initSearch();
        layoutTitle.setTxtTitle("我的位置");
        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initSearch() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
    }

    private void initSwipeRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                if (isSearch) {
                    searchLocation(keyword, currPage - 1);
                } else {

                    RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                    prensent.refreshVenuesNearly(currPage);
                }

            }
        });
    }

    private void initSwitcherLayout() {
        switcherLayout = new SwitcherLayout(this, refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initRecyclerView() {
        adapter = new SelectLocationAdapter(this);

        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);

        SearchHeaderView headView = new SearchHeaderView(this);
        headView.setOnsearchListner(this);
        headView.setSearchHint(getResources().getString(R.string.search_nearly_location));
        headView.setTxtSearchTitle(getResources().getString(R.string.nearly_store));
        RecyclerViewUtils.setHeaderView(recyclerView, headView);

    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (isSearch) {
                searchLocation(keyword, currPage - 1);
            } else {
                prensent.getVenuesNearlyMore(currPage);
            }

        }
    };

    @Override
    public void onSearch(String keyword) {

        isSearch = true;
        currPage = 1;
        this.keyword = keyword;

        searchLocation(keyword, currPage - 1);

    }

    private void searchLocation(String keyword, int page) {
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                .keyword(keyword)
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(new LatLng(App.lat, App.lon))
                .radius(500)
                .pageNum(page)
                .pageCapacity(20);
        mPoiSearch.searchNearby(nearbySearchOption);
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        refreshLayout.setRefreshing(false);
        if (currPage == 1) {
            venuesArray.clear();
        }

        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
//            ToastGlobal.showShortConsecutive("未找到结果");
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            for (PoiInfo info : result.getAllPoi()) {
                Logger.i("PoiInfo : " + info.name + ", " + info.address + " ,lat = "
                        + info.location.latitude + ", lon = " + info.location.longitude);
                VenuesBean venuesBean = new VenuesBean();
                venuesBean.setName(info.name);
                venuesBean.setAddress(info.address);
                venuesBean.lat = info.location.latitude + "";
                venuesBean.lng = info.location.longitude + "";

                venuesArray.add(venuesBean);
            }

            adapter.setData(venuesArray);

        }

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }


    @Override
    public void onRefreshData(ArrayList<VenuesBean> gym) {
        refreshLayout.setRefreshing(false);

        venuesArray.clear();
        venuesArray.addAll(gym);
        adapter.setData(venuesArray);
    }

    @Override
    public void onGetMoreData(ArrayList<VenuesBean> gym) {
        venuesArray.addAll(gym);
        adapter.setData(venuesArray);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPoiSearch.destroy();
    }
}
