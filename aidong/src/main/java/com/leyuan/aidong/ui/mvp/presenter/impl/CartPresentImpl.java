package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.entity.data.ShopData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.PayUtils;
import com.leyuan.aidong.ui.mine.view.CartHeaderView;
import com.leyuan.aidong.ui.mvp.model.CartModel;
import com.leyuan.aidong.ui.mvp.model.impl.CartModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.CartPresent;
import com.leyuan.aidong.ui.mvp.view.GoodsSkuPopupWindowView;
import com.leyuan.aidong.ui.mvp.view.UpdateDeliveryInfoActivityView;
import com.leyuan.aidong.widget.SwitcherLayout;

import static com.leyuan.aidong.utils.Constant.DELIVERY_EXPRESS;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public class CartPresentImpl implements CartPresent{
    private Context context;
    private CartModel cartModel;
    private CartHeaderView cartHeaderView;
    private GoodsSkuPopupWindowView skuPopupWindowView;
    private UpdateDeliveryInfoActivityView updateDeliveryInfoActivityView;

    public CartPresentImpl(Context context, CartHeaderView cartHeaderView) {
        this.context = context;
        this.cartHeaderView = cartHeaderView;
        if(cartModel == null){
            cartModel = new CartModelImpl();
        }
    }

    public CartPresentImpl(Context context, UpdateDeliveryInfoActivityView view) {
        this.context = context;
        this.updateDeliveryInfoActivityView = view;
        if(cartModel == null){
            cartModel = new CartModelImpl();
        }
    }

    public CartPresentImpl(Context context, GoodsSkuPopupWindowView skuPopupWindowView) {
        this.context = context;
        this.skuPopupWindowView = skuPopupWindowView;
        if(cartModel == null){
            cartModel = new CartModelImpl();
        }
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout) {
        cartModel.getCart(new CommonSubscriber<ShopData>(context,switcherLayout) {
            @Override
            public void onNext(ShopData shopData) {
                if(shopData != null && shopData.getCart() != null  && !shopData.getCart().isEmpty()){
                    switcherLayout.showContentLayout();
                    cartHeaderView.updateCartRecyclerView(shopData.getCart());
                }else {
                    cartHeaderView.showEmptyGoodsView();
                }
            }
        });
    }

    @Override
    public void pullToRefreshData() {
        cartModel.getCart(new RefreshSubscriber<ShopData>(context) {
            @Override
            public void onNext(ShopData shopData) {
                if(shopData != null && shopData.getCart() != null  && !shopData.getCart().isEmpty()){
                    cartHeaderView.updateCartRecyclerView(shopData.getCart());
                }else {
                    cartHeaderView.showEmptyGoodsView();
                }
            }
        });
    }

    @Override
    public void deleteSingleGoods(final String id, final int shopPosition, final int goodsPosition) {
        cartModel.deleteCart(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                cartHeaderView.deleteSingleGoodsResult(baseBean,id,shopPosition,goodsPosition);
            }
        },id);
    }

    @Override
    public void deleteMultiGoods(final String ids) {
        cartModel.deleteCart(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                cartHeaderView.deleteMultiGoodsResult(baseBean);
            }
        },ids);
    }

    @Override
    public void updateGoodsCount(String id, int mount, final int shopPosition, final int goodsPosition) {
        cartModel.updateDeliveryInfo(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                cartHeaderView.updateGoodsCountResult(baseBean,shopPosition,goodsPosition);  //未作校验 上层自行判断
            }
        },id,mount);
    }

    @Override
    public void updateGoodsDeliveryInfo(String id, final String gymId) {
        cartModel.updateDeliveryInfo(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if(DELIVERY_EXPRESS.equals(gymId)){
                    updateDeliveryInfoActivityView.setExpressResult(baseBean);
                }else {
                    updateDeliveryInfoActivityView.setSelfDeliveryResult(baseBean);
                }

            }
        },id,gymId);
    }

    @Override
    public void addCart(String skuCode, int amount,String gymId,String recommendId) {
        cartModel.addCart(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                skuPopupWindowView.addCartResult(baseBean);   //未作校验 上层自行判断
            }
        },skuCode,amount,gymId,recommendId);
    }

    @Override
    public void payCart(String integral, String coin, String coupon, String payType, String pickUpId,
                        String pickUpDate, final PayInterface.PayListener listener, String... id) {
        cartModel.payCart(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                PayUtils.pay(context,payOrderData.getOrder(),listener);
            }
        },integral,coin,coupon,payType,pickUpId,pickUpDate,id);
    }
}
