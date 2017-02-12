package com.leyuan.aidong.ui.mine.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.FastLoginPresenter;
import com.leyuan.aidong.ui.mvp.view.FastLoginViewInterface;
import com.leyuan.aidong.utils.StringUtils;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.widget.CommonTitleLayout;

public class FastLoginActivity extends BaseActivity implements View.OnClickListener, FastLoginViewInterface {

    private CommonTitleLayout layoutTitle;
    private FastLoginPresenter presenter;

    private String mobile;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_login);
        presenter = new FastLoginPresenter(this, this);

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.bt_get_verify_code).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);
    }

    private EditText getEidtTelephone() {
        return (EditText) findViewById(R.id.eidt_telephone);
    }

    private EditText getEidtVerifyCode() {
        return (EditText) findViewById(R.id.eidt_verify_code);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_get_verify_code:
                String tel = getEidtTelephone().getText().toString().trim();
                if (StringUtils.isMatchTel(tel)) {
                    presenter.getIdentify(tel);
                } else {
                    getEidtTelephone().setError("请输入正确手机号");
                }
                break;
            case R.id.button_login:
                if (verifyEdit()) {
                    presenter.login(mobile, code);
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

        return true;
    }

    @Override
    public void onGetIdentify(boolean success) {
        if (success) {
            ToastUtil.showLong(App.context, "验证码已发送,请查看");
        }
    }

    @Override
    public void onLoginResult(boolean success) {
        if (success) {
            ToastUtil.showShort(App.context, "登录成功");
            finish();
          //  finishActivityByClassName("LoginActivity");
        } else {
            ToastUtil.showShort(App.context, "登录失败 请重新提交");
        }
    }
}
