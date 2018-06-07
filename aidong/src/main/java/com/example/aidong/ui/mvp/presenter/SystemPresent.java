package com.example.aidong.ui.mvp.presenter;

import com.example.aidong .ui.mvp.view.SplashView;
import com.example.aidong .ui.mvp.view.SystemView;

/**
 * 系统配置
 * Created by song on 2016/11/10.
 */
public interface SystemPresent {

    void setSystemView(SystemView systemView);

    void setSplashView(SplashView splashView);

    /**
     * 获取系统配置信息
     * @param os
     */
    void getSystemInfo(String os);
    /**
     * 选择城市里 去获取系统配置信息
     * @param os
     */
    void getSystemInfoSelected(String os);
}
