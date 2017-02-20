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



public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder<T>> {
    private static final String TAG = "BaseRecyclerViewAdapter";

    protected Context context;
    protected List<T> data;
    protected LayoutInflater mInflater;

    private OnRecyclerViewItemClickListener<T> onRecyclerViewItemClickListener;
    private OnRecyclerViewLongItemClickListener<T> onRecyclerViewLongItemClickListener;

    public BaseRecyclerViewAdapter(@NonNull Context context, @NonNull List<T> datas) {
        this.context = context;
        this.data = new ArrayList<>();
        this.data.addAll(datas);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return getViewType(position, data.get(position));
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder holder = null;
        if (getLayoutResId(viewType) != 0) {
            View rootView = mInflater.inflate(getLayoutResId(viewType), parent, false);
            holder = getViewHolder(parent, rootView, viewType);
        } else {
            holder = getViewHolder(parent, null, viewType);
        }
        setUpItemEvent(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        T data = this.data.get(position);
        holder.itemView.setTag(R.id.recycler_view_tag, data);
        holder.onBindData(data, position);
        onBindData(holder, data, position);
    }

    private void setUpItemEvent(final BaseRecyclerViewHolder holder) {
        if (onRecyclerViewItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这个获取位置的方法，防止添加删除导致位置不变
                    int layoutPosition = holder.getAdapterPosition();
                    onRecyclerViewItemClickListener.onItemClick(holder.itemView,
                                                                layoutPosition,
                                                                data.get(layoutPosition));
                }
            });
        }
        if (onRecyclerViewLongItemClickListener != null) {
            //longclick
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getAdapterPosition();
                    onRecyclerViewLongItemClickListener.onItemLongClick(holder.itemView,
                                                                        layoutPosition,
                                                                        data.get(layoutPosition));
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void updateData(List<T> datas) {
        this.data.clear();
        this.data.addAll(datas);
       // notifyDataSetChanged();
    }

    public void addMore(List<T> datas) {
        this.data.addAll(datas);
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

    protected abstract int getLayoutResId(int viewType);

    protected abstract BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View inflatedView, int viewType);

    protected void onBindData(BaseRecyclerViewHolder<T> holder, T data, int position) {

    }

    public OnRecyclerViewItemClickListener<T> getOnRecyclerViewItemClickListener() {
        return onRecyclerViewItemClickListener;
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener<T> onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public OnRecyclerViewLongItemClickListener<T> getOnRecyclerViewLongItemClickListener() {
        return onRecyclerViewLongItemClickListener;
    }

    public void setOnRecyclerViewLongItemClickListener(OnRecyclerViewLongItemClickListener<T> onRecyclerViewLongItemClickListener) {
        this.onRecyclerViewLongItemClickListener = onRecyclerViewLongItemClickListener;
    }
}
