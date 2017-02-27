package com.leyuan.aidong.ui.discover.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.adapter.discover.DateAdapter;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointVenuesActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.widget.SimpleTitleBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



/**
 * 预约场馆
 * 上午九点之前,下午三点之前可预约,若今天不可预约日期从明天开始
 * Created by song on 2016/10/26.
 */
public class AppointVenuesActivity extends BaseActivity implements View.OnClickListener, DateAdapter.ItemClickListener ,AppointVenuesActivityView{
    private static final String MORNING = "0";
    private static final String AFTERNOON = "1";

    private SimpleTitleBar titleBar;
    private TextView tvVenuesName;
    private TextView tvAddress;
    private ImageView ivPhone;
    private RecyclerView rvDate;
    private TextView tvMorning;
    private TextView tvAfternoon;
    private TextView tvUsername;
    private TextView tvUserPhone;
    private TextView tvAppoint;

    private DateAdapter dateAdapter;
    private String venuesName;
    private String venuesAddress;
    private String venuesPhone;

    private List<String> days = new ArrayList<>();
    private int selectedPosition = 0;
    private boolean isMorningUnable = false;
    private boolean isAfternoonUnable = false;

    private String venuesId;
    private String appointDate;
    private String appointPeriod;
    private String userName;
    private String userPhone;

    private VenuesPresent venuesPresent;

    public static void start(Context context, String venuesId,String name, String address, String phone) {
        Intent starter = new Intent(context, AppointVenuesActivity.class);
        starter.putExtra("venuesId", venuesId);
        starter.putExtra("name", name);
        starter.putExtra("address", address);
        starter.putExtra("venuesPhone", phone);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_venues);
        venuesPresent = new VenuesPresentImpl(this,this);
        userName = App.mInstance.getUser().getName();
        userPhone = App.mInstance.getUser().getMobile();
        if (getIntent() != null) {
            venuesId = getIntent().getStringExtra("venuesId");
            venuesName = getIntent().getStringExtra("name");
            venuesAddress = getIntent().getStringExtra("address");
            venuesPhone = getIntent().getStringExtra("venuesPhone");
        }
        initView();
        setListener();
    }

    private void initButtonStatus() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour < 9) {           //上午9点以前 默认选中上午
            selectMorning();
            enableAfternoon();
            isMorningUnable = false;
            isAfternoonUnable = false;
        } else if (hour < 15) {    //下午3点以前 上午不可选,默认选中下午
            unableMorning();
            selectAfternoon();
            isMorningUnable = true;
            isAfternoonUnable = false;
            selectedPosition = 0;
        } else {                 //当天都不可选,默认选中第二天上午
            selectMorning();
            enableAfternoon();
            isMorningUnable = false;
            isAfternoonUnable = false;
            selectedPosition = 1;
        }
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        tvVenuesName = (TextView) findViewById(R.id.tv_name);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        ivPhone = (ImageView) findViewById(R.id.iv_phone);
        rvDate = (RecyclerView) findViewById(R.id.rv_date);
        tvMorning = (TextView) findViewById(R.id.tv_morning);
        tvAfternoon = (TextView) findViewById(R.id.tv_afternoon);
        tvUsername = (TextView) findViewById(R.id.et_username);
        tvUserPhone = (TextView) findViewById(R.id.et_phone);
        tvAppoint = (TextView) findViewById(R.id.tv_appointment);
        initButtonStatus();
        rvDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dateAdapter = new DateAdapter(selectedPosition);
        rvDate.setAdapter(dateAdapter);
        days = DateUtils.getSevenDate();
        dateAdapter.setData(days);
        tvVenuesName.setText(venuesName);
        tvAddress.setText(venuesAddress);

        appointDate = days.get(0);
        tvUsername.setText(userName);
        tvUserPhone.setText(userPhone);
    }

    private void setListener() {
        titleBar.setOnClickListener(this);
        ivPhone.setOnClickListener(this);
        tvMorning.setOnClickListener(this);
        tvAfternoon.setOnClickListener(this);
        dateAdapter.setItemClickListener(this);
        tvAppoint.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_phone:
                showCallUpDialog();
                break;
            case R.id.tv_morning:
                if (isMorningUnable) {
                    Toast.makeText(AppointVenuesActivity.this, "今天上午已不能预约", Toast.LENGTH_LONG).show();
                    return;
                }
                selectMorning();
                if (!isAfternoonUnable) {
                    enableAfternoon();
                }
                break;
            case R.id.tv_afternoon:
                if (isAfternoonUnable) {
                    Toast.makeText(AppointVenuesActivity.this, "下午已不能预约", Toast.LENGTH_LONG).show();
                    return;
                }
                selectAfternoon();
                if (!isMorningUnable) {
                    enableMorning();
                }
                break;
            case R.id.tv_appointment:
                venuesPresent.appointVenues(venuesId,appointDate,appointPeriod,userName,userPhone);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        selectedPosition = position;
        appointDate = days.get(position);
        if (position > 0) {
            selectMorning();
            enableAfternoon();
            isMorningUnable = false;
            isAfternoonUnable = false;
        } else {
            setFirstDayStatus();
        }
    }

    private void selectMorning() {
        appointPeriod = MORNING;
        tvMorning.setTextColor(Color.parseColor("#ffffff"));
        tvMorning.setBackgroundResource(R.drawable.shape_solid_corner_black);
    }

    private void enableMorning() {
        tvMorning.setTextColor(Color.parseColor("#000000"));
        tvMorning.setBackgroundResource(R.drawable.shape_solid_corner_white);
    }

    private void unableMorning() {
        tvMorning.setTextColor(Color.parseColor("#ebebeb"));
        tvMorning.setBackgroundResource(R.drawable.shape_stroke_gray);
    }

    private void selectAfternoon() {
        appointPeriod = AFTERNOON;
        tvAfternoon.setTextColor(Color.parseColor("#ffffff"));
        tvAfternoon.setBackgroundResource(R.drawable.shape_solid_corner_black);
    }

    private void enableAfternoon() {
        tvAfternoon.setTextColor(Color.parseColor("#000000"));
        tvAfternoon.setBackgroundResource(R.drawable.shape_solid_corner_white);
    }

    private void unableAfternoon() {
        tvAfternoon.setTextColor(Color.parseColor("#ebebeb"));
        tvAfternoon.setBackgroundResource(R.drawable.shape_stroke_gray);
    }

    private void setFirstDayStatus() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour < 9) {           //上午9点以前 默认选中上午
            selectMorning();
            enableAfternoon();
            isMorningUnable = false;
            isAfternoonUnable = false;
        } else if (hour < 15) {    //下午3点以前 上午不可选,默认选中下午
            unableMorning();
            selectAfternoon();
            isMorningUnable = true;
            isAfternoonUnable = false;
        } else {                 //当天都不可选,默认选中第二天上午
            unableMorning();
            unableAfternoon();
            isMorningUnable = true;
            isAfternoonUnable = true;
        }
    }

    private void showCallUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(String.format(getString(R.string.confirm_call_up), venuesPhone))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }


    public void callUp() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + venuesPhone);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

    @Override
    public void appointVenuesResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            Toast.makeText(this,"预约成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"预约失败",Toast.LENGTH_LONG).show();
        }
    }

}
