package com.leyuan.aidong.adapter.baseadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;

import java.util.ArrayList;
import java.util.List;



public abstract class BaseHolderViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private static final String TAG = "BaseHolderViewAdapter";
    protected Context context;
    protected List<T> data;
    private LayoutInflater mInflater;

    public BaseHolderViewAdapter(@NonNull Context context, @NonNull List<T> data) {
        this.context = context;
        this.data = new ArrayList<>();
        this.data.addAll(data);
        mInflater = LayoutInflater.from(context);
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
            View rootView = mInflater.inflate(getLayoutResId(viewType), parent, false);
            holder = getViewHolder(parent, rootView, viewType);
        } else {
            holder = getViewHolder(parent, null, viewType);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        T data = this.data.get(position);
        holder.itemView.setTag(R.id.recycler_view_tag, data);
        holder.onBindData(data,position);
        onBindData(holder, data, position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void updateData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);
       // notifyDataSetChanged();
    }

    public void addMore(List<T> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(int pos, @NonNull T data) {
        if (this.data != null) {
            this.data.add(pos, data);
            notifyItemInserted(pos);
        }
    }

    public void addData(@NonNull T data) {
        if (this.data != null) {
            this.data.add(data);
            notifyItemInserted(this.data.size() - 1);
        }
    }

    public void deleteData(int pos) {
        if (data != null && data.size() > pos) {
            data.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    public T findData(int pos) {
        if (pos < 0 || pos > data.size()) {
            Log.e(TAG, "no this position");
            return null;
        }
        return data.get(pos);
    }

    protected abstract int getViewType(int position, @NonNull T data);

    protected abstract BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View inflatedView, int viewType);

    protected  int getLayoutResId(int viewType){
        return 0;
    }

    protected void onBindData(BaseRecyclerViewHolder holder, T data, int position) {

    }

}
