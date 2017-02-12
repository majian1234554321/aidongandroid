package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.data.GoodsData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.entity.data.ShopData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.module.pay.AliPay;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.WeiXinPay;
import com.leyuan.aidong.ui.mvp.model.CartModel;
import com.leyuan.aidong.ui.mvp.model.RecommendModel;
import com.leyuan.aidong.ui.mvp.model.impl.CartModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.RecommendModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.CartPresent;
import com.leyuan.aidong.ui.mvp.view.CartActivityView;
import com.leyuan.aidong.ui.mvp.view.GoodsSkuPopupWindowView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public class CartPresentImpl implements CartPresent{
    private static final String TYPE = "cart";
    private Context context;
    private CartModel cartModel;
    private RecommendModel recommendModel;
    private CartActivityView cartActivityView;
    private GoodsSkuPopupWindowView skuPopupWindowView;

    public CartPresentImpl(Context context) {
        this.context = context;
        if(cartModel == null){
            cartModel = new CartModelImpl();
        }
    }

    public CartPresentImpl(Context context, CartActivityView cartActivityView) {
        this.context = context;
        this.cartActivityView = cartActivityView;
        if(cartModel == null){
            cartModel = new CartModelImpl();
        }
        if(recommendModel == null){
            recommendModel = new RecommendModelImpl();
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
                    cartActivityView.showEmptyGoodsView();
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
                }else {
                    cartActivityView.showEmptyGoodsView();
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

    @Override
    public void pullToRefreshRecommendData() {
        recommendModel.getRecommendGoods(new RefreshSubscriber<GoodsData>(context) {
            @Override
            public void onNext(GoodsData goodsData) {
                List<GoodsBean> goodsList = new ArrayList<>();
                if(goodsData != null && goodsData.getProduct() != null){
                    goodsList = goodsData.getProduct();
                }
                if(goodsList.isEmpty()){
                    cartActivityView.showEmptyRecommendGoodsView();
                }else {
                    cartActivityView.updateRecommendGoods(goodsList);
                }
            }
        },TYPE, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreRecommendData(RecyclerView recyclerView, final int pageSize, int page) {
        recommendModel.getRecommendGoods(new RequestMoreSubscriber<GoodsData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(GoodsData goodsData) {
                List<GoodsBean> goodsList = new ArrayList<>();
                if(goodsData != null && goodsData.getProduct() != null){
                    goodsList = goodsData.getProduct();
                }
                if(!goodsList.isEmpty()){
                    cartActivityView.updateRecommendGoods(goodsList);
                }
                //没有更多数据了显示到底提示
                if(goodsList.size() < pageSize){
                    cartActivityView.showEndFooterView();
                }
            }
        },TYPE,page);
    }

    @Override
    public void payCart(String integral, String coin, String coupon, String payType,
                        String pickUpId, final PayInterface.PayListener listener,String... id) {
        cartModel.payCart(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                String payType = payOrderData.getOrder().getPayType();
                PayInterface payInterface = "alipay".equals(payType) ? new AliPay(context,listener)
                        : new WeiXinPay(context,listener);
                payInterface.payOrder(payOrderData.getOrder());
            }
        },integral,coin,coupon,payType,pickUpId,id);
    }
}
