package com.leyuan.aidong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.mvp.presenter.SystemPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.SystemPresentImpl;
import com.leyuan.aidong.utils.SharePrefUtils;
import com.leyuan.aidong.utils.TransitionHelper;
import com.leyuan.aidong.utils.UiManager;


public class SplashActivity extends BaseActivity {
    private static final int MESSAGE = 1;
    private static final int DURATION = 3000;
    private static final String OS = "android";
    private boolean isFirstEnter = true;

    //todo
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == MESSAGE){
                if(isFirstEnter) {
                    UiManager.activityJump(SplashActivity.this, GuideActivity.class);
                }else {
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(SplashActivity.this, false);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashActivity.this, pairs);
                    startActivity(intent,optionsCompat.toBundle());
                }
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SystemPresent systemPresent = new SystemPresentImpl(this);
        systemPresent.getSystemInfo(OS);
        isFirstEnter = SharePrefUtils.getBoolean(SplashActivity.this,"isFirstEnter",true);
        handler.sendEmptyMessageDelayed(MESSAGE, DURATION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
