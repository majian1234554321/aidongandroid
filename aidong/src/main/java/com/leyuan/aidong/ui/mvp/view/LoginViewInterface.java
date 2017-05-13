package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.model.UserCoach;

import java.util.ArrayList;

public interface LoginViewInterface {

    void loginResult(UserCoach user, ArrayList<CouponBean> coupons);

//    void onAutoLoginSuccess(boolean success);
}
