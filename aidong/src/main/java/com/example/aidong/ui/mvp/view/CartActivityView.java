package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.GoodsBean;

import java.util.List;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public interface CartActivityView {



    void updateRecommendGoods(List<GoodsBean> goodsBeanList);

    void showEndFooterView();

    void showEmptyRecommendView();
}
