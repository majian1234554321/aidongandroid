package com.example.aidong.activity.account;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;

public class UserAgreementActivity extends BaseActivity {
	private ImageView mimageview_user_agreement_back;
	private Intent intent;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}

	protected void setupView() {
		setContentView(R.layout.layout_user_agreement);
		init();
	}

	protected void initData() {
		onClick();
	}

	private void init() {
		mimageview_user_agreement_back = (ImageView) findViewById(R.id.imageview_user_agreement_back);
		intent = new Intent();
	}

	private void onClick() {
		mimageview_user_agreement_back
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
	}
}
