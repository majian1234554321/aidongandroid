package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.GoodsBean;

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
     * 显示购物车没有数据布局
     */
    void showEmptyView();

    /**
     * 修改购物车中商品数量
     * @param baseBean BaseBean
     */
    void setUpdateCart(BaseBean baseBean);

    /**
     * 删除购物车中商品
     * @param baseBean BaseBean
     */
    void setDeleteCart(BaseBean baseBean);
}
