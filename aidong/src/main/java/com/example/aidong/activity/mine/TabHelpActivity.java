package com.example.aidong.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.mine.account.UserAgreementActivity;

public class TabHelpActivity extends BaseActivity {
	private ImageView mlayout_tab_help_img_back;
	private RelativeLayout mlayout_tab_help_version_of_the_introduction,
			mlayout_tab_help_about_mx, mlayout_tab_help_user_agreement;
	private Intent intent;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}

	protected void setupView() {
		setContentView(R.layout.layout_tab_help);
		init();

	}

	protected void initData() {
		onClick();
	}

	private void init() {
		intent = new Intent();
		mlayout_tab_help_img_back = (ImageView) findViewById(R.id.layout_tab_help_img_back);
		mlayout_tab_help_version_of_the_introduction = (RelativeLayout) findViewById(R.id.layout_tab_help_version_of_the_introduction);
		mlayout_tab_help_about_mx = (RelativeLayout) findViewById(R.id.layout_tab_help_about_mx);
		mlayout_tab_help_user_agreement = (RelativeLayout) findViewById(R.id.layout_tab_help_user_agreement);
	}

	private void onClick() {
		mlayout_tab_help_img_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mlayout_tab_help_version_of_the_introduction
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						intent.setClass(TabHelpActivity.this,
								VersionOfTheIntroductionActivity.class);
						startActivity(intent);
					}
				});
		mlayout_tab_help_about_mx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent.setClass(TabHelpActivity.this, AboutMxActivity.class);
				startActivity(intent);
			}
		});
		mlayout_tab_help_user_agreement
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						intent.setClass(TabHelpActivity.this,
								UserAgreementActivity.class);
						startActivity(intent);
					}
				});
	}
}
