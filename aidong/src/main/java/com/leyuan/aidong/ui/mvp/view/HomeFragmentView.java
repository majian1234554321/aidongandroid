package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.HomeBean;
import com.leyuan.aidong.entity.VenuesBean;

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
     * 设置弹出广告
     * @param banner
     */
    void updatePopupBanner(List<BannerBean> banner);

    /**
     * 更新列表
     * @param homeCategoryBean HomeBean集合
     */
    void updateRecyclerView(List<HomeBean> homeCategoryBean);


    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();

    void updateSportsHistory(List<VenuesBean> venuesBeanList);

    void updateCourseCategory(List<CategoryBean> courseBeanList);


}
