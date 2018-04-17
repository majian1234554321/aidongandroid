package com.leyuan.aidong.adapter.discover;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.module.photopicker.boxing.BoxingMediaLoader;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.ui.home.view.ItemDragHelperCallback;
import com.leyuan.aidong.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.leyuan.aidong.ui.App.context;


/**
 * 发表动态适配器
 * Created by song on 2017/1/13.
 */
public class PublishDynamicAdapter extends RecyclerView.Adapter<PublishDynamicAdapter.ImageHolder>
        implements ItemDragHelperCallback.OnItemMoveListener{
    private static final int DEFAULT_MAX_IMAGE_COUNT = 6;
    private static final int ITEM_TYPE_MEDIA = 1;
    private static final int ITEM_TYPE_ADD_IMAGE = 2;

    private boolean isPhoto;
    private List<BaseMedia> data = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setData(List<BaseMedia> data, boolean isPhoto) {
        if(data != null) {
            this.data = data;
            this.isPhoto = isPhoto;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            if(!isPhoto) {
                return 1;
            }else{
                if (data.size() < DEFAULT_MAX_IMAGE_COUNT) {
                    return data.size() + 1;
                } else {
                    return DEFAULT_MAX_IMAGE_COUNT;
                }
            }
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.size() > 0 && position < data.size() ? ITEM_TYPE_MEDIA : ITEM_TYPE_ADD_IMAGE;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, final int pos) {
        final int position = holder.getAdapterPosition();
        if(getItemViewType(position) == ITEM_TYPE_MEDIA){
            BoxingMediaLoader.getInstance().displayThumbnail(holder.image, data.get(position).getPath(),
                    holder.itemView.getLayoutParams().width, holder.itemView.getLayoutParams().height);
            holder.delete.setVisibility(View.VISIBLE);
            holder.ivPlay.setVisibility(data.get(position).getType() == BaseMedia.TYPE.IMAGE
                    ? View.GONE : View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onDeleteMediaClick(position);
                    }
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onMediaItemClick(data.get(position));
                    }
                }
            });

        }else {
            holder.image.setBackgroundResource(R.drawable.icon_add3);
            holder.delete.setVisibility(View.GONE);
            holder.ivPlay.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onAddMediaClick();
                    }
                }
            });
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(data, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView delete;
        ImageView ivPlay;

        private ImageHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.dv_image);
            delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            ivPlay = (ImageView) itemView.findViewById(R.id.iv_play);
            int width = (ScreenUtil.getScreenWidth(context) - 8 * context.getResources().getDimensionPixelOffset(R.dimen.media_margin))/3;
            int height = width;
            itemView.getLayoutParams().width = width;
            itemView.getLayoutParams().height = height;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onAddMediaClick();
        void onDeleteMediaClick(int position);

        void onMediaItemClick(BaseMedia baseMedia);
    }

}
