package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.BannerBean;
import com.leyuan.support.entity.HomeBean;

import java.util.List;

/**
 * 首页
 * Created by song on 2016/8/13.
 */
public interface HomeFragmentView {

    /**
     * 更新Banner
     * @param bannerBeanList BannerBean集合
     */
    void updateBanner(List<BannerBean> bannerBeanList);

    /**
     * 更新列表
     * @param homeCategoryBean HomeBean集合
     */
    void updateRecyclerView(List<HomeBean> homeCategoryBean);

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
