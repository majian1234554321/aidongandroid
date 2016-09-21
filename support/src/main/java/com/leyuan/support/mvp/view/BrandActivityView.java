package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.BrandBean;

/**
 * 品牌详情
 * Created by song on 2016/8/13.
 */
public interface BrandActivityView {
    /**
     * 更新列表
     * @param brandBean
     */
    void updateRecyclerView(BrandBean brandBean);

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
