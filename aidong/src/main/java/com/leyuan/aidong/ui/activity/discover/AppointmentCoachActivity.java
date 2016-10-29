package com.leyuan.aidong.ui.activity.discover;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;

/**
 * 预约私教
 * Created by song on 2016/10/26.
 */
public class AppointmentCoachActivity extends BaseActivity implements View.OnClickListener {
    private SimpleDraweeView dvAvatar;
    private ImageView ivGender;
    private TextView tvName;
    private TextView tvDistance;
    private ImageView ivPhone;

    private RecyclerView rvDate;
    private TextView tvMorning;
    private TextView tvAfternoon;

    private EditText etUsername;
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_coach);
        initView();
        setListener();
    }

    private void initView(){
        dvAvatar = (SimpleDraweeView) findViewById(R.id.dv_avatar);
        ivGender = (ImageView) findViewById(R.id.iv_gender);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        ivPhone = (ImageView) findViewById(R.id.iv_phone);
        rvDate = (RecyclerView) findViewById(R.id.rv_date);
        tvMorning = (TextView) findViewById(R.id.tv_morning);
        tvAfternoon = (TextView) findViewById(R.id.tv_afternoon);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPhone = (EditText) findViewById(R.id.et_phone);
    }

    private void setListener() {
        ivPhone.setOnClickListener(this);
        tvMorning.setOnClickListener(this);
        tvAfternoon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_phone:

                break;
            case R.id.tv_morning:
                break;
            case R.id.tv_afternoon:
                break;
            default:
                break;
        }
    }
}
