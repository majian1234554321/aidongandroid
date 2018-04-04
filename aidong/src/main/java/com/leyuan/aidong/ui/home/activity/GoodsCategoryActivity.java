package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.CategoryAdapter;
import com.leyuan.aidong.adapter.home.NurtureAdapter;
import com.leyuan.aidong.entity.BaseGoodsBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.GoodsRecommendPresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsActivityView;
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

import static com.leyuan.aidong.utils.Constant.RECOMMEND_NUTRITION;


/**
 * 营养品界面和装备界面 健康餐合成一个界面
 * @author hu
 */
public class GoodsCategoryActivity extends BaseActivity implements GoodsActivityView {
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recommendView;

    private int currPage = 1;
    private List<GoodsBean> nurtureList;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private NurtureAdapter nurtureAdapter;
    private GoodsRecommendPresentImpl recommendPresent;
    private  BaseGoodsBean.GoodsType goodsType;
    public static void start(Context context, BaseGoodsBean.GoodsType type){
        Intent intent = new Intent(context, GoodsCategoryActivity.class);
        intent.putExtra("goodsType",type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodsType = (BaseGoodsBean.GoodsType) getIntent().getSerializableExtra("goodsType");

        setContentView(R.layout.activity_goods_category);
        recommendPresent = new GoodsRecommendPresentImpl(this,this,goodsType);
        initTopLayout();
        initSwipeRefreshLayout();
        initRecommendRecyclerView();
        recommendPresent.commendLoadRecommendData(switcherLayout,RECOMMEND_NUTRITION);
    }

    private void initTopLayout(){
        SimpleTitleBar titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        titleBar.setTitle(BaseGoodsBean.getGoodsNameByType(this,goodsType));
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView categoryView = (RecyclerView)findViewById(R.id.rv_category);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, goodsType);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false);
        categoryView.setLayoutManager(layoutManager);
        categoryView.setAdapter(categoryAdapter);
        categoryAdapter.setData(SystemInfoUtils.getFoodsCategory(this));



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
        nurtureAdapter = new NurtureAdapter(this, Constant.GOODS_NUTRITION);
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
}
