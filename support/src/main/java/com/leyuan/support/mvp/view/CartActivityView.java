package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.GoodsBean;

import java.util.List;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public interface CartActivityView {
    /**
     * 更新列表
     * @param goodsBeanList GoodsBean
     */
    void updateRecyclerView(List<GoodsBean> goodsBeanList);

    /**
     * 显示没有数据布局
     */
    void showEmptyView();
}
