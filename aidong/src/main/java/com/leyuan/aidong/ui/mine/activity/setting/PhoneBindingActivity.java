package com.leyuan.aidong.ui.mine.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.entity.user.MineInfoBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.RegisterPresenterInterface;
import com.leyuan.aidong.ui.mvp.presenter.impl.MineInfoPresenterImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.RegisterPresenter;
import com.leyuan.aidong.ui.mvp.view.MineInfoView;
import com.leyuan.aidong.ui.mvp.view.RegisterViewInterface;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.LogAidong;
import com.leyuan.aidong.utils.StringUtils;
import com.leyuan.aidong.utils.TimeCountUtil;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.widget.CommonTitleLayout;
import com.leyuan.aidong.widget.DialogImageIdentify;

import java.util.ArrayList;

/**
 * Created by user on 2017/3/11.
 */
public class PhoneBindingActivity extends BaseActivity implements View.OnClickListener, RegisterViewInterface {
    private CommonTitleLayout layoutTitle;
    private RegisterPresenterInterface presenter;
    private String mobile;
    private String code;
    private DialogImageIdentify mDialogImageIdentify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_bingding);
        presenter = new RegisterPresenter(this, this);

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        findViewById(R.id.btn_identify).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);
        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getInstance().exitLogin();
                setResult(RESULT_CANCELED, new Intent());
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
        mobile = getEidtTelephone().getText().toString().trim();
        if (!StringUtils.isMatchTel(mobile)) {
            getEidtTelephone().setError("请输入正确手机号");
            return false;
        }
        if (!TextUtils.equals(mobile, presenter.getBingdingMobile())) {
            ToastGlobal.showShort("与验证手机不一致");
            return false;
        }

        code = getEidtVerifyCode().getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            getEidtVerifyCode().setError("请输入验证码");
            return false;
        }

        if (presenter.getToken() == null) {
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
            mDialogImageIdentify.dismiss();
//            mDialogImageIdentify.refreshImage(mobile);
        }
    }

    @Override
    public void register(boolean success) {
        DialogUtils.dismissDialog();
        if (success) {
            ToastUtil.showShort(App.context, "绑定成功");
            Intent intent = new Intent();
            intent.putExtra(Constant.BINDING_PHONE, presenter.getBingdingMobile());
            setResult(RESULT_OK, intent);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_PHONE_BINDING_SUCCESS));
            if (App.getInstance().isLogin()) {
                new MineInfoPresenterImpl(this, new MineInfoView() {
                    @Override
                    public void onGetMineInfo(MineInfoBean mineInfoBean) {

                        finish();
                    }
                }).getMineInfo();
            } else {
                finish();
            }

        } else {
            ToastUtil.showShort(App.context, "绑定失败 请重新提交");
        }
    }

    @Override
    public void onCheckCaptchaImageResult(boolean success, String mobile) {
        DialogUtils.dismissDialog();
        if (success) {
            DialogUtils.showDialog(this, "", true);
            presenter.bindingCaptcha(mobile);
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
                DialogUtils.showDialog(PhoneBindingActivity.this, "", true);
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
    public void onBackPressed() {
        super.onBackPressed();
        App.getInstance().exitLogin();
        setResult(RESULT_CANCELED, new Intent());
        finish();
    }
}
