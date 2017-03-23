package com.leyuan.aidong.ui.mvp.model;


import com.leyuan.aidong.entity.BaseBean;

import rx.Subscriber;

public interface ChangePasswordInterface {

   void changePassword(Subscriber<BaseBean> subscriber, String password, String new_password, String re_passsword);
}
