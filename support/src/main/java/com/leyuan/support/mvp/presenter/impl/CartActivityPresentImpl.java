package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.GoodsBean;
import com.leyuan.support.http.subscriber.NormalSubscriber;
import com.leyuan.support.mvp.model.CartModel;
import com.leyuan.support.mvp.model.impl.CartModelImpl;
import com.leyuan.support.mvp.presenter.CartActivityPresent;
import com.leyuan.support.mvp.view.CartActivityView;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.List;

import rx.Subscriber;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public class CartActivityPresentImpl implements CartActivityPresent{
    private Context context;
    private CartModel cartModel;
    private CartActivityView cartActivityView;

    public CartActivityPresentImpl(Context context, CartActivityView cartActivityView) {
        this.context = context;
        this.cartActivityView = cartActivityView;
        cartModel = new CartModelImpl();
    }

    @Override
    public void normalLoadingData(final SwitcherLayout switcherLayout) {
        cartModel.getCart(new NormalSubscriber<List<GoodsBean>>(switcherLayout) {
            @Override
            public void onNext(List<GoodsBean> goodsBeanList) {
                if(goodsBeanList == null ||goodsBeanList.isEmpty()){
                    switcherLayout.showEmptyLayout();
                }else{
                    cartActivityView.updateRecyclerView(goodsBeanList);
                }
            }
        });
    }

    @Override
    public void pullToRefreshData(RecyclerView recyclerView) {

    }


    @Override
    public void deleteGoods(String ids) {
        cartModel.deleteCart(new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {

            }
        },ids);
    }

    @Override
    public void updateGoods(String id, int mount) {
        cartModel.updateCart(new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {

            }
        },id,mount);
    }
}
