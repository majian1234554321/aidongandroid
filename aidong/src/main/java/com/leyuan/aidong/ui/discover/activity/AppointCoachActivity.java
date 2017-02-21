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
import com.leyuan.aidong.entity.CoachBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.adapter.discover.DateAdapter;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointCoachActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.SimpleTitleBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 预约私教
 * Created by song on 2016/10/26.
 */
public class AppointCoachActivity extends BaseActivity implements View.OnClickListener, DateAdapter.ItemClickListener,AppointCoachActivityView {
    private static final String MORNING = "0";
    private static final String AFTERNOON = "1";

    private SimpleTitleBar titleBar;
    private ImageView dvAvatar;
    private ImageView ivGender;
    private TextView tvName;
    private TextView tvDistance;
    private ImageView ivPhone;
    private RecyclerView rvDate;
    private TextView tvMorning;
    private TextView tvAfternoon;
    private TextView tvUsername;
    private TextView tvPhone;
    private TextView tvAppoint;

    private List<String> days = new ArrayList<>();
    private DateAdapter dateAdapter;
    private boolean isMorningUnable = false;
    private boolean isAfternoonUnable = false;
    private int selectedPosition = 0;
    private String venuesId;
    private CoachBean coachBean;
    private String appointDate;
    private String appointPeriod;
    private String userName;
    private String userPhone;

    private VenuesPresent venuesPresent;

    public static void start(Context context, String venuesId, CoachBean coachBean) {
        Intent starter = new Intent(context, AppointCoachActivity.class);
        starter.putExtra("venuesId",venuesId);
        starter.putExtra("coachBean",coachBean);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_coach);
        venuesPresent = new VenuesPresentImpl(this,this);
        userName = App.mInstance.getUser().getName();
        userPhone = App.mInstance.getUser().getMobile();
        if(getIntent() != null){
            venuesId = getIntent().getStringExtra("venuesId");
            coachBean = getIntent().getParcelableExtra("coachBean");
        }

        initView();
        setListener();
    }

    private void initView(){
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        dvAvatar = (ImageView) findViewById(R.id.dv_avatar);
        ivGender = (ImageView) findViewById(R.id.iv_gender);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        ivPhone = (ImageView) findViewById(R.id.iv_phone);
        rvDate = (RecyclerView) findViewById(R.id.rv_date);
        tvMorning = (TextView) findViewById(R.id.tv_morning);
        tvAfternoon = (TextView) findViewById(R.id.tv_afternoon);
        tvUsername = (TextView) findViewById(R.id.et_username);
        tvPhone = (TextView) findViewById(R.id.et_phone);
        tvAppoint = (TextView) findViewById(R.id.tv_appointment);
        initButtonStatus();
        dateAdapter = new DateAdapter(selectedPosition);
        rvDate.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rvDate.setAdapter(dateAdapter);
        days = DateUtils.getSevenDate();
        dateAdapter.setData(days);

        GlideLoader.getInstance().displayImage(coachBean.getAvatar(), dvAvatar);
        tvName.setText(coachBean.getName());
        if("0".equals(coachBean.getGender())){   //男
            ivGender.setBackgroundResource(R.drawable.icon_man);
        }else {
            ivGender.setBackgroundResource(R.drawable.icon_woman);
        }
        tvUsername.setText(userName);
        tvPhone.setText(userPhone);
        appointDate = days.get(0);
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
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_phone:
                showCallUpDialog();
                break;
            case R.id.tv_morning:
                if (isMorningUnable) {
                    Toast.makeText(AppointCoachActivity.this, "今天上午已不能预约", Toast.LENGTH_LONG).show();
                    return;
                }
                selectMorning();
                if (!isAfternoonUnable) {
                    enableAfternoon();
                }
                break;
            case R.id.tv_afternoon:
                if (isAfternoonUnable) {
                    Toast.makeText(AppointCoachActivity.this, "下午已不能预约", Toast.LENGTH_LONG).show();
                    return;
                }
                selectAfternoon();
                if (!isMorningUnable) {
                    enableMorning();
                }
                break;
            case R.id.tv_appointment:
                venuesPresent.appointCoach(venuesId,coachBean.getCoachId(),appointDate,
                        appointPeriod,userName,userPhone);
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

    private void showCallUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(String.format(getString(R.string.confirm_call_up), coachBean.getMobile()))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PermissionGen.with(AppointCoachActivity.this)
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);

    }

    @PermissionSuccess(requestCode = 100)
    public void callUp() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + coachBean.getMobile());
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
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

    @Override
    public void appointCoachResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            Toast.makeText(this,"预约成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"预约失败",Toast.LENGTH_LONG).show();
        }
    }
}
