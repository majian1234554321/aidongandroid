package com.leyuan.aidong.ui.activity.mine.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.RegisterPresenter;
import com.leyuan.aidong.ui.mvp.presenter.RegisterPresenterInterface;
import com.leyuan.aidong.ui.mvp.view.RegisterViewInterface;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.widget.CommonTitleLayout;
import com.leyuan.commonlibrary.util.StringUtils;


public class FindPasswordActivity extends BaseActivity implements View.OnClickListener, RegisterViewInterface {
    private CommonTitleLayout layoutTitle;
    private RegisterPresenterInterface presenter;
    private String mobile;
    private String code;
    private String password;
    private String re_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_password);
        presenter = new RegisterPresenter(this, this);

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        findViewById(R.id.btn_identify).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);
        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private EditText getEidtTelephone() {
        return (EditText) findViewById(R.id.eidt_telephone);
    }

    private EditText getEidtVerifyCode() {
        return (EditText) findViewById(R.id.eidt_verify_code);
    }

    private EditText getEidtPassword() {
        return (EditText) findViewById(R.id.eidt_password);
    }

    private EditText getEidtRePassword() {
        return (EditText) findViewById(R.id.eidt_re_password);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_identify:
                String tel = getEidtTelephone().getText().toString().trim();
                if (StringUtils.isMatchTel(tel)) {
                    presenter.foundIdentify(tel);
                } else {
                    getEidtTelephone().setError("请输入正确手机号");
                }
                break;
            case R.id.button_login:
                if(verifyEdit()){
                    presenter.checkIdentify(mobile,code,password);
                }
                break;
        }
    }

    private boolean verifyEdit() {
        mobile = getEidtTelephone().getText().toString().trim();
        if (!StringUtils.isMatchTel(mobile)) {
            getEidtTelephone().setError("请输入正确手机号");
            return false;
        }

        code = getEidtVerifyCode().getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            getEidtVerifyCode().setError("请输入验证码");
            return false;
        }

        password = getEidtPassword().getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            getEidtPassword().setError("请输入密码");
            return false;
        }

        re_password = getEidtRePassword().getText().toString().trim();
        if (TextUtils.isEmpty(re_password)) {
            getEidtRePassword().setError("请输入密码");
            return false;
        }

        return true;
    }

    @Override
    public void getIdentifyCode(boolean success) {
        if(success){
            ToastUtil.showLong(App.context,"验证码已发送,请查看");
        }else{
            ToastUtil.showShort(App.context,"手机号已注册或无效,请重新输入");
        }
    }

    @Override
    public void register(boolean success) {

        if(success){
            ToastUtil.showShort(App.context,"注册成功");
            finish();
        }else{
            ToastUtil.showShort(App.context,"注册失败 请重新提交");
        }
    }

    @Override
    public void checkCaptchaImage(boolean success, String mobile) {

    }
}
