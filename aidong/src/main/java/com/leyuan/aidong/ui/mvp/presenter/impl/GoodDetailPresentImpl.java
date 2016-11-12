package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.util.Log;

import com.leyuan.aidong.entity.data.EquipmentDetailData;
import com.leyuan.aidong.entity.data.FoodDetailData;
import com.leyuan.aidong.entity.data.NurtureDetailData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.ui.mvp.model.EquipmentModel;
import com.leyuan.aidong.ui.mvp.model.FoodModel;
import com.leyuan.aidong.ui.mvp.model.NurtureModel;
import com.leyuan.aidong.ui.mvp.model.impl.EquipmentModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.FoodModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.NurtureModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.GoodsDetailPresent;
import com.leyuan.aidong.ui.mvp.view.GoodsDetailActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 商品 包含装备、健康餐饮、营养品
 * Created by song on 2016/9/22.
 */
public class GoodDetailPresentImpl implements GoodsDetailPresent {
    private static final String TYPE_FOOD = "food";
    private static final String TYPE_EQUIPMENT = "equipment";
    private static final String TYPE_NURTURE = "nutrition";

    private GoodsDetailActivityView goodsDetailView;    //商品详情View层对象

    public GoodDetailPresentImpl(GoodsDetailActivityView view) {
        this.goodsDetailView = view;
    }

    @Override
    public void getGoodsDetail(final SwitcherLayout switcherLayout, String type, String id) {
        switch (type){
            case TYPE_FOOD:
                FoodModel foodModel = new FoodModelImpl();
                foodModel.getFoodDetail(new CommonSubscriber<FoodDetailData>(switcherLayout) {
                    @Override
                    public void onNext(FoodDetailData foodDetailData) {
                        if(foodDetailData != null && foodDetailData.getFood() != null){
                            goodsDetailView.setGoodsDetail(foodDetailData.getFood());
                            switcherLayout.showContentLayout();
                        }else{
                            switcherLayout.showEmptyLayout();
                        }
                    }
                },id);
                break;

            case TYPE_EQUIPMENT:
                EquipmentModel equipmentModel = new EquipmentModelImpl();
                equipmentModel.getEquipmentDetail(new CommonSubscriber<EquipmentDetailData>(switcherLayout) {
                    @Override
                    public void onNext(EquipmentDetailData equipmentDetailData) {
                        if(equipmentDetailData != null && equipmentDetailData.getEquipment()!= null){
                            goodsDetailView.setGoodsDetail(equipmentDetailData.getEquipment());
                            switcherLayout.showContentLayout();
                        }else{
                            switcherLayout.showEmptyLayout();
                        }
                    }
                },id);
                break;

            case TYPE_NURTURE:
                NurtureModel nurtureModel = new NurtureModelImpl();
                nurtureModel.getNurtureDetail(new CommonSubscriber<NurtureDetailData>(switcherLayout) {
                    @Override
                    public void onNext(NurtureDetailData nurtureDetailData) {
                        if(nurtureDetailData != null && nurtureDetailData.getNurture()!= null){
                            goodsDetailView.setGoodsDetail(nurtureDetailData.getNurture());
                            switcherLayout.showContentLayout();
                        }else{
                            switcherLayout.showEmptyLayout();
                        }
                    }
                },id);
                break;
            default:
                Log.e("GoodDetailPresentImpl","type must be food,equipment or nutrition");
                break;
        }
    }
}
