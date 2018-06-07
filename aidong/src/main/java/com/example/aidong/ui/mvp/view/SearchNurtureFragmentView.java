package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.NurtureBean;

import java.util.List;

/**
 * 搜索营养品
 * Created by song on 2016/9/1.
 */
public interface SearchNurtureFragmentView {

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();

    void onRecyclerViewRefresh(List<NurtureBean> venuesBeanList);

    void onRecyclerViewLoadMore(List<NurtureBean> venuesBeanList);

    void showEmptyView();
}
