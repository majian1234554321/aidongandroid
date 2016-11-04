package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BrandBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.RecommendAdapter;
import com.leyuan.aidong.ui.mvp.presenter.HomePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.HomePresentImpl;
import com.leyuan.aidong.ui.mvp.view.BrandActivityView;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;

import java.util.ArrayList;

/**
 * 首页品牌详情
 * Created by song on 2016/8/18.
 */
public class BrandActivity extends BaseActivity implements BrandActivityView {
    private View headerView;
    private ImageView ivCover;
    private TextView tvDesc;

    private SimpleTitleBar titleBar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private int currPage = 1;
    private ArrayList<GoodsBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private RecommendAdapter brandAdapter;
    private HomePresent present;

    private int id;

    public static void start(Context context, String id) {
        Intent starter = new Intent(context, BrandActivity.class);
        starter.putExtra("id",id);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_detail);
        pageSize = 20;
        present = new HomePresentImpl(this,this);
        intTitleBar();
        initHeaderView();
        initSwipeRefreshLayout();
        initRecyclerView();
        brandAdapter.setData(null);
        wrapperAdapter.notifyDataSetChanged();
    }

    private void intTitleBar(){
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initHeaderView(){
        headerView = View.inflate(this,R.layout.header_brand_detail,null);
        ivCover = (ImageView)headerView.findViewById(R.id.iv_brand_cover);
        tvDesc = (TextView)headerView.findViewById(R.id.tv_desc);
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
               // present.pullToRefreshBrandData(id);
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
               // refreshLayout.setRefreshing(true);
                //present.pullToRefreshBrandData(id);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_brand_detail);
        data = new ArrayList<>();
        brandAdapter = new RecommendAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(brandAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter)
                recyclerView.getAdapter(), manager.getSpanCount()));
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recyclerView, headerView);
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && !data.isEmpty()) {
                present.requestMorBrandeData(recyclerView,pageSize,currPage,id);
            }
        }
    };

    @Override
    public void updateRecyclerView(BrandBean brandBean) {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showEndFooterView() {

    }
}
