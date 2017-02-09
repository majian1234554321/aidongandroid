package com.leyuan.aidong.ui.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;




/**
 * 申请售后下一步
 * Created by song on 2016/10/18.
 */
public class ApplyServiceNextActivity extends BaseActivity implements View.OnClickListener {
    private SimpleTitleBar titleBar;
    private RelativeLayout infoLayout;
    private TextView tvApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_service_next);

        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        infoLayout = (RelativeLayout) findViewById(R.id.rl_info);
        tvApply = (TextView) findViewById(R.id.tv_apply);

        titleBar.setOnClickListener(this);
        infoLayout.setOnClickListener(this);
        tvApply.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_info:

                break;
            case R.id.tv_apply:
                break;
            default:
                break;
        }
    }
}
