package com.leyuan.aidong.ui.activity.mine.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.mine.login.FindPasswordActivity;
import com.leyuan.aidong.ui.activity.mine.login.RegisterActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.LoginPresenter;
import com.leyuan.aidong.ui.mvp.presenter.LoginPresenterInterface;
import com.leyuan.aidong.ui.mvp.view.LoginViewInterface;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.util.StringUtils;


public class LoginActivity extends BaseActivity implements View.OnClickListener ,LoginViewInterface {

    private LoginPresenterInterface loginPresenter;
    private String telephone;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        loginPresenter = new LoginPresenter(this,this);

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
                if (verify()) {
                    loginPresenter.login(telephone,password);
                }
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

    private boolean verify() {
        telephone = getEditTelephone().getText().toString().trim();
        if (!StringUtils.isMatchTel(telephone)) {
            getEditTelephone().setError("请输入正确手机号");
            return false;
        }

        password =  getPassword().getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            getPassword().setError("请输入密码");
            return false;
        }
        return true;
    }

    @Override
    public void loginResult(boolean success) {
        if(success){
            ToastUtil.showShort(App.context,"登录成功");
            finish();
        }
//        else{
//            ToastUtil.showShort(App.context,"账户或密码错误");
//        }
    }
}
