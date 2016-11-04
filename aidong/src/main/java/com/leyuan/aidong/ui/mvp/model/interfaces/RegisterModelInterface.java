package com.leyuan.aidong.ui.mvp.model.interfaces;

import com.leyuan.aidong.entity.model.UserCoach;

import rx.Subscriber;

public interface RegisterModelInterface {
    void regitserIdentify(Subscriber<UserCoach> subscriber, String mobile);
    void foundIdentify(Subscriber<UserCoach> subscriber, String mobile);

    void checkIdentify(Subscriber<UserCoach> subscriber, String mobile, String code, String password);
}
