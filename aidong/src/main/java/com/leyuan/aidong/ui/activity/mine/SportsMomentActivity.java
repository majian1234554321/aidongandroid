package com.leyuan.aidong.ui.activity.mine;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.fragment.mine.UserDynamicFragment;

/**
 * 爱动时刻
 * Created by song on 2017/1/20.
 */
public class SportsMomentActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_moment);
        UserDynamicFragment fragment = new UserDynamicFragment();
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
