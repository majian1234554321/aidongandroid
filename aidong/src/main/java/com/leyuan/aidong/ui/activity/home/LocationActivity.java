package com.leyuan.aidong.ui.activity.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;


/**
 * 切换地址
 * Created by song on 2016/11/7.
 */
public class LocationActivity extends BaseActivity{
    private SimpleTitleBar tvTitle;
    private TextView tvLocation;
    private RecyclerView rvCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        tvTitle = (SimpleTitleBar) findViewById(R.id.title_bar);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        rvCity = (RecyclerView) findViewById(R.id.rv_city);

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
