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
import com.leyuan.aidong.adapter.GoodsCategoryAdapter;
import com.leyuan.aidong.adapter.home.GoodsRecommendAdapter;
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


/**
 * 商品品牌推荐界面
 *
 * @author song
 */
public class GoodsBrandRecommendActivity extends BaseActivity implements NurtureActivityView {
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recommendView;

    private int currPage = 1;
    private List<GoodsBean> goodsList;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private GoodsRecommendAdapter goodsAdapter;
    private RecommendPresent recommendPresent;

    private String goodsType;
    private String typeName;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {
                case Constant.BROADCAST_ACTION_GOODS_PAY_FAIL:
                case Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS:
                    finish();
                    break;
            }
        }
    };

    public static void start(Context context, String goodsType,String typeName) {
        Intent intent = new Intent(context, GoodsBrandRecommendActivity.class);
        intent.putExtra("goodsType", goodsType);
        intent.putExtra("typeName", typeName);
        (context).startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodsType = getIntent().getStringExtra("goodsType");
        typeName = getIntent().getStringExtra("typeName");
        initBroadCastReceiver();

        setContentView(R.layout.activity_food_and_beverage);
        recommendPresent = new RecommendPresentImpl(this, this);
        initTopLayout();
        initSwipeRefreshLayout();
        initRecommendRecyclerView();
        recommendPresent.commendLoadRecommendData(switcherLayout, goodsType);
    }

    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_FAIL);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    private void initTopLayout() {
        SimpleTitleBar titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        RecyclerView categoryView = (RecyclerView) findViewById(R.id.rv_category);
        GoodsCategoryAdapter categoryAdapter = new GoodsCategoryAdapter(this, goodsType);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        categoryView.setLayoutManager(layoutManager);
        categoryView.setAdapter(categoryAdapter);
        categoryAdapter.setData(SystemInfoUtils.getGoodsCategory(this, goodsType)); //要改
        titleBar.setTitle(typeName);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this, refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recommendView);
                recommendPresent.pullToRefreshRecommendData(goodsType);
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recommendView);
                recommendPresent.commendLoadRecommendData(switcherLayout, goodsType);
            }
        });
    }


    private void initRecommendRecyclerView() {
        recommendView = (RecyclerView) findViewById(R.id.rv_recommend);
        goodsList = new ArrayList<>();
        goodsAdapter = new GoodsRecommendAdapter(this, goodsType);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(goodsAdapter);
        recommendView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter)
                recommendView.getAdapter(), manager.getSpanCount()));
        recommendView.setLayoutManager(manager);
        recommendView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recommendView, View.inflate(this, R.layout.header_nurture, null));
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (goodsList != null && goodsList.size() >= pageSize) {
                recommendPresent.requestMoreRecommendData(recommendView, pageSize, currPage, goodsType);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<GoodsBean> goodsBeanList) {
        if (refreshLayout.isRefreshing()) {
            goodsList.clear();
            refreshLayout.setRefreshing(false);
        }
        goodsList.addAll(goodsBeanList);
        goodsAdapter.setData(goodsList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recommendView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(this, R.layout.empty_recommend, null);
        switcherLayout.addCustomView(view, "empty");
        switcherLayout.showCustomLayout("empty");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
