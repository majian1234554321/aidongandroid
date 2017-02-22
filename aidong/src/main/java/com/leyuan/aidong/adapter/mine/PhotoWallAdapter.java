package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.module.photopicker.boxing.BoxingMediaLoader;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 照片墙适配器
 * Created by song on 2017/2/7.
 */
public class PhotoWallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int MAX_UPLOAD_IMAGE_COUNT = 8;        //上传照片数量限制
    private static final int ITEM_TYPE_IMAGE = 1;
    private static final int ITEM_TYPE_ADD_IMAGE = 2;

    private Context context;
    private List<BaseMedia> data = new ArrayList<>();
    private OnItemClickListener listener;

    public PhotoWallAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BaseMedia> data){
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
            BoxingMediaLoader.getInstance().displayThumbnail(((PhotoWallAdapter.ImageHolder) holder).image, data.get(position).getPath(),
                    100, 100);
           // ((PhotoWallAdapter.ImageHolder) holder).image.setImageURI("file//" + data.get(position).getPath());
            ((PhotoWallAdapter.ImageHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,data.size());
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
        ImageView image;
        ImageView delete;

        public ImageHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.dv_image);
            delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            int width = (ScreenUtil.getScreenWidth(context) -
                    5 * context.getResources().getDimensionPixelOffset(R.dimen.photo_wall_margin))/4;
            image.getLayoutParams().width = width;
            image.getLayoutParams().height = width;
        }
    }

    private class AddHolder extends RecyclerView.ViewHolder {
        ImageView add;

        public AddHolder(View itemView) {
            super(itemView);
            add = (ImageView) itemView.findViewById(R.id.iv_add);
            int width = (ScreenUtil.getScreenWidth(context) -
                    5 * context.getResources().getDimensionPixelOffset(R.dimen.photo_wall_margin))/4;
            add.getLayoutParams().width = width;
            add.getLayoutParams().height = width;
        }
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onAddImageItemClick();
       // void onDeleteImage();
    }
}
