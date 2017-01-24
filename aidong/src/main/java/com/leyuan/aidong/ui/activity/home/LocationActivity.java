package com.leyuan.aidong.ui.activity.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.CityAdapter;
import com.leyuan.aidong.ui.mvp.presenter.HomePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.HomePresentImpl;
import com.leyuan.aidong.ui.mvp.view.LocationActivityView;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;

import java.util.List;

/**
 * 城市切换界面
 * Created by song on 2016/8/23.
 */
public class LocationActivity extends BaseActivity implements LocationActivityView{
    private SimpleTitleBar titleBar;
    private TextView tvLocation;
    private RecyclerView recyclerView;
    private CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);
        HomePresent homePresent = new HomePresentImpl(this,this);
        initView();
        homePresent.getOpenCity();
    }

    private void initView(){
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        recyclerView = (RecyclerView) findViewById(R.id.rv_city);
        cityAdapter = new CityAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cityAdapter);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void setOpenCity(List<String> cities) {
        cityAdapter.setData(cities);
    }
}
