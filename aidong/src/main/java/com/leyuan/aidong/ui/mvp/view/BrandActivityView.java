package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.GoodsBean;

import java.util.List;

/**
 * 品牌详情
 * Created by song on 2016/8/13.
 */
public interface BrandActivityView {
    /**
     * 更新列表
     * @param goodsBeanList
     */
    void updateRecyclerView(List<GoodsBean> goodsBeanList);



    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
