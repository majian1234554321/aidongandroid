package com.leyuan.aidong.ui.mine.activity.account;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.module.ChatLoginService;
import com.leyuan.aidong.module.chat.manager.EmChatLoginManager;
import com.leyuan.aidong.module.chat.manager.EmChatRegisterManager;
import com.leyuan.aidong.module.thirdpartylogin.ThirdLoginUtils;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.CouponNewcomerActivity;
import com.leyuan.aidong.ui.mine.activity.setting.PhoneBindingThirdLoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.CourseConfigPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.LoginPresenter;
import com.leyuan.aidong.ui.mvp.presenter.impl.MineInfoPresenterImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.SystemPresentImpl;
import com.leyuan.aidong.ui.mvp.view.LoginViewInterface;
import com.leyuan.aidong.ui.mvp.view.RequestCountInterface;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.RequestResponseCount;
import com.leyuan.aidong.utils.StringUtils;
import com.leyuan.aidong.utils.ThirdClientValid;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.utils.UiManager;

import java.util.ArrayList;


public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginViewInterface, EmChatLoginManager.OnLoginListner, EmChatRegisterManager.OnChatRegisterCallback, ThirdLoginUtils.OnThirdPartyLogin, RequestCountInterface {

    private static final String TAG = "LoginActivity";
    private LoginPresenter loginPresenter;
    private String telephone;
    private String password;

    private EmChatLoginManager chatLoginManager;
    private EmChatRegisterManager chatRegisterManager;
    private LocalReceiver receiver;
    private ArrayList<CouponBean> coupons;
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constant.BROADCAST_ACTION_PHONE_BINDING_SUCCESS:
                    loginResult(user, coupons);
                    break;
            }
        }
    };
    private UserCoach user;
    private int requestCounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i("login " + TAG, " onCreate");

        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(Constant.BROADCAST_ACTION_PHONE_BINDING_SUCCESS);
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter1);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.WX_LOGIN_SUCCESS_ACTION);
        receiver = new LocalReceiver();
        registerReceiver(receiver, intentFilter);

        IntentFilter registerFilter = new IntentFilter();
        registerFilter.addAction(Constant.BROADCAST_ACTION_REGISTER_SUCCESS);
        registerReceiver(registerReceiver, registerFilter);

        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this, this);
        chatLoginManager = new EmChatLoginManager(this);

        loginPresenter.setLoginViewInterface(this);

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
                    loginPresenter.login(telephone, password);
                    DialogUtils.showDialog(this, "", true);
                }
                break;
            case R.id.forget_password:

                UiManager.activityJump(this, FindPasswordActivity.class);
                break;
            case R.id.register:

                UiManager.activityJump(this, RegisterActivity.class);
                break;
            case R.id.button_weixin:
                DialogUtils.showDialog(this, "", true);
                loginPresenter.loginThirdParty(ThirdLoginUtils.LOGIN_WEIXIN);


                break;
            case R.id.button_weibo:
                DialogUtils.showDialog(this, "", true);
                loginPresenter.loginThirdParty(ThirdLoginUtils.LOGIN_WEIBO);


                break;
            case R.id.button_qq:
                if (!ThirdClientValid.isQQClientAvailable(this)) {
                    ToastGlobal.showLong("没有安装QQ");
                    return;

                }

                DialogUtils.showDialog(this, "", true);
                loginPresenter.loginThirdParty(ThirdLoginUtils.LOGIN_QQ);


                break;
        }
    }


    private EditText getEditTelephone() {
        return (EditText) findViewById(R.id.edit_telephone);
    }

    private EditText getPassword() {
        return (EditText) findViewById(R.id.password);
    }

    private boolean verify() {
        telephone = getEditTelephone().getText().toString().trim();
        if (!StringUtils.isMatchTel(telephone)) {
            getEditTelephone().setError("请输入正确手机号");
            return false;
        }

        password = getPassword().getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            getPassword().setError("请输入密码");
            return false;
        }
        return true;
    }

    @Override
    public void loginResult(UserCoach user, ArrayList<CouponBean> coupons) {
        DialogUtils.dismissDialog();
        this.coupons = coupons;

        if (user != null) {

            DialogUtils.showDialog(this, "", false);
            ChatLoginService.startService(this, String.valueOf(user.getId()));

            RequestResponseCount requestResponse = new RequestResponseCount(this);

            MineInfoPresenterImpl mineInfoPresenter = new MineInfoPresenterImpl(this);
            mineInfoPresenter.setOnRequestResponse(requestResponse);
            mineInfoPresenter.getMineInfo();

            FollowPresentImpl followPresent = new FollowPresentImpl(this);
            followPresent.setOnRequestResponse(requestResponse);
            followPresent.getFollowList();

            SystemPresentImpl systemPresent = new SystemPresentImpl(this);
            systemPresent.setOnRequestResponse(requestResponse);
            systemPresent.getSystemInfo("android");

            CourseConfigPresentImpl coursePresentNew = new CourseConfigPresentImpl(this);
            coursePresentNew.setOnRequestResponse(requestResponse);
            coursePresentNew.getCourseFilterConfig();

            requestCounts = 4;


            sendBroadcast(new Intent(Constant.BROADCAST_ACTION_REGISTER_SUCCESS));
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_LOGIN_SUCCESS));
        }
    }

    @Deprecated
    @Override
    public void needBindingPhone(UserCoach user, ArrayList<CouponBean> coupons) {
        this.user = user;
        this.coupons = coupons;
        DialogUtils.dismissDialog();

        UiManager.activityJump(this, PhoneBindingThirdLoginActivity.class);
    }

    @Override
    public void snsNeedBindingPhone(LoginResult user) {
        this.user = user.getUser();
        this.coupons = user.getCoupons();
        DialogUtils.dismissDialog();

        PhoneBindingThirdLoginActivity.start(this,user.getProfile_info().toString(),user.getType().toString());
//        UiManager.activityJump(this, PhoneBindingThirdLoginActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Logger.i(TAG, "receiver = " + receiver + ", == null ? " + (receiver == null));
    }

    @Override
    public void onChatLogin(boolean result) {
        DialogUtils.dismissDialog();
        if (result) {
            ToastUtil.showConsecutiveShort("登陆成功");
            finish();
        } else {
            ToastUtil.showConsecutiveShort("登陆成功 聊天服务不可用");
            finish();
        }
    }

    @Override
    public void onNeedRegister(final String userName) {
        DialogUtils.showDialog(this, "", false);
        if (chatRegisterManager == null)
            chatRegisterManager = new EmChatRegisterManager(this);
        chatRegisterManager.registerEmChat(userName);

    }

    @Override
    public void onChatRegisterResult(boolean success, String userName) {
        DialogUtils.dismissDialog();
        if (success && userName != null) {
            DialogUtils.showDialog(this, "", false);
            chatLoginManager.login(userName);
        } else {
            ToastUtil.showConsecutiveShort("登陆成功 聊天服务注册失败");
            finish();
        }
    }

    //    应用调用Andriod_SDK接口时，如果要成功接收到回调，需要在调用接口的Activity的onActivityResult方法中增加如下代码：
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DialogUtils.dismissDialog();
        loginPresenter.onActivityResultData(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        DialogUtils.dismissDialog();
        Logger.i("share", " loginactivity onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
        chatLoginManager.release();
        if (chatRegisterManager != null)
            chatRegisterManager.release();
        unregisterReceiver(receiver);
        unregisterReceiver(registerReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        receiver = null;
        registerReceiver = null;
    }

    @Override
    public void onThridLoginStart(String sns, String code) {
        DialogUtils.dismissDialog();
        if (TextUtils.isEmpty(sns) || TextUtils.isEmpty(code)) return;

        DialogUtils.showDialog(this, "", true);
        Logger.i("third login start","sns = +"+sns+"code = "+code);
        loginPresenter.loginSns(sns, code);
    }

    @Override
    public void onRequestCount(int requestCount) {
        Logger.i("requestCount = " + requestCount);
        if (requestCount >= requestCounts) {
            DialogUtils.dismissDialog();
            finishSelf();
        }

    }

    private void finishSelf() {
        if (coupons != null && !coupons.isEmpty()) {
            CouponNewcomerActivity.start(this, coupons);
        }

        ToastGlobal.showShort(R.string.login_success);
        setResult(RESULT_OK, null);
        finish();
    }

    private BroadcastReceiver registerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    public class LocalReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.i("Login Receiver ，", "onReceive");
            DialogUtils.dismissDialog();
            if (intent != null) {
                int state = intent.getIntExtra(Constant.STATE, 4);
                switch (state) {
                    case 1:
                        String code = intent.getStringExtra(Constant.WX_LOGIN_CODE);
                        Logger.i("login wx onReceive ", " login code  = " + code);
                        if (code != null) {
                            DialogUtils.showDialog(LoginActivity.this, "", false);
                            loginPresenter.loginSns("weixin", code);
                        }
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }


            }

        }
    }
}
