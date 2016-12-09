package com.leyuan.aidong.ui.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.home.adapter.NurtureAdapter;
import com.leyuan.aidong.ui.activity.home.adapter.CategoryAdapter;
import com.leyuan.aidong.entity.NurtureBean;
import com.leyuan.aidong.ui.mvp.presenter.NurturePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.NurturePresentImpl;
import com.leyuan.aidong.ui.mvp.view.NurtureActivityView;
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
 * 营养品界面
 * @author song
 */
public class NurtureActivity extends BaseActivity implements NurtureActivityView{
    private SimpleTitleBar titleBar;
    private RecyclerView categoryView;
    private CategoryAdapter categoryAdapter;

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recommendView;

    private int currPage = 1;
    private List<NurtureBean> nurtureList;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private NurtureAdapter nurtureAdapter;
    private NurturePresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurture);
        pageSize = 20;
        present = new NurturePresentImpl(this,this);

        initTopLayout();
        initSwipeRefreshLayout();
        initRecommendRecyclerView();

        present.getCategory();
        present.commonLoadRecommendData(switcherLayout);
    }

    private void initTopLayout(){
        titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        categoryView = (RecyclerView)findViewById(R.id.rv_category);
        categoryAdapter = new CategoryAdapter(this,GoodsFilterActivity.TYPE_NURTURE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        categoryView.setLayoutManager(layoutManager);
        categoryView.setAdapter(categoryAdapter);
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
                present.pullToRefreshRecommendData();
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadRecommendData(switcherLayout);
            }
        });
    }


    private void initRecommendRecyclerView() {
        recommendView = (RecyclerView)findViewById(R.id.rv_recommend);
        nurtureList = new ArrayList<>();
        nurtureAdapter = new NurtureAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(nurtureAdapter);
        recommendView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) recommendView.getAdapter(), manager.getSpanCount()));
        recommendView.setLayoutManager(manager);
        recommendView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recommendView,View.inflate(this,R.layout.header_nurture_or_equipment,null));
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (nurtureList != null && !nurtureList.isEmpty()) {
                present.requestMoreRecommendData(recommendView,pageSize,currPage);
            }
        }
    };

    @Override
    public void setCategory(ArrayList<CategoryBean> categoryBeanList) {
        categoryAdapter.setData(categoryBeanList);
    }

    @Override
    public void updateRecyclerView(List<NurtureBean> nurtureBeanList) {
        if(refreshLayout.isRefreshing()){
            nurtureList.clear();
            refreshLayout.setRefreshing(false);
        }
        nurtureList.addAll(nurtureBeanList);
        nurtureAdapter.setData(nurtureList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recommendView, LoadingFooter.State.TheEnd);
    }
}
