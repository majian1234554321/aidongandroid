package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.VenuesBean;

import java.util.List;

/**
 * 搜索
 * Created by song on 2016/9/1.
 */
public interface SearchVenuesFragmentView {
    /**
     * 更新列表
     * @param venuesBeanList VenuesBean集合
     */
    void updateRecyclerView(List<VenuesBean> venuesBeanList);

    /**
     * 显示空值界面布局
     */
    void showEmptyView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
