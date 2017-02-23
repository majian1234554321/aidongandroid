package com.leyuan.aidong.ui.mine.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.widget.UISwitchButton;
import com.leyuan.aidong.widget.wheelview.ScreenInfo;
import com.leyuan.aidong.widget.wheelview.WheelMain;

import java.util.Calendar;

public class TabMineDoNotDisturbPeriodActivity extends BaseActivity {
	private ImageView mimageview_do_not_disturb_period_back;
	private TextView mtextview_do_not_disturb_period_back_submit,
			mtextview_accept_the_new_message_alert_start_time1,
			mtextview_accept_the_new_message_alert_end_time_1;
	private WheelMain wheelMain;
	private UISwitchButton mswitch4;
	private SharedPreferences mSharedPreferences;
	private Intent intent;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}

	protected void setupView() {
		setContentView(R.layout.layout_tab_mine_do_not_disturb_period);
		init();
		onClick();
	}

	protected void initData() {

	}

	private void init() {
		mimageview_do_not_disturb_period_back = (ImageView) findViewById(R.id.imageview_do_not_disturb_period_back);
		mtextview_do_not_disturb_period_back_submit = (TextView) findViewById(R.id.textview_do_not_disturb_period_back_submit);
		mtextview_accept_the_new_message_alert_start_time1 = (TextView) findViewById(R.id.textview_accept_the_new_message_alert_start_time1);
		mtextview_accept_the_new_message_alert_end_time_1 = (TextView) findViewById(R.id.textview_accept_the_new_message_alert_end_time_1);
		mswitch4 = (UISwitchButton) findViewById(R.id.switch4);
		mSharedPreferences = getSharedPreferences("DoNot",
				Context.MODE_PRIVATE);
		mswitch4.setChecked(mSharedPreferences.getBoolean("state", false));
		intent = new Intent();
	}

	private void onClick() {
		mimageview_do_not_disturb_period_back
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
		mtextview_do_not_disturb_period_back_submit
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						intent.setClass(TabMineDoNotDisturbPeriodActivity.this,
								TabMineMessageReminderActivity.class);
						TabMineDoNotDisturbPeriodActivity.this.finish();
					}
				});
		mtextview_accept_the_new_message_alert_start_time1
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						LayoutInflater inflater = LayoutInflater
								.from(TabMineDoNotDisturbPeriodActivity.this);
						final View timepickerview = inflater.inflate(
								R.layout.timepicker, null);
						ScreenInfo screenInfo = new ScreenInfo(
								TabMineDoNotDisturbPeriodActivity.this);
						wheelMain = new WheelMain(timepickerview);
						wheelMain.screenheight = screenInfo.getHeight();
						Calendar calendar = Calendar.getInstance();
						int year = calendar.get(Calendar.YEAR);
						int month = calendar.get(Calendar.MONTH);
						int day = calendar.get(Calendar.DAY_OF_MONTH);
						wheelMain.initDateTimePicker(year, month, day);
						new AlertDialog.Builder(
								TabMineDoNotDisturbPeriodActivity.this)
								.setTitle("请选择时间")
								.setView(timepickerview)
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												mtextview_accept_the_new_message_alert_start_time1
														.setText(wheelMain
																.getTime());
											}
										})
								.setNegativeButton("取消",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
											}
										}).show();
					}
				});
		mtextview_accept_the_new_message_alert_end_time_1
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						LayoutInflater inflater = LayoutInflater
								.from(TabMineDoNotDisturbPeriodActivity.this);
						final View timepickerview = inflater.inflate(
								R.layout.timepicker, null);
						ScreenInfo screenInfo = new ScreenInfo(
								TabMineDoNotDisturbPeriodActivity.this);
						wheelMain = new WheelMain(timepickerview);
						wheelMain.screenheight = screenInfo.getHeight();
						Calendar calendar = Calendar.getInstance();
						int year = calendar.get(Calendar.YEAR);
						int month = calendar.get(Calendar.MONTH);
						int day = calendar.get(Calendar.DAY_OF_MONTH);
						wheelMain.initDateTimePicker(year, month, day);
						new AlertDialog.Builder(
								TabMineDoNotDisturbPeriodActivity.this)
								.setTitle("请选择时间")
								.setView(timepickerview)
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												mtextview_accept_the_new_message_alert_end_time_1
														.setText(wheelMain
																.getTime());
											}
										})
								.setNegativeButton("取消",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
											}
										}).show();

					}
				});
		mswitch4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (!isChecked) {
					mtextview_accept_the_new_message_alert_start_time1
							.setVisibility(View.GONE);
					mtextview_accept_the_new_message_alert_end_time_1
							.setVisibility(View.GONE);
				} else {
					mtextview_accept_the_new_message_alert_start_time1
							.setVisibility(View.VISIBLE);
					mtextview_accept_the_new_message_alert_end_time_1
							.setVisibility(View.VISIBLE);
				}
				Editor editor = mSharedPreferences.edit();
				editor.putBoolean("state", isChecked);
				editor.commit();
			}
		});

	}

}
