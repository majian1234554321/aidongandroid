package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.model.UserCoach;

import java.util.ArrayList;

public interface RegisterViewInterface {

    void onGetIdentifyCode(boolean success);

    void register(boolean success);

    void onCheckCaptchaImageResult(boolean success, String mobile);

    void onRequestStart();

    void onRegisterResult(UserCoach user, ArrayList<CouponBean> coupons);

}
