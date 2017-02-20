package com.leyuan.aidong.ui.mine.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.discover.fragment.CircleFragment;

/**
 * 爱动时刻
 * Created by song on 2017/1/20.
 */
public class AiDongMomentActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_moment);
        CircleFragment fragment = new CircleFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fl_container,fragment).commit();

        findViewById(R.id.title_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
