package com.example.aidong.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.utils.ActivityTool;

public class AboutMxActivity extends BaseActivity {
	private ImageView mlayout_tab_about_mx_title_img;
	private TextView textview_version;
	private Intent intent;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}

	protected void setupView() {
		setContentView(R.layout.layout_about_mx);
		init();
	}

	protected void initData() {
		onClick();
	}

	private void init() {
		intent = new Intent();
		mlayout_tab_about_mx_title_img = (ImageView) findViewById(R.id.layout_tab_about_mx_title_img);
		textview_version = (TextView) findViewById(R.id.textview_version);
		String version = ActivityTool.getVersion(AboutMxActivity.this);
		textview_version.setText(version);
	}

	private void onClick() {
		mlayout_tab_about_mx_title_img
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
	}
}
