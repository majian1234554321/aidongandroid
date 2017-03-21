package com.leyuan.aidong.ui.home.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.CityAdapter;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.HomePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.HomePresentImpl;
import com.leyuan.aidong.ui.mvp.view.LocationActivityView;
import com.leyuan.aidong.widget.SimpleTitleBar;

import java.util.List;

/**
 * 城市切换界面
 * Created by song on 2016/8/23.
 */
public class LocationActivity extends BaseActivity implements LocationActivityView {
    private SimpleTitleBar titleBar;
    private TextView tvLocation;
    private RecyclerView recyclerView;
    private CityAdapter cityAdapter;
    private ImageView img_selected;
    private RelativeLayout layout_location_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);
        HomePresent homePresent = new HomePresentImpl(this, this);
        initView();
        homePresent.getOpenCity();
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        layout_location_city = (RelativeLayout) findViewById(R.id.layout_location_city);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        recyclerView = (RecyclerView) findViewById(R.id.rv_city);
        img_selected = (ImageView) findViewById(R.id.img_selected);
        tvLocation.setText(App.getInstance().getLocationCity());
        img_selected.setVisibility(TextUtils.equals(App.getInstance().getLocationCity(), App.getInstance().getSelectedCity()) ?
                View.VISIBLE : View.GONE);

        cityAdapter = new CityAdapter(this);
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
    public void setOpenCity(final List<String> cities) {
        cityAdapter.setData(cities);
        layout_location_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
