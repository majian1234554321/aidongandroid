package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.adapter.baseadapter.BaseRecyclerViewAdapter;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.discover.viewholder.BaseCircleViewHolder;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.utils.Logger;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 爱动圈适配器
 * Created by song on 2017/2/16.
 * //todo 用自定义控件显示图片替换根据图片数量引用不同布局的写法
 */
public class CircleDynamicAdapter extends BaseRecyclerViewAdapter<DynamicBean> {
    private SparseArray<ViewHolderInfo> viewHolderKeyArray;
    private IDynamicCallback callback;
    private boolean showLikeAndCommentLayout;

    private CircleDynamicAdapter(Builder builder) {
        this(builder.context, builder.data);
        this.viewHolderKeyArray = builder.viewHolderKeyArray;
        this.callback = builder.callback;
        this.showLikeAndCommentLayout = builder.showLikeAndCommentLayout;
    }

    private CircleDynamicAdapter(@NonNull Context context, @NonNull List<DynamicBean> data) {
        super(context, data);
    }

    @Override
    protected int getViewType(int position, @NonNull DynamicBean data) {
        return data.getDynamicType();
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return 0;
    }

    @Override
    protected BaseCircleViewHolder getViewHolder(ViewGroup parent, View inflatedView, int viewType) {
        ViewHolderInfo viewHolderInfo = viewHolderKeyArray.get(viewType);
        if (viewHolderInfo != null) {
            BaseCircleViewHolder circleBaseViewHolder = createCircleViewHolder(context, parent, viewHolderInfo);
            if (circleBaseViewHolder != null) {
                circleBaseViewHolder.setCallback(callback);
                circleBaseViewHolder.showLikeAndCommentLayout(showLikeAndCommentLayout);
            }
            return circleBaseViewHolder;
        }
        return null;
    }


    public static final class Builder<T> {
        private Context context;
        private SparseArray<ViewHolderInfo> viewHolderKeyArray = new SparseArray<>();
        private List<T> data;
        private DynamicPresent dynamicPresent;
        private IDynamicCallback callback;
        private boolean showLikeAndCommentLayout;

        public Builder(Context context) {
            this.context = context;
            data = new ArrayList<>();
        }

        public Builder<T> addType(Class<? extends BaseCircleViewHolder> viewHolderClass, int viewType, int layoutResId) {
            final ViewHolderInfo info = new ViewHolderInfo();
            info.holderClass = viewHolderClass;
            info.viewType = viewType;
            info.layoutResID = layoutResId;
            viewHolderKeyArray.put(viewType, info);
            return this;
        }

        public Builder<T> setData(List<T> data) {
            this.data = data;
            return this;
        }

        public Builder<T> setDynamicCallback(IDynamicCallback callback) {
            this.callback = callback;
            return this;
        }

        public Builder<T> showLikeAndCommentLayout(boolean show) {
            this.showLikeAndCommentLayout = show;
            return this;
        }

        public CircleDynamicAdapter build() {
            return new CircleDynamicAdapter(this);
        }
    }

    private static final class ViewHolderInfo {
        public Class<? extends BaseCircleViewHolder> holderClass;
        public int viewType;
        public int layoutResID;
    }

    private BaseCircleViewHolder createCircleViewHolder(Context context, ViewGroup viewGroup, ViewHolderInfo viewHolderInfo) {
        if (viewHolderInfo == null) {
            throw new NullPointerException("not find this viewHolder");
        }
        Class<? extends BaseCircleViewHolder> className = viewHolderInfo.holderClass;
        Logger.i("class  >>>  " + className);
        Constructor constructor = null;
        try {
            constructor = className.getConstructor(Context.class, ViewGroup.class, int.class);
            return (BaseCircleViewHolder) constructor.newInstance(context, viewGroup, viewHolderInfo.layoutResID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface IDynamicCallback {

        void onBackgroundClick(DynamicBean dynamicBean);
        void onAvatarClick(String id);
        void onVideoClick(String url);
        void onImageClick(List<String> photoUrls, List<Rect> viewLocalRect, int currentPhotoPosition);
        void onLikeClick(int position,String id,boolean isLike);
        void onCommentClick();
        void onShareClick();
    }
}
