package com.example.aidong.ui.activity.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.ui.activity.home.adapter.RecommendAdapter;
import com.example.aidong.ui.activity.mine.adapter.CartShopAdapter;
import com.example.aidong.entity.GoodsBean;
import com.example.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong.widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.example.aidong.widget.endlessrecyclerview.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public class CartActivity extends BaseActivity{
    private View headerView;
    private RecyclerView shopView;
    private CartShopAdapter shopAdapter;


    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recommendView;

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
        shopView = (RecyclerView) headerView.findViewById(R.id.rv_cart_header);
        shopView.setLayoutManager(new LinearLayoutManager(this));
        shopAdapter = new CartShopAdapter(this);
        shopView.setAdapter(shopAdapter);
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
        recommendView = (RecyclerView)findViewById(R.id.rv_goods);
        data = new ArrayList<>();
        recommendAdapter = new RecommendAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(recommendAdapter);
        recommendView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) recommendView.getAdapter(), manager.getSpanCount()));
        recommendView.setLayoutManager(manager);
        RecyclerViewUtils.setHeaderView(recommendView,headerView);
        RecyclerViewUtils.setFooterView(recommendView,View.inflate(this,R.layout.list_footer_end,null));
    }
}
