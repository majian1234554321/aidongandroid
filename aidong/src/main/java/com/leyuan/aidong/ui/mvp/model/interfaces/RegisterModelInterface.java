package com.leyuan.aidong.ui.mvp.model.interfaces;

import com.leyuan.aidong.entity.user.User;

import rx.Subscriber;

public interface RegisterModelInterface {
    void regitserIdentify(Subscriber<User> subscriber, String mobile);
    void foundIdentify(Subscriber<User> subscriber, String mobile);

    void checkIdentify(Subscriber<User> subscriber, String mobile, String code, String password, String re_password);
}
