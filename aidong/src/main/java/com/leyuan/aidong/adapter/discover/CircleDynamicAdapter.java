package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.adapter.baseadapter.BaseHolderViewAdapter;
import com.leyuan.aidong.entity.CommentBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.discover.viewholder.BaseCircleViewHolder;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.constant.DynamicType;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 爱动圈适配器
 * Created by song on 2017/2/16.
 * //todo 用自定义控件显示图片替换根据图片数量引用不同布局的写法
 */
public class CircleDynamicAdapter extends BaseHolderViewAdapter<DynamicBean> {
    private SparseArray<ViewHolderInfo> viewHolderKeyArray;
    private IDynamicCallback callback;
    private boolean showLikeAndCommentLayout;
    private boolean showFollowButton;
    private boolean showCMDMessageLayout;

    private CircleDynamicAdapter(Context context,List<DynamicBean> data){
        super(context,data);
    }

    private CircleDynamicAdapter(Builder builder) {
        this(builder.context, builder.data);
        this.callback = builder.callback;
        this.viewHolderKeyArray = builder.viewHolderKeyArray;
        this.showFollowButton = builder.showFollowButton;
        this.showCMDMessageLayout = builder.showCMDMessageLayout;

        this.showLikeAndCommentLayout = builder.showLikeAndCommentLayout;
    }

    @Override
    protected int getViewType(int position, @NonNull DynamicBean data) {
        return data.getDynamicType();
    }

    @Override
    protected BaseCircleViewHolder getViewHolder(ViewGroup parent, View inflatedView, int viewType) {
        ViewHolderInfo viewHolderInfo = viewHolderKeyArray.get(viewType);
        if (viewHolderInfo != null) {
            BaseCircleViewHolder circleBaseViewHolder = createCircleViewHolder(context, parent, viewHolderInfo);
            if (circleBaseViewHolder != null) {
                circleBaseViewHolder.setCallback(callback);
                circleBaseViewHolder.setShowFollowButton(showFollowButton);
                circleBaseViewHolder.setShowCMDMessageLayout(showCMDMessageLayout);
                circleBaseViewHolder.showLikeAndCommentLayout(showLikeAndCommentLayout);
            }
            return circleBaseViewHolder;
        }
        return null;
    }


    public static final class Builder<T> {
        private Context context;
        private SparseArray<ViewHolderInfo> viewHolderKeyArray ;
        private List<T> data;
        private IDynamicCallback callback;
        private boolean showLikeAndCommentLayout;
        private boolean showFollowButton;
        private boolean showCMDMessageLayout = true;

        public Builder(Context context) {
            this.context = context;
            data = new ArrayList<>();
            viewHolderKeyArray = new SparseArray<>();
        }

        public Builder<T> addType(Class<? extends BaseCircleViewHolder> viewHolderClass,
                                  @DynamicType int viewType, int layoutResId) {
            final ViewHolderInfo info = new ViewHolderInfo();
            info.holderClass = viewHolderClass;
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

        public Builder<T> showFollowButton(boolean show) {
            this.showFollowButton = show;
            return this;
        }

        public Builder<T> showCMDMessageLayout(boolean show) {
            this.showCMDMessageLayout = show;
            return this;
        }

        public CircleDynamicAdapter build() {
            return new CircleDynamicAdapter(this);
        }
    }

    private static final class ViewHolderInfo {
        private Class<? extends BaseCircleViewHolder> holderClass;
        private int layoutResID;
    }

    private BaseCircleViewHolder createCircleViewHolder(Context context, ViewGroup parent, ViewHolderInfo viewHolderInfo) {
        if (viewHolderInfo == null) {
            throw new NullPointerException("not find this viewHolder");
        }
        Class<? extends BaseCircleViewHolder> className = viewHolderInfo.holderClass;
        Logger.i("class  >>>  " + className);
        Constructor constructor;
        try {
            constructor = className.getConstructor(Context.class, ViewGroup.class, int.class);
            return (BaseCircleViewHolder) constructor.newInstance(context, parent, viewHolderInfo.layoutResID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface IDynamicCallback {
        void onBackgroundClick(int position);
        void onAvatarClick(String id, String userType);
        void onVideoClick(String url);
        void onImageClick(List<String> photoUrls, List<Rect> viewLocalRect, int currPosition);
        void onLikeClick(int position,String id,boolean isLike);
        void onCommentClick(DynamicBean dynamicBean,int position);
        void onShareClick(DynamicBean dynamic);
        void onFollowClick(String id);



        void onLikeClick(DynamicBean dynamic);

        void onCommentListClick(DynamicBean dynamic, int position, CommentBean item);
    }


    public static abstract class SimpleDynamicCallback implements IDynamicCallback{

        @Override
        public void onBackgroundClick(int position) {

        }

        @Override
        public void onAvatarClick(String id, String userType) {

        }

        @Override
        public void onVideoClick(String url) {

        }

        @Override
        public void onImageClick(List<String> photoUrls, List<Rect> viewLocalRect, int currPosition) {

        }

        @Override
        public void onLikeClick(int position, String id, boolean isLike) {

        }

        @Override
        public void onCommentClick(DynamicBean dynamicBean,int position) {

        }

        @Override
        public void onShareClick(DynamicBean dynamic) {

        }

        @Override
        public void onFollowClick(String id) {

        }

        @Deprecated
        @Override
        public void onLikeClick(DynamicBean dynamic) {

        }
    }
}
