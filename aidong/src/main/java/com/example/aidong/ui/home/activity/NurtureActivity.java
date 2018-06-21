package com.example.aidong.ui.home.activity;

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
import com.example.aidong.adapter.home.CategoryAdapter;
import com.example.aidong.adapter.home.CategoryAdapter2;
import com.example.aidong.adapter.home.NurtureAdapter;
import com.example.aidong.entity.CategoryBean;
import com.example.aidong.entity.GoodsBean;
import com.example.aidong.entity.MarketPartsBean;
import com.example.aidong.entity.StoreChildBen;
import com.example.aidong.ui.BaseActivity;
import com.example.aidong.ui.mvp.presenter.RecommendPresent;
import com.example.aidong.ui.mvp.presenter.impl.RecommendPresentImpl;
import com.example.aidong.ui.mvp.view.NurtureActivityView;
import com.example.aidong.utils.Constant;
import com.example.aidong.utils.SystemInfoUtils;
import com.example.aidong.widget.SimpleTitleBar;
import com.example.aidong.widget.SwitcherLayout;
import com.example.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong.widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.example.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.example.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong.widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.aidong.utils.Constant.GOODS_NUTRITION;
import static com.example.aidong.utils.Constant.RECOMMEND_NUTRITION;


/**
 * 营养品界面
 *
 * @author song
 */
//todo 营养品界面和装备界面合成一个界面
public class NurtureActivity extends BaseActivity implements NurtureActivityView {
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recommendView;

    private int currPage = 1;
    private List<GoodsBean> nurtureList;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private NurtureAdapter nurtureAdapter;
    private RecommendPresentImpl recommendPresent;

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
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBroadCastReceiver();
        setContentView(R.layout.activity_nurture);


        position = getIntent().getIntExtra("selectPosition", 0);
        recommendPresent = new RecommendPresentImpl(this, this);
        initTopLayout();
        initSwipeRefreshLayout();
        initRecommendRecyclerView();

    }

    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_FAIL);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    private void initTopLayout() {
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this, refreshLayout);
        SimpleTitleBar titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        RecyclerView categoryView = (RecyclerView) findViewById(R.id.rv_category);
        CategoryAdapter2 categoryAdapter = new CategoryAdapter2(this, position);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        categoryView.setLayoutManager(layoutManager);
        categoryView.setAdapter(categoryAdapter);


        if (SystemInfoUtils.getMarketPartsBean(this) != null) {
            List<CategoryBean> list = new ArrayList<>();
            titleBar.setTitle(SystemInfoUtils.getMarketPartsBean(this).get(position).name);

            for (int i = 0; i < SystemInfoUtils.getMarketPartsBean(this).get(position).children.size(); i++) {
                CategoryBean storeChildBen = new CategoryBean();
                storeChildBen.setCategory_id(SystemInfoUtils.getMarketPartsBean(this).get(position).children.get(i).category_id);
                storeChildBen.setCover(SystemInfoUtils.getMarketPartsBean(this).get(position).children.get(i).cover);
                storeChildBen.setName(SystemInfoUtils.getMarketPartsBean(this).get(position).children.get(i).name);

                list.add(storeChildBen);
            }


            if (SystemInfoUtils.getMarketPartsBean(this) != null &&
                    SystemInfoUtils.getMarketPartsBean(this).get(position) != null &&
                    SystemInfoUtils.getMarketPartsBean(this).get(position).children != null &&
                    SystemInfoUtils.getMarketPartsBean(this).get(position).children.size()>0&&
                    SystemInfoUtils.getMarketPartsBean(this).get(position).children.get(0) != null &&
                    SystemInfoUtils.getMarketPartsBean(this).get(position).children.get(0).children != null) {

                for (int i = 0; i < SystemInfoUtils.getMarketPartsBean(this).get(position).children.get(0).children.size(); i++) {
                    CategoryBean storeChildBen = new CategoryBean();
                    storeChildBen.setCategory_id(SystemInfoUtils.getMarketPartsBean(this).get(position).children.get(0).children.get(i).category_id);
                    storeChildBen.setCover(SystemInfoUtils.getMarketPartsBean(this).get(position).children.get(0).children.get(i).cover);
                    storeChildBen.setName(SystemInfoUtils.getMarketPartsBean(this).get(position).children.get(0).children.get(i).name);


                    list.add(storeChildBen);
                }

            }


            categoryAdapter.setData(list);

            recommendPresent.commendLoadRecommendData(switcherLayout, SystemInfoUtils.getMarketPartsBean(this).get(position).category_id + "");
        }


        titleBar.setOnClickListener(new View.OnClickListener() {
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
                RecyclerViewStateUtils.resetFooterViewState(recommendView);
                recommendPresent.pullToRefreshRecommendData(RECOMMEND_NUTRITION);
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recommendView);
                recommendPresent.commendLoadRecommendData(switcherLayout, RECOMMEND_NUTRITION);
            }
        });
    }


    private void initRecommendRecyclerView() {
        recommendView = (RecyclerView) findViewById(R.id.rv_recommend);
        nurtureList = new ArrayList<>();
        nurtureAdapter = new NurtureAdapter(this, GOODS_NUTRITION);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(nurtureAdapter);
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
            if (nurtureList != null && nurtureList.size() >= pageSize) {
                recommendPresent.requestMoreRecommendData(recommendView, pageSize, currPage, RECOMMEND_NUTRITION);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<GoodsBean> goodsBeanList) {
        if (refreshLayout.isRefreshing()) {
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
