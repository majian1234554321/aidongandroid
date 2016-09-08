package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.NurtureBean;

import java.util.List;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public interface NurtureActivityView {
    /**
     * 更新列表
     * @param nurtureBeanList
     */
    void updateRecyclerView(List<NurtureBean> nurtureBeanList);

    /**
     * 显示加载到的内容布局
     */
    void showContentView();

    /**
     * 显示空值界面
     */
    void showEmptyView();

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
