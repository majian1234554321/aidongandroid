package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.VenuesBean;

import java.util.List;

/**
 * 发现-场馆
 * Created by song on 2016/8/29.
 */
public interface DiscoverVenuesActivityView {
    /**
     * 更新列表
     * @param venuesBeanList
     */
    void updateRecyclerView(List<VenuesBean> venuesBeanList);

    /**
     * 显示空值界面
     */
    void showEmptyView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
