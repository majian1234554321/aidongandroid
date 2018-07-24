package com.example.aidong.ui.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.adapter.GoodsListAdapter;
import com.example.aidong.entity.BaseGoodsBean;
import com.example.aidong.entity.CategoryBean;
import com.example.aidong.entity.EquipmentBean;
import com.example.aidong.entity.GoodsBean;
import com.example.aidong.entity.NurtureBean;
import com.example.aidong.ui.BaseActivity;
import com.example.aidong.ui.home.view.GoodsFilterView;
import com.example.aidong.ui.mvp.presenter.GoodsListPrensetImpl;
import com.example.aidong.ui.mvp.view.GoodsFilterActivityView;
import com.example.aidong.utils.Constant;
import com.example.aidong.utils.Logger;
import com.example.aidong.utils.SystemInfoUtils;
import com.example.aidong.utils.TransitionHelper;
import com.example.aidong.widget.SwitcherLayout;
import com.example.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong.widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 营养品和装备筛选
 * Created by song on 2016/8/18.
 */
public class GoodsListActivity2 extends BaseActivity implements View.OnClickListener, GoodsFilterActivityView {
    private static final String HEAT_DESC = "heat_desc";
    private static final String ORDERS_COUNT_DESC = "orders_count_desc";
    private static final String PRICE_ASC = "floor_price_asc";
    private static final String PRICE_DESC = "floor_price_desc";
    private static final int COMMEND_LOAD_DATA = 3;
    private static final int PULL_TO_REFRESH_DATA = 4;
    private static final int REQUEST_MORE_DATA = 5;

    private TextView tvTitle;
    private ImageView ivBack;
    private TextView tvSearch;
    private GoodsFilterView filterView;
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<GoodsBean> goodsArray;
    private GoodsListAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private String goodsType;
    private int selectedCategoryPosition = 0;

    private GoodsListPrensetImpl goodsListPrenset;

    private List<CategoryBean> categoryBeans;

    private String gymName;
    private String categoryId;  //分类筛选
    private String sort;        //排序
    private String gymId;

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
    private int selectPoistion;

    //从营养品或装备界面跳过来
    public static void start(Context context, String goodsType, int pos) {
        Intent starter = new Intent(context, GoodsListActivity2.class);
        starter.putExtra("goodsType", goodsType);
        starter.putExtra("pos", pos);

        context.startActivity(starter);
    }

    //从场馆详情跳过来
    public static void start(Context context, String goodsType, String gymName, String gymId) {
        Intent starter = new Intent(context, GoodsListActivity2.class);
        starter.putExtra("goodsType", goodsType);
        starter.putExtra("gymName", gymName);
        starter.putExtra("gymId", gymId);
        context.startActivity(starter);
    }


    public static void start(Context context, String goodsType, int gymId, int selectPosition, String categoryId) {
        Intent starter = new Intent(context, GoodsListActivity2.class);
        starter.putExtra("goodsType", goodsType);
        starter.putExtra("pos", gymId);
        starter.putExtra("selectPoistion", selectPosition);
        starter.putExtra("categoryId", categoryId);
        context.startActivity(starter);
    }


    public static void start(Context context, String goodsType, int pos, int selectPosition, String categoryId, String gymId) {
        Intent starter = new Intent(context, GoodsListActivity2.class);
        starter.putExtra("goodsType", goodsType);
        starter.putExtra("pos", pos);
        starter.putExtra("selectPoistion", selectPosition);
        starter.putExtra("categoryId", categoryId);
        starter.putExtra("gymId", gymId);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBroadCastReceiver();
        setContentView(R.layout.activity_goods_filter);

        if (getIntent() != null) {
            gymName = getIntent().getStringExtra("gymName");

            selectPoistion = getIntent().getIntExtra("selectPoistion", 0);

            categoryId = getIntent().getStringExtra("categoryId");
            gymId = getIntent().getStringExtra("gymId");


            if (!TextUtils.isEmpty(gymName)) {
                gymId = getIntent().getStringExtra("gymId");
                goodsType = getIntent().getStringExtra("goodsType");
            } else {
                goodsType = getIntent().getStringExtra("goodsType");
                selectedCategoryPosition = getIntent().getIntExtra("pos", 0);
            }
        }


        goodsListPrenset = new GoodsListPrensetImpl(this, this, goodsType);

        initFilterLayout();
        initSwipeRefreshLayout();
        initRecyclerView();
        getListData(COMMEND_LOAD_DATA);
    }

    private void initBroadCastReceiver() {

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_FAIL);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    private void initFilterLayout() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvSearch = (TextView) findViewById(R.id.tv_search);
        ivBack.setOnClickListener(this);
        tvSearch.setOnClickListener(this);


