package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.ShopBean;

import java.util.List;

/**
 * the callback of cart header view
 * Created by song on 2017/2/22.
 */
public interface ICartHeaderView {
    /**
     * 更新列表
     * @param shopBeanList ShopBean
     */
    void updateRecyclerView(List<ShopBean> shopBeanList);

    /**
     * 显示购物车没有数据布局
     */
    void showEmptyGoodsView();

    /**
     * 修改购物车中商品数量
     * @param baseBean BaseBean
     * @param shopPosition the operation position of shop
     */
    void updateGoodsCountResult(BaseBean baseBean,int shopPosition,int goodsPosition);

    /**
     * 删除购物车中商品
     * @param baseBean BaseBean
     */
    void setDeleteGoodsResult(BaseBean baseBean);
}
