package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.entity.model.result.LoginResult;

import java.util.ArrayList;

public interface LoginViewInterface {

    void loginResult(UserCoach user, ArrayList<CouponBean> coupons);

    void needBindingPhone(UserCoach user, ArrayList<CouponBean> coupons);

    void snsNeedBindingPhone(LoginResult user);

//    void onAutoLoginSuccess(boolean success);
}
