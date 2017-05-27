package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 裁剪图片适配器
 * Created by song on 17/05/26.
 */
public class ClipPhotosAdapter extends RecyclerView.Adapter<ClipPhotosAdapter.PhotoHolder>{
    private Context context;
    private List<BaseMedia> photos = new ArrayList<>();
    private PhotoClickListener photoClickListener;

    public ClipPhotosAdapter(Context context) {
        this.context = context;
    }

    public void setPhotos(List<BaseMedia> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_clip_photo,parent,false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, final int position) {
        BaseMedia baseMedia = photos.get(position);
        GlideLoader.getInstance().displayImage(baseMedia.getPath(),holder.photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoClickListener != null){
                    photoClickListener.onPhotoItemClick(position);
                }
            }
        });
    }

    class PhotoHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        public PhotoHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.iv_clip_photo);
        }
    }

    public void setPhotoClickListener(PhotoClickListener photoClickListener) {
        this.photoClickListener = photoClickListener;
    }

    public interface PhotoClickListener{
        void onPhotoItemClick(int position);
    }
}
