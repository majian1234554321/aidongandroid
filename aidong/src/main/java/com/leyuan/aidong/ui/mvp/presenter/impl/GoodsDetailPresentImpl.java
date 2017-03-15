package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.entity.data.EquipmentDetailData;
import com.leyuan.aidong.entity.data.FoodDetailData;
import com.leyuan.aidong.entity.data.NurtureDetailData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.EquipmentModel;
import com.leyuan.aidong.ui.mvp.model.FoodModel;
import com.leyuan.aidong.ui.mvp.model.NurtureModel;
import com.leyuan.aidong.ui.mvp.model.impl.EquipmentModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.FoodModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.NurtureModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.GoodsDetailPresent;
import com.leyuan.aidong.ui.mvp.view.GoodsDetailActivityView;
import com.leyuan.aidong.ui.mvp.view.SelfDeliveryVenuesActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.constant.GoodsType;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品 包含装备、健康餐饮、营养品
 * Created by song on 2016/9/22.
 */
//todo 重复代码
public class GoodsDetailPresentImpl implements GoodsDetailPresent {
    private Context context;
    private GoodsDetailActivityView goodsDetailView;            //商品详情View层对象
    private SelfDeliveryVenuesActivityView venuesActivityView;  //自提场馆列表数据
    private EquipmentModel equipmentModel;
    private NurtureModel nurtureModel;
    private FoodModel foodModel;
    private List<VenuesBean> venuesBeanList = new ArrayList<>();

    public GoodsDetailPresentImpl(Context context, GoodsDetailActivityView view) {
        this.context = context;
        this.goodsDetailView = view;
    }

    public GoodsDetailPresentImpl(Context context, SelfDeliveryVenuesActivityView view) {
        this.context = context;
        this.venuesActivityView = view;
    }

    @Override
    public void getGoodsDetail(String type, String id) {
        switch (type){
            case GoodsType.EQUIPMENT:
                if(equipmentModel == null){
                    equipmentModel = new EquipmentModelImpl(context);
                }
                equipmentModel.getEquipmentDetail(new BaseSubscriber<EquipmentDetailData>(context) {
                    @Override
                    public void onNext(EquipmentDetailData equipmentDetailData) {
                        if(equipmentDetailData != null && equipmentDetailData.getEquipment()!= null){
                            goodsDetailView.setGoodsDetail(equipmentDetailData.getEquipment());
                        }
                    }
                },id);
                break;
            case GoodsType.NUTRITION:
                if(nurtureModel == null){
                    nurtureModel = new NurtureModelImpl(context);
                }
                nurtureModel.getNurtureDetail(new BaseSubscriber<NurtureDetailData>(context) {
                    @Override
                    public void onNext(NurtureDetailData nurtureDetailData) {
                        if(nurtureDetailData != null && nurtureDetailData.getNurture()!= null) {
                            goodsDetailView.setGoodsDetail(nurtureDetailData.getNurture());
                        }
                    }
                },id);
                break;
            default:
                break;
        }
    }

    @Override
    public void getGoodsDetail(final SwitcherLayout switcherLayout, String type, String id) {
        switch (type){
            case GoodsType.FOOD:
                if(foodModel == null){
                    foodModel = new FoodModelImpl();
                }
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

            case GoodsType.EQUIPMENT:
                if(equipmentModel == null){
                    equipmentModel = new EquipmentModelImpl(context);
                }
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

            case GoodsType.NUTRITION:
                if(nurtureModel == null){
                    nurtureModel = new NurtureModelImpl(context);
                }
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
                Logger.e("GoodsDetailPresentImpl","type must be foods,equipments or nutrition");
                break;
        }
    }

    @Override
    public void commonLoadVenues(final SwitcherLayout switcherLayout, String type, String sku) {
        switch (type){
            case GoodsType.EQUIPMENT:
                if(equipmentModel == null){
                    equipmentModel = new EquipmentModelImpl(context);
                }
                equipmentModel.getDeliveryVenues(new CommonSubscriber<VenuesData>(switcherLayout) {
                    @Override
                    public void onNext(VenuesData venuesData) {
                        if(venuesData != null && venuesData.getGym() != null){
                            venuesBeanList = venuesData.getGym();
                        }
                        if(!venuesBeanList.isEmpty()){
                            switcherLayout.showContentLayout();
                            venuesActivityView.updateRecyclerView(venuesBeanList);
                        }else {
                            switcherLayout.showEmptyLayout();
                        }
                    }
                },sku, Constant.PAGE_FIRST);
                break;
            case GoodsType.NUTRITION:
                if(nurtureModel == null){
                    nurtureModel = new NurtureModelImpl(context);
                }
                nurtureModel.getDeliveryVenues(new CommonSubscriber<VenuesData>(switcherLayout) {
                    @Override
                    public void onNext(VenuesData venuesData) {
                        if(venuesData != null && venuesData.getGym() != null){
                            venuesBeanList = venuesData.getGym();
                        }
                        if(!venuesBeanList.isEmpty()){
                            switcherLayout.showContentLayout();
                            venuesActivityView.updateRecyclerView(venuesBeanList);
                        }else {
                            switcherLayout.showEmptyLayout();
                        }
                    }
                },sku, Constant.PAGE_FIRST);

                break;
            case GoodsType.FOOD:
                if(foodModel == null){
                    foodModel = new FoodModelImpl();
                }
                foodModel.getDeliveryVenues(new CommonSubscriber<VenuesData>(switcherLayout) {
                    @Override
                    public void onNext(VenuesData venuesData) {
                        if(venuesData != null && venuesData.getGym() != null){
                            venuesBeanList = venuesData.getGym();
                        }
                        if(!venuesBeanList.isEmpty()){
                            switcherLayout.showContentLayout();
                            venuesActivityView.updateRecyclerView(venuesBeanList);
                        }else {
                            switcherLayout.showEmptyLayout();
                        }
                    }
                },sku, Constant.PAGE_FIRST);
                break;
            default:
                Logger.e("GoodsDetailPresentImpl","type must be foods,equipments or nutrition");
                break;
        }
    }

