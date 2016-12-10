package com.leyuan.aidong.ui.activity.discover;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.discover.adapter.DateAdapter;
import com.leyuan.aidong.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 预约场馆
 * 上午十点之前,下午三点之前可预约,若今天不可预约日期从明天开始
 * Created by song on 2016/10/26.
 */
public class AppointVenuesActivity extends BaseActivity implements View.OnClickListener, DateAdapter.ItemClickListener {
    private TextView tvVenuesName;
    private TextView tvAddress;
    private ImageView ivPhone;
    private RecyclerView rvDate;
    private TextView tvMorning;
    private TextView tvAfternoon;
    private TextView tvUsername;
    private TextView tvPhone;

    private DateAdapter dateAdapter;
    private String venuesName;
    private String venuesAddress;
    private String phone;

    private  List<String> days = new ArrayList<>();
    private int selectedPosition = 0;
    private String appointData;

    private boolean isMorningUnable = false;
    private boolean isAfternoonUnable = false;

    public static void start(Context context,String name,String address,String phone) {
        Intent starter = new Intent(context, AppointVenuesActivity.class);
        starter.putExtra("name",name);
        starter.putExtra("address",address);
        starter.putExtra("phone",phone);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_venues);
        if(getIntent() != null){
            venuesName = getIntent().getStringExtra("name");
            venuesAddress = getIntent().getStringExtra("address");
            phone = getIntent().getStringExtra("phone");
        }

        initView();
        setListener();
    }

    private void initView() {
        tvVenuesName = (TextView) findViewById(R.id.tv_name);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        ivPhone = (ImageView) findViewById(R.id.iv_phone);
        rvDate = (RecyclerView) findViewById(R.id.rv_date);
        tvMorning = (TextView) findViewById(R.id.tv_morning);
        tvAfternoon = (TextView) findViewById(R.id.tv_afternoon);
        tvUsername = (TextView) findViewById(R.id.et_username);
        tvPhone = (TextView) findViewById(R.id.et_phone);
        rvDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dateAdapter = new DateAdapter();
        rvDate.setAdapter(dateAdapter);
        days = DateUtils.getSevenDate();
        dateAdapter.setData(days);

        setButtonStatus();

        tvVenuesName.setText(venuesName);
        tvAddress.setText(venuesAddress);
        tvUsername.setText(App.mInstance.getUser().getName());
        tvPhone.setText(App.mInstance.getUser().getMobile());
    }

    private void setButtonStatus() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if(hour < 9){           //上午9点以前 默认选中上午
            selectMorning();
            enableAfternoon();
            isMorningUnable = false;
            isAfternoonUnable = false;
        }else if(hour < 15){    //下午3点以前 上午不可选,默认选中下午
            unableMorning();
            selectAfternoon();
            isMorningUnable = true;
            isAfternoonUnable = false;
        }else {                 //当天都不可选,默认选中第二天上午
            unableMorning();
            unableAfternoon();
            isMorningUnable = true;
            isAfternoonUnable = true;
            selectedPosition = 1;
            dateAdapter.setSelectedPosition(selectedPosition);
        }
    }

    private void setListener() {
        ivPhone.setOnClickListener(this);
        tvMorning.setOnClickListener(this);
        tvAfternoon.setOnClickListener(this);
        dateAdapter.setItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_phone:

                break;
            case R.id.tv_morning:
                if(isMorningUnable ){
                    Toast.makeText(AppointVenuesActivity.this,"今天上午已不能预约",Toast.LENGTH_LONG).show();
                    return;
                }
                selectMorning();
                if(!isAfternoonUnable){
                    enableAfternoon();
                }
                break;
            case R.id.tv_afternoon:
                if(isAfternoonUnable ){
                    Toast.makeText(AppointVenuesActivity.this,"下午已不能预约",Toast.LENGTH_LONG).show();
                    return;
                }
                selectAfternoon();
                if(!isMorningUnable){
                    enableMorning();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(AppointVenuesActivity.this,"选中第" + (position + 1),Toast.LENGTH_LONG).show();
        selectedPosition = position;
        appointData = days.get(position);
        if(position > 0){
            selectMorning();
            enableAfternoon();
            isMorningUnable = false;
            isAfternoonUnable = false;
        }else {
            setButtonStatus();
        }
    }

    private void selectMorning(){
        tvMorning.setTextColor(Color.parseColor("#ffffff"));
        tvMorning.setBackgroundResource(R.drawable.shape_solid_black);
    }

    private void enableMorning(){
        tvMorning.setTextColor(Color.parseColor("#000000"));
        tvMorning.setBackgroundResource(R.drawable.shape_stroke_white);
    }

    private void unableMorning(){
        tvMorning.setTextColor(Color.parseColor("#ebebeb"));
        tvMorning.setBackgroundResource(R.drawable.shape_stroke_gray);
    }

    private void selectAfternoon(){
        tvAfternoon.setTextColor(Color.parseColor("#ffffff"));
        tvAfternoon.setBackgroundResource(R.drawable.shape_solid_black);
    }

    private void enableAfternoon(){
        tvAfternoon.setTextColor(Color.parseColor("#000000"));
        tvAfternoon.setBackgroundResource(R.drawable.shape_stroke_white);
    }

    private void unableAfternoon(){
        tvAfternoon.setTextColor(Color.parseColor("#ebebeb"));
        tvAfternoon.setBackgroundResource(R.drawable.shape_stroke_gray);
    }

}
