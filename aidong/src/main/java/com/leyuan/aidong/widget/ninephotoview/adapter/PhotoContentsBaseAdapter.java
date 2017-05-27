package com.leyuan.aidong.widget.ninephotoview.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.widget.ninephotoview.adapter.observer.PhotoAdapterObservable;
import com.leyuan.aidong.widget.ninephotoview.adapter.observer.PhotoBaseDataObserver;


/**
 *
 * Created by 大灯泡 on 2016/11/9.
 */

public abstract class PhotoContentsBaseAdapter {


    private PhotoAdapterObservable observable = new PhotoAdapterObservable();


    public void registerDataSetObserver(PhotoBaseDataObserver observer) {
        observable.registerObserver(observer);

    }

    public void unregisterDataSetObserver(PhotoBaseDataObserver observer) {
        observable.unregisterObserver(observer);
    }

    public void notifyDataChanged() {
        observable.notifyChanged();
        observable.notifyInvalidated();
    }


    public abstract ImageView onCreateView(ImageView convertView, ViewGroup parent, int position);

    public abstract void onBindData(int position, @NonNull ImageView convertView);

    public abstract int getCount();
}
