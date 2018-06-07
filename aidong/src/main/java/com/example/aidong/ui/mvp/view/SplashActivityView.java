package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.SystemBean;

/**
 * 闪屏页
 * Created by song on 2016/11/10.
 */
public interface SplashActivityView {

    /**
     * 设置后台配置信息
     * @param bean SystemBean
     */
    void setSystemInfo(SystemBean bean);
}
