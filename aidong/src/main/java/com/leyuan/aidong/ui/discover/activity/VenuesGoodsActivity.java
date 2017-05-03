package com.leyuan.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.GoodsFilterAdapter;
import com.leyuan.aidong.entity.EquipmentBean;
import com.leyuan.aidong.entity.NurtureBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.EquipmentPresent;
import com.leyuan.aidong.ui.mvp.presenter.NurturePresent;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;

import java.util.List;

import static com.leyuan.aidong.utils.Constant.GOODS_NUTRITION;

/**
 * 场馆商品列表界面
 * Created by song on 2017/4/26.
 */
public class VenuesGoodsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final int PULL_TO_REFRESH_DATA = 1;
    private static final int REQUEST_MORE_DATA = 2;

    private SimpleTitleBar titleBar;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<NurtureBean> nurtureList;
    private List<EquipmentBean> equipmentList;
    private GoodsFilterAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private String goodsType;
    private String venuesId;
    private NurturePresent nurturePresent;
    private EquipmentPresent equipmentPresent;

    public static void start(Context context,String goodsType) {
        Intent starter = new Intent(context, VenuesGoodsActivity.class);
        starter.putExtra("goodsType",goodsType);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues_goods);
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.rv_goods);

        adapter = new GoodsFilterAdapter(this, Constant.GOODS_EQUIPMENT);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    @Override
    public void onRefresh() {
        getListData(PULL_TO_REFRESH_DATA);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            getListData(REQUEST_MORE_DATA);
        }
    };


    private void getListData(int operation){
        switch (operation){
            case PULL_TO_REFRESH_DATA:
                currPage = 1;
                refreshLayout.setRefreshing(true);
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                if(GOODS_NUTRITION.equals(goodsType)){
                    nurturePresent.pullToRefreshNurtureData(venuesId);
                }else {
                    equipmentPresent.pullToRefreshEquipmentData(venuesId);
                }
                break;
            case REQUEST_MORE_DATA:
                currPage ++;
                if(GOODS_NUTRITION.equals(goodsType) && nurtureList.size() >= pageSize){
                    nurturePresent.requestMoreNurtureData(venuesId,recyclerView,pageSize,currPage);
                }else if(GOODS_NUTRITION.equals(goodsType) && equipmentList.size() >= pageSize){
                    equipmentPresent.requestMoreEquipmentData(venuesId,recyclerView,pageSize,currPage);
                }
                break;
            default:
                break;
        }
    }
}
