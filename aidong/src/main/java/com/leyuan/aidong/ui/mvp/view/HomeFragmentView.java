package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.HomeBean;

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
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}