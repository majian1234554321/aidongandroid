package com.leyuan.aidong.ui.activity.mine.account;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.mine.login.CompleteUserInfomationActivity;
import com.leyuan.aidong.ui.activity.mine.login.FindPasswordActivity;
import com.leyuan.aidong.ui.activity.mine.login.RegisterActivity;
import com.leyuan.commonlibrary.manager.UiManager;


/**
 * Created by user on 2016/10/31.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.forget_password).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
        findViewById(R.id.button_weixin).setOnClickListener(this);
        findViewById(R.id.button_weibo).setOnClickListener(this);
        findViewById(R.id.button_qq).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_login:
                UiManager.activityJump(this,CompleteUserInfomationActivity.class);
                break;
            case R.id.forget_password:
                UiManager.activityJump(this,FindPasswordActivity.class);
                break;
            case R.id.register:

                UiManager.activityJump(this,RegisterActivity.class);
                break;
            case R.id.button_weixin:

                break;
            case R.id.button_weibo:

                break;
            case R.id.button_qq:

                break;
        }
    }

    private EditText getEditTelephone(){
        return (EditText) findViewById(R.id.edit_telephone);
    }

    private EditText getPassword(){
        return (EditText) findViewById(R.id.password);
    }
}
