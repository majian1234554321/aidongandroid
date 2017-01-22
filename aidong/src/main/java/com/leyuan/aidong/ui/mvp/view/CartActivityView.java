package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.ShopBean;

import java.util.List;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public interface CartActivityView {
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
     * 显示没有推荐商品时的布局
     */
    void showEmptyRecommendGoodsView();

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

    void updateRecommendGoods(List<GoodsBean> goodsBeanList);

    void showEndFooterView();
}
