package com.example.aidong.adapter.discover;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aidong.R;
import com.example.aidong .utils.DensityUtil;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.ScreenUtil;
import com.example.aidong .utils.qiniu.QiNiuImageProcessUtils;

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
      //  Logger.w("recyclerView","DynamicImageAdapter onCreateViewHolder" );
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_image,parent,false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageHolder holder, final int position) {
       // Logger.w("recyclerView","DynamicImageAdapter onBindView" + position);
        String url = data.get(position);
        if(!url.equals(holder.image.getTag())) {
            holder.image.setTag(url);
            int minWidth = holder.image.getLayoutParams().width;
            GlideLoader.getInstance().displayImage(QiNiuImageProcessUtils.minWidthScale(context,url,minWidth), holder.image);
            Logger.w("recyclerView","DynamicImageAdapter " + QiNiuImageProcessUtils.minWidthScale(context,url,minWidth));
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
        ImageView image;
        public ImageHolder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.dv_image);
            if(data.size() == 1){
                image.getLayoutParams().width = ScreenUtil.getScreenWidth(context);
                image.getLayoutParams().height = ScreenUtil.getScreenWidth(context);
            }else if(data.size() == 2 || data.size() == 4){
                image.getLayoutParams().width = (ScreenUtil.getScreenWidth(context) - DensityUtil.dp2px(context,5))/2 ;
                image.getLayoutParams().height = (ScreenUtil.getScreenWidth(context) - DensityUtil.dp2px(context,5))/2;
            }else if(data.size() == 6){
                image.getLayoutParams().width = (ScreenUtil.getScreenWidth(context)- DensityUtil.dp2px(context,10))/3 ;
                image.getLayoutParams().height = (ScreenUtil.getScreenWidth(context)- DensityUtil.dp2px(context,10))/3;
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
