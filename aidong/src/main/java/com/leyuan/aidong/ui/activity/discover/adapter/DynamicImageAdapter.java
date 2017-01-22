package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动圈图片适配器
 * Created by song on 2016/12/26.
 */
//todo 优化 嵌套RecyclerView导致复用失效
public class DynamicImageAdapter extends RecyclerView.Adapter<DynamicImageAdapter.ImageHolder>{
    private Context context;
    private List<String> data = new ArrayList<>();
    private ImageClickListener imageClickListener;

    public DynamicImageAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> data) {
        if(data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Logger.w("recyclerView","DynamicImageAdapter onCreateViewHolder" );
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_image,parent,false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageHolder holder, final int position) {
        Logger.w("recyclerView","DynamicImageAdapter onBindView" + position);
        String url = data.get(position);
        if(!url.equals(holder.image.getTag())) {
            holder.image.setTag(url);
            holder.image.setImageURI(url);
        }
        ViewCompat.setTransitionName(holder.image, String.valueOf(position) + "transition");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageClickListener != null){
                    imageClickListener.onImageClick(holder.image,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView image;
        public ImageHolder(View itemView) {
            super(itemView);
            image = (SimpleDraweeView)itemView.findViewById(R.id.dv_image);
            if(data.size() == 1){
                image.getLayoutParams().width = ScreenUtil.getScreenWidth(context);
                image.getLayoutParams().height = ScreenUtil.getScreenWidth(context);
            }else if(data.size() == 2 || data.size() == 4){
                image.getLayoutParams().width = ScreenUtil.getScreenWidth(context)/2;
                image.getLayoutParams().height = ScreenUtil.getScreenWidth(context)/2;
            }else if(data.size() == 6){
                image.getLayoutParams().width = ScreenUtil.getScreenWidth(context)/3;
                image.getLayoutParams().height = ScreenUtil.getScreenWidth(context)/3;
            }
        }
    }

    public void setImageClickListener(ImageClickListener listener) {
        this.imageClickListener = listener;
    }

    interface ImageClickListener{
        void onImageClick(View view,int imagePosition);
    }
}
