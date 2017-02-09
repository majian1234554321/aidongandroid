package com.leyuan.aidong.ui.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.model.location.ImageItem;

import java.util.ArrayList;
import java.util.List;


/**
 * 发表动态
 * Created by song on 2017/1/13.
 */
public class PublishDynamicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int DEFAULT_MAX_IMAGE_COUNT = 6;
    public static final String TYPE_PHOTO = "photo";
    private static final String TYPE_VIDEO = "video";
    private static final int ITEM_TYPE_IMAGE = 1;
    private static final int ITEM_TYPE_ADD = 2;

    private Context context;
    private String type;
    private List<ImageItem> data = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public PublishDynamicAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ImageItem> data, String type) {
        if(data != null) {
            this.data = data;
            this.type = type;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            if(TYPE_VIDEO.equals(type)) {
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
        if (data.size() > 0 && position < data.size()) {
            return ITEM_TYPE_IMAGE;
        } else {
            return ITEM_TYPE_ADD;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_IMAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
            return new ImageHolder(view);
        } else if (viewType == ITEM_TYPE_ADD) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_add_image, parent, false);
            return new AddHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ImageHolder) {
            ((ImageHolder) holder).image.setImageURI("file://" + data.get(position).getImagePath());
            ((ImageHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.remove(position);
                    notifyDataSetChanged();
                }
            });
            ((ImageHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onImageItemClick(position);
                    }
                }
            });
        } else if (holder instanceof AddHolder) {
            ((AddHolder) holder).add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onAddImageClick();
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onImageItemClick(int position);
        void onAddImageClick();
    }
}
