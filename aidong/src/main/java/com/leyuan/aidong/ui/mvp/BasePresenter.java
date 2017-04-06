package com.leyuan.aidong.ui.mvp;

import rx.subscriptions.CompositeSubscription;


public abstract class BasePresenter<V> {
    protected V mView;
    protected CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public void setView(V v) {
        this.mView = v;
        this.onAttached();
    }

    public abstract void onAttached();

    public void onDetached() {
        mCompositeSubscription.unsubscribe();
    }
}
