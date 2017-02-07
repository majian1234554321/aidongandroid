package com.leyuan.aidong.ui.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ImageBean;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.MAX_UPLOAD_IMAGE_COUNT;

/**
 * 照片墙适配器
 * Created by song on 2017/2/7.
 */
public class PhotoWallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int ITEM_TYPE_IMAGE = 1;
    private static final int ITEM_TYPE_ADD_IMAGE = 2;

    private Context context;
    private List<ImageBean> data = new ArrayList<>();
    private OnItemClickListener listener;

    public PhotoWallAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ImageBean> data){
        if(data != null){
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            if(data.size() < MAX_UPLOAD_IMAGE_COUNT){
                return data.size() + 1;
            }else{
                return MAX_UPLOAD_IMAGE_COUNT;
            }
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.size() > 0 && position < data.size()) {
            return ITEM_TYPE_IMAGE;
        } else {
            return ITEM_TYPE_ADD_IMAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_IMAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
            return new PhotoWallAdapter.ImageHolder(view);
        } else if (viewType == ITEM_TYPE_ADD_IMAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_add_image, parent, false);
            return new PhotoWallAdapter.AddHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PhotoWallAdapter.ImageHolder) {
            ((PhotoWallAdapter.ImageHolder) holder).image.setImageURI(data.get(position).getUrl());
            ((PhotoWallAdapter.ImageHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.remove(position);
                    notifyDataSetChanged();
                }
            });

            ((PhotoWallAdapter.ImageHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onPhotoItemClick(position);
                    }
                }
            });

        } else if (holder instanceof PhotoWallAdapter.AddHolder) {
            ((PhotoWallAdapter.AddHolder) holder).add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onAddImageItemClick();
                    }
                }
            });
        }
    }

    private class ImageHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView image;
        ImageView delete;

        public ImageHolder(View itemView) {
            super(itemView);
            image = (SimpleDraweeView) itemView.findViewById(R.id.dv_image);
            delete = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }

    private class AddHolder extends RecyclerView.ViewHolder {
        ImageView add;

        public AddHolder(View itemView) {
            super(itemView);
            add = (ImageView) itemView.findViewById(R.id.iv_add);
        }
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onAddImageItemClick();
        void onPhotoItemClick(int position);
    }
}
