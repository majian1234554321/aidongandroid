package com.example.aidong.ui.mine.activity.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.aidong.R;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mvp.presenter.impl.ChangePasswordPresenter;
import com.example.aidong .ui.mvp.view.ChangePasswordViewInterface;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .utils.UiManager;
import com.example.aidong .widget.CommonTitleLayout;


public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener, ChangePasswordViewInterface {

    private CommonTitleLayout layoutTitle;
    private String password;
    private String new_password;
    private String re_passsword;
    private ChangePasswordPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mPresenter = new ChangePasswordPresenter(this, this);

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        findViewById(R.id.button_login).setOnClickListener(this);
        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private EditText getEidtOldPassword() {
        return (EditText) findViewById(R.id.eidt_old_password);
    }

    private EditText getEidtNewPassword() {
        return (EditText) findViewById(R.id.eidt_new_password);
    }

    private EditText getEidtNewPasswordAgain() {
        return (EditText) findViewById(R.id.eidt_new_password_again);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                if (verifyEdit()) {
                    mPresenter.changePassword(password, new_password, re_passsword);
                }
                break;
        }
    }

    private boolean verifyEdit() {
        password = getEidtOldPassword().getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            getEidtOldPassword().setError("请输入密码");
            return false;
        }

        new_password = getEidtNewPassword().getText().toString().trim();
        if (TextUtils.isEmpty(new_password)) {
            getEidtNewPassword().setError("请输入新密码");
            return false;
        }

        re_passsword = getEidtNewPasswordAgain().getText().toString().trim();
        if (TextUtils.isEmpty(re_passsword)) {
            getEidtNewPasswordAgain().setError("请再次输入密码");
            return false;
        }
        return true;
    }

    @Override
    public void onChangePasswordResult(boolean success) {
        if (success) {
            ToastGlobal.showShort( "密码修改成功 请重新登录");
            UiManager.activityJump(this, LoginActivity.class);
            finish();
        }else{
//            ToastGlobal.showShort("修改失败");
        }
    }
}
