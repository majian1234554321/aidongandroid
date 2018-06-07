package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.VenuesBean;

import java.util.List;

/**
 * 搜索场馆
 * Created by song on 2016/9/1.
 */
public interface SearchVenuesFragmentView {

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();

    void onRecyclerViewRefresh(List<VenuesBean> venuesBeanList);

    void onRecyclerViewLoadMore(List<VenuesBean> venuesBeanList);

    void showEmptyView();
}
