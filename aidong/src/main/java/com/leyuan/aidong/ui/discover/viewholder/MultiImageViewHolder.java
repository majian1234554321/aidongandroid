package com.leyuan.aidong.ui.discover.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.discover.view.ForceClickImageView;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ScreenUtil;
import com.leyuan.aidong.utils.constant.DynamicType;
import com.leyuan.aidong.widget.ninephotoview.PhotoLayout;
import com.leyuan.aidong.widget.ninephotoview.adapter.PhotoContentsBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片
 * Created by song on 2017/2/16.
 */
public class MultiImageViewHolder extends BaseCircleViewHolder implements PhotoLayout.OnItemClickListener, PhotoLayout.OnSetUpChildLayoutParamsListener {
    private PhotoLayout photoLayout;
    private InnerContainerAdapter adapter;

    public MultiImageViewHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
    }

    @Override
    public void onFindChildView(@NonNull View rootView) {
        photoLayout = (PhotoLayout) itemView.findViewById(R.id.circle_image_container);
        if (photoLayout.getOnItemClickListener() == null) {
            photoLayout.setOnItemClickListener(this);
            photoLayout.setOnSetUpChildLayoutParamsListener(this);
        }
    }

    @Override
    public void onBindDataToChildView(@NonNull final DynamicBean data, int position, @DynamicType int viewType) {
        if (adapter == null) {
            adapter = new InnerContainerAdapter(getContext(), data.image);
            photoLayout.setAdapter(adapter);
        } else {
            adapter.updateData(data.image);
        }
    }

    @Override
    public void onItemClick(ImageView view, int position) {
        if (callback != null) {
            callback.onImageClick(adapter.data, photoLayout.getContentViewsDrawableRects(), position);
        }
    }

    @Override
    public void onSetUpParams(ImageView child, PhotoLayout.LayoutParams p, int position, boolean isSingle) {
        if (isSingle) {
            String url = adapter.data.get(0);
            float imageWidth;
            float imageHeight;
            try {
                imageWidth = FormatUtil.parseFloat(url.substring(url.indexOf("w=") + 2, url.indexOf("_h")));
                imageHeight = FormatUtil.parseFloat(url.substring(url.indexOf("h=") + 2, url.lastIndexOf(".")));
                int maxWidth = (int) (ScreenUtil.getScreenWidth(context) * 3/ 5f);
                int maxHeight = (int) (ScreenUtil.getScreenWidth(context) / 2f);
                int minWidth = maxWidth/2;
                int minHeight = maxHeight /2;
                if (imageHeight > imageWidth) {          //竖图
                    int width = (int) (imageWidth / imageHeight * maxHeight);
                    if(width < minWidth){
                        width = minWidth;
                    }
                    p.height = maxHeight;
                    p.width = width;
                } else {                                 //横图
                    int height = (int) (imageHeight / imageWidth * maxWidth);
                    if(height < minHeight){
                        height = minHeight;
                    }
                    p.width = maxWidth;
                    p.height = height;
                }
            } catch (Exception e) {
                p.height = DensityUtil.dp2px(context, 125);
                p.width = DensityUtil.dp2px(context, 125);
                e.printStackTrace();
            }
        }
    }

    private class InnerContainerAdapter extends PhotoContentsBaseAdapter {
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
            GlideLoader.getInstance().displayImage(data.get(position), convertView);
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
