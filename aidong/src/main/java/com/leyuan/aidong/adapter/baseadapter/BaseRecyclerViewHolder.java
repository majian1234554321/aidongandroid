package com.leyuan.aidong.adapter.baseadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseRecyclerViewHolder(Context context, ViewGroup parent, int layoutResId) {
        super(LayoutInflater.from(context).inflate(layoutResId, parent, false));
    }

    public abstract void onBindData(T data, int position);


    protected Context getContext(){
        return itemView.getContext();
    }
}
