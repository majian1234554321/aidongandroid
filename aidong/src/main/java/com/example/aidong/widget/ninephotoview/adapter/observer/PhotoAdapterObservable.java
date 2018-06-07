package com.example.aidong.widget.ninephotoview.adapter.observer;

/**
 *
 * Created by 大灯泡 on 2016/11/9.
 */

public class PhotoAdapterObservable extends PhotoImageObservable<PhotoBaseDataObserver> {

    public void notifyChanged() {
        synchronized (observers) {
            for (int i = observers.size() - 1; i >= 0; i--) {
                observers.get(i).onChanged();
            }
        }
    }

    public void notifyInvalidated() {
        synchronized (observers) {
            for (int i = observers.size() - 1; i >= 0; i--) {
                observers.get(i).onInvalidated();
            }
        }
    }
}
