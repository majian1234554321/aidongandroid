package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ImageBean;
import com.leyuan.aidong.module.photopicker.boxing.BoxingMediaLoader;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 照片墙适配器
 * Created by song on 2017/2/7.
 */
public class PhotoWallAdapter extends RecyclerView.Adapter<PhotoWallAdapter.ImageHolder>{
    private static final int MAX_UPLOAD_IMAGE_COUNT = 8;        //上传照片数量限制
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
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_update_photo_image, parent, false);
        return new PhotoWallAdapter.ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageHolder holder, final int pos) {
        final int position = holder.getAdapterPosition();
        if(getItemViewType(position) == ITEM_TYPE_IMAGE){
            ImageBean bean = data.get(position);
            if(bean.getType() == ImageBean.TYPE.LOCAL_IMAGE){
                BoxingMediaLoader.getInstance().displayThumbnail( holder.image, bean.getPath(),100, 100);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listener != null){
                            listener.onDeleteLocalImage(position);
                        }
                    }
                });
            }else {
                GlideLoader.getInstance().displayImage(bean.getUrl(),holder.image);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listener != null){
                            listener.onDeleteNetImage(position);
                        }
                    }
                });
            }
            holder.delete.setVisibility(View.VISIBLE);
        } else {
            holder.image.setBackgroundResource(R.drawable.outline_add_box_white_36);
            holder.delete.setVisibility(View.GONE);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onAddImageItemClick();
                    }
                }
            });
        }
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView delete;

        private ImageHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.dv_image);
            delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            int width = (ScreenUtil.getScreenWidth(context) -
                    5 * context.getResources().getDimensionPixelOffset(R.dimen.photo_wall_margin))/4;
            int height = width;
            itemView.getLayoutParams().width = width;
            itemView.getLayoutParams().height = height;
        }
    }



    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onAddImageItemClick();
        void onDeleteNetImage(int position);
        void onDeleteLocalImage(int position);
    }
}
