package com.leyuan.aidong.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;

/**
 * Created by user on 2018/3/19.
 */
public class ContestRuleActivity extends BaseActivity {

    public static void start(Context context, String contestId) {

        Intent intent = new Intent(context, ContestRuleActivity.class);
        intent.putExtra("contestId", contestId);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_rule);
    }

}
