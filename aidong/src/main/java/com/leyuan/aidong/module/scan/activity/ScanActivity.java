package com.leyuan.aidong.module.scan.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.leyuan.aidong.ui.BaseActivity;
import com.example.aidong.R;

public class ScanActivity extends BaseActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}

	protected void setupView() {
		setContentView(R.layout.scancode_capture);

	}

	protected void initData() {
		// TODO Auto-generated method stub

	}

}
