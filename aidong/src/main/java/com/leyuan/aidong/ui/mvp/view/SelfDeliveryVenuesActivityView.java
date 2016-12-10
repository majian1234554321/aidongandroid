package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.VenuesBean;

import java.util.List;

/**
 * 选择自提门店View层
 * Created by song on 2016/12/9.
 */
public interface SelfDeliveryVenuesActivityView {

    /**
     * 设置场馆品牌
     */
    void setGymBrand(List<CategoryBean> gymBrandBeanList);

    /**
     * 设置热门商圈
     */
    void setBusinessCircle(List<DistrictBean> circleBeanList);

    /**
     * 更新自提场馆列表数据
     * @param venuesBeanList
     */
    void updateRecyclerView(List<VenuesBean> venuesBeanList);

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
