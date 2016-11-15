package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.ui.mvp.model.CartModel;
import com.leyuan.aidong.ui.mvp.model.impl.CartModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.CartActivityPresent;
import com.leyuan.aidong.ui.mvp.view.CartActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.List;

import rx.Subscriber;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public class CartPresentImpl implements CartActivityPresent{
    private Context context;
    private CartModel cartModel;
    private CartActivityView cartActivityView;

    public CartPresentImpl(Context context, CartActivityView cartActivityView) {
        this.context = context;
        this.cartActivityView = cartActivityView;
        cartModel = new CartModelImpl();
    }

    @Override
    public void normalLoadingData(final SwitcherLayout switcherLayout) {
        cartModel.getCart(new CommonSubscriber<List<GoodsBean>>(switcherLayout) {
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
    public void pullToRefreshData() {

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