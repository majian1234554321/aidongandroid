package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.home.adapter.RecommendAdapter;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;

/**
 * 预约成功界面
 * Created by song on 2016/9/12.
 */
public class AppointmentSuccessActivity extends BaseActivity implements View.OnClickListener{
    private TextView tvTime;
    private TextView returnHome;
    private TextView checkAppointment;

    private SimpleTitleBar titleBar;
    private RecyclerView recyclerView;
    private RecommendAdapter recommendAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private String time;

    private void newInstance(Context context,String time){
        Intent intent = new Intent(context,AppointmentSuccessActivity.class);
        intent.putExtra("time",time);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_success);

        if(getIntent() != null){
            time = getIntent().getStringExtra("time");
        }
        initView();
    }

    private void initView() {
        View headerView = View.inflate(this,R.layout.header_appointment_success,null);
        tvTime = (TextView) headerView.findViewById(R.id.tv_time);
        returnHome = (TextView) headerView.findViewById(R.id.tv_home);
        checkAppointment = (TextView) headerView.findViewById(R.id.tv_appointment);

        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recommend);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recommendAdapter = new RecommendAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(recommendAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        RecyclerViewUtils.setHeaderView(recyclerView,headerView);

        tvTime.setText(time);
        returnHome.setOnClickListener(this);
        checkAppointment.setOnClickListener(this);

        titleBar.setBackListener(new SimpleTitleBar.OnBackClickListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_home:
                break;
            case R.id.tv_appointment:
                break;
            default:
                break;
        }
    }
}
