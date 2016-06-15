package com.example.aidong.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.aidong.BaseActivity;
import com.example.aidong.MainActivity;
import com.leyuan.commonlibrary.manager.UIManager;

public class SplashActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        UIManager.activityJump(this, MainActivity.class);
    }
}
