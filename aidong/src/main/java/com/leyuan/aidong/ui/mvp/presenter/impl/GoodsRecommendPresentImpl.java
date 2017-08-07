package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.BaseGoodsBean;
import com.leyuan.aidong.ui.mvp.view.AppointSuccessActivityView;
import com.leyuan.aidong.ui.mvp.view.CartActivityView;
import com.leyuan.aidong.ui.mvp.view.EquipmentActivityView;
import com.leyuan.aidong.ui.mvp.view.GoodsActivityView;
import com.leyuan.aidong.ui.mvp.view.NurtureActivityView;
import com.leyuan.aidong.ui.mvp.view.PaySuccessActivityView;

/**
 * Created by user on 2017/8/1.
 */
public class GoodsRecommendPresentImpl extends RecommendPresentImpl {

    private BaseGoodsBean.GoodsType goodsType;

    public GoodsRecommendPresentImpl(Context context, NurtureActivityView view) {
        super(context, view);
    }

    public GoodsRecommendPresentImpl(Context context, EquipmentActivityView view) {
        super(context, view);
    }

    public GoodsRecommendPresentImpl(Context context, CartActivityView view) {
        super(context, view);
    }

    public GoodsRecommendPresentImpl(Context context, AppointSuccessActivityView view) {
        super(context, view);
    }

    public GoodsRecommendPresentImpl(Context context, PaySuccessActivityView view) {
        super(context, view);
    }

    public GoodsRecommendPresentImpl(Context context, GoodsActivityView goodsCategoryActivity, BaseGoodsBean.GoodsType goodsType) {
        super(context, goodsCategoryActivity);
        this.goodsType = goodsType;
    }
}

