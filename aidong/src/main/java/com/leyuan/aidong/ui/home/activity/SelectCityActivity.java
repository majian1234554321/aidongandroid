package com.leyuan.aidong.ui.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.adapter.home.CityAdapter;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.widget.SimpleTitleBar;

import java.util.ArrayList;

/**
 * 城市切换界面
 * Created by song on 2016/8/23.
 */
public class SelectCityActivity extends BaseActivity implements CityAdapter.OnCitySelectListener {
    private SimpleTitleBar titleBar;
    private TextView tvLocation;
    private RecyclerView recyclerView;
    private CityAdapter cityAdapter;
    private ImageView img_selected;
    String selectedCity;
    ArrayList<String> citys;

    public static void startForResult(Activity context, int requestCode, String selectedCity, ArrayList<String> citys) {

        Intent intent = new Intent(context, SelectCityActivity.class);

        intent.putExtra("selectedCity", selectedCity);
        intent.putStringArrayListExtra("citys", citys);
        context.startActivityForResult(intent,requestCode);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedCity = getIntent().getStringExtra("selectedCity");
        citys = getIntent().getStringArrayListExtra("citys");

        setContentView(R.layout.activity_change_city);
        initView();
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        recyclerView = (RecyclerView) findViewById(R.id.rv_city);
        img_selected = (ImageView) findViewById(R.id.img_selected);
        tvLocation.setText(selectedCity);
        img_selected.setVisibility(View.VISIBLE);

        cityAdapter = new CityAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cityAdapter);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cityAdapter.setData(citys);
    }


    @Override
    public void onCitySelect(String str) {
        Intent intent = new Intent();
        intent.putExtra("selectedCity",str);

        setResult(RESULT_OK,intent);
        finish();
    }

}
