package com.leyuan.aidong.ui.mine.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.leyuan.aidong.R;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.module.thirdpartylogin.ThirdLoginUtils;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.login.FindPasswordActivity;
import com.leyuan.aidong.ui.mine.login.RegisterActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.LoginPresenter;
import com.leyuan.aidong.ui.mvp.view.LoginViewInterface;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.util.StringUtils;


public class LoginActivity extends BaseActivity implements View.OnClickListener ,LoginViewInterface {

    private LoginPresenter loginPresenter;
    private String telephone;
    private String password;
    private SharePopupWindow sharePopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        loginPresenter = new LoginPresenter(this,this);
        sharePopupWindow = new SharePopupWindow(this);

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
//                sharePopupWindow.showAtBottom("测试标题","测试内容","http://o8e1adk04.bkt.clouddn.com/image/2016/11/18/941b1d51-9e24-47bb-8b1a-6a172abbdce3.jpg",
//                        "http://www.baidu.com");
                loginPresenter.loginThirdParty(ThirdLoginUtils.LOGIN_WEIXIN);

                break;
            case R.id.button_weibo:
                loginPresenter.loginThirdParty(ThirdLoginUtils.LOGIN_WEIBO);

                break;
            case R.id.button_qq:
                loginPresenter.loginThirdParty(ThirdLoginUtils.LOGIN_QQ);

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
//          ToastUtil.showShort(App.context,App.mInstance.getUser().getName()+"姓名");
            finish();
        }
//        else{
//            ToastUtil.showShort(App.context,"账户或密码错误");
//        }
    }


    //    应用调用Andriod_SDK接口时，如果要成功接收到回调，需要在调用接口的Activity的onActivityResult方法中增加如下代码：
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loginPresenter.onActivityResultData(requestCode,resultCode,data);
        sharePopupWindow.onActivityResult(requestCode,resultCode,data);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        sharePopupWindow.onNewIntent(intent);
    }
}
