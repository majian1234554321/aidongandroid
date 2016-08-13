package com.leyuan.support.mvp.view;


import com.leyuan.support.entity.VenuesBean;

import java.util.List;

/**
 * 发现-场馆
 * Created by Song on 2016/8/2.
 */
public interface DiscoverVenuesFragmentView {

    /**
     * 更新场馆列表
     * @param venuesList 场馆
     */
    public void updateRecyclerView(List<VenuesBean> venuesList);

    /**
     * 显示FooterView，提示没有任何内容了
     */
    public void showEndFooterView();



}
