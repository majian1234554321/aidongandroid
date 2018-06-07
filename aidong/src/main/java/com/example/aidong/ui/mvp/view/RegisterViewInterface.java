package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.CouponBean;
import com.example.aidong .entity.model.UserCoach;

import java.util.ArrayList;

public interface RegisterViewInterface {

    void onGetIdentifyCode(boolean success);

    void register(boolean success);

    void onCheckCaptchaImageResult(boolean success, String mobile);

    void onRequestStart();

    void onRegisterResult(UserCoach user, ArrayList<CouponBean> coupons);

}
