package com.example.aidong.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.GoodsFilterAdapter;
import com.example.aidong.activity.home.view.GoodsFilterView;
import com.leyuan.support.entity.NurtureBean;
import com.leyuan.support.entity.data.NurtureData;
import com.leyuan.support.mvp.presenter.NurturePresent;
import com.leyuan.support.mvp.presenter.impl.NurturePresentImpl;
import com.leyuan.support.mvp.view.NurtureActivityView;
import com.leyuan.support.widget.customview.SwitcherLayout;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 营养品和装备筛选
 * Created by song on 2016/8/18.
 */
public class GoodsFilterActivity extends BaseActivity implements View.OnClickListener,NurtureActivityView{
    private ImageView ivBack;
    private TextView tvSearch;


    private GoodsFilterView filterView;

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<NurtureData> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private GoodsFilterAdapter nurtureAdapter;
    private NurturePresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_filter);
        pageSize = 20;
        present = new NurturePresentImpl(this,this);

        initTopLayout();
        initSwipeRefreshLayout();
        initRecyclerView();
        present.commonLoadData(switcherLayout);
    }

    private void  initTopLayout(){
        tvSearch = (TextView)findViewById(R.id.et_search);

        filterView = (GoodsFilterView)findViewById(R.id.view_filter);
        filterView.setCategoryList(Arrays.asList(getResources().getStringArray(R.array.characterTag)));
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
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
               // present.pullToRefreshHomeData();
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
               // refreshLayout.setRefreshing(true);
              //  present.pullToRefreshHomeData();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_goods);
        recyclerView.requestFocus();
        data = new ArrayList<>();
        nurtureAdapter = new GoodsFilterAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(nurtureAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && !data.isEmpty()) {
                //present.requestMoreHomeData(recyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){


            default:
                break;
        }
    }

    @Override
    public void updateRecyclerView(List<NurtureBean> nurtureBeanList) {

    }

    @Override
    public void showEndFooterView() {

    }
}
