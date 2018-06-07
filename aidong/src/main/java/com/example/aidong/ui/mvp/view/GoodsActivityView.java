package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.GoodsBean;

import java.util.List;

/**
 * Created by user on 2017/8/1.
 */
public interface GoodsActivityView {
    /**
     * 更新列表
     * @param goodsBeanList
     */
    void updateRecyclerView(List<GoodsBean> goodsBeanList);

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();

    void showEmptyView();
}
