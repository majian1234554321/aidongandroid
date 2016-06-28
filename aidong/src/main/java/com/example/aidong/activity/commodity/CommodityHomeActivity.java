package com.example.aidong.activity.commodity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.CommodityHomeTimeAdapter;
import com.example.aidong.adapter.CommodityHomeTuijianAdapter;
import com.example.aidong.view.MultiGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1 on 2016/6/27.
 */
public class CommodityHomeActivity extends BaseActivity implements View.OnClickListener {
    private TextView txt_title, txt_commodity_home_more;
    private MultiGridView gridview_commodity_home_tuijian;
    private RecyclerView recycler_commodity_home_time;
    private ImageView img_back;


    //来源，由哪里点进来的，1代表营养品，2代表装备，默认营养品
    private int from = 1;

    private List<String> timeList = new ArrayList<String>();
    private List<String> tuijianList = new ArrayList<String>();

    private CommodityHomeTimeAdapter timeAdapter;
    private CommodityHomeTuijianAdapter tuijianAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_home);
        initView();
        initData();
        setClick();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_commodity_home_more = (TextView) findViewById(R.id.txt_commodity_home_more);
        gridview_commodity_home_tuijian = (MultiGridView) findViewById(R.id.gridview_commodity_home_tuijian);
        recycler_commodity_home_time = (RecyclerView) findViewById(R.id.recycler_commodity_home_time);
        recycler_commodity_home_time.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CommodityHomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recycler_commodity_home_time.setLayoutManager(layoutManager);
        recycler_commodity_home_time.setFocusable(true);
        recycler_commodity_home_time.setFocusableInTouchMode(true);
        recycler_commodity_home_time.requestFocus();
    }

    private void initData() {
        from = getIntent().getIntExtra("from", 1);
        switch (from) {
            case 1:
                txt_title.setText(getResources().getString(R.string.txt_commodity_yingyangpin));
                break;
            case 2:
                txt_title.setText(getResources().getString(R.string.txt_commodity_zhaungbei));
                break;
            default:
                break;
        }

        for (int i = 0; i < 5; i++) {
            timeList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
        }
        timeAdapter = new CommodityHomeTimeAdapter(CommodityHomeActivity.this, timeList);
        recycler_commodity_home_time.setAdapter(timeAdapter);

        for (int i = 0; i < 5; i++) {
            tuijianList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
        }
        tuijianAdapter = new CommodityHomeTuijianAdapter(CommodityHomeActivity.this, tuijianList);
        gridview_commodity_home_tuijian.setAdapter(tuijianAdapter);
    }

    private void setClick() {
    }

    @Override
    public void onClick(View v) {

    }
}
