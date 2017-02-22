package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.adapter.baseadapter.BaseHolderViewAdapter;
import com.leyuan.aidong.adapter.baseadapter.BaseRecyclerViewHolder;
import com.leyuan.aidong.entity.HomeBean;
import com.leyuan.aidong.utils.Logger;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页适配器
 * Created by song on 2017/2/21.
 */
public class HomeAdapter extends BaseHolderViewAdapter<HomeBean> {
    private SparseArray<ViewHolderInfo> viewHolderArray;

    private HomeAdapter(Builder builder) {
        super(builder.context,builder.data);
        this.viewHolderArray = builder.viewHolderArray;
    }

    @Override
    protected int getViewType(int position, @NonNull HomeBean data) {
        return data.getItemType();
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View inflatedView, int viewType) {
        ViewHolderInfo viewHolderInfo = viewHolderArray.get(viewType);
        if (viewHolderInfo != null) {
            return createCircleViewHolder(context, parent, viewHolderInfo);
        }
        return null;
    }

    public static final class Builder<T> {
        private Context context;
        private SparseArray<ViewHolderInfo> viewHolderArray;
        private List<T> data;

        public Builder(Context context) {
            this.context = context;
            data = new ArrayList<>();
            viewHolderArray = new SparseArray<>();
        }

        public HomeAdapter.Builder<T> addType(Class<? extends BaseRecyclerViewHolder> viewHolderClass,
                                              int viewType, int layoutResId) {
            final ViewHolderInfo info = new HomeAdapter.ViewHolderInfo();
            info.holderClass = viewHolderClass;
            info.layoutResID = layoutResId;
            viewHolderArray.put(viewType, info);
            return this;
        }

        public HomeAdapter.Builder<T> setData(List<T> data) {
            this.data = data;
            return this;
        }

        public HomeAdapter build() {
            return new HomeAdapter(this);
        }
    }

    private static final class ViewHolderInfo {
        private Class<? extends BaseRecyclerViewHolder> holderClass;
        private int layoutResID;
    }

    private BaseRecyclerViewHolder createCircleViewHolder(Context context, ViewGroup viewGroup, ViewHolderInfo viewHolderInfo) {
        if (viewHolderInfo == null) {
            throw new NullPointerException("not find this viewHolder");
        }
        Class<? extends BaseRecyclerViewHolder> className = viewHolderInfo.holderClass;
        Logger.i("class  >>>  " + className);
        Constructor constructor ;
        try {
            constructor = className.getConstructor(Context.class, ViewGroup.class, int.class);
            return (BaseRecyclerViewHolder) constructor.newInstance(context, viewGroup, viewHolderInfo.layoutResID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
