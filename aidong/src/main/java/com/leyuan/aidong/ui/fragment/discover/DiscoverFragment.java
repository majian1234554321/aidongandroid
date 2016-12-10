package com.leyuan.aidong.ui.fragment.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseFragment;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 发现 -- 发现
 * Created by song on 2016/11/19.
 */
public class DiscoverFragment extends BaseFragment{
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private BGABanner bannerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    private void initView(View view) {
        View headerView = View.inflate(getContext(), R.layout.header_discover,null);
        bannerLayout = (BGABanner) headerView.findViewById(R.id.banner);
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_discover);

    }
}
