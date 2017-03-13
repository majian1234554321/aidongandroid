package com.leyuan.aidong.ui.mvp.view;

/**
 * View层基类
 * Created by song on 2017/3/10.
 */
public interface BaseView {

    void showLoadingView();

    void showEmptyView();

    void showContentView();

    void showErrorNetView();
}
