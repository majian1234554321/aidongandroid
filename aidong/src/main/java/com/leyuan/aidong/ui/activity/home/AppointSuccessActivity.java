package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.ui.activity.home.adapter.RecommendAdapter;
import com.leyuan.aidong.ui.activity.mine.AppointmentActivity;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;

/**
 * 预约成功界面
 * Created by song on 2016/9/12.
 */
public class AppointSuccessActivity extends BaseActivity implements View.OnClickListener{
    private TextView tvTime;
    private TextView returnHome;
    private TextView checkAppointment;

    private SimpleTitleBar titleBar;
    private RecyclerView recyclerView;
    private RecommendAdapter recommendAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private String time;

    private void start(Context context,String time){
        Intent intent = new Intent(context,AppointSuccessActivity.class);
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
        setListener();
    }

    private void initView() {
        View headerView = View.inflate(this,R.layout.header_appointment_success,null);
        tvTime = (TextView) headerView.findViewById(R.id.tv_time);
       // tvTime.setText(time);
        returnHome = (TextView) headerView.findViewById(R.id.tv_home);
        checkAppointment = (TextView) headerView.findViewById(R.id.tv_appointment);
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recommend);
        recommendAdapter = new RecommendAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(recommendAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter)
                recyclerView.getAdapter(), manager.getSpanCount()));
        recyclerView.setLayoutManager(manager);
        RecyclerViewUtils.setHeaderView(recyclerView,headerView);
    }

    private void setListener(){
        returnHome.setOnClickListener(this);
        checkAppointment.setOnClickListener(this);
        titleBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
            case R.id.tv_home:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_appointment:
                startActivity(new Intent(this, AppointmentActivity.class));
                break;
            default:
                break;
        }
    }
}
