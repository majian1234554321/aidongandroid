package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
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
import java.util.List;

/**
 * 首页品牌详情
 * Created by song on 2016/8/18.
 */
public class BrandActivity extends BaseActivity implements BrandActivityView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private SimpleTitleBar titleBar;
    private SimpleDraweeView dvCover;
    private TextView tvDesc;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private ArrayList<GoodsBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private RecommendAdapter brandAdapter;
    private HomePresent present;

    private String type;
    private String id;
    private String title;
    private String url;
    private String introduce;

    public static void start(Context context, String type, String id, String title, String url,String introduce) {
        Intent starter = new Intent(context, BrandActivity.class);
        starter.putExtra("type",type);
        starter.putExtra("id",id);
        starter.putExtra("title",title);
        starter.putExtra("url",url);
        starter.putExtra("introduce",introduce);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_detail);
        pageSize = 20;
        present = new HomePresentImpl(this,this);
        if(getIntent() != null){
            type = getIntent().getStringExtra("type");
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");
            introduce = getIntent().getStringExtra("introduce");
        }

        initView();
        setListener();
        present.pullToRefreshBrandData(id);
    }

    private void initView(){
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        View headerView = View.inflate(this,R.layout.header_brand_detail,null);
        dvCover = (SimpleDraweeView)headerView.findViewById(R.id.dv_cover);
        tvDesc = (TextView)headerView.findViewById(R.id.tv_brand_desc);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView)findViewById(R.id.rv_brand_detail);
        titleBar.setTitle(title);
        dvCover.setImageURI(url);
        tvDesc.setText(introduce);
        setColorSchemeResources(refreshLayout);
        data = new ArrayList<>();
        brandAdapter = new RecommendAdapter(this,type);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(brandAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter)
                recyclerView.getAdapter(), manager.getSpanCount()));
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recyclerView, headerView);
    }

    private void setListener(){
        titleBar.setOnClickListener(this);
        dvCover.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    //refresh
    @Override
    public void onRefresh() {
        currPage = 1;
        present.pullToRefreshBrandData(id);
    }

    //loading more
    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                present.requestMorBrandeData(recyclerView,pageSize,currPage,id);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_bar:
                finish();
                break;
            case R.id.iv_cover:
                break;
           default:
               break;
        }
    }

    @Override
    public void updateRecyclerView(List<GoodsBean> goodsBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(goodsBeanList);
        brandAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showEndFooterView() {

    }

}
