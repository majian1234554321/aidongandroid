package com.leyuan.aidong.ui.mvp.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.ui.mvp.presenter.impl.EquipmentPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.NurturePresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsFilterActivityView;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.utils.constant.GoodsType;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;

import static com.leyuan.aidong.utils.Constant.GOODS_EQUIPMENT;
import static com.leyuan.aidong.utils.Constant.GOODS_FOODS;
import static com.leyuan.aidong.utils.Constant.GOODS_NUTRITION;

/**
 * Created by user on 2017/8/1.
 */
public class GoodsListPrensetImpl {
    private final Context context;
    private NurturePresent nurturePresent;
    private EquipmentPresent equipmentPresent;
    private FoodAndBeveragePresentImpl foodsPresent;

    private String goodsType;

    public GoodsListPrensetImpl(Context context, GoodsFilterActivityView filterActivityView, @GoodsType String goodsType) {
        this.context = context;
        nurturePresent = new NurturePresentImpl(filterActivityView, context);
        foodsPresent = new FoodAndBeveragePresentImpl(filterActivityView, context);
        equipmentPresent = new EquipmentPresentImpl(context, filterActivityView);
        this.goodsType = goodsType;
    }

    public ArrayList<CategoryBean> getCategotyListByType() {
        if (GOODS_NUTRITION.equals(goodsType)) {
            return SystemInfoUtils.getNurtureCategory(context);
        } else if (GOODS_EQUIPMENT.equals(goodsType)) {
            return SystemInfoUtils.getEquipmentCategory(context);
        } else if (GOODS_FOODS.equals(goodsType)) {
            return SystemInfoUtils.getFoodsCategory(context);
        }
        return null;
    }

    public void commendLoadGoodsData(SwitcherLayout switcherLayout, String categoryId, String sort, String gymId) {
        if (GOODS_NUTRITION.equals(goodsType)) {
            nurturePresent.commendLoadNurtureData(switcherLayout, categoryId, sort, gymId);
        } else if (GOODS_FOODS.equals(goodsType)) {
            foodsPresent.commendLoadFoodsData(switcherLayout, categoryId, sort, gymId);
        } else {
            equipmentPresent.commonLoadEquipmentData(switcherLayout, categoryId, sort, gymId);
        }
    }

    public void pullToRefreshGoodsData(String categoryId, String sort, String gymId) {
        if (GOODS_NUTRITION.equals(goodsType)) {
            nurturePresent.pullToRefreshNurtureData(categoryId, sort, gymId);
        } else if (GOODS_FOODS.equals(goodsType)) {
            foodsPresent.pullToRefreshFoodsData(categoryId, sort, gymId);
        } else {
            equipmentPresent.pullToRefreshEquipmentData(categoryId, sort, gymId);
        }
    }

    public void requestMoreGoodsData(RecyclerView recyclerView, int pageSize, int currPage, String categoryId, String sort, String gymId) {
        if (GOODS_NUTRITION.equals(goodsType)) {
            nurturePresent.requestMoreNurtureData(recyclerView, pageSize, currPage, categoryId, sort, gymId);
        } else if (GOODS_FOODS.equals(goodsType)) {
            foodsPresent.requestMoreFoodsData(recyclerView, pageSize, currPage, categoryId, sort, gymId);
        } else if (GOODS_EQUIPMENT.equals(goodsType)) {
            equipmentPresent.requestMoreEquipmentData(recyclerView, pageSize, currPage, categoryId, sort, gymId);
        }
    }
}
