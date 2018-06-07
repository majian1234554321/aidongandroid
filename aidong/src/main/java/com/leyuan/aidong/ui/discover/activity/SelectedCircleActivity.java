package com.leyuan.aidong.ui.discover.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aidong.R;
import com.leyuan.aidong.adapter.discover.SelectCircleAdapter;
import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.discover.view.SearchHeaderView;
import com.leyuan.aidong.ui.mvp.presenter.CirclePrensenter;
import com.leyuan.aidong.ui.mvp.view.SelectedCircleView;
import com.leyuan.aidong.utils.DialogUtils;
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

public class SelectedCircleActivity extends BaseActivity implements SearchHeaderView.OnSearchListener, SelectedCircleView {

    private CommonTitleLayout layoutTitle;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private SwitcherLayout switcherLayout;
    SelectCircleAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    CirclePrensenter circlePrensenter;


    boolean isSearch;
    private String keyword;
    private SearchHeaderView headView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_location_activity);

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        initSwipeRefreshLayout();
        initRecyclerView();
        initSwitcherLayout();
        circlePrensenter = new CirclePrensenter(this);
        circlePrensenter.setSelectedCircleListener(this);
        circlePrensenter.getRecommendCircle();

        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initSwipeRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                if (isSearch) {
                    circlePrensenter.searchCircle(keyword);
                } else {
                    circlePrensenter.getRecommendCircle();
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
        adapter = new SelectCircleAdapter(this);

        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);

        headView = new SearchHeaderView(this);
        headView.setOnsearchListner(this);
        headView.setSearchHint(getResources().getString(R.string.search_more_circle));
        headView.setTxtSearchTitle(getResources().getString(R.string.hot_recommend));

        RecyclerViewUtils.setHeaderView(recyclerView, headView);

    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
        }
    };

    @Override
    public void onSearch(String keyword) {
        isSearch = true;
        currPage = 1;
        this.keyword = keyword;
        if (headView != null)
            headView.setTxtSearchTitleVisible(View.GONE);
        DialogUtils.showDialog(this, "", true);
        circlePrensenter.searchCircle(keyword);
    }

    @Override
    public void onGetRecommendCircle(ArrayList<CampaignBean> items) {
        adapter.setData(items);
    }

    @Override
    public void onSearchCircleResult(ArrayList<CampaignBean> items) {
        DialogUtils.dismissDialog();
        adapter.setData(items);
    }
}
