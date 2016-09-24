package com.example.aidong.ui.activity.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.ui.activity.discover.adapter.DynamicAdapter;
import com.example.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

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
