package com.example.aidong.activity.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.discover.adapter.DynamicAdapter;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

/**
 * 发现
 * Created by song on 2016/8/29.
 */
public class DiscoverActivity extends BaseActivity{
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView  recyclerView;
    private DynamicAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
    }


}
