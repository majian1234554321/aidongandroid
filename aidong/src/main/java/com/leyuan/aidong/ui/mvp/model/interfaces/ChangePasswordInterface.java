package com.leyuan.aidong.ui.mvp.model.interfaces;


import com.leyuan.aidong.entity.model.result.LoginResult;

import rx.Subscriber;

public interface ChangePasswordInterface {

   void changePassword(Subscriber<LoginResult> subscriber, String password, String new_password, String re_passsword);
}
