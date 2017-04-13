package com.leyuan.aidong.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.StoreAdapter;
import com.leyuan.aidong.entity.HomeItemBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.home.activity.SearchActivity;
import com.leyuan.aidong.ui.home.view.StoreHeaderView;
import com.leyuan.aidong.ui.mvp.presenter.HomePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.HomePresentImpl;
import com.leyuan.aidong.ui.mvp.view.StoreFragmentView;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 商城
 * Created by song on 2017/4/12.
 */
public class StoreFragment extends BaseFragment implements StoreFragmentView{
    private ImageView ivSearch;
    private StoreHeaderView headerView;
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<HomeItemBean> data = new ArrayList<>();

    private HomePresent homePresent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homePresent = new HomePresentImpl(getContext(),this);
        initView(view);
        setListener();
        homePresent.commonLoadStoreData(switcherLayout);
    }

    private void initView(View view){
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(),recyclerView);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_goods);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        headerView = new StoreHeaderView(getContext());
        storeAdapter = new StoreAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(storeAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        RecyclerViewUtils.setHeaderView(recyclerView, headerView);
    }


    private void setListener(){
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),SearchActivity.class));
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homePresent.commonLoadStoreData(switcherLayout);
            }
        });
    }


    @Override
    public void updateRecyclerView(List<HomeItemBean> homeBeanList) {
        if (refreshLayout.isRefreshing()) {
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(homeBeanList);
        storeAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }
}
