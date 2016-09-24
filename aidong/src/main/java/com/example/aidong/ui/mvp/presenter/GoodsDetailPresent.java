package com.example.aidong.ui.mvp.presenter;

import com.example.aidong.widget.customview.SwitcherLayout;

/**
 * 商品 包含装备、健康餐饮、营养品
 * Created by song on 2016/9/22.
 */
public interface GoodsDetailPresent {

    /**
     * 获取商品详情
     * @param switcherLayout SwitcherLayout
     * @param type 装备:equipment 健康餐饮:food 营养品 nurture
     * @param id  装备 健康餐饮 营养品 id
     */
    void getGoodsDetail(SwitcherLayout switcherLayout,String type,String id);
}
