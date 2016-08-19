package com.leyuan.support.widget.tabLayout.listener;

import android.support.annotation.DrawableRes;

/**
 * 自定义Tab需实现的接口
 */
public interface ICustomTab {

    /**
     * 获取Tab标题
     * @return
     */
    String getTabTitle();

    /**
     * 获取Tab选中状态的icon
     * @return
     */
    @DrawableRes
    int getTabSelectedIcon();

    /**
     * 获取Tab未选中状态的Tab
     * @return
     */
    @DrawableRes
    int getTabUnselectedIcon();
}