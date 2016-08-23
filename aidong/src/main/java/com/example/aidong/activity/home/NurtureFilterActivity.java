package com.example.aidong.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.NurtureFilterAdapter;
import com.leyuan.support.entity.NurtureDataBean;
import com.leyuan.support.mvp.presenter.NurtureActivityPresent;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品筛选
 * Created by song on 2016/8/18.
 */
public class NurtureFilterActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout categoryLayout;
    private TextView tvCategory;
    private ImageView ivCategory;
    private TextView tvHot;
    private TextView tvSales;
    private RelativeLayout priceLayout;
    private TextView tvPrice;
    private ImageView ivPrice;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<NurtureDataBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapAdapter;
    private NurtureFilterAdapter nurtureAdapter;
    private NurtureActivityPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurture_filter);
        initTopLayout();
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void  initTopLayout(){
        categoryLayout = (RelativeLayout)findViewById(R.id.category);
        tvCategory = (TextView)findViewById(R.id.tv_category);
        ivCategory = (ImageView)findViewById(R.id.iv_category);
        tvHot = (TextView)findViewById(R.id.hot);
        tvSales = (TextView)findViewById(R.id.sales);
        priceLayout = (RelativeLayout)findViewById(R.id.price);
        tvPrice = (TextView)findViewById(R.id.tv_price);
        ivPrice = (ImageView)findViewById(R.id.iv_price);

        categoryLayout.setOnClickListener(this);
        tvHot.setOnClickListener(this);
        tvSales.setOnClickListener(this);
        priceLayout.setOnClickListener(this);
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                present.pullToRefreshData(recyclerView);
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                present.pullToRefreshData(recyclerView);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_nurture);
        data = new ArrayList<>();
        nurtureAdapter = new NurtureFilterAdapter();
        wrapAdapter = new HeaderAndFooterRecyclerViewAdapter(nurtureAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && !data.isEmpty()) {
                present.requestMoreData(recyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.category:
                break;
            case R.id.hot:
                break;
            case R.id.sales:
                break;
            case R.id.price:
                break;
            default:
                break;
        }
    }
}
