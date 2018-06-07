package com.leyuan.aidong.ui.mine.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.RegisterPresenterInterface;
import com.leyuan.aidong.ui.mvp.presenter.impl.RegisterPresenter;
import com.leyuan.aidong.ui.mvp.view.RegisterViewInterface;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.LogAidong;
import com.leyuan.aidong.utils.StringUtils;
import com.leyuan.aidong.utils.TimeCountUtil;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.widget.CommonTitleLayout;
import com.leyuan.aidong.widget.DialogImageIdentify;

import java.util.ArrayList;

/**
 * Created by user on 2017/3/21.
 */
public class PhoneUnBindingActivity extends BaseActivity implements View.OnClickListener, RegisterViewInterface {
    private CommonTitleLayout layoutTitle;
    private RegisterPresenterInterface presenter;
    private String mobile;
    private String code;
    private DialogImageIdentify mDialogImageIdentify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_unbingding);
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
        if (App.getInstance().isLogin()) {
            mobile = App.getInstance().getUser().getMobile();
            getEidtTelephone().setText(mobile);
        }

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
                    DialogUtils.showDialog(this, "", true);
                    presenter.checkIdentifyBinding(code);
                }
                break;
        }
    }

    private boolean verifyEdit() {

        code = getEidtVerifyCode().getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            getEidtVerifyCode().setError("请输入验证码");
            return false;
        }

        if(presenter.getToken()== null){
            ToastGlobal.showShort(R.string.please_get_identify_first);
            return false;
        }

        return true;
    }

    @Override
    public void onGetIdentifyCode(boolean success) {
        DialogUtils.dismissDialog();
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
        DialogUtils.dismissDialog();
        if (success) {
            ToastUtil.showShort(App.context, "验证成功");
            UiManager.activityJumpForResult(this, new Bundle(), PhoneBindingActivity.class, Constant.REQUEST_PHONE_BINGDING);
        }
//        else {
//            ToastUtil.showShort(App.context, "验证失败 请核对手机号");
//        }
    }

    @Override
    public void onCheckCaptchaImageResult(boolean success, String mobile) {
        DialogUtils.dismissDialog();
        if (success) {
            DialogUtils.showDialog(this, "", true);
            presenter.unbindingCaptcha(mobile);
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
                DialogUtils.showDialog(PhoneUnBindingActivity.this, "", true);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_PHONE_BINGDING) {
            setResult(resultCode, data);
            finish();
        }
    }
}
