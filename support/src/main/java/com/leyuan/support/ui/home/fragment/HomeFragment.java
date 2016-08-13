package com.leyuan.support.ui.home.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.support.R;
import com.leyuan.support.entity.VenuesBean;
import com.leyuan.support.ui.BaseFragment;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页
 * Created by Song on 2016/8/1.
 */
public class HomeFragment extends BaseFragment {

    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private List<VenuesBean> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_discover_venues,null);
        initRefreshLayout();
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        data = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_venues);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        // RecyclerViewUtils.setHeaderView(mRecyclerView, new HomeHeadView(mContext));
    }


    private void initRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.clear();

            }
        });
    }
}
