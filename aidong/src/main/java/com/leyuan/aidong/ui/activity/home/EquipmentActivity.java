package com.leyuan.aidong.ui.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.home.adapter.EquipmentAdapter;
import com.leyuan.aidong.ui.activity.home.adapter.CategoryAdapter;
import com.leyuan.aidong.entity.EquipmentBean;
import com.leyuan.aidong.ui.mvp.presenter.EquipmentPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.EquipmentPresentImpl;
import com.leyuan.aidong.ui.mvp.view.EquipmentActivityView;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 装备
 * Created by song on 2016/8/25
 */
public class EquipmentActivity extends BaseActivity implements EquipmentActivityView{
    private SimpleTitleBar titleBar;
    private RecyclerView categoryRecyclerView;


    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recommendRecyclerView;

    private int currPage = 1;
    private List<EquipmentBean> equipmentList;
    private EquipmentAdapter equipmentAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private EquipmentPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        pageSize = 20;
        present = new EquipmentPresentImpl(this,this);

        initTopLayout();
        initSwipeRefreshLayout();
        initRecommendRecyclerView();

        present.commonLoadData(switcherLayout);
    }

    private void initTopLayout(){
        titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        categoryRecyclerView = (RecyclerView)findViewById(R.id.rv_category);
        CategoryAdapter categoryAdapter = new CategoryAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadData(switcherLayout);
            }
        });
    }

    private void initRecommendRecyclerView() {
        recommendRecyclerView = (RecyclerView)findViewById(R.id.rv_recommend);
        equipmentList = new ArrayList<>();
        equipmentAdapter = new EquipmentAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(equipmentAdapter);
        recommendRecyclerView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter)recommendRecyclerView.getAdapter(), manager.getSpanCount()));
        recommendRecyclerView.setLayoutManager(manager);
        recommendRecyclerView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recommendRecyclerView, View.inflate(this,R.layout.header_nurture_or_equipment,null));
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (equipmentList != null && equipmentList.size() >= pageSize) {
                present.requestMoreData(recommendRecyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<EquipmentBean> equipmentBeanList) {
        if(refreshLayout.isRefreshing()){
            equipmentList.clear();
            refreshLayout.setRefreshing(false);
        }
        equipmentList.addAll(equipmentBeanList);
        equipmentAdapter.setData(equipmentList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recommendRecyclerView, LoadingFooter.State.TheEnd);
    }
}
