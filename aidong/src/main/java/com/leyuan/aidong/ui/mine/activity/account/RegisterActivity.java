package com.leyuan.aidong.ui.mine.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.ProfileBean;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.module.ChatLoginService;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.WebViewActivity;
import com.leyuan.aidong.ui.mine.activity.CouponNewcomerActivity;
import com.leyuan.aidong.ui.mvp.presenter.RegisterPresenterInterface;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.MineInfoPresenterImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.RegisterPresenter;
import com.leyuan.aidong.ui.mvp.presenter.impl.SystemPresentImpl;
import com.leyuan.aidong.ui.mvp.view.RegisterViewInterface;
import com.leyuan.aidong.ui.mvp.view.RequestCountInterface;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.RequestResponseCount;
import com.leyuan.aidong.utils.StringUtils;
import com.leyuan.aidong.utils.TimeCountUtil;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.widget.CommonTitleLayout;
import com.leyuan.aidong.widget.DialogImageIdentify;

import java.util.ArrayList;

import static com.leyuan.aidong.ui.App.context;


public class RegisterActivity extends BaseActivity implements View.OnClickListener, RegisterViewInterface, RequestCountInterface {

    private CommonTitleLayout layoutTitle;
    private TextView txtProtocol;
    private RegisterPresenterInterface presenter;
    private DialogImageIdentify mDialogImageIdentify;

    private String mobile;
    private String code;
    private String password;
    private ArrayList<CouponBean> coupons;
//    private String re_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter(this, this);
        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        findViewById(R.id.btn_identify).setOnClickListener(this);

        txtProtocol = (TextView) findViewById(R.id.txt_protocol);
        txtProtocol.setText(Html.fromHtml("我已阅读<font color=\"#000000\">《爱动健身》</font>服务协议"));
        findViewById(R.id.button_register).setOnClickListener(this);
        txtProtocol.setOnClickListener(this);
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

    private CheckBox getCheckboxProtocol() {
        return (CheckBox) findViewById(R.id.checkbox_protocol);
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
            case R.id.button_register:
                if (verifyEdit()) {
                    presenter.checkIdentifyRegister(App.getInstance().getToken(), code, password);
//                    presenter.checkIdentify(App.mInstance.getToken(), code, password);
                }
                break;
            case R.id.txt_protocol:
                WebViewActivity.start(this, "用户协议", ConstantUrl.URL_USER_AGREEMENT);
//                startActivity(new Intent(this, UserAgreementActivity.class));
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

        if (password.length() < 6 || password.length() > 14) {
            ToastGlobal.showShort("密码长度不正确");
            return false;
        }
//
//        re_password = getEidtRePassword().getText().toString().trim();
//        if (TextUtils.isEmpty(re_password)) {
//            getEidtRePassword().setError("请输入密码");
//            return false;
//        }

        if (!getCheckboxProtocol().isChecked()) {
            // ToastUtil.showShort(.context, "需同意协议才能完成注册");

            ToastGlobal.showShort("需同意协议才能完成注册");
            return false;
        }

        if(App.getInstance().getToken() == null){
            ToastGlobal.showShort(R.string.please_get_identify_first);
            return false;
        }

        return true;
    }

    @Override
    public void onGetIdentifyCode(boolean success) {
        DialogUtils.dismissDialog();
        if (success) {
            ToastUtil.showShort(context, "验证码已发送,请查看");
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
        //改为在onRegisterResult里面做处理
//        DialogUtils.dismissDialog();
//        if (success) {
//            ToastGlobal.showShort("注册成功");
//            CompleteUserInfoActivity.start(this, new ProfileBean());
//            finish();
//        }
    }

    @Override
    public void onCheckCaptchaImageResult(boolean success, String mobile) {
        if (success) {
            presenter.regitserIdentify(mobile);
        } else if (mDialogImageIdentify != null && mDialogImageIdentify.isShowing()) {
            DialogUtils.dismissDialog();
            mDialogImageIdentify.clearContent();
            mDialogImageIdentify.refreshImage(mobile);
        }
    }

    @Override
    public void onRequestStart() {
        DialogUtils.showDialog(this, "", false);
    }

    @Override
    public void onRegisterResult(UserCoach user, ArrayList<CouponBean> coupons) {

        DialogUtils.dismissDialog();
        this.coupons = coupons;

        if (user != null) {

            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_NEW_USER_REGISTER));
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
        }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
    }

    @Override
    public void onRequestCount(int requestCount) {
        Logger.i("requestCount = " + requestCount);
        Logger.i("requestCount = " + requestCount);
        if (requestCount >= 3) {
            DialogUtils.dismissDialog();
            finishSelf();
        }
    }

    private void finishSelf() {
        ToastGlobal.showShort(R.string.register_success);

        if (coupons != null && !coupons.isEmpty()) {
            Intent intentCoupon = new Intent(this, CouponNewcomerActivity.class);
            intentCoupon.putExtra("coupons", coupons);

            Intent intentCompletUser = new Intent(this, CompleteUserInfoActivity.class);
            intentCompletUser.putExtra("profileBean", new ProfileBean());

            startActivities(new Intent[]{intentCoupon, intentCompletUser});
        } else {
            CompleteUserInfoActivity.start(this, new ProfileBean());
        }

//        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_REGISTER_SUCCESS));
        sendBroadcast(new Intent(Constant.BROADCAST_ACTION_REGISTER_SUCCESS));
        finish();
    }
}
