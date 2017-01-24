package com.leyuan.aidong.ui.activity.discover;

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
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.discover.adapter.DateAdapter;
import com.leyuan.aidong.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 预约场馆
 * 上午九点之前,下午三点之前可预约,若今天不可预约日期从明天开始
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

    private List<String> days = new ArrayList<>();
    private String appointDate;
    private int selectedPosition = 0;
    private boolean isMorningUnable = false;
    private boolean isAfternoonUnable = false;

    public static void start(Context context, String name, String address, String phone) {
        Intent starter = new Intent(context, AppointVenuesActivity.class);
        starter.putExtra("name", name);
        starter.putExtra("address", address);
        starter.putExtra("phone", phone);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_venues);
        if (getIntent() != null) {
            venuesName = getIntent().getStringExtra("name");
            venuesAddress = getIntent().getStringExtra("address");
            phone = getIntent().getStringExtra("phone");
            phone = "111";
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
        tvVenuesName = (TextView) findViewById(R.id.tv_name);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        ivPhone = (ImageView) findViewById(R.id.iv_phone);
        rvDate = (RecyclerView) findViewById(R.id.rv_date);
        tvMorning = (TextView) findViewById(R.id.tv_morning);
        tvAfternoon = (TextView) findViewById(R.id.tv_afternoon);
        tvUsername = (TextView) findViewById(R.id.et_username);
        tvPhone = (TextView) findViewById(R.id.et_phone);
        initButtonStatus();
        rvDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dateAdapter = new DateAdapter(selectedPosition);
        rvDate.setAdapter(dateAdapter);
        days = DateUtils.getSevenDate();
        dateAdapter.setData(days);
        tvVenuesName.setText(venuesName);
        tvAddress.setText(venuesAddress);
        tvUsername.setText(App.mInstance.getUser().getName());
        tvPhone.setText(App.mInstance.getUser().getMobile());
    }

    private void setListener() {
        ivPhone.setOnClickListener(this);
        tvMorning.setOnClickListener(this);
        tvAfternoon.setOnClickListener(this);
        dateAdapter.setItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
        tvMorning.setTextColor(Color.parseColor("#ffffff"));
        tvMorning.setBackgroundResource(R.drawable.shape_solid_black);
    }

    private void enableMorning() {
        tvMorning.setTextColor(Color.parseColor("#000000"));
        tvMorning.setBackgroundResource(R.drawable.shape_stroke_white);
    }

    private void unableMorning() {
        tvMorning.setTextColor(Color.parseColor("#ebebeb"));
        tvMorning.setBackgroundResource(R.drawable.shape_stroke_gray);
    }

    private void selectAfternoon() {
        tvAfternoon.setTextColor(Color.parseColor("#ffffff"));
        tvAfternoon.setBackgroundResource(R.drawable.shape_solid_black);
    }

    private void enableAfternoon() {
        tvAfternoon.setTextColor(Color.parseColor("#000000"));
        tvAfternoon.setBackgroundResource(R.drawable.shape_stroke_white);
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
        builder.setMessage(String.format(getString(R.string.confirm_call_up), phone))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PermissionGen.with(AppointVenuesActivity.this)
                                .addRequestCode(100)
                                .permissions(Manifest.permission.CALL_PHONE)
                                .request();
                         dialog.dismiss();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);

    }

    @PermissionSuccess(requestCode = 100)
    public void callUp() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }


}
