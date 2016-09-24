package com.example.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.ui.activity.home.adapter.RecommendAdapter;
import com.example.aidong.entity.BrandBean;
import com.example.aidong.entity.GoodsBean;
import com.example.aidong.ui.mvp.presenter.HomePresent;
import com.example.aidong.ui.mvp.presenter.impl.HomePresentImpl;
import com.example.aidong.ui.mvp.view.BrandActivityView;
import com.example.aidong.widget.customview.SimpleTitleBar;
import com.example.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong.widget.endlessrecyclerview.RecyclerViewUtils;

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
    private HeaderAndFooterRecyclerViewAdapter wrapAdapter;
    private RecommendAdapter brandAdapter;
    private HomePresent present;

    private int id;

    /**
     * 跳转到品牌详情界面
     * @param context 来源界面
     * @param id id
     */
    public static void newInstance(Context context, String id){
        Intent intent = new Intent(context,BrandActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
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
    }

    private void intTitleBar(){
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        titleBar.setBackListener(new SimpleTitleBar.OnBackClickListener() {
            @Override
            public void onBack() {
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
                present.pullToRefreshBrandData(id);
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                present.pullToRefreshBrandData(id);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_brand_detail);
        data = new ArrayList<>();
        brandAdapter = new RecommendAdapter(this);
        wrapAdapter = new HeaderAndFooterRecyclerViewAdapter(brandAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapAdapter);
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
