package com.leyuan.aidong.ui.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aidong.R;
import com.leyuan.aidong.adapter.home.CategoryAdapter;
import com.leyuan.aidong.adapter.home.NurtureAdapter;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.RecommendPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.RecommendPresentImpl;
import com.leyuan.aidong.ui.mvp.view.NurtureActivityView;
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
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.GOODS_NUTRITION;
import static com.leyuan.aidong.utils.Constant.RECOMMEND_NUTRITION;


/**
 * 营养品界面
 * @author song
 */
//todo 营养品界面和装备界面合成一个界面
public class NurtureActivity extends BaseActivity implements NurtureActivityView{
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recommendView;

    private int currPage = 1;
    private List<GoodsBean> nurtureList;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private NurtureAdapter nurtureAdapter;
    private RecommendPresent recommendPresent;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Constant.BROADCAST_ACTION_GOODS_PAY_FAIL:
                case Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBroadCastReceiver();
        setContentView(R.layout.activity_nurture);
        recommendPresent = new RecommendPresentImpl(this,this);
        initTopLayout();
        initSwipeRefreshLayout();
        initRecommendRecyclerView();
        recommendPresent.commendLoadRecommendData(switcherLayout,RECOMMEND_NUTRITION);
    }

    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_FAIL);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,filter);
    }

    private void initTopLayout(){
        SimpleTitleBar titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        RecyclerView categoryView = (RecyclerView)findViewById(R.id.rv_category);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, GOODS_NUTRITION);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false);
        categoryView.setLayoutManager(layoutManager);
        categoryView.setAdapter(categoryAdapter);
        categoryAdapter.setData(SystemInfoUtils.getNurtureCategory(this));
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (CustomRefreshLayout)findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recommendView);
                recommendPresent.pullToRefreshRecommendData(RECOMMEND_NUTRITION);
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recommendView);
                recommendPresent.commendLoadRecommendData(switcherLayout,RECOMMEND_NUTRITION);
            }
        });
    }


    private void initRecommendRecyclerView() {
        recommendView = (RecyclerView)findViewById(R.id.rv_recommend);
        nurtureList = new ArrayList<>();
        nurtureAdapter = new NurtureAdapter(this,GOODS_NUTRITION);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(nurtureAdapter);
        recommendView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter)
                recommendView.getAdapter(), manager.getSpanCount()));
        recommendView.setLayoutManager(manager);
        recommendView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recommendView,View.inflate(this,R.layout.header_nurture,null));
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (nurtureList != null && nurtureList.size() >= pageSize) {
                recommendPresent.requestMoreRecommendData(recommendView,pageSize,currPage, RECOMMEND_NUTRITION);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<GoodsBean> goodsBeanList) {
        if(refreshLayout.isRefreshing()){
            nurtureList.clear();
            refreshLayout.setRefreshing(false);
        }
        nurtureList.addAll(goodsBeanList);
        nurtureAdapter.setData(nurtureList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recommendView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(this,R.layout.empty_recommend,null);
        switcherLayout.addCustomView(view,"empty");
        switcherLayout.showCustomLayout("empty");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
