package com.example.aidong.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.BaseApp;
import com.example.aidong.R;
import com.example.aidong.common.UrlLink;
import com.example.aidong.http.HttpConfig;
import com.example.aidong.model.UserCoach;
import com.example.aidong.model.result.ChangeResult;
import com.example.aidong.model.result.CheckResult;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordForgetActivity extends BaseActivity implements
        IHttpCallback {
    private Button mbtnGetActiveCode, mBtnReset;
    private TextView mtxt_automatic_acquisition, mtxt_60s;
    private View mview12;
    private TimeCount time;
    private EditText meditPhonenum, mEditNewPassword, meditActiveCode;
    private RelativeLayout layout_password_forget;
    private static final int CAPTCHACHECK = 0;
    private static final int CAPTCHACHANGE = 1;
    private String mobile;
    private String verifyCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        initData();
    }

    protected void setupView() {
        setContentView(R.layout.layout_password_forget);
        initView();
        setClick();
    }

    protected void initData() {
        time = new TimeCount(60000, 1000);
    }

    @Override
    public void onGetData(Object data, int requestCode, String response) {
        switch (requestCode) {
            case CAPTCHACHECK:
                CheckResult cres = (CheckResult) data;
                if (cres.getCode() == 1) {
                    if (cres.getData() != null) {
                        ToastUtil.show(getResources().getString(
                                R.string.upadtepassword), this);
                        //					BaseApp.mInstance.setUser(cres.getData().getUser());
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
                break;
            case CAPTCHACHANGE:
                ChangeResult rres = (ChangeResult) data;
                if (rres.getCode() == 1) {
                    if (rres.getData() != null) {
                        UserCoach user = new UserCoach();
                        user.setToken(rres.getData().getToken());
                        BaseApp.mInstance.setUser(user);
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onError(String reason, int requestCode) {

    }

    private void initView() {
        mtxt_automatic_acquisition = (TextView) findViewById(R.id.txt_automatic_acquisition);
        mtxt_60s = (TextView) findViewById(R.id.txt_60s);
        mview12 = findViewById(R.id.view12);
        layout_password_forget = (RelativeLayout) findViewById(R.id.layout_password_forget);
        mbtnGetActiveCode = (Button) findViewById(R.id.btnGetActiveCode);
        meditPhonenum = (EditText) findViewById(R.id.editPhonenum);
        mEditNewPassword = (EditText) findViewById(R.id.editNewPassword);
        meditActiveCode = (EditText) findViewById(R.id.editActiveCode);
        mBtnReset = (Button) findViewById(R.id.btnPasswordReset);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mtxt_automatic_acquisition.setVisibility(View.GONE);
            mtxt_60s.setVisibility(View.GONE);
            //			mview12.setVisibility(View.GONE);
            mbtnGetActiveCode.setVisibility(View.VISIBLE);
            meditPhonenum.setClickable(true);
            meditPhonenum.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mtxt_60s.setVisibility(View.VISIBLE);
            mtxt_automatic_acquisition.setVisibility(View.VISIBLE);
            //			mview12.setVisibility(View.VISIBLE);
            mbtnGetActiveCode.setVisibility(View.INVISIBLE);
            mtxt_60s.setText(millisUntilFinished / 1000 + "S");
            meditPhonenum.setClickable(false);
            meditPhonenum.setEnabled(false);
        }
    }

    private void setClick() {
        layout_password_forget = (RelativeLayout) findViewById(R.id.layout_password_forget);
        layout_password_forget.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                try {
                    return imm.hideSoftInputFromWindow(getCurrentFocus()
                            .getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });
        findViewById(R.id.btnBack).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        mbtnGetActiveCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = meditPhonenum.getText().toString().trim();
                if (mobile == null || mobile.length() == 0) {
                    ToastUtil.show(getResources().getString(
                            R.string.pleasenumber),PasswordForgetActivity.this);
                    //					meditPhonenum.setError(Html.fromHtml("<font color=#808183>"
                    //							+ getResources().getString(R.string.pleasenumber)
                    //							+ "</font>"));
                } else {
                    ToastUtil.show(getResources().getString(
                            R.string.sendpassword),PasswordForgetActivity.this);
                    addTask(PasswordForgetActivity.this, new IHttpTask(
                            UrlLink.CAPTCHACHANGE_URL, paramsinit(),
                            ChangeResult.class), HttpConfig.POST, CAPTCHACHANGE);
                    time.start();
                }
            }
        });
        mBtnReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = mEditNewPassword.getText().toString()
                        .trim();
                verifyCode = meditActiveCode.getText().toString().trim();
                if (newPassword == null || newPassword.length() == 0) {
                    ToastUtil.show(getResources().getString(
                            R.string.pleasenewpassword),PasswordForgetActivity.this);
                    mEditNewPassword.setError(Html
                            .fromHtml("<font color=#808183>"
                                    + getResources().getString(
                                    R.string.pleasenewpassword)
                                    + "</font>"));
                } else if (verifyCode == null || verifyCode.length() == 0) {
                    ToastUtil.show(getResources().getString(
                            R.string.please_code),PasswordForgetActivity.this);
                    meditActiveCode.setError(Html
                            .fromHtml("<font color=#808183>"
                                    + getResources().getString(
                                    R.string.please_code) + "</font>"));
                } else {

                    Map<String, String> head = new HashMap<String, String>();
                    if (BaseApp.mInstance.getUser() != null && BaseApp.mInstance.getUser().getToken() != null) {
                        head.put("token", BaseApp.mInstance.getUser().getToken());
                    }


                    addTask(PasswordForgetActivity.this, new IHttpTask(
                            UrlLink.CAPTCHACHECK_URL, head, paramsinitReset(newPassword),
                            CheckResult.class), HttpConfig.POST, CAPTCHACHECK);

                }
            }
        });
    }

    public List<BasicNameValuePair> paramsinit() {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        BasicNameValuePair pair1 = new BasicNameValuePair("mobile", mobile);
        paramsaaa.add(pair1);
        return paramsaaa;
    }

    public List<BasicNameValuePair> paramsinitReset(String newPassword) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        BasicNameValuePair pair2 = new BasicNameValuePair("captcha", verifyCode);
        BasicNameValuePair pair3 = new BasicNameValuePair("password",
                newPassword);
        paramsaaa.add(pair2);
        paramsaaa.add(pair3);
        return paramsaaa;
    }
}
