package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.GymBrandBean;
import com.leyuan.aidong.entity.VenuesBean;

import java.util.List;

/**
 * 发现-场馆
 * Created by song on 2016/8/29.
 */
public interface DiscoverVenuesActivityView {

    /**
     * 设置课程分类
     */
    void setGymBrand(List<GymBrandBean> gymBrandBeanList);

    /**
     * 设置热门商圈
     */
    void setBusinessCircle(List<DistrictBean> circleBeanList);

    /**
     * 更新列表
     * @param venuesBeanList
     */
    void updateRecyclerView(List<VenuesBean> venuesBeanList);

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
