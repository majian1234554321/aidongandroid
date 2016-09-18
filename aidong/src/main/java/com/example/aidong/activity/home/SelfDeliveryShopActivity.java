package com.example.aidong.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.leyuan.support.widget.customview.SwitcherLayout;

/**
 * 选择自提门店
 * Created by song on 2016/9/14.
 */
public class SelfDeliveryShopActivity extends BaseActivity{
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_delivery);
    }
}
