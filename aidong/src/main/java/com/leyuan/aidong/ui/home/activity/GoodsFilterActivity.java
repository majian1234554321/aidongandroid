package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.GoodsFilterAdapter;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.EquipmentBean;
import com.leyuan.aidong.entity.NurtureBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.view.GoodsFilterView;
import com.leyuan.aidong.ui.mvp.presenter.EquipmentPresent;
import com.leyuan.aidong.ui.mvp.presenter.NurturePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.EquipmentPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.NurturePresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsFilterActivityView;
import com.leyuan.aidong.utils.TransitionHelper;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.GOODS_NUTRITION;


/**
 * 营养品和装备筛选
 * Created by song on 2016/8/18.
 */
public class GoodsFilterActivity extends BaseActivity implements View.OnClickListener,GoodsFilterActivityView {
    private static final String HEAT_DESC = "heat_desc";
    private static final String ORDERS_COUNT_DESC = "orders_count_desc";
    private static final String PRICE_ASC = "price_asc";
    private static final String PRICE_DESC = "price_desc";
    private static final int COMMEND_LOAD_DATA = 3;
    private static final int PULL_TO_REFRESH_DATA = 4;
    private static final int REQUEST_MORE_DATA = 5;

    private ImageView ivBack;
    private TextView tvSearch;
    private GoodsFilterView filterView;
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<NurtureBean> nurtureList;
    private List<EquipmentBean> equipmentList;
    private GoodsFilterAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private String goodsType;
    private int selectedCategoryPosition = 0;
    private List<CategoryBean> categoryBeanList;
    private NurturePresent nurturePresent;
    private EquipmentPresent equipmentPresent;
    private String categoryId;  //分类筛选
    private String sort;        //排序


    public static void start(Context context, String goodsType, ArrayList<CategoryBean> categoryList,int pos) {
        Intent starter = new Intent(context, GoodsFilterActivity.class);
        starter.putExtra("goodsType",goodsType);
        starter.putExtra("pos",pos);
        starter.putExtra("categoryList",categoryList);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_filter);
        nurturePresent = new NurturePresentImpl(this,this);
        equipmentPresent = new EquipmentPresentImpl(this,this);
        if(getIntent() != null){
            goodsType = getIntent().getStringExtra("goodsType");
            selectedCategoryPosition = getIntent().getIntExtra("pos",0);
            categoryBeanList = (List<CategoryBean>) getIntent().getSerializableExtra("categoryList");
            categoryId = categoryBeanList.get(selectedCategoryPosition).getId();
        }

        initTopLayout();
        initSwipeRefreshLayout();
        initRecyclerView();
        getListData(COMMEND_LOAD_DATA);
    }

    private void  initTopLayout(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvSearch = (TextView)findViewById(R.id.tv_search);
        ivBack.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        filterView = (GoodsFilterView)findViewById(R.id.view_filter);
        filterView.setCategoryList(categoryBeanList);
        filterView.setSelectedCategoryPosition(selectedCategoryPosition);
        filterView.setOnFilterClickListener(new GoodsFilterView.OnFilterClickListener() {
            @Override
            public void onCategoryItemClick(int position) {
                categoryId = categoryBeanList.get(position).getId();
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
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListData(PULL_TO_REFRESH_DATA);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_goods);
        equipmentList = new ArrayList<>();
        nurtureList = new ArrayList<>();
        adapter = new GoodsFilterAdapter(this,goodsType);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            getListData(REQUEST_MORE_DATA);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
                Intent intent = new Intent(this,SearchActivity.class);
                final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, false);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
                startActivity(intent,optionsCompat.toBundle());
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
        if(refreshLayout.isRefreshing()){
            equipmentList.clear();
            refreshLayout.setRefreshing(false);
        }
        equipmentList.addAll(equipments);
        adapter.setEquipmentList(equipmentList);
        wrapperAdapter.notifyDataSetChanged();
        switcherLayout.showContentLayout();
    }

    @Override
    public void updateNurtureRecyclerView(List<NurtureBean> beanList) {
        if(refreshLayout.isRefreshing()){
            nurtureList.clear();
            refreshLayout.setRefreshing(false);
        }
        nurtureList.addAll(beanList);
        adapter.setNurtureList(nurtureList);
        wrapperAdapter.notifyDataSetChanged();
        switcherLayout.showContentLayout();
    }

    @Override
    public void showEmptyView() {
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        switcherLayout.showEmptyLayout();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    private void getListData(int operation){
        switch (operation){
            case COMMEND_LOAD_DATA:
                if(GOODS_NUTRITION.equals(goodsType)){
                    nurturePresent.commendLoadNurtureData(switcherLayout, categoryId, sort);
                }else {
                    equipmentPresent.commonLoadEquipmentData(switcherLayout, categoryId, sort);
                }
                break;
            case PULL_TO_REFRESH_DATA:
                currPage = 1;
                refreshLayout.setRefreshing(true);
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                if(GOODS_NUTRITION.equals(goodsType)){
                    nurturePresent.pullToRefreshNurtureData(categoryId,sort);
                }else {
                    equipmentPresent.pullToRefreshEquipmentData(categoryId,sort);
                }
                break;
            case REQUEST_MORE_DATA:
                currPage ++;
                if(GOODS_NUTRITION.equals(goodsType) && nurtureList.size() >= pageSize){
                    nurturePresent.requestMoreNurtureData(recyclerView,pageSize,currPage, categoryId,sort);
                }else if(GOODS_NUTRITION.equals(goodsType) && equipmentList.size() >= pageSize){
                    equipmentPresent.requestMoreEquipmentData(recyclerView,pageSize,currPage, categoryId,sort);
                }
                break;
            default:
                break;
        }
    }
}
