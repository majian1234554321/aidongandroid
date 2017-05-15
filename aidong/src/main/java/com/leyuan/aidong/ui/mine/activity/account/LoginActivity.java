package com.leyuan.aidong.ui.mine.activity.account;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.module.chat.manager.EmChatLoginManager;
import com.leyuan.aidong.module.chat.manager.EmChatRegisterManager;
import com.leyuan.aidong.module.thirdpartylogin.ThirdLoginUtils;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.CouponNewcomerActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.LoginPresenter;
import com.leyuan.aidong.ui.mvp.presenter.impl.MineInfoPresenterImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.SystemPresentImpl;
import com.leyuan.aidong.ui.mvp.view.LoginViewInterface;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.StringUtils;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.utils.UiManager;

import java.util.ArrayList;


public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginViewInterface, EmChatLoginManager.OnLoginListner, EmChatRegisterManager.OnChatRegisterCallback, ThirdLoginUtils.OnThirdPartyLogin {

    private static final String TAG = "LoginActivity";
    private LoginPresenter loginPresenter;
    private String telephone;
    private String password;

    private EmChatLoginManager chatLoginManager;
    private EmChatRegisterManager chatRegisterManager;
    private LocalReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i("login " + TAG, " onCreate");
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

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.WX_LOGIN_SUCCESS_ACTION);
        receiver = new LocalReceiver();
        registerReceiver(receiver, intentFilter);
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

                loginPresenter.loginThirdParty(ThirdLoginUtils.LOGIN_WEIXIN);
                DialogUtils.showDialog(this, "", true);

                break;
            case R.id.button_weibo:
                loginPresenter.loginThirdParty(ThirdLoginUtils.LOGIN_WEIBO);
                DialogUtils.showDialog(this, "", true);

                break;
            case R.id.button_qq:
                loginPresenter.loginThirdParty(ThirdLoginUtils.LOGIN_QQ);
                DialogUtils.showDialog(this, "", true);

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
        if (user != null) {
            DialogUtils.showDialog(this, "", false);
            chatLoginManager.login(String.valueOf(user.getId()));
            setResult(RESULT_OK, null);
            new MineInfoPresenterImpl(this).getMineInfo();
            new FollowPresentImpl(this).getFollowList();            //登录成功后需要获取关注列表
            new SystemPresentImpl(this).getSystemInfo("android");   //登录成功后需要刷新配置(课程视频提示需要更新)
        }
        if (coupons != null && !coupons.isEmpty()) {
        CouponNewcomerActivity.start(this, coupons);
        }
//        new CouponPresentImpl(this, new CouponFragmentView() {
//            @Override
//            public void updateRecyclerView(List<CouponBean> couponBeanList) {
//                CouponNewcomerActivity.start(LoginActivity.this, (ArrayList<CouponBean>) couponBeanList);
//            }
//
//            @Override
//            public void showEmptyView() {
//
//            }
//
//            @Override
//            public void showEndFooterView() {
//
//            }
//        }).pullToRefreshData("valid");

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
        receiver = null;
    }

    @Override
    public void onThridLoginStart(String sns, String code) {
        DialogUtils.showDialog(this, "", true);
        loginPresenter.loginSns(sns, code);
    }

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