        filterView = (GoodsFilterView) findViewById(R.id.view_filter);


        if (SystemInfoUtils.getMarketPartsBean(this) != null
                && SystemInfoUtils.getMarketPartsBean(this).size() > 0
                && SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion) != null
                && SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion).children != null
                && SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion).children.size()>0
                && SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion).children.get(0)!=null
                && SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion).children.get(0).children != null

                ) {
            List<CategoryBean> list = new ArrayList<>();


            for (int i = 0; i < SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion).children.size(); i++) {
                CategoryBean storeChildBen = new CategoryBean();
                storeChildBen.setCategory_id(SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion).children.get(i).category_id);
                storeChildBen.setCover(SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion).children.get(i).cover);
                storeChildBen.setName(SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion).children.get(i).name);

                list.add(storeChildBen);
            }

            for (int i = 0; i < SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion).children.get(0).children.size(); i++) {
                CategoryBean storeChildBen = new CategoryBean();
                storeChildBen.setCategory_id(SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion).children.get(0).children.get(i).category_id);
                storeChildBen.setCover(SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion).children.get(0).children.get(i).cover);
                storeChildBen.setName(SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion).children.get(0).children.get(i).name);


                list.add(storeChildBen);
            }
            tvTitle.setText(SystemInfoUtils.getMarketPartsBean(this).get(selectPoistion).name);
            filterView.setCategoryList(list);
            filterView.setSelectedCategoryPosition(selectedCategoryPosition);
        }


        filterView.setOnFilterClickListener(new GoodsFilterView.OnFilterClickListener() {
            @Override
            public void onCategoryItemClick(String id) {
                categoryId = id;
                getListData(PULL_TO_REFRESH_DATA);
            }

            @Override
            public void onPopularityClick() {
                sort = HEAT_DESC;
                getListData(PULL_TO_REFRESH_DATA);
            }

            @Override
            public void onSaleClick() {
                sort = ORDERS_COUNT_DESC;
                getListData(PULL_TO_REFRESH_DATA);
            }

            @Override
            public void onPriceClick(boolean low2High) {
                sort = low2High ? PRICE_ASC : PRICE_DESC;
                getListData(PULL_TO_REFRESH_DATA);
            }
        });
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this, refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListData(PULL_TO_REFRESH_DATA);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_goods);
        goodsArray = new ArrayList<>();

        adapter = new GoodsListAdapter(this, goodsType);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private String TAG = "GoodsListActivity";
    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {

            Logger.i(TAG, "onLoadNextPage");
            getListData(REQUEST_MORE_DATA);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                Intent intent = new Intent(this, SearchActivity.class);
                final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, false);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
                startActivity(intent, optionsCompat.toBundle());
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void updateEquipmentRecyclerView(List<EquipmentBean> equipments) {
    }

    @Override
    public void updateGoodsRecyclerView(List<GoodsBean> beanList) {

        Logger.i(TAG, "updateGoodsRecyclerView is refresh : " + refreshLayout.isRefreshing());
        if (refreshLayout.isRefreshing()) {

            goodsArray.clear();
            refreshLayout.setRefreshing(false);
            Logger.i(TAG, "goodsArray.clear(); ");
        }
        goodsArray.addAll(beanList);
        adapter.setGoodsData(goodsArray);
        wrapperAdapter.notifyDataSetChanged();
        switcherLayout.showContentLayout();
        Logger.i(TAG, "goodsArray.size : " + goodsArray.size());
    }

    @Override
    public void updateNurtureRecyclerView(List<NurtureBean> beanList) {
    }

    @Override
    public void showEmptyView() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        View view = View.inflate(this, R.layout.empty_search, null);
        switcherLayout.addCustomView(view, "empty");
        switcherLayout.showCustomLayout("empty");
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    private void getListData(int operation) {
        switch (operation) {
            case COMMEND_LOAD_DATA:
                goodsListPrenset.commendLoadGoodsData2(switcherLayout, categoryId, sort, gymId);

                break;
            case PULL_TO_REFRESH_DATA:
                currPage = 1;
                refreshLayout.setRefreshing(true);
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                goodsListPrenset.pullToRefreshGoodsData2(categoryId, sort, gymId);

                break;
            case REQUEST_MORE_DATA:
                Logger.i(TAG, "getListData case REQUEST_MORE_DATA");
                currPage++;
                if (goodsArray.size() >= pageSize) {
                    goodsListPrenset.requestMoreGoodsData2(recyclerView, pageSize, currPage, categoryId, sort, gymId);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
