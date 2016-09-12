package com.example.aidong.activity.mine.shopcart;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.RecommendAdapter;
import com.example.aidong.activity.mine.adapter.CartShopAdapter;
import com.leyuan.support.entity.GoodsBean;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public class CartActivity extends BaseActivity{
    private View headerView;
    private RecyclerView shopRecyclerView;
    private CartShopAdapter shopAdapter;


    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private List<GoodsBean> data;
    private RecommendAdapter recommendAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initHeaderView();
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void initHeaderView() {
        headerView = View.inflate(this,R.layout.header_cart,null);
        shopRecyclerView = (RecyclerView) headerView.findViewById(R.id.rv_cart_header);
        shopRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shopAdapter = new CartShopAdapter(this);
        shopRecyclerView.setAdapter(shopAdapter);
    }

    private void initSwipeRefreshLayout(){
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_goods);
        data = new ArrayList<>();
        recommendAdapter = new RecommendAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(recommendAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(wrapperAdapter);
        RecyclerViewUtils.setHeaderView(recyclerView,headerView);
        RecyclerViewUtils.setFooterView(recyclerView,View.inflate(this,R.layout.list_footer_end,null));
    }
}
