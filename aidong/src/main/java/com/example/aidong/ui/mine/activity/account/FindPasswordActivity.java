package com.example.aidong.ui.mine.activity.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aidong.R;
import com.example.aidong .entity.CouponBean;
import com.example.aidong .entity.model.UserCoach;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mvp.presenter.RegisterPresenterInterface;
import com.example.aidong .ui.mvp.presenter.impl.RegisterPresenter;
import com.example.aidong .ui.mvp.view.RegisterViewInterface;
import com.example.aidong .utils.LogAidong;
import com.example.aidong .utils.StringUtils;
import com.example.aidong .utils.TimeCountUtil;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .utils.ToastUtil;
import com.example.aidong .widget.CommonTitleLayout;
import com.example.aidong .widget.DialogImageIdentify;

import java.util.ArrayList;


public class FindPasswordActivity extends BaseActivity implements View.OnClickListener, RegisterViewInterface {
    private CommonTitleLayout layoutTitle;
    private RegisterPresenterInterface presenter;
    private String mobile;
    private String code;
    private String password;
    private String re_password;
    private DialogImageIdentify mDialogImageIdentify;

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
                    showImageIdentifyDialog(tel);
                } else {
                    getEidtTelephone().setError("请输入正确手机号");
                }
                break;
            case R.id.button_login:
                if (verifyEdit()) {
                    LogAidong.i("checkIdentify token = ", "" + App.mInstance.getToken());
                    presenter.checkIdentify(App.mInstance.getToken(), code, password);
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

        if(App.getInstance().getToken() == null){
            ToastGlobal.showShort(R.string.please_get_identify_first);
            return false;
        }

//        re_password = getEidtRePassword().getText().toString().trim();
//        if (TextUtils.isEmpty(re_password)) {
//            getEidtRePassword().setError("请输入密码");
//            return false;
//        }

        return true;
    }

    @Override
    public void onGetIdentifyCode(boolean success) {

        if (success) {
            ToastUtil.showShort(App.context, "验证码已发送,请查看");
            if (mDialogImageIdentify != null && mDialogImageIdentify.isShowing()) {
                mDialogImageIdentify.dismiss();
            }
            new TimeCountUtil(60000, 1000, (Button) findViewById(R.id.btn_identify)).start();

        } else if (mDialogImageIdentify != null && mDialogImageIdentify.isShowing()) {
            mDialogImageIdentify.clearContent();
            mDialogImageIdentify.refreshImage(mobile);
        }
    }

    @Override
    public void register(boolean success) {

        if (success) {
            ToastGlobal.showShort("修改成功");
            finish();
        }
    }

    @Override
    public void onCheckCaptchaImageResult(boolean success, String mobile) {
        if (success) {
            presenter.foundIdentify(mobile);
        } else if (mDialogImageIdentify != null && mDialogImageIdentify.isShowing()) {
            mDialogImageIdentify.clearContent();
            mDialogImageIdentify.refreshImage(mobile);
        }

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRegisterResult(UserCoach user, ArrayList<CouponBean> coupons) {

    }

    private void showImageIdentifyDialog(final String tel) {
//           if(mDialogImageIdentify == null){
        mDialogImageIdentify = new DialogImageIdentify(this);
        mDialogImageIdentify.setOnInputCompleteListener(new DialogImageIdentify.OnInputCompleteListener() {
            @Override
            public void inputIdentify(String imageIndentify) {
//                presenter.regitserIdentify(tel,imageIndentify);
                presenter.checkCaptchaImage(tel, imageIndentify);
            }

            @Override
            public void refreshImage() {
                mDialogImageIdentify.refreshImage(tel);
            }
        });
//           }

        mDialogImageIdentify.show();
        mDialogImageIdentify.refreshImage(tel);
    }

}