    @Override
    public void pullToRefreshVenues(String type, String sku) {
        switch (type){
            case GoodsType.EQUIPMENT:
                if(equipmentModel == null){
                    equipmentModel = new EquipmentModelImpl(context);
                }
                equipmentModel.getDeliveryVenues(new RefreshSubscriber<VenuesData>(context) {
                    @Override
                    public void onNext(VenuesData venuesData) {
                        if(venuesData != null && venuesData.getGym() != null){
                            venuesBeanList = venuesData.getGym();
                        }
                        if(!venuesBeanList.isEmpty()){
                            venuesActivityView.updateRecyclerView(venuesBeanList);
                        }
                    }
                },sku,Constant.PAGE_FIRST);

                break;
            case GoodsType.NUTRITION:
                if(nurtureModel == null){
                    nurtureModel = new NurtureModelImpl(context);
                }
                nurtureModel.getDeliveryVenues(new RefreshSubscriber<VenuesData>(context) {
                    @Override
                    public void onNext(VenuesData venuesData) {
                        if(venuesData != null && venuesData.getGym() != null){
                            venuesBeanList = venuesData.getGym();
                        }
                        if(!venuesBeanList.isEmpty()){
                            venuesActivityView.updateRecyclerView(venuesBeanList);
                        }
                    }
                },sku,Constant.PAGE_FIRST);
                break;
            case GoodsType.FOOD:
                if(foodModel == null){
                    foodModel = new FoodModelImpl();
                }
                foodModel.getDeliveryVenues(new RefreshSubscriber<VenuesData>(context) {
                    @Override
                    public void onNext(VenuesData venuesData) {
                        if(venuesData != null && venuesData.getGym() != null){
                            venuesBeanList = venuesData.getGym();
                        }
                        if(!venuesBeanList.isEmpty()){
                            venuesActivityView.updateRecyclerView(venuesBeanList);
                        }
                    }
                },sku,Constant.PAGE_FIRST);
                break;
            default:
                Logger.e("GoodsDetailPresentImpl","type must be foods,equipments or nutrition");
                break;
        }
    }

    @Override
    public void requestMoreVenues(RecyclerView recyclerView, final int pageSize, String type, String sku, int page) {
        switch (type) {
            case GoodsType.EQUIPMENT:
                if (equipmentModel == null) {
                    equipmentModel = new EquipmentModelImpl(context);
                }
                equipmentModel.getDeliveryVenues(new RequestMoreSubscriber<VenuesData>(context, recyclerView, pageSize) {
                    @Override
                    public void onNext(VenuesData venuesData) {
                        if (venuesData != null && venuesData.getGym() != null) {
                            venuesBeanList = venuesData.getGym();
                        }
                        if (!venuesBeanList.isEmpty()) {
                            venuesActivityView.updateRecyclerView(venuesBeanList);
                        }
                        //没有更多数据了显示到底提示
                        if (venuesBeanList.size() < pageSize) {
                            venuesActivityView.showEndFooterView();
                        }
                    }
                }, sku, page);
                break;
            case GoodsType.NUTRITION:
                if (nurtureModel == null) {
                    nurtureModel = new NurtureModelImpl(context);
                }
                nurtureModel.getDeliveryVenues(new RequestMoreSubscriber<VenuesData>(context, recyclerView, pageSize) {
                    @Override
                    public void onNext(VenuesData venuesData) {
                        if (venuesData != null && venuesData.getGym() != null) {
                            venuesBeanList = venuesData.getGym();
                        }
                        if (!venuesBeanList.isEmpty()) {
                            venuesActivityView.updateRecyclerView(venuesBeanList);
                        }
                        //没有更多数据了显示到底提示
                        if (venuesBeanList.size() < pageSize) {
                            venuesActivityView.showEndFooterView();
                        }
                    }
                }, sku, page);
                break;
            case GoodsType.FOOD:
                if (foodModel == null) {
                    foodModel = new FoodModelImpl();
                }
                foodModel.getDeliveryVenues(new RequestMoreSubscriber<VenuesData>(context, recyclerView, pageSize) {
                    @Override
                    public void onNext(VenuesData venuesData) {
                        if (venuesData != null && venuesData.getGym() != null) {
                            venuesBeanList = venuesData.getGym();
                        }
                        if (!venuesBeanList.isEmpty()) {
                            venuesActivityView.updateRecyclerView(venuesBeanList);
                        }
                        //没有更多数据了显示到底提示
                        if (venuesBeanList.size() < pageSize) {
                            venuesActivityView.showEndFooterView();
                        }
                    }
                }, sku, page);
                break;
            default:
                Logger.e("GoodsDetailPresentImpl", "type must be foods,equipments or nutrition");
                break;
        }
    }


}
