package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.CategoryBean;
import com.example.aidong .entity.DistrictBean;
import com.example.aidong .entity.VenuesBean;

import java.util.List;

/**
 * 发现-场馆
 * Created by song on 2016/8/29.
 */
public interface DiscoverVenuesActivityView {

    /**
     * 设置场馆品牌
     */
    void setGymBrand(List<CategoryBean> gymBrandBeanList);

    /**
     * 设置热门商圈
     */
    void setBusinessCircle(List<DistrictBean> circleBeanList);

    /**
     * 设置场馆分类
     */
    void setGymTypes(List<String> gymTypesList);

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();

    void onRefreshData(List<VenuesBean> venuesBeanList);

    void onLoadMoreData(List<VenuesBean> venuesBeanList);

    void showEmptyView();
}
