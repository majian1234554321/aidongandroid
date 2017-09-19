/*
 *  Copyright (C) 2017 Bilibili
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.leyuan.aidong.module.photopicker.boxing_impl.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.module.photopicker.boxing.model.BoxingManager;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.impl.ImageMedia;
import com.leyuan.aidong.module.photopicker.boxing_impl.view.MediaItemLayout;
import com.leyuan.aidong.utils.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * A RecyclerView.Adapter for image or video picker showing.
 *
 * @author ChenSL
 */
public class BoxingMediaAdapter extends RecyclerView.Adapter {

    private static final int CAMERA_TYPE = 0;
    private static final int NORMAL_TYPE = 1;

    private int mOffset;
    private boolean mMultiImageMode;

    private List<BaseMedia> mMedias;
    private List<BaseMedia> mSelectedMedias;
    private LayoutInflater mInflater;
    private BoxingConfig mMediaConfig;
    private View.OnClickListener mOnCameraClickListener;
    private View.OnClickListener mOnMediaClickListener;
    private OnCheckListener mOnCheckListener;
    private OnMediaCheckedListener mOnCheckedListener;
    private Drawable mDefaultDrawable;

    public BoxingMediaAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mMedias = new ArrayList<>();
        this.mSelectedMedias = new ArrayList<>();
        this.mMediaConfig = BoxingManager.getInstance().getBoxingConfig();
        this.mOffset = mMediaConfig.isNeedCamera() ? 1 : 0;
        this.mMultiImageMode = mMediaConfig.getMode() == BoxingConfig.Mode.MULTI_IMG;
        this.mOnCheckListener = new OnCheckListener();
        this.mDefaultDrawable = ContextCompat.getDrawable(context, R.drawable.ic_default_image);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mMediaConfig.isNeedCamera()) {
            return CAMERA_TYPE;
        }
        return NORMAL_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (CAMERA_TYPE == viewType) {
            return new CameraViewHolder(mInflater.inflate(R.layout.layout_recycleview_header, parent, false));
        }
        return new ImageViewHolder(mInflater.inflate(R.layout.layout_recycleview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CameraViewHolder) {
            CameraViewHolder viewHolder = (CameraViewHolder) holder;
            viewHolder.mCameraLayout.setOnClickListener(mOnCameraClickListener);
        } else {
            int pos = position - mOffset;

            final BaseMedia media = mMedias.get(pos);
            final ImageViewHolder vh = (ImageViewHolder) holder;

            vh.mItemLayout.setDrawable(mDefaultDrawable);
            vh.mItemLayout.setTag(media);

            vh.mItemLayout.setOnClickListener(mOnMediaClickListener);
            vh.mItemLayout.setTag(R.id.media_item_check, pos);
            vh.mItemLayout.setMedia(media);
            vh.mItemChecked.setVisibility(mMultiImageMode ? View.VISIBLE : View.GONE);
            if (mMultiImageMode && media instanceof ImageMedia) {
                vh.mItemLayout.setChecked(((ImageMedia) media).isSelected());
                vh.mItemChecked.setTag(R.id.media_layout, vh.mItemLayout);
                vh.mItemChecked.setTag(media);
                vh.mItemChecked.setOnClickListener(mOnCheckListener);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        Logger.i("media","getItemCount size =   " + (mMedias.size() + mOffset)+" ,mMedias.size() = " +mMedias.size());
        return mMedias.size() + mOffset;
    }

    public void setOnCameraClickListener(View.OnClickListener onCameraClickListener) {
        mOnCameraClickListener = onCameraClickListener;
    }

    public void setOnCheckedListener(OnMediaCheckedListener onCheckedListener) {
        mOnCheckedListener = onCheckedListener;
    }

    public void setOnMediaClickListener(View.OnClickListener onMediaClickListener) {
        mOnMediaClickListener = onMediaClickListener;
    }

    public List<BaseMedia> getSelectedMedias() {
        return mSelectedMedias;
    }

    public void setSelectedMedias(List<BaseMedia> selectedMedias) {
        if (selectedMedias == null) {
            return;
        }
        mSelectedMedias.clear();
        mSelectedMedias.addAll(selectedMedias);
//        Logger.i("media","setSelectedMedias size =   " + mSelectedMedias.size());
    }

    public void addAllData(@NonNull List<BaseMedia> data) {
        this.mMedias.addAll(data);
        Logger.i("media","addAllData mMedias size =   " + mMedias.size());
        notifyDataSetChanged();
    }

    public void addData(BaseMedia media) {
        this.mMedias.add(media);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.mMedias.clear();
    }

    public List<BaseMedia> getAllMedias() {
        return mMedias;
    }

    private static class ImageViewHolder extends RecyclerView.ViewHolder {
        MediaItemLayout mItemLayout;
        View mItemChecked;

        ImageViewHolder(View itemView) {
            super(itemView);
            mItemLayout = (MediaItemLayout) itemView.findViewById(R.id.media_layout);
            mItemChecked = itemView.findViewById(R.id.media_item_check);
        }
    }

    private static class CameraViewHolder extends RecyclerView.ViewHolder {
        View mCameraLayout;
        ImageView imageView;

        CameraViewHolder(final View itemView) {
            super(itemView);
            mCameraLayout = itemView.findViewById(R.id.camera_layout);
            imageView = (ImageView) itemView.findViewById(R.id.camera_txt);
            if (BoxingManager.getInstance().getBoxingConfig().isVideoMode()) {
                imageView.setImageResource(R.drawable.ic_video_white);
            }
        }
    }

    private class OnCheckListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            MediaItemLayout itemLayout = (MediaItemLayout) v.getTag(R.id.media_layout);
            BaseMedia media = (BaseMedia) v.getTag();
            if (mMediaConfig.getMode() == BoxingConfig.Mode.MULTI_IMG) {
                if (mOnCheckedListener != null) {
                    mOnCheckedListener.onChecked(itemLayout, media);
                }
            }
        }
    }

    public interface OnMediaCheckedListener {
        /**
         * In multi image mode, selecting a {@link BaseMedia} or undo.
         */
        void onChecked(View v, BaseMedia iMedia);
    }

}
