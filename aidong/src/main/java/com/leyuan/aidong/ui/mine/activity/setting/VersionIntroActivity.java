package com.leyuan.aidong.ui.mine.activity.setting;

import android.os.Bundle;
import android.view.View;

import com.leyuan.aidong.ui.GuideActivity;

/**
 * Created by user on 2017/3/15.
 */
public class VersionIntroActivity extends GuideActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lastImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
