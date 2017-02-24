package com.leyuan.aidong.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.mvp.presenter.SystemPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.SystemPresentImpl;
import com.leyuan.aidong.utils.UiManager;


public class SplashActivity extends BaseActivity {
    private SystemPresent systemPresent;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    UiManager.activityJump(SplashActivity.this, MainActivity.class);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        systemPresent = new SystemPresentImpl(this);
        systemPresent.getSystemInfo("android");
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        mHandler.sendEmptyMessageDelayed(1, 2000);
//        if (App.mInstance.isLogin()) {
//            EMClient.getInstance().groupManager().loadAllGroups();
//            EMClient.getInstance().chatManager().loadAllConversations();
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
