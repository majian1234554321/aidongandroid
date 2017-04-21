package com.leyuan.aidong.adapter.baseadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



public abstract class BaseHolderViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    protected Context context;
    protected List<T> data;
    private LayoutInflater inflater;

    public BaseHolderViewAdapter(@NonNull Context context, @NonNull List<T> data) {
        this.context = context;
        this.data = new ArrayList<>();
        this.data.addAll(data);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if(data == null || data.isEmpty()){
            return -1;
        }
        return getViewType(position, data.get(position));
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder holder;
        if (getLayoutResId(viewType) != 0) {
            View rootView = inflater.inflate(getLayoutResId(viewType), parent, false);
            holder = getViewHolder(parent, rootView, viewType);
        } else {
            holder = getViewHolder(parent, null, viewType);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        T data = this.data.get(position);
        holder.onBindData(data,position);
        //onBindData(holder, data, position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void updateData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    protected abstract int getViewType(int position, @NonNull T data);

    protected abstract BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View inflatedView, int viewType);

    protected  int getLayoutResId(int viewType){
        return 0;
    }
}
