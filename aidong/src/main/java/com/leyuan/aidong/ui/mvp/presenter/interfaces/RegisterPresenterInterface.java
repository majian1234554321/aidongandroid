package com.leyuan.aidong.ui.mvp.presenter.interfaces;

public interface RegisterPresenterInterface {

    void regitserIdentify(String mobile);
    void foundIdentify(String mobile);
    void checkIdentify(String mobile, String code, String password);

}
