package com.leyuan.aidong.ui.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.GoodsRecommendAdapter;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.GoodsVirtualListPresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/12/29.
 */
public class GoodsVirtualListActivity extends BaseActivity implements GoodsActivityView {
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recommendView;

    private int currPage = 1;
    private List<GoodsBean> goodsList;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private GoodsRecommendAdapter goodsAdapter;
    private GoodsVirtualListPresentImpl present;

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
    private String product_ids;


    public static void start(Context context, String product_ids) {
        Intent intent = new Intent(context, GoodsVirtualListActivity.class);
        intent.putExtra("product_ids", product_ids);
        (context).startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_pack_info);
        product_ids = getIntent().getStringExtra("product_ids");
        present = new GoodsVirtualListPresentImpl(this,this);

        initBroadCastReceiver();
        initSwipeRefreshLayout();
        initRecommendRecyclerView();

        present.commendLoadRecommendData(switcherLayout,product_ids);

    }

    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_FAIL);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }
    private void initSwipeRefreshLayout() {
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this, refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recommendView);
                present.pullToRefreshData(product_ids);
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recommendView);
                present.commendLoadRecommendData(switcherLayout,product_ids);
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
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (goodsList != null && goodsList.size() >= pageSize) {
                present.requestMoreRecommendData(recommendView, pageSize, currPage, product_ids);
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
