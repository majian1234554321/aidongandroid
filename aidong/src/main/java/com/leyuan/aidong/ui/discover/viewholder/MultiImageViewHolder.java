package com.leyuan.aidong.ui.discover.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.discover.view.ForceClickImageView;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.constant.DynamicType;
import com.leyuan.aidong.widget.ninephotoview.PhotoLayout;
import com.leyuan.aidong.widget.ninephotoview.adapter.PhotoContentsBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片
 * Created by song on 2017/2/16.
 */
public class MultiImageViewHolder extends BaseCircleViewHolder implements PhotoLayout.OnItemClickListener, PhotoLayout.OnBlankClickListener {
    private PhotoLayout photoLayout;
    private InnerContainerAdapter adapter;
    private int position;

    public MultiImageViewHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
    }

    @Override
    public void onFindChildView(@NonNull View rootView) {
        photoLayout = (PhotoLayout) itemView.findViewById(R.id.circle_image_container);
        if (photoLayout.getOnItemClickListener() == null) {
            photoLayout.setOnItemClickListener(this);
            photoLayout.setOnBlankClickListener(this);
        }
    }

    @Override
    public void onBindDataToChildView(@NonNull final DynamicBean data, int position, @DynamicType int viewType) {
        this.position = position;
        if (adapter == null) {
            adapter = new InnerContainerAdapter(getContext(), data.image);
            photoLayout.setAdapter(adapter);
        } else {
            adapter.updateData(data.image);
        }
    }

    @Override
    public void onItemClick(ImageView view, int position) {
        if(callback != null){
            callback.onImageClick(adapter.data,photoLayout.getContentViewsDrawableRects() ,position);
        }
    }

    @Override
    public void onBlankClick() {
        if(callback != null){
            callback.onBackgroundClick(position);
        }
    }

    private static class InnerContainerAdapter extends PhotoContentsBaseAdapter {
        private Context context;
        private List<String> data;

        InnerContainerAdapter(Context context, List<String> data) {
            this.context = context;
            this.data = new ArrayList<>();
            this.data.addAll(data);
        }

        @Override
        public ImageView onCreateView(ImageView convertView, ViewGroup parent, int position) {
            if (convertView == null) {
                convertView = new ForceClickImageView(context);
                convertView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            return convertView;
        }

        @Override
        public void onBindData(int position, @NonNull ImageView convertView) {
            GlideLoader.getInstance().displayImage(data.get(position),convertView);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        public void updateData(List<String> data) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataChanged();
        }
    }
}
