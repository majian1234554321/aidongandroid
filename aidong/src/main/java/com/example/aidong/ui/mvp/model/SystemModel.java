package com.example.aidong.ui.mvp.model;

import com.example.aidong .entity.SystemBean;

import rx.Subscriber;

/**
 * 系统配置
 * Created by song on 2016/11/10.
 */
public interface SystemModel {

    /**
     * 获取系统配置
     * @param subscriber Subscriber
     * @param os ios|android
     */
    void getSystem(Subscriber<SystemBean> subscriber, String os);
}
