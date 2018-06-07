package com.leyuan.aidong.ui.discover.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.R;
import com.leyuan.aidong.adapter.discover.DateAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CoachBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointCoachActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.StringUtils;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.dialog.BaseDialog;
import com.leyuan.aidong.widget.dialog.ButtonOkListener;
import com.leyuan.aidong.widget.dialog.DialogSingleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 预约私教
 * Created by song on 2016/10/26.
 */
public class AppointCoachActivity extends BaseActivity implements View.OnClickListener,
        DateAdapter.ItemClickListener, AppointCoachActivityView {
    private static final int CALL_PHONE_PERM = 1;
    private static final String MORNING = "0";
    private static final String AFTERNOON = "1";

    private SimpleTitleBar titleBar;
    private ImageView ivAvatar;
    private ImageView ivGender;
    private TextView tvName;
    private TextView tvDistance;
    private ImageView ivPhone;
    private RecyclerView rvDate;
    private TextView tvMorning;
    private TextView tvAfternoon;
    private EditText etUsername;
    private EditText etPhone;
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
        starter.putExtra("venuesId", venuesId);
        starter.putExtra("coachBean", coachBean);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_coach);
        venuesPresent = new VenuesPresentImpl(this, this);
        userName = App.mInstance.getUser().getName();
        userPhone = App.mInstance.getUser().getMobile();
        if (getIntent() != null) {
            venuesId = getIntent().getStringExtra("venuesId");
            coachBean = getIntent().getParcelableExtra("coachBean");
        }

        initView();
        setListener();
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        ivAvatar = (ImageView) findViewById(R.id.dv_avatar);
        ivGender = (ImageView) findViewById(R.id.iv_gender);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        ivPhone = (ImageView) findViewById(R.id.iv_phone);
        rvDate = (RecyclerView) findViewById(R.id.rv_date);
        tvMorning = (TextView) findViewById(R.id.tv_morning);
        tvAfternoon = (TextView) findViewById(R.id.tv_afternoon);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPhone = (EditText) findViewById(R.id.et_phone);
        tvAppoint = (TextView) findViewById(R.id.tv_appointment);
        initButtonStatus();
        dateAdapter = new DateAdapter(selectedPosition);
        rvDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvDate.setAdapter(dateAdapter);
        days = DateUtils.getSevenDate();
        dateAdapter.setData(days);

        GlideLoader.getInstance().displayCircleImage(coachBean.getAvatar(), ivAvatar);
        tvName.setText(coachBean.getName());
        if ("0".equals(coachBean.getGender())) {   //男
            ivGender.setBackgroundResource(R.drawable.icon_man);
        } else {
            ivGender.setBackgroundResource(R.drawable.icon_woman);
        }
        if (userName != null)
            etUsername.setText(userName);
        if (userPhone != null)
            etPhone.setText(userPhone);

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
        switch (view.getId()) {
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
                if (verifyOk()) {
                    venuesPresent.appointCoach(venuesId, coachBean.getId(), appointDate, appointPeriod,
                            etUsername.getText().toString().trim(), etPhone.getText().toString().trim());
                }

                break;
            default:
                break;
        }
    }

    private boolean verifyOk() {
        String userName = etUsername.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showShort(this, "请输入姓名");
            return false;
        }

        String phone = etPhone.getText().toString().trim();
        if (!StringUtils.isMatchTel(phone)) {
            ToastUtil.showShort(this, "请输入正确手机号");
            return false;
        }
        return true;
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
                        callUp();
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


    @AfterPermissionGranted(CALL_PHONE_PERM)
    public void callUp() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + coachBean.getMobile());
            intent.setData(data);
            try {
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_phone),
                    CALL_PHONE_PERM, Manifest.permission.CALL_PHONE);
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
        tvMorning.setBackgroundResource(R.drawable.shape_corners_stroke_gray);
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
        tvAfternoon.setBackgroundResource(R.drawable.shape_corners_stroke_gray);
    }

    @Override
    public void appointCoachResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            new DialogSingleButton(this).setContentDesc("预约成功")
                    .setBtnOkListener(new ButtonOkListener() {
                        @Override
                        public void onClick(BaseDialog dialog) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        } else {
            Toast.makeText(this, "预约失败", Toast.LENGTH_LONG).show();
        }
    }
}
