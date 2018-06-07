package com.leyuan.aidong.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.example.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.competition.fragment.ContestDynamicFragment;

/**
 * 爱动时刻
 * Created by song on 2017/1/20.
 */
public class ContestDynamicActivity extends BaseActivity{

    public static void start(Context context, String contestId) {

        Intent intent = new Intent(context, ContestDynamicActivity.class);
        intent.putExtra("contestId", contestId);
        context.startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_dynamic);
        String contestId = getIntent().getStringExtra("contestId");



        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fl_container, ContestDynamicFragment.newInstance(contestId)).commit();

        findViewById(R.id.title_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
