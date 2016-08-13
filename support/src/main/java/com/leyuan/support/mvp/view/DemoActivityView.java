package com.leyuan.support.mvp.view;


import com.leyuan.support.entity.DemoBean;

import java.util.List;

/**
 * Created by song on 2016/8/9.
 */
public interface DemoActivityView {

    /**
     * 更新列表
     * @param demoList
     */
     void updateRecyclerView(List<DemoBean> demoList);

    /**
     * 显示空值界面
     */
    void showEmptyView();

    /**
     * 隐藏空值界面
     */
    void hideEmptyView();

    /**
     * 显示RecyclerView
     */
    void showRecyclerView();

    /**
     * 隐藏RecyclerView
     */
    void hideRecyclerView();

    /**
     * 整体界面显示无网络界面
     * 对于有缓存的界面空实现该方法即可
     */
    void showErrorView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();

}
