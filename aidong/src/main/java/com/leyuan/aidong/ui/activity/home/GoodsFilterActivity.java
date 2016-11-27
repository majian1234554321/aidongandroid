package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.EquipmentBean;
import com.leyuan.aidong.entity.NurtureBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.GoodsFilterAdapter;
import com.leyuan.aidong.ui.activity.home.view.GoodsFilterView;
import com.leyuan.aidong.ui.mvp.presenter.EquipmentPresent;
import com.leyuan.aidong.ui.mvp.presenter.NurturePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.EquipmentPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.NurturePresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsFilterActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品和装备筛选
 * Created by song on 2016/8/18.
 */
public class GoodsFilterActivity extends BaseActivity implements View.OnClickListener,GoodsFilterActivityView {
    public static final int TYPE_NURTURE = 1;       //营养品
    public static final int TYPE_EQUIPMENT = 2;     //装备

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

    private int type = 1;
    private int selectedPosition = 0;
    private List<CategoryBean> categoryBeanList;
    private NurturePresent nurturePresent;
    private EquipmentPresent equipmentPresent;

    public static void start(Context context, int type, ArrayList<CategoryBean> categoryList,int pos) {
        Intent starter = new Intent(context, GoodsFilterActivity.class);
        starter.putExtra("type",type);
        starter.putExtra("position",pos);
        starter.putParcelableArrayListExtra("categoryList",categoryList);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_filter);
        pageSize = 20;
        nurturePresent = new NurturePresentImpl(this,this);
        equipmentPresent = new EquipmentPresentImpl(this,this);
        if(getIntent() != null){
            type = getIntent().getIntExtra("type",TYPE_NURTURE);
            selectedPosition = getIntent().getIntExtra("position",0);
            categoryBeanList = getIntent().getParcelableArrayListExtra("categoryList");
        }

        initTopLayout();
        initSwipeRefreshLayout();
        initRecyclerView();

        if(type == TYPE_NURTURE){
            nurturePresent.commendLoadNurtureData(switcherLayout);
        }else {
            equipmentPresent.commonLoadEquipmentData(switcherLayout);
        }
    }

    private void  initTopLayout(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvSearch = (TextView)findViewById(R.id.tv_search);
        filterView = (GoodsFilterView)findViewById(R.id.view_filter);
        filterView.setCategoryList(categoryBeanList);
        filterView.setSelectedCategoryPosition(selectedPosition);
        filterView.setOnFilterClickListener(new GoodsFilterView.OnFilterClickListener() {
            @Override
            public void onCategoryItemClick(String category) {
                Toast.makeText(GoodsFilterActivity.this,""+category,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPopularityClick() {

            }

            @Override
            public void onSaleClick() {

            }

            @Override
            public void onPriceClick(boolean low2High) {
                Toast.makeText(GoodsFilterActivity.this,""+low2High,Toast.LENGTH_SHORT).show();
            }
        });

        tvSearch.setOnClickListener(this);
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                if(type == TYPE_NURTURE){
                    nurturePresent.pullToRefreshNurtureData();
                }else {
                    equipmentPresent.pullToRefreshEquipmentData();
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_goods);
        equipmentList = new ArrayList<>();
        nurtureList = new ArrayList<>();
        adapter = new GoodsFilterAdapter(this,type == TYPE_NURTURE ? TYPE_NURTURE : TYPE_EQUIPMENT);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if(type == TYPE_NURTURE && nurtureList != null && !nurtureList.isEmpty()){
                nurturePresent.requestMoreNurtureData(recyclerView,pageSize,currPage);
            }else if(type == TYPE_EQUIPMENT && equipmentList != null && !equipmentList.isEmpty()){
                equipmentPresent.requestMoreEquipmentData(recyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void updateEquipmentRecyclerView(List<EquipmentBean> equipmentList) {
        if(refreshLayout.isRefreshing()){
            equipmentList.clear();
            refreshLayout.setRefreshing(false);
        }
        equipmentList.addAll(equipmentList);
        adapter.setEquipmentList(equipmentList);
        wrapperAdapter.notifyDataSetChanged();
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
    }

    @Override
    public void showEndFooterView() {

    }
}
