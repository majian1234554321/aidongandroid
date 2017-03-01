package com.leyuan.aidong.ui.mine.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.widget.SimpleTitleBar;

/**
 * 系统消息
 * Created by song on 2016/11/2.
 */
public class SystemMessageActivity extends BaseActivity{
    private SimpleTitleBar titleBar;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);

        initView();
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        rvMessage = (RecyclerView) findViewById(R.id.rv_message);
    }
}