package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.ShopData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.ui.mvp.model.CartModel;
import com.leyuan.aidong.ui.mvp.model.impl.CartModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.CartPresent;
import com.leyuan.aidong.ui.mvp.view.CartActivityView;
import com.leyuan.aidong.ui.mvp.view.GoodsSkuPopupWindowView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public class CartPresentImpl implements CartPresent {
    private Context context;
    private CartModel cartModel;
    private CartActivityView cartActivityView;
    private GoodsSkuPopupWindowView skuPopupWindowView;

    public CartPresentImpl(Context context, CartActivityView cartActivityView) {
        this.context = context;
        this.cartActivityView = cartActivityView;
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
    public void commonLoadingData(final SwitcherLayout switcherLayout) {
        cartModel.getCart(new CommonSubscriber<ShopData>(switcherLayout) {
            @Override
            public void onNext(ShopData shopData) {
                if(shopData != null && shopData.getCart() != null  && !shopData.getCart().isEmpty()){
                    switcherLayout.showContentLayout();
                    cartActivityView.updateRecyclerView(shopData.getCart());
                }else {
                    cartActivityView.showEmptyView();
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
                    cartActivityView.updateRecyclerView(shopData.getCart());
                }
            }
        });
    }

    @Override
    public void deleteCart(String ids) {
        cartModel.deleteCart(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                cartActivityView.setDeleteCart(baseBean);  //未作校验 上层自行判断
            }
        },ids);
    }

    @Override
    public void updateCart(String id, int mount) {
        cartModel.updateCart(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                cartActivityView.setUpdateCart(baseBean);  //未作校验 上层自行判断
            }
        },id,mount);
    }

    @Override
    public void addCart(String skuCode, int amount,String gymId) {
        cartModel.addCart(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                skuPopupWindowView.addCartResult(baseBean);   //未作校验 上层自行判断
            }
        },skuCode,amount,gymId);
    }
}
