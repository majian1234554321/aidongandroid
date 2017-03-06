package com.leyuan.aidong.ui.home.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.CategoryAdapter;
import com.leyuan.aidong.adapter.home.EquipmentAdapter;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.RecommendPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.RecommendPresentImpl;
import com.leyuan.aidong.ui.mvp.view.EquipmentActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.TYPE_EQUIPMENT;

/**
 * 装备
 * Created by song on 2016/8/25
 */
public class EquipmentActivity extends BaseActivity implements EquipmentActivityView{
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recommendView;

    private int currPage = 1;
    private List<GoodsBean> equipmentList;
    private EquipmentAdapter equipmentAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private RecommendPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        present = new RecommendPresentImpl(this,this);

        initTopLayout();
        initSwipeRefreshLayout();
        initRecommendRecyclerView();
        present.commendLoadData(switcherLayout, Constant.RECOMMEND_TYPE_EQUIPMENT);
    }

    private void initTopLayout(){
        SimpleTitleBar titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        RecyclerView categoryView = (RecyclerView)findViewById(R.id.rv_category);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this,TYPE_EQUIPMENT);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false);
        categoryView.setLayoutManager(layoutManager);
        categoryView.setAdapter(categoryAdapter);
        categoryAdapter.setData(SystemInfoUtils.getEquipmentCategory(this));
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
                RecyclerViewStateUtils.resetFooterViewState(recommendView);
                present.pullToRefreshData(Constant.RECOMMEND_TYPE_EQUIPMENT);
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recommendView);
                present.commendLoadData(switcherLayout,Constant.RECOMMEND_TYPE_EQUIPMENT);
            }
        });
    }

    private void initRecommendRecyclerView() {
        recommendView = (RecyclerView)findViewById(R.id.rv_recommend);
        equipmentList = new ArrayList<>();
        equipmentAdapter = new EquipmentAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(equipmentAdapter);
        recommendView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter)
                recommendView.getAdapter(), manager.getSpanCount()));
        recommendView.setLayoutManager(manager);
        recommendView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recommendView, View.inflate(this,R.layout.header_nurture,null));
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (equipmentList != null && equipmentList.size() >= pageSize) {
                present.requestMoreData(recommendView,pageSize,currPage,Constant.RECOMMEND_TYPE_EQUIPMENT);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<GoodsBean> goodsBeanList) {
        if(refreshLayout.isRefreshing()){
            equipmentList.clear();
            refreshLayout.setRefreshing(false);
        }
        equipmentList.addAll(goodsBeanList);
        equipmentAdapter.setData(equipmentList);
        wrapperAdapter.notifyDataSetChanged();
    }


    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recommendView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showEmptyView() {

    }
}
