package com.example.aidong.ui.mvp.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong .entity.CategoryBean;
import com.example.aidong .entity.GoodsBean;
import com.example.aidong .entity.data.GoodsData;
import com.example.aidong.http.RetrofitHelper;
import com.example.aidong.http.api.GoodsService;
import com.example.aidong .http.subscriber.BaseSubscriber;
import com.example.aidong .http.subscriber.CommonSubscriber;
import com.example.aidong .http.subscriber.RefreshSubscriber;
import com.example.aidong .http.subscriber.RequestMoreSubscriber;
import com.example.aidong .ui.mvp.model.impl.GoodsModelImpl;
import com.example.aidong .ui.mvp.view.GoodsFilterActivityView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.SystemInfoUtils;
import com.example.aidong .widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.aidong .utils.Constant.GOODS_EQUIPMENT;
import static com.example.aidong .utils.Constant.GOODS_FOODS;
import static com.example.aidong .utils.Constant.GOODS_NUTRITION;
import static com.example.aidong .utils.Constant.GOODS_TICKET;

/**
 * Created by user on 2017/8/1.
 */
public class GoodsListPrensetImpl {
    private static final java.lang.String TAG = "GoodsListActivity";
    private GoodsModelImpl goodsModel;
    private GoodsFilterActivityView filterActivityView;
    private List<GoodsBean> nurtureBeanList = new ArrayList<>();
    private final Context context;
    private String goodsType;

    public GoodsListPrensetImpl(Context context, GoodsFilterActivityView filterActivityView,  String goodsType) {
        this.context = context;
        this.goodsType = goodsType;
        this.filterActivityView = filterActivityView;
        goodsModel = new GoodsModelImpl(context);
    }



    /**
     * 要修改成活的
     * @return
     */
    public ArrayList<CategoryBean> getCategotyListByType() { //
        if (GOODS_NUTRITION.equals(goodsType)) {
            return SystemInfoUtils.getNurtureCategory(context);
        } else if (GOODS_EQUIPMENT.equals(goodsType)) {
            return SystemInfoUtils.getEquipmentCategory(context);
        } else if (GOODS_FOODS.equals(goodsType)) {
            return SystemInfoUtils.getFoodsCategory(context);
        }else if (GOODS_TICKET.equals(goodsType)) {
            return SystemInfoUtils.getTicketCategory(context);
        }
        return null;
    }

    public void commendLoadGoodsData(final SwitcherLayout switcherLayout, String categoryId, String sort, String gymId) {
        goodsModel.getGoods(new CommonSubscriber<GoodsData>(context, switcherLayout) {
            @Override
            public void onNext(GoodsData nurtureDataBean) {
                if (nurtureDataBean != null && nurtureDataBean.getProduct() != null) {
                    nurtureBeanList = nurtureDataBean.getProduct();
                }
                if (!nurtureBeanList.isEmpty()) {
                    switcherLayout.showContentLayout();
                    filterActivityView.updateGoodsRecyclerView(nurtureBeanList);
                } else {
                    filterActivityView.showEmptyView();
                }
            }
        },goodsType, Constant.PAGE_FIRST, categoryId, sort, gymId);

    }




    public void commendLoadGoodsData2(final SwitcherLayout switcherLayout, String categoryId, String sort, String gymId) {
        goodsModel.getGoods2(new CommonSubscriber<GoodsData>(context, switcherLayout) {
            @Override
            public void onNext(GoodsData nurtureDataBean) {
                if (nurtureDataBean != null && nurtureDataBean.getProduct() != null) {
                    nurtureBeanList = nurtureDataBean.getProduct();
                }
                if (!nurtureBeanList.isEmpty()) {
                    switcherLayout.showContentLayout();
                    filterActivityView.updateGoodsRecyclerView(nurtureBeanList);
                } else {
                    filterActivityView.showEmptyView();
                }
            }
        },goodsType, Constant.PAGE_FIRST, categoryId, sort, gymId);

    }


    public void pullToRefreshGoodsData(String categoryId, String sort, String gymId) {

        goodsModel.getGoods(new RefreshSubscriber<GoodsData>(context) {
            @Override
            public void onNext(GoodsData nurtureDataBean) {
                if (nurtureDataBean != null && nurtureDataBean.getProduct() != null) {
                    nurtureBeanList = nurtureDataBean.getProduct();
                }
                if (!nurtureBeanList.isEmpty()) {
                    filterActivityView.updateGoodsRecyclerView(nurtureBeanList);
                } else {
                    filterActivityView.showEmptyView();
                }
            }
        },goodsType, Constant.PAGE_FIRST, categoryId, sort, gymId);
    }


    public void pullToRefreshGoodsData2(String categoryId, String sort, String gymId) {

        goodsModel.getGoods2(new RefreshSubscriber<GoodsData>(context) {
            @Override
            public void onNext(GoodsData nurtureDataBean) {
                if (nurtureDataBean != null && nurtureDataBean.getProduct() != null) {
                    nurtureBeanList = nurtureDataBean.getProduct();
                }
                if (!nurtureBeanList.isEmpty()) {
                    filterActivityView.updateGoodsRecyclerView(nurtureBeanList);
                } else {
                    filterActivityView.showEmptyView();
                }
            }
        },goodsType, Constant.PAGE_FIRST, categoryId, sort, gymId);
    }


    public void requestMoreGoodsData2(RecyclerView recyclerView, final int pageSize, int page,
                                     String brandId, String sort, String gymId) {
        goodsModel.getGoods2(new RequestMoreSubscriber<GoodsData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(GoodsData nurtureDataBean) {

                Logger.i(TAG,"requestMoreGoodsData onNext");
                if (nurtureDataBean != null && nurtureDataBean.getProduct() != null) {
                    nurtureBeanList = nurtureDataBean.getProduct();
                }
                if (!nurtureBeanList.isEmpty()) {
                    filterActivityView.updateGoodsRecyclerView(nurtureBeanList);
                }
                //没有更多数据了显示到底提示
                if (nurtureBeanList.size() < pageSize) {
                    filterActivityView.showEndFooterView();
                }
            }
        }, goodsType,page, brandId, sort, gymId);
    }






    public void requestMoreGoodsData(RecyclerView recyclerView, final int pageSize, int page,
                                     String brandId, String sort, String gymId) {
        goodsModel.getGoods(new RequestMoreSubscriber<GoodsData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(GoodsData nurtureDataBean) {

                Logger.i(TAG,"requestMoreGoodsData onNext");
                if (nurtureDataBean != null && nurtureDataBean.getProduct() != null) {
                    nurtureBeanList = nurtureDataBean.getProduct();
                }
                if (!nurtureBeanList.isEmpty()) {
                    filterActivityView.updateGoodsRecyclerView(nurtureBeanList);
                }
                //没有更多数据了显示到底提示
                if (nurtureBeanList.size() < pageSize) {
                    filterActivityView.showEndFooterView();
                }
            }
        }, goodsType,page, brandId, sort, gymId);
    }


    public void getVirtualGoodsList( String product_ids) {
        goodsModel.getVirtualGoodsList(new BaseSubscriber<GoodsData>(context) {
            @Override
            public void onNext(GoodsData goodsData) {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        },product_ids);
    }
}
