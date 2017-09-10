package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.entity.data.GoodsDetailData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.IsLoginSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.impl.GoodsModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.GoodsDetailPresent;
import com.leyuan.aidong.ui.mvp.view.GoodsDetailActivityView;
import com.leyuan.aidong.ui.mvp.view.SelfDeliveryVenuesActivityView;
import com.leyuan.aidong.utils.Constant;
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
    //    private EquipmentModel equipmentModel;
//    private NurtureModel nurtureModel;
//    private FoodModel foodModel;
    private List<VenuesBean> venuesBeanList = new ArrayList<>();
    private GoodsModelImpl goodsModel;

    public GoodsDetailPresentImpl(Context context, GoodsDetailActivityView view) {
        this.context = context;
        this.goodsDetailView = view;
    }

    public GoodsDetailPresentImpl(Context context, SelfDeliveryVenuesActivityView view) {
        this.context = context;
        this.venuesActivityView = view;
    }

    @Override
    public void getGoodsDetail(@GoodsType String type, String id) {
        if (goodsModel == null) {
            goodsModel = new GoodsModelImpl(context);
        }
        goodsModel.getGoodsDetail(new IsLoginSubscriber<GoodsDetailData>(context) {
            @Override
            public void onNext(GoodsDetailData equipmentDetailData) {
                if (equipmentDetailData != null && equipmentDetailData.getProduct() != null) {
                    goodsDetailView.setGoodsDetail(equipmentDetailData.getProduct());
                }
            }
        }, type, id);

    }

    @Override
    public void getGoodsDetail(final SwitcherLayout switcherLayout, @GoodsType String type, String id) {
        if (type == null) return;
        if (goodsModel == null) {
            goodsModel = new GoodsModelImpl(context);
        }
        goodsModel.getGoodsDetail(new CommonSubscriber<GoodsDetailData>(context, switcherLayout) {
            @Override
            public void onNext(GoodsDetailData equipmentDetailData) {
                if (equipmentDetailData != null && equipmentDetailData.getProduct() != null) {
                    goodsDetailView.setGoodsDetail(equipmentDetailData.getProduct());
                    switcherLayout.showContentLayout();
                }else {
                    switcherLayout.showEmptyLayout();
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                goodsDetailView.showErrorView();
            }

        }, type, id);

    }

    @Override
    public void commonLoadVenues(final SwitcherLayout switcherLayout, @GoodsType String type, String sku, String brandId, String landmark) {
        if (goodsModel == null) {
            goodsModel = new GoodsModelImpl(context);
        }
        goodsModel.getDeliveryVenues(new CommonSubscriber<VenuesData>(context, switcherLayout) {
            @Override
            public void onNext(VenuesData venuesData) {
                if (venuesData != null && venuesData.getGym() != null) {
                    venuesBeanList = venuesData.getGym();
                }
                if (!venuesBeanList.isEmpty()) {
                    switcherLayout.showContentLayout();
                    venuesActivityView.onRefreshData(venuesBeanList);
                } else {
                    venuesActivityView.showEmptyView();
                }
            }
        },type, sku, Constant.PAGE_FIRST, brandId, landmark);


//        switch (type) {
//            case GOODS_EQUIPMENT:
//                if (equipmentModel == null) {
//                    equipmentModel = new EquipmentModelImpl(context);
//                }
//                equipmentModel.getDeliveryVenues(new CommonSubscriber<VenuesData>(context, switcherLayout) {
//                    @Override
//                    public void onNext(VenuesData venuesData) {
//                        if (venuesData != null && venuesData.getGym() != null) {
//                            venuesBeanList = venuesData.getGym();
//                        }
//                        if (!venuesBeanList.isEmpty()) {
//                            switcherLayout.showContentLayout();
//                            venuesActivityView.onRefreshData(venuesBeanList);
//                        } else {
//                            venuesActivityView.showEmptyView();
//                        }
//                    }
//                }, sku, Constant.PAGE_FIRST, brandId, landmark);
//                break;
//            case GOODS_FOODS:
//            case GOODS_NUTRITION:
//                if (nurtureModel == null) {
//                    nurtureModel = new NurtureModelImpl(context);
//                }
//                nurtureModel.getDeliveryVenues(new CommonSubscriber<VenuesData>(context, switcherLayout) {
//                    @Override
//                    public void onNext(VenuesData venuesData) {
//                        if (venuesData != null && venuesData.getGym() != null) {
//                            venuesBeanList = venuesData.getGym();
//                        }
//                        if (!venuesBeanList.isEmpty()) {
//                            switcherLayout.showContentLayout();
//                            venuesActivityView.onRefreshData(venuesBeanList);
//                        } else {
//                            venuesActivityView.showEmptyView();
//                        }
//                    }
//                }, sku, Constant.PAGE_FIRST, brandId, landmark);
//
//                break;
//            case GOODS_FOODS:
//                if (foodModel == null) {
//                    foodModel = new FoodModelImpl();
//                }
//                foodModel.getDeliveryVenues(new CommonSubscriber<VenuesData>(context, switcherLayout) {
//                    @Override
//                    public void onNext(VenuesData venuesData) {
//                        if (venuesData != null && venuesData.getGym() != null) {
//                            venuesBeanList = venuesData.getGym();
//                        }
//                        if (!venuesBeanList.isEmpty()) {
//                            switcherLayout.showContentLayout();
//                            venuesActivityView.onRefreshData(venuesBeanList);
//                        } else {
//                            venuesActivityView.showEmptyView();
//                        }


//                    }
//                }, sku, Constant.PAGE_FIRST);
//                break;
//            default:
//                Logger.e("GoodsDetailPresentImpl", "type must be foods,equipments or nutrition");
//                break;
//        }
    }

    @Override
    public void pullToRefreshVenues(@GoodsType String type, String sku, String brandId, String landmark) {

        if (goodsModel == null) {
            goodsModel = new GoodsModelImpl(context);
        }
        goodsModel.getDeliveryVenues(new RefreshSubscriber<VenuesData>(context) {
            @Override
            public void onNext(VenuesData venuesData) {
                if (venuesData != null && venuesData.getGym() != null) {
                    venuesBeanList = venuesData.getGym();
                }
                if (!venuesBeanList.isEmpty()) {
                    venuesActivityView.onRefreshData(venuesBeanList);
                } else {
                    venuesActivityView.showEmptyView();
                }
            }
        }, type,sku, Constant.PAGE_FIRST, brandId, landmark);

//        switch (type) {
//            case GOODS_EQUIPMENT:
//                if (equipmentModel == null) {
//                    equipmentModel = new EquipmentModelImpl(context);
//                }
//                equipmentModel.getDeliveryVenues(new RefreshSubscriber<VenuesData>(context) {
//                    @Override
//                    public void onNext(VenuesData venuesData) {
//                        if (venuesData != null && venuesData.getGym() != null) {
//                            venuesBeanList = venuesData.getGym();
//                        }
//                        if (!venuesBeanList.isEmpty()) {
//                            venuesActivityView.onRefreshData(venuesBeanList);
//                        } else {
//                            venuesActivityView.showEmptyView();
//                        }
//                    }
//                }, sku, Constant.PAGE_FIRST, brandId, landmark);
//
//                break;
//            case GOODS_FOODS:
//            case GOODS_NUTRITION:
//                if (nurtureModel == null) {
//                    nurtureModel = new NurtureModelImpl(context);
//                }
//                nurtureModel.getDeliveryVenues(new RefreshSubscriber<VenuesData>(context) {
//                    @Override
//                    public void onNext(VenuesData venuesData) {
//                        if (venuesData != null && venuesData.getGym() != null) {
//                            venuesBeanList = venuesData.getGym();
//                        }
//                        if (!venuesBeanList.isEmpty()) {
//                            venuesActivityView.onRefreshData(venuesBeanList);
//                        } else {
//                            venuesActivityView.showEmptyView();
//                        }
//                    }
//                }, sku, Constant.PAGE_FIRST, brandId, landmark);
//                break;
////            case GOODS_FOODS:
////                if (foodModel == null) {
////                    foodModel = new FoodModelImpl();
////                }
////                foodModel.getDeliveryVenues(new RefreshSubscriber<VenuesData>(context) {
////                    @Override
////                    public void onNext(VenuesData venuesData) {
////                        if (venuesData != null && venuesData.getGym() != null) {
////                            venuesBeanList = venuesData.getGym();
////                        }
////                        if (!venuesBeanList.isEmpty()) {
////                            venuesActivityView.onRefreshData(venuesBeanList);
////                        } else {
////                            venuesActivityView.showEmptyView();
////                        }
////                    }
////                }, sku, Constant.PAGE_FIRST);
////                break;
//            default:
//                Logger.e("GoodsDetailPresentImpl", "type must be foods,equipments or nutrition");
//                break;
//        }
    }

    @Override
    public void requestMoreVenues(RecyclerView recyclerView, final int pageSize, @GoodsType String type, String sku, int page, String brandId, String landmark) {
        if (goodsModel == null) {
            goodsModel = new GoodsModelImpl(context);
        }
        goodsModel.getDeliveryVenues(new RequestMoreSubscriber<VenuesData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(VenuesData venuesData) {
                if (venuesData != null && venuesData.getGym() != null) {
                    venuesBeanList = venuesData.getGym();
                }
                if (!venuesBeanList.isEmpty()) {
                    venuesActivityView.onLoadMoreData(venuesBeanList);
                }
                //没有更多数据了显示到底提示
                if (venuesBeanList.size() < pageSize) {
                    venuesActivityView.showEndFooterView();
                }
            }
        },type, sku, page, brandId, landmark);



//        switch (type) {
//            case GOODS_EQUIPMENT:
//                if (equipmentModel == null) {
//                    equipmentModel = new EquipmentModelImpl(context);
//                }
//                equipmentModel.getDeliveryVenues(new RequestMoreSubscriber<VenuesData>(context, recyclerView, pageSize) {
//                    @Override
//                    public void onNext(VenuesData venuesData) {
//                        if (venuesData != null && venuesData.getGym() != null) {
//                            venuesBeanList = venuesData.getGym();
//                        }
//                        if (!venuesBeanList.isEmpty()) {
//                            venuesActivityView.onLoadMoreData(venuesBeanList);
//                        }
//                        //没有更多数据了显示到底提示
//                        if (venuesBeanList.size() < pageSize) {
//                            venuesActivityView.showEndFooterView();
//                        }
//                    }
//                }, sku, page, brandId, landmark);
//                break;
//            case GOODS_FOODS:
//            case GOODS_NUTRITION:
//                if (nurtureModel == null) {
//                    nurtureModel = new NurtureModelImpl(context);
//                }
//                nurtureModel.getDeliveryVenues(new RequestMoreSubscriber<VenuesData>(context, recyclerView, pageSize) {
//                    @Override
//                    public void onNext(VenuesData venuesData) {
//                        if (venuesData != null && venuesData.getGym() != null) {
//                            venuesBeanList = venuesData.getGym();
//                        }
//                        if (!venuesBeanList.isEmpty()) {
//                            venuesActivityView.onLoadMoreData(venuesBeanList);
//                        }
//                        //没有更多数据了显示到底提示
//                        if (venuesBeanList.size() < pageSize) {
//                            venuesActivityView.showEndFooterView();
//                        }
//                    }
//                }, sku, page, brandId, landmark);
//                break;
////            case GOODS_FOODS:
////                if (foodModel == null) {
////                    foodModel = new FoodModelImpl();
////                }
////                foodModel.getDeliveryVenues(new RequestMoreSubscriber<VenuesData>(context, recyclerView, pageSize) {
////                    @Override
////                    public void onNext(VenuesData venuesData) {
////                        if (venuesData != null && venuesData.getGym() != null) {
////                            venuesBeanList = venuesData.getGym();
////                        }
////                        if (!venuesBeanList.isEmpty()) {
////                            venuesActivityView.onLoadMoreData(venuesBeanList);
////                        }
////                        //没有更多数据了显示到底提示
////                        if (venuesBeanList.size() < pageSize) {
////                            venuesActivityView.showEndFooterView();
////                        }
////                    }
////                }, sku, page);
////                break;
//            default:
//                Logger.e("GoodsDetailPresentImpl", "type must be foods,equipments or nutrition");
//                break;
//        }
    }


}
